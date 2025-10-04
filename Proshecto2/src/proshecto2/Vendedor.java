package proshecto2;

/**
 * 
 * @author kiquemarroquin
 */
public class Vendedor extends Usuarios {

    // CONSTANTES DE POSICIÓN DE CAMPOS EN CSV
    public static final int ID = Usuarios.ID; 
    public static final int NOMBRE = Usuarios.NOMBRE; 
    public static final int CONTRASEÑA = Usuarios.CONTRASEÑA;
    public static final int TIPOUSUR = Usuarios.TIPOUSUR; // "VENDEDOR"

    public static final int GENERO = Usuarios.CAMPOS;      // El índice empieza después del último campo de Usuarios
    public static final int VENTAS_HECHAS = Usuarios.CAMPOS + 1;
    public static final int NUM_CAMPOS_VENDEDOR = Usuarios.CAMPOS + 2; // Total de campos para el CSV

    // Atributos de la claseeeee
    private String genero;
    private int Ventashechas;

    // El contructor
    public Vendedor(String id, String nombre, String Contraseña, String genero) {
        super(id, nombre, Contraseña, "VENDEDOR"); 

        this.genero = genero; 
        this.Ventashechas = 0; 
    }

    // GETTERS Y SETTERS, y gracias inge moiseeees, me salvo la cola
     public String getId() {
        return super.id;
    }
    public String getGenero() {
        return genero;
    }
    
    public int getVentasHechas() {
        return Ventashechas;
    }
    
    public void setVentasHechas(int ventas) {
        this.Ventashechas = ventas;
    }
     public String setId() {
        return id;
    }
    public void setNombre(String nombre) {
        super.nombre = nombre; 
    }
    
    public void setContraseña(String Contraseña) {
        super.Contraseña = Contraseña;
    }

    public String getNombre() {
        return super.nombre;
    }
    public String getContraseña() {
        return super.Contraseña;
    }
    
    // UTILIDADES
    
    @Override //
    public String[] toArray() {
        // Devuelve el array completo: ID, NOMBRE, CONTRASEÑA, TIPO, GÉNERO, VENTAS
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