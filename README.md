Una guía clara y práctica para conectar HTML y frontend a los endpoints REST del proyecto Spring Boot. Esta documentación está organizada por controlador, con ejemplos de uso desde HTML y observaciones importantes.

---

## Guía de Integración con Endpoints REST

### Convenciones generales

- Todos los endpoints anotados con `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` son **rutas públicas** que pueden ser invocadas desde HTML, JavaScript, Postman, etc.
- Si un endpoint usa `{}` entre la ruta (por ejemplo, `/clientes/{id}`), el valor debe colocarse dinámicamente en la URL.
- Los parámetros por `@RequestParam` deben enviarse en la parte de consulta de la URL: `/ruta?param=valor`.

---

## ClienteController `/clientes`

| Método 								| Descripción 							| Ejemplo de llamada desde HTML 																				|
|---------------------------------------|---------------------------------------|---------------------------------------------------------------------------------------------------------------|
| `@DeleteMapping("/{id}")` 			| Elimina cliente por ID 				| **Solo por JavaScript**: `fetch("/clientes/5", { method: "DELETE" })` 										|
| `@GetMapping("/dni/{dni}")` 			| Busca cliente por DNI 				| `<a href="/clientes/dni/12345678">Buscar por DNI</a>` 														|
| `@GetMapping("/correo/{correo}")` 	| Busca cliente por correo electrónico 	| `<a href="/clientes/correo/alguien%40dominio.com">Buscar por correo</a>`<br>⚠️ Usar `encodeURIComponent()` 	|
| `@GetMapping("/telefono/{telefono}")` | Busca cliente por teléfono 			| `<a href="/clientes/telefono/987654321">Buscar por teléfono</a>` 												|

---

## DetallePedidoController `/detallesPedidos`

| Método 									| Descripción 								| Ejemplo de uso 														|
|-------------------------------------------|-------------------------------------------|-----------------------------------------------------------------------|
| `@GetMapping("/producto/{idProducto}")` 	| Obtiene detalles de pedidos por producto 	| `<a href="/detallesPedidos/producto/2">Detalles del producto 2</a>` 	|

---

## MovimientoInventarioController `/movimientosInventario`

| Método 						| Descripción 											| Ejemplo de uso 																																			|
|-------------------------------|-------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| `@GetMapping("/tipo/{tipo}")` | Filtra movimientos por tipo (`INGRESO` o `SALIDA`) 	| `<a href="/movimientosInventario/tipo/INGRESO">Movimientos de ingreso</a>` 																				|
| `@GetMapping("/rango_fecha`)` | Filtra movimientos por fechas 						| `<a href="/movimientosInventario/rango_fecha?inicio=2025-01-01&fin=2025-07-01">Por rango</a>`<br>✅ También puede usarse en formularios `method="GET"` 	|

---

## ProductoController `/productos`

| Método 										| Descripción 								| Ejemplo de llamada desde HTML 										|
|-----------------------------------------------|-------------------------------------------|-----------------------------------------------------------------------|
| `@GetMapping("/proveedor/nombre/{nombre}")` 	| Busca productos por nombre del proveedor 	| `<a href="/productos/proveedor/nombre/Nike">Productos de Nike</a>` 	|
| `@GetMapping("/stock_maximo/{limite}")` 		| Busca productos con stock ≤ límite 		| `<a href="/productos/stock_maximo/10">Productos con bajo stock</a>` 	|

---

## ExportarReporte (Pedidos, Productos, Detalles)

Estos endpoints te permiten exportar archivos CSV filtrados o completos:

### `/reporte/pedidos`
- Exportar todos o por filtro:  
  ```
  /reporte/pedidos/exportar
  /reporte/pedidos/exportar?estado=ENTREGADO&idCliente=3
  ```

### `/reporte/detalles`
- Exportar todos o por producto/pedido:
  ```
  /reporte/detalles/exportar
  /reporte/detalles/exportar?idProducto=1
  ```

### `/reporte/productos`
- Exportar todos o por filtros múltiples:
  ```
  /reporte/productos/exportar
  /reporte/productos/exportar?talla=42&stockMaximo=10
  ```

En HTML puedes usarlos con:

```html
<a href="/reporte/productos/exportar">📥 Descargar productos</a>
```

Para generar los archivos CSV, el servidor utiliza el método generarArchivoCSV(...) definido en la clase base ExportarReporteController. Este método construye una respuesta ResponseEntity<byte[]> con la cabecera HTTP Content-Disposition: attachment, lo que indica al navegador que debe tratar el contenido como un archivo adjunto descargable.

Esta cabecera provoca que el navegador muestre un cuadro de diálogo para guardar el archivo (como “Guardar como...”) o lo descargue automáticamente en la carpeta de descargas predeterminada del usuario, según la configuración de su navegador (por ejemplo, Chrome, Firefox o Edge).

Los controladores que generan estos archivos son:

    ExportarReportePedido, para pedidos filtrados o generales

    ExportarReporteDetallePedido, para exportar líneas de venta o productos vendidos

    ExportarReporteProducto, para exportar inventario según filtros como talla, proveedor o stock

Todos ellos extienden de ExportarReporteController y utilizan el método común generarArchivoCSV(...) para construir la respuesta de descarga.

---

## Recomendaciones adicionales

- Usar JavaScript `fetch()` para los `@DeleteMapping` y los `@PostMapping` con `@RequestBody`.
- Verificar codificación de correos, textos con espacios o caracteres especiales usando `encodeURIComponent()`.
- Fechas deben estar en formato ISO (`YYYY-MM-DD` o `YYYY-MM-DDTHH:mm` si usan `LocalDateTime`).
- Evitar usar formularios estándar para peticiones tipo DELETE o PUT (solo soportan GET y POST). Usa JavaScript o librerías como Axios para eso.

---
