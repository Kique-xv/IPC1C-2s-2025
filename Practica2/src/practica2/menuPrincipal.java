package practica2;
//una clase para el menu... 

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author kiquemarroquin
 */
public class menuPrincipal extends JFrame {

    public menuPrincipal() {
        setTitle("Arena USAC--------- Menú Principal");
        //el tamaño de la venta
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//se cierra le ventana la app se termina

        //un panel con un GridLayour de 10 filas y  una columna (por las opciones)
        JPanel panel = new JPanel(new GridLayout(10, 1, 10, 10));

        //un tityulo bonito para el menu
        JLabel titulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        panel.add(titulo);

        //creamos TODOS LOS BOTONES PARA CADA OPCION
        JButton btAgregar = new JButton("1. Agregar Personaje");
        JButton btModificar = new JButton("2. Modificar Personaje");
        JButton btEliminar = new JButton("3. Eliminar Personaje");
        JButton btVer = new JButton("4. Ver Personajes Registrados");
        JButton btSimular = new JButton("5. Simulacion de Combates");
        JButton btHistorial = new JButton("6. Ver Historial de Batallas");
        JButton btBuscar = new JButton("7. Buscar Personaje");
        JButton btGuardar = new JButton("8. Guardar y Cargar Estado del Sistema ");
        JButton btYO = new JButton("9. Ver Datos del Estudiante ");

        panel.add(btAgregar);
        panel.add(btModificar);
        panel.add(btEliminar);
        panel.add(btVer);
        panel.add(btSimular);
        panel.add(btHistorial);
        panel.add(btBuscar);
        panel.add(btGuardar);
        panel.add(btYO);

        add(panel);

    }

}
