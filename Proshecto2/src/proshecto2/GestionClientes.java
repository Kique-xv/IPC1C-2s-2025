//el sub menu de crear clientes, igual que el de vendedores y productos 
package proshecto2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kiquemarroquin
 */
public class GestionClientes  extends JFrame{
    private DefaultTableModel tmodelo;

    public GestionClientes(){
         setTitle("Gestion de Clientes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel tituloAcc = new JLabel("Botones de accion");
        tituloAcc.setFont(new Font("Arial", Font.BOLD, 16));
        tituloAcc.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(tituloAcc);//fila 1 titulo del panel

        JButton btCrear = new JButton("Crear nuevo Cliente");
        JButton btModificar = new JButton("Modificar cliente");
        JButton btEliminar = new JButton("Eliminar cliente");
        JButton btCargar = new JButton("Carga masiva de Clientes");

        panel.add(btCrear);
        panel.add(btModificar);
        panel.add(btEliminar);
        panel.add(btCargar);
        //los botones ALA IZQUIERDA
        add(panel, BorderLayout.WEST);

        JLabel titulo2 = new JLabel("Gestion y listado de clientes", SwingConstants.CENTER);
        titulo2.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo2, BorderLayout.NORTH);

        JPanel tabla = Cargartabla();
        add(tabla, BorderLayout.CENTER);
        
          //las acciones de los botones
        btCrear.addActionListener(e -> new CrearClientes(this).setVisible(true));
      btModificar.addActionListener(e -> new ModCliente(this).setVisible(true));
        //btEliminar.addActionListener(e -> new EliminarVendedor(this).setVisible(true));
      //  btCargar.addActionListener(e -> new CargarVendedor(this).setVisible(true));

        setVisible(true);
    }
    public void actualizarTabla(){
        //los encabezados
        String[] nomColumna = {"Codigo", "Nombre", "Genero", "Cumpleaños"};
   //obtener los datos
   Object[][] Ndatos = AdminDClientes.DatosTablaCliente();
   if(tmodelo !=null){
       tmodelo.setDataVector(Ndatos, nomColumna);
       tmodelo.fireTableDataChanged();
   }
   this.revalidate();
   this.repaint();
    }
      //gracias stack overflow
    private JPanel Cargartabla() {
        //zampamos los encabezados de la tabla
        String[] nomColumna = {"Codigo", "Nombre", "Genero", " Cumpleaños"};
        //obtenemos los datos del admin de Clientes
        Object[][] datosClient = AdminDClientes.DatosTablaCliente();

        //creamos la tabla LITERALMENTE TODO LO QUE NECESITABA ERA UN MALDITO THIS!!!!!!! ES ENSERKIO
        this.tmodelo = new DefaultTableModel(datosClient, nomColumna) {

            @Override
            // PAR QUE LA TABLA NO SEA EDITABLE
            public boolean isCellEditable(int fila, int columna
            ) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnas
            ) {
                if (columnas == 3) {
                    return Integer.class;
                }
                return super.getColumnClass(columnas);
            }
        };
        JTable tablaClient = new JTable(this.tmodelo);
//
        tablaClient.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaClient.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaClient.setRowHeight(25);
//contenedor principal de la tabla
        JPanel panelCont = new JPanel(new BorderLayout());
        JLabel subtit = new JLabel("Listado de Clientes", SwingConstants.LEFT);
        subtit.setFont(new Font("Arial", Font.BOLD, 16));

        panelCont.add(subtit, BorderLayout.NORTH);

        panelCont.add(new JScrollPane(tablaClient), BorderLayout.CENTER);

        return panelCont;
    }
}

