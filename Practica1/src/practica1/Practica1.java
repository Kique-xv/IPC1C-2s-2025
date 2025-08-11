//Autor Kique Marroquin y williams marroquin (mi viejo)
package practica1;

import java.util.Scanner;
    public class Practica1 { 
        
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Declaracion de variables
        int escribir;       
     String  [][] personajes = new String [25][8]; // Matriz de personajes
     int cantidadPersonajes = 0; //contador de personajes registrados 
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
                        
                        if (cantidadPersonajes >=25){
                            System.out.println("Ya llegaste al maximo numero de personajes registrados");
                            break;
                        }            
                        scanner.nextLine();
                       System.out.println("Registro de un nuevo personaje");
                       
                       //verificar si el nombre esta repetido
                       String nombre; 
                       boolean Nrepetido = false;
                       do{
                           
                          System.out.println("Ingrese el nombre del personje");
                          nombre =scanner.nextLine();
                          for(int i = 0; i<cantidadPersonajes; i++){
                              if(personajes [i][0] != null && personajes[i][0].equalsIgnoreCase(nombre)){
                                  Nrepetido = true;
                                  System.out.println("Ese nombre ya fue regitrasdo, elige otro nombre");
                                   break;       
                              }
                          }                          
                       } while (Nrepetido);
                           
                       System.out.println("Ingresa el arma de tu preferencia");
                       String arma = scanner.nextLine();
                      
                      System.out.println("Ingresa el nivel de poder (de 0 a 100)");
                      int nivel_pod=scanner.nextInt();
                      scanner.nextLine(); // esto es para limpioar el buffer despues de ller un numero 
                                      
                      // Ingreso de las 5 habilidades
                   String [] habilidades = new String [5];
                   for (int i = 0; i <5; i ++){                      
                        System.out.print("Habilidad #" + (i+1) + ": ");
                       habilidades[i] = scanner.nextLine();
                             }
            //Guardando los personajes en la matriz 
            personajes[cantidadPersonajes][0] =nombre;
            personajes [cantidadPersonajes][1] = arma;
            personajes[cantidadPersonajes][2] =String.valueOf(nivel_pod);
            for (int i =0; i<5; i++){
                personajes [cantidadPersonajes][3+i] = habilidades[i];
            }
           cantidadPersonajes++;                       
           
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
                   if(cantidadPersonajes == 0){
                      System.out.println("No hay ningun personaje registrado... de momento");
                   } else{
                       for (int i = 0; i <cantidadPersonajes; i++){
                           System.out.println("Personaje No."+ ( i+ 1 ) );
                           System.out.println("Nombre" + personajes[i][0]);
                           System.out.println("El arma equipadad es:" + personaje[i][1]);
                           System.out.println("Su nivel de poder es:" + personajes[i][2]);
                           System.out.println("Sus habilidades son:");
                           for (int j = 3; j < 8; j++){
                               System.out.println("-----" + personajes[i][j]);
                           }
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
