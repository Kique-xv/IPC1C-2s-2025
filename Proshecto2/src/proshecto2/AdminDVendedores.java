package proshecto2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author kiquemarroquin
 */
public class AdminDVendedores {

    private static final String Archivo_Vendedor = "Vendedores.csv";

    private static final String Estado_Vendedor = "vendedores.ser"; //ser

    private static final int MVendedores = 50;
    private static final int NUM_CAMPOS_VENDEDOR = 7; // Ahora son 7 columnas por lo del reporte y las weas
    //esta es la matriz solo para los vendedores
    private static Vendedor[] listadVendedores = new Vendedor[MVendedores];
    private static int CantVendedores = 0;


// para guardar en el csv
    public static void Guardarestado() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Estado_Vendedor))) {
            oos.writeInt(CantVendedores); // Guardar el contador primero
            for (int i = 0; i < CantVendedores; i++) {
                if (listadVendedores[i] != null) {
                    oos.writeObject(listadVendedores[i]); // Guardar cada objeto
                }
            }
            System.out.println("Estado de productos guardado en " + Estado_Vendedor);
        } catch (IOException e) {
            System.err.println("Error al guardar estado de productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void cargarEstado() {
        File archivoEstado = new File(Estado_Vendedor);
        System.out.println("Deserializando estado de Productos"); 
        if (!archivoEstado.exists()) {
            System.out.println("El Archivo " + Estado_Vendedor + " no se encontro se cargara desde el csv"); 
            CargarVendedores(); // cargar desde CSV como respaldo
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoEstado))) {
            CantVendedores = ois.readInt(); // Leer el contador
            listadVendedores = new Vendedor[MVendedores]; // Crear el arreglo
            for (int i = 0; i < CantVendedores; i++) {
                listadVendedores[i] = (Vendedor) ois.readObject(); // Leer cada objeto
            }
            System.out.println("Estado de productos cargado desde " + Estado_Vendedor + "Total: " + CantVendedores);
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("Error al cargar estado de producto: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar datos guardados de productos, se cargara desde el csv", "Error", JOptionPane.WARNING_MESSAGE);
            // Si falla la, intenta cargar desde CSV
            CantVendedores = 0; 
            listadVendedores = new Vendedor[MVendedores];
            CargarVendedores();
        }
    }

    public static void inicializar() {
        System.out.println("INICIALIZANDO SISTEMA DE PRODUCTOS...");
        cargarEstado();
        System.out.println("Sistema de productos listo :D");
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
            int numl = 0;
            Lector.readLine();
            while ((linea = Lector.readLine()) != null && CantVendedores < listadVendedores.length) {
                String[] datos = linea.split(",");
                //patron vendedor: Codigo,  nombre, genero, contraseña, ventas
                if (datos.length >= NUM_CAMPOS_VENDEDOR) {
                    int ventas = 0;
                    try {
                        String id = datos[0].trim();
                        String nombre = datos[1].trim();
                        String Contraseña = datos[2].trim();
                        //el tipo de usuario lo guarada en datos[3]
                        String genero = datos[4].trim();
                        ventas = Integer.parseInt(datos[5].trim());
                        double totalVentas = Double.parseDouble(datos[6].trim());

                        if (datos.length > 5 && !datos[5].trim().isEmpty()) {
                            try {
                                totalVentas = Double.parseDouble(datos[5].trim());
                            } catch (NumberFormatException nfParseDouble) {
                                System.err.println("Advertencia: Total de ventas inválido para vendedor " + id + ". Usando 0.0.");
                            }
                        }

                        Vendedor Nvendedor = new Vendedor(id, nombre, Contraseña, genero);
                        Nvendedor.setVentasHechas(ventas);
                        Nvendedor.setTotalVentasHechas(totalVentas);
                        listadVendedores[CantVendedores++] = Nvendedor;

                        if (!AdminDUsuarios.CodRepetido(id)) { // asi evitamos los duplicados si  en AdminDUsuarios ya lo cargo
                            AdminDUsuarios.AgregarUsuario(Nvendedor);
                        }

                    } catch (NumberFormatException nfe) {

                        JOptionPane.showMessageDialog(null, "Error al crear al vendedor desde el CSV" + linea, "Error 16", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e) {

                    }
                }
            }
        } catch (IOException e) {

            JOptionPane.showMessageDialog(null, "Error al cargar el archivo CSV de vendedores.", "Error 17", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void GuardarVendedor() {
        try (PrintWriter escribir = new PrintWriter(new FileWriter(Archivo_Vendedor))) {
            escribir.println("codigo,nombre,contraseña,tipoUsuario,genero,ventasHechas,totalVentasHechas");
            for (int i = 0; i < CantVendedores; i++) {
                Vendedor v = listadVendedores[i];
                if (v != null) {
                    //formato del archivo, cod, nombre, genero, contraseña y ventas y si eñ mp haber agregadp el tipo de usuario no me dejaba inciar sesion      
                    String lineacsv
                            = v.getId() + ","
                            + v.getNombre() + ","
                            + v.getContraseña() + ","
                            + v.getTipoUsuario() + ","
                            +//ESTA MALDITA LINEA ME KGO LA NOCHE >:/
                            v.getGenero() + ","
                            + v.getVentasHechas() + ","
                            + v.getTotalVentasHechas(); //las ventas hechas osea todasa pues
                    escribir.println(lineacsv);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar vendedores en CSV.", "Error 18", JOptionPane.ERROR_MESSAGE);
        }
        Guardarestado();
    }
    
    //tdodo esto lo traje de administrador de usuarios porque al guardar los vendedores en el mismo archivo que el admin crea problemaas ayuda son  las 3 am 7.7
    public static boolean CodRepetido(String id) {
        for (int i = 0; i < CantVendedores; i++) { //buscar en la matriz de vendedore
            if (listadVendedores[i].getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    //esto de aca para abajo es para los vendedores
    public static Vendedor BuscarVendedor(String id) {
        for (int i = 0; i < CantVendedores; i++) {
            //verificamos que la instancia del vendedor y el id sean iagualisyos, como todas la mujeres
            if (listadVendedores[i].getId().equalsIgnoreCase(id)) {
                return listadVendedores[i];
            }
        }
        return null; //no devuelve nada como ella 7-7
    }

    //creamos y agregamos un nuevo vendeor si el codigo SI ES unnicos
    public static boolean CreacionVendedor(String id, String nombre, String genero, String contrseña) {
        if (CantVendedores >= MVendedores) {
            JOptionPane.showMessageDialog(null, "El limte de vendedores fue alcanzado", "Error 06", JOptionPane.ERROR_MESSAGE);
            Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Crear_Vendedor, Bitacora.ESTADO_FALLIDA, "Maximo de vendedores alcanzado");

            return false;
        }
        if (CodRepetido(id)) {
            JOptionPane.showMessageDialog(null, "El codigo de vendedor ya esta en uso", "Error 07", JOptionPane.ERROR_MESSAGE);
            Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Crear_Vendedor, Bitacora.ESTADO_FALLIDA, "Codigo repetido");

            return false;
        }
        Vendedor Nvendedor = new Vendedor(id, nombre, contrseña, genero);
        listadVendedores[CantVendedores++] = Nvendedor;
        GuardarVendedor();
        AdminDUsuarios.AgregarUsuario(Nvendedor);
        Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Crear_Vendedor, Bitacora.ESTADO_EXITOSA, "Creacion de vendedor exitosa");
        return true;
    }
//un metodo para modifcar el nombre y contraseña

    public static boolean ModVendedor(String id, String Nnombre, String Ncontraseña) {
        Vendedor v = BuscarVendedor(id);
        if (v != null) {
            v.setNombre(Nnombre);
            v.setContraseña(Ncontraseña);
            GuardarVendedor();
            Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Mod_Vendedor, Bitacora.ESTADO_EXITOSA, "Modificacion de vendedor exitosa");

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
                Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Eli_Vendedor, Bitacora.ESTADO_EXITOSA, "Eliminacion de vendedor exitosa");

                return true;
            }
        }
        return false;
    }

    //para la tabla de vendedores LPM
    public static Object[][] DatosTablaVendedor() {
        //miramos si hay vendedores cargados para evitar errores
        if (listadVendedores == null || CantVendedores == 0) {
            return new Object[0][4];
        }
        Object[][] datos = new Object[CantVendedores][4];
        for (int i = 0; i < CantVendedores; i++) {
            //obtenemos el objeto vendedore de la lista
            Vendedor v = listadVendedores[i];
            datos[i][0] = v.getId();
            datos[i][1] = v.getNombre();
            datos[i][2] = v.getGenero();
            //ESTO imPEDIA QUE LA MALDITA TABLA SE ACTUALICE
            datos[i][3] = v.getVentasHechas();
        }
        return datos;
    }

    public static Vendedor ValidIngreso(String id, String Contraseña) {
        //buscamos al vendedor por codigo
        Vendedor v = BuscarVendedor(id);
        //si lo encuentra y la contraseña existe
        if (v != null && v.getContraseña().equals(Contraseña)) {
            return v;
        }
        return null;
    }

    //para el fokin reporte
    public static Vendedor[] getListadVendedores() {
        //creamos una copira para evitar modificaciones externas por si las moscas
        Vendedor[] copia = new Vendedor[CantVendedores];
        System.arraycopy(listadVendedores, 0, copia, 0, CantVendedores);
        return copia;
    }

    public static int getCantVendedores() {
        return CantVendedores;
    }
}
