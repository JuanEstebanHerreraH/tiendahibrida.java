package com.mycompany.tiendaadmindesktop.UI;

import com.mycompany.tiendaadmindesktop.DAO.UsuarioDAO;
import com.mycompany.tiendaadmindesktop.DAO.LogDAO;
import com.mycompany.tiendaadmindesktop.Util.Sesion;

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

        add(new JLabel("Usuario"));
        add(txtUsuario);

        add(new JLabel("Contraseña"));
        add(txtPassword);

        add(new JLabel());
        add(btnLogin);

        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Completa todos los campos");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.loginAdmin(usuario, password)) {

            // ✅ AQUÍ VAN LOS LOGS
            Sesion.usuario = usuario;
            LogDAO.registrar(Sesion.usuario, "LOGIN_ADMIN");

            new DashboardFrame();
            dispose();

        } else {
            JOptionPane.showMessageDialog(this,
                "Credenciales inválidas o no eres admin");
        }
    }
}
