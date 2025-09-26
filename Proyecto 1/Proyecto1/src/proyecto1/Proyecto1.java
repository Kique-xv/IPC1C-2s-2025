// Este proyecto fue hecho por Kique Marroquin y algunas tazas de avena :3
package proyecto1;

import java.util.InputMismatchException;
import java.util.Scanner;
//Las Importaciones de las clases que hice por aparte
import static proyecto1.Productos.EliminarProd;
import static proyecto1.Productos.RegisVenta; // aca vienen tambien las liberias de formatos y la de la excepcion
import static proyecto1.Productos.buscarProducto;//Lo importe porque... si ponia el codigo esto se haria muy largo :(
import static proyecto1.Reportes.ReporteHist;
import static proyecto1.Reportes.generarPDF;//ahoa si xddd
import static proyecto1.ValidarAccion.LimpHistorial;// para limpiar el historial
import static proyecto1.ValidarAccion.MostarAcciones;//Esto es para  ver la bitacora
import static proyecto1.ValidarAccion.VerAccion;// y esto es para la funcion de la bitacora

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
        String vendedor = "Guillermo Marroquin";
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
                sc.nextLine();
                // Consumir el salto de linea restante
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                System.out.println("Error 001: Debes ingresar un valor numérico. Inténtalo de nuevo.");
                sc.nextLine();
                VerAccion(vendedor, "Ingreso de opcion al menu", "fallida");
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

                        System.out.println("Precio del producto(Q):");
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
                        System.out.println();
                    } while (continuar.equals("s"));
                    VerAccion(vendedor, "Agregar producto", "Correcta");
                    break;
                case 2:
                    System.out.println("Buscar Productos:");
                    if (CantInventario <= 0) {
                        System.out.println("No hay productos registrados");
                        System.out.println();
                        VerAccion(vendedor, "Buscar productos ", "Fallida");
                        break;
                    }
                    System.out.println();
                    //Wuu hice una importacion de una clase que hice yey...
                    buscarProducto(Inventario, CantInventario, sc, vendedor);
                    VerAccion(vendedor, " Buscar Producto producto", "Correcta");
                    break;
                case 3:
                    System.out.println("Eliminar Productos");
                    System.out.println();

                    do {
                        if (CantInventario > 0) {
                            CantInventario = EliminarProd(Inventario, CantInventario, sc, vendedor);
                        } else {
                            System.out.println("No hay productos para eliminar");
                            System.out.println();
                            VerAccion(vendedor, "Agregar producto", "fallida");
                        }
                        System.out.print("¿Desea eliminar otro producto? (s/n):  ");
                        continuar = sc.nextLine().toLowerCase();
                    } while (continuar.equals("s"));
                    break;
                case 4:
                    System.out.println("Registrar Ventas");
                    if (CantInventario > 0) {
                        RegisVenta(Inventario, CantInventario, sc, vendedor);
                    } else {
                        System.out.println("No hay productos en inventario");
                        VerAccion(vendedor, "Registrar  Venta", "fallida");
                    }
                    VerAccion(vendedor, "Registrar venta  ", "Correcta");
                    break;
                case 5:
                    System.out.println("Generar Reportes");
                    sc.nextLine();
                    if (CantInventario <= 0) {
                        System.out.println("No hay productos en inventario para generar un reporte");
                        VerAccion(vendedor, "Generar Reportes ", "fallida");
                    } else {
                        generarPDF(Inventario, CantInventario, sc, vendedor);
                    }
                    VerAccion(vendedor, " Registrar Venta", "Correcta");
                    break;
                case 6:
                    System.out.println("Ver Datos del Estudiante"); // esto de los saltos de linea me sirve para ahorrar espacio
                    System.out.println("Este programa fue realizado por Guillermo Enrique Marroquin Morán. \nCarnet: 202103527. \nEste programa si tiene Derechos de autor");
                    System.out.println();
                    VerAccion(vendedor, "Ver datos del estudiante", "Correcta");// como chuccha podria NO ver mis datos....
                    break;
                case 7:
                    System.out.println("Bievenido a las opciones de la bitaciora de acciones");
                    System.out.println("1. Ver bitacora de acciones : ");
                    System.out.println("2. Limpiar bitacora e historial de ventas : ");
                    System.out.println("3. Bitacora en pdf");
                    System.out.println("Elije una opcion: ");
                    try {
                        int optBit = Validar.VerificarNum(sc);
                        sc.nextLine();

                        switch (optBit) {
                            case 1:
                                MostarAcciones();
                                VerAccion(vendedor, "Ver bitacora de acciones", "Correcta");
                                break;
                            case 2:
                                System.out.print("¿Esta seguro de eliminar el historial? (s/n)");
                                String confirm = sc.nextLine().toLowerCase();
                                if (confirm.equals("s")) {
                                    LimpHistorial();
                                    VerAccion(vendedor, "Limpiar historial de de acciones y ventas", "Correcta");
                                } else {
                                    System.out.println("No se realizo la limpieza");
                                }
                                break;
                            case 3:
                                ReporteHist(vendedor);
                                break;
                            default:
                                System.out.println("Error 016: Opcion no valida");
                                VerAccion(vendedor, "Opcion de bitacora, valor no valido", "fallida");
                                break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Error 017: Ingrese un numero, intentelo otra vez");
                        sc.nextLine();
                        VerAccion(vendedor, "Opcion de bitacora, valor no valido", "fallida");
                    }
                    break;
                case 8:
                    System.out.println("¡Feliz tarde, adiós!");
                    VerAccion(vendedor, "Salir del programa", "Correcta");
                    break;
                default:
                    System.out.println("Opción no válida, ingresa otra opción");
                    System.out.println();
                    VerAccion(vendedor, "Ingreso de opcion del menu ", "Fallida");
            }
        } while (opcion != 8);
        sc.close();
    }
}
