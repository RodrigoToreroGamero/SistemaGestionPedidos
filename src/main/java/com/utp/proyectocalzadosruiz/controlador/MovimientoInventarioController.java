/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.MovimientoInventario;
import com.utp.proyectocalzadosruiz.modelo.dao.MovimientoInventarioDAO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/movimientosInventario")
public class MovimientoInventarioController {

    @Autowired
    private MovimientoInventarioDAO movimientoInventarioDAO;

    @GetMapping
    public ResponseEntity<List<MovimientoInventario>> listarTodo() {
        return ResponseEntity.ok(movimientoInventarioDAO.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventario> obtenerPorId(@PathVariable Integer id) {
        Optional<MovimientoInventario> resultado = movimientoInventarioDAO.findById(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody MovimientoInventario nuevo) {
        movimientoInventarioDAO.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @RequestBody MovimientoInventario actualizado) {
        Optional<MovimientoInventario> existente = movimientoInventarioDAO.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }

        MovimientoInventario entidad = existente.get();

        entidad.setProducto(actualizado.getProducto());
        entidad.setUsuario(actualizado.getUsuario());
        entidad.setTipoMovimiento(actualizado.getTipoMovimiento());
        entidad.setCantidad(actualizado.getCantidad());
        entidad.setFecha(actualizado.getFecha());
        entidad.setObservaciones(actualizado.getObservaciones());

        movimientoInventarioDAO.save(entidad);
        return ResponseEntity.ok("Actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (!movimientoInventarioDAO.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }
        movimientoInventarioDAO.deleteById(id);
        return ResponseEntity.ok("Eliminado correctamente");
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<MovimientoInventario>> obtenerPorProducto(@PathVariable Integer idProducto) {
        List<MovimientoInventario> movimientos = movimientoInventarioDAO.findByProducto_Id(idProducto);

        if (movimientos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movimientos);
        } else {
            return ResponseEntity.ok(movimientos);
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MovimientoInventario>> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        List<MovimientoInventario> movimientos = movimientoInventarioDAO.findByUsuario_Id(idUsuario);

        if (movimientos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movimientos);
        } else {
            return ResponseEntity.ok(movimientos);
        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MovimientoInventario>> obtenerPorTipoMovimiento(@PathVariable String tipo) {
        List<MovimientoInventario> movimientos = movimientoInventarioDAO.findByTipoMovimiento(tipo);

        if (movimientos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movimientos);
        } else {
            return ResponseEntity.ok(movimientos);
        }
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<MovimientoInventario>> obtenerPorFecha(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {

        List<MovimientoInventario> movimientos = movimientoInventarioDAO.findByFecha(fecha);

        if (movimientos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movimientos);
        } else {
            return ResponseEntity.ok(movimientos);
        }
    }

    @GetMapping("/rango_fecha")
    public ResponseEntity<List<MovimientoInventario>> obtenerPorRangoFechas(@RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio, @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {

        List<MovimientoInventario> movimientos = movimientoInventarioDAO.findByFechaBetween(inicio, fin);

        if (movimientos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movimientos);
        } else {
            return ResponseEntity.ok(movimientos);
        }
    }

}
