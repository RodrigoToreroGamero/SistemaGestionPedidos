package com.utp.proyectocalzadosruiz;

import com.utp.proyectocalzadosruiz.modelo.Cliente;
import com.utp.proyectocalzadosruiz.modelo.DetallePedido;
import com.utp.proyectocalzadosruiz.modelo.Pedido;
import com.utp.proyectocalzadosruiz.modelo.Producto;
import com.utp.proyectocalzadosruiz.modelo.Proveedor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import com.utp.proyectocalzadosruiz.modelo.Usuario;
import com.utp.proyectocalzadosruiz.modelo.dao.ClienteDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.DetallePedidoDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.PedidoDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.ProductoDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.ProveedorDAO;
import com.utp.proyectocalzadosruiz.modelo.dao.UsuarioDAO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.ApplicationRunner;

//@Configuration
public class DemoDataConfig {

    /*
    @Bean
    public ApplicationRunner cargarFlujoCompleto(
            UsuarioDAO usuarioDAO,
            ProveedorDAO proveedorDAO,
            ProductoDAO productoDAO,
            ClienteDAO clienteDAO,
            PedidoDAO pedidoDAO,
            DetallePedidoDAO detallePedidoDAO
    ) {
        return args -> {
            try {
                // VENDEDOR
                Usuario vendedor = usuarioDAO.findAll().stream()
                        .filter(u -> "vendedor".equalsIgnoreCase(u.getRol()))
                        .findFirst()
                        .orElse(null);
                if (vendedor == null) {
                    vendedor = new Usuario();
                    vendedor.setNombre("Vendedor Demo");
                    vendedor.setContrasena("demo123");
                    vendedor.setRol("vendedor");
                    usuarioDAO.save(vendedor);
                }

                // PROVEEDOR
                Proveedor proveedor = proveedorDAO.findAll().stream().findFirst().orElse(null);
                if (proveedor == null) {
                    proveedor = new Proveedor();
                    proveedor.setNombre("Diseños Elegantes S.A.C.");
                    proveedor.setContacto("contacto@disenelegante.com");
                    proveedor.setTelefono("987-654-321");
                    proveedor.setDireccion("Av. Moda Fina 101, San Isidro");
                    proveedorDAO.save(proveedor);
                }

                // PRODUCTOS
                if (productoDAO.count() == 0) {
                    productoDAO.save(new Producto(null, "Zapatilla Urbana", "Cuero italiano", "40", 10, 349.90, proveedor));
                    productoDAO.save(new Producto(null, "Pulsera Diamante", "Oro blanco", "Única", 3, 1299.99, proveedor));
                }
                List<Producto> productos = productoDAO.findAll();

                // CLIENTE
                Cliente cliente = new Cliente();
                cliente.setNombres("Isabella");
                cliente.setApellidos("Montenegro");
                cliente.setDni("48752631");
                cliente.setDireccion("Av. Encanto 202, Miraflores");
                cliente.setTelefono("999-111-222");
                cliente.setCorreo("isabella.montenegro@google.com");
                clienteDAO.save(cliente);

                // PEDIDO
                Pedido pedido = new Pedido();
                pedido.setVendedor(vendedor);
                pedido.setCliente(cliente);
                pedido.setFecha(LocalDateTime.now());
                pedido.setEstado("pendiente");
                pedidoDAO.save(pedido);

                // DETALLES
                for (Producto producto : productos) {
                    DetallePedido detalle = new DetallePedido();
                    detalle.setPedido(pedido);
                    detalle.setProducto(producto);
                    detalle.setCantidad(2);
                    detalle.setPrecioUnitario(producto.getPrecio());
                    detallePedidoDAO.save(detalle);
                }

                System.out.println("Flujo demo completo insertado.");
            } catch (Exception e) {
                System.err.println("Error en cargarFlujoCompleto: " + e.getMessage());
                e.printStackTrace();
            }
        };        
    }
*/

}
