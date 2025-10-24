//esto es PARA LA  BITACORA Y SI YA ESTOY CANSADO.... ME FALTA BASTANTE TODAVIA :(
package proshecto2;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
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
public class Bitacora {

    private static final String Archiv_Bitacora = "Bitacora.csv";
    private static final DateTimeFormatter FHFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    //Constantespara los usuarios 
    public static final String Tipo_Admin = "ADMIN";
    public static final String Tipo_Vendedor = "VENDEDOR";
    public static final String Tipo_Cliente = "CLIENTE";
    public static final String Tipo_Sistema = "SISTEMA";//para los eventos que no sea involucardo a los webones de los usuarios

    //Constantes para las operacione
    public static final String OP_Ingreso_Exitoso = "INGRESO_EXITOSO";
    public static final String OP_Ingreso_Fallido = "INGRESO_FALLIDO";
    public static final String OP_Salida = "SALIDA";

    //para lo del vendedor
    public static final String OP_Crear_Vendedor = "CREAR_VENDEDOR";
    public static final String OP_Mod_Vendedor = "MODIFICAR_VENDEDOR";
    public static final String OP_Eli_Vendedor = "ELIMINAR_VENDEDOR";
    public static final String OP_Cargar_Vendedor = "CARGAR_VENDEDOR_CSV";

    //para lo del preoucto
    public static final String OP_Crear_Producto = "CREAR_PRODUCTO";
    public static final String OP_Mod_Producto = "MODIFICAR_PRODUCTO";
    public static final String OP_Eli_Producto = "ELIMINAR_PRODUCTO";
    public static final String OP_Cargar_Producto = "CARGAR_PRODUCTO";
    public static final String OP_Agregar_Stock = "AGREGAR_STOCK";
    public static final String OP_Ver_Detalles_Prod = "VER_DETALLES_PRODUCTO";
    public static final String OP_Cargar_Stock = "CARGAR_STOCK_SCV";

    //PARA EL CLIENTE
    public static final String OP_Crear_Cliente = "CREAR_CLIENTE";
    public static final String OP_Mod_Cliente = "MODIFICAR_CLIENTE";
    public static final String OP_Eli_Cliente = "ELIMINAR_CLIENTE";
    public static final String OP_Cargar_Cliente = "CARGAR_CLIENTE_CSV";

    //otrascoas
    public static final String OP_Agregar_Producto_Carrito = "AGREGAR_PRODUCTO_CARRITO";
    public static final String OP_Actualizar_Pedido = "ACTUALIZAR_PEDIDO";
    public static final String OP_Eliminar_Producto_Carrito = "ELIMINAR_PRODUCTO_CARRITO";
    public static final String OP_Realizar_Pedido = "REALIZAR_PEDIDO";
    public static final String OP_Confirmar_Pedido = "CONFIRMAR_PEDIDO";
    public static final String OP_Ver_Historial = "VER_HISTORIAL_COMPRAS";

    //reportes
    public static final String OP_Generar_Reporte = "GENERAR_REPORTE";
    public static final String OP_Generar_Reporte_Prod_Mas = "GENERAR_REPORTE_PRODUCTOS_MAS_VENDIDOS";
    public static final String OP_Generar_Reporte_Prod_Menos = "GENERAR_REPORTE_PRODUCTOS_MENOS_VENDIDOS";
    public static final String OP_Generar_Reporte_Inventario = "GENERAR_REPORTE_INVENTARIO";
    public static final String OP_Generar_Reporte_Ventas_Vendedor = "GENERAR_REPORTE_VENTAS_VENDEDOR";
    public static final String OP_Generar_Reporte_Clientes_Activos = "GENERAR_REPORTE_CLIENTES_ACTIVOS";
    public static final String OP_Generar_Reporte_Financiero = "GENERAR_REPORTE_FINANCIERO";
    public static final String OP_Generar_Reporte_Prod_Caducar = "GENERAR_REPORTE_PRODUCTOS_POR_CADUCAR";

    public static final String OP_GENERAR_REPORTE = "GENERAR_REPORTE";
    public static final String OP_EXPORTAR_BITACORA = "EXPORTAR_BITACORA";

    // estsod de la tarea
    public static final String ESTADO_EXITOSA = "EXITOSA";
    public static final String ESTADO_FALLIDA = "FALLIDA";

    public static synchronized void RegistrarEvento(String tipoUsuario, String codigoUsuario, String operacion, String estado, String descripcion) {
        LocalDateTime ahora = LocalDateTime.now();
        String HfFormato = ahora.toString();
//limpiamos  y preoaaramos los datos para el csv
        String tipoUsuLimpio = (tipoUsuario != null ? tipoUsuario.toUpperCase() : "DESCONOCIDO").replace("\"", "\"\"");
        String codigoUsuLimpio = (codigoUsuario != null ? codigoUsuario : "N/A").replace("\"", "\"\"");
        String opLimpia = (operacion != null ? operacion.toUpperCase() : "DESCONOCIDA").replace("\"", "\"\"");
        String estadoLimpio = (estado != null ? estado.toUpperCase() : "DESCONOCIDO").replace("\"", "\"\"");
        String descLimpia = (descripcion != null ? descripcion : "").replace("\"", "\"\"");
        //prar escribir en el archivo

        String lineaCsv = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                HfFormato,
                tipoUsuLimpio,
                codigoUsuLimpio,
                opLimpia,
                estadoLimpio,
                descLimpia);
        File archivo = new File(Archiv_Bitacora);
        boolean esNuevo = !archivo.exists() || archivo.length() == 0;

