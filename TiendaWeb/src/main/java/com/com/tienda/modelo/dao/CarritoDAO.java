package com.com.tienda.modelo.dao;

import com.com.tienda.modelo.entidades.Producto;
import com.com.tienda.modelo.entidades.Venta;
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

    // Obtener carrito
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
                p.setStock(rs.getInt("cantidad")); // cantidad en carrito
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

    // Obtener stock
    public int obtenerStockProducto(int idProducto) throws Exception {
        String sql = "SELECT stock FROM productos WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("stock") : 0;
        }
    }

    // Actualizar cantidad en carrito
    public void actualizarCantidad(int idUsuario, int idProducto, int cantidad) throws Exception {
        String sql = "UPDATE Carrito SET cantidad=? WHERE id_usuario=? AND id_producto=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idUsuario);
            ps.setInt(3, idProducto);
            ps.executeUpdate();
        }
    }

    // Eliminar producto del carrito
    public void eliminarProducto(int idUsuario, int idProducto) throws Exception {
        String sql = "DELETE FROM Carrito WHERE id_usuario=? AND id_producto=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        }
    }

    // CALCULAR TOTAL
    public double calcularTotal(int idUsuario) throws Exception {
        String sql = """
            SELECT SUM(p.precio * c.cantidad) AS total
            FROM Carrito c
            JOIN productos p ON p.id = c.id_producto
            WHERE c.id_usuario=?
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getDouble("total") : 0;
        }
    }

    // Crear venta (retorna id generado)
    public int crearVenta(int idUsuario, double total) throws Exception {
        String sql = """
            INSERT INTO ventas(id_usuario, total, fecha)
            OUTPUT INSERTED.id
            VALUES (?, ?, GETDATE())
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setDouble(2, total);

            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // Registrar DETALLE
    public void registrarDetalle(int idVenta, Producto p) throws Exception {

        String sql = "INSERT INTO detalle_venta(id_venta, id_producto, cantidad, precio, subtotal) VALUES(?,?,?,?,?)";

        double subtotal = p.getPrecio() * p.getStock(); // stock = cantidad en carrito

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            ps.setInt(2, p.getId());
            ps.setInt(3, p.getStock());
            ps.setDouble(4, p.getPrecio());
            ps.setDouble(5, subtotal);
            ps.executeUpdate();
        }
    }

    // Descontar stock del producto
    public void descontarStock(int idProducto, int cantidad) throws Exception {
        String sql = "UPDATE productos SET stock = stock - ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        }
    }
public Venta obtenerVenta(int idVenta) throws Exception {
    String sql = "SELECT id, id_usuario, total, fecha FROM ventas WHERE id = ?";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idVenta);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Venta v = new Venta();
            v.setId(rs.getInt("id"));
            v.setIdUsuario(rs.getInt("id_usuario"));
            v.setTotal(rs.getDouble("total"));
            v.setFecha(rs.getString("fecha"));
            return v;
        }
    }
    return null;
}

public List<Producto> obtenerDetallesVenta(int idVenta) throws Exception {

    String sql = """
        SELECT dv.id_producto, p.nombre, dv.cantidad, dv.precio, dv.subtotal
        FROM detalle_venta dv
        JOIN productos p ON p.id = dv.id_producto
        WHERE dv.id_venta = ?
    """;

    List<Producto> lista = new ArrayList<>();

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idVenta);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Producto p = new Producto();
            p.setId(rs.getInt("id_producto"));
            p.setNombre(rs.getString("nombre"));
            p.setPrecio(rs.getDouble("precio"));
            p.setStock(rs.getInt("cantidad")); // EN DETALLE stock = cantidad comprada
            p.setSubtotal(rs.getDouble("subtotal"));
            lista.add(p);
        }
    }
    return lista;
}



}

