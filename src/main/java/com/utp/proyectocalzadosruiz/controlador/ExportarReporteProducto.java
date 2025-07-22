/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import com.utp.proyectocalzadosruiz.modelo.Producto;
import com.utp.proyectocalzadosruiz.modelo.dao.ProductoDAO;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
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
@RequestMapping("/reporte/productos")
public class ExportarReporteProducto extends ExportarReporteController {

    @Autowired
    private ProductoDAO productoDAO;

    @GetMapping("/filtrar")
    public ResponseEntity<List<Producto>> obtenerProductosFiltrados(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String talla,
            @RequestParam(required = false) String proveedor,
            @RequestParam(required = false) Double minPrecio,
            @RequestParam(required = false) Double maxPrecio,
            @RequestParam(required = false) Integer stockMaximo
    ) {
        if (nombre != null) {
            return ResponseEntity.ok(productoDAO.findByNombre(nombre));
        } else if (talla != null) {
            return ResponseEntity.ok(productoDAO.findByTalla(talla));
        } else if (proveedor != null) {
            return ResponseEntity.ok(productoDAO.findByProveedorNombre(proveedor));
        } else if (minPrecio != null && maxPrecio != null) {
            return ResponseEntity.ok(productoDAO.findByPrecioBetween(minPrecio, maxPrecio));
        } else if (stockMaximo != null) {
            return ResponseEntity.ok(productoDAO.findByStockActualLessThanEqual(stockMaximo));
        } else {
            return ResponseEntity.ok(productoDAO.findAll());
        }
    }

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportarProductosCSV(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String talla,
            @RequestParam(required = false) String proveedor,
            @RequestParam(required = false) Double minPrecio,
            @RequestParam(required = false) Double maxPrecio,
            @RequestParam(required = false) Integer stockMaximo
    ) {
        List<Producto> productos = obtenerProductosFiltrados(nombre, talla, proveedor, minPrecio, maxPrecio, stockMaximo).getBody();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);
        writer.println("ID,Nombre,Descripción,Talla,StockActual,Precio,Proveedor");

        for (Producto p : productos) {
            writer.printf("%d,%s,%s,%s,%d,%.2f,%s%n",
                    p.getId(),
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getTalla(),
                    p.getStockActual(),
                    p.getPrecio(),
                    p.getProveedor().getNombre()
            );
        }

        writer.flush();
        return generarArchivoCSV(out, "reporte_productos.csv");
    }
   
}
