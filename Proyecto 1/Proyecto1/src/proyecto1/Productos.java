package proyecto1;
//Una clase aledaña para posiblemente buscar, eliminar y otras

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
//Una funcion para registrar una venta, usando como base la parte de la parctica de buscar por nombre

    public static void RegisVenta(String[][] Inventario, int CantInventario, Scanner sc) {
        int Prod = -1;
        System.out.println("Ingrese el codigo del producto a vender: ");
        String CodVenta = sc.nextLine();
        for (int i = 0; i<CantInventario; i++) { // Buscmos en el inventario por el codigo con un ciclo for
            if (Inventario[i][0] != null && Inventario[i][0].equalsIgnoreCase(CodVenta)) {
                Prod = i;
                break;
            }
        }
        if (Prod != -1) {// Esto es porque si  en dado caso el indice a buscar es -1, por la posicion -1 de la matriz esto no va afuncionar 
            mostrarProd(Inventario, Prod);

            System.out.println("Ingrese la cantidad del produco que se va a vender");
            int cantidad = Validar.verNumPos(sc);
            int Stock2 = Integer.parseInt(Inventario[Prod][4]);

            //Validadmos que la venta sea exitosa
            if (cantidad > Stock2) {
                System.out.println("Error 006: La cantidad a vender es superior al stock");
                return;
            }
            // Actualizamos el nuevo stock con la cantidad vendida
            int Stock3 = Stock2 - cantidad;
            Inventario[Prod][4] = String.valueOf(Stock3);

            //calciulamos el total de la venta :3
            double precio = Double.parseDouble(Inventario[Prod][3]);
            double Total = precio * cantidad;

            //esto es para la fecha y hora de la ventas yuju :3
            LocalDateTime actual = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("DD-MM-YYYY HH-:mm:ss");
            String FechaHora = actual.format(formato);

            // Registramos la venta en un archibo de texto
            try {
                FileWriter fw = new FileWriter("Venta.txt", true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println("--------------------");
                pw.println("Ventas Efectuadas: ");
                pw.println("Hora y Fecha de la transacción: " + FechaHora);
                pw.println("Producto vendido: " + Inventario[Prod][1]);
                pw.println("Cantidad vendida del producto es : " + cantidad);
                pw.println("el total a pagar es de: " + Total + " Qeutzales");
                pw.println("----------------------");
                pw.close();
            } catch (IOException e) {
                System.out.println("Error 007: ocurrio un error al registrar la venta en el archivo");
                e.printStackTrace();
            }
            System.out.println("La venta fue efectuada");
            System.out.println("Fueron vendidas: " + cantidad + " unidades de :" + Inventario[Prod][1]);
            System.out.println("La cantidad actual del producto es: " + Stock3);
            System.out.println("La cantidad a pagar es :" + Total + "Quetzales");
        } else {
            System.out.println("Error 008: el producto con el codigo : " + CodVenta + " No fue encontrado");
        }
    }  
}
