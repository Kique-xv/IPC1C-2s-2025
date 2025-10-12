//la parte de agregar stock
package proshecto2;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author kiquemarroquin
 */
public class AgregStock extends JFrame {

    private JTextField txtSagregar;
    private JTextField txtCbuscar;
    private JButton btAgregar;
    private ProductosAlmacen ventana;
    private Vendedor vendedorAct;

    public AgregStock(ProductosAlmacen ventana, Vendedor vendedor) {
        this.vendedorAct = vendedor;
        this.ventana = ventana;
        setTitle("Agregar Stock");
        setSize(400, 400);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Escribe el codigo del producto que quieras buscar: "));
        txtCbuscar = new JTextField(10);
        add(txtCbuscar);

        add(new JLabel("Inserte la cantidad del producto: "));
        txtSagregar = new JTextField(10);
        add(txtSagregar);

        add(new JLabel());
        btAgregar = new JButton("Agregar");

        btAgregar.addActionListener(e -> AgregarStock());
        add(btAgregar);
    }

    private void AgregarStock() {
        String codigo = txtCbuscar.getText().trim();
        String Stockstr = txtSagregar.getText().trim();

        if (codigo.isEmpty() || Stockstr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes de llenar el campo solicitado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int CantAgregar = Integer.parseInt(Stockstr);
            if (CantAgregar <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad del producto agregar debe de ser mayor a cero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //hacemos una invocacion del admin de productos
            boolean AgregadoB = AdminDProductos.agregarStock(codigo, CantAgregar, vendedorAct.getId(), vendedorAct.getNombre());

            if (AgregadoB) {
                JOptionPane.showMessageDialog(this, "La cantidad ha sido agregada", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                //Limpiar los campos 
                txtCbuscar.setText("");
                txtSagregar.setText("");
                if (ventana != null) {
                    ventana.ActualizarTabla();
                }

            } else {
                JOptionPane.showMessageDialog(this, "El producto no fue encontrado, o no se pudo realizar esta operacion", " Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe de ser un numero valido", " Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
