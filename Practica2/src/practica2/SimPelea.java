//Ahoar voy a respetar a dragon quuest...
//NI ME VOY ATREVER A PONER UNA CANCION, YA DE POR SI NO HE DORMIDO
package practica2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                                                                                 validarAccion.regisAccion("Madrazos entre personajes" , false, "Salio MAL APROPOSITO");

            return;
        }

        if (Luchador1 == null || Luchador2 == null) {
            JOptionPane.showMessageDialog(this, "Selecciona DOS Personajes para el combate", "Error 023", JOptionPane.ERROR_MESSAGE);
                                                                             validarAccion.regisAccion("Madrazos entre personajes" , false, "Salio MAL APROPOSITO");

        }
        if (Luchador1.equals(Luchador2)) {
            JOptionPane.showMessageDialog(this, "Un personaje no puede pelear solito, seria muy raro eso", "Error 024", JOptionPane.ERROR_MESSAGE);
                                                                              validarAccion.regisAccion("Madrazos entre personajes" , false, "Salio MAL APROPOSITO");

        }
        int indice1 = Personajes.buscarPer(Luchador1);
        int indice2 = Personajes.buscarPer(Luchador2);

        if (indice1 == -1 || indice2 == -1) {
            JOptionPane.showMessageDialog(this, "Algun personaje, no existe, ¿Cual? no se la verdad", "Error 025", JOptionPane.ERROR_MESSAGE);
                                                                      validarAccion.regisAccion("Madrazos entre personajes" , false, "Salio MAL APROPOSITO");
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
                                                                       validarAccion.regisAccion("Madrazos entre personajes" , false, "Salio MAL");

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
        int turnos = 0;

        while (Integer.parseInt(atacante[Personajes.HP]) > 0 && Integer.parseInt(defensor[Personajes.HP]) > 0) {
            try {
                turnos++;// cada ataque cuenta como turno
                int dañoBase = Integer.parseInt(atacante[Personajes.ATAQUE]);
                int agiliDef = Integer.parseInt(defensor[Personajes.AGILIDAD]);
                int defDef = Integer.parseInt(defensor[Personajes.DEFENSA]);

                //la probabilidad de esquivar
                boolean esquivar = random.nextInt(10) < agiliDef;

                if (esquivar) {
                    int t = turnos;
                    SwingUtilities.invokeLater(()
                            -> bitacora.append("\nTurno" + t + ": " + Natacante + " HA ATACADO, PERO \n "
                                    + Ndefensor + "HA ESQUIVADO EL ATAQUE "));
                } else {
                    int dañoFinal = dañoBase - defDef;
                    if (dañoFinal < 0) {
                        dañoFinal = 0;
                    }

                    int hpActualDef = Integer.parseInt(defensor[Personajes.HP]);
                    hpActualDef -= dañoFinal;
                    defensor[Personajes.HP] = String.valueOf(hpActualDef);

                    //Chapus para evitar un error 
                    int t = turnos;
                    final int dañoF = dañoFinal;
                    final int HpActDef = hpActualDef;

// Es la linea mas larga que he echo... dios... me habre ganado un recor guinen´t
                    SwingUtilities.invokeLater(()
                            -> bitacora.append("\nTurno" + t + ":" + Natacante + " HA ECHO UN ATAQUE " + Ndefensor + " HA SUFRIDO \n"
                                    + dañoF + "  PUNTOS DE DAÑO, LOS DE VIDA RESTANTES DE " + Ndefensor + " ES:  " + HpActDef));
                }
                int veloAtacante = Integer.parseInt(atacante[Personajes.VELOCIDAD]);
                Thread.sleep(1000 / veloAtacante);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        int turnosF = turnos;
        //vamos arreglar esto..
        SwingUtilities.invokeLater(() -> {
            String ganador = "";
            String perdedor = "";

            if (Integer.parseInt(atacante[Personajes.HP]) > 0) {
                ganador = Natacante;
                perdedor = Ndefensor;

            } else if (Integer.parseInt(defensor[Personajes.HP]) > 0) {
                ganador = Ndefensor;
                perdedor = Natacante;
            }
            if (!ganador.isEmpty()) {
                bitacora.append("\n" + ganador + " Ha ganado el combate en \n " + turnosF + "turnos \n");
                actualHistorial(ganador, perdedor, turnosF);
                validarAccion.regisAccion("madrazos entre personajes", true, "Salio bien");

            } else {
                bitacora.append("TENEMOS UN EMPATE A LOS: " + turnosF + "TURNOS \n");
            }
            btIniLucha.setEnabled(true);

        });
    }

    private void actualHistorial(String ganador, String perdedor, int turnos) {
        //la hora y fehcha
        SimpleDateFormat fechaform = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat Horaform = new SimpleDateFormat("HH:mm:ss");

        Date ahora = new Date();
        String fecha = fechaform.format(ahora);
        String hora = Horaform.format(ahora);

        //otro chapus
        int vicAct = 0;
        int derrAct = 0;

        int inGanar = Personajes.buscarPer(ganador);
        int inPerder = Personajes.buscarPer(perdedor);

        String HistGanar = Personajes.personaje[inGanar][Personajes.VICDERR];
        String HistPer = Personajes.personaje[inPerder][Personajes.VICDERR];

//sin esto el programa falla... y tuve que rehacer esta basura
        try {
            String[] datos = HistGanar.split("\\|");
            vicAct = Integer.parseInt(datos[0].trim());
            derrAct = Integer.parseInt(datos[1].trim());
        } catch (Exception e) {
            try {
                String[] datos = HistGanar.split("-");
                vicAct = Integer.parseInt(datos[0].trim());
                derrAct = Integer.parseInt(datos[1].trim());
            } catch (Exception e2) {
                vicAct = 0;
                derrAct = 0;
            }
        }
        vicAct++;
        Personajes.personaje[inGanar][Personajes.VICDERR] = vicAct + " | " + derrAct + " | " + turnos + "|" + fecha + " | " + hora;
        //reseteamos el conteno de perdidas y ganancias
        vicAct = 0;
        derrAct = 0;

        try {
            String[] datos = HistPer.split("\\|");
            vicAct = Integer.parseInt(datos[0].trim());
            derrAct = Integer.parseInt(datos[1].toLowerCase());
        } catch (Exception e) {
            try {
                String[] datos = HistPer.split("-");
                vicAct = Integer.parseInt(datos[0].trim());
                derrAct = Integer.parseInt(datos[1].trim());
            } catch (Exception e2) {
                vicAct = 0;
                derrAct = 0;
            }
        }
        derrAct++;
        Personajes.personaje[inPerder][Personajes.VICDERR] = vicAct + " | " + derrAct + " | " + turnos + "|" + fecha + " | " + hora;
    }
}
