//una clase para los productos tecnologicos
package proshecto2;

/**
 *
 * @author kiquemarroquin
 */
public class productoTec extends Productos {

    public static final int GARANTIA = Productos.CAMPOS_Prod;
    public static final int CAMPOSTEC = Productos.CAMPOS_Prod + 1;

    private int MesesGarantia;

    public productoTec(String codigo, String nombre, double precio, int stock, int MesesGarantia) {
        super(codigo, nombre, "TECNOLOGIA", precio, stock);
        this.MesesGarantia = MesesGarantia;
    }

    public int getMesesGarantia() {
        return MesesGarantia;
    }
 public void setMesesGarantia(int MesesGarantia) {
    this.MesesGarantia = MesesGarantia;
}   
//ets madre salio al aplicar losmetodos abstractos

    @Override
    public String[] toArray() {
        return new String[]{
            super.codigo,
            super.nombre,
            super.categoria,
           
            String.valueOf(super.precio),
            String.valueOf(super.stock),
            String.valueOf(this.MesesGarantia)
        };
    }

    @Override
    public String toCsLine() {
        return super.codigo + "," + super.nombre + "," + super.categoria + "," + super.precio + "," + super.stock + "," + this.MesesGarantia;
    }
}
