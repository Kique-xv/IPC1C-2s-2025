//con un cafe con el 99999999999999% de energia vamos a darle pelea a este proyecto feo
//posiblemente falte otra u otras semanas al gym.... MAS ME VALE QUE SALGA BIEN >:l
package proshecto2;

import javax.swing.SwingUtilities;

/**
 *
 * @author kiquemarroquin
 */
public class Proshecto2 {

    public static void main(String[] args) {
        System.out.println("INCIANDO HIP-SHOP...................."); //ME SEGUIRE NEGANDO A LLAMARLO SANCARLISTA SHOP
        //cargamos todo de una?   
        System.out.println("Cargando estado de Productos...");
        AdminDProductos.inicializar();
        
        System.out.println("Cargando estado de Vendedores...");
        AdminDVendedores.inicializar(); 
        
        System.out.println("Cargando estado de Clientes...");
        AdminDClientes.inicializar();  
        
        System.out.println("Cargando estado de Pedidos Pendientes...");
        AdminDPedidos.inicializar();
        
        System.out.println("Cargando estado de Compras Confirmadas...");
        AdminDCompras.inicializar();  
        
        System.out.println("Carga de estado completada");
        
        //los hilos monitores
        System.out.println("INCIANDO LOS MONITORES EN SEGUNDO PLANO");
        MonitorSesion monitorsesion = new MonitorSesion();
        MonitorPedidos monitorpedidos = new MonitorPedidos();
        MonitorStas monitorstas = new MonitorStas();
        
        //los iniciamos los hilos vaya 
        monitorsesion.start();
        monitorpedidos.start();
        monitorstas.start();
        System.out.println("MONITORES INCIADOS :D");

        SwingUtilities.invokeLater(() -> { //esto va aca, como en la practica 
                    new IngresarUsuarios().setVisible(true);
        });
    }
}
