// Este proyecto fue hecho por Kique Marroquin y algunas tazas de avena :3
package proyecto1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Proyecto1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Bienvenido al menu principal");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Buscar Producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Registrar Venta");
            System.out.println("5. Generar Reportes");
            System.out.println("6. Ver Datos del Estudiante");
            System.out.println("7. Bitacora de acciones");
            System.out.println("8. Salir");
            System.out.println("Elije una opción:");

            try {
opcion = Validar.VerificarNum(sc);           
        // Consumir el salto de linea restante
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                System.out.println("Error: Debes ingresar un valor numérico. Inténtalo de nuevo.");
               break;
            }

            switch (opcion) {
                case 1:
                    System.out.println("Agregar Productos");
                    break;
                case 2:
                    System.out.println("Buscar Productos");
                    break;
                case 3:
                    System.out.println("Eliminar Productos");
                    break;
                case 4:
                    System.out.println("Registrar Ventas");
                    break;
                case 5:
                    System.out.println("Generar Reportes");
                    break;
                case 6:
                    System.out.println("Ver Datos del Estudiante");
                    System.out.println("este programa fue realizado por Guillermo Enrique Marroquin Morán");
                    System.out.println("Carnet: 202103527");
                    System.out.println("Este programa si tiene Derechos de autor");
                    break;
                case 7:
                    System.out.println("Bitacora de acciones");
                    break;
                case 8:
                    System.out.println("¡Feliz tarde, adiós!");
                    break;
                default:
                    System.out.println("Opción no válida, ingresa otra opción");
            }
        } while (opcion != 8);
        sc.close();
    }
}
