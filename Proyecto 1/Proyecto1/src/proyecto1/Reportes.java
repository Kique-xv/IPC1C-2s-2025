//Okeeeeey.... hice una clase que no estaba en el proyecto y tuve que hacer chanchuyo
//a veces me preocupa lo despistadoo que soy
package proyecto1;

/**
 *
 * @author kiquemarroquin
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

//Liberias de itext7
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
//Las librerias que leen, que encuentran y que hacen excepciones si no encuentran arhicvos de audio 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static proyecto1.ValidarAccion.VerAccion;

//@author kiquemarroquin
public class Reportes {
    //Sip un metodo para generar reportes, (no lo iba a poner en la de productos que desmadre seria)
    //dios que miedo

    public static void generarPDF(String[][] Inventario, int CantInventario, Scanner sc, String vendedor) {
        System.out.println("¿Qué reporte se desea generar?");
        System.out.println("1. Reporte de Stock");
        System.out.println("2. Reporte de ventas");
        System.out.println("3. Bitacora de Acciones");
        System.out.println("Ingresa tu opcion: ");

        try {
            int optRep = sc.nextInt();
            sc.nextLine();

            switch (optRep) {
                case 1:
                    ReporteStock(Inventario, CantInventario, vendedor);
                    break;
                case 2:
                    ReporteVenta(vendedor);
                    break;
                default:
                    System.out.println("Opcion no es valida, intente de nuevo");
                    VerAccion(vendedor, "Generar reporte, opcion no valida ", "fallida");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error 009: Debes de ingresar un numero valido, intente de nuevo");
            sc.nextLine();
            VerAccion(vendedor, "Generar reporte, valor ingresado no valido ", "fallida");
        }
    }

    public static void ReporteStock(String[][] Inventario, int CantInventario, String vendedor) {
        LocalDateTime Hactual = LocalDateTime.now();
        //formato de dias_meses_año, Hora_Minuto_ segundo
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-YYYY_HH_mm_ss");
        //nombramos al archivo :3
        String nomArchi = Hactual.format(formato) + "_Stock.pdf";

        try {
            PdfWriter Esc = new PdfWriter(nomArchi);
            PdfDocument pdf = new PdfDocument(Esc);
            Document docu = new Document(pdf);
            docu.add(new Paragraph("Reporte de Stock - " + Hactual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
//Creamos celdas con el el atritibuto de cada producto
            Table tabla = new Table(new float[]{1, 2, 2, 1, 1});
            tabla.addCell("Codigo");
            tabla.addCell("Nombre");
            tabla.addCell("Categoría");
            tabla.addCell("Precio");
            tabla.addCell("Stock");

//LLenamos las celdas recorriendo con el ciclo for, lo productos con sus atributos registrados           
            for (int i = 0; i < CantInventario; i++) {
                if (Inventario[i][0] != null) {
                    tabla.addCell(Inventario[i][0]);
                    tabla.addCell(Inventario[i][1]);
                    tabla.addCell(Inventario[i][2]);
                    tabla.addCell("Q. " + Inventario[i][3]);
                    tabla.addCell(Inventario[i][4]);
                }
            }
            docu.add(tabla);
            docu.close();
            //Se hace aviso de que el reporte si se creo, o bueno eso espero que funcione
            System.out.println("El reporte de stock fue generado en :" + nomArchi);
            VerAccion(vendedor, "Generar reporte de stock ", "Correcta");
        } catch (FileNotFoundException e) {
            System.out.println("Error 010: no se pudo crear el archivo:");
            VerAccion(vendedor, "Generar reporte de stock , no se encontro el archivo", "fallida");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error 011: ocurrio un problema al general el reporte");
            e.printStackTrace();
            VerAccion(vendedor, "Generar reporte de stock ", "fallida");

        }
    }

    //Una funcion para el reporte de ventas, asi mejor, y no se ve todo desordenado
    public static void ReporteVenta(String vendedor) {
        LocalDateTime HFactual = LocalDateTime.now();
        //ya lo dije arriba :P
        DateTimeFormatter HFormato = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        //nombramos al otro archivo
        String nomArchiv = HFactual.format(HFormato) + "_Ventas.pdf";

        try {
            // se ordeno muy feo... asi que se vaya 
            // aca declaro las variables de escribir en el archivo, de documento, de lectura y escrituta en archivo de texto
            PdfWriter Escribir = new PdfWriter(nomArchiv);
            PdfDocument pdf = new PdfDocument(Escribir);
            Document documento = new Document(pdf);
            Scanner Fs = new Scanner(new FileReader("Venta.txt"));
            {
            }
            documento.add(new Paragraph("Historial de Ventas - " + HFactual.format(DateTimeFormatter.ofPattern("dd7mm/yyyy HH:mm:ss"))));
            while (Fs.hasNextLine()) {
                documento.add(new Paragraph(Fs.nextLine()));
            }
            documento.close();
            System.out.println("Reporte de venta generdado en : " + nomArchiv);
            VerAccion(vendedor, "Generar reporte de ventas ", "Correcta");
        } catch (FileNotFoundException e) {
            System.out.println("Error 012: el archivo de ventas.txt no se encontró, no hay ventas por reportar");
            VerAccion(vendedor, "Generar reporte de venta, no se encontro el archivo ", "fallida");
        } catch (IOException e) {
            System.out.println("Error 013: ocurrio un error de lectura o escritura");
            VerAccion(vendedor, "Generar reporte de ventas ", "fallida");
            e.printStackTrace();
        }
    }

    public static void ReporteHist(String vendedor) {
        LocalDateTime HFactual = LocalDateTime.now();
        DateTimeFormatter HFormato = DateTimeFormatter.ofPattern("dd_MM_YYYY_HH:mm:ss");

        String nomArchiv = HFactual.format(HFormato) + "_Bitacora.pdf";
        try {
            PdfWriter Escribir = new PdfWriter(nomArchiv);
            PdfDocument pdf = new PdfDocument(Escribir);
            Document Doc = new Document(pdf);
            Scanner Fs = new Scanner(new FileReader("Bitacora.txt"));
            {
            }
            Doc.add(new Paragraph("Bitacora de aciones- " + HFactual.format(DateTimeFormatter.ofPattern("dd_MM_YYYYHH:mm:ss"))));
            while (Fs.hasNextLine()) {
                Doc.add(new Paragraph(Fs.nextLine()));
            }
            Doc.close();
            System.out.println("Bitacora de acciones generada en :" + nomArchiv);
            VerAccion(vendedor, "Generar Bitacora en pdf", "Correcta");
        } catch (FileNotFoundException e) {
            System.out.println("Error 014: el archivo de bitacora no fue encontrado :");
            VerAccion(vendedor, "Generar Bitacora en pdf ", "fallida");
        } catch (IOException e) {
            System.out.println("Error 015: Algo fallo que cosa ps no se");
            VerAccion(vendedor, "Generar Bitacora en pdf", "fallida");
            e.printStackTrace();
        }
    }
}
