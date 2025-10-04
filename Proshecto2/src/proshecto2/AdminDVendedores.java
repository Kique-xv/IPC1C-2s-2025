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
public class AdminDVendedores {

    private static final String Archivo_Vendedor = "Vendedores.csv";
    private static final int MVendedores = 50;

    //esta es la matriz solo para los vendedores
    private static Vendedor[] listadVendedores = new Vendedor[MVendedores];
    private static int CantVendedores = 0;

    static {
        CargarVendedores();
      //  System.out.println("DEBUG: Vendedores cargados al inicio: " + CantVendedores); esto feue de ayuda xd
    }

    public static void CargarVendedores() {
        File archivo = new File(Archivo_Vendedor);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(null, "El arhivo de vendedores no fue encontrado", "Error 015", JOptionPane.ERROR_MESSAGE);
            return;
        }
        CantVendedores = 0;
        try (BufferedReader Lector = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = Lector.readLine()) != null && CantVendedores < listadVendedores.length) {
                String[] datos = linea.split(",");
                //patron vendedor: Codigo,  nombre, genero, contraseña, ventas
                if (datos.length >= Vendedor.NUM_CAMPOS_VENDEDOR) {
                    try {
                        String id = datos[Vendedor.ID].trim();
                        String nombre = datos[Vendedor.NOMBRE].trim();
                        String Contraseña = datos[Vendedor.CONTRASEÑA].trim();
                        String genero = datos[Vendedor.GENERO].trim();
                        int ventas = Integer.parseInt(datos[Vendedor.VENTAS_HECHAS].trim());

                        Vendedor Nvendedor = new Vendedor(id, nombre, Contraseña, genero);
                        //setter para las ventas
                        Nvendedor.setVentasHechas(ventas);
                        listadVendedores[CantVendedores++] = Nvendedor;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al crear al vendedor desde el CSV" + linea, "Error 16", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo CSV de vendedores.", "Error 17", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void GuardarVendedor() {
        try (PrintWriter escribir = new PrintWriter(new FileWriter(Archivo_Vendedor))) {
            for (int i = 0; i < CantVendedores; i++) {
                Vendedor v = listadVendedores[i];
                //formato del archivo, cod, nombre, genero, contraseña y ventas
                String lineacsv = v.getId() + "," + v.getNombre() + "," + v.getGenero() + "," + v.getContraseña() + "," + v.getVentasHechas();
                escribir.println(lineacsv);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar vendedores en CSV.", "Error 18", JOptionPane.ERROR_MESSAGE);
        }
    }
    //tdodo esto lo traje de administrador de usuarios porque al guardar los vendedores en el mismo archivo que el admin crea problemaas ayuda son  las 3 am 7.7

    public static boolean CodRepetido(String id) {
        System.out.println("DEBUG: Buscando duplicado para ID: " + id);
        for (int i = 0; i < CantVendedores; i++) { //buscar en la matriz de vendedore
         //   System.out.println("DEBUG: Comparando con vendedor cargado: " + listadVendedores[i].getId());
            if (listadVendedores[i].getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    //esto de aca para abajo es para los vendedores
    public static Vendedor BuscarVendedor(String id) {
        for (int i = 0; i < CantVendedores; i++) {
            //verificamos que la instancia del vendedor y el id sean iagualisyos, como todas la mujeres
            if (listadVendedores[i].getId().equals(id)) {
                return listadVendedores[i];
            }
        }
        return null; //no devuelve nada como ella 7-7
    }

    //creamos y agregamos un nuevo vendeor si el codigo SI ES unnicos
    public static boolean CreacionVendedor(String id, String nombre, String genero, String contrseña) {
        if (CantVendedores >= MVendedores) {
            JOptionPane.showMessageDialog(null, "El limte de vendedores fue alcanzado", "Error 06", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (CodRepetido(id)) {
            JOptionPane.showMessageDialog(null, "El codigo de vendedor ya esta en uso", "Error 07", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Vendedor Nvendedor = new Vendedor(id, nombre, contrseña, genero);
        listadVendedores[CantVendedores++] = Nvendedor;
        GuardarVendedor();
        return true;
    }
//un metodo para modifcar el nombre y contraseña

    public static boolean ModVendedor(String id, String Nnombre, String Ncontraseña) {
        Vendedor v = BuscarVendedor(id);
        if (v != null) {
            v.setNombre(Nnombre);
            v.setContraseña(Ncontraseña);
            GuardarVendedor();
            return true;
        }
        return false;
    }

    //un metodo de eliminacion por el codigo
    public static boolean EliminarVendedor(String id) {
        for (int i = 0; i < CantVendedores; i++) {
            if (listadVendedores[i].getId().equals(id)) {
                //el todo confiable ciclo for
                for (int j = i; j < CantVendedores - 1; j++) {
                    listadVendedores[j] = listadVendedores[j + 1];
                }
                listadVendedores[CantVendedores - 1] = null; //eliminamos la ultima referencia 
                CantVendedores--;
                GuardarVendedor();
                return true;
            }
        }
        return false;
    }
}