        try (PrintWriter escribir = new PrintWriter(new FileWriter(Archiv_Bitacora, true))) {
            if (esNuevo) {
                escribir.println("FechaHoraISO,TipoUsuario,CodigoUsuario,Operacion,Estado,Descripcion");
            }
            escribir.println(lineaCsv);
        } catch (IOException e) {
            System.err.println(" No se pudo escribir en el archivo de bitácora");
            System.err.println("Línea no registrada: " + lineaCsv);
            e.printStackTrace();
        }
    }

    //para exportar esta chingada a pdf
    public static void BitacoraPdf() {
        File ArchivoCsv = new File(Archiv_Bitacora);
        if (!ArchivoCsv.exists() || ArchivoCsv.length() == 0) {
            JOptionPane.showMessageDialog(null, "El archivo de la bitacora esta vacio o no existe", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter Formatonombre = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        String NombreArchiv = ahora.format(Formatonombre) + "_Bitacora.pdf";

        try {
            PdfWriter escribir = new PdfWriter(NombreArchiv);
            PdfDocument pdf = new PdfDocument(escribir);
            Document document = new Document(pdf);

            document.add(new Paragraph("Bitácora de acciones ")
                    .setFontSize(16).setBold().setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Exportado el: " + ahora.format(FHFormato))
                    .setTextAlignment(TextAlignment.CENTER).setMarginBottom(15));
            //la tabla con las 6 columnas
            Table tabla = new Table(UnitValue.createPercentArray(new float[]{18, 12, 12, 18, 10, 30})) // Ajusta anchos
                    .useAllAvailableWidth();

            //leemos el csv y lo añadimos a pdf
            try (BufferedReader lector = new BufferedReader(new FileReader(ArchivoCsv))) {
                String linea;
                boolean encabezado = true;
                while ((linea = lector.readLine()) != null) {
                    String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Split por coma fuera de comillas, intelignet no?

                    if (partes.length == 6) {
                        Cell[] celdas = new Cell[6];
                        for (int i = 0; i < 6; i++) {
                            // quitqmos las comillas si existen al principio o al final
                            String textCelda = partes[i];
                            if (textCelda.startsWith("\"") && textCelda.endsWith("\"")) {
                                textCelda = textCelda.substring(1, textCelda.length() - 1).replace("\"\"", "\"");//quitamos las comillas
                            }
                            if (i == 0 && !encabezado) {
                                try {
                                    LocalDateTime fecha = LocalDateTime.parse(textCelda);
                                    textCelda = fecha.format(FHFormato);
                                } catch (Exception e) {
                                    //no gacemos nada
                                }
                            }
                            celdas[i] = new Cell().add(new Paragraph(textCelda));
                            if (encabezado) {
                                celdas[i].setBold(); //todo en negrita
                            }
                        }
                        for (Cell celda : celdas) {
                            if (encabezado) {
                                tabla.addHeaderCell(celda);
                            } else {
                                tabla.addCell(celda);
                            }
                        }
                    } else if (!linea.trim().isEmpty()) {//no añadimos filas vacias
                        tabla.addCell(new Cell(1, 6).add(new Paragraph("Error al paresea la linea : " + linea).setFontSize(8)));
                    }
                    encabezado = false;
                }
            }
            document.add(tabla);
            document.close();
            RegistrarEvento(Bitacora.Tipo_Admin, "admin", Bitacora.OP_EXPORTAR_BITACORA, Bitacora.ESTADO_EXITOSA, "Bitacora exportada a PDF");
            JOptionPane.showMessageDialog(null, "Bitácora exportada a pdf", "Exportación Completa", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.err.println("Error al exportar la bitácora a PDF: " + e.getMessage());
            e.printStackTrace();
            RegistrarEvento(Bitacora.Tipo_Admin, "admin", Bitacora.OP_EXPORTAR_BITACORA, Bitacora.ESTADO_FALLIDA, "No se pudo exportar la bitacora  a PDF");
            JOptionPane.showMessageDialog(null, "Error al exportar la bitácora a PDF.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void BorrarBitacora() {
        File archivoBit = new File(Archiv_Bitacora);

        if (!archivoBit.exists()) {
            JOptionPane.showMessageDialog(null, "El archivo de la bitacora no existe actualmente.", "Archivo No Encontrado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        //pedimos la confirmacion
        int Confirm = JOptionPane.showConfirmDialog(null, "¿Estas seguro de querer borrar el archivo de la bitacora?", "Confirmar borrado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        //si el susuario deice si
        if (Confirm == JOptionPane.YES_OPTION) {
            //intentemos borrar
            try {
                RegistrarEvento(Bitacora.Tipo_Admin, "admin", "BORRAR_BITACORA", "INTENTO", "Intentando borrar la botacora");

                if (archivoBit.delete()) {
                    JOptionPane.showMessageDialog(null, "Se elimino la bitácora", "Borrado completo", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo borrar el archivo de bitácora", "Error al Borrar", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SecurityException e) {
                JOptionPane.showMessageDialog(null, "No se pudo borrar el archivo de bitácora", "Error al Borrar", JOptionPane.ERROR_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado al borrar la bitácora.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Borrado de bitácora cancelado.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
