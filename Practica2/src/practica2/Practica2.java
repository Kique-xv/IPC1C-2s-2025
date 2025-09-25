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
            Bienvenida welcomeWindow = new Bienvenida("/Users/kiquemarroquin/Desktop/10thAnniversaryStreamSoundtrack/Song_That_Plays_When_Somebody_Verses_Sans_(by_Carlos_Insaneintherain_Eiene).wav");//el archivo debe de SER WAV NO MP3 :(((((
//Cambie la cancion para que no me desesperando de oirla a CADA RATO
            //que se vea
            welcomeWindow.setVisible(true);

        });
    }
}
