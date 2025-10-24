///ya vamos por el finaaaaaaal siiiiiii, felicidaaaaaaaad
package proshecto2;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author kiquemarroquin
 */
public class CompraAceptada  implements Serializable{
    private String idPedido;
    private  transient LocalDateTime fechaConfirm; //guardamos cuando se confirmo el pedido
    private String idCliente; //para saber a quien le perteneze la compra o pedido
    private  double total;
    private String fechaConfirmStr; // 
    
    private ProdCarrito[] prods; //para guardar una copua en los pedidos
    private int cantProductos;
    
    public CompraAceptada(String idPedido, LocalDateTime fecha, String idCliente, double total, ProdCarrito[] prods, int cantProductos) {
        this.idPedido = idPedido;
        this.fechaConfirm = fecha;
        this.idCliente = idCliente;
        this.total = total;
        this.prods = prods;
        this.cantProductos = cantProductos;
        this.fechaConfirmStr = fecha.toString();
    }
    //los geters, xd
    public String getIdPedido(){
        return idPedido;
    }
    public LocalDateTime getFechaConfirm(){
        if(fechaConfirm == null && fechaConfirmStr != null){
            try{
                fechaConfirm = LocalDateTime.parse(fechaConfirmStr);
            } catch(Exception e){
                //jeje nada xddd
            }
        }
        return fechaConfirm;
    }
    public String getIdCliente(){
        return idCliente;
    }
    public double getTotal(){
        return total;
    }
    public ProdCarrito[] getProds(){
        return prods;
    }
    public int getCantProductos(){
        return cantProductos;
    }
}
