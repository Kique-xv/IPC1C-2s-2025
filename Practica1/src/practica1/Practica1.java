//Autor Kique Marroquin y williams marroquin (mi viejo)
package practica1;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;
public class Practica1 { 
        
         // el modulo de buscaer personajes, este se usa en las opciones 2,3,4,5,6 y 7, waw que monton
    public static int buscarPer(String[][] personajes, int cantidad, String nombre) {
        for (int i = 0; i < cantidad; i++) {
            if (personajes[i][0] != null && personajes[i][0].equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1; // esto indica que si el personaje no se encuentra va a retornar un -1
    }
      //el modulo de mostrar personaje, se usa en las opciones 2,4,5 y posiblemente 7
    public static void mostrarPer(String [][] personajes, int Per){
        System.out.println("Personaje");
        System.out.println("nombre:"+ personajes[Per][0]);
        System.out.println("arma:"+ personajes[Per][1]);
        System.out.println("nivel de poder:"+ personajes[Per][2]);
        System.out.println("habilidades:");
        for (int j =3; j<8; j++){
            System.out.println("nombre:"+ personajes[Per][j]);
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Declaramos las variables
        int escribir;       
     String  [][] personajes = new String [25][8]; // Matriz de personajes
     int cantidadPer = 0; //esto es el contador de personajes registrados 
     
     String [] historialCo = new String [50]; //para guardar en matriz hasta 50 madrazos 
     int cantPeleas = 0;
     String continuar;   
     
     Random random = new Random();
     
     
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
//Ejecutar segun las opciones que se ponga en la consola, pudo haber sido if´s, pero es mas practico switch
 switch (escribir) {
                case 1:
                    do{
                        
                        if (cantidadPer >=25){
                            System.out.println("Ya llegaste al maximo numero de personajes registrados");
                            break;
                        }            
                        scanner.nextLine();
                       System.out.println("Registro de un nuevo personaje");
                       
                       //verificamos si el nombre esta repetido o no
                       String nombre; 
                       boolean Nrepetido = false;
                       do{
                           
                          System.out.println("Ingrese el nombre del personje");
                          nombre =scanner.nextLine();
                          for(int i = 0; i<cantidadPer; i++){
                              //el equals es para "ignorar" maysuculas y minusculas del nombre en cuestion
                              if(personajes [i][0] != null && personajes[i][0].equalsIgnoreCase(nombre)){
                                  Nrepetido = true;
                                  System.out.println("Ese nombre ya fue regitrasdo, elige otro nombre");
                                   break;       
                              }
                          }                          
                       } while (Nrepetido);
                           
                       System.out.println("Ingresa el arma de tu preferencia");
                       String arma = scanner.nextLine();
                      
                       int nivel_pod=0;
                      
                       do{                        
                      System.out.println("Ingresa el nivel de poder (de 0 a 100)");                   
                      scanner.nextLine(); // esto es para "comerse" el enter despues de leer un numero 
                    }    while (nivel_pod < 0 || nivel_pod > 100); //esto es para solo registrar los numeros de 0 a 100
                                       
                      // Ingreso de las 5 habilidades
                   String [] habilidades = new String [5];// esta linea me trajo problemas y muchos 
                   for (int i = 0; i <5; i ++){                      
                        System.out.print("Habilidad #" + (i+1) + ": ");
                       habilidades[i] = scanner.nextLine();
                             }
            //Se van a guardar los personajes en una matriz de 25*8
            personajes[cantidadPer][0] =nombre;
            personajes [cantidadPer][1] = arma;
            personajes[cantidadPer][2] =String.valueOf(nivel_pod);
            for (int i =0; i<5; i++){
                personajes [cantidadPer][3+i] = habilidades[i];
            }
           cantidadPer++;                       
           
            // pregunta si quieres continuar
            System.out.print("¿Deseas ingresar otro personaje? (s/n): ");
           continuar = scanner.nextLine().toLowerCase();
                    } while (continuar.equals("s"));        
                          break;
           case 2:
                    System.out.println("Modificar atributos del Personaje" );
                    scanner.nextLine();
                  
                    System.out.println("Ingresa el nombre del personaje a quien quieras modificar");
                    String nombreMod = scanner.nextLine();
                    int Mod = buscarPer(personajes, cantidadPer, nombreMod);//llamado del modulo de buscar personajes
                   
                    if (Mod ==-1){// eso es porque si al buscar el personaje, el modulo retornara un -1 y eso en una matriz no existe
                        System.out.println("Ese personaje no existe, ingresa otro nombre");
break;                        
                    }
                    else { // se mostraran los datos iniciales, igual como en la opcion 4 y 5 
                        System.out.println("Estos son los datos actuales del personaje");
                        mostrarPer(personajes, Mod); // llamado del modulo de mostrar personajes
                        
                        System.out.println("Ingresa un nuevo nombre");
                        personajes[Mod][0] = scanner.nextLine();
                        
                        System.out.println("Elige un arma nueva");
                        personajes[Mod][1] = scanner.nextLine();
                        
                       int nuevoNiv;
                       do {
                           System.out.println("Ingresa un nuevo nivel de poder");
                           nuevoNiv = scanner.nextInt();
                           scanner.nextLine();
                       } while (nuevoNiv<0 || nuevoNiv > 100 );
                       personajes [Mod][2] = String.valueOf(nuevoNiv);
                       
                       for (int i = 0; i <5; i++ ){
                           System.out.println("Nueva habilidad #"+ (i+1) +":");
                           personajes[Mod][3+i]= scanner.nextLine();
                       } break;
                    }
                case 3:
                      System.out.println("Elimina un personaje");
                    scanner.nextLine();
          System.out.println("Pon el nombre del personaje que quieras eliminar");
          String nombreEli = scanner.nextLine();
          int Eli = buscarPer(personajes,  cantidadPer, nombreEli);       
          if (Eli == -1 ){ //esto es por si en la matriz no sale el nombre se vaya a la posicion -1
          System.out.println("No se ha encontrado al personaje, ingresa otro nombre");
           }
           personajes [cantidadPer -1] = new String[8]; // se borra de la memoria la columna del personaje
           cantidadPer--;
          System.out.println("El personaje se ha eliminado"); 
                  
          break;
                case 4:
                    System.out.println("Ver atributos del personaje :");
                    scanner.nextLine();
                    
                    System.out.println("ingresa el nombre del personaje del que quieras ver sus atributos");
                    String nombreBus = scanner.nextLine();
                    
                    int Buscar = buscarPer(personajes, cantidadPer, nombreBus); 
                    if (Buscar ==-1){
                    System.out.println("No se ha encontrado a ese personaje, ingresa otro nombre");
                } else {
                        mostrarPer(personajes, Buscar);
                        } 
                    break;
                    
                    //Hacemos un llamado del modulo de mostar personajes y del modulo de buscar
               case 5:
                    System.out.println("Listado de de personajes");
                   if(cantidadPer == 0){
                      System.out.println("No hay ningun personaje registrado... de momento");
                   } else{
                       for (int i = 0; i <cantidadPer; i++){
                           mostrarPer(personajes, i); // llamado del modulo de mostar personajes
                           System.out.println();
                           }
                       }                
                    break;
                case 6:
                    System.out.println("¡Realizar combate entre personajes!");
                    scanner.nextLine();
                    
                   System.out.println("Ingresa el nombre del primer peleador"); 
                   String per1 = scanner.nextLine();
                   int ID1 = buscarPer(personajes, cantidadPer, per1);
                   
                   System.out.println("Ingresa al segundo peleador");
                   String per2 = scanner.nextLine();
                   int ID2 = buscarPer(personajes, cantidadPer, per2);
                   
                    if(ID1==-1 || ID2==-1){
                        System.out.println("Un personaje o los dos no existen, todavia");
                    }else{
                     // Usare la libreria random porque quiero y puedo
                     int ganador = random.nextInt(2); //0 para un personaje 1 para el otro
                     String resultado;
                    if (ganador ==0){
                        resultado = personajes [ID1][0] + "pierdes contra" + personajes[ID2][0];                      
                    } else{
                        resultado = personajes [ID2][0] + "pierdes contra" + personajes[ID1][0];
                    }
                   
                    String fecha_hora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                    String Registrar = fecha_hora +"---"+ resultado;
                    
                    if (cantPeleas < historialCo.length){
                        historialCo[cantPeleas] = Registrar;
                        cantPeleas++;
                    }
                    System.out.println("Combate registrado:"+ Registrar);
                     }
                    break;
                case 7:
                    System.out.println("Ver historial de combates");
                    if(cantPeleas ==0){
                        System.out.println("No hay peleas registradas.. aun");
                    }else {
                        for(int i=0; i<cantPeleas; i++){
                            System.out.println(historialCo[i]);
                        }
                    }
                    break;
                case 8:
                    System.out.println("Este programa fue hecho por Guillermo Enrique Marroquin Morán");
                    System.out.println("202103527");
                    System.out.println("Este programa tiene derechos de autor o no...");
                    break;
                case 9:
                    System.out.println("BYE BYEEEEE");
                    break;
                default:
                    System.out.println("!Esa opcion no existe!, ingresa otra opción.");
            }
      } while (escribir != 9); // Todo esto se repite indefinidamente hasta que pongan 9 
        scanner.close();              
    }
    }
    