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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//@author kiquemarroquin
public class Reportes {
    //Sip un metodo para generar reportes, (no lo iba a poner en la de productos que desmadre seria)
    //dios que miedo

    public static void generarPDF(String[][] Inventario, int CantInventario, Scanner sc) {
        System.out.println("¿Qué reporte se desea generar?");
        System.out.println("1. Reporte de Stock");
        System.out.println("2. Reporte de ventas");
        System.out.println("Ingresa tu opcion: ");

        try {
            int optRep = sc.nextInt();
            sc.nextLine();

            switch (optRep) {
                case 1:
                    ReporteStock(Inventario, CantInventario);
                    break;
                case 2:
                    ReporteVenta();
                    break;
                default:
                    System.out.println("Opcion no es valida, intente de nuevo");

            }
        } catch (InputMismatchException e) {
            System.out.println("Error 009: Debes de ingresar un numero valido, intente de nuevo");
            sc.nextLine();
        }
    }

    public static void ReporteStock(String[][] Inventario, int CantInventario) {
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
        } catch (FileNotFoundException e) {
            System.out.println("Error 010: no se pudo crear el archivo:");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error 011: ocurrio un problema al general el reporte");
            e.printStackTrace();
        }
    }

    //Una funcion para el reporte de ventas, asi mejor, y no se ve todo desordenado
    public static void ReporteVenta() {
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
        } catch (FileNotFoundException e) {
            System.out.println("Error 012: el archivo de ventas.txt no se encontró, no hay ventas por reportar");
        } catch (IOException e) {
            System.out.println("Error 013: ocurrio un error de lectura o escritura");
            e.printStackTrace();
        }
    }
}
