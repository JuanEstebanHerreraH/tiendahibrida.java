package com.mycompany.tiendaadmindesktop.UI;

import javax.swing.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {

        setTitle("Panel Admin");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnProductos = new JButton("Productos");
        JButton btnLogs = new JButton("Logs");

        btnProductos.addActionListener(e -> {
            new ProductoFrame();
            dispose(); // 🔥 cierra el menú
        });

        btnLogs.addActionListener(e -> {
            new LogsFrame();
            dispose(); // 🔥 cierra el menú
        });

        JPanel panel = new JPanel();
        panel.add(btnProductos);
        panel.add(btnLogs);
        add(panel);

        setVisible(true);
    }
}
