//jeje dice proshecto xd
//una clase que bueno... su nombre lo dice todo
package proshecto2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;//xd
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author kiquemarroquin
 */
public class IngresarUsuarios extends JFrame {

    private JTextField txtID;
    private JPasswordField txtContraseña;
    private JButton btIngresar;

    public IngresarUsuarios() {
        setTitle("Hip Shop------- Incio de sesion");//ME NIEGO DECIR SANCARLISTA SHOP SUENA  HORRIBLE COMO NOMBRE
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); //3 filas y 2 comlimnas

        //partes de la interfaz
        panel.add(new JLabel("ingrese el codigo ID de usuario", SwingConstants.RIGHT));
        txtID = new JTextField(15);
        panel.add(txtID);

        panel.add(new JLabel("ingrese su constraseña", SwingConstants.RIGHT));
        txtContraseña = new JPasswordField(15);
        panel.add(txtContraseña);

        //boton de ingreso
        btIngresar = new JButton("Inciar sesion");
        panel.add(new JLabel(""));
        panel.add(btIngresar);

        add(panel, BorderLayout.CENTER);

        btIngresar.addActionListener(e -> IntentoIngresar());//un metdo que ta alli abajo
    }

    private void IntentoIngresar() {
        String id = txtID.getText().trim();
        String Contraseña = new String(txtContraseña.getPassword());

        Usuarios UsuarioAutenticar = AdminDUsuarios.autenticazion(id, Contraseña);
        if (UsuarioAutenticar != null) {
            String tipo = UsuarioAutenticar.getTipoUsuario();
            if ("ADMIN".equals(tipo)) {
                JOptionPane.showMessageDialog(this, "Bienvenodo admin", "Acceso otorgado", JOptionPane.INFORMATION_MESSAGE);
                new MenuAdmin().setVisible(true); //abre el menu de admin
                this.dispose();
                
            } else if ("VENDEDOR".equals(tipo)) {
                JOptionPane.showMessageDialog(this, "Bienvenido Vendedor", "Acceso otorgado", JOptionPane.INFORMATION_MESSAGE);
                //new MenuVendedor.setVisible(true) cuadno ya este ese modulo
                this.dispose();
            } else if ("CLIENTE".equals(tipo)) {
                JOptionPane.showMessageDialog(this, "Bienvenido Cliente ", "Acceso otorgado", JOptionPane.INFORMATION_MESSAGE);
                //new MenuCliente, misma cosa
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "El usuario no esta defindo, Intenta otra vez", "Error de autenticacion", JOptionPane.ERROR_MESSAGE);
            }
            //ACA IRA LO DE  ABRIR EL MENU DE ADMIN, VENDEDOR, CLIENTE, CON LO DE SIEMPRE, EL SETVISIBLE(TRUE)
        } else {
            JOptionPane.showMessageDialog(this, "El Id o la contaseña son incorrectos, Intenta otra vez", "Error de autenticacion", JOptionPane.ERROR_MESSAGE);
            txtContraseña.setText(""); //limpiamos el campo de contraseña
        }
    }
}
