//al fin salimos de los vendedores
package proshecto2;

/**
 *
 * @author kiquemarroquin
 */
public abstract class Productos {
    //las constantes de posicion 
    public static final int CODIGO =0;
    public static final int NOMBRE =1;
    public static final int CATEGORIA =2;
    public static final int PRECIO =3;
    public static final int STOCK =4;
    public static final int CAMPOS_Prod =6;
    //usamos los protected para usarlas en las demas clases
    protected String codigo;
    protected String nombre;
    protected String categoria;
    protected double precio;
    protected int stock;
 
    public Productos(String codigo, String nombre, String categoria, double precio, int stock){
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }
    //los getters ya saben lo que dire a continuacion... gracias inge xd
    public String getCodigo(){
        return codigo;
    }
    public String getNombre(){
        return nombre;
    }
    public String getCategoria(){
        return categoria;
    }
    public double getPrecio(){
        return precio;
    } 
    public int getStock(){
        return stock;
    }
    public void setNombre(String nombre){
        this.nombre =nombre;
    }
   
    public void setStock(int stock){
        this.stock = stock;
    }
    //los metodos abstractos para los gestores, asi no la kgo como con los vendedores
    public abstract String[] toArray();
    public abstract String toCsLine();
}
