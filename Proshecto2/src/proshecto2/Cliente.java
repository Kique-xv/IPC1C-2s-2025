//despues de tanto ya se extrañaba una clase nueva eh xd
//es un constructo pa los clientes pedorros
package proshecto2;

/**
 *
 * @author kiquemarroquin
 */
public class Cliente  extends Usuarios {
    //atributos unicos del cliente
    private String genero;
    private String cumpleaños;
    
    public Cliente(String id, String nombre, String contraseña, String genero, String cumpleaños){
        super(id,nombre, contraseña, "CLIENTE");
        this.genero = genero;
        this.cumpleaños = cumpleaños;
    }
    //los getters
    public String getGenero(){
        return genero;
    }
    public String getCumpleaños(){
                return cumpleaños;
    }
    @Override
    public String getNombre(){
        return nombre;
    }  
    
    public String getContraseña(){
        return Contraseña;
    }
    //los setters para modificar las cosas 
   public void setGenero(String genero){//SOLO DOS GENEROS ALV
     this.genero = genero;
   }
   public void setCumpleaños(String cumpleaños){
       this.cumpleaños = cumpleaños;
   }
   public void setNomber(String nombre){
       this.nombre = nombre;
   }
   public void setContraseña(String contraseña){
       this.Contraseña = contraseña;
   }
}
