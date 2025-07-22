## Guía Técnica – Flujo “Registrar Pedido”

### Objetivo
Permitir que un vendedor inicie el registro de un pedido desde una vista web, cargando información dinámica mediante Spring Boot, Thymeleaf y una base de datos MySQL.

---

## Vista principal – Home

**Archivo:** `src/main/resources/templates/home.html`

```html
<a th:href="@{/usuario/{id}/formulario(id=${vendedorId})}">Registrar Pedido</a>
<a href="/pedidos">Ver lista de pedidos</a>
```

Este enlace construye dinámicamente la URL:
```
/usuario/1/formulario
```
Usando el ID de vendedor que se carga en el modelo desde el controlador.

---

## Controlador principal – HomeController

**Archivo:** `com.utp.proyectocalzadosruiz.controlador.HomeController.java`

```java
@Controller
public class HomeController {

    @GetMapping("/")
    public String mostrarHome(Model model) {
        model.addAttribute("vendedorId", 1L); // ID de vendedor fijo (temporal)
        return "home"; // Renderiza home.html
    }
}
```

Este método prepara la vista principal (`home.html`) y envía el `vendedorId` como atributo para usarlo en el enlace de “Registrar Pedido”.

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        <title>Bienvenido al Sistema de Calzados Ruiz</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Bienvenido al sistema</h1>
        <a th:href="@{/usuario/{id}/formulario(id=${vendedorId})}">Registrar Pedido</a>
        <br/>
        <a href="/pedidos">Ver lista de pedidos</a>
    </body>
</html>

---

## Redirección al formulario de pedido



Cuando el usuario hace clic en el enlace, se accede a:

```
/usuario/1/formulario
```

Esta URL activa el siguiente método en el controlador de pedidos.

---

## Controlador de pedido – PedidoController

**Archivo:** `com.utp.proyectocalzadosruiz.controlador.PedidoController.java`

```java
@GetMapping("/usuario/{id}/formulario")
public String mostrarFormulario(@PathVariable Long id, Model model) {
    Optional<Usuario> vendedorExistente = usuarioDAO.findById(id);

    if (vendedorExistente.isEmpty()) {
        model.addAttribute("mensaje", "Usuario no encontrado");
        return "error";
    }

    Usuario vendedor = vendedorExistente.get();
    if (!"vendedor".equalsIgnoreCase(vendedor.getRol())) {
        model.addAttribute("mensaje", "Rol inválido");
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

    return "registrarPedido"; // Renderiza registrarPedido.html
}
```

Este método:
- Obtiene y valida al vendedor por su `id`.
- Crea un objeto `Pedido` con cliente y un detalle inicial.
- Carga la lista de productos desde la base de datos.
- Envía todos los datos a la vista `registrarPedido.html`.

---

## Formulario de registro – Vista de pedido

**Archivo:** `src/main/resources/templates/registrarPedido.html`

Fragmento de ejemplo mostrado:

```html
<div th:if="${pedido != null}">
    <h3>Detalle del Pedido</h3>
    <p><strong>Cliente:</strong>
        <span th:text="${pedido.cliente.nombres + ' ' + pedido.cliente.apellidos}"></span>
    </p>
    <p><strong>Fecha:</strong>
        <span th:text="${#temporals.format(pedido.fecha, 'dd/MM/yyyy HH:mm')}"></span>
    </p>
    <p><strong>Estado:</strong>
        <span th:text="${pedido.estado}"></span>
    </p>
    <h4>Productos:</h4>
    <ul>
        <li th:each="detalle : ${pedido.detalles}">
            <span th:text="${detalle.producto.nombre}"></span> -
            <span th:text="${detalle.cantidad}"></span> unidades -
            <span th:text="${detalle.precioUnitario}"></span> soles c/u
        </li>
    </ul>
</div>
```

Aquí se muestran los datos del pedido y sus detalles si el objeto `pedido` ha sido correctamente poblado en el controlador.

---

## Configuración MySQL

**Archivo:** `src/main/resources/application.properties`

```properties
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3306/calzados_db
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

Asegúrate de que la base de datos exista y tiene las tablas necesarias (`cliente`, `usuario`, `producto`, `pedido`,`detalle_pedido`).  
El modo `validate` verifica que las entidades Java coincidan con las estructuras en MySQL.

---

## ¿Se puede probar este flujo?

Tener:
- MySQL instalado y configurado.
- En nombre de la base de datos coincida con el de spring.datasource.url en src/main/resources/<default package>/application.properties
- Este proyecto ejecutándose en su máquina.

pueden ingresar a `http://localhost:8081`, hacer clic en "Registrar Pedido", y probar el formulario de pedidos.

