//con un cafe con el 99999999999999% de energia vamos a darle pelea a este proyecto feo
//posiblemente falte otra u otras semanas al gym.... MAS ME VALE QUE SALGA BIEN >:l
package proshecto2;

import javax.swing.SwingUtilities;

/**
 *
 * @author kiquemarroquin
 */
public class Proshecto2 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { //esto va aca, como en la practica 
            new IngresarUsuarios().setVisible(true);
        });
    }
}
