package practica2;
//una clase para el menu... 
//bueno. para que coexistan las dos ventanas esta sera la "Clase principal xd"

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        //accion del boton agregar personaje
        btAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Personajes agregarVentana = new Personajes();
                agregarVentana.setVisible(true);
            }
        });
        JButton btModificar = new JButton("2. Modificar Personaje");
        //accion del boton de modificar personajes
        btModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarPersonaje modificarVentana = new modificarPersonaje();
                modificarVentana.setVisible(true);
            }
        });

        JButton btEliminar = new JButton("3. Eliminar Personaje");
        //Accion del boton 3
        btEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPersonaje eliminarVentana = new eliminarPersonaje();
                eliminarVentana.setVisible(true);
            }
        });

        JButton btVer = new JButton("4. Ver Personajes Registrados");
        //Accion del boton  de ver personajes :3
        btVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verPersonaje verVentana = new verPersonaje();
                verVentana.setVisible(true);
            }
        });

        JButton btSimular = new JButton("5. Simulacion de Combates");
        //Accion boton de MAdrazos
        btSimular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimPelea combateVentana = new SimPelea();
                combateVentana.setVisible(true);
            }
        });
        JButton btHistorial = new JButton("6. Ver Historial de Batallas");
        //Accion del boton de historial
        btHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historialCombate VentanaHistorial = new historialCombate();
                VentanaHistorial.setVisible(true);
            }
        });

        JButton btBuscar = new JButton("7. Buscar Personaje");
        // accion del boton este...
        btBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarPer VentanaBuscar = new BuscarPer();
                VentanaBuscar.setVisible(true);
            }
        });

        JButton btGuardar = new JButton("8. Guardar Estado del Sistema ");
        //accion del boton guardar
        btGuardar.addActionListener(e -> gestorAchivo.GuardarPesonaje());

        JButton btCargar = new JButton("9. Cargar Estado del sistema");
        //accion bon cargar
        btCargar.addActionListener(e -> gestorAchivo.CargarPersonajes());

        JButton btLimpiar = new JButton("10. Limpiar Estado del Sistema ");
//Accion del boton  de limpiar
        btLimpiar.addActionListener(e -> gestorAchivo.BorrarDatos());

        JButton btBitcora = new JButton("11. limpiar bitacora de acciones");
        //accion boton de bitacora, 
        btBitcora.addActionListener(e -> validarAccion.MostrarBit());

        JButton btYO = new JButton("12. Ver Datos del Estudiante ");
        btYO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                YOOOOO ventanaYO = new YOOOOO();// sip soy yo, que mas xd
                ventanaYO.setVisible(true);
            }
        });

//en mac el menu se ve bonito la verdad, VIVA MAC y sus precios no
        panel.add(btAgregar);
        panel.add(btModificar);
        panel.add(btEliminar);
        panel.add(btVer);
        panel.add(btSimular);
        panel.add(btHistorial);
        panel.add(btBuscar);
        panel.add(btGuardar);
        panel.add(btCargar);
        panel.add(btLimpiar);
        panel.add(btBitcora);
        panel.add(btYO);

        add(panel);

    }
}
// cada vez estas cosas se ponen mas dificiles