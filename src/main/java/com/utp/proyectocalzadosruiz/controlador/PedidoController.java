/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.Cliente;
import com.utp.proyectocalzadosruiz.modelo.DetallePedido;
import com.utp.proyectocalzadosruiz.modelo.Enumeraciones;
import com.utp.proyectocalzadosruiz.modelo.Pedido;
import com.utp.proyectocalzadosruiz.modelo.Producto;
import com.utp.proyectocalzadosruiz.modelo.Usuario;
import com.utp.proyectocalzadosruiz.modelo.dao.ClienteDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.PedidoDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.ProductoDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.ProveedorDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.UsuarioDAO;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Autowired
    private ProveedorDAO proveedorDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    static final Integer MAX_PRODUCTOS = 5;

    @GetMapping
    public String listarTodo(Model modelo) {
        modelo.addAttribute("pedidos", pedidoDAO.findAll());
        return "exportarPedidos";
    }

    @GetMapping("/{id}")
    public String verPedidoPorId(@PathVariable Integer id, Model model) {
        Optional<Pedido> resultado = pedidoDAO.findById(id);

        if (resultado.isEmpty()) {
            model.addAttribute("mensaje", "Pedido no encontrado");
            return "error";
        }

        model.addAttribute("pedido", resultado.get());
        return "exportarPedidos";
    }

    @PostMapping("/usuario/{id}")
    public String crear(@PathVariable Integer id, @ModelAttribute Pedido nuevo, Model modelo, HttpServletRequest request) {
        Optional<Usuario> usuarioExistente = this.usuarioDAO.findById(id);

        if (usuarioExistente.isEmpty()) {
            modelo.addAttribute("mensaje", "Usuario no encontrado");
            return "error";
        }

        Usuario usuario = usuarioExistente.get();

        if (usuario.getRol() != Enumeraciones.Rol.vendedor) {
            modelo.addAttribute("mensaje", "El usuario no tiene rol de vendedor");
            return "error";
        }

        Producto personalizado = new Producto();
        personalizado.setNombre(request.getParameter("nombrePersonalizado"));
        personalizado.setDescripcion(request.getParameter("descripcionPersonalizada"));
        personalizado.setTalla(request.getParameter("tallaPersonalizada"));

        try {
            String precioParam = request.getParameter("precioPersonalizado");
            if (precioParam != null && !precioParam.isBlank()) {
                personalizado.setPrecio(new BigDecimal(precioParam));
            } else {
                modelo.addAttribute("mensaje", "El precio personalizado está vacío");
                return "error";
            }
        } catch (NumberFormatException e) {
            modelo.addAttribute("mensaje", "Formato inválido en el precio");
            return "error";
        }

        try {
            personalizado.setProveedor(proveedorDAO.findById(1).get());
        } catch (NoSuchElementException e) {
            modelo.addAttribute("mensaje", "Proveedor no encontrado");
            return "error";
        }

        productoDAO.save(personalizado);

        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(personalizado);
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(personalizado.getPrecio());

        detalle.setPedido(nuevo);
        nuevo.getDetalles().add(detalle);

        for (int i = 0; i < MAX_PRODUCTOS; i++) {
            String productoIdStr = request.getParameter("productoId" + i);
            // Si el vendedor no añadió este producto, lo saltamos
            if (productoIdStr == null || productoIdStr.isBlank()) {
                continue;
            }

            try {
                Integer productoId = Integer.parseInt(productoIdStr);
                Integer cantidad = Integer.parseInt(request.getParameter("cantidad" + i));
                BigDecimal precio = new BigDecimal(request.getParameter("precio" + i));

                Producto producto = productoDAO.findById(productoId).orElseThrow();

                DetallePedido detalleExtra = new DetallePedido();
                detalleExtra.setProducto(producto);
                detalleExtra.setCantidad(cantidad);
                detalleExtra.setPrecioUnitario(precio);
                detalleExtra.setPedido(nuevo);

                nuevo.getDetalles().add(detalleExtra);
            } catch (Exception e) {
                modelo.addAttribute("mensaje", "Error procesando producto adicional #" + (i + 1));
                return "error";
            }
        }

        nuevo.setUsuario(usuario); // suponiendo que Pedido.setVendedor acepta un Usuario
        nuevo.setFecha(LocalDateTime.now());
        nuevo.setEstado(Enumeraciones.Estado.Pendiente);

        try {
            clienteDAO.save(nuevo.getCliente());
        } catch (DataIntegrityViolationException e) {
            modelo.addAttribute("mensaje", "Ya existe un cliente con ese DNI.");
            return "error";
        }

        pedidoDAO.save(nuevo);

        modelo.addAttribute("mensaje", "Pedido creado exitosamente por el vendedor");

        return "exito";
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @RequestBody Pedido pedidoActualizado) {
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
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
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
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Integer idCliente) {
        List<Pedido> pedidos = pedidoDAO.findByCliente_Id(idCliente);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pedidos);
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @GetMapping("/vendedor/{idVendedor}")
    public ResponseEntity<List<Pedido>> buscarPorVendedor(@PathVariable Integer idVendedor) {
        List<Pedido> pedidos = pedidoDAO.findByUsuario_Id(idVendedor);

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
    public String mostrarFormulario(@PathVariable Integer id, Model model) {
        Usuario vendedor = null;
        try {
            Optional<Usuario> vendedorExistente = usuarioDAO.findById(id);

            if (vendedorExistente.isEmpty()) {
                model.addAttribute("mensaje", "Usuario no encontrado");
                return "error";
            }

            vendedor = vendedorExistente.get();

            if (vendedor.getRol() != Enumeraciones.Rol.vendedor) {
                model.addAttribute("mensaje", "Rol inválido");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("mensaje", "No se pudo conectar con la base de datos (usuarios)");
            return "error";
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(vendedor);
        pedido.setCliente(new Cliente());

        for (int i = 0; i < MAX_PRODUCTOS; i++) {
            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(new Producto()); // para evitar problemas con binding en Thymeleaf
            pedido.getDetalles().add(detalle);
        }

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
        model.addAttribute("maxIndex", MAX_PRODUCTOS - 1);
        model.addAttribute("maxProductos", MAX_PRODUCTOS);

        return "registrarPedido";
    }

}
