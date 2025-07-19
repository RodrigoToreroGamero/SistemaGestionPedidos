/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodri
 */

@Entity
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    
    @Column(name = "talla", nullable = false)
    private String talla;
    
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;
    
    @Column(name = "precio", nullable = false)
    private Double precio;
    
    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;
    
    @OneToMany(mappedBy = "producto")
    private List<DetallePedido> detalles = new ArrayList<>();
    
    @OneToMany(mappedBy = "producto")
    private List<MovimientoInventario> movimientos = new ArrayList<>();

    public Producto() {
    }

    
    
    public Producto(Long id, String nombre, String descripcion, String talla, Integer stockActual, Double precio, Proveedor proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.talla = talla;
        this.stockActual = stockActual;
        this.precio = precio;
        this.proveedor = proveedor;
    }
     

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public List<MovimientoInventario> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoInventario> movimientos) {
        this.movimientos = movimientos;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", talla=" + talla + ", stockActual=" + stockActual + ", precio=" + precio + ", proveedor=" + proveedor + ", detalles=" + detalles + ", movimientos=" + movimientos + '}';
    }       

    
}
