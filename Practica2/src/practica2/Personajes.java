package practica2;

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
public class Personajes extends JFrame {

    //una matriz de 50*9 para guardar a los personajes
    public static String[][] personaje = new String[100][9];

    //Un contador estatico para que generar el ID sea unico y poderlos rastrear
    public static int CantPersonajes = 0;
    public static int UltID = 0;
    public static int contadorID = 1;
    // contantes para la matriz 
    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int ARMA = 2;
    public static final int HP = 3;
    public static final int ATAQUE = 4;
    public static final int VELOCIDAD = 5;
    public static final int AGILIDAD = 6;
    public static final int DEFENSA = 7;
    public static final int VICDERR = 8;//Victorias y derrotas
    public static final int NUMCAMPOSPERSONAJE = 9;

    //componentes de esta interfaz
    private JTextField txtNombre;
    private JTextField txtArma;
    private JTextField txtHp;
    private JTextField txtAtaque;
    private JTextField txtVelocidad;
    private JTextField txtAgilidad;
    private JTextField txtDefensa;

    //una clase publica para hacer llamados en todas partes mejor, ya me irritede hacer lo mismo ashudddaaaa
    public static int buscarPer(String busqueda) {
        if (busqueda == null || busqueda.trim().isEmpty()) {
            return -1;
        }
        String buscar = busqueda.trim().toLowerCase();

        for (int i = 0; i < CantPersonajes; i++) {
            String id = personaje[i][ID];
            String nombre = personaje[i][NOMBRE].toLowerCase();

            if (id.equals(buscar) || nombre.equals(buscar)) {
                return i; //devolveremos el indice del personaje que ecnotramos
            }
        }
        return -1;
    }

    //ahora si la clase para guardar el personaje 
    public Personajes() {
        setTitle("Agregar Personaje");
        setSize(900, 900);
        setLocationRelativeTo(null);//para CENTRARLA
        //solo se va a ocultar para poder reusarla, viva el reciclaje
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        //el titulo del panel NO ARRIBITA si no que... bueno DENTRO del panel
        JLabel titulo = new JLabel("Agregue un nuevo personaje", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titulo);
        panel.add(new JLabel("")); //un espacio en blanco como el System.out.println();

        // los apartados que se deben de ser llenados para agregar un personaje
        panel.add(new JLabel("Escribe el nombre que quieras: "));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Elige el arma de tu preferencia: "));
        txtArma = new JTextField();
        panel.add(txtArma);

        panel.add(new JLabel("Escoge los puntos de vida del personaje (100 a 500):"));
        txtHp = new JTextField();
        panel.add(txtHp);

        panel.add(new JLabel("Escribe los puntos de ataque del personaje (10 a 100)"));
        txtAtaque = new JTextField();
        panel.add(txtAtaque);

        panel.add(new JLabel("Inserte la velocidad del personaje (1 a 10) :"));
        txtVelocidad = new JTextField();
        panel.add(txtVelocidad);

        panel.add(new JLabel("Escriba la agilidad del personaje(1 a 10) :"));
        txtAgilidad = new JTextField();
        panel.add(txtAgilidad);

        panel.add(new JLabel("Esribe la defensa que tendra el personaje (1 a 50)"));
        txtDefensa = new JTextField();
        panel.add(txtDefensa);

        //un boton para guardar los daros 
        JButton btGuardar = new JButton("Guardar cambios");
        panel.add(btGuardar);

        add(panel);

        btGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPersonaje();
            }
        });
    }

    private void guardarPersonaje() {
        if (CantPersonajes > 100) {

            JOptionPane.showMessageDialog(this, "El limte de personajes se alcanzo,borra alguno", "Error 001", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //todo esto es para que no hayan espacios sin llenar :3 muy intelignet
        String nombre = txtNombre.getText();
        if (nombre.isEmpty() || txtArma.getText().isEmpty() || txtHp.getText().isEmpty() || txtAtaque.getText().isEmpty() || txtVelocidad.getText().isEmpty() || txtAgilidad.getText().isEmpty() || txtDefensa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llena todos los apartados :D", "Error 002", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int hp, ataque, velocidad, agilidad, defensa;
        try {
            hp = Integer.parseInt(txtHp.getText());
            if (hp < 100 || hp > 500) {
                JOptionPane.showMessageDialog(this, "Los puntos de vida deben de ser un numero entero de 100 a 500", "Error 003", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ataque = Integer.parseInt(txtAtaque.getText());
            if (ataque < 10 || ataque > 100) {
                JOptionPane.showMessageDialog(this, "Los puntos de ataque deben de ser un numero entero de 10 a 100", "Error 004", JOptionPane.ERROR_MESSAGE);
                return;
            }
            velocidad = Integer.parseInt(txtVelocidad.getText());
            if (velocidad < 1 || velocidad > 10) {
                JOptionPane.showMessageDialog(this, "La velocidad debe de ser un numero entero de 1 a 10", "Error 005", JOptionPane.ERROR_MESSAGE);
                return;
            }
            agilidad = Integer.parseInt(txtVelocidad.getText());
            if (agilidad < 1 || agilidad > 10) {
                JOptionPane.showMessageDialog(this, "La agilidad debe de ser un numero entero de 1 a 10", "Error 006", JOptionPane.ERROR_MESSAGE);
                return;
            }
            defensa = Integer.parseInt(txtDefensa.getText());
            if (defensa < 1 || defensa > 50) {
                JOptionPane.showMessageDialog(this, "La defensa debe de ser un numero entero de 100 a 500", "Error 007", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los apartados que son numericos deben ser llenados con eso... pos numeros", "Error 008", JOptionPane.ERROR_MESSAGE);
            return;
        }

        personaje[CantPersonajes][ID] = String.valueOf(Personajes.contadorID);
        personaje[CantPersonajes][NOMBRE] = txtNombre.getText();
        personaje[CantPersonajes][ARMA] = txtArma.getText();
        personaje[CantPersonajes][HP] = txtHp.getText();
        personaje[CantPersonajes][ATAQUE] = txtAtaque.getText();
        personaje[CantPersonajes][VELOCIDAD] = txtVelocidad.getText();
        personaje[CantPersonajes][AGILIDAD] = txtAgilidad.getText();
        personaje[CantPersonajes][DEFENSA] = txtDefensa.getText();
        personaje[CantPersonajes][VICDERR] = "0-0"; //Inciamos esto con nada

        CantPersonajes++; //se suma un personaje mas al plantel xdddd
        contadorID++; //otro chups wuuuuu
        JOptionPane.showMessageDialog(this, txtNombre.getText() + " se ha agregado al roster de personajes ");
        txtNombre.setText("");
        txtArma.setText("");
        txtHp.setText("");
        txtAtaque.setText("");
        txtVelocidad.setText("");
        txtAgilidad.setText("");
        txtDefensa.setText("");
    }
}
