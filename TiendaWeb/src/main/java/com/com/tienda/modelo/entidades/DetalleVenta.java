package com.com.tienda.modelo.entidades;

public class DetalleVenta {

private int idProducto;
private String nombre;
private int cantidad;
private double precio;
private double subtotal;

public int getIdProducto() {
    return idProducto;
}

public void setIdProducto(int idProducto) {
    this.idProducto = idProducto;
}

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public int getCantidad() {
    return cantidad;
}

public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
}

public double getPrecio() {
    return precio;
}

public void setPrecio(double precio) {
    this.precio = precio;
}

public double getSubtotal() {
    return subtotal;
}

public void setSubtotal(double subtotal) {
    this.subtotal = subtotal;
}

}
