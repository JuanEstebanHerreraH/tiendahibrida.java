
package com.mycompany.tiendaadmindesktop.Modelo;


public class Producto {
private int id;
private String nombre;
private String descripcion;
private double precio;
private int stock;
private String imagen;
private double subtotal;      
private byte[] imagenBlob;


    public byte[] getImagenBlob() {
        return imagenBlob;
    }

    public void setImagenBlob(byte[] imagenBlob) {
        this.imagenBlob = imagenBlob;
    }

public int getstock() { return stock; }
public void setstock(int stock) { this.stock = stock; }

public double getSubtotal() {
    return subtotal;
}

public void setSubtotal(double subtotal) {
    this.subtotal = subtotal;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}