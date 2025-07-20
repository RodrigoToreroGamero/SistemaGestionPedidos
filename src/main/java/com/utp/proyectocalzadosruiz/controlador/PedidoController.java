/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.Cliente;
import com.utp.proyectocalzadosruiz.modelo.DetallePedido;
import com.utp.proyectocalzadosruiz.modelo.Pedido;
import com.utp.proyectocalzadosruiz.modelo.Producto;
import com.utp.proyectocalzadosruiz.modelo.Usuario;
import com.utp.proyectocalzadosruiz.modelo.dao.PedidoDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.ProductoDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.UsuarioDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author rodri
 */
@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoDAO pedidoDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private ProductoDAO productoDAO;

    @GetMapping
    public String listarTodo(Model modelo) {
        modelo.addAttribute("pedidos", pedidoDAO.findAll());
        return "exportarPedidos";
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        Optional<Pedido> resultado = pedidoDAO.findById(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
     */
    @GetMapping("/{id}")
    public String verPedidoPorId(@PathVariable Long id, Model model) {
        Optional<Pedido> resultado = pedidoDAO.findById(id);

        if (resultado.isEmpty()) {
            model.addAttribute("mensaje", "Pedido no encontrado");
            return "error";
        }

        model.addAttribute("pedido", resultado.get());
        return "exportarPedidos";
    }

    @PostMapping("/usuario/{id}")
    public String crear(@PathVariable Long id, @ModelAttribute Pedido nuevo, Model modelo) {
        Optional<Usuario> usuarioExistente = this.usuarioDAO.findById(id);

        if (usuarioExistente.isEmpty()) {
            modelo.addAttribute("mensaje", "Usuario no encontrado");
            return "error";
        }

        Usuario usuario = usuarioExistente.get();

        if (!"vendedor".equalsIgnoreCase(usuario.getRol())) {
            modelo.addAttribute("mensaje", "El usuario no tiene rol de vendedor");
            return "error";
        }

        nuevo.setVendedor(usuario); // suponiendo que Pedido.setVendedor acepta un Usuario
        nuevo.setFecha(LocalDateTime.now());
        nuevo.setEstado("pendiente");

        pedidoDAO.save(nuevo);

        modelo.addAttribute("mensaje", "Pedido creado exitosamente por el vendedor");

        return "exito";
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
    public String buscarPorEstado(@RequestParam("valor") String estado, Model modelo) {
        List<Pedido> pedidos = pedidoDAO.findByEstado(estado);

        if (pedidos.isEmpty()) {
            modelo.addAttribute("mensaje", "No se encontraron pedidos con el estado '" + estado + "'");
            return "exportarPedidos";
        } else {
            modelo.addAttribute("pedidos", pedidos);
            return "exportarPedidos";
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

    @GetMapping("/usuario/{id}/formulario")
    public String mostrarFormulario(@PathVariable Long id, Model model) {
        Usuario vendedor = null;
        try {
            Optional<Usuario> vendedorExistente = usuarioDAO.findById(id);

            if (vendedorExistente.isEmpty()) {
                model.addAttribute("mensaje", "Usuario no encontrado");
                return "error";
            }

            vendedor = vendedorExistente.get();

            if (!"vendedor".equalsIgnoreCase(vendedor.getRol())) {
                model.addAttribute("mensaje", "Rol inválido");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("mensaje", "No se pudo conectar con la base de datos (usuarios)");
            return "error";
        }

        Pedido pedido = new Pedido();
        pedido.setVendedor(vendedor);
        pedido.setCliente(new Cliente());

        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(new Producto());
        pedido.getDetalles().add(detalle);

        List<Producto> productos;

        try {
            productos = productoDAO.findAll();
        } catch (Exception e) {
            productos = new ArrayList<>();
            model.addAttribute("mensajeProductos", "No se pudo cargar la lista de productos");
        }

        model.addAttribute("pedido", pedido);
        model.addAttribute("productos", productos);
        model.addAttribute("vendedorId", vendedor.getId());
        return "registrarPedido";
    }

    @GetMapping("/pedidos")
    public String verPedidos(Model model) {
        model.addAttribute("pedidos", pedidoDAO.findAll());
        return "exportarPedidos";
    }

}
