//para el menu de admin
package proshecto2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author kiquemarroquin
 */
public class MenuAdmin extends JFrame {

    public MenuAdmin() {
        setTitle("Hip Shop---- Modulo Administrador");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JLabel titulo = new JLabel("Modulo administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 19));
        panel.add(titulo);

        JButton btVendedores = new JButton(" Gestion de Vendedores");
        JButton btProductos = new JButton(" Gestion de Productos");
        JButton btReportes = new JButton("Generar Reportes");
        JButton btCerrar = new JButton("Cerrar sesion");

        panel.add(btVendedores);
        panel.add(btProductos);
        panel.add(btReportes);

        add(btCerrar, BorderLayout.SOUTH);
        //acciones de los botones aca
        btVendedores.addActionListener(e -> {
            new GestionVendedor().setVisible(true);
        });
        btProductos.addActionListener(e -> {
            new GestionProductos().setVisible(true);
        });

        btReportes.addActionListener(e -> MenuReportes());

        add(panel, BorderLayout.CENTER);

        btCerrar.addActionListener(e -> CerrarSesion());

    }

    private void CerrarSesion() {
        //cerrar esta ventana y regresar a la ventana de ingreso
        this.dispose();
        new IngresarUsuarios().setVisible(true);
    }

    private void MenuReportes() {
        //para los reportes
        String[] opciones = {"Inventario Simple"};
        String seleccion = (String) JOptionPane.showInputDialog(this,
                "Selecciona el reporte que desees generar",
                "Generar Reportes",
                JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]);
        
        if (seleccion != null) {
            switch (seleccion) {
                case "Inventario Simple":
                    GenerarReportes.GenerarReporteInventario();
                    break;

                //los demas
                default:
                    JOptionPane.showMessageDialog(this, "OPcion no existente");
            }
        }
    }
}
