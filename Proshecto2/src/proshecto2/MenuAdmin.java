//para el menu de admin
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
public class MenuAdmin  extends JFrame{
    public MenuAdmin(){
        setTitle("Hip Shop---- Modulo Administrador");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        
        JLabel titulo = new JLabel("Modulo administrador" , SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 19));
        panel.add(titulo);
        
        JButton btVendedores = new JButton("1. Gestion de Vendedores");
        JButton btProductos = new JButton("2. Gestion de Productos");
        JButton btReportes = new JButton("3. Generar Reportes");
        
        panel.add(btVendedores);
        panel.add(btProductos);
        panel.add(btReportes);
        
        //acciones de los botones aca
        btVendedores.addActionListener(e -> {
            new GestionVendedor().setVisible(true);
        }  );
      add(panel, BorderLayout.CENTER);
    }
}
