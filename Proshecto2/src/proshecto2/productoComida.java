//una clase de contructora para los productos alimenticios
package proshecto2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author kiquemarroquin
 */
public class productoComida extends Productos {
//mas constantes
    public static final int VENCIMIENTO= Productos.CAMPOS_Prod;
public static final int CAMPOCOM = Productos.CAMPOS_Prod +1;

private String FechaVencer;
    public productoComida(String codigo, String nombre, double precio, int stock, String FechaVencer) {
        super(codigo, nombre, "ALIMENTO", precio, stock);
        this.FechaVencer= FechaVencer;
    }
  public String getFechaVencer(){
      return FechaVencer;
  }      

    @Override
    public String[] toArray() {
        return new String[]{
            super.codigo,
            super.nombre,
            super.categoria,
       
            String.valueOf(super.precio),
            String.valueOf(super.stock),
            this.FechaVencer
        };
    }
    @Override
    public String toCsLine() {
        return super.codigo + "," + super.nombre + "," + super.precio + "," + super.stock + "," + super.categoria + "," + this.FechaVencer;
    }
    public void setFechaVencer(String FechaVencer) {
    this.FechaVencer = FechaVencer;
}
    public static boolean FechaValida(String fechaStr){
        if(fechaStr == null || fechaStr.trim().isEmpty()){
            return false;
        }
        try{
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = LocalDate.parse(fechaStr.trim() , formato);
            return true;
        } catch(DateTimeParseException e){
            return false;
        }
    }
    }
    

