/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.modelo.dao;

import com.utp.proyectocalzadosruiz.modelo.Pedido;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rodri
 */
@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByEstado(String estado);

    List<Pedido> findByCliente_Id(Integer ID_Cliente);

    List<Pedido> findByUsuario_Id(Integer ID_Vendedor);

    List<Pedido> findByFecha(LocalDateTime fecha);

    List<Pedido> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
