/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz;

import com.utp.proyectocalzadosruiz.modelo.DetallePedido;
import com.utp.proyectocalzadosruiz.modelo.Pedido;
import com.utp.proyectocalzadosruiz.modelo.Producto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author rodri
 */
@Controller
public class PedidoDemoController {

    private List<Pedido> pedidosEnMemoria = new ArrayList<>();

    @GetMapping("/registrar-demo")
    public String mostrarFormulario(Model model) {
        Pedido pedido = new Pedido();
        pedido.getDetalles().add(new DetallePedido()); // uno vacío para iniciar

        List<Producto> productos = List.of(
            new Producto(1L, "Producto A", "Desc", "Talla", 5, 100.0, null),
            new Producto(2L, "Producto B", "Desc", "Talla", 3, 200.0, null)
        );

        model.addAttribute("pedido", pedido);
        model.addAttribute("productos", productos);
        model.addAttribute("vendedorId", 1L);
        return "registrarPedido";
    }

    @PostMapping("/registrar-demo")
    public String procesarFormulario(@ModelAttribute Pedido pedido) {
        pedidosEnMemoria.add(pedido);
        return "redirect:/exportar-demo";
    }

    @GetMapping("/exportar-demo")
    public String exportarPedidos(Model model) {
        model.addAttribute("pedidos", pedidosEnMemoria);
        return "exportarPedidos";
    }
}

