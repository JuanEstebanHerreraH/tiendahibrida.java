package com.mycompany.tiendaadmindesktop.DAO;

import com.mycompany.tiendaadmindesktop.Modelo.Producto;
import com.mycompany.tiendaadmindesktop.Util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listar() {

        List<Producto> lista = new ArrayList<>();

        String sql = """
            SELECT id, nombre, descripcion, precio, stock, imagen
            FROM productos
        """;

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                p.setImagen(rs.getString("imagen"));

                lista.add(p);
            }

        } catch (Exception e) {
        }

        return lista;
    }

public void eliminar(int id) {

    String sql = "DELETE FROM productos WHERE id = ?";

    try (Connection con = ConexionDB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        ps.executeUpdate();

    } catch (Exception e) {
    }
}

public void insertar(Producto p) {

    String sql = """
        INSERT INTO productos (nombre, descripcion, precio, stock, imagen)
        VALUES (?, ?, ?, ?, ?)
    """;

    try (Connection con = ConexionDB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, p.getNombre());
        ps.setString(2, p.getDescripcion());
        ps.setDouble(3, p.getPrecio());
        ps.setInt(4, p.getStock());
        ps.setBytes(5, p.getImagenBlob());

        ps.executeUpdate();

    } catch (Exception e) {
    }
}


public void actualizar(Producto p) {

    String sql = """
        UPDATE productos
        SET nombre=?, descripcion=?, precio=?, stock=?, imagen=?
        WHERE id=?
    """;

    try (Connection con = ConexionDB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, p.getNombre());
        ps.setString(2, p.getDescripcion());
        ps.setDouble(3, p.getPrecio());
        ps.setInt(4, p.getStock());
        ps.setBytes(5, p.getImagenBlob());
        ps.setInt(6, p.getId());

        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}


}