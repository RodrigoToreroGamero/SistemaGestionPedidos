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
import java.time.LocalDateTime;

/**
 *
 * @author rodri
 */

@Entity
public class MovimientoInventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @Column(name = "tipo_movimiento", nullable = false)
    private String tipoMovimiento;
    
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
    
    @Column(name = "observaciones", nullable = false)
    private String observaciones;

    public MovimientoInventario() {
    }   
    
    public MovimientoInventario(Long id, Producto producto, Usuario usuario, String tipoMovimiento, Integer cantidad, LocalDateTime fecha, String observaciones) {
        this.id = id;
        this.producto = producto;
        this.usuario = usuario;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }   
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "MovimientoInventario{" + "id=" + id + ", producto=" + producto + ", usuario=" + usuario + ", tipoMovimiento=" + tipoMovimiento + ", cantidad=" + cantidad + ", fecha=" + fecha + ", observaciones=" + observaciones + '}';
    }

   
    
    
}
