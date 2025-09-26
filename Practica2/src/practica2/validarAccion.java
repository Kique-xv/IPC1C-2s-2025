//igual que en el proyecto... hare esta cosa.. unas... muchas vecss
package practica2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author kiquemarroquin
 */
public class validarAccion {
//una mausque herramienta que nos ayudara mas tarde

    private static final String NombreBit = "Bitacora_Acciones.txt"; //un archivo de datos
    private static final SimpleDateFormat FECHA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void regisAccion(String accion, boolean estado, String mensaje) {
        //ibtenemos la fecha
        String tiempo = FECHA.format(new Date());

        // ahora veremos male sal o sale bien
        String Estado = estado ? "Salio bien" : "Salio mal";

        //creamos un registro
        String resgitrar = String.format("[%s] [%s] - %s: %s", tiempo, Estado, accion, mensaje);

        //lo podemos en consola
        System.out.println(resgitrar);

        //lo mandamos a un txt xddd por si las moscas
        try (PrintWriter escribir = new PrintWriter(new FileWriter(NombreBit, true))) {
            escribir.println(resgitrar);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Algo Malo sal... no se que chucha fue", "Error 030", JOptionPane.ERROR_MESSAGE);
            validarAccion.regisAccion("guardar datos en la bitacora", false, "Salio mal");
        }
    }

    //ahora veremos la bitacora en consola
    public static void MostrarBit() {
        File archivo = new File(NombreBit);
        //ojala que no se vea feo despues del archivo de musica o si no me mato, se va a ver TODAS LAS LLAMADAS QUE HE HECHO DE ESTE METODO, no este pero ya sabes... enserio estas leyedo esto...
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(null, "La bicatora no exixte xd, ojala no la hayas borrado menso", "Error 031", JOptionPane.ERROR_MESSAGE);
            validarAccion.regisAccion("Buscar bitacora", false, "Salio mal APROPOSITO");
            return;
        }
        //para que se borre eso
        int confirm = JOptionPane.showConfirmDialog(null, "¿se va a borrar la bicatora... estas seguro?", "Confirmar boorador de la botacora", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (archivo.delete()) {
                JOptionPane.showMessageDialog(null, "Todas las acciones se borraron :D", "salio bien", JOptionPane.INFORMATION_MESSAGE);
                validarAccion.regisAccion("Borrar la bitacora", true, "Salio bien");
            } else {
                JOptionPane.showMessageDialog(null, "No borramos la bitacora, porque? no me preguntes wn tu lo decidiste", "Error 032", JOptionPane.ERROR_MESSAGE);
                validarAccion.regisAccion("borrar la bitacora", true, "Salio  y ya");
            }
        }
    }
}
