package proshecto2;

import java.awt.BorderLayout;
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
public class CrearVendedor extends JFrame {

    private GestionVendedor ventana1;

    private JTextField txtCodigo, txNombre, txtContraseña;
    private JComboBox<String> JcGenero;
    private JButton btCrear;

    public CrearVendedor(GestionVendedor ventana1) {
        this.ventana1 = ventana1;
        setTitle("Crear nuevo vendedor");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        //lo que aparecera en el panel
        panel.add(new JLabel("Escriba el codigo: "));
        txtCodigo = new JTextField();
        panel.add(txtCodigo);

        panel.add(new JLabel("Escriba el nombre y apellido del vendedor : "));
        txNombre = new JTextField();
        panel.add(txNombre);

        panel.add(new JLabel("Ingrese el genero del vendedor :"));
        JcGenero = new JComboBox<>(new String[]{"M", "F"});//respetamos la viejas costumbres
        panel.add(JcGenero);

        panel.add(new JLabel("Escriba la Contraseña para el vendedor: "));
        txtContraseña = new JTextField();
        panel.add(txtContraseña);

        btCrear = new JButton("Crear Vendedor");
        panel.add(new JLabel());//un espacito en blanco para que se vea mejor
        panel.add(btCrear);

        btCrear.addActionListener(e -> crearVendedor());

        add(panel, BorderLayout.CENTER);
    }

    //la imaginacion se feue de sabatico
    private void crearVendedor() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txNombre.getText().trim();
        String contraseña = txtContraseña.getText().trim();
        String genero = (String) JcGenero.getSelectedItem();
//las validaciones de siempre
        if (codigo.isEmpty() || nombre.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben de ser llenados", "Error de validacion", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (AdminDVendedores.CreacionVendedor(codigo, nombre, genero, contraseña)) {
            JOptionPane.showMessageDialog(this, " Vendedor " + nombre + " Creado, ventas realizadas 0 ", "Vendedor creado", JOptionPane.INFORMATION_MESSAGE);
            if (ventana1 != null) {
                ventana1.actualizarTabla();
            }
//limpieza de los campos despuesd de crear
            txtCodigo.setText("");
            txNombre.setText("");
            txtContraseña.setText("");
            JcGenero.setSelectedIndex(0);
        }
    }
}
