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
public class GestionVendedor extends JFrame {
private DefaultTableModel tmodelo;
    public GestionVendedor() {
        setTitle("Gestion de Vendedores");
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

        JButton btCrear = new JButton("Crear Nuevo Vendedor");
        JButton btModificar = new JButton("Modificar Vendedor");
        JButton btEliminar = new JButton("Eliminar Vendedor");
        JButton btCargar = new JButton("Carga masiva de vendedores");

        panel.add(btCrear);
        panel.add(btModificar);
        panel.add(btEliminar);
        panel.add(btCargar);
        //los botones ALA IZQUIERDA
        add(panel, BorderLayout.WEST);

        JLabel titulo2 = new JLabel("Gestion y listado de vendedores", SwingConstants.CENTER);
        titulo2.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo2, BorderLayout.NORTH);

        JPanel tabla = Cargartabla();
        add(tabla, BorderLayout.CENTER);

        //las acciones de los botones
        btCrear.addActionListener(e -> new CrearVendedor(this).setVisible(true));
        btModificar.addActionListener(e -> new ModVendedor(this).setVisible(true));
        btEliminar.addActionListener(e -> new EliminarVendedor(this).setVisible(true));
        btCargar.addActionListener(e -> new CargarVendedor(this).setVisible(true));

        setVisible(true);
    }
    
    public void actualizarTabla(){
        //los encabezados
        String[] nomColumna = {"Codigo", "Nombre", "Genero", "Ventas realizadas"};
   //obtener los datos
   Object[][] Ndatos = AdminDVendedores.DatosTablaVendedor();
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
        String[] nomColumna = {"Codigo", "Nombre", "Genero", "Ventas realizadas"};
        //obtenemos los datos del admin de vendedores
        Object[][] datosVen = AdminDVendedores.DatosTablaVendedor();

        //creamos la tabla LITERALMENTE TODO LO QUE NECESITABA ERA UN MALDITO THIS!!!!!!! ES ENSERKIO
        this.tmodelo = new DefaultTableModel(datosVen, nomColumna) {

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
        JTable tablaVen = new JTable(this.tmodelo);
//
        tablaVen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaVen.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaVen.setRowHeight(25);
//contenedor principal de la tabla
        JPanel panelCont = new JPanel(new BorderLayout());
        JLabel subtit = new JLabel("Listado de vendedores", SwingConstants.LEFT);
        subtit.setFont(new Font("Arial", Font.BOLD, 16));

        panelCont.add(subtit, BorderLayout.NORTH);

        panelCont.add(new JScrollPane(tablaVen), BorderLayout.CENTER);

        return panelCont;
    }
}
//TE ODIO JAVA 😭
//Java: Yo tambien te odio kique