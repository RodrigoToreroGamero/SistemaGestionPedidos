/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.modelo.dao;

import com.utp.proyectocalzadosruiz.modelo.Proveedor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rodri
 */
@Repository
public interface ProveedorDAO extends JpaRepository<Proveedor, Integer> {

    Optional<Proveedor> findByNombre(String nombre);
}
