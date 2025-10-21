//para los reportes
package proshecto2;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import java.io.FileNotFoundException;
import com.itextpdf.layout.property.TextAlignment; //maldito yutu... me dijo que era propeties
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author kiquemarroquin
 */
public class GenerarReportes {
    //para probar un generador de archivos estandar

    private static String NombreArchivo(String tipoReporte) {
        LocalDateTime ahora = LocalDateTime.now();
        //formato de DD_MM_YYYY_HH_mm_ss
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        return ahora.format(formato) + "_" + tipoReporte + ".pdf";//para mientras en txt
    }

    //inventario simple
    public static void GenerarReporteInventario() {
        String NombreArchiv = NombreArchivo("Inventario");

        try {
//creamos el pdfwriter y el document
            PdfWriter escribir = new PdfWriter(NombreArchiv);
            PdfDocument pdf = new PdfDocument(escribir);

            //crear el documento pa to esto
            Document document = new Document(pdf);

            //añadir el ttirulo y la fecha
            document.add(new Paragraph("-------Reporte de inventario----------")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));//espacio despues de la fecha

            //creamos la tabla con las 4 columnas 
            Table tabla = new Table(UnitValue.createPercentArray(new float[]{10, 25, 15, 10, 10, 15, 15}))// anchos 
                    .useAllAvailableWidth(); //que use todo el ancho de la pagina

            //añadimos los encabezados
            tabla.addHeaderCell(new Cell().add(new Paragraph("Codigo").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Nombre").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Categoria").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Stock").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Estado").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Ultimo ingreso").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Sugerencias").setBold()));

            Object[][] datosProductos = AdminDProductos.DatosTablaCliente();
            if (datosProductos.length == 0) {
                document.add(new Paragraph("No hay productos registrados en el inventario"));
            } else {
                for (Object[] fila : datosProductos) {
                    try {
                        String codigo = (fila[0] != null) ? fila[0].toString() : "";
                        String nombre = (fila[1] != null) ? fila[1].toString() : "";
                        String categoria = (fila[2] != null) ? fila[2].toString() : "";
                        int stock = (fila[3] instanceof Integer) ? (Integer) fila[3] : 0;

                        //para el estaod y sugerencias
                        String estado;
                        String sugerencia;
                        if (stock < 10) {
                            estado = "Critico";
                            sugerencia = "Urgrente actualizar stock exitente";
                        } else if (stock <= 20) {
                            estado = "Bajo";
                            sugerencia = "Considerar abastecer stock";
                        } else {
                            estado = "Ok";
                            sugerencia = "Todo en orden";
                        }
                        //Buscar fecha
                        String ultIngreso = AdminDCompras.BuscarActualizacionStock(codigo);

                        //aladimos las celdas a la tabla 
                        tabla.addCell(new Cell().add(new Paragraph(codigo)));
                        tabla.addCell(new Cell().add(new Paragraph(nombre)));
                        tabla.addCell(new Cell().add(new Paragraph(categoria)));
                        tabla.addCell(new Cell().add(new Paragraph(String.valueOf(stock))));
                        tabla.addCell(new Cell().add(new Paragraph(estado)));
                        tabla.addCell(new Cell().add(new Paragraph(ultIngreso)));
                        tabla.addCell(new Cell().add(new Paragraph(sugerencia)));

                    } catch (Exception e) {
                        System.err.println("Error procesando fila para el pdf: " + java.util.Arrays.toString(fila) + " -> " + e.getMessage());
                        // Opcional: Añadir una fila de error a la tabla
                        tabla.addCell(new Cell(1, 7).add(new Paragraph("Error al procesar producto: " + (fila[0] != null ? fila[0] : "ID Desconocido"))));
                    }
                }
                document.add(tabla);
            }
            document.close();
            JOptionPane.showMessageDialog(null, "Reporte pdf de inventario generado", "Reporte generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se pudo cerar el archivo pdf", "Error de archivo", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo generar el reporte PDF ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //los demas reportes xd
}
