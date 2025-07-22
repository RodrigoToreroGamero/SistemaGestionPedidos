/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.modelo.dao;

import com.utp.proyectocalzadosruiz.modelo.DetallePedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rodri
 */
@Repository
public interface DetallePedidoDAO extends JpaRepository<DetallePedido, Integer> {

    List<DetallePedido> findByProducto_Id(Integer ID_Producto);

    List<DetallePedido> findByPedido_Id(Integer ID_Pedido);
}
