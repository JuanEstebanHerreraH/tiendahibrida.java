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

    private final JTextField txtBuscar;
    private final JButton btnNuevo;
    private final JButton btnEliminar;

    public ProductoFrame() {

        setTitle("Gestión de Productos");
        setSize(900, 450);
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

        // ================= BUSCADOR =================
        txtBuscar = new JTextField(20);
        JLabel lblBuscar = new JLabel("Buscar:");

        JPanel panelTop = new JPanel();
        panelTop.add(lblBuscar);
        panelTop.add(txtBuscar);

        add(panelTop, BorderLayout.NORTH);

        sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }
        });

        // ================= BOTONES =================
        JPanel panelBotones = new JPanel();

        btnNuevo = new JButton("Nuevo");
        btnEliminar = new JButton("Eliminar");

        btnNuevo.addActionListener(e -> abrirFormulario());
        btnEliminar.addActionListener(e -> eliminarProducto());

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        cargarProductos();
        setVisible(true);
    }

    // ================= MÉTODOS =================

    private void cargarProductos() {

        modelo.setRowCount(0);
        ProductoDAO dao = new ProductoDAO();

        for (Producto p : dao.listar()) {
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

        String texto = txtBuscar.getText();

        if (texto.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(
                RowFilter.regexFilter("(?i)" + texto, 1) // columna Nombre
            );
        }
    }

    private void abrirFormulario() {
        ProductoForm form = new ProductoForm(this);
        form.setVisible(true);
        cargarProductos();
    }

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

}
