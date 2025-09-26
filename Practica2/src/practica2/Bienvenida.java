//SIP ESTO ES ES PARA PROBAR ALGO
//una ventana de bienbenidda
package practica2;
//Todas esatas liberias son porque si okey?
//Esto sera muy divertido de explicarlo en la documentacion

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
//siip toda estas madres son para UNA CANCION BOBA DEL DELTARUN
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
//Botones, los paneles, las eltras que iran el la bienvenida xddd
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;// y esta madre que en yutu dijo que es importante y le creo alv

/**
 *
 * @author kiquemarroquin
 */
public class Bienvenida extends JFrame {

    private MusicPlayer mPlayer; //quiero reproducir musica en la ventana de bienvenida

    public Bienvenida(String audioFilepath) {
        setTitle("Bienvenido a  AREA USAC 2025"); //esto saldra en la ventana osea... arribita
        //tamaño de la ventana
        setSize(700, 400);
        setLocationRelativeTo(null);// para centrar la ventana
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //hagamos un boton pa salir... que dar click dobel y salir es muy aburrido
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));

        //El mensaje que saldra en la ventana osea adentro pue
        JLabel SaludoMesj = new JLabel("Bienvenido a POKEMON STADIUM TEXT VERSION", SwingConstants.CENTER);
        SaludoMesj.setFont(new Font("Papyrus", Font.BOLD, 19));

        //crearemos el boton de salir y se va a ordernar con el mensaje HOY SI
        JPanel bP = new JPanel();
        JButton Malir = new JButton("Salir");
        Malir.setPreferredSize(new Dimension(100, 30));
        bP.add(Malir);

        mainPanel.add(SaludoMesj);
        mainPanel.add(bP);//referrencia a los simson de malir sal
        add(mainPanel);

        //Hilo para reporoducir el ost
        mPlayer = new MusicPlayer(audioFilepath);
        Thread musicThread = new Thread(mPlayer);
        musicThread.start();

        //la accion de parar la rola
        Malir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {// esto salio automaticamanet :0
                mPlayer.stop(); //se para la musica
                dispose();// se cierra la venta

                //abre la ventana del m eun principal
                menuPrincipal MenuPrincipal = new menuPrincipal();
                MenuPrincipal.setVisible(true);
            }
        });

        //agregamos un stop para la musica al cerrar la ventana
        addWindowListener(new WindowAdapter() {
            public void CerrarVent(WindowEvent e) {
                mPlayer.stop();
            }
        });
    }

    public class MusicPlayer implements Runnable {

        private String audioFilePath;
        private Clip clip;
        private volatile boolean isRunning = true;

        public MusicPlayer(String audioFilePath) {
            this.audioFilePath = audioFilePath;
        }

        @Override
        public void run() {
            try {
                File audioFile = new File(audioFilePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);

                if (!AudioSystem.isLineSupported(info)) {
                    System.err.println("No se encontro el archivo a repoducir");
                    return;
                }
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioStream);

                //inciamos la repoduccion en bucleeeeeee
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                System.out.println("Reproduciendo:Song_That_Plays_When_Somebody_Verses_Sans_(by_Carlos_Insaneintherain_Eiene)");
                System.out.println("De Deltarune, por Toby Fox y compañia");
                validarAccion.regisAccion("Escuchar un temazo", true, "Salio bien");

                while (isRunning) {
                    //con esto mantenemos "vivo" mientras se reproduce el temazo xdddd
                    Thread.sleep(100);
                }
                clip.stop();
                clip.close();
                audioStream.close();
                System.out.println("La musica se ha detenido...");

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            isRunning = false;
        }
    }
}
