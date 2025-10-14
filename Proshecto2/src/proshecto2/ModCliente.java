//una clase para modificar al cliente
package proshecto2;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 *
 * @author kiquemarroquin
 */
public class ModCliente extends JFrame {

    //para actualizar la tabla
    private GestionClientes ventana;
    //las chingaderas del cliente
    private JTextField txtCodBuscar, txtNombre, txtCumple, txtContraseña;
    private JComboBox<String> cbGenero;
    private JButton btBuscar, btguardar;

    //el codigo este no se modificar
    private String CodAct = ""; //para guardar el codigo del cliente 

    public ModCliente(GestionClientes ventana) {
        this.ventana = ventana;
        //las cosas de la ventana
        setTitle("Modificar Cliente");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //la parte de buscar
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Escribe el Id del cliente a quien quieras modificar"));

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtCodBuscar = new JTextField(15);
        panel.add(txtCodBuscar, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        btBuscar = new JButton("Buscar");
        panel.add(btBuscar, gbc);

        //un separador muy chulo
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(new JSeparator(), gbc);
        gbc.gridwidth = 1; //un separador

        //los apartados para editar
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Ingrese el nombre del cliente"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridwidth = 2;
        txtNombre = new JTextField();
        panel.add(txtNombre, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Ingrese el genero del cliente"));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        cbGenero = new JComboBox<>(new String[]{"M", "F"});
        panel.add(cbGenero, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Ingrese el cumpleaños del cliente(DD/MM/YYYY)"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        txtCumple = new JTextField();
        panel.add(txtCumple, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Ingrese la contraseña"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        txtContraseña = new JTextField();
        panel.add(txtContraseña, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 2;
        gbc.gridy = 6;
        btguardar = new JButton("Guardar Cambios");
        panel.add(btguardar, gbc);

        //acciones de los botones
        btBuscar.addActionListener(e -> buscarCliente());
        btguardar.addActionListener(e -> guardarCambios());

        //estado de los campos 
        habilitarEdit(false);

        add(panel);
    }

    private void habilitarEdit(boolean habilitar) {
        txtNombre.setEnabled(habilitar);
        cbGenero.setEnabled(habilitar);
        txtCumple.setEnabled(habilitar);
        txtContraseña.setEnabled(habilitar);
        btguardar.setEnabled(habilitar);

        // bloquemos la búsqueda mientras estamos editamos
        txtCodBuscar.setEnabled(!habilitar);
        btBuscar.setEnabled(!habilitar);
    }
    //pa buscar la madre de los clientes, no a su madre osea ya sabes no jodas 

    private void buscarCliente() {
        String codigo = txtCodBuscar.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un código para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Cliente cliente = AdminDClientes.BuscarCliente(codigo);
        //si lo encontramos por el codigo hablitamos el espacio para que se pueda editar
        if (cliente != null) {
            CodAct = cliente.getId();
            txtNombre.setText(cliente.getNombre());
            cbGenero.setSelectedItem(cliente.getGenero());
            txtCumple.setText(cliente.getCumpleaños());
            txtContraseña.setText(cliente.getcontraseña());

            habilitarEdit(true);
            JOptionPane.showMessageDialog(this, "Cliente encontrado, ya puedes editarlo", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún cliente con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            habilitarEdit(false);
        }
    }
    //para guardar cambios

    private void guardarCambios() {
        String nombre = txtNombre.getText().trim();
        String genero = (String) cbGenero.getSelectedItem();
        String cumpleaños = txtCumple.getText().trim();
        String contraseña = txtContraseña.getText().trim();
        ///por si no llenan nada xd
        if (nombre.isEmpty() || cumpleaños.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llenar todos los apartados", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (AdminDClientes.ModCliente(CodAct, nombre, contraseña, genero, nombre)) {
            JOptionPane.showMessageDialog(this, "El cliente ha sido modificado :D", "Exito", JOptionPane.INFORMATION_MESSAGE);
            if (ventana != null) {
                ventana.actualizarTabla();
            }
            this.dispose();
        } else {
            //por si las moscas ¿si?
            JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar los cambios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}