///ya vamos por el finaaaaaaal siiiiiii, felicidaaaaaaaad
package proshecto2;

import java.time.LocalDateTime;

/**
 *
 * @author kiquemarroquin
 */
public class CompraAceptada {
    private String idPedido;
    private LocalDateTime fechaConfirm; //guardamos cuando se confirmo el pedido
    private String idCliente; //para saber a quien le perteneze la compra o pedido
    private  double total;
    
    public CompraAceptada(String idPedido, LocalDateTime fecha, String idCliente, double total) {
        this.idPedido = idPedido;
        this.fechaConfirm = fecha;
        this.idCliente = idCliente;
        this.total = total;
    }
    //los geters, xd
    public String getIdPedido(){
        return idPedido;
    }
    public LocalDateTime getFechaConfirm(){
        return fechaConfirm;
    }
    public String getIdCliente(){
        return idCliente;
    }
    public double getTotal(){
        return total;
    }
}
