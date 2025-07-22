/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.modelo.dao;

import com.utp.proyectocalzadosruiz.modelo.MovimientoInventario;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rodri
 */
@Repository
public interface MovimientoInventarioDAO extends JpaRepository<MovimientoInventario, Integer> {

    List<MovimientoInventario> findByProducto_Id(Integer ID_Producto);

    List<MovimientoInventario> findByUsuario_Id(Integer ID_Usuario);

    List<MovimientoInventario> findByTipoMovimiento(String tipo);

    List<MovimientoInventario> findByFecha(LocalDateTime fecha);

    List<MovimientoInventario> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

}
