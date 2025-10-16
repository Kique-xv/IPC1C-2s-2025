//la clase para eliminar el cliente muajajaja
package proshecto2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author kiquemarroquin
 */
public class EliminarCliente extends JFrame {

    private JTextField txtCodElim;
    private JButton btEliminar;
    private GestionClientes ventana;//para la tabla xd

    public EliminarCliente(GestionClientes ventana) {
        this.ventana = ventana;

        //las cosas visuales de la ventana de eliminarclientes
        setTitle("Eliminar Cliente");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Codigo del cliente a eliminar"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtCodElim = new JTextField(15);
        panel.add(txtCodElim, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        btEliminar = new JButton("Elminar Cliente");
        panel.add(btEliminar, gbc);

        add(panel);
        btEliminar.addActionListener(e -> eliminarCliente());//metodo de alli abajo

    }

    private void eliminarCliente() {
        String codigo = txtCodElim.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un codigo para buscar", "Aviso", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //buscamos al cliente 
        Cliente cliente = AdminDClientes.BuscarCliente(codigo);

        if (cliente != null) {
            int Confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estas seguro de querer eliminar a:" + cliente.getNombre() + " ?",
                    "Eliminar cliente",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (Confirm == JOptionPane.YES_OPTION) {
                if (AdminDClientes.EliminarCliente(codigo)) {
                    JOptionPane.showMessageDialog(this, "El cliente se ha eliminado", "Eliminacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                    if (ventana != null) {
                        ventana.actualizarTabla();
                    }
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nose ha encontrado al cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
