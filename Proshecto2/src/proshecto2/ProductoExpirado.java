//para el reporte de caducidad
package proshecto2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;//esto me toco que buscar alv... enserio quien se le ocurrio este proycto no tiene compacion...

/**
 *
 * @author kiquemarroquin
 */
public class ProductoExpirado {
    public productoComida producto;
    
    public long diasRestantes;
    public String estadoPriori;
    public double ValorRiesgo;
    public String recomendacion;
    
    // Constructor que calculara todo todito
    public ProductoExpirado(productoComida p){
        this.producto =p;
        LocalDate hoy = LocalDate.now();
        LocalDate fechaCaducidad = null;
        this.diasRestantes =-999; //por si hay error
                
                try{
                  fechaCaducidad  = LocalDate.parse(p.getFechaVencer());
                  this.diasRestantes = ChronoUnit.DAYS.between(hoy, fechaCaducidad);
                } catch(Exception e){
                    System.err.println("Error al leer la fecha de caducidad " + p.getFechaVencer() + " para  el producto " + p.getCodigo());
               //si la fecha es invalida (jsjsjsjs) lo ponemos como que ya esta caducado
                    this.diasRestantes = -1;
                }
        if(this.diasRestantes < 0){
            this.estadoPriori ="CADUCADO";
            this.recomendacion = "DESECHAR";
        } else if(this.diasRestantes <=3){
            this.estadoPriori ="CRITICO";
            this.recomendacion ="DESCUENTO";        
        } else if(this.diasRestantes <=7){
            this.estadoPriori="URGENTE";
            this.recomendacion ="PROMOCIONAR";
        } else{
            this.estadoPriori ="OK";
            this.recomendacion ="MONITOREO";
        }
        this.ValorRiesgo = p.getStock() * p.getPrecio();
    }


}
