//para las compas
package proshecto2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author kiquemarroquin
 */
public class AdminDCompras {

    private static final String ArchivCompra = "HistorialCompras.csv"; // archivo de las compras
    private static final int MaxCompras = 500; //el maximo de compras confimadas gracias a lanzar moneda online
    private static CompraAceptada[] listadCompras = new CompraAceptada[MaxCompras];
    private static int CantCompras = 0;

    static {
        CargarCompra();
    }

    //
    public static void CargarCompra() {
        File archivo = new File(ArchivCompra);
        if (!archivo.exists()) {
            return; //si no hay historial
        }
        CantCompras = 0;//reinciar el contador antes de cargar
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            lector.readLine();

            while ((linea = lector.readLine()) != null && CantCompras < MaxCompras) {
                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    try {
                        String idPedido = datos[0].trim();
                        LocalDateTime fecha = LocalDateTime.parse(datos[1].trim());
                        String idCliente = datos[2].trim();
                        double total = Double.parseDouble(datos[3].trim());

                        Pedidos pedido = new Pedidos(idPedido, fecha, idCliente, "Cliente Desconocido", total, null, 0);//solo pa probar usamos nada y nada xd

                        listadCompras[CantCompras++] = new CompraAceptada(idPedido, fecha, idCliente, total,null, 0);
                    } catch (Exception e) {
                        System.err.println("Error al cargar línea de Compras.csv: " + linea + " -> " + e.getMessage());
                    }
                } else {
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el historial de compras.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
//para guardar en el csv

    public static void GuardarCompras() {
        File archivo = new File(ArchivCompra);
        try (PrintWriter escribir = new PrintWriter(new FileWriter(ArchivCompra))) {
            //para el encabeza
            escribir.println("idPedido,fechaConfirmacion,idCliente,total");
            for (int i = 0; i < CantCompras; i++) {
                CompraAceptada c = listadCompras[i];
                //guardar le fecha en formato para facilitar la lectura
                String lineaCsv = c.getIdPedido() + "," + c.getFechaConfirm().toString() + "," + c.getIdCliente() + "," + c.getTotal();
                escribir.println(lineaCsv);
            }
        } catch (IOException e) {
            System.err.println("    > ERROR FATAL al guardar ComprasConfirmadas.csv:");
            e.printStackTrace(); // <-- Esto imprimirá el error completo en la consola
            JOptionPane.showMessageDialog(null,
                    "No se pudo guardar el historial de compras.\nError: " + e.getMessage() + "\nVerifica los permisos de la carpeta.",
                    "Error de Archivo",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //metodo para agregar una comprar al arreglo
    public static void agregarCompra(CompraAceptada compra) {
        if (CantCompras < MaxCompras) {
            listadCompras[CantCompras++] = compra;
            //en el futuro PODRIA poner algo como pa guardar en un csv, pero alli veo xd
            //efectivamente lo hice xd
            GuardarCompras();//lo guardamos de una 
        } else {
            JOptionPane.showMessageDialog(null, "Ha alcanzado el maximo de compras", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static Object[][] DatosTablaHistorial(String idCliente) {

        //contamos cuatas compras tiene el cliente
        int contadorCliente = 0;
        for (int i = 0; i < CantCompras; i++) {
            if (listadCompras[i] != null && listadCompras[i].getIdCliente().equalsIgnoreCase(idCliente)) {
                contadorCliente++;
            }
        }
        Object[][] datos = new Object[contadorCliente][3]; //codifo, fecha y total ein quesaliitos
        DateTimeFormatter formatoVista = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int indiceDat = 0;

        //recorremos la lista completa y copiamos solo las del clietnte
        for (int i = 0; i < CantCompras; i++) {
            CompraAceptada c = listadCompras[i];
            if (c.getIdCliente().equalsIgnoreCase(idCliente)) {
                datos[indiceDat][0] = c.getIdPedido();
                datos[indiceDat][1] = c.getFechaConfirm().format(formatoVista);
                datos[indiceDat][2] = c.getTotal();
                indiceDat++;
            }
        }
        return datos;
    }

    public static String BuscarActualizacionStock(String codigoProducto) {
        File ArchivHist = new File("HistorialStock.csv");
        LocalDateTime ultFecha = null; //ultima fecha de actuializacion
        DateTimeFormatter FormatoArchiv = DateTimeFormatter.ISO_LOCAL_DATE_TIME; //pa la fecha

        if (!ArchivHist.exists()) {
            return "No hay historial"; //no necesito explicar          
        }
        try (BufferedReader lector = new BufferedReader(new FileReader(ArchivHist))) {
            String linea;
            lector.readLine();

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                //formato que esperamos Fecha en iso , codigo-producto, cantidad, usuario,id-usuario, 
                if (datos.length >= 2 && datos[1].trim().equalsIgnoreCase(codigoProducto)) {
                    try {
                        LocalDateTime fActual = LocalDateTime.parse(datos[0].trim(), FormatoArchiv);
                        if (ultFecha == null || fActual.isAfter(ultFecha)) {
                            ultFecha = fActual;
                        }
                    } catch (Exception e) {
                        System.err.println("Error al leer la fecha en historial: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer HistorialStock.csv  " + e.getMessage());
            return "Error de lectura";
        }
        if (ultFecha != null) {
            //devolvemos la feyc en un formato mas leible para mensos como yo xd
            return ultFecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } else {
            return "sin registros"; // no se encontraron ingresso para el producto
        }
    }

    //sip para acceder a la lista completa para los reportes
    public static CompraAceptada[] getListadComprasAceptadas() {
        return listadCompras;
    }

    public static int getCantComprasAceptadas() {
        return CantCompras;
    }
}
