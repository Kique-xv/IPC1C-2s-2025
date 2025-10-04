
package proshecto2;
//la gestion de productos es exaaaactamente igual al de vendedores

import java.awt.BorderLayout;
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
public class GestionProductos extends JFrame {

    public GestionProductos() {
        setTitle("Gestion de productos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JLabel titulo = new JLabel("Gestion de Productos ¿que deseas hacer?", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo);

        JButton btCrear = new JButton("Crear Nuevo producto");
        JButton btModificar = new JButton("Modificar Producto");
        JButton btEliminar = new JButton("Eliminar Producto");
        JButton btCargar = new JButton("Carga masiva de productos");

        panel.add(btCrear);
        panel.add(btModificar);
        panel.add(btEliminar);
        panel.add(btCargar);

        //las acciones de los botones
   
     setVisible(true);
    }
}


