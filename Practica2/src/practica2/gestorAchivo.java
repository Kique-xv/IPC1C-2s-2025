/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author kiquemarroquin
 */
public class gestorAchivo {

    private static final String NombreArchi = "Historial_personajes.txt";

    //ya me canseeeeee, el jodido cafe ya no me hace efecto
    public static void GuardarPesonaje() {
        try (PrintWriter escribir = new PrintWriter(new FileWriter(NombreArchi))) {
            escribir.println(Personajes.CantPersonajes);
            for (int i = 0; i < Personajes.CantPersonajes; i++) {
                escribir.println(String.join("|", Personajes.personaje[i]));
            }
            JOptionPane.showMessageDialog(null, "Se ha hecho guardado los Datos :D");
            validarAccion.regisAccion("guardar datos del personaje", true, " datos de " + Personajes.CantPersonajes + "Salio bien");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar, porque? yo que se man D:" + e.getMessage(), "Error 030", JOptionPane.ERROR_MESSAGE);
            validarAccion.regisAccion("guardar datos del personaje", false, " datos de " + Personajes.CantPersonajes + "Salio MAL");

        }
    }

    public static void CargarPersonajes() {
        //para buscar el archivo y buscar cada dato xd
        File archivo = new File(NombreArchi);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(null, "No hemos encontrado nada... Estamos Jodidos", "Error 031", JOptionPane.WARNING_MESSAGE);
            validarAccion.regisAccion("Encontrar el archivo", false, "Salio MAL");

            return;
        }
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String Lineas;
            if ((Lineas = lector.readLine()) != null) {
                Personajes.CantPersonajes = Integer.parseInt(Lineas.trim());
            }
            for (int i = 0; i < Personajes.CantPersonajes; i++) {
                if ((Lineas = lector.readLine()) != null) {
                    String[] datos = Lineas.split("\\|");
                    if (datos.length >= Personajes.NUMCAMPOSPERSONAJE) {
                        for (int j = 0; j < Personajes.NUMCAMPOSPERSONAJE; j++) {
                            Personajes.personaje[i][j] = datos[j].trim();
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Se ha Cargado el sistema :D");
            validarAccion.regisAccion("guardar datos en archivo", true, "Salio MAL");

        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar el archivo.... SIP NOS JODIMOS" + e.getMessage(), "Error 032", JOptionPane.ERROR_MESSAGE);
            validarAccion.regisAccion("guardar datos en archivo", false, "Salio MAL");

        }
    }

    public static void BorrarDatos() {
        File archivo = new File(NombreArchi);
        if (archivo.exists()) {
            int Confirm = JOptionPane.showConfirmDialog(null, "Se va a eliminar todos los datos...¿ REALMENTE ESTAS SEGURO?", "Confirmar Limpieza", JOptionPane.YES_NO_OPTION);
            if (Confirm == JOptionPane.YES_OPTION) {
                if (archivo.delete()) {
                    Personajes.CantPersonajes = 0; //reseteamos el contador de personajes
                    JOptionPane.showMessageDialog(null, "TODO fue Borrado... yay..");
                    validarAccion.regisAccion("borrar datos del archivo", true, "Salio bien");

                } else {
                    JOptionPane.showMessageDialog(null, "Algo salio mal.. aun...o bueno ya sabes ", "Error 033", JOptionPane.ERROR_MESSAGE);
                    validarAccion.regisAccion("borrar datos del historial", false, "Salio MAL");

                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay nada que borrar aun...", "Error 034", JOptionPane.WARNING_MESSAGE);
            validarAccion.regisAccion("Borrar datos del hisotial", false, "Salio MAL");

        }
    }
}
