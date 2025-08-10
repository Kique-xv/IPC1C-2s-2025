//Autor Kique Marroquin y williams marroquin (mi viejo)
package practica1;

import java.util.ArrayList;
import java.util.Scanner;

    public class Practica1 { 
        
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int escribir;
        ArrayList<Personaje> personajes = new ArrayList<>();
       String continuar;
    
        do{  
           // Deplegar el  Menu principal 
           
           System.out.println("Menu principal");
            System.out.println("1. Agregar Personaje");
            System.out.println("2. Modificar Personaje ");
            System.out.println("3. Eliminar Personaje");
            System.out.println("4. Ver Datos de un Personaje");
            System.out.println("5. Ver Listado de Personaje");
            System.out.println("6. Realizar pelea entre Personajes");
            System.out.println("7. Ver Historial de peleas");
            System.out.println("8. Ver datos de Estudiante");
            System.out.println("9. Salir");
            System.out.print("Elige una opción: ");
            
escribir = scanner.nextInt();
//Ejecutar segun las opciones que se ponga en la consola 
 switch (escribir) {
                case 1:
                    do{
                       System.out.println("Registro de un nuevo personaje");
                        scanner.nextLine();
                        
                       System.out.println("Ingresa el nombre");
                       String nombre = scanner.nextLine();
                    
                    // ver si el personaje esta repetido
                    boolean Nomrepetido = false;
                    for (Personaje Pexiste : personajes);
                    
                       
                       System.out.println("Ingresa el arma de tu preferencia");
                       String arma = scanner.nextLine();
                      
                      System.out.println("Ingresa el nivel de poder (de 0 a 100)");
                      int nivel_pod=scanner.nextInt();
                      scanner.nextLine(); // esto es para limpioar el buffer despues de ller un numero 
                    
                      // Ingreso de las 5 habilidades
                     String [] habilidades = new String[5];
            System.out.println("Ingresa 5 habilidades del personaje:");
            for (int i = 0; i < 5; i++) {
                System.out.print("  Habilidad #" + (i + 1) + ": ");
                habilidades[i]= scanner.nextLine();
                
                    }
                                  // Crear y agregar el personaje
            Personaje nuevo = new Personaje (nombre , arma, nivel_pod, habilidades);
            personajes.add(nuevo);

            // Preguntar si quiere continuar
            System.out.print("\n¿Deseas ingresar otro personaje? (s/n): ");
            continuar = scanner.nextLine().toLowerCase();
                    } while (continuar.equals("s"));        
                          break;
           case 2:
                    System.out.println("Modificar atributos del Personaje" );
                    
                    
                    break;
                case 3:
                      System.out.println("Elimina un personaje");
        break;
                case 4:
                    System.out.println("Ver atributos del personaje :");
                    break;
                case 5:
                    System.out.println("Listado de de personajes");
                    System.out.println("PERSONAJES REGISTRADOS ");
        for (int i = 0; i < personajes.size(); i++) {
            Personaje p = personajes.get(i);
            System.out.println("\nPersonaje #" + (i + 1));
            System.out.println("Nombre: " + p.nombre);
            System.out.println("Arma: " + p.arma);
            System.out.println("Nivel de Poder: " + p.nivel_pod);
            System.out.println("Habilidades:");
            for (String habilidades : p.habilidades) {
                System.out.println("  - " + habilidades);
                scanner.close();
            }
        }
                    break;
                case 6:
                    System.out.println("RRealizar combate");
                    break;
                case 7:
                    System.out.println("Ver historial de combates");
                    break;
                case 8:
                    System.out.println("Este programa fue hecho por Guillermo Enrique Marroquin Morán");
                    System.out.println("202103527");
                    System.out.println("Este programa tiene derechos de autor o no...");
                    break;
                case 9:
                    System.out.println("Adios");
                    break;
                default:
                    System.out.println("!Esa opcion no existe!, ingresa otra opción.");
            }
      } while (escribir != 9); // El menú se repite hasta que el usuario elija salir

        scanner.close();
               
    }
}
