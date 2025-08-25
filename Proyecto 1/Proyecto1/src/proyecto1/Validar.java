package proyecto1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validar {

    public static int VerificarNum(Scanner sc) throws InputMismatchException {
        if (! sc.hasNextInt()) {
            throw new InputMismatchException("Error: Por favor ingresa un numero del 1 al 8");
        }
        return sc.nextInt();
    }
}
