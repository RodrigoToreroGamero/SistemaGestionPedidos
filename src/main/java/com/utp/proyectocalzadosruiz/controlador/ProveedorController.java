/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.Proveedor;
import com.utp.proyectocalzadosruiz.modelo.dao.ProveedorDAO;
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
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorDAO proveedorDAO;

    @GetMapping
    public ResponseEntity<List<Proveedor>> listarTodo() {
        return ResponseEntity.ok(proveedorDAO.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable Long id) {
        Optional<Proveedor> resultado = proveedorDAO.findById(id);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Proveedor nuevo) {
        proveedorDAO.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody Proveedor actualizado) {
        Optional<Proveedor> existente = proveedorDAO.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }

        Proveedor entidad = existente.get();

        entidad.setNombre(actualizado.getNombre());
        entidad.setContacto(actualizado.getContacto());
        entidad.setTelefono(actualizado.getTelefono());
        entidad.setDireccion(actualizado.getDireccion());

        proveedorDAO.save(entidad);
        return ResponseEntity.ok("Actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (!proveedorDAO.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }
        proveedorDAO.deleteById(id);
        return ResponseEntity.ok("Eliminado correctamente");
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Proveedor> obtenerPorNombre(@PathVariable String nombre) {
        Optional<Proveedor> resultado = proveedorDAO.findByNombre(nombre);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
