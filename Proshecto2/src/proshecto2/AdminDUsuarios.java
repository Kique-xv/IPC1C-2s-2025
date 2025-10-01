//Una clase para administrar los usuarios, con un nombre muy evidente no?
package proshecto2;

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
public class AdminDUsuarios {

    private static final String ArchiUsur = "Usuarios.csv";

    //una muy bonita matriz para los objetos usuario
    private static Usuarios[] listadUsuarios = new Usuarios[100]; //numero maximo de usuarios
    private static int CantUsuarios = 0; //ya se la saben el contrador de usuarios 

    static {
        cargarUsuarios(); //un metodo que estara abajito
        if (CantUsuarios == 0) {
            //el admin
            listadUsuarios[CantUsuarios++] = new Usuarios("admin", "Admin", "IPC1C", "ADMIN"); //xd
            GuardarUsuarios();//lo mismo que dije arriba
        }
    }

    public static void cargarUsuarios() {
        File archivo = new File(ArchiUsur);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(null, "El archivo de los usuarios no se encontro, se inciara como admin", "Error 01", JOptionPane.ERROR_MESSAGE);
            return;
        }
        CantUsuarios = 0; //reinciamos el contador para cargar

        try (BufferedReader Lector = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = Lector.readLine()) != null && CantUsuarios < listadUsuarios.length) {
                String[] datos = linea.split(",");

                if (datos.length == Usuarios.CAMPOS) {
                    listadUsuarios[CantUsuarios++] = new Usuarios(
                            datos[Usuarios.ID].trim(),
                            datos[Usuarios.NOMBRE].trim(),
                            datos[Usuarios.CONTRASEÑA].trim(),
                            datos[Usuarios.TIPOUSUR].trim()
                    );
                }
            }
            if (CantUsuarios > 0) {
                JOptionPane.showMessageDialog(null, "Los usuarios creados se han cargado", "Accion exitosamente exitosa", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Algo salio mal en la carga de los archivos", "Error 02", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void GuardarUsuarios() {
        try (PrintWriter escribir = new PrintWriter(new FileWriter(ArchiUsur))) {
            for (int i = 0; i < CantUsuarios; i++) {
                Usuarios u = listadUsuarios[i];
                escribir.println(u.getId() + "," + u.getNombre() + "," + u.getcontraseña() + "," + u.getTipoUsuario());
            }
            JOptionPane.showMessageDialog(null, "Los suarios fueron guardados en el archivo correspondiente", "Accion salio bien", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Algo salio mal al guardar a los usuarios", "Error 03", JOptionPane.ERROR_MESSAGE);
        }
    }
//un metodo para validar las credenciales de los usuarios

    public static Usuarios autenticazion(String id, String Contraseña) {
        for (int i = 0; i < CantUsuarios; i++) {
            Usuarios u = listadUsuarios[i];
            if (u.getId().equals(id) && u.getcontraseña().equals(Contraseña)) {
                return u;
            }
        }
        return null; //la operacion salio mal xd
    }

    public static void AgregarUsuario(Usuarios nUsuarios) {
        if (CantUsuarios < listadUsuarios.length) {
            listadUsuarios[CantUsuarios++] = nUsuarios;
            GuardarUsuarios();
        } else {
            JOptionPane.showMessageDialog(null, "se ha llegado al maximo de usuarios registrados", "Error 04", JOptionPane.WARNING_MESSAGE);
        }
    }
}