package com.com.tienda.modelo.dao;

import com.com.tienda.modelo.entidades.Venta;
import java.sql.*;

public class VentaDAO {

    public boolean registrarVenta(Venta v) {
String sql = "INSERT INTO ventas (id_usuario, id_producto, cantidad, fecha) "
           + "VALUES (?, ?, ?, GETDATE())";


        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, v.getIdUsuario());
            ps.setInt(2, v.getIdProducto());
            ps.setInt(3, v.getCantidad());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error registrar venta: " + e.getMessage());
            return false;
        }
    }
}
