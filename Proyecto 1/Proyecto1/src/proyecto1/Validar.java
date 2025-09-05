package proyecto1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validar {
//metodo que verifica que solo hayan numeros en las opcones y posiblemente en muchas otras opciones

    public static int VerificarNum(Scanner sc) throws InputMismatchException {
        if (!sc.hasNextInt()) {
            throw new InputMismatchException("Error 002: Por favor ingresa un numero del 1 al 8");
        }
        return sc.nextInt();
    }
    //Un nuevo metodo para ver si la cantidad de estock y lo otro sea positivos, o en general numeros vaya
    public static int verNumPos(Scanner sc) {
        int num;
        while (true) {
            try {
                num = sc.nextInt();
                if (num < 0) {
                    System.out.println("Error 003: por favor ingrese un valor positivo");
                    System.out.println("Por favor elija un numero postivo");
                    System.out.println();

                } else {
                    sc.nextLine();
                    return num;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error 004: por favor ingrese un valor numerico que sea valido");
                sc.nextLine();
            }
        }
    }
//sip esto cuenta como una funcion para validar algo, aparte... que feo se veria un codigo chapucero
    public static String validarCategoria(Scanner sc) {
        int optCatego;
        do {
            System.out.println("Selecione la categoria a la que pertenezca el producto:");
            System.out.println("1. Camisa");
            System.out.println("2. Pantalón");
            System.out.println("3. Accesorio");
            System.out.println("Ingrese la opcion:");
            try {
                optCatego = sc.nextInt();
                sc.nextLine();
                switch (optCatego) {
                    case 1:
                        return "Camisa";
                    case 2:
                        return "Pantalón";
                    case 3:
                        return "Accesorio";
                    default:
                        System.out.println("Opcion no valida, por favor, elige un numero del a al 3");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error 005: Debes de ingresar un numero, intentelo de nuevo");
                sc.nextLine();
            }
        } while (true);
    }
}
