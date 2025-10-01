
package proshecto2;

import java.io.Serializable;

/**
 *
 * @author kiquemarroquin
 */
public class Usuarios implements Serializable{
    
    // id de serie, me lo dijo yutu :((
    private static final long seriaVersionUID =1L;
    
    //los campos de todos los usuarios 
    public static final int ID =0; 
    public static final int NOMBRE =1; 
    public static final int CONTRASEÑA =2; 
    public static final int TIPOUSUR =3; 
    public static final int CAMPOS =4; 

    private String id;
    private String nombre;
    private String Contraseña;
    private String tipoUsuario;
    
    public Usuarios(String id, String  nombre, String Contraseña, String tipoUusario){
        //gracias inge moises :3
        this.id = id;
        this.nombre = nombre;
        this.Contraseña = Contraseña;
        this.tipoUsuario = tipoUsuario;    
    }
    //los getters, GRACIAS INGE ME ESTA SALVANDo LA COLA
    public String getId(){
        return id;
    }
public String getNombre(){
    return nombre;  
}
public String getcontraseña(){
    return Contraseña;
}
public String getTipoUsuario(){
    return tipoUsuario;
}

//un metodo para obtener los datos como un arregglos
public String[] toArray(){
    return new String[] {id, nombre, Contraseña, tipoUsuario};
}
public String Bienvenida(){
    return"Bienvenid@ a HIP SHOP " +nombre;
}
}
