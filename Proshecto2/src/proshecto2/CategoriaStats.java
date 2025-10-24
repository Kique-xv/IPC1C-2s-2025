// para ver que categoria se vende mas y si es para el jodido reporte
package proshecto2;

/**
 *
 * @author kiquemarroquin
 */
public class CategoriaStats {
     public String nombreCategoria;
     public int cantVendida;
     public double IngresoTotal;
     public double ParticipacionPorcent;
     public double PromPrecioVenta;
     
     public CategoriaStats(String nombre){
         this.nombreCategoria = nombre;
         this.cantVendida =0;
         this.IngresoTotal =0.0;
         this.ParticipacionPorcent =0.0;
         this.PromPrecioVenta =0.0;
     }   
}
