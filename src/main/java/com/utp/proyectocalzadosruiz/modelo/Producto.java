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
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodri
 */

@Entity
@Table(name = "producto")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Producto")
    private Integer id;
    
    @Column(name = "Nombre")
    private String nombre;
    
    @Column(name = "Descripcion")
    private String descripcion;
    
    @Column(name = "Talla")
    private String talla;
    
    @Column(name = "Stock_Actual")
    private Integer stockActual;
    
    @Column(name = "Precio")
    private BigDecimal precio;
    
    @ManyToOne
    @JoinColumn(name = "ID_Proveedor")
    private Proveedor proveedor;
    
    @OneToMany(mappedBy = "producto")
    private List<DetallePedido> detalles = new ArrayList<>();
    
    @OneToMany(mappedBy = "producto")
    private List<MovimientoInventario> movimientos = new ArrayList<>();

    public Producto() {
    }

    
    
    public Producto(Integer id, String nombre, String descripcion, String talla, Integer stockActual, BigDecimal precio, Proveedor proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.talla = talla;
        this.stockActual = stockActual;
        this.precio = precio;
        this.proveedor = proveedor;
    }
     

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
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
