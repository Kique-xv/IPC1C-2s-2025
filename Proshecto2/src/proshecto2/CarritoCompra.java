//se me estan acabano los nombreeeeees y las ideas para los mismos, en palabras de un sabio, YA NOS CARGÓ LA CHINGADA
package proshecto2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.DecimalFormat;
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
public class CarritoCompra extends JFrame {

    private JTable TablaCarro;
    private DefaultTableModel tmodelo;
    private JLabel lbTotal;
    private Cliente clienteAct;

    public CarritoCompra(Cliente cliente) {
        this.clienteAct = cliente;

        setTitle("Mi carrito de compras - " + cliente.getNombre());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //panel de acciones de botones de accion
        JPanel panelInf = new JPanel(new BorderLayout());
        JPanel panelBot = new JPanel();

        //los botnoes en cuestion
        JButton btEliminar = new JButton("Eliminar producto");
        JButton btActualizar = new JButton("Actualizar pedido");
        JButton btHacerPedido = new JButton("Realizar pedidos");

        panelBot.add(btEliminar);
        panelBot.add(btActualizar);

        lbTotal = new JLabel("Total a pagar :  Q.0.00");
        lbTotal.setFont(new Font("Arial", Font.BOLD, 18));

        panelInf.add(panelBot, BorderLayout.WEST);
        panelInf.add(lbTotal, BorderLayout.CENTER);
        panelInf.add(btHacerPedido, BorderLayout.EAST);

        //la tabla del carrito
        String[] columnas = {"Codgo", "Nombre", "Cantidad", "Precio", "Subtotal"};
        tmodelo = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TablaCarro = new JTable(tmodelo);
        ActualizarVista();

        //accion de los botones
        btEliminar.addActionListener(e -> EliminarProdSeleecionado());
        btActualizar.addActionListener(e -> ActualizarProdSeleccionado());
        btHacerPedido.addActionListener(e -> RealizarPedido());

        add(new JScrollPane(TablaCarro), BorderLayout.CENTER);
        add(panelInf, BorderLayout.SOUTH);
    }

    private void ActualizarVista() {
        tmodelo.setRowCount(0);
        ProdCarrito[] Prods = Carrito.getProds();
        DecimalFormat dF = new DecimalFormat("#.00");

        for (int i = 0; i < Carrito.getCantProd(); i++) {
            ProdCarrito Prod = Prods[i];
            double Subtotal = Prod.producto.getPrecio() * Prod.cantidad;
            Object[] Fila = {
                Prod.producto.getCodigo(),
                Prod.producto.getNombre(),
                Prod.cantidad,
                "Q" + dF.format(Prod.producto.getPrecio()),
                "Q" + dF.format(Subtotal)
            };
            tmodelo.addRow(Fila);
        }
        lbTotal.setText("Total: Q " + dF.format(Carrito.CalcularTotal()));
    }

    private void EliminarProdSeleecionado() {
        int fila = TablaCarro.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminarlo", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Carrito.EliminarProd(fila);
        Bitacora.RegistrarEvento(Bitacora.Tipo_Cliente, clienteAct.getId(), Bitacora.OP_Eliminar_Producto_Carrito, Bitacora.ESTADO_EXITOSA, "Se elimino un producto del carrto de compras");
        ActualizarVista();
    }

    private void ActualizarProdSeleccionado() {
        int fila = TablaCarro.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para actualizarlo", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String NcantidadStr = JOptionPane.showInputDialog(this, "Ingresa la nueva cantidad", "Actualizar Cantidad", JOptionPane.QUESTION_MESSAGE);
        //las validaciones
        if (NcantidadStr != null) {
            try {
                int Ncantidad = Integer.parseInt(NcantidadStr);
                if (Ncantidad > 0) {
                    if (Carrito.actualizarCant(fila, Ncantidad)) {
                        Bitacora.RegistrarEvento(Bitacora.Tipo_Cliente, clienteAct.getId(), Bitacora.OP_Actualizar_Pedido, Bitacora.ESTADO_EXITOSA, "Se Modifico los productos del carrito de compras");
                        ActualizarVista();
                    }
                } else {
                    Bitacora.RegistrarEvento(Bitacora.Tipo_Cliente, clienteAct.getId(), Bitacora.OP_Actualizar_Pedido, Bitacora.ESTADO_FALLIDA, "No se Modifico los productos del carrito de compras, stock insuficiente");
                    JOptionPane.showMessageDialog(this, "La cantidad debe de ser mayor a cero, para quitar el producto, usa el boton de eliminar ", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingresa un numero valido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //para realizar los pedidos
    private void RealizarPedido() {
        //solo confimamos la chingadera
        if (AdminDPedidos.CrearPedido(clienteAct)) {
            JOptionPane.showMessageDialog(this, "Tu pedido se ha realizado con exito", "Pedido Efectuado", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
    }
}
