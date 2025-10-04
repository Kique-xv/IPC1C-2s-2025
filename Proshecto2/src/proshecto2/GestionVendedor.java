package proshecto2;

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
public class GestionVendedor extends JFrame {

    public GestionVendedor() {
        setTitle("Gestion de Vendedores");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JLabel titulo = new JLabel("Gestion de Vendedores ¿que deseas hacer?", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo);

        JButton btCrear = new JButton("Crear Nuevo Vendedor");
        JButton btModificar = new JButton("Modificar Vendedor");
        JButton btEliminar = new JButton("Eliminar Vendedor");
        JButton btCargar = new JButton("Carga masiva de vendedores");

        panel.add(btCrear);
        panel.add(btModificar);
        panel.add(btEliminar);
        panel.add(btCargar);

        //las acciones de los botones
        btCrear.addActionListener(e -> new CrearVendedor().setVisible(true));
     btModificar.addActionListener(e -> new ModVendedor().setVisible(true));
     btEliminar.addActionListener(e -> new EliminarVendedor().setVisible(true));
      btCargar.addActionListener(e -> new CargarVendedor().setVisible(true));
     add(panel, BorderLayout.CENTER);
     setVisible(true);
    }
}
