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
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 *
 * @author rodri
 */

@Entity
@Table(name = "detallespedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DetallePedido")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_Pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "ID_Producto")
    private Producto producto;

    @Column(name = "Cantidad")
    private Integer cantidad;

    @Column(name = "PrecioUnitario")
    private BigDecimal precioUnitario;

    public DetallePedido() {
    }
       
    public DetallePedido(Integer id, Pedido pedido, Producto producto, Integer cantidad, BigDecimal precioUnitario) {
        this.id = id;
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        return "DetallePedido{" + "id=" + id + ", pedido=" + pedido + ", producto=" + producto + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + '}';
    }

}
