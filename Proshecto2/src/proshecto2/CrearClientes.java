//para crear los clientes, oseaaaa ya saben la ventana 
package proshecto2;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author kiquemarroquin
 */
public class CrearClientes extends JFrame {

    private GestionClientes ventana;
    private JTextField txtCodigo, txtNombre, txtCumple, txtContraseña;
    private JComboBox<String> cbGenero;
    private JButton btCrear;

    public CrearClientes(GestionClientes ventana) {
        this.ventana = ventana;
        setTitle("Crear nuevo Cliente");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        //los apartados de la creacion del cliente        
        panel.add(new JLabel("Escribe el codigo del cliente"));
        txtCodigo = new JTextField();
        panel.add(txtCodigo);

        panel.add(new JLabel("Escribe el nombre completo del Cliente"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Ingrese el genero del cliente"));
        cbGenero = new JComboBox<>(new String[]{"M", "F"});
        panel.add(cbGenero);

        panel.add(new JLabel("Ingrese la fecha de cumpleaños (DD/MM/YYYY)"));
        txtCumple = new JTextField();
        panel.add(txtCumple);

        panel.add(new JLabel("Escriba la contraseña del cliente"));
        txtContraseña = new JTextField();
        panel.add(txtContraseña);

        panel.add(new JLabel());//espacio en blacon

        btCrear = new JButton("Crear Cliente");
        panel.add(btCrear);

//accion del boton
        btCrear.addActionListener(e -> crearClientes());
        add(panel);
    }

    private void crearClientes() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String genero = (String) cbGenero.getSelectedItem();
        String cumple = txtCumple.getText().trim();
        String contraseña = txtContraseña.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || cumple.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Porfavor llenasr todos los campos", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (AdminDClientes.CreacionClientes(codigo, nombre, contraseña, genero, cumple)) {
            JOptionPane.showMessageDialog(this, "El cliente " + nombre + " ha sido creado", "Exito", JOptionPane.INFORMATION_MESSAGE);
            //llamado actualizar la tabla
            if (ventana != null) {
                ventana.actualizarTabla();
            }
            this.dispose();
        }
    }
}
