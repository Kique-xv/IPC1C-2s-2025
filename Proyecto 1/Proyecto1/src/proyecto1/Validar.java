package proyecto1;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Validar {
//metodo que verifica que solo hayan numeros en las opcones y posiblemente en muchas otras opciones
    public static int VerificarNum(Scanner sc) throws InputMismatchException {
        if (! sc.hasNextInt()) {
            throw new InputMismatchException("Error: Por favor ingresa un numero del 1 al 8");
        }
        return sc.nextInt();
    }
    //Un nuevo metodo para ver si la cantidad de estock y lo otro sea positivos, o en general numeros vaya
public static int verNumPos(Scanner sc){
int num;
while(true){
try {
num =sc.nextInt();
if(num<0){
System.out.println("Error: por favor ingrese un valor positivo");
} else{
sc.nextLine();
return num;
}
 } catch(InputMismatchException e){
System.out.println("Error: por favor ingrese un valor numerico que sea valido");
sc.nextLine();
}
   }
   }
}