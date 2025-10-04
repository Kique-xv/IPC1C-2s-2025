//una clase con un nombre muy obvio
package proshecto2; //te odio text to spech

/**
 *
 * @author kiquemarroquin
 */
public class ValidarDatos {
    //patron de csv COD, Nomb genero, contraseña, ventas confirmadas
    private static final int NUM_CAMPO_VENDEDOR_CSV = 5; //mira.... ya me canse de andar inventando nombre cool asi se va esta madre

    public static boolean ValidarFormVendedor(String[] datos){
        //validamos la cantidad
        if(datos.length != NUM_CAMPO_VENDEDOR_CSV){
            return false;
        }
        //validamos que los campos no esten vacios despues del .trim
        for(String dato: datos){
            if(dato == null || dato.trim().isEmpty()){
                return false;
            }
        }
        //validamos el formato del genero solo hombre y mujer como buenas costumbre, nada de los 39 tipos de guey
        String genero = datos[2].trim().toUpperCase();
        if(!genero.equals("M") && !genero.equals("F")){
            return false;
        }
        //validamos el formato de ventas, obvio un entero no soy weon
        try{
            Integer.parseInt(datos[4].trim());         
        } catch(NumberFormatException e){
            return false; //el campo de ventas no es un numoer
        }
        return true; //si pasa todo:3
    }
}
