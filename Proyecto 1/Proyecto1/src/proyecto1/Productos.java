package proyecto1;
//Una clase aledaña para posiblemente buscar, eliminar y otras

import java.util.InputMismatchException; // tambien esta libreria...
import java.util.Scanner;// debe de haber una forma para que no tenga que importar siempre esta libreria...

public class Productos {
//una funcion o metodo para buscar productos, yyy sip me base muuucho en la de la practica xd

    public static void buscarProducto(String[][] Inventario, int CantInventario, Scanner sc) {
        int optBuscar;
        String valBuscar;
        boolean encontrar = false;
        System.out.println("Bienvenido a la seccion de busqueda de Productos ");
        System.out.println();//sip, un salto de linea pa que se vea mejor
        System.out.println("¿Como desea buscar el producto'");
        System.out.println("1. Por Codigo");
        System.out.println("2. Por Nombre");
        System.out.println("3. Por Categoria");
        System.out.println("Ingrese su opcion:");
        try {
            optBuscar = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Error  006: Debe de ingresar un numero valido, intente de nuevo");
            sc.nextLine();
            return;
        }
        switch (optBuscar) {
            case 1:
                System.out.println("Ingrese el codigo a buscar:");
                valBuscar = sc.nextLine();
                for (int i = 0; i < CantInventario; i++) {
                    if (Inventario[i][0] != null && Inventario[i][0].equalsIgnoreCase(valBuscar)) {
                        mostrarProd(Inventario, i);
                        encontrar = true;
                        break;
                    }
                }
                break;
            case 2:
                System.out.println("Ingrese el nombre a buscar:");
                valBuscar = sc.nextLine();
                for (int i = 0; i < CantInventario; i++) {
                    //esto va a ingnorar las mayusculas y minusculas, solo toma las palabras
                    if (Inventario[i][1] != null && Inventario[i][1].toLowerCase().contains(valBuscar.toLowerCase())) {
                        mostrarProd(Inventario, i);
                        encontrar = true;
                    }
                }
                break;
            case 3:
                System.out.println("Ingrese la categoria a buscar:");
                valBuscar = sc.nextLine();
                for (int  i = 0; i < CantInventario; i++ ) {
                    //Igual aca, solo que.. bueno hace una equivalencia entre los productos ingresado y los que encuentre, solo que ignora las mayusculas
        if (Inventario[i][2] != null && Inventario[i][2].toLowerCase().equals(valBuscar.toLowerCase())) {
                        mostrarProd(Inventario, i);
                        encontrar = true;
                    }
                }
                break;
            default:
                System.out.println("Opcion no valida, intente otra vez");
                return;
        }
        if (!encontrar) {
            System.out.println("No se encontraron productos que coincidan con la busqueda");

        }
    }

    //Este metodo ayuda a visualizar los porductos, igual me base de la practica... sip mi creatividad llego a 0
    public static void mostrarProd(String[][] Inventario, int indice) {
        System.out.println("Se ha encontrado el producto");
        System.out.println("Codigo: " + Inventario[indice][0]);
        System.out.println("Nombre: " + Inventario[indice][1]);
        System.out.println("Categoria: " + Inventario[indice][2]);
        System.out.println("Precio: " + Inventario[indice][3]);
        System.out.println("Stock: " + Inventario[indice][4]);
    }
}
