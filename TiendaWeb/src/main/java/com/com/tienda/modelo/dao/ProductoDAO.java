package com.com.tienda.modelo.dao;

import com.tienda.modelo.entidades.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // LISTAR TODOS
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecioUSD(rs.getDouble("precio_usd"));
                p.setStock(rs.getInt("stock"));
                p.setCategoria(rs.getString("categoria"));
                p.setImagenURL(rs.getString("imagen_url"));
                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error listar productos: " + e.getMessage());
        }

        return lista;
    }


    // AGREGAR
    public boolean agregar(Producto p) {
String sql = "INSERT INTO productos "
           + "(nombre, precio_usd, stock, categoria, imagen_url) "
           + "VALUES (?, ?, ?, ?, ?)";


        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecioUSD());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getCategoria());
            ps.setString(5, p.getImagenURL());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error agregar producto: " + e.getMessage());
            return false;
        }
    }


    // BUSCAR POR ID
    public Producto buscar(int id) {
        Producto p = null;
        String sql = "SELECT * FROM productos WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecioUSD(rs.getDouble("precio_usd"));
                p.setStock(rs.getInt("stock"));
                p.setCategoria(rs.getString("categoria"));
                p.setImagenURL(rs.getString("imagen_url"));
            }

        } catch (Exception e) {
            System.out.println("Error buscar producto: " + e.getMessage());
        }

        return p;
    }
    // ACTUALIZAR PRODUCTO
public boolean actualizar(Producto p) {
    String sql = "UPDATE productos SET nombre=?, precio_usd=?, stock=?, categoria=?, imagen_url=? "
               + "WHERE id=?";

    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, p.getNombre());
        ps.setDouble(2, p.getPrecioUSD());
        ps.setInt(3, p.getStock());
        ps.setString(4, p.getCategoria());
        ps.setString(5, p.getImagenURL());
        ps.setInt(6, p.getId());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        System.out.println("Error actualizar producto: " + e.getMessage());
        return false;
    }
}
public boolean eliminar(int id) {
    String sql = "DELETE FROM productos WHERE id = ?";

    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        System.out.println("Error eliminar producto: " + e.getMessage());
        return false;
    }
}

}
