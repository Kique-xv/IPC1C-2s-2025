//aca va toda la logica de los clientes, y con logica me refiero a ABSOLUTAMENTE TODOS LOS METODOS REFERENTES A LOS CLIENTES
//Esto me autocopie xddddd
//si lees esto RESPIRACION AUTOMÁTICA
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
public class AdminDClientes {

    private static final String ArchiClient = "Clientes.csv";
    private static final int MaxClientes = 100; //el maximo de clientes

    private static Cliente[] listadClientes = new Cliente[MaxClientes];
    private static int CantClientes = 0;

    //esto es para cargar los clientes al inciar el programa
    static {
        CargarClientes(); //metodo que ta alli abajo
    }

    public static void CargarClientes() {
        File archivo = new File(ArchiClient);
        if (!archivo.exists()) {
            return; // si no hay un archivo no deberia cargarse nada 
        }
        CantClientes = 0;
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
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
        try (PrintWriter escribir = new PrintWriter(new FileWriter(ArchiClient))) {
            escribir.println("id, nombre, contraseña, genero, cumpleaños");//el encabezado
            //ciclo for para recorrer la lista de clientes creados para encontrar los que se hayan creado ya
            for (int i = 0; i < CantClientes; i++) {
                Cliente c = listadClientes[i];
                String lineaCsv = c.getId() + "," + c.getNombre() + "," + c.getcontraseña() + "," + c.getGenero() + "," + c.getCumpleaños();
                escribir.println(lineaCsv);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los clientes en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
    public static boolean CreacionClientes(String id, String nombre, String contraseña, String genero, String cumpleaños) {
        if (CantClientes >= MaxClientes) {
            JOptionPane.showMessageDialog(null, "Se alcanzó el límite de clientes para crear", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (CodRepetido(id)) {
            JOptionPane.showMessageDialog(null, "El código de cliente ya está en uso", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //creamos el nuevo cliente 
        Cliente Ncliente = new Cliente(id, nombre, contraseña, genero, cumpleaños);
        listadClientes[CantClientes++] = Ncliente;

        //Lo agregamos ambien a la lista pa que LUEGO INICIEN SESION NO PIENSO KGARLA DE NUEVO
        AdminDUsuarios.AgregarUsuario(Ncliente);

        //y lo guardamos en la lisata de clientes xd
        GuardarClientes();
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
    public static Cliente BuscarCliente(String id){
        for(int i =0; i < CantClientes; i++){
            if(listadClientes[i].getId().equalsIgnoreCase(id)){
                return listadClientes[i]; //si lo encontramos deuvelve el objeto
            }
        }
        return null; //devuelve nulo o mejor dhico no devuelve na´si no lo encuentra
    }
    //pa modificar clientes
    public static boolean ModCliente(String id, String Nnombre, String Ncontraseña, String Ngenero, String Ncumple){
        //buscamos al cliente que queremos modificar
        Cliente Modcliente = BuscarCliente(id);
        
        if(Modcliente != null){
            //si lo encontramos, actualizmoa sus datos con los seters o serers como hablan en inglich
            Modcliente.setNomber(Nnombre);
            Modcliente.setContraseña(Ncontraseña);
            Modcliente.setCumpleaños(Ncumple);
            Modcliente.setGenero(Ngenero);
            //esto es para que el cambio de contraseña funcione en el incion de sesion
            AdminDUsuarios.ActualizarUsuario(id, Nnombre, Ncontraseña);
            
            //lo guardamos en el archivo csv de clientes
            GuardarClientes();
            return true;//devolvemos un verdadero si salio bien xd
            
        }
        return false;
    }
}
