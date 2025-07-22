/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 *
 * @author rodri
 */

@Entity
@Table(name = "movimientoinventario")
public class MovimientoInventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Movimiento")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "ID_Producto")
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "ID_Usuario")
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "TipoMovimiento")
    private Enumeraciones.TipoMovimiento tipoMovimiento;
    
    @Column(name = "Cantidad")
    private Integer cantidad;
    
    @Column(name = "Fecha")
    private LocalDateTime fecha;
    
    @Column(name = "Observaciones")
    private String observaciones;

    public MovimientoInventario() {
    }   
    
    public MovimientoInventario(Integer id, Producto producto, Usuario usuario, Enumeraciones.TipoMovimiento tipoMovimiento, Integer cantidad, LocalDateTime fecha, String observaciones) {
        this.id = id;
        this.producto = producto;
        this.usuario = usuario;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }   
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

    public Enumeraciones.TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(Enumeraciones.TipoMovimiento tipoMovimiento) {
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
