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
import java.time.LocalDate;
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

            document.add(new Paragraph("Generado el:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
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

            document.add(new Paragraph("Generado el:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
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
//para ordenar lo vendedores

    private static void OrdenarVendedores(Vendedor[] vendedores, int cantidad) {
//ordenamos por el tatal de ventas generadas 
        for (int i = 0; i < cantidad - 1; i++) {
            for (int j = 0; j < cantidad - i - 1; j++) {
                //chequeamos que no hayan nulos
                if (vendedores[j] == null || vendedores[j + 1] == null) {
                    continue;
                }
                if (vendedores[j].getTotalVentasHechas() < vendedores[j + 1].getTotalVentasHechas()) {
                    //intercambiamos 
                    Vendedor temp = vendedores[j];
                    vendedores[j] = vendedores[j + 1];
                    vendedores[j + 1] = temp;
                }
            }
        }
    }

    //para las ventasd por vendedor
    public static void ReporteVentasVendedor() {
        String NombreArchiv = NombreArchivo("VentasPorVendedor");
        Vendedor[] vendedores = AdminDVendedores.getListadVendedores();
        int cantVendedores = AdminDVendedores.getCantVendedores();

        if (cantVendedores == 0) {
            JOptionPane.showMessageDialog(null, "No hay vendedores registrados", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        //ordenbamos los vendedores pa el rango
        OrdenarVendedores(vendedores, cantVendedores);
        try {
            PdfWriter escribir = new PdfWriter(NombreArchiv);
            PdfDocument pdf = new PdfDocument(escribir);
            Document document = new Document(pdf);

            document.add(new Paragraph("Reporte de ventas por vendedor")
                    .setFontSize(16).setBold().setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Generado el:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .setTextAlignment(TextAlignment.CENTER).setMargin(20));

            Table tabla = new Table(UnitValue.createPercentArray(new float[]{10, 18, 32, 15, 15, 10})) //codigo, nombre, los pedidos confirmados, y el total de ventas
                    .useAllAvailableWidth();
            //encabezados actualizados
            tabla.addHeaderCell(new Cell().add(new Paragraph("Rango").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Codigo de vendedor").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Nombre del vendedor").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Compras confirmadas").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Total de ventas (Q)").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Promedio de ventas").setBold()));

            for (int i = 0; i < cantVendedores; i++) {
                //una validacion por si las moscas
                if (vendedores[i] != null) {
                    Vendedor v = vendedores[i];

                    //calcular promedio a las celdas
                    double promedioVenta = 0.0;
                    if (v.getTotalVentasHechas() > 0) {//para evitar dividir por cero
                        promedioVenta = v.getTotalVentasHechas() / v.getVentasHechas();
                    }
                    tabla.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1)))); // rango
                    tabla.addCell(new Cell().add(new Paragraph(v.getId())));
                    tabla.addCell(new Cell().add(new Paragraph(v.getNombre())));
                    tabla.addCell(new Cell().add(new Paragraph(String.valueOf(v.getTotalVentasHechas()))));
                    tabla.addCell(new Cell().add(new Paragraph(String.format("%.2f", v.getTotalVentasHechas()))));
                    tabla.addCell(new Cell().add(new Paragraph(String.format("%.2f", promedioVenta)))); // el  promedio
                }
            }
            document.add(tabla);
            document.close();
            JOptionPane.showMessageDialog(null, "Reporte pdf " + NombreArchiv + " generado ", "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte pdf.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void ReporteClientesActivos() {//ya el pvot nombre lo dice todo
        String NombreArchiv = NombreArchivo("ClientesActivos");
        Cliente[] TodosClientes = AdminDClientes.getListdClientes();
        int cantClientes = AdminDClientes.getCantlientes();

        CompraAceptada[] pedidosConfirmados = AdminDCompras.getListadComprasAceptadas();
        int CantPedidosConfirm = AdminDCompras.getCantComprasAceptadas();

        if (cantClientes == 0 || CantPedidosConfirm == 0) {
            JOptionPane.showMessageDialog(null, "No hay suficientes datos (clientes o compras) para generar el reporte.", "Datos Insuficientes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //calculasmos las estadisticas
        ClienteStats[] statsClientes = new ClienteStats[cantClientes];
        int clienteActivo = 0; //contamos para eltamaño final del repotes
        LocalDateTime Hace1Mes = LocalDateTime.now().minusDays(30);

        for (int i = 0; i < cantClientes; i++) {
            Cliente cliente = TodosClientes[i];
            if (cliente == null) {
                continue;
            }

            ClienteStats stats = new ClienteStats(cliente);
            LocalDateTime ultCompraTemp = null;

            //recorremos todos los pedidos
            for (int j = 0; j < CantPedidosConfirm; j++) {
                CompraAceptada pedido = pedidosConfirmados[j];

                if (pedido != null && pedido.getIdCliente().equalsIgnoreCase(cliente.getId())) {
                    LocalDateTime fechaConfirm = pedido.getFechaConfirm();

                    //actualizamos la ultima compra general
                    if (ultCompraTemp == null || fechaConfirm.isAfter(ultCompraTemp)) {
                        ultCompraTemp = fechaConfirm;
                    }
                    //comprobamos que la compra esta en los ultimos 30 dias
                    if (fechaConfirm.isAfter(Hace1Mes)) {
                        stats.CantComprasMes++;
                        stats.MontoTotalMes += pedido.getTotal();
                    }
                }
            }
            stats.fechaUltCompra = ultCompraTemp;//guradar la ultima compra encotrontada
            //clasfificacion de clientes
            if (stats.CantComprasMes > 0) {
                if (stats.CantComprasMes >= 5) {//5 compras o mas es frecuente
                    stats.Clasificacion = "Frecuente";
                } else if (stats.CantComprasMes >= 2) {//de 2  a 4 es ocuacional
                    stats.Clasificacion = "Ocasional";
                } else {//1 compra o nada es nuevo
                    stats.Clasificacion = "Nuevo";
                }
                statsClientes[clienteActivo++] = stats;
            }
        }
// generar pdf si solo hay clientes activos xdddd VIVA EL CAPITALISMO
        if (clienteActivo == 0) {
            JOptionPane.showMessageDialog(null, "No se encontraron clientes con compras en el ultimo mes", "Sin datos Recientes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            PdfWriter escribir = new PdfWriter(NombreArchiv);
            PdfDocument pdf = new PdfDocument(escribir);
            Document document = new Document(pdf);

            document.add(new Paragraph("Reporte de Clientes Activos (ultimos 30 dias)")
                    .setFontSize(16).setBold().setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Generado el:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .setTextAlignment(TextAlignment.CENTER).setMargin(20));

//ajustamos columnas y anchos
            Table tabla = new Table(UnitValue.createPercentArray(new float[]{15, 30, 20, 15, 10, 10}))
                    .useAllAvailableWidth();

            tabla.addHeaderCell(new Cell().add(new Paragraph("Codigo").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Nombre del Cliete").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Fecha de la ultima compra").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Total gastado (ultimos 30 dias)").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Numero de compras (ultimos 30 dias)").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Clasificacion").setBold()));

            DateTimeFormatter formatoFechaV = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

//interamos solo hasta clientes activos
            for (int i = 0; i < clienteActivo; i++) {
                ClienteStats stat = statsClientes[i];
                if (stat != null && stat.cliente != null) {
                    tabla.addCell(new Cell().add(new Paragraph(stat.cliente.getId())));
                    tabla.addCell(new Cell().add(new Paragraph(stat.cliente.getNombre())));
                    tabla.addCell(new Cell().add(new Paragraph(stat.fechaUltCompra != null ? stat.fechaUltCompra.format(formatoFechaV) : "N/A")));
                    tabla.addCell(new Cell().add(new Paragraph(String.format("Q%.2f", stat.MontoTotalMes))));
                    tabla.addCell(new Cell().add(new Paragraph(String.valueOf(stat.CantComprasMes))));
                    tabla.addCell(new Cell().add(new Paragraph(stat.Clasificacion)));
                }
            }
            document.add(tabla);
            document.close();
            JOptionPane.showMessageDialog(null, "Reporte PDF '" + NombreArchiv + "' generado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte pdf.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void ReporteFinanciero() {
        String NombreArchiv = NombreArchivo("Reportefinanciero");
        CompraAceptada[] pedidosConfirm = AdminDCompras.getListadComprasAceptadas();
        int cantPedidosConfirm = AdminDCompras.getCantComprasAceptadas();

        if (cantPedidosConfirm == 0) {
            JOptionPane.showMessageDialog(null, "No hay pedidos confirmados para generar el reporte.", "Sin Datos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //las estadisticas por cada categoria
        //usmos arreglos separados para cada categoria concida
        CategoriaStats statsTecnologia = new CategoriaStats("TECNOLOGIA");
        CategoriaStats statsAlimento = new CategoriaStats("ALIMENTO");
        CategoriaStats statsOtros = new CategoriaStats("OTROS");
        double IngresosTotales = 0.0;

        for (int i = 0; i < cantPedidosConfirm; i++) {
            CompraAceptada pedido = pedidosConfirm[i];
            if (pedido == null || pedido.getProds() == null || pedido.getCantProductos() <= 0) {
                continue;
            }

            ProdCarrito[] prod = pedido.getProds();
            for (int j = 0; j < pedido.getCantProductos(); j++) {
                ProdCarrito prods = prod[j]; //ENSERIO UNA I ME ESTUVO LEYENDO TODAS ESA BASURA....
                if (prods == null || prods.producto == null) {
                    continue;
                }

                double ingresoProd = prods.cantidad * prods.producto.getPrecio();
                String categoria = prods.producto.getCategoria().toUpperCase(); //pa las mayusculas

                //acumular en cada categoria
                switch (categoria) {
                    case "TECNOLOGIA":
                        statsTecnologia.cantVendida += prods.cantidad;
                        statsTecnologia.IngresoTotal += ingresoProd;
                        break;
                    case "ALIMENTO":
                        statsAlimento.cantVendida += prods.cantidad;
                        statsAlimento.IngresoTotal += ingresoProd;
                        break;
                    case "OTROS":
                        statsOtros.cantVendida += prods.cantidad;
                        statsOtros.IngresoTotal += ingresoProd;
                        break;
                    default:
                        //ignora categorias desconocidas o manejar como error
                        break;
                }
                IngresosTotales += ingresoProd; //sumar al total general
            }
        }
        //calculaos procentajes y promedios
        CategoriaStats[] TodoStats = {statsTecnologia, statsAlimento, statsOtros};
        for (CategoriaStats stats : TodoStats) {
            if (IngresosTotales > 0) {
                stats.ParticipacionPorcent = (stats.IngresoTotal / IngresosTotales) * 100.0;
            } else {
                stats.ParticipacionPorcent = 0.0; //evitamos la divicion por cero 
            }
            if (stats.cantVendida > 0) {
                stats.PromPrecioVenta = stats.IngresoTotal / stats.cantVendida;
            } else {
                stats.ParticipacionPorcent = 0.0; //lo mismo de evitar dividir en cero
            }
        }
        //el jodido pe de efe
        try {
            PdfWriter escribir = new PdfWriter(NombreArchiv);
            PdfDocument pdf = new PdfDocument(escribir);
            Document document = new Document(pdf);

            document.add(new Paragraph("Reporte financiero por categoria")
                    //QUIERO HACER UNA ACLARACION, NO TENGO NI PVTA IDE con maregn de ganacia y de crecimiento mensual... asi que se queda asi alm

                    .setFontSize(16).setBold().setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .setTextAlignment(TextAlignment.CENTER).setMargin(20));

            Table tabla = new Table(UnitValue.createPercentArray(new float[]{25, 20, 20, 15, 20}))//categproa, cantidad vendida, ingresos, porcentaje de cada categoria, y promedio precios
                    .useAllAvailableWidth();

            tabla.addHeaderCell(new Cell().add(new Paragraph("Categoria de cada producto").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Cantidad vendida").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Ingresos totales(Q)").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Porcentaje de participacion").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Promedio de precio de venta(Q)").setBold()));

            //añadimos las filas por cada categoria con sus datos
            for (CategoriaStats stats : TodoStats) {
                //solo añadir si hubo ventas en esa categoria
                if (stats.cantVendida > 0 || stats.IngresoTotal > 0) {
                    tabla.addCell(new Cell().add(new Paragraph(stats.nombreCategoria)));
                    tabla.addCell(new Cell().add(new Paragraph(String.valueOf(stats.cantVendida))));
                    tabla.addCell(new Cell().add(new Paragraph(String.format("%.2f", stats.IngresoTotal))));
                    tabla.addCell(new Cell().add(new Paragraph(String.format("%.1f%%", stats.ParticipacionPorcent))));
                    tabla.addCell(new Cell().add(new Paragraph(String.format("%.2f", stats.PromPrecioVenta))));
                }
            }
            document.add(tabla);
            //resumen generales por si las moscas
            document.add(new Paragraph("\nIngresos totales: Q" + String.format("%.2f", IngresosTotales))
                    .setBold().setTextAlignment(TextAlignment.RIGHT)); // a la derecha alv xd
            document.close();

            JOptionPane.showMessageDialog(null, "Reporte pdf" + NombreArchiv + " generado", "Error", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte pdf", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //para ordenar por dias restantes
    private static void OrdenPorDias(ProductoExpirado[] Orden, int cantidad) {
        //ordenamos por fias resttntes en orden asencedente
        for (int i = 0; i < cantidad - 1; i++) {
            for (int j = 0; j < cantidad - i - 1; j++) {
                if (Orden[j] == null || Orden[j + 1] == null) {
                    continue;
                }
                if (Orden[j].diasRestantes > Orden[j + 1].diasRestantes) {
                    //hacemos cambio
                    ProductoExpirado temp = Orden[j];
                    Orden[j] = Orden[j + 1];
                    Orden[j + 1] = temp;
                }
            }
        }
    }

    public static void ReporteProductoCaducar() {
        String NombreArchiv = NombreArchivo("ProductosPorExpirar");
        Productos[] TodosProductos = AdminDProductos.getListadProductos();
        int cantProductos = AdminDProductos.getCantProducto();

//filtramos y calculasmos los productos de comida xd
        ProductoExpirado[] ProductoAlimento = new ProductoExpirado[cantProductos];
        int cantAlimentos = 0;

        for (int i = 0; i < cantProductos; i++) {
            if (TodosProductos[i] instanceof productoComida) {
                productoComida pCom = (productoComida) TodosProductos[i];
                //clculasmos la info de caudcidada
                ProductoExpirado pEx = new ProductoExpirado(pCom);
                if (!pEx.estadoPriori.equals("OK")) { 
                     ProductoAlimento[cantAlimentos++] = pEx;
                }
            }
        }
        if (cantAlimentos == 0) {
            JOptionPane.showMessageDialog(null, "No se encontraron productos alimenticios próximos a caducar", "Sin datos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        OrdenPorDias(ProductoAlimento, cantAlimentos);

        try {
            PdfWriter escribir = new PdfWriter(NombreArchiv);
            PdfDocument pdf = new PdfDocument(escribir);
            Document document = new Document(pdf);

            document.add(new Paragraph("Reporte de productos por caducar")
                    .setFontSize(16).setBold().setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .setTextAlignment(TextAlignment.CENTER).setMargin(20));
//los anchos y eso
            Table tabla = new Table(UnitValue.createPercentArray(new float[]{12, 25, 12, 10, 8, 13, 20})) //codigo, nombre, expirar, dias restanes stovk y el valor de riesgo en quetzales
                    .useAllAvailableWidth();

            tabla.addHeaderCell(new Cell().add(new Paragraph("Codigo del producto").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Nombre del producto").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("fecha de caducidad").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Dias restanes ").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Cantidad restante").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Valor de riesgo (Q)").setBold()));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Estado/Recomendacion").setBold()));

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (int i = 0; i < cantAlimentos; i++) {
                ProductoExpirado pEx = ProductoAlimento[i];
                if (pEx != null && pEx.producto != null) {
                    LocalDate fechaExp = null;
                    try {
                        fechaExp = LocalDate.parse(pEx.producto.getFechaVencer());
                    } catch (Exception ignored) {
                    }
                    //la tabla
                    tabla.addCell(new Cell().add(new Paragraph(pEx.producto.getCodigo())));
                    tabla.addCell(new Cell().add(new Paragraph(pEx.producto.getNombre())));
                    tabla.addCell(new Cell().add(new Paragraph(fechaExp != null ? fechaExp.format(formatoFecha) : "Error fecha")));
                    tabla.addCell(new Cell().add(new Paragraph(String.valueOf(pEx.diasRestantes))));
                    tabla.addCell(new Cell().add(new Paragraph(String.valueOf(pEx.producto.getStock()))));
                    tabla.addCell(new Cell().add(new Paragraph(String.format("%.2f", pEx.ValorRiesgo))));
                    tabla.addCell(new Cell().add(new Paragraph(String.format(pEx.estadoPriori + " / " + pEx.recomendacion))));
                }
            }
            document.add(tabla);
            document.close();
            JOptionPane.showMessageDialog(null, "Reporte PDF " + NombreArchiv + "generado.", "Reporte generado", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte pdf", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
