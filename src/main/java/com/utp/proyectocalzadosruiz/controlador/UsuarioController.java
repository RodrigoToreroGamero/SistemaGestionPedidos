/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.Usuario;
import com.utp.proyectocalzadosruiz.modelo.dao.UsuarioDAO;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rodri
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodo() {
        return ResponseEntity.ok(usuarioDAO.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        Optional<Usuario> resultado = usuarioDAO.findById(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Usuario nuevo) {
        usuarioDAO.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @RequestBody Usuario actualizado) {
        Optional<Usuario> existente = usuarioDAO.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }

        Usuario entidad = existente.get();

        entidad.setNombre(actualizado.getNombre());
        entidad.setContrasena(actualizado.getContrasena()); // encriptar si es necesario
        entidad.setRol(actualizado.getRol());

        usuarioDAO.save(entidad);
        return ResponseEntity.ok("Actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (!usuarioDAO.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }
        usuarioDAO.deleteById(id);
        return ResponseEntity.ok("Eliminado correctamente");
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Usuario> buscarPorNombre(@PathVariable String nombre) {
        Optional<Usuario> resultado = usuarioDAO.findByNombre(nombre);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> buscarPorRol(@PathVariable String rol) {
        List<Usuario> usuarios = usuarioDAO.findByRol(rol);

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usuarios);
        } else {
            return ResponseEntity.ok(usuarios);
        }
    }

}
