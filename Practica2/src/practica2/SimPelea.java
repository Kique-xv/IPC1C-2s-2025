//Ahoar voy a respetar a dragon quuest...
//NI ME VOY ATREVER A PONER UNA CANCION, YA DE POR SI NO HE DORMIDO
package practica2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author kiquemarroquin
 */
public class SimPelea extends JFrame {
    //esto es para buscar los personajes de la matriz

    private JComboBox<String> cbLuchador1;
    private JComboBox<String> cbLuchador2;

    private JButton btIniLucha;
    private JTextArea bitacoraArea;
    private JScrollPane sP;

    private Random random = new Random();

    public SimPelea() {
        //esto ya se lo saben
        setTitle("Simuladro de Combates");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//otro chapus para que el texto si se vea...
        JPanel PanelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Elige dos personajes para LUCHAR", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        cbLuchador1 = new JComboBox<>();
        cbLuchador2 = new JComboBox<>();
        CargarLuchador(); //un metodo que alli abajo esta xd

        PanelSuperior.add(titulo);
        PanelSuperior.add(new JLabel("Elige al primer luchador"));
        PanelSuperior.add(cbLuchador1);
        PanelSuperior.add(new JLabel("Ahora Elige al segundo luchador"));
        PanelSuperior.add(cbLuchador2);

        btIniLucha = new JButton("MADRAZOS");
        PanelSuperior.add(btIniLucha);

        //otro chapus para evitar que el boton crashee el programa
        bitacoraArea = new JTextArea();
        bitacoraArea.setEditable(false);
        JScrollPane sP = new JScrollPane(bitacoraArea);

        //agregar los componentes a la region del panel principal
        panelPrincipal.add(PanelSuperior, BorderLayout.NORTH); //el panel con el titulo, el botno y el seleccioador
        panelPrincipal.add(sP, BorderLayout.CENTER);
        add(panelPrincipal);

        btIniLucha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IniciarCombate();  //();//metodo que alli ta MAS abajo
            }
        });
    }

    private void CargarLuchador() {
        for (int i = 0; i < Personajes.CantPersonajes; i++) {
            String nombre = Personajes.personaje[i][Personajes.NOMBRE];
            cbLuchador1.addItem(nombre);
            cbLuchador2.addItem(nombre);
        }
    }

    private void IniciarCombate() {
        String Luchador1 = (String) cbLuchador1.getSelectedItem();
        String Luchador2 = (String) cbLuchador2.getSelectedItem();

        //otro chapus...
        if (Personajes.CantPersonajes < 2) {
            JOptionPane.showMessageDialog(this, "NECESITAS ALMENOS DOS Personajes para el combate", "Error 022", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Luchador1 == null || Luchador2 == null) {
            JOptionPane.showMessageDialog(this, "Selecciona DOS Personajes para el combate", "Error 023", JOptionPane.ERROR_MESSAGE);
        }
        if (Luchador1.equals(Luchador2)) {
            JOptionPane.showMessageDialog(this, "Un personaje no puede pelear solito, seria muy raro eso", "Error 024", JOptionPane.ERROR_MESSAGE);
        }
        int indice1 = Personajes.buscarPer(Luchador1);
        int indice2 = Personajes.buscarPer(Luchador2);

        if (indice1 == -1 || indice2 == -1) {
            JOptionPane.showMessageDialog(this, "Algun personaje, no existe, ¿Cual? no se la verdad", "Error 025", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //los luchadores
        String[] L1 = Personajes.personaje[indice1];
        String[] L2 = Personajes.personaje[indice2];

        //los puntos de vida de cada uno
        int Hp1 = Integer.parseInt(L1[Personajes.HP]);
        int Hp2 = Integer.parseInt(L2[Personajes.HP]);

        if (Hp1 <= 0 || Hp2 <= 0) {
            JOptionPane.showMessageDialog(this, "Alguno de los personajes ya se murio xD", "Error 026", JOptionPane.ERROR_MESSAGE);
            return;
        }
        bitacoraArea.setText("BIENVENIDOS AL COMBATE, HOY SE ENFRETARÁ: " + Luchador1 + "CONTRA" + Luchador2 + "SIGAN VIENDO");
        btIniLucha.setEnabled(false);

        String[] P1 = L1.clone();
        String[] P2 = L2.clone();

        //la parte de los hilos... me wa a morir
        Thread hilo1 = new Thread(() -> Combate(P1, P2, bitacoraArea));
        Thread hilo2 = new Thread(() -> Combate(P1, P2, bitacoraArea));
        hilo1.start();
        hilo2.start();
    }

    private void Combate(String[] atacante, String[] defensor, JTextArea bitacora) {
        String Natacante = atacante[Personajes.NOMBRE];
        String Ndefensor = defensor[Personajes.NOMBRE];

        while (Integer.parseInt(atacante[Personajes.HP]) > 0 && Integer.parseInt(defensor[Personajes.HP]) > 0) {
            try {
                int dañoBase = Integer.parseInt(atacante[Personajes.ATAQUE]);
                int agiliDef = Integer.parseInt(defensor[Personajes.AGILIDAD]);
                int defDef = Integer.parseInt(defensor[Personajes.DEFENSA]);

                //la probabilidad de esquivar
                boolean esquivar = random.nextInt(10) < agiliDef;

                if (esquivar) {
                    SwingUtilities.invokeLater(() -> bitacora.append(Natacante + " HA ATACADO, PERO " + Ndefensor + "HA ESQUIVADO EL ATAQUE"));
                } else {
                    int dañoFinal = dañoBase - defDef;
                    if (dañoFinal < 0) {
                        dañoFinal = 0;
                    }

                    int hpActualDef = Integer.parseInt(defensor[Personajes.HP]);
                    hpActualDef -= dañoFinal;
                    defensor[Personajes.HP] = String.valueOf(hpActualDef);
                    //Chapus para evitar un error 
                    final int dañoF = dañoFinal;
                    final int HpActDef = hpActualDef;

// Es la linea mas larga que he echo... dios... me habre ganado un recor guinen´t
                    SwingUtilities.invokeLater(() -> bitacora.append(Natacante + " HA ECHO UN ATAQUE " + Ndefensor + " HA SUFRIDO \n"
                            + dañoF + "  PUNTOS DE DAÑO, LOS DE VIDA RESTANTES DE " + Ndefensor + " ES:  " + HpActDef));
                }
                int veloAtacante = Integer.parseInt(atacante[Personajes.VELOCIDAD]);
                Thread.sleep(1000 / veloAtacante);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        if (Integer.parseInt(atacante[Personajes.HP]) > 0) {
            SwingUtilities.invokeLater(() -> {
                bitacora.append(Natacante + " ES EL GANADOR DEL COMBATEEEEEEEEEEEEE");
                actualHistorial(Natacante, Ndefensor);// una funcion abaja mas abajo
                btIniLucha.setEnabled(true);
            });
        }
    }

    private void actualHistorial(String ganar, String perder) {
        int inGanar = Personajes.buscarPer(ganar);
        int inPerder = Personajes.buscarPer(perder);

        if (inGanar != -1) {
            String[] HistGanar = Personajes.personaje[inGanar][Personajes.VICDERR].split("-");
            int victoria = Integer.parseInt(HistGanar[0]) + 1;
            Personajes.personaje[inGanar][Personajes.VICDERR] = victoria + "-" + HistGanar[1];
        }
        if (inPerder != -1) {
            String[] HistPer = Personajes.personaje[inPerder][Personajes.VICDERR].split("-");
            int derrota = Integer.parseInt(HistPer[1]) + 1;
            Personajes.personaje[inPerder][Personajes.VICDERR] = HistPer[0] + "-" + derrota;
        }
    }
}
