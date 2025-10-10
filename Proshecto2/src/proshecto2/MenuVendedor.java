//menu para los vendedores
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
public class MenuVendedor extends JFrame {

    private Vendedor vendedorAct;

    public MenuVendedor(Vendedor v) {
        this.vendedorAct = v;

        setTitle("Menu de vendedor:" + v.getNombre());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //el panel
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        //añadimos la bienvenida
        JLabel lbBienvenida = new JLabel("Hola, " + v.getNombre() + " seas bienvenido", SwingConstants.CENTER);
        lbBienvenida.setFont(new Font("Arial", Font.BOLD, 20));
        add(lbBienvenida, BorderLayout.NORTH);

        //los botones del vendendor
        JButton btProductos = new JButton("Gestion de productos");
        JButton btClientes = new JButton("Gestion de Clientes");
        JButton btPedidos = new JButton("Pedidos Realizados");
        JButton btCerrar = new JButton("Cerrar Sesion");

        //posicion de botones
        panel.add(btProductos);
        panel.add(btClientes);
        panel.add(btPedidos);
        add(btCerrar, BorderLayout.SOUTH);
         add(panel, BorderLayout.CENTER);


//accion de los botones
     btProductos.addActionListener(e->  new ProductosAlmacen().setVisible(true));
        //b2
        //b3
        btCerrar.addActionListener(e -> CerrarSesion());
    }
    private void CerrarSesion() {
        //Cerrar esta ventana y regresar a la pantalla de ingreso
        this.dispose();
        new IngresarUsuarios().setVisible(true);
    }
}
