/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.modelo;

/**
 *
 * @author rodri
 */
public class Enumeraciones {

    public enum Rol {
        admin,
        vendedor,
        almacen
    }

    public enum Estado {
        Pendiente,
        Entregado,
        Cancelado
    }

    public enum TipoMovimiento {
        Entrada,
        Salida
    }

}
