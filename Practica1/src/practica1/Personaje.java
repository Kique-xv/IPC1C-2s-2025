
package practica1;

public class Personaje {
   // Declaracion de variables 
      String nombre;
      String  arma;
      String [] habilidades ;// esto porque son 5 habilidades maximo 
      int nivel_pod;
      
      Personaje (String nombre, String arama, int nivel_pod, String habilidades[])
      {
        this.nombre = nombre;
        this.arma = arma;
        this.nivel_pod = nivel_pod;
        this.habilidades = habilidades;
        } 
}
