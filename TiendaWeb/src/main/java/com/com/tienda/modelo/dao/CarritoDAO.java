package com.com.tienda.modelo.dao;

import com.com.tienda.modelo.entidades.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAO {

    private Connection getConnection() throws Exception {
        return ConexionDB.getConexion();
    }

    // Agregar producto al carrito
    public void agregarProducto(int idUsuario, int idProducto) throws Exception {

        String sqlBuscar = "SELECT cantidad FROM Carrito WHERE id_usuario=? AND id_producto=?";
        String sqlInsert = "INSERT INTO Carrito(id_usuario, id_producto, cantidad) VALUES (?, ?, 1)";
        String sqlUpdate = "UPDATE Carrito SET cantidad = cantidad + 1 WHERE id_usuario=? AND id_producto=?";

        int stockDisponible = obtenerStockProducto(idProducto);

        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sqlBuscar);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idProducto);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int cantidadActual = rs.getInt("cantidad");
                if (cantidadActual < stockDisponible) {
                    PreparedStatement ps2 = conn.prepareStatement(sqlUpdate);
                    ps2.setInt(1, idUsuario);
                    ps2.setInt(2, idProducto);
                    ps2.executeUpdate();
                }
            } else {
                if (stockDisponible > 0) {
                    PreparedStatement ps2 = conn.prepareStatement(sqlInsert);
                    ps2.setInt(1, idUsuario);
                    ps2.setInt(2, idProducto);
                    ps2.executeUpdate();
                }
            }
        }
    }

    // Obtener carrito del usuario
    public List<Producto> obtenerCarrito(int idUsuario) throws Exception {
        String sql = """
            SELECT p.id, p.nombre, p.precio, c.cantidad
            FROM Carrito c
            JOIN productos p ON p.id = c.id_producto
            WHERE c.id_usuario = ?
        """;

        List<Producto> lista = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("cantidad")); // aquí cantidad en carrito
                lista.add(p);
            }
        }
        return lista;
    }

    // Vaciar carrito
    public void vaciarCarrito(int idUsuario) throws Exception {
        String sql = "DELETE FROM Carrito WHERE id_usuario=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        }
    }

    // Actualizar cantidad de un producto en el carrito respetando stock
    public void actualizarCantidad(int idUsuario, int idProducto, int cantidadDeseada) throws Exception {
        int stockDisponible = obtenerStockProducto(idProducto);
        if (cantidadDeseada > stockDisponible) {
            cantidadDeseada = stockDisponible;
        }
        if (cantidadDeseada < 1) {
            cantidadDeseada = 1;
        }

        String sqlUpdate = "UPDATE Carrito SET cantidad = ? WHERE id_usuario = ? AND id_producto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
            ps.setInt(1, cantidadDeseada);
            ps.setInt(2, idUsuario);
            ps.setInt(3, idProducto);
            ps.executeUpdate();
        }
    }

    // Obtener stock real del producto
    public int obtenerStockProducto(int idProducto) throws Exception {
        String sql = "SELECT stock FROM productos WHERE id = ?";
        int stock = 0;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stock = rs.getInt("stock");
            }
        }
        return stock;
    }
    // Obtener la cantidad que el usuario tiene de un producto en el carrito
public int obtenerCantidadEnCarrito(int idUsuario, int idProducto) throws Exception {
    String sql = "SELECT cantidad FROM Carrito WHERE id_usuario=? AND id_producto=?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        ps.setInt(2, idProducto);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("cantidad");
        } else {
            return 0;
        }
    }
}

public void eliminarProducto(int idUsuario, int idProducto) throws Exception {
    String sql = "DELETE FROM Carrito WHERE id_usuario=? AND id_producto=?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        ps.setInt(2, idProducto);
        ps.executeUpdate();
    }
}


}
