//mejor sera una clase por cada boton, asi mas arreglado no?
package practica2;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
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
import static practica2.Personajes.personaje;

/**
 *
 * @author kiquemarroquin
 */
public class modificarPersonaje extends JFrame {

    private JTextField txtBuscar;
    private JButton btBuscar;

    //Esto para mostrar los datos actuales del personaje en cuestión
    private JLabel LbNombreAct;
    private JLabel LbArmaAct;
    private JLabel LbHpAct;
    private JLabel LbAtaqueAct;
    private JLabel LbVelocidadAct;
    private JLabel LbAgilidadAct;
    private JLabel LbDefensaAct;

    //CAmpos de texto para editar los nuevos campos
    private JTextField txtNArma;
    private JTextField txtNHp;
    private JTextField txtNAtaque;
    private JTextField txtNVelocidad;
    private JTextField txtNAgilidad;
    private JTextField txtNDefensa;

    private JButton btGuardar;

    //esto es para almacenar el indice del personaje que se encuentre en la matriz
    private int pEncontrar = -1;

    public modificarPersonaje() {
        setTitle("Modifcar Personaje");// titulo de la ventana
        setSize(500, 600);//tamaño de la ventana
        setLocationRelativeTo(null);// centramos la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//ya se la saben para que al cerrar se cierre el proceso

        JPanel panel = new JPanel(new GridLayout(12, 2, 10, 20));
        //titulo de la ventana
        JLabel titulo = new JLabel("Modificar personaje", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo);
        panel.add(new JLabel(""));

        //Apartado para buscar el personaje
        panel.add(new JLabel("Buscar por ID o Nombre"));
        txtBuscar = new JTextField();
        panel.add(txtBuscar);

        btBuscar = new JButton("Buscar");
        panel.add(btBuscar);
        panel.add(new JLabel(""));

        //Esto es para que se vean los datos actyuales
        LbNombreAct = new JLabel("Nombre: ");
        panel.add(LbNombreAct);
        LbArmaAct = new JLabel("Arma: ");
        panel.add(LbArmaAct);
        LbHpAct = new JLabel("Puntos de salud: ");
        panel.add(LbHpAct);
        LbAtaqueAct = new JLabel("Ataque: ");
        panel.add(LbAtaqueAct);
        LbVelocidadAct = new JLabel("Velocidad: ");
        panel.add(LbVelocidadAct);
        LbAgilidadAct = new JLabel("Agilidad: ");
        panel.add(LbAgilidadAct);
        LbDefensaAct = new JLabel("Defensa: ");
        panel.add(LbDefensaAct);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        //Campos que se van a llenar como los nuevos datos
        panel.add(new JLabel("Escoge el arma de tu preferncia: "));
        txtNArma = new JTextField();
        panel.add(txtNArma);

        panel.add(new JLabel("Ingresa los puntos de vida (100 a 500):"));
        txtNHp = new JTextField();
        panel.add(txtNHp);

        panel.add(new JLabel("Ingresa los puntos de ataque (10 a 100)"));
        txtNAtaque = new JTextField();
        panel.add(txtNAtaque);

        panel.add(new JLabel("Ingresa los puntos de velocidad (1 a 10)"));
        txtNVelocidad = new JTextField();
        panel.add(txtNVelocidad);

        panel.add(new JLabel("Ingresa los puntos de agilidad (1 a 10)"));
        txtNAgilidad = new JTextField();
        panel.add(txtNAgilidad);

        panel.add(new JLabel("Ingresa los puntos de defensa: "));
        txtNDefensa = new JTextField();
        panel.add(txtNDefensa);

        btGuardar = new JButton("Guardar Cambios");
        panel.add(btGuardar);

        //Agregamos todo esto a la ventana en cuestion
        add(panel);
        
        btBuscar.addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) {
              buscarPer();  
            }
            }  );
  
        //le agregamos la accion al boton de guardar
        btGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatos(); //Un llamado a un metodo
            }
        });
    } 
    private void buscarPer() {
        String buscar = txtBuscar.getText().trim();
        pEncontrar = -1; //resetear el indice

        if (buscar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa un ID o Nombre para busacar", "Error 011", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //el ciclo for de toda la vida para busacar la cadena de texto en la matriz de personaje
        for (int i = 0; i < Personajes.CantPersonajes; i++) {
            String Id = Personajes.personaje[i][Personajes.ID];
            String nombre = Personajes.personaje[i][Personajes.NOMBRE];

            if (Id.equals(buscar) || nombre.equalsIgnoreCase(buscar)) {
                pEncontrar = i;
                mostrarPer(i);  //Otro llamado
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "El personaje no se ha encontrado", "Error 012", JOptionPane.ERROR_MESSAGE);
    }

    //un metodo para mostrar el personaje, solo que aca es con JLabel en vez del System.out.println
    private void mostrarPer(int i) {
        String[] datos = Personajes.personaje[i];
        LbNombreAct.setText("Nombre: " + datos[Personajes.NOMBRE]);
        LbArmaAct.setText("Arma: " + datos[Personajes.ARMA]);
        LbHpAct.setText("Puntos de vida: " + datos[Personajes.HP]);
        LbAtaqueAct.setText("Ataque: " + datos[Personajes.ATAQUE]);
        LbVelocidadAct.setText("Velocidad: " + datos[Personajes.VELOCIDAD]);
        LbAgilidadAct.setText("Agilidad: " + datos[Personajes.AGILIDAD]);
        LbDefensaAct.setText("Defensa: " + datos[Personajes.DEFENSA]);

        //CArgamos los datos para editar para que sea mas facil modificarlos
        txtNArma.setText(datos[Personajes.ARMA]);
        txtNHp.setText(datos[Personajes.HP]);
        txtNAtaque.setText(datos[Personajes.ATAQUE]);
        txtNVelocidad.setText(datos[Personajes.VELOCIDAD]);
        txtNAgilidad.setText(datos[Personajes.AGILIDAD]);
        txtNDefensa.setText(datos[Personajes.DEFENSA]);
    }

    private void guardarDatos() {
        if (pEncontrar == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, busacr un personaje primero", "Error 013", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //validar los valores;
        try {
            int hp = Integer.parseInt(txtNHp.getText());
            if (hp < 100 || hp > 500) {
                JOptionPane.showMessageDialog(this, "Los puntos de vida deben de estar entre 100 y 500", "Error 014", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int ataque = Integer.parseInt(txtNAtaque.getText());
            if (ataque < 10 || ataque > 100) {
                JOptionPane.showMessageDialog(this, "Los puntos de ataque deben de estar entre 100 y 500", "Error 015", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int velocidad = Integer.parseInt(txtNVelocidad.getText());
            if (velocidad < 1 || velocidad > 10) {
                JOptionPane.showMessageDialog(this, "La agilidad debe de estar entre 1 a 10", "Error 016", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int agilidad = Integer.parseInt(txtNAgilidad.getText());
            if (agilidad < 1 || agilidad > 10) {
                JOptionPane.showMessageDialog(this, "La agilidad debe de estar entre 1 a 10", "Error 017", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int defensa = Integer.parseInt(txtNDefensa.getText());
            if (defensa < 1 || defensa > 50) {
                JOptionPane.showMessageDialog(this, "La defensa debe de estar entre 1 a 50", "Error 018", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Actualizacion de la matriz
            Personajes.personaje[pEncontrar][Personajes.ARMA] = txtNArma.getText();
            Personajes.personaje[pEncontrar][Personajes.HP] = String.valueOf(hp);
            Personajes.personaje[pEncontrar][Personajes.ATAQUE] = String.valueOf(ataque);
            Personajes.personaje[pEncontrar][Personajes.VELOCIDAD] = String.valueOf(velocidad);
            Personajes.personaje[pEncontrar][Personajes.AGILIDAD] = String.valueOf(agilidad);
            Personajes.personaje[pEncontrar][Personajes.DEFENSA] = String.valueOf(defensa);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los apartados que son numericos deben ser llenados con eso... pos numeros", " Error 019", JOptionPane.ERROR_MESSAGE);
        }
    }

    //esto es bueno para limpiar las entradas
    private void Limpiar() {
        txtBuscar.setText("");
        txtNArma.setText("");
        txtNHp.setText("");
        txtNAtaque.setText("");
        txtNVelocidad.setText("");
        txtNAgilidad.setText("");
        LbNombreAct.setText("");
        LbArmaAct.setText("");
        LbHpAct.setText("");
        LbAtaqueAct.setText("");
        LbVelocidadAct.setText("");
        LbAgilidadAct.setText("");
        LbDefensaAct.setText("");
        LbDefensaAct.setText("");
        pEncontrar = -1;
    }
}
