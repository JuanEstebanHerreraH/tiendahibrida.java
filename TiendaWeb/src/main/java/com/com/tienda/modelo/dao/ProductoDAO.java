package com.com.tienda.modelo.dao;

import com.com.tienda.modelo.entidades.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // LISTAR TODOS LOS PRODUCTOS
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, precio, stock, imagen FROM productos";

        try (Connection con = ConexionDB.getConexion();
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
            System.out.println("Error listar productos: " + e.getMessage());
        }

        return lista;
    }

    // AGREGAR PRODUCTO
    public boolean agregar(Producto p) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, imagen) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getImagen());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error agregar producto: " + e.getMessage());
            return false;
        }
    }

    // BUSCAR PRODUCTO POR ID
    public Producto buscar(int id) {
        Producto p = null;
        String sql = "SELECT id, nombre, descripcion, precio, stock, imagen FROM productos WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Producto();
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setStock(rs.getInt("stock"));
                    p.setImagen(rs.getString("imagen"));
                }
            }

        } catch (Exception e) {
            System.out.println("Error buscar producto: " + e.getMessage());
        }

        return p;
    }

    // ACTUALIZAR PRODUCTO
    public boolean actualizar(Producto p) {
        String sql = "UPDATE productos SET nombre=?, descripcion=?, precio=?, stock=?, imagen=? "
                   + "WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getImagen());
            ps.setInt(6, p.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error actualizar producto: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR PRODUCTO
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
    // OBTENER PRODUCTOS POR CATEGORIA
public List<Producto> obtenerPorCategoria(String categoria) {
    List<Producto> lista = new ArrayList<>();
    String sql = "SELECT id, nombre, descripcion, precio, stock, imagen FROM productos WHERE descripcion = ?";

    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, categoria);
        try (ResultSet rs = ps.executeQuery()) {
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
        }

    } catch (Exception e) {
        System.out.println("Error obtener productos por categoría: " + e.getMessage());
    }

    return lista;
}

// OBTENER TODAS LAS CATEGORIAS (DISTINTAS)
public List<String> obtenerCategorias() {
    List<String> categorias = new ArrayList<>();
    String sql = "SELECT DISTINCT descripcion FROM productos";

    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            categorias.add(rs.getString("descripcion"));
        }

    } catch (Exception e) {
        System.out.println("Error obtener categorias: " + e.getMessage());
    }

    return categorias;
}

}
