///el historial de compras
package proshecto2;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kiquemarroquin
 */
public class HistorialCompras extends JFrame{
    
    private JTable TablaHistorial;
    private DefaultTableModel tmodelo;
    private Cliente clienteAct;
    
    public HistorialCompras(Cliente cliente){
        this.clienteAct = cliente;
        setTitle("Mi historial de compras" +cliente.getNombre());
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        add(new JLabel("Tus compras confirmadas" , SwingConstants.CENTER), BorderLayout.NORTH);
  
    String[] columnas = {"Codigo pedido", "Fecha de confirmacion", "Total Compras"};
    tmodelo = new DefaultTableModel(columnas, 0){
        @Override
        public boolean isCellEditable(int fila, int columna){
            return false;
        }
    };
   TablaHistorial = new JTable(tmodelo);
   actualizarTabla();
   
   add(new JScrollPane(TablaHistorial), BorderLayout.CENTER);
    }
    private void actualizarTabla(){
        tmodelo.setRowCount(0); //para limpiar
        
        Object[][] datos = AdminDCompras.DatosTablaHistorial(clienteAct.getId());
        for(Object[] fila : datos){
            tmodelo.addRow(fila);
        }
    }
}
