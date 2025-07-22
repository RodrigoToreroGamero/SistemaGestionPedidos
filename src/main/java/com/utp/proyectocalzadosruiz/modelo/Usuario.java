/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodri
 */

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario")
    private Integer id;
    
    @Column(name = "NombreUsuario", unique = true)
    private String nombre;
    
    @Column(name = "Contraseña")
    private String contrasena;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Rol")
    private Enumeraciones.Rol rol;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<MovimientoInventario> movimientos = new ArrayList<>();

    public Usuario() {
    }

    
    
    public Usuario(Integer id, String nombre, String contrasena, Enumeraciones.Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Enumeraciones.Rol getRol() {
        return rol;
    }

    public void setRol(Enumeraciones.Rol rol) {
        this.rol = rol;
    }

    public List<MovimientoInventario> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoInventario> movimientos) {
        this.movimientos = movimientos;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", contrasena=" + contrasena + ", rol=" + rol + ", movimientos=" + movimientos + '}';
    }

    
    
}
