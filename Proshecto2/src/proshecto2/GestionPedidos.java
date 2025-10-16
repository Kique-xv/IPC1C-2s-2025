//grcias al inge el extends es generalizacion, el yutub lo dicen diferente
//es gestion de pedidos , que shush mas puedo decir
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
public class GestionPedidos extends JFrame {

    private JTable tablaPedidos;
    private DefaultTableModel tmodelo;
    private Vendedor vendedorAct;

    public GestionPedidos(Vendedor vendedor) {
        this.vendedorAct = vendedor;
        setTitle("Modulo de vendedor-----------------------Gestion de pedidos pendientes"); //jaja dijo dientes xd
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //para los botones qleros 
        JPanel panelSup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btConfirm = new JButton("Confirmar Pedido");
        panelSup.add(new JLabel("Selecciona un pedido de la lista y dale click al boton de confirmar pedido"));
        panelSup.add(btConfirm);

        //la tabla EN EL CENTRO 
        String[] columnas = {"Codigo de pedido", "Fecha de generacion", "codigo del cliente", "Nombre del cliente", "Total a pagar"};
        tmodelo = new DefaultTableModel(columnas, 0) {
            //LA PTA TABLA NO ES EDITABLE          
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaPedidos = new JTable(tmodelo);
        actualizarTabla(); //carga los datos

        //boton de accion
        btConfirm.addActionListener(e -> ConfirmarPedido());

        add(panelSup, BorderLayout.NORTH);
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);
    }

    private void ConfirmarPedido() {
        int filaSeleccionar = tablaPedidos.getSelectedRow();

        if (filaSeleccionar == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un pedido", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        //vamos a obtener el id del pedido, de la primera fila seleccionada
        String IDpedido = (String) tablaPedidos.getValueAt(filaSeleccionar, 0);

        //hacemos el llamado a la parte de Admindepedidos
        if (AdminDPedidos.ConfirmPedido(IDpedido, vendedorAct)) {
            //si la confimacion salio se refresca la talba
            actualizarTabla();

        } else {
            JOptionPane.showMessageDialog(this, "Algo fallo al confimar algun pedido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarTabla() {
        tmodelo.setRowCount(0);//limpiamos la tabla

        Object[][] datos = AdminDPedidos.DatosTabla();

        for (Object[] fila : datos) {
            tmodelo.addRow(fila);

        }
    }
}
