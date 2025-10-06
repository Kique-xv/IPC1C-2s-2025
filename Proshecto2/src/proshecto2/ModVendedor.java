package proshecto2;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author kiquemarroquin
 */
public class ModVendedor extends JFrame {

    private JTextField txtCodBuscar;
    private JTextField txtNnombre;
    private JPasswordField txtNcontraseña;
    private JButton btBuscar;
    private JButton btModificar;

    private GestionVendedor ventana1;
    public ModVendedor(GestionVendedor ventana1) {
        this.ventana1 = ventana1;
        setTitle("Modificar Vendedor");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //componentes de buscar
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Escriba el codigo del vendedor a buscar: ", SwingConstants.RIGHT), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtCodBuscar = new JTextField(15);
        panel.add(txtCodBuscar, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        btBuscar = new JButton("Buscar");
        panel.add(btBuscar, gbc);

        //componentes para la modficacion, 
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(new JSeparator(), gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Escriba el nuevo nombre del vededor: ", SwingConstants.RIGHT), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        txtNnombre = new JTextField(20);
        txtNnombre.setEnabled(false);
        panel.add(txtNnombre, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Escriba la nueva contraseña: ", SwingConstants.RIGHT), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        txtNcontraseña = new JPasswordField(20);
        txtNcontraseña.setEnabled(false);
        panel.add(txtNcontraseña, gbc);
        
        btModificar = new JButton("Guardar cambios");
        btModificar.setEnabled(false);
        panel.add(btModificar, gbc);

        //acciones de los botones
        btBuscar.addActionListener(e -> buscarVendedor());
        btModificar.addActionListener(e -> ConfirmarEdit());

        add(panel, BorderLayout.NORTH);
    }

    private void buscarVendedor() {
        String Codigo = txtCodBuscar.getText().trim();
        if (Codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes de ingresar un codigo para buscar", "Error 07", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Vendedor vendedor = AdminDVendedores.BuscarVendedor(Codigo);//;D
        if (vendedor != null) {
            JOptionPane.showMessageDialog(this, "Vendedor encontrado: " + vendedor.getNombre() + "", " Accion exitosa", JOptionPane.INFORMATION_MESSAGE);

            //habilitamos las areas de texto
            txtNnombre.setText(vendedor.getNombre()); //cargamos el nombre del vendedor
            txtNcontraseña.setText("");//dejamos la contraseña vacia

            txtNnombre.setEnabled(true);
            txtNcontraseña.setEnabled(true);
            btModificar.setEnabled(true);

            txtCodBuscar.setEnabled(false);
            btBuscar.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "El vendedor no fue encontrado", "Error 08", JOptionPane.ERROR_MESSAGE);
            //limpiamos y desabilitamos las areas de testo
            txtNnombre.setText("");
            txtNcontraseña.setText("");
            txtNnombre.setEnabled(false);
            txtNcontraseña.setEnabled(false);
            btModificar.setEnabled(false);
        }
    }

    private void ConfirmarEdit() {
        String codigo = txtCodBuscar.getText().trim();
        String Nnombre = txtNnombre.getText().trim();
        String NContraseña = new String(txtNcontraseña.getPassword()).toLowerCase();
        //validacion para que los campos esten llenos
        if (Nnombre.isEmpty() || NContraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y contraseña no pueden de estar vacios", "Error 09", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (AdminDVendedores.ModVendedor(codigo, Nnombre, NContraseña)) {//llamado al metodo de modifcar vendedro, me ahorro trabajo
            JOptionPane.showMessageDialog(this, "vendedor modificado", "Modifcacion exitosa", JOptionPane.INFORMATION_MESSAGE);
           if (ventana1 != null) {
             ventana1.actualizarTabla();
         }
            this.dispose(); //cerrar al terminar
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar el vendedor", "Error 10", JOptionPane.ERROR_MESSAGE);
        }
    }
}
