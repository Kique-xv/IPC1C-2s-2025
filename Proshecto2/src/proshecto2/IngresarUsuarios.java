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

    public IngresarUsuarios() {// el nombre es siempre sera HIP SHOP xdddd, lei el nombre sancarlista shop y geniunamente ta feo xdd, nada encontra de la u, arriba usac, pero ship shop suena mejor, me niego a pensar lo opuesto xd, te das cuenta que estas leyendo esto y que yo en algun momento me puse esquiso para poner esta madre verdad, solo digo eh, alguien de los dos esta mal y posiblemente sea yo 
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
            Bitacora.RegistrarEvento(tipo, id, Bitacora.OP_Ingreso_Exitoso, Bitacora.ESTADO_EXITOSA, "Inicio de sesión correcto.");
            //manejo para cada tipo de usuario
            System.out.println("Tipo de usuario ingresado: " + tipo);
            AdminDUsuarios.AumentarSesiones();
            if ("ADMIN".equals(tipo)) {
                JOptionPane.showMessageDialog(this, "Bienvenido admin", "Acceso otorgado", JOptionPane.INFORMATION_MESSAGE);
                new MenuAdmin().setVisible(true); //abre el menu de admin
                this.dispose();

            } else if ("VENDEDOR".equals(tipo)) {
                Vendedor vendedorAct = AdminDVendedores.BuscarVendedor(id);
                if (vendedorAct != null) {
                    JOptionPane.showMessageDialog(this, "Bienvenido Vendedor", "Acceso otorgado", JOptionPane.INFORMATION_MESSAGE);
                    new MenuVendedor(vendedorAct).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error del sistema ", "Venderdor no registrado", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("CLIENTE".equals(tipo)) {

                Cliente clienteAct = AdminDClientes.BuscarCliente(id);
                if (clienteAct != null) {
                    JOptionPane.showMessageDialog(this, "Bienvenido Cliente ", "Acceso otorgado", JOptionPane.INFORMATION_MESSAGE);
                    //new MenuCliente, misma cosa
                    new MenuCliente(clienteAct).setVisible(true); //le metemos el objeto cliente al menu
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "El usuario no esta defindo, Intenta otra vez", "Error de autenticacion", JOptionPane.ERROR_MESSAGE);
            }
            //ACA IRA LO DE  ABRIR EL MENU DE ADMIN, VENDEDOR, CLIENTE, CON LO DE SIEMPRE, EL SETVISIBLE(TRUE)
        } else {
            JOptionPane.showMessageDialog(this, "El Id o la contaseña son incorrectos, Intenta otra vez", "Error de autenticacion", JOptionPane.ERROR_MESSAGE);
            Bitacora.RegistrarEvento(Bitacora.Tipo_Sistema, id, Bitacora.OP_Ingreso_Fallido, Bitacora.ESTADO_EXITOSA, "Inicio de sesión correcto.");
            txtContraseña.setText(""); //limpiamos el campo de contraseña
        }
    }
}
