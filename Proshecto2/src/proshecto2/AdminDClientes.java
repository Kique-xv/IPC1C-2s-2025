//aca va toda la logica de los clientes, y con logica me refiero a ABSOLUTAMENTE TODOS LOS METODOS REFERENTES A LOS CLIENTES
//Esto me autocopie xddddd
//si lees esto RESPIRACION AUTOMÁTICA
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
public class AdminDClientes {

    private static final String ArchiClient = "Clientes.csv";
    private static final String ESTADO_CLIENTES_SER = "clientes.ser";
    private static final int MaxClientes = 100; //el maximo de clientes

    private static Cliente[] listadClientes = new Cliente[MaxClientes];
    private static int CantClientes = 0;
    public static void inicializar() {
        System.out.println("INICIALIZANDO SISTEMA DE PRODUCTOS...");
        cargarEstado();
        System.out.println("Sistema de productos listo :D");
    }

    public static void guardarEstado() {
        System.out.println("Serializando estado de Clientes ");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ESTADO_CLIENTES_SER))) {
            oos.writeInt(CantClientes); // Guardar contador
            for (int i = 0; i < CantClientes; i++) {
                if (listadClientes[i] != null) {
                    oos.writeObject(listadClientes[i]); // Guardar objeto
                }
            }
            System.out.println("Estado de clientes guardado en " + ESTADO_CLIENTES_SER);
        } catch (IOException e) {
            System.err.println("Error al guardar estado de clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void cargarEstado() {
        File archivoEstado = new File(ESTADO_CLIENTES_SER);
        System.out.println("Deserializando estado de Clientes");
        if (!archivoEstado.exists()) {
            System.out.println("Archivo " + ESTADO_CLIENTES_SER + " no encontrado se cargara desde el csv");
            CargarClientes(); 
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoEstado))) {
            CantClientes = ois.readInt(); // Leer contador
            listadClientes = new Cliente[MaxClientes]; // Reiniciar arreglo
            for (int i = 0; i < CantClientes; i++) {
                listadClientes[i] = (Cliente) ois.readObject(); // Leer objeto y castear
            }
            System.out.println(" Estado de clientes cargado desde " + ESTADO_CLIENTES_SER + "Total: " + CantClientes);
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("Error al cargar estado de clientes: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar datos guardados de clientes (.ser). Se intentará cargar desde CSV.", "Error Serialización", JOptionPane.WARNING_MESSAGE);
            CantClientes = 0; // Reiniciar
            listadClientes = new Cliente[MaxClientes]; // Limpiar
            CargarClientes(); 
        }
    }

    public static void CargarClientes() {
        File archivo = new File(ArchiClient);
        if (!archivo.exists()) {
            return; // si no hay un archivo no deberia cargarse nada 
        }
        CantClientes = 0;
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numlinea = 1;
            lector.readLine();//nos saltamos el encabezado

            while ((linea = lector.readLine()) != null && CantClientes < MaxClientes) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {//id, nombre, contraseña, genero, cumple
                    Cliente cliente = new Cliente(
                            datos[0].trim(),
                            datos[1].trim(),
                            datos[2].trim(),
                            datos[3].trim(),
                            datos[4].trim()
                    );
                    listadClientes[CantClientes++] = cliente;
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo de clientes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void GuardarClientes() {
        System.out.println("Guardando Clientes.csv"); // Mensaje debug
        try (PrintWriter escribir = new PrintWriter(new FileWriter(ArchiClient))) {
            escribir.println("Codigo,nombre,cotraseña,genero,cumpleaños");//el encabezado
            //ciclo for para recorrer la lista de clientes creados para encontrar los que se hayan creado ya
            for (int i = 0; i < CantClientes; i++) {
                Cliente c = listadClientes[i];
                if (c != null) {
                    String lineaCsv
                            = c.getId() + ","
                            + c.getNombre() + ","
                            + c.getcontraseña() + ","
                            + c.getGenero() + ","
                            + c.getCumpleaños();
                    escribir.println(lineaCsv);
                }
            }
            System.out.println("Clientes.csv guardado"); // Mensaje debug
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los clientes en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarEstado();
    }

    //veremos  si el codigo del cliente ya essta en uso
    public static boolean CodRepetido(String id) {
        for (int i = 0; i < CantClientes; i++) {
            if (listadClientes[i].getId().equalsIgnoreCase(id)) {//ignoramos las mayuculas y minusculas 
                return true;
            }
        }
        return false;
    }

    //lo mismo de productos y vendedires hasta el nombre lo copie, es 
    public static boolean CreacionClientes(String id, String nombre, String contraseña, String genero, String cumpleaños, String idVendedor) {
        if (CantClientes >= MaxClientes) {
            JOptionPane.showMessageDialog(null, "Se alcanzó el límite de clientes para crear", "Error", JOptionPane.ERROR_MESSAGE);
            Bitacora.RegistrarEvento(Bitacora.Tipo_Vendedor, idVendedor, Bitacora.OP_Crear_Cliente, Bitacora.ESTADO_FALLIDA, "se llego al limite de clientes creados");

            return false;
        }
        if (CodRepetido(id)) {
            JOptionPane.showMessageDialog(null, "El código de cliente ya está en uso", "Error", JOptionPane.ERROR_MESSAGE);
            Bitacora.RegistrarEvento(Bitacora.Tipo_Vendedor, idVendedor, Bitacora.OP_Crear_Cliente, Bitacora.ESTADO_FALLIDA, "no se creo el cliente por codigo repitido");

            return false;
        }
        //creamos el nuevo cliente 
        Cliente Ncliente = new Cliente(id, nombre, contraseña, genero, cumpleaños);
        listadClientes[CantClientes++] = Ncliente;

        //Lo agregamos ambien a la lista pa que LUEGO INICIEN SESION NO PIENSO KGARLA DE NUEVO
        AdminDUsuarios.AgregarUsuario(Ncliente);

        //y lo guardamos en la lisata de clientes xd
        GuardarClientes();
        Bitacora.RegistrarEvento(Bitacora.Tipo_Vendedor, idVendedor, Bitacora.OP_Crear_Cliente, Bitacora.ESTADO_EXITOSA, "se ha creado un cliente");
        return true;
    }

    //para la tabla de Clientes LPM
    public static Object[][] DatosTablaCliente() {
        //miramos si hay vendedores cargados para evitar errores
        if (listadClientes == null || CantClientes == 0) {
            return new Object[0][4];
        }
        Object[][] datos = new Object[CantClientes][4];
        for (int i = 0; i < CantClientes; i++) {
            //obtenemos el objeto vendedore de la lista
            Cliente c = listadClientes[i];
            datos[i][0] = c.getId();
            datos[i][1] = c.getNombre();
            datos[i][2] = c.getGenero();
            //ESTO imPEDIA QUE LA MALDITA TABLA SE ACTUALICE
            datos[i][3] = c.getCumpleaños();
        }
        return datos;
    }

    //metodo pa buscal al cliente por su codigo
    public static Cliente BuscarCliente(String id) {
        for (int i = 0; i < CantClientes; i++) {
            if (listadClientes[i].getId().equalsIgnoreCase(id)) {
                return listadClientes[i]; //si lo encontramos deuvelve el objeto
            }
        }
        return null; //devuelve nulo o mejor dhico no devuelve na´si no lo encuentra
    }

    //pa modificar clientes
    public static boolean ModCliente(String id, String Nnombre, String Ncontraseña, String Ngenero, String Ncumple, String idVendedor) {
        //buscamos al cliente que queremos modificar
        Cliente Modcliente = BuscarCliente(id);

        if (Modcliente != null) {
            //si lo encontramos, actualizmoa sus datos con los seters o serers como hablan en inglich
            Modcliente.setNomber(Nnombre);
            Modcliente.setContraseña(Ncontraseña);
            Modcliente.setCumpleaños(Ncumple);
            Modcliente.setGenero(Ngenero);
            //esto es para que el cambio de contraseña funcione en el incion de sesion
            AdminDUsuarios.ActualizarUsuario(id, Nnombre, Ncontraseña);

            //lo guardamos en el archivo csv de clientes
            GuardarClientes();
            Bitacora.RegistrarEvento(Bitacora.Tipo_Vendedor, idVendedor, Bitacora.OP_Mod_Cliente, Bitacora.ESTADO_EXITOSA, "Se modifoco los datos correctamente de un cliente");

            return true;//devolvemos un verdadero si salio bien xd

        }
        Bitacora.RegistrarEvento(Bitacora.Tipo_Vendedor, idVendedor, Bitacora.OP_Mod_Cliente, Bitacora.ESTADO_FALLIDA, "No se encontro al cliente");

        return false;

    }

    public static boolean EliminarCliente(String Id, String idVendedor) {
        int encontrar = -1;
        //buscamos el indice del cliente a eliminar
        for (int i = 0; i < CantClientes; i++) {
            if (listadClientes[i].getId().equalsIgnoreCase(Id)) {
                encontrar = i;
                break;
            }
        }
        if (encontrar != -1) {
            //eliminamos al cliente de la lista de clientes
            for (int i = encontrar; i < CantClientes - 1; i++) {
                listadClientes[i] = listadClientes[i + 1];
            }
            listadClientes[CantClientes - 1] = null;
            CantClientes--;

            //lo eliminamos de la lista general de usuarios
            AdminDUsuarios.EliminarUsuario(Id);

            //lo guardamos en ambos archivos para mantener la consistencia
            GuardarClientes();
            AdminDUsuarios.GuardarUsuarios(); //ca que se guarde en la parte de inicio de sesion
            Bitacora.RegistrarEvento(Bitacora.Tipo_Vendedor, idVendedor, Bitacora.OP_Eli_Cliente, Bitacora.ESTADO_EXITOSA, "Se eliminó correctamente a un cliente");

            return true;
        }
        return false; //si no encuentra al cliente xd
    }

    //el metodo para cargar los clientes
    public static String CargarClientes(File archivo, String idVendedor) {
        StringBuilder resumen = new StringBuilder();
        int cargaExito = 0;
        int fallaForm = 0;
        int fallaLogic = 0; //para los duplicado y otras chingaderas
        boolean cambios = false;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            lector.readLine();
            int numLinea = 1;

            String linea;
            while ((linea = lector.readLine()) != null) {
                numLinea++;

                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] datos = linea.split(",");

                //validacion que sean 5 columnas
                if (datos.length != 5) {
                    resumen.append("linea").append(numLinea).append(": error porque se esperan 5 columnas .\n");
                    fallaLogic++;
                    continue;
                }

                String codigo = datos[0].trim();
                String nombre = datos[1].trim();
                String contraseña = datos[2].trim().toUpperCase();
                String genero = datos[3].trim();
                String cumple = datos[4].trim();

                //la otra validacion si el codigo ya exite 
                if (AdminDClientes.CodRepetido(codigo)) {
                    resumen.append("Linea").append(numLinea).append(": error  porque el codigo : ").append(codigo).append(" esta repetido \n");
                    Bitacora.RegistrarEvento(Bitacora.Tipo_Vendedor, idVendedor, Bitacora.OP_Cargar_Cliente, Bitacora.ESTADO_FALLIDA, "No se cargaron correctamente los clientes");

                    fallaLogic++;
                    continue;
                }
                //si todo va poila intentamos crear el clinete
                if (AdminDClientes.CreacionClientesSinGuardar(codigo, nombre, contraseña, genero, cumple, idVendedor)) {
                    cambios = true;
                    cargaExito++;
                } else {
                    resumen.append("Línea ").append(numLinea).append(": erro  al crear al cliente.\n");
                    fallaLogic++;
                }
            }
        } catch (IOException e) {
            return "Error al leer el archivo" + e.getMessage();
        }
        if (cambios) {
            GuardarClientes();
            guardarEstado();
        }
        String RepFinal = " Resumen de la carga de clientes\n "
                + "Clientes cargados correctamente: " + cargaExito + "\n"
                + "Errores de formato:  " + fallaForm + "\n"
                + "Duplicados y otros: " + fallaLogic + "\n"
                + "Detalles \n"
                + resumen.toString();
        Bitacora.RegistrarEvento(Bitacora.Tipo_Vendedor, idVendedor, Bitacora.OP_Cargar_Cliente, Bitacora.ESTADO_EXITOSA, "Se cargaron correctamente los clientes");
        return RepFinal;
    }

    //para el reporte de los webos
    public static Cliente[] getListdClientes() {
        //devolvemos la copiar por si las moscas
        Cliente[] copia = new Cliente[CantClientes];
        System.arraycopy(listadClientes, 0, copia, 0, CantClientes);
        return copia;
    }

    public static int getCantlientes() {
        return CantClientes;
    }

    public static boolean CreacionClientesSinGuardar(String id, String nombre, String contraseña, String genero, String cumpleaños, String idVendedor) {
        if (CantClientes >= MaxClientes) {
            return false;
        }
        Cliente Ncliente = new Cliente(id, nombre, contraseña, genero, cumpleaños);
        listadClientes[CantClientes++] = Ncliente;

        if (!AdminDUsuarios.CodRepetido(id)) {
            AdminDUsuarios.AgregarUsuario(Ncliente);
        } else {
            AdminDUsuarios.ActualizarUsuario(id, nombre, contraseña);
            return true;
        }
        return false;

    }
}
