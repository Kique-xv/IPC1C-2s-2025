//una clase de produtos que no sean comida o tecnologia, que  listo que sos bardock
package proshecto2;

/**
 *
 * @author kiquemarroquin
 */
public class productoOtros extends Productos {
//total de campos para el csv de otros productos

    public static final int CAMPORT = Productos.CAMPOS_Prod;

    public productoOtros(String codigo, String nombre, double precio, int stock) {
        super(codigo, nombre, "OTROS", precio, stock);
    }

    @Override
    public String[] toArray() {
        return new String[]{
            super.codigo,
            super.nombre,
            super.categoria,
            String.valueOf(super.precio),
            String.valueOf(super.stock),
            "N/A" //usamos eso para mantener la consistencia en las tablas 
        };
    }

    @Override
    public String toCsLine() {
        return super.codigo + "," + super.nombre + "," + super.precio + "," + super.stock + "," + super.categoria + "," + "N/A";
    }
}
