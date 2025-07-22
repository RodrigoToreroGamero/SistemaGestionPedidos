/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.DetallePedido;
import com.utp.proyectocalzadosruiz.modelo.dao.DetallePedidoDAO;
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
@RequestMapping("/detallesPedidos")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoDAO detallePedidoDAO;

    @GetMapping
    public ResponseEntity<List<DetallePedido>> listarTodo() {
        List<DetallePedido> detalles = detallePedidoDAO.findAll();
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerPorId(@PathVariable Integer id) {
        Optional<DetallePedido> resultado = detallePedidoDAO.findById(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody DetallePedido detalle) {
        detallePedidoDAO.save(detalle);
        return ResponseEntity.status(HttpStatus.CREATED).body("Detalle creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @RequestBody DetallePedido detalleActualizado) {
        Optional<DetallePedido> detalleExistente = detallePedidoDAO.findById(id);
        if (detalleExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Detalle no encontrado");
        }

        DetallePedido detalle = detalleExistente.get();
        detalle.setCantidad(detalleActualizado.getCantidad());
        detalle.setPrecioUnitario(detalleActualizado.getPrecioUnitario());

        detallePedidoDAO.save(detalle);
        return ResponseEntity.ok("Detalle actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (!detallePedidoDAO.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Detalle no encontrado");
        }
        detallePedidoDAO.deleteById(id);
        return ResponseEntity.ok("Detalle eliminado correctamente");
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<DetallePedido>> buscarPorProducto(@PathVariable Integer idProducto) {
        List<DetallePedido> detalles = detallePedidoDAO.findByProducto_Id(idProducto);

        if (detalles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detalles);
        } else {
            return ResponseEntity.ok(detalles);
        }
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<DetallePedido>> buscarPorPedido(@PathVariable Integer idPedido) {
        List<DetallePedido> detalles = detallePedidoDAO.findByPedido_Id(idPedido);

        if (detalles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detalles);
        } else {
            return ResponseEntity.ok(detalles);
        }
    }

}
