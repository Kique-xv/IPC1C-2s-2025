//los peidos pendientes
package proshecto2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kiquemarroquin
 */
public class MonitorPedidos extends Thread {
    private static final DateTimeFormatter Formato_timestamp = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public MonitorPedidos(){
        setDaemon(true);
        setName("Monitor-Pedidos");
    }
    
    @Override
    public void run(){
       try{
           while(true){
           int pendientes = AdminDPedidos.getCantPedidosPendientes();
           String timestamp = LocalDateTime.now().format(Formato_timestamp);
           
           //mensaje
           System.out.println("Pedidos Pendientes: " +pendientes+ " porcesando................" +timestamp);
           Thread.sleep(8000); //cada 8 segundos eso es bastante poco  alv
           }
       } catch(InterruptedException e){
           System.out.println(getName() + " interrumpido.");
           Thread.currentThread().interrupt();
       } catch(Exception e){
           System.err.println("Error en " + getName() + " : " + e.getMessage());
             e.printStackTrace();
       }
    }
}
