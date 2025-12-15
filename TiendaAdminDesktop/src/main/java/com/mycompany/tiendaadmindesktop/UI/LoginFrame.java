package com.mycompany.tiendaadmindesktop.UI;

import com.mycompany.tiendaadmindesktop.DAO.UsuarioDAO;
import com.mycompany.tiendaadmindesktop.DAO.LogDAO;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final JTextField txtUsuario;
    private final JPasswordField txtPassword;
    private final JButton btnLogin;

    public LoginFrame() {

        setTitle("Login Admin");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Ingresar");

        add(new JLabel("Email"));
        add(txtUsuario);

        add(new JLabel("Contraseña"));
        add(txtPassword);

        add(new JLabel());
        add(btnLogin);

        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

    String email = txtUsuario.getText().trim();
    String password = new String(txtPassword.getPassword());

    if (email.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Completa todos los campos");
        return;
    }

    UsuarioDAO dao = new UsuarioDAO();

    if (dao.loginAdmin(email, password)) {

        LogDAO.registrar(email, "LOGIN_ADMIN");

        DashboardFrame dashboardFrame = new DashboardFrame();
        dispose();

    } else {
        JOptionPane.showMessageDialog(this,
            "Credenciales inválidas o no eres admin");
    }

    }
}
