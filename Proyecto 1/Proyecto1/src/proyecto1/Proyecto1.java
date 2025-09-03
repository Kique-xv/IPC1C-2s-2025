// Este proyecto fue hecho por Kique Marroquin y algunas tazas de avena :3
package proyecto1;

import java.util.InputMismatchException;
import java.util.Scanner;
import static proyecto1.Productos.buscarProducto;//Lo importe porque... si ponia el codigo esto se haria muy largo :(

public class Proyecto1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        //Declaracion de la matriz del oinventario y continuar
        final int MAXPROD = 100;
        final int ATRI = 5;
        String[][] Inventario = new String[MAXPROD][ATRI];
        int CantInventario = 0;
        String continuar;
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
                System.out.println("Error 001: Debes ingresar un valor numérico. Inténtalo de nuevo.");
                break;
            }
            switch (opcion) {
                case 1:
                    do {
                        if (CantInventario >= MAXPROD) {
                            System.out.println("Haz llegado al limite de productos ingresados");
                            break;
                        }
                        sc.nextLine();
                        System.out.println("Bienvenido al apartado de agregar productos");
                        System.out.println();

                        System.out.println("Ingrese el nombre del vendedor:");
                        String vendedor = sc.nextLine();

                        String codigo;
                        boolean CodRep;
                        do {
                            CodRep = false;  //Validamos que el codigo sea unico
                            System.out.println("Ingrese el codigo del producto:");
                            codigo = sc.nextLine();
                            for (int i = 0; i < CantInventario; i++) {
                                if (Inventario[i][0] != null && Inventario[i][0].equalsIgnoreCase(codigo)) {
                                    CodRep = true;
                                    System.out.println("Ese codigo ya fue ingresado, favor de ingresar otro");
                                    break;
                                }
                            }
                        } while (CodRep);

                        System.out.print("Nombre del Producto:");
                        String nombre = sc.nextLine();

                        //yay hice un llamado a un metodo wuuuuu
                        String categoria = Validar.validarCategoria(sc);

                        System.out.println("Precio del producto:");
                        int precio = Validar.verNumPos(sc);

                        System.out.println("Cantidad en Stock:");
                        int stock = Validar.verNumPos(sc);

                        Inventario[CantInventario][0] = codigo;
                        Inventario[CantInventario][1] = nombre;
                        Inventario[CantInventario][2] = categoria;
                        Inventario[CantInventario][3] = String.valueOf(precio);
                        Inventario[CantInventario][4] = String.valueOf(stock);
                        CantInventario++;

                        System.out.print("¿Desea ingresar otro producto? (s/n):  ");
                        continuar = sc.nextLine().toLowerCase();
                    } while (continuar.equals("s"));
                    break;
                case 2:
                    System.out.println("Buscar Productos:");
                    if (CantInventario <= 0) {
                        System.out.println("No hay productos registrados");
                        System.out.println();
                        break;
                    }
                    System.out.println();
                    //Wuu hice una importacion de una clase que hice yey...
                    buscarProducto(Inventario, CantInventario, sc);
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
                    System.out.println("Ver Datos del Estudiante"); // esto de los saltos de linea me sirve para ahorrar espacio
                    System.out.println("Este programa fue realizado por Guillermo Enrique Marroquin Morán. \nCarnet: 202103527. \nEste programa si tiene Derechos de autor");
                    System.out.println();
                    break;
                case 7:
                    System.out.println("Bitacora de acciones");
                    break;
                case 8:
                    System.out.println("¡Feliz tarde, adiós!");
                    break;
                default:
                    System.out.println("Opción no válida, ingresa otra opción");
                    System.out.println();
            }
        } while (opcion != 8);
        sc.close();
    }
}
