/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.modelo.dao;

import com.utp.proyectocalzadosruiz.modelo.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rodri
 */
@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombre(String nombre);

    List<Usuario> findByRol(String rol);
}
