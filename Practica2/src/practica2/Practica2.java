//Creo que ya funciona, bueno esta es la clase principal, ojala... que esto no me lleve mucho...
// como esto es pokimon bueno ya me puedo poner creativo, mas esquiso
package practica2;

import javax.swing.SwingUtilities;

/**
 *
 * @author kiquemarroquin
 */
public class Practica2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //Creamos una nueva instancia para la ventana de bienvenida
            Bienvenida welcomeWindow = new Bienvenida("/Users/kiquemarroquin/Desktop/IPC1/c41ed7a0.wav");//el archivo debe de SER WAV NO MP3 :(((((

            //que se vea
            welcomeWindow.setVisible(true);

        });
    }
}
