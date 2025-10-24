//para el carrito de compras xd
package proshecto2;

import java.io.Serializable;
import javax.swing.JOptionPane;

class ProdCarrito implements Serializable{//el nombre es por producto carrito
    public   Productos producto;
    public int cantidad;
    
    public ProdCarrito(Productos producto, int cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
    }
}
/**
 *
 * @author kiquemarroquin
 */
//esta webada se encarga de gestionar la lista de productos en el carrito
public class Carrito {
    
    private static final int MaxProdCarr = 50; //un maximo de 50 productos distintos en el carro, porque no mamen no me voy a poder muy especialito con esto
    private static ProdCarrito[] Prods = new ProdCarrito[MaxProdCarr];
    private static int cantProductos =0;
    
    public static ProdCarrito[] getProds(){
        return Prods;
    }
    
    public static int getCantProd(){
        return cantProductos;
    }
    
    //un metodo para agregar un producto al carrito
    public static boolean AgregarProducto(Productos producto, int cantidad){
        if(cantProductos >= MaxProdCarr){
            JOptionPane.showMessageDialog(null, "Has alcanzado el limite de productos en el carrito" , "Carro de compras lleno", JOptionPane.WARNING_MESSAGE);
      return false;
        }
        
        Prods[cantProductos++] = new ProdCarrito(producto, cantidad);
        JOptionPane.showMessageDialog(null, cantidad + " unidades de: " + producto.getNombre()+ " agregado al carrito de compras", "Tarea Exitosa" , JOptionPane.INFORMATION_MESSAGE);
    return true;
    }
    //para el boton de eliminar 
    public  static void EliminarProd(int indiceProd){
       if(indiceProd <0 || indiceProd >= cantProductos) return; //por si acaso
       
       for(int i= indiceProd; i<cantProductos-1; i++){
           Prods[i] = Prods[i+1];
       }
      Prods[cantProductos-1] =null;
      cantProductos--; 
    }
    
    public static boolean actualizarCant(int indiceProd, int Ncantidad){
        if(indiceProd <0 || indiceProd >= cantProductos) return false;
        
        ProdCarrito prod = Prods[indiceProd];
        //validamos contra el sotck disponible del producto
        
        if(Ncantidad > prod.producto.getStock()){
            JOptionPane.showMessageDialog(null, "Stock insuficiente, solo quedan: " +prod.producto.getStock()+ " unidades" , "Error", JOptionPane.ERROR_MESSAGE);
      return false;
        }
        prod.cantidad = Ncantidad;
        return true;
    }
    //para el total de las compras
    public static double CalcularTotal(){
        double total = 0.0;
        for(int i= 0; i< cantProductos; i++){
            total += Prods[i].producto.getPrecio() * Prods[i].cantidad;
        }
        return total;
    }
       //para limpiar el carrito
    public static void LimpiarCarro(){
        //vacuamos el carrito reiniciando el arreglo y el contador
        Prods = new ProdCarrito[MaxProdCarr];
        cantProductos =0;
    }
}
