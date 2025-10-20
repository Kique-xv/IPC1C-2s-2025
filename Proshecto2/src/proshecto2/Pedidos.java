//para la parte de generar los pedidos y asi la chingada
package proshecto2;

import java.time.LocalDateTime;

/**
 *
 * @author kiquemarroquin
 */
public class Pedidos {
    
    private String IDpedido;
    private LocalDateTime fechaGeneracion;
    private String IDcliente;
    private String NombreCliente;
    private double total;
    private ProdCarrito[] Prods; //para guardar productos
    private int cantProductos;
    
    
    //para los pedidos, el contructor
    public Pedidos(String IDpedido, LocalDateTime fecha, String IDcliente,String NombreCliente, double total, ProdCarrito[] Prods, int cantProductos ){
       this.IDpedido =  IDpedido;
       this.fechaGeneracion = fecha;
       this.IDcliente = IDcliente;
       this.NombreCliente = NombreCliente;
       this.total = total;
       this.Prods = Prods;
       this.cantProductos = cantProductos;
    }
   //los guerers o como se diga del ingles al español alv
    public String getIDpedido(){
        return IDpedido;
    }
    
    public LocalDateTime getFechaGeneracion(){
        return fechaGeneracion;
    }
    
    public String getIdCliente(){
        return IDcliente;
    }
    public String getNombreCliente(){
        return NombreCliente;
    }
    public double getTotal(){
        return total;
    }
    public int getCantProductos(){
        return cantProductos;
    }
    public ProdCarrito[] getProds(){
        return Prods;
    }
}
