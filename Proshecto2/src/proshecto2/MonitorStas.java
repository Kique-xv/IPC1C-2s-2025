///ya saben pa que es esto pero aunasi, es para las ventas y los productos registrado y asi
package proshecto2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kiquemarroquin
 */
public class MonitorStas  extends Thread{ //me comi la T la gran pucha
    private static final  DateTimeFormatter Formato_timestapm = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public MonitorStas(){
        setDaemon(true);
        setName("Monitor-Estadisticas");
        
    }
    
    public void run(){
        try{
            while(true){
                //obtenermos los datos ventas = ventas acptadas
                int ventas = AdminDCompras.getCantComprasAceptadas();
                int ProductoReg =AdminDProductos.getCantProducto();
                String timestamp = LocalDateTime.now().format(Formato_timestapm);
                
                //el mensaje
                System.out.println("Ventas del día: " + ventas + " | Productos registrados: " + ProductoReg + " | " + timestamp);
         Thread.sleep(150000); //se actualiza cada 15 segundos
            }
        } catch(InterruptedException e){
            System.out.println(getName() + " interrumpido.");
            Thread.currentThread().interrupt();
        } catch(Exception e){
            System.err.println("Error en " + getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
