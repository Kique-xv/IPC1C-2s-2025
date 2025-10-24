//al igual que vendedores y eso
package proshecto2;

import java.time.LocalDateTime;

/**
 *
 * @author kiquemarroquin
 */
public class ClienteStats {
    public Cliente cliente;
    
    public LocalDateTime fechaUltCompra;
    public int CantComprasMes;
    public double MontoTotalMes;
    public String Clasificacion;
    
    public ClienteStats(Cliente c){
        this.cliente =c;
        this.fechaUltCompra = null; 
        this.CantComprasMes =0;
        this.MontoTotalMes =0;
        this.Clasificacion ="Inactivo";
    }
}
