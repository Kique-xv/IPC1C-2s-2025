//eeee sip esta clase latenia de hace reato ocupando espacio, PERO ES MOMENTO DE BRILLAR.
package proyecto1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author kiquemarroquin
 */
public class ValidarAccion {
    // una funcion para registrar cada accion e imprimirlas en un archivo de texto

    public static void VerAccion(String vendedor, String accion, String estado) {
        try (FileWriter Fw = new FileWriter("Bitacora.txt", true); PrintWriter pw = new PrintWriter(Fw)) {

            LocalDateTime Ahora = LocalDateTime.now();
            DateTimeFormatter Formato = DateTimeFormatter.ofPattern("dd_mm_yyyy HH:mm:ss");
            String fechaHora = Ahora.format(Formato);

            pw.println("----------------------------------------------");
            pw.println("--------BITACORA DE ACCIONES--------");
            pw.println("Fecha y Hora: " + fechaHora);
            pw.println("Usuario: " + vendedor);
            pw.println("Accion :" + accion);
            pw.println("Estado :" + estado);
            pw.println("----------------------------------------------");

        } catch (IOException e){
            System.out.println("Error 013: Ocurrio un error al registrar la accion en la bitaora");         
        }
    }
    //funcion para mostar la Bitacora
    public static void MostarAcciones(){
        System.out.println("-------Historial de acciones----------");
        try(Scanner Fs = new Scanner(new File("Bitacora.txt"))){
            while(Fs.hasNextLine()) {
                System.out.println(Fs.nextLine());             
            }
        } catch(FileNotFoundException e){
                   System.out.println("Error 014: No se ha encontrado el archivo de la bitacora");
        }
    }
        //Pense que como TECNICAMENTE los archivos son temporales.... 
//me saque del coco una forma para limpiar los archivos de texto, me regañaran? lo dudo
    public static void LimpHistorial(){
        try {
          //  limpiar la bitacora
          new FileWriter("Bitacora.txt", false).close();
          System.out.println("La bitacora fue limpiada :D");
          
          //Limpiar las ventas
          new FileWriter("Venta.txt", false).close();
          System.out.println("Historial de ventas fue limpiado :D");
        } catch(IOException e){
            System.out.println("Erro 015: Ocurrio un error al intentar limpiar los archivos");
        }
    }
}
