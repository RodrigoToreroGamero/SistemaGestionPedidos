/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.Cliente;
import com.utp.proyectocalzadosruiz.modelo.dao.ClienteDAO;
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
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodo() {
        return ResponseEntity.ok(this.clienteDAO.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        Optional<Cliente> resultado = clienteDAO.findById(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Cliente nuevo) {
        this.clienteDAO.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody Cliente actualizado) {
        Optional<Cliente> existente = this.clienteDAO.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }

        Cliente entidad = existente.get();

        entidad.setNombres(actualizado.getNombres());
        entidad.setApellidos(actualizado.getApellidos());
        entidad.setDni(actualizado.getDni());
        entidad.setDireccion(actualizado.getDireccion());
        entidad.setTelefono(actualizado.getTelefono());
        entidad.setCorreo(actualizado.getCorreo());

        this.clienteDAO.save(entidad);
        return ResponseEntity.ok("Actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (!this.clienteDAO.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }
        this.clienteDAO.deleteById(id);
        return ResponseEntity.ok("Eliminado correctamente");
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Cliente> buscarPorDni(@PathVariable String dni) {
        Optional<Cliente> resultado = clienteDAO.findByDni(dni);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Cliente> buscarPorCorreo(@PathVariable String correo) {
        Optional<Cliente> resultado = clienteDAO.findByCorreo(correo);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<Cliente> buscarPorTelefono(@PathVariable String telefono) {
        Optional<Cliente> resultado = clienteDAO.findByTelefono(telefono);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
