/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.Producto;
import com.utp.proyectocalzadosruiz.modelo.dao.ProductoDAO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rodri
 */
@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoDAO productoDAO;

    @GetMapping
    public ResponseEntity<List<Producto>> listarTodo() {
        return ResponseEntity.ok(productoDAO.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Integer id) {
        Optional<Producto> resultado = productoDAO.findById(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Producto nuevo) {
        productoDAO.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @RequestBody Producto actualizado) {
        Optional<Producto> existente = productoDAO.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }

        Producto entidad = existente.get();

        entidad.setNombre(actualizado.getNombre());
        entidad.setDescripcion(actualizado.getDescripcion());
        entidad.setTalla(actualizado.getTalla());
        entidad.setStockActual(actualizado.getStockActual());
        entidad.setPrecio(actualizado.getPrecio());
        entidad.setProveedor(actualizado.getProveedor());

        productoDAO.save(entidad);
        return ResponseEntity.ok("Actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (!productoDAO.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }
        productoDAO.deleteById(id);
        return ResponseEntity.ok("Eliminado correctamente");
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> buscarPorNombre(@PathVariable String nombre) {
        List<Producto> productos = productoDAO.findByNombre(nombre);

        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productos);
        } else {
            return ResponseEntity.ok(productos);
        }
    }

    @GetMapping("/talla/{talla}")
    public ResponseEntity<List<Producto>> buscarPorTalla(@PathVariable String talla) {
        List<Producto> productos = productoDAO.findByTalla(talla);

        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productos);
        } else {
            return ResponseEntity.ok(productos);
        }
    }

    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<Producto>> buscarPorProveedor(@PathVariable Integer idProveedor) {
        List<Producto> productos = productoDAO.findByProveedor_Id(idProveedor);

        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productos);
        } else {
            return ResponseEntity.ok(productos);
        }
    }

    @GetMapping("/proveedor/nombre/{nombre}")
    public ResponseEntity<List<Producto>> buscarPorProveedorNombre(@PathVariable String nombre) {
        List<Producto> productos = productoDAO.findByProveedorNombre(nombre);

        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productos);
        } else {
            return ResponseEntity.ok(productos);
        }
    }

    @GetMapping("/precio")
    public ResponseEntity<List<Producto>> buscarPorRangoPrecio(@RequestParam("min") Double min, @RequestParam("max") Double max) {

        List<Producto> productos = productoDAO.findByPrecioBetween(min, max);

        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productos);
        } else {
            return ResponseEntity.ok(productos);
        }
    }

    @GetMapping("/stock_maximo/{limite}")
    public ResponseEntity<List<Producto>> buscarPorStockMaximo(@PathVariable Integer limite) {
        List<Producto> productos = productoDAO.findByStockActualLessThanEqual(limite);

        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productos);
        } else {
            return ResponseEntity.ok(productos);
        }
    }

}
