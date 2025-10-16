 ///para ADMINISTRAR PEDidos 
package proshecto2;

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

    public static boolean ConfirmPedido(String idPedido, Vendedor vendedor) {
        int indiPedido = -1;

        for (int i = 0; i < CantPedidos; i++) {
            if (listadPedidos[i].getIDpedido().equalsIgnoreCase(idPedido)) {
                indiPedido = i;
                break;
            }
        }
        if (indiPedido != -1) {
            // la parte de confirmar 

            //Incrementear las ventas del contador de ventasd del vendedor
            vendedor.setVentasHechas(vendedor.getVentasHechas() + 1);
            AdminDVendedores.GuardarVendedor(); // guardamos los cambios xd

            //eliminar la parte de la lista de pendientes pendientes
            for (int i = indiPedido; i < CantPedidos - 1; i++) {
                listadPedidos[i] = listadPedidos[i + 1];
            }
            listadPedidos[CantPedidos - 1] = null;
            CantPedidos--;

            //la parte para guardarlo en el csv de pedidos
            JOptionPane.showMessageDialog(null, "El pedido " + idPedido + " ha sido confirmado\n");
            return true;
        }
        return false;//si no se encuentra ñada
    }
}
