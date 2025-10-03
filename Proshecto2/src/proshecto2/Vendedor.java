//una clase que herede la de admin o bueno lo de usuarios ya sabes xd
package proshecto2;

/**
 *
 * @author kiquemarroquin
 */
public class Vendedor extends Usuarios {

    //el indece de los uevso campos que sigue desde los campos de la clase de usuairos 
    public static final int GENERO = Usuarios.CAMPOS;//no pondre la palabra chistosa esto es un proyecto serio xd
    public static final int VENTAS_HECHAS = Usuarios.CAMPOS + 1;
    public static final int NUM_CAMPOSV = Usuarios.CAMPOS + 2;

    private String genero;
    private int Ventashechas;

    public Vendedor(String id, String nombre, String Contraseña, String tipoUusario) {
        super(id, nombre, Contraseña, "VENDEDOR"); //salio del ide no me ragañen :(

        this.genero = genero;
        this.Ventashechas = 0;
    }

    public String getGenero() {
        return genero;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return Contraseña;
    }
    
    public String setNombre(){
        return nombre;
    }
    
    public String setContraseña(){
        return Contraseña;
    }
            
    public String[] toArary() {
        //devolvera el id, nombre, contraseña el tipo de usuario, genero y ventas confirmadas
        return new String[]{
            super.id,
            super.nombre,
            super.Contraseña,
            super.tipoUsuario,
            this.genero,
            String.valueOf(this.Ventashechas)
        };
    }

    public String bienvenidaV() {
        return "bienvenid@ al modulo de vendedor de Hip Shop, " + super.nombre;
    }
}
