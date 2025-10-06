package proshecto2;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author kiquemarroquin
 */
public class ModProducto extends JFrame {

    // campos busqueda xd
    private JTextField txtCodBuscar;
    private JButton btBuscar;

    //campos para la modificar
    private JTextField txtNnombre;
    private JLabel LbCategoria;
    private JTextField txtNprecio;
    private JTextField txtNstock;

    //campos Para modifcar
    private JLabel lbAtributo;
    private JTextField txtAtributo;
    private JButton btGuardarC;

    private String CodigoAct = "";
    private GestionProductos ventana2;

    public ModProducto(GestionProductos ventana2) {
        this.ventana2 = ventana2;
        //ya se la saben
        setTitle("Modificar Productos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Codigo a buscar: ", SwingConstants.RIGHT));

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtCodBuscar = new JTextField(15);
        panel.add(txtCodBuscar);

        gbc.gridx = 2;
        gbc.gridy = 0;
        btBuscar = new JButton("Buscar Productos: ");
        panel.add(btBuscar, gbc);

        //separador
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(new JSeparator(), gbc);

        //Categoria solo para la lectura
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Ingresa el nuevo nombre del producto: ", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        txtNnombre = new JTextField(20);
        panel.add(txtNnombre);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        lbAtributo = new JLabel("Ingrese el Atributo Especifico :", SwingConstants.RIGHT);
        panel.add(lbAtributo, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        txtAtributo = new JTextField(20);
        panel.add(txtAtributo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        btGuardarC = new JButton("Guardar cambios");
        panel.add(btGuardarC, gbc);
        //accion de los botones 
        btBuscar.addActionListener(e -> BuscarProd());
        btGuardarC.addActionListener(e -> ConfirmMod());
//la parte de NO habilitar los campos de edicion o modificacion sea el caso lpm
        habilitarMod(false);
        add(panel);
    }

    //un metodo para habilitar los campos para modificarlos
    private void habilitarMod(boolean habilitar) {
        txtNnombre.setEnabled(habilitar);
        txtAtributo.setEnabled(habilitar);
        btGuardarC.setEnabled(habilitar);

        //desabilitamos la busqueda cuadno se busca
        txtCodBuscar.setEnabled(!habilitar);
        btBuscar.setEnabled(!habilitar);
    }

    //buscar metodo
    private void BuscarProd() {
        String codigo = txtCodBuscar.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes de ingrear el codigo del porducto", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Productos producto = AdminDProductos.BuscarProd(codigo);

        if (producto != null) {
            CodigoAct = producto.getCodigo(); //lo guardamos el codigo para la modificacion de los  atributos(no el codigo xd)
            habilitarMod(true);

            //cargamos el nombre
            txtNnombre.setText(producto.getNombre());

            // cargamos al atributo especifico con el hermoso polimorfismo
            if (producto instanceof productoTec) {
                productoTec pT = (productoTec) producto;
                lbAtributo.setText("Escribe los meses de Garanta del producto: ");
                txtAtributo.setText(String.valueOf(pT.getMesesGarantia()));
            } else if (producto instanceof productoComida) {
                productoComida pCom = (productoComida) producto;
                txtAtributo.setText(pCom.getFechaVencer());
            } else {
                JOptionPane.showMessageDialog(this, "tipo de producto desconocido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "El producto encontrado es: " + producto.getNombre(), "Producto Encontrado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "El producto no fue encontrado ", " Error", JOptionPane.ERROR_MESSAGE);
            CodigoAct = "";
            habilitarMod(false);
        }
    }

    //
    private void ConfirmMod() {
        if (CodigoAct.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes de buscar un producto primero ", " Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nNombre = txtNnombre.getText().trim();
        String nAtributo = txtAtributo.getText().trim();

        //validacion para los campos vacios
        if (nNombre.isEmpty() || nAtributo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben de ser llenados ", " Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //es para validar que no la kguemos en el atributo se de tecnologia y no pongan un numero o pongan un numero que sea menor de 0
        if (LbCategoria.getText().equals("TECNOLOGIA")) {
            try {
                int garantia = Integer.parseInt(nAtributo);
                if (garantia <= 0) {
                    JOptionPane.showMessageDialog(this, "El valor de la garantia debe de ser un numero positivo", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El valor de la garantia debe de ser un numero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //hacemos un llamado al gestor
        if (AdminDProductos.ModProductos(CodigoAct, nNombre, nAtributo)) {
            JOptionPane.showMessageDialog(this, "El producto fue modificado", "La modificacion salio bien ", JOptionPane.INFORMATION_MESSAGE);
          if(ventana2 != null){
              ventana2.ActualizarTabla();
          }
            this.dispose(); //cerramos la venta al terminar :3
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar el producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
