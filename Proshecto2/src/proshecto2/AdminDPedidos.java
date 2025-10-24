 ///para ADMINISTRAR PEDidos 
package proshecto2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private static final String ESTADO_PEDIDOS_SER = "PedidosPendientes.ser";
    private static final int MaxPedidos = 200;

    private static Pedidos[] listadPedidos = new Pedidos[MaxPedidos];
    private static int CantPedidos = 0;

    //el get prar la chingadera de los hilos
    public static int getCantPedidosPendientes() {
        return CantPedidos;//devolvemos el contador de pedidos sin confirmar
    }


    public static void inicializar() {
        System.out.println("INICIALIZANDO SISTEMA DE PRODUCTOS...");
        cargarEstado();
        System.out.println("Sistema de productos listo :D");
    }

    public static void guardarEstado() {
        System.out.println("Serializando estado de Pedidos Pendientes");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ESTADO_PEDIDOS_SER))) {
            oos.writeInt(CantPedidos);
            for (int i = 0; i < CantPedidos; i++) {
                if (listadPedidos[i] != null) {
                    oos.writeObject(listadPedidos[i]);
                }
            }
            System.out.println("Estado de pedidos pendientes guardado en " + ESTADO_PEDIDOS_SER);
        } catch (IOException e) {
            System.err.println("Error al guardar estado de pedidos pendientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void cargarEstado() {
        File archivoEstado = new File(ESTADO_PEDIDOS_SER);
        System.out.println("Deserializando estado de Pedidos Pendientes");
        if (!archivoEstado.exists()) {
            System.out.println("Archivo " + ESTADO_PEDIDOS_SER + " no encontrado, se inciara sin pedidos pendientes cargados.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoEstado))) {
            CantPedidos = ois.readInt();
            listadPedidos = new Pedidos[MaxPedidos]; 
            for (int i = 0; i < CantPedidos; i++) {
                listadPedidos[i] = (Pedidos) ois.readObject();
            }
            System.out.println("Estado de pedidos pendientes cargado desde " + ESTADO_PEDIDOS_SER + "Total: " + CantPedidos);
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("Error al cargar estado de pedidos pendientes: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar datos guardados de pedidos pendientes", "Error", JOptionPane.WARNING_MESSAGE);
            CantPedidos = 0; // Reiniciar
            listadPedidos = new Pedidos[MaxPedidos]; 
        }
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
        guardarEstado();
        //guardamos el cambio de stock en el archivo de productos         
        JOptionPane.showMessageDialog(null, "El pedido se ha realizado", "Pedido Creado", JOptionPane.INFORMATION_MESSAGE);
        Bitacora.RegistrarEvento(Bitacora.OP_Eli_Cliente, cliente.getId(), Bitacora.OP_Realizar_Pedido, Bitacora.ESTADO_EXITOSA, "Pedido realizado");

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
                Bitacora.RegistrarEvento(Bitacora.Tipo_Vendedor, vendedor.getId(), Bitacora.OP_Confirmar_Pedido, Bitacora.ESTADO_FALLIDA, "No se pudo confirmar la compra");
                System.err.println("Error al procesar productos del pedido " + idPedido + ": " + e.getMessage());
                e.printStackTrace(); // Imprime más detalles del error
                JOptionPane.showMessageDialog(null, "Error al procesar los productos del pedido. Verifica la consola.", "Error Interno", JOptionPane.ERROR_MESSAGE);
                return false; // Detener si hay  un error aquí  
            }
            //le subimos las ventas al vendedior
            vendedor.setVentasHechas(vendedor.getVentasHechas() + 1);
            vendedor.setTotalVentasHechas(vendedor.getVentasHechas() + PedidoConfirm.getTotal());
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
            Bitacora.RegistrarEvento(Bitacora.Tipo_Vendedor, vendedor.getId(), Bitacora.OP_Confirmar_Pedido, Bitacora.ESTADO_EXITOSA, "Pedido confirmado");

            //eliminar el pedido de la lista de pendientes
            for (int i = indiPedido; i < CantPedidos - 1; i++) {
                listadPedidos[i] = listadPedidos[i + 1];
            }
            listadPedidos[CantPedidos - 1] = null;
            CantPedidos--;
            guardarEstado();
            JOptionPane.showMessageDialog(null, "El pedodo " + idPedido + " ha sido confirmado \nLa cantidad disponible se ha actualizado ");
            return true;
        }
        return false;
    }
}
