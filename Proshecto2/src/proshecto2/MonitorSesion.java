// la cochinada de los hilos... como lo odio
package proshecto2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kiquemarroquin
 */
public class MonitorSesion  extends Thread{
    private static final DateTimeFormatter Formato_Time = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public MonitorSesion(){
        setDaemon(true); //si se detiene el programa c cieera
        setName("Monitor-Sesione");
    }
    
    @Override
    public void run(){
      try{
          while(true){
          int activos = AdminDUsuarios.getSesionesActivas();
          String timestamp = LocalDateTime.now().format(Formato_Time);
          //mensaje
          System.out.println("Usuarios Activos: " +activos+ " ultima actividad " + timestamp);
          Thread.sleep(10000); //creo que esto son 10 segundos
      }
      }  catch(InterruptedException e){
          System.out.println(getName() + " interrumpido.");
          Thread.currentThread().interrupt();
      } catch (Exception e){
          System.err.println("Error en " +getName()+ " : " + e.getMessage());
          e.printStackTrace();
      }
    }
    
}
