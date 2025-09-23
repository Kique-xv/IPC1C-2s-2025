//yo digo que cada clase por opcion es mas ordenano no?
//ya el nombre de la clase dice que chucha voy hacer no?
package practica2;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author kiquemarroquin
 */
public class eliminarPersonaje extends JFrame {

    private JTextField txtBuscar;
    private JButton btBuscar;
    private JLabel LbPencontrado; //de alguna manera u otra debo simplificar los nombres o me dara retraso mental
    private JButton btEliminar;

    //El indice que nos dira si el personaje esta o no
    private int Pecontrado = -1;

    public eliminarPersonaje() {
        setTitle("Eliminar Personaje");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 20));

        JLabel tirulo = new JLabel("Eliminar Personaje", SwingConstants.CENTER);
        tirulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(tirulo);

        panel.add(new JLabel("Escribe ACA el ID o el nombre del personaje que deseas MATAR"));
        txtBuscar = new JTextField();
        panel.add(txtBuscar);
        
        JPanel pBoton = new JPanel(new FlowLayout());
        btBuscar = new JButton("Buscar");
        pBoton.add(btBuscar);
        panel.add(pBoton);
        
        LbPencontrado = new JLabel("Tamus buscando", SwingConstants.CENTER);
        LbPencontrado.setVisible(false);
        panel.add(LbPencontrado);

        btEliminar = new JButton("Eliminar Personaje");
        btEliminar.setVisible(false);
        panel.add(btEliminar);
        //añadimos todo eso de arriba al panel o como le dire de ahora en adelante, plantilla
        add(panel);

        btBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PersonajeEli(); //llamado al metodo que hare alli abajito
            }
        });

        btEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confimarEliminar(); //otro metodo que hare mas mas abajo
            }
        });
    }
        //justo como prometi xd
private void PersonajeEli() {
        String Buscar = txtBuscar.getText().trim();
        Pecontrado = -1;
        LbPencontrado.setVisible(false);
        btEliminar.setVisible(false);

        if (Buscar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "INGRESA EL NOMBRE O EL ID CTM", "Error 020", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < Personajes.CantPersonajes; i++) {
            String id = Personajes.personaje[i][Personajes.ID];
            String nombre = Personajes.personaje[i][Personajes.NOMBRE];

            if (id.equals(Buscar) || nombre.equalsIgnoreCase(Buscar)) {
                Pecontrado = i;
                String Necontrado = Personajes.personaje[i][Personajes.NOMBRE];
                LbPencontrado.setText("El personaje a eliminar es: " + Necontrado);
                LbPencontrado.setVisible(true);
                btEliminar.setVisible(true);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No se ha encontrado al personaje, f por ti", "Error 021", JOptionPane.ERROR_MESSAGE);
    }
    private void confimarEliminar() {
        if (Pecontrado != -1) {
            String nEliminar = Personajes.personaje[Pecontrado][Personajes.NOMBRE];
            int Confirmar = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estas seguro de eliminar a: " + nEliminar + "?",
                    "Confirmar Eliminarcion",
                    JOptionPane.YES_NO_OPTION);
            if(Confirmar == JOptionPane.YES_OPTION){
                EliminarPersonaje(Pecontrado);
                JOptionPane.showMessageDialog(this, nEliminar + " Se elimino del roster de personajes");
                dispose();
            }
        }
    }
    private void EliminarPersonaje(int in) {
        //Obtenemos el historial de peleas... si es que hay
        String historial = Personajes.personaje[in][Personajes.VICDERR];
        boolean GP = historial != null && !historial.isEmpty() && !historial.equals("0-0");

        if (GP) {
            //si el personaje no se ha peleado con nadie, vamos a decir que no hay nada, como el meme de avatar
            Personajes.personaje[in][Personajes.NOMBRE] = "Ya no esta";
            Personajes.personaje[in][Personajes.ARMA] = "No hay";
            Personajes.personaje[in][Personajes.HP] = "No hay";
            Personajes.personaje[in][Personajes.ATAQUE] = "No hay";
            Personajes.personaje[in][Personajes.VELOCIDAD] = "No hay";
            Personajes.personaje[in][Personajes.AGILIDAD] = "No hay";
            Personajes.personaje[in][Personajes.DEFENSA] = "No hay";
        } else {
            // si el personaje no se ha sacado la chucha con alguien mas lo eliminamos totalmente y ya
            for (int i = in; i < Personajes.CantPersonajes - 1; i++) {
                Personajes.personaje[i] = Personajes.personaje[i + 1];
            }
            Personajes.personaje[Personajes.CantPersonajes - 1] = new String[9];
            Personajes.CantPersonajes--;
        }
    }
}
