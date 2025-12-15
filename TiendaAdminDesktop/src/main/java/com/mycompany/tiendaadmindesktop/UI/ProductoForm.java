package com.mycompany.tiendaadmindesktop.UI;

import com.mycompany.tiendaadmindesktop.DAO.LogDAO;
import com.mycompany.tiendaadmindesktop.DAO.ProductoDAO;
import com.mycompany.tiendaadmindesktop.Modelo.Producto;
import com.mycompany.tiendaadmindesktop.Util.Sesion;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

public class ProductoForm extends JDialog {

    private JTextField txtNombre, txtPrecio, txtStock;
    private JTextArea txtDescripcion;
    private JLabel lblImagen;
    private byte[] imagen;

    private Producto producto;
    private boolean editando;

    // NUEVO
    public ProductoForm(JFrame parent) {
        super(parent, "Nuevo Producto", true);
        this.editando = false;
        init();
    }

    // EDITAR
    public ProductoForm(JFrame parent, Producto p) {
        super(parent, "Editar Producto", true);
        this.producto = p;
        this.editando = true;
        init();
        cargar();
    }

    private void init() {

        setSize(420, 380);
        setLocationRelativeTo(getParent());
        setLayout(new GridLayout(7, 2, 5, 5));

        txtNombre = new JTextField();
        txtDescripcion = new JTextArea();
        txtPrecio = new JTextField();
        txtStock = new JTextField();
        lblImagen = new JLabel("Sin imagen");

        JButton btnImg = new JButton("Imagen");
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Nombre"));
        add(txtNombre);

        add(new JLabel("Descripción"));
        add(new JScrollPane(txtDescripcion));

        add(new JLabel("Precio"));
        add(txtPrecio);

        add(new JLabel("Stock"));
        add(txtStock);

        add(new JLabel("Imagen"));
        add(btnImg);

        add(new JLabel());
        add(lblImagen);

        add(new JLabel());
        add(btnGuardar);

        btnImg.addActionListener(e -> imagen());
        btnGuardar.addActionListener(e -> guardar());
    }

    private void cargar() {
        txtNombre.setText(producto.getNombre());
        txtDescripcion.setText(producto.getDescripcion());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtStock.setText(String.valueOf(producto.getStock()));
        imagen = producto.getImagenBlob();
        if (imagen != null) lblImagen.setText("Imagen cargada");
    }

    private void imagen() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            lblImagen.setText(f.getName());
            try {
                imagen = Files.readAllBytes(f.toPath());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar imagen");
            }
        }
    }

    private void guardar() {

        if (!editando) producto = new Producto();

        producto.setNombre(txtNombre.getText());
        producto.setDescripcion(txtDescripcion.getText());
        producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
        producto.setStock(Integer.parseInt(txtStock.getText()));
        producto.setImagenBlob(imagen);

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
