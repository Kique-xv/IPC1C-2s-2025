//ya falta poco...
//ya no aguanto maaaaaaaas
package practica2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
//si gano esta madre me gane unas semanas de total descanso jugando al silson

/**
 *
 * @author kiquemarroquin
 */
public class BuscarPer extends JFrame {

    private JTextField txtBuscar;
    private JLabel LbEncontrar;
    private JTextArea Area;

    public BuscarPer() {
        //ya no quiero explicar esta madre
        setTitle("Buscar el personaje por su nombre");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel PanelPrincipal = new JPanel(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Buscar Personaje", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 19));
        PanelPrincipal.add(titulo, BorderLayout.NORTH);

        //el coso para buscar
        JPanel panelBuscar = new JPanel(new GridLayout(1, 2, 5, 5));
        txtBuscar = new JTextField();
        JButton btBuscar = new JButton("Buscar Personajes");
        panelBuscar.add(new JLabel("Escribe del Personaje: "));
        panelBuscar.add(txtBuscar);

        JPanel PanelCentro = new JPanel(new BorderLayout());
        LbEncontrar = new JLabel("Informacion del Personaje es: ", SwingUtilities.CENTER);
        LbEncontrar.setFont(new Font("Arial", Font.BOLD, 14));
        PanelCentro.add(LbEncontrar, BorderLayout.NORTH);

        Area = new JTextArea();
        Area.setEditable(false);
        PanelCentro.add(Area, BorderLayout.CENTER);

        PanelPrincipal.add(panelBuscar, BorderLayout.CENTER);
        PanelPrincipal.add(btBuscar, BorderLayout.SOUTH);
        add(PanelPrincipal, BorderLayout.NORTH);
        add(PanelCentro, BorderLayout.CENTER);

        btBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPersonajes(); //ya te ls sabes no... un metodo alli abajo
            }
        });
    }

    private void buscarPersonajes() {
        String Nbuscar = txtBuscar.getText().trim();
        if (Nbuscar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mira... pon un nombre si?", "Error 028", JOptionPane.ERROR_MESSAGE);
            validarAccion.regisAccion("buscar pesonajes", false, "Salio MAL APROPOSITO");

            return;
        }
        int indicePer = Personajes.buscarPer(Nbuscar);//sabia que seria util hacer el llamado

        if (indicePer != -1) {
            String[] personaje = Personajes.personaje[indicePer];
            String nombre = personaje[Personajes.NOMBRE];
            String arma = personaje[Personajes.ARMA];
            String ataque = personaje[Personajes.ATAQUE];
            String Hp = personaje[Personajes.HP];
            String velocidad = personaje[Personajes.VELOCIDAD];
            String vicDerr = personaje[Personajes.VICDERR];
            String defensa = personaje[Personajes.DEFENSA];

            StringBuilder resultado = new StringBuilder();
            resultado.append("Hemos encontrado a este Buey: \n");
            resultado.append("El nombre es: ").append(nombre).append("\n");
            resultado.append("El Arma del personaje es: ").append(arma).append("\n");
            resultado.append("Los puntos  de vida son: ").append(Hp).append("\n");
            resultado.append("Los puntos de ataque son: ").append(ataque).append("\n");
            resultado.append("La velocidad es: ").append(velocidad).append("\n");
            resultado.append("Los puntos de defensa son : ").append(defensa).append("\n");

//un chapus de ayuda
            String[] datos = new String[5];
            if (vicDerr != null && vicDerr.contains("|")) {
                datos = vicDerr.split("\\|");
            } else {
                datos[0] = "0";
                datos[1] = "0";
                datos[2] = "N/A";
                datos[3] = "---";
                datos[4] = "--:--:--";
            }
            String victorias = datos.length > 0 ? datos[0].trim() : "0";
            String derrotas = datos.length > 1 ? datos[1].trim() : "0";
            String turnos = datos.length > 2 ? datos[2].trim() : "N/A";
            String fecha = datos.length > 3 ? datos[3].trim() : "----";
            String hora = datos.length > 4 ? datos[4].trim() : "--:--:--";

            resultado.append("Historial: ").append("Vicotorias: ").append(victorias).append(" , Derrotas:").append(derrotas).append("\n");
            resultado.append("Ultimo Combate: ").append(turnos).append("  turnos,  ").append("Fecha:  ").append(fecha).append(", Hora: ").append(hora).append("\n");

            Area.setText(resultado.toString());
            validarAccion.regisAccion("Buscar el personaje", true, "Salio bien :p");
        } else {
            JOptionPane.showMessageDialog(this, "No hemos encontrado al personaje... no pusiste nada verdad?", "Error 029", JOptionPane.ERROR_MESSAGE);
            validarAccion.regisAccion("Buscar personajes", false, "Salio MAL");
        }
    }
}
