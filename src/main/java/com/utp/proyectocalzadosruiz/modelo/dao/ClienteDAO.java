/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.modelo.dao;

import com.utp.proyectocalzadosruiz.modelo.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rodri
 */
@Repository
public interface ClienteDAO extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByDni(String dni);

    Optional<Cliente> findByEmail(String correo);

    List<Cliente> findByNombresAndApellidos(String nombres, String apellidos);

    List<Cliente> findByDireccion(String direccion);

    Optional<Cliente> findByTelefono(String telefono);

}
