//gaste el nombre de productos para una clase xd, toca aue improvisar 
package proshecto2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kiquemarroquin
 */
public class CatalogoProductos extends JFrame {

    private JTable Tablaproductos;
    private DefaultTableModel tmodelo; //t modelo par tabla modelo, hay que explicar las coas alm
    private Cliente clienteAct;

    public CatalogoProductos(Cliente cliente) {
        this.clienteAct = cliente;
        //la parte de la ventana y asi....
        setTitle("Catalogo de productos");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //la parte de los botones
        JPanel panelSup = new JPanel(new FlowLayout(FlowLayout.RIGHT)); //a la derecha
        JButton btAgreagarCarrito = new JButton("Agregar al Carrito de compras");

        panelSup.add(new JLabel("Selecciona un producto y haz click para agregarlo"));
        panelSup.add(btAgreagarCarrito);

        //la tabla de productos 
        String[] columnas = {"Codigo", "Nombre", "Categoria", "Cantidad Disponible"};
        tmodelo = new DefaultTableModel(columnas, 0) {
            //para que no sea editable la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Tablaproductos = new JTable(tmodelo);
        actualizarTabla();//el metodo de actualizar tabla

        //accion del boton de agregar al carro
        btAgreagarCarrito.addActionListener(e -> AgregarCarrito());

        add(panelSup, BorderLayout.NORTH);
        add(new JScrollPane(Tablaproductos), BorderLayout.CENTER);
    }

    private void AgregarCarrito() {
        int filaSeleccionar = Tablaproductos.getSelectedRow();
        if (filaSeleccionar == -1) {
            JOptionPane.showMessageDialog(this, "Porfavor selecciona un producto de la lista", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String codigoProd = (String) Tablaproductos.getValueAt(filaSeleccionar, 0);
        Productos productoSeleccionar = AdminDProductos.BuscarProd(codigoProd);
        int StockDisponible = productoSeleccionar.getStock();

        //aca pedimos la cantidad del producto que quiera el usuairio
        String cantidadStr = JOptionPane.showInputDialog(this,
                "Producto: " + productoSeleccionar.getNombre() + " \nStock disponible:  " + StockDisponible + " \n\n¿cuantas unidades del producto desea agregar?", "Agregar al carrito", JOptionPane.QUESTION_MESSAGE);

        if (cantidadStr != null && !cantidadStr.isEmpty()) {
            try {
                int cantidadDeseada = Integer.parseInt(cantidadStr);

                //las validaciones
                if (cantidadDeseada <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe de ser un numero positivo", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (cantidadDeseada > StockDisponible) {
                    JOptionPane.showMessageDialog(this, "No hay suficente Stock para realizar el pedido solo quedan :" + StockDisponible + " unidaddes", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //si pasa todas las validaciones llamamos agregar al carrito
                if (Carrito.AgregarProducto(productoSeleccionar, cantidadDeseada)) {
                    Bitacora.RegistrarEvento(Bitacora.Tipo_Cliente, clienteAct.getId(), Bitacora.OP_Agregar_Producto_Carrito, Bitacora.ESTADO_EXITOSA, "se agreagron productos al carrito de compra");
                }
            } catch (NumberFormatException ex) {
                Bitacora.RegistrarEvento(Bitacora.Tipo_Cliente, clienteAct.getId(), Bitacora.OP_Agregar_Producto_Carrito, Bitacora.ESTADO_FALLIDA, "No se pudo añadira nada, formato invalido");

                JOptionPane.showMessageDialog(this, "Ingrese un valor valido porfavor", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTabla() {
        tmodelo.setRowCount(0);
        Object[][] datos = AdminDProductos.DatosTablaCliente(); //un nuevo metodo para la parte de la tabla
        for (Object[] fila : datos) {
            tmodelo.addRow(fila);
        }
    }
}
