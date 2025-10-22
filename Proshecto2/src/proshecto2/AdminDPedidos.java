 ///para ADMINISTRAR PEDidos 
package proshecto2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author kiquemarroquin
 */
public class AdminDPedidos {

    //archivo y el maximo de pedidos
    private static final String ArchivPedidos = "Pedidos.csv";
    private static final int MaxPedidos = 200;

    private static Pedidos[] listadPedidos = new Pedidos[MaxPedidos];
    private static int CantPedidos = 0;

    static {
        //cargar datos csv xd
    }

    public static Object[][] DatosTabla() {

        Object[][] datos = new Object[CantPedidos][5];
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < CantPedidos; i++) {
            Pedidos p = listadPedidos[i];

            datos[i][0] = p.getIDpedido();
            datos[i][1] = p.getFechaGeneracion().format(formato);
            datos[i][2] = p.getIdCliente();
            datos[i][3] = p.getNombreCliente();
            datos[i][4] = p.getTotal();

        }
        return datos;
    }

    public static boolean CrearPedido(Cliente cliente) {
        if (Carrito.getCantProd() == 0) {
            JOptionPane.showMessageDialog(null, "Tu carrito se encuentra vacio", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        //Generamos un id unico para el peido, lo hare lo mas simple posible no me quiero complicar la vida xd
        String NIdPedido = "PE-" + (CantPedidos + 101);
        double total = Carrito.CalcularTotal();

        ProdCarrito[] ProdsComprados = new ProdCarrito[Carrito.getCantProd()];
        System.arraycopy(Carrito.getProds(), 0, ProdsComprados, 0, Carrito.getCantProd());

        Pedidos Npedido = new Pedidos(NIdPedido, LocalDateTime.now(), cliente.getId(), cliente.getNombre(), total, ProdsComprados, Carrito.getCantProd());
//por aca asicoamos la lista de productos del carro con el pedido

        listadPedidos[CantPedidos++] = Npedido;

        Carrito.LimpiarCarro();
        //guardamos el cambio de stock en el archivo de productos         
        JOptionPane.showMessageDialog(null, "El pedido se ha realizado", "Pedido Creado", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    public static boolean ConfirmarPedido(String idPedido, Vendedor vendedor) {
        int indiPedido = -1;
        Pedidos PedidoConfirm = null;

        for (int i = 0; i < CantPedidos; i++) {
            if (listadPedidos[i].getIDpedido().equalsIgnoreCase(idPedido)) {
                indiPedido = i;
                PedidoConfirm = listadPedidos[i];
                break;
            }
        }
        if (PedidoConfirm != null) {
            try {
                if (PedidoConfirm.getProds() != null) { // Verifica que los productos no sean nulos
                    for (int i = 0; i < PedidoConfirm.getCantProductos(); i++) {
                        ProdCarrito Prods = PedidoConfirm.getProds()[i];
                        if (Prods != null && Prods.producto != null) {//verificamos el item y el producto
                            AdminDProductos.ReservaStock(Prods.producto.getCodigo(), Prods.cantidad);
                        } else {
                            System.err.println("Error: producto nulo en el pedido" + idPedido + " en el indice " + i);
                        }
                    }
                    AdminDProductos.GuardarProductos(); //guardamos cambios del stock
                } else {
                    System.err.println("Error: La lista de productos es nula para el pedido " + idPedido);
                }
            } catch (Exception e) {
                System.err.println("Error al procesar productos del pedido " + idPedido + ": " + e.getMessage());
                e.printStackTrace(); // Imprime más detalles del error
                JOptionPane.showMessageDialog(null, "Error al procesar los productos del pedido. Verifica la consola.", "Error Interno", JOptionPane.ERROR_MESSAGE);
                return false; // Detener si hay  un error aquí  
            }
            //le subimos las ventas al vendedior
            vendedor.setVentasHechas(vendedor.getVentasHechas() + 1);
            AdminDVendedores.GuardarVendedor();

            ProdCarrito[] productosConfirmados = new ProdCarrito[PedidoConfirm.getCantProductos()];
            System.arraycopy(PedidoConfirm.getProds(), 0, productosConfirmados, 0, PedidoConfirm.getCantProductos());
            //creamos el registro paranel historial
            CompraAceptada Ncompra = new CompraAceptada(
                    PedidoConfirm.getIDpedido(),
                    LocalDateTime.now(),
                    PedidoConfirm.getIdCliente(),
                    PedidoConfirm.getTotal(),
                    productosConfirmados,
                    PedidoConfirm.getCantProductos()
            );
            AdminDCompras.agregarCompra(Ncompra);

            //eliminar el pedido de la lista de pendientes
            for (int i = indiPedido; i < CantPedidos - 1; i++) {
                listadPedidos[i] = listadPedidos[i + 1];
            }
            listadPedidos[CantPedidos - 1] = null;
            CantPedidos--;
            JOptionPane.showMessageDialog(null, "El pedodo " + idPedido + " ha sido confirmado \nLa cantidad disponible se ha actualizado ");
            return true;
        }
        return false;
    }
}
