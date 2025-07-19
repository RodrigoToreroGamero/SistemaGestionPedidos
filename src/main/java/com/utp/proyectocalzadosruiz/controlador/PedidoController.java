/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.Pedido;
import com.utp.proyectocalzadosruiz.modelo.Usuario;
import com.utp.proyectocalzadosruiz.modelo.dao.PedidoDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.UsuarioDAO;
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
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoDAO pedidoDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodo() {
        return ResponseEntity.ok(pedidoDAO.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        Optional<Pedido> resultado = pedidoDAO.findById(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/usuario/{id}")
    public ResponseEntity<String> crear(@PathVariable Long id, @RequestBody Pedido nuevo) {
        Optional<Usuario> usuarioExistente = this.usuarioDAO.findById(id);

        if (usuarioExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioExistente.get();

        if (!"vendedor".equalsIgnoreCase(usuario.getRol())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario no tiene rol de vendedor");
        }

        nuevo.setVendedor(usuario); // suponiendo que Pedido.setVendedor acepta un Usuario
        nuevo.setFecha(LocalDateTime.now());
        nuevo.setEstado("pendiente");

        pedidoDAO.save(nuevo);

        return ResponseEntity.ok("Pedido creado exitosamente por el vendedor");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody Pedido pedidoActualizado) {
        Optional<Pedido> pedidoExistente = pedidoDAO.findById(id);
        if (pedidoExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
        }

        Pedido pedido = pedidoExistente.get();
        pedido.setEstado(pedidoActualizado.getEstado());

        pedidoDAO.save(pedido);
        return ResponseEntity.ok("Pedido actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (!pedidoDAO.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
        }

        pedidoDAO.deleteById(id);
        return ResponseEntity.ok("Pedido eliminado correctamente");
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> buscarPorEstado(@PathVariable String estado) {
        List<Pedido> pedidos = pedidoDAO.findByEstado(estado);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pedidos);
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Long idCliente) {
        List<Pedido> pedidos = pedidoDAO.findByClienteId(idCliente);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pedidos);
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @GetMapping("/vendedor/{idVendedor}")
    public ResponseEntity<List<Pedido>> buscarPorVendedor(@PathVariable Long idVendedor) {
        List<Pedido> pedidos = pedidoDAO.findByVendedorId(idVendedor);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pedidos);
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Pedido>> buscarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {

        List<Pedido> pedidos = pedidoDAO.findByFecha(fecha);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pedidos);
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @GetMapping("/rango")
    public ResponseEntity<List<Pedido>> buscarPorRangoFechas(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {

        List<Pedido> pedidos = pedidoDAO.findByFechaBetween(inicio, fin);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pedidos);
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }   

}
