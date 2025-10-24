//clase asociada a la ventana para crear productos :3
package proshecto2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
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
public class CrearProductos extends JFrame {

    //los campos que vamos allenar
    private JTextField txtCodigo, txtNombre, txtPrecio, txtStock;
    private JComboBox<String> cbCategoria;
    private JButton btCrear;

    private GestionProductos ventana2;
    //Campos para las categorias especificas
    private JLabel lbGarantia;
    private JTextField txtGarantia;
    private JLabel lbVencimiento;
    private JTextField txtVencimiento;

    public CrearProductos(GestionProductos ventana2) {
        this.ventana2 = ventana2;
        setTitle("Crear nuevo producto");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //ya saben si se cierra se termina el proceso
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

        //Los campos para llenar 
        panel.add(new JLabel("Ingresa el codigo del producto: "));
        txtCodigo = new JTextField();
        panel.add(txtCodigo);

        panel.add(new JLabel("Ingresa el nombre del producto: "));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Ingresae el precio del producto: "));
        txtPrecio = new JTextField();
        panel.add(txtPrecio);

        panel.add(new JLabel("Ingresa la cantidad disponible del producto: "));
        txtStock = new JTextField();
        panel.add(txtStock);

        panel.add(new JLabel("Elija la categotria del producto: "));
        cbCategoria = new JComboBox<>(new String[]{"TECNOLOGIA", "ALIMENTO", "OTROS"});
        panel.add(cbCategoria);

        //panel dinamicos para la garantia
        //para la garantia 
        lbGarantia = new JLabel("Ingrese la cantidad de meses de garantia del producto: ");
        txtGarantia = new JTextField();
        panel.add(lbGarantia);
        panel.add(txtGarantia);

        //para la fecha de vencimiento
        lbVencimiento = new JLabel("Ingrese la fecha de vencimiento del Producto (YYYY-MM-DD)");
        txtVencimiento = new JTextField();
        panel.add(lbVencimiento);
        panel.add(txtVencimiento);

        //boton para crear
        btCrear = new JButton("Crear producto");
        panel.add(new JLabel());
        panel.add(btCrear);

        //inciamos  la vista 
        ActVistaAtribut((String) cbCategoria.getSelectedItem()); //un metodo para la vista de la fecha de vencimiento y de la garantia

        //accion del boton 
        cbCategoria.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ActVistaAtribut((String) e.getItem());
            }
        });
        btCrear.addActionListener(e -> CrearProductos());
        add(panel, BorderLayout.CENTER);
    }

    //este metodo es para cambiar la visibilidad de los campos de garantia y fecha de vencimiento
    private void ActVistaAtribut(String categoria) {
        if (categoria.equals("TECNOLOGIA")) {
            lbGarantia.setVisible(true);
            txtGarantia.setVisible(true);
            lbVencimiento.setVisible(false);
            txtVencimiento.setVisible(false);
            txtVencimiento.setText(""); //limpiamos el campo que no vemos 
        } else if (categoria.equals("ALIMENTO")) {
            lbGarantia.setVisible(false);
            txtGarantia.setVisible(false);
            lbVencimiento.setVisible(true);
            txtVencimiento.setVisible(true);
            txtVencimiento.setText(""); //limpiamos el campo que no vemos 
        } else if (categoria.equals("OTROS")) {
            lbGarantia.setVisible(false);
            txtGarantia.setVisible(false);
            lbVencimiento.setVisible(false);
            txtVencimiento.setVisible(false);
            txtGarantia.setText("N/A"); //limpiamos y marcamos como N/A, Ningun atributo
            txtVencimiento.setText("N/A"); //lo mismo que dije arriba
        }
        //actualizamos la ventana para que se actualice
        revalidate();
        repaint();
    }

    //no demo de explicar pero en fin, un metodo para crear el pdoructo
    private void CrearProductos() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = (String) cbCategoria.getSelectedItem();
        String precioStr = txtPrecio.getText().trim();
        String stockStr = txtStock.getText().trim();

        String Atributo = "";

        //obtenemos el atributo de cada producto
        if (categoria.equals("TECNOLOGIA")) {
            Atributo = txtGarantia.getText().trim();
        } else if (categoria.equals("ALIMENTO")) {
            Atributo = txtVencimiento.getText().trim();

            if (!productoComida.FechaValida(Atributo)) {
                JOptionPane.showMessageDialog(this, "Formato de fecha no es valido\n" + "Use el formato de fecha AAAA-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (categoria.equals("OTROS")) {
            Atributo = "N/A";
        }
        //por si el baboso del usuario no llena todos los apartados
        if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben de ser llenamos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!categoria.equals("OTROS") && Atributo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe de ingresar el atributo del producto(fecha vencimiento o la garantia)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
//las validaciones numericas y el formato de la fecha de vencimiento y la garantia 
        double precio;
        int stock;
        try {
            precio = Double.parseDouble(precioStr);
            stock = Integer.parseInt(stockStr);
            if (precio <= 0 || stock <= 0) {
                JOptionPane.showMessageDialog(this, "Los valores del stock y del precio deben de ser valores positivos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio y el stock deben ser valores validos, intentalo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
//validamos el atributo especifico de cada categoria
        if (categoria.equals("TECNOLOGIA")) {
            try {
                int garantia = Integer.parseInt(Atributo);
                if (garantia <= 0) {
                    JOptionPane.showMessageDialog(this, "El valor de la garantia debe de ser un numero positivo", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El valor de la garantia debe de ser un valor valido ", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
//el da la fecha xd

//llamamos al administrado de productos
        if (AdminDProductos.CreacionProducto(codigo, nombre, precio, stock, categoria, Atributo)) {
            JOptionPane.showMessageDialog(this, "El producto fue agreado al inventario", "Producto creado", JOptionPane.ERROR_MESSAGE);
            if (ventana2 != null) {
                ventana2.ActualizarTabla();
            }
//limpiamos los campos
            txtCodigo.setText("");
            txtNombre.setText("");
            txtPrecio.setText("");
            txtStock.setText("");
            txtGarantia.setText("");
            txtVencimiento.setText("");
            cbCategoria.setSelectedItem(0);
        }//si en el administrador devuelve un false , se mostrara los mensajesde validacion, de codigo repetido y asi...
    }
}
