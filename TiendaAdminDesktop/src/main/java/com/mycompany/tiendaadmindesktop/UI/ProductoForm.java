package com.mycompany.tiendaadmindesktop.UI;

import com.mycompany.tiendaadmindesktop.DAO.LogDAO;
import com.mycompany.tiendaadmindesktop.DAO.ProductoDAO;
import com.mycompany.tiendaadmindesktop.Modelo.Producto;
import com.mycompany.tiendaadmindesktop.Util.Sesion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;

public class ProductoForm extends JDialog {

    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JButton btnGuardar;
    private JButton btnImagen;
    private JLabel lblImagen;

    private Producto producto;
    private boolean editando = false;
    private byte[] imagenSeleccionada;

    // NUEVO
    public ProductoForm(JFrame parent) {
        super(parent, "Nuevo Producto", true);
        initUI();
    }

    // EDITAR
    public ProductoForm(JFrame parent, Producto p) {
        super(parent, "Editar Producto", true);
        this.producto = p;
        this.editando = true;
        initUI();
        cargarDatos();
    }

    private void initUI() {

        setSize(450, 420);
        setLocationRelativeTo(getParent());
        setLayout(new GridLayout(7, 2));

        txtNombre = new JTextField();
        txtDescripcion = new JTextArea();
        txtPrecio = new JTextField();
        txtStock = new JTextField();
        btnGuardar = new JButton("Guardar");
        btnImagen = new JButton("Seleccionar Imagen");
        lblImagen = new JLabel("Sin imagen");

        add(new JLabel("Nombre"));
        add(txtNombre);

        add(new JLabel("Descripción"));
        add(new JScrollPane(txtDescripcion));

        add(new JLabel("Precio"));
        add(txtPrecio);

        add(new JLabel("Stock"));
        add(txtStock);

        add(new JLabel("Imagen"));
        add(btnImagen);

        add(new JLabel());
        add(lblImagen);

        add(new JLabel());
        add(btnGuardar);

        btnImagen.addActionListener(e -> seleccionarImagen());
        btnGuardar.addActionListener(e -> guardar());

        txtPrecio.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        txtStock.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
    }

    private void seleccionarImagen() {

        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            lblImagen.setText(file.getName());

            try {
                imagenSeleccionada = Files.readAllBytes(file.toPath());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al cargar imagen");
            }
        }
    }

    private void cargarDatos() {
        txtNombre.setText(producto.getNombre());
        txtDescripcion.setText(producto.getDescripcion());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtStock.setText(String.valueOf(producto.getStock()));
        imagenSeleccionada = producto.getImagenBlob();
        if (imagenSeleccionada != null) {
            lblImagen.setText("Imagen cargada");
        }
    }

private void guardar() {

    if (!editando) {
        producto = new Producto();
    }

    producto.setNombre(txtNombre.getText());
    producto.setDescripcion(txtDescripcion.getText());
    producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
    producto.setStock(Integer.parseInt(txtStock.getText()));

    if (imagenSeleccionada != null) {
        producto.setImagenBlob(imagenSeleccionada);
    }

    ProductoDAO dao = new ProductoDAO();

    if (editando) {
        dao.actualizar(producto);
        LogDAO.registrar(Sesion.usuario, "EDITAR_PRODUCTO");
    } else {
        dao.insertar(producto);
        LogDAO.registrar(Sesion.usuario, "CREAR_PRODUCTO");
    }

    dispose();
}
}
