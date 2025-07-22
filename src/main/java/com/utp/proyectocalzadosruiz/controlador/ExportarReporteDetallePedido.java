/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.DetallePedido;
import com.utp.proyectocalzadosruiz.modelo.dao.DetallePedidoDAO;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/reporte/detalles")
public class ExportarReporteDetallePedido extends ExportarReporteController {

    @Autowired
    private DetallePedidoDAO detallePedidoDAO;

    @GetMapping("/filtrar")
    public ResponseEntity<List<DetallePedido>> obtenerDetallesFiltrados(
            @RequestParam(required = false) Integer idProducto,
            @RequestParam(required = false) Integer idPedido
    ) {
        if (idProducto != null) {
            return ResponseEntity.ok(detallePedidoDAO.findByProducto_Id(idProducto));
        } else if (idPedido != null) {
            return ResponseEntity.ok(detallePedidoDAO.findByPedido_Id(idPedido));
        } else {
            return ResponseEntity.ok(detallePedidoDAO.findAll());
        }
    }

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportarDetallesCSV(
            @RequestParam(required = false) Integer idProducto,
            @RequestParam(required = false) Integer idPedido
    ) {
        List<DetallePedido> detalles;

        if (idProducto != null) {
            detalles = detallePedidoDAO.findByProducto_Id(idProducto);
        } else if (idPedido != null) {
            detalles = detallePedidoDAO.findByPedido_Id(idPedido);
        } else {
            detalles = detallePedidoDAO.findAll();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);
        writer.println("IDDetalle,IDPedido,Producto,Cantidad,PrecioUnitario,Subtotal");

        for (DetallePedido d : detalles) {
            BigDecimal subtotal = d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad()));

            writer.printf("%d,%d,%s,%d,%.2f,%.2f%n",
                    d.getId(),
                    d.getPedido().getId(),
                    d.getProducto().getNombre(),
                    d.getCantidad(),
                    d.getPrecioUnitario().doubleValue(),
                    subtotal.doubleValue()
            );

        }

        writer.flush();
        return generarArchivoCSV(out, "reporte_detalles.csv");
    }

}
