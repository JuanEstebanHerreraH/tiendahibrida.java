package com.com.tienda.modelo.entidades;

public class Venta {
private int id;
private int idUsuario;
private int idProducto; 
private int cantidad;   
private String fecha;
private double total;
// GETTERS Y SETTERS

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public double getTotal() {
    return total;
}

public void setTotal(double total) {
    this.total = total;
}

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
