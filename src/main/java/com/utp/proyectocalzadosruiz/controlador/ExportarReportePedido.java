/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.Pedido;
import com.utp.proyectocalzadosruiz.modelo.dao.PedidoDAO;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author rodri
 */
@Controller
@RequestMapping("/reporte/pedidos")
public class ExportarReportePedido extends ExportarReporteController {

    @Autowired
    private PedidoDAO pedidoDAO;

    @GetMapping("/filtrar")
    public ResponseEntity<List<Pedido>> obtenerPedidosFiltrados(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Integer idCliente,
            @RequestParam(required = false) Integer idVendedor,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin
    ) {
        return ResponseEntity.ok(aplicarFiltrosPedidos(estado, idCliente, idVendedor, inicio, fin));
    }

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportarPedidosCSV(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Integer idCliente,
            @RequestParam(required = false) Integer idVendedor,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin
    ) {
        List<Pedido> pedidos = aplicarFiltrosPedidos(estado, idCliente, idVendedor, inicio, fin);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);
        writer.println("IDPedido,Fecha,Cliente,Vendedor,Estado,MontoTotal");

        for (Pedido p : pedidos) {
            BigDecimal montoTotal = p.getDetalles().stream()
                    .map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            writer.printf("%d,%s,%s,%s,%s,%.2f%n",
                    p.getId(),
                    p.getFecha(), 
                    p.getCliente().getNombres(),
                    p.getUsuario().getNombre(),
                    p.getEstado().toString(), 
                    montoTotal.doubleValue() 
            );
        }

        writer.flush();
        return generarArchivoCSV(out, "reporte_pedidos.csv");
    }

    private List<Pedido> aplicarFiltrosPedidos(String estado, Integer idCliente, Integer idVendedor,
            LocalDateTime inicio, LocalDateTime fin) {
        if (inicio != null && fin != null) {
            return pedidoDAO.findByFechaBetween(inicio, fin);
        } else if (estado != null) {
            return pedidoDAO.findByEstado(estado);
        } else if (idCliente != null) {
            return pedidoDAO.findByCliente_Id(idCliente);
        } else if (idVendedor != null) {
            return pedidoDAO.findByUsuario_Id(idVendedor);
        } else {
            return pedidoDAO.findAll();
        }
    }

}
