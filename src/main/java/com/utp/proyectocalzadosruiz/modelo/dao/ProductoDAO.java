/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.modelo.dao;

import com.utp.proyectocalzadosruiz.modelo.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rodri
 */
@Repository
public interface ProductoDAO extends JpaRepository<Producto, Integer> {

    List<Producto> findByNombre(String nombre);

    List<Producto> findByTalla(String talla);

    List<Producto> findByProveedor_Id(Integer ID_Proveedor);
    
    List<Producto> findByProveedorNombre(String nombre);

    List<Producto> findByPrecioBetween(Double min, Double max);

    List<Producto> findByStockActualLessThanEqual(Integer limite);


}
