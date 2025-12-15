package com.mycompany.tiendaadmindesktop.UI;

import com.mycompany.tiendaadmindesktop.DAO.LogDAO;
import com.mycompany.tiendaadmindesktop.DAO.ProductoDAO;
import com.mycompany.tiendaadmindesktop.Modelo.Producto;
import com.mycompany.tiendaadmindesktop.Util.Sesion;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;

public class ProductoFrame extends JFrame {

    private final JTable tabla;
    private final DefaultTableModel modelo;
    private final TableRowSorter<DefaultTableModel> sorter;

    private final JTextField txtBuscarNombre;
    private final JTextField txtBuscarDescripcion;
    private final JButton btnNuevo;
    private final JButton btnEditar;
    private final JButton btnEliminar;
    private final JButton btnVolver;

    public ProductoFrame() {

        setTitle("Gestión de Productos");
        setSize(950, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= TABLA =================
        modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Descripción", "Precio", "Stock"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ================= BUSCADORES =================
        txtBuscarNombre = new JTextField(15);
        txtBuscarDescripcion = new JTextField(15);

        JLabel lblBuscarNombre = new JLabel("Buscar por nombre:");
        JLabel lblBuscarDesc = new JLabel("Buscar por descripción:");

        JPanel panelTop = new JPanel();
        panelTop.add(lblBuscarNombre);
        panelTop.add(txtBuscarNombre);
        panelTop.add(lblBuscarDesc);
        panelTop.add(txtBuscarDescripcion);

        add(panelTop, BorderLayout.NORTH);

        sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);

        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filtrar(); }
            public void removeUpdate(DocumentEvent e) { filtrar(); }
            public void changedUpdate(DocumentEvent e) { filtrar(); }
        };

        txtBuscarNombre.getDocument().addDocumentListener(listener);
        txtBuscarDescripcion.getDocument().addDocumentListener(listener);

        // ================= BOTONES =================
        JPanel panelBotones = new JPanel();

        btnNuevo = new JButton("Nuevo");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnVolver = new JButton("Volver al menú");

        btnNuevo.addActionListener(e -> abrirFormularioNuevo());
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnVolver.addActionListener(e -> volverMenu());

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        cargarProductos();
        setVisible(true);
    }

    // ================= MÉTODOS =================

    private void cargarProductos() {
        modelo.setRowCount(0);

        for (Producto p : new ProductoDAO().listar()) {
            modelo.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock()
            });
        }
    }

    private void filtrar() {
        String textoNombre = txtBuscarNombre.getText().trim().toLowerCase();
        String textoDesc = txtBuscarDescripcion.getText().trim().toLowerCase();

        if (textoNombre.isEmpty() && textoDesc.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    String nombre = entry.getStringValue(1).toLowerCase();
                    String descripcion = entry.getStringValue(2).toLowerCase();
                    boolean coincideNombre = nombre.contains(textoNombre);
                    boolean coincideDescripcion = descripcion.contains(textoDesc);
                    return coincideNombre && coincideDescripcion;
                }
            });
        }
    }

    // ===== NUEVO =====
    private void abrirFormularioNuevo() {
        ProductoForm form = new ProductoForm(this);
        form.setVisible(true);
        cargarProductos();
    }

    // ===== EDITAR =====
    private void editarProducto() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecciona un producto para editar");
            return;
        }

        int id = (int) tabla.getValueAt(fila, 0);

        Producto producto = new ProductoDAO().buscarPorId(id);

        ProductoForm form = new ProductoForm(this, producto);
        form.setVisible(true);

        LogDAO.registrar(
            Sesion.usuario,
            "EDITAR_PRODUCTO ID=" + id
        );

        cargarProductos();
    }

    // ===== ELIMINAR =====
    private void eliminarProducto() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecciona un producto");
            return;
        }

        int id = (int) tabla.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Eliminar producto?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            new ProductoDAO().eliminar(id);

            LogDAO.registrar(
                Sesion.usuario,
                "ELIMINAR_PRODUCTO ID=" + id
            );

            cargarProductos();
        }
    }

    // ===== VOLVER =====
    private void volverMenu() {
        dispose();
        new DashboardFrame();
    }
}
