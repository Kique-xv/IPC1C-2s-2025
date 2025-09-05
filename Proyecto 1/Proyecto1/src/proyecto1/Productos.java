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
                for (int i = 0; i < CantInventario; i++) {
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
    public static void mostrarProd(String[][] Inventario, int i) {
        System.out.println("Se ha encontrado el producto");
        System.out.println("Codigo: " + Inventario[i][0]);
        System.out.println("Nombre: " + Inventario[i][1]);
        System.out.println("Categoria: " + Inventario[i][2]);
        System.out.println("Precio: " + Inventario[i][3]);
        System.out.println("Stock: " + Inventario[i][4]);
    }
//Sip un Metodo que sea para buscar productos exclusivamante para eliminarlos, me base mucho en la de la practica yeeey

    public static int EliminarProd(String[][] Inventario, int CantInventario, Scanner sc) {
        System.out.println("Ingrese el Codigo del producto que se quiera eliminar:  ");
        String codEli = sc.nextLine();

        int Eliminar = -1;
        //Usamos un bucle for para buscar en la matriz inventario para encontrar el producto
        for (int i = 0; i < CantInventario; i++) {
            if (Inventario[i][0] != null && Inventario[i][0].equalsIgnoreCase(codEli)) {
                Eliminar = i;
                break;
            }
        }
//Si el producto fue encontrado 
        if (Eliminar != -1) {
            System.out.println("Se encontro el siguiente producto: ");
            //Hacemos un llamado para mostrar el producto a eliminar 
            mostrarProd(Inventario, Eliminar);
            System.out.println();
            System.out.println("¿Esta seguro de la eliminacion del producto (s/n): ");
            String Confirmar = sc.nextLine().toLowerCase();

            if (Confirmar.equals("s")) {
                //Eliminamos la posicion donde se encuentre el producto
                for (int i = Eliminar; i < CantInventario - 1; i++) {
                    Inventario[i] = Inventario[i + 1];
                }
//Este bloquecito de codigo bonito es para "limpiar" la ultima fila para que no haya duplicaciones
                Inventario[CantInventario - 1] = new String[5];
                System.out.println("El producto fue eliminado ");
                CantInventario--;//baja el "contenido" del inventario una unidad UWU
            } else {
                System.out.println("El producto no fue eliminado ");
            }
        } else {
            System.out.println("El producto: " + codEli + "no fue encontrado");
        }
        return CantInventario;//devolvemos el valor de la nueva matriz de inventario
    }
}
