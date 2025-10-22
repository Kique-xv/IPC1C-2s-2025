// stats suena mas chigon que estadisticas y nadie me hara cambiar de opinio
package proshecto2;

/**
 *
 * @author kiquemarroquin
 */
public class ProductoStats {
    public Productos producto; //referenciamos al producto original
    public int CantVendida;
    public double IngresosGen;
    
    public ProductoStats(Productos p){
        this.producto =p;
        this.CantVendida =0;
        this.IngresosGen =0;     
    }
}
