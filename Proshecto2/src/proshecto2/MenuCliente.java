//el menu del cliente que es la parte Medio facil xd
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
public class MenuCliente extends JFrame {

    private Cliente clienteAct;

    public MenuCliente(Cliente cliente) {//pa la tabla xd
        this.clienteAct = cliente;
        //la ventana ya sabes no quiero explicar esto mas veces de lo que sea necesario xd
        setTitle("Bienvenido al menu de cliente " + cliente.getNombre());
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //la bienvenida xd
        JLabel lbBienvenida = new JLabel("¿Que deseas hacer hoy?", SwingConstants.CENTER);//como si pudieras hacer mucho :(
        lbBienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        add(lbBienvenida, BorderLayout.NORTH);

        //la parte de los botones
        JPanel panelBot = new JPanel(new GridLayout(3, 1, 15, 15));

        JButton btProductos = new JButton("Ver productos y compras");
        JButton btCarrito = new JButton("Ver carrito de compras");
        JButton btHistorial = new JButton("Ver historial de compras");
        JButton btSalir = new JButton("Cerrar sesion");

        panelBot.add(btProductos);
        panelBot.add(btCarrito);
        panelBot.add(btHistorial);

        //la parte las acciones de los botones
        btProductos.addActionListener(e -> {
            new CatalogoProductos(clienteAct).setVisible(true);
        });
        
        btCarrito.addActionListener(e -> {
            new CarritoCompra(clienteAct).setVisible(true);
        });
       
        btHistorial.addActionListener(e ->{
            new HistorialCompras(clienteAct).setVisible(true);
        });
        
        btSalir.addActionListener(e -> CerrarSesion());

        add(panelBot, BorderLayout.CENTER);
        add(btSalir, BorderLayout.SOUTH);
    }

    private void CerrarSesion() {
        this.dispose();
        new IngresarUsuarios().setVisible(true);
    }
}
