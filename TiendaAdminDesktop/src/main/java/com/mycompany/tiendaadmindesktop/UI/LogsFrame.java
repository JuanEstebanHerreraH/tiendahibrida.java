package com.mycompany.tiendaadmindesktop.UI;

import com.mycompany.tiendaadmindesktop.DAO.LogDAO;
import com.mycompany.tiendaadmindesktop.Modelo.Log;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class LogsFrame extends JFrame {

    private final JTable tabla;
    private final DefaultTableModel modelo;

    public LogsFrame() {


        setTitle("Auditoría del Sistema");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(
            new String[]{"ID", "Usuario", "Acción", "Fecha"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarLogs();
        setVisible(true);
        
        JButton btnExcel = new JButton("Exportar Excel");
JPanel panelTop = new JPanel();
panelTop.add(btnExcel);
add(panelTop, BorderLayout.NORTH);
btnExcel.addActionListener(e -> {

    JFileChooser chooser = new JFileChooser();
    chooser.setSelectedFile(new File("logs.xlsx"));

    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        LogDAO.exportarExcel(
            chooser.getSelectedFile().getAbsolutePath()
        );
        JOptionPane.showMessageDialog(this,
            "Logs exportados a Excel");
    }
});

JButton btnPDF = new JButton("Exportar PDF");
panelTop.add(btnPDF);

btnPDF.addActionListener(e -> {

    JFileChooser chooser = new JFileChooser();
    chooser.setSelectedFile(new File("logs.pdf"));

    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        LogDAO.exportarPDF(
            chooser.getSelectedFile().getAbsolutePath()
        );
        JOptionPane.showMessageDialog(this,
            "Logs exportados a PDF");
    }
});

    }

    private void cargarLogs() {

        modelo.setRowCount(0);

        for (Log log : LogDAO.listar()) {
            modelo.addRow(new Object[]{
                log.getId(),
                log.getUsuario(),
                log.getAccion(),
                log.getFecha()
            });
        }
    }
}
