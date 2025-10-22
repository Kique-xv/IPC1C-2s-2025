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

    //el metodo para calcular las estadisticas
    private static ProductoStats[] CalcularStatsVentas() {
        //obtenemos todos los productos unicos existentes
        //tuve que hacer mas y mas chamba y no he estudiado :(
        Productos[] TodoProductos = AdminDProductos.getListadProductos();
        int numProductosOriginal = AdminDProductos.getCantProducto();

        if (numProductosOriginal == 0) {
            return new ProductoStats[0]; //ñada de ñada  
        }
        //ceramos un arreglo arreglado arreglativo prar guardar las stats xd
        ProductoStats[] tempEstadisticas = new ProductoStats[numProductosOriginal];
        int statsReales = 0;
        for (int i = 0; i < numProductosOriginal; i++) {
            if (TodoProductos[i] != null) {
                tempEstadisticas[statsReales++] = new ProductoStats(TodoProductos[i]);
            } else {
                System.err.println(" Producto nulo encontrado en la lista de productos.");
            }
        }
        //ajustamos el tamaño si hubi nulos
        ProductoStats[] estadisticas = new ProductoStats[statsReales];
        System.arraycopy(tempEstadisticas, 0, estadisticas, 0, statsReales);
        int numProductos = statsReales;

        //comprobar si quedo vacio despues de filtrar
        if (numProductos == 0) {
            return new ProductoStats[0]; //salir si todos son nullos
        }
        //obtenemos TODOS los pedidos confirmados
        CompraAceptada[] ComprasConfirm = AdminDCompras.getListadComprasAceptadas();
        int CantCompras = AdminDCompras.getCantComprasAceptadas();
        //recorremos cada pedido confirmado
        for (int i = 0; i < CantCompras; i++) {
            CompraAceptada pedido = ComprasConfirm[i];
            if (pedido == null || pedido.getProds() == null || pedido.getCantProductos() <= 0) {
                continue; //
            }
            ProdCarrito[] Prods = pedido.getProds();
            for (int j = 0; j < pedido.getCantProductos(); j++) {
                ProdCarrito Prod = Prods[j];
                if (Prod == null || Prod.producto == null) {
                    continue;
                }

                //buscamos el producto en el arreglo de estadisticas
                for (int k = 0; k < numProductos; k++) {
    
                    if (estadisticas[k].producto.getCodigo().equalsIgnoreCase(Prod.producto.getCodigo())) {
                        //actualizamos la cantidad e ingresos
                        estadisticas[k].CantVendida += Prod.cantidad;
                        estadisticas[k].IngresosGen += Prod.cantidad * Prod.producto.getPrecio();
                        break; //pasamos al siguiente pedido
                    }
                }
            }
        }
        return estadisticas;
    }

    //unmetodo para ordenar;
    private static void OrdenarStats(ProductoStats[] stats, boolean decend) {
        int n = stats.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (stats[j] == null || stats[j + 1] == null || stats[j].producto == null || stats[j + 1].producto == null) {
                    System.err.println("Se encontro un producto nulo");
                    continue;
                }
                boolean cambio;
                if (decend) {
                    cambio = stats[j].CantVendida < stats[j + 1].CantVendida;
                } else {
                    cambio = stats[j].CantVendida > stats[j + 1].CantVendida;
                }
                if (cambio) {
                    //intercambiamos
                    ProductoStats temp = stats[j];
                    stats[j] = stats[j + 1];
                    stats[j + 1] = temp;
                }
            }
        }
    }

    //para los menos vendidos
    public static void GenerarReporteMenosVen() {
        String NombreArchiv = NombreArchivo("MenosVendidos");
        ProductoStats[] estadisticas = CalcularStatsVentas();

        if (estadisticas == null || estadisticas.length == 0) {
            JOptionPane.showMessageDialog(null, "No hay datos de ventas para generar el reporte", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        OrdenarStats(estadisticas, false); //ordenamos de mayor a menor

        try {
            PdfWriter escribir = new PdfWriter(NombreArchiv);
            PdfDocument pdf = new PdfDocument(escribir);
            Document document = new Document(pdf);

            document.add(new Paragraph("TOP 5 PRODUCTOS MENOS VENDIDOS")
                    .setFontSize(16).setBold().setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Generando el:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .setTextAlignment(TextAlignment.CENTER).setMarginBottom(20));
//la tabla
            Table tabla = new Table(UnitValue.createPercentArray(new float[]{40, 20, 20, 20}))
                    .useAllAvailableWidth();

            tabla.addHeaderCell(new Cell().add(new Paragraph("Nombre de producto").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Cantidad vendidad").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Cantidad dispobile").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Recomendacion").setBold()));

            int limite = Math.min(5, estadisticas.length);
            for (int i = 0; i < limite; i++) {
                ProductoStats stat = estadisticas[i];
                tabla.addCell(new Cell().add(new Paragraph(stat.producto.getNombre())));
                tabla.addCell(new Cell().add(new Paragraph(String.valueOf(stat.CantVendida))));
                tabla.addCell(new Cell().add(new Paragraph(String.valueOf(stat.producto.getStock())))); // el stock actual
                tabla.addCell(new Cell().add(new Paragraph("Poner en descuento")));//la recomendacion

            }
            document.add(tabla);
            document.close();
            JOptionPane.showMessageDialog(null, "Reporte PDF '" + NombreArchiv + "' generado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte .", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //para los mas vendidos

    public static void GenerarReporteMasVen() {
        String NombreArchiv = NombreArchivo("MasVendidos");
        ProductoStats[] estadisticas = CalcularStatsVentas();

        if (estadisticas == null || estadisticas.length == 0) {
            JOptionPane.showMessageDialog(null, "No hay datos de ventas para generar el reporte.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        OrdenarStats(estadisticas, true); //orden decendente

        try {
            //la misma mica que lo otro
            PdfWriter escribir = new PdfWriter(NombreArchiv);
            PdfDocument pdf = new PdfDocument(escribir);
            Document document = new Document(pdf);

            document.add(new Paragraph("TOP 5 PRODUCTOS MAS VENDIDOS")
                    .setFontSize(16).setBold().setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Generando el:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .setTextAlignment(TextAlignment.CENTER).setMarginBottom(20));
//la tabla x2
            Table tabla = new Table(UnitValue.createPercentArray(new float[]{40, 20, 20, 20}))
                    .useAllAvailableWidth();

            tabla.addHeaderCell(new Cell().add(new Paragraph("Nombre de producto").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Cantidad vendidad").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Catergoria").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Ganancias generadas(Q)").setBold()));
//tomamos hasta 5, o menos si no hay tanta coas
            int limite = Math.min(5, estadisticas.length);
            for (int i = 0; i < limite; i++) {
                ProductoStats stat = estadisticas[i];
                //contenido de la tabla
                tabla.addCell(new Cell().add(new Paragraph(stat.producto.getNombre())));
                tabla.addCell(new Cell().add(new Paragraph(String.valueOf(stat.CantVendida))));
                tabla.addCell(new Cell().add(new Paragraph(stat.producto.getCategoria())));
                tabla.addCell(new Cell().add(new Paragraph(String.format("%.2f", stat.IngresosGen))));
            }
            document.add(tabla);
            document.close();
            JOptionPane.showMessageDialog(null, "Reporte PDF '" + NombreArchiv + "' generado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte PDF.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
