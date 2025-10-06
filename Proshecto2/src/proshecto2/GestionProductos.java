package proshecto2;
//la gestion de productos es exaaaactamente igual al de vendedores

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
public class GestionProductos extends JFrame {

    private DefaultTableModel tmodelo;

    public GestionProductos() {
        setTitle("Gestion de productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel tituloAcc = new JLabel("Botones de accion");
        tituloAcc.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(tituloAcc);//fila 1 titulo del panel

        JButton btCrear = new JButton("Crear Nuevo producto");
        JButton btModificar = new JButton("Modificar Producto");
        JButton btEliminar = new JButton("Eliminar Producto");
        JButton btCargar = new JButton("Carga masiva de productos");

        panel.add(btCrear);
        panel.add(btModificar);
        panel.add(btEliminar);
        panel.add(btCargar);
        add(panel, BorderLayout.WEST); //ahora a la derecha

//la misma cosa que en la de vendedores solo que aca xd
        JLabel titulo2 = new JLabel("Gestion y Listado de Productos", SwingConstants.CENTER);
        titulo2.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo2, BorderLayout.NORTH);

        JPanel tabla = CargarTabla();//metodo para eso.. cargar la tabla
        add(tabla, BorderLayout.CENTER);

        //las acciones de los botones
        btCrear.addActionListener(e -> new CrearProductos(this).setVisible(true));
        btModificar.addActionListener(e -> new ModProducto(this).setVisible(true));
        btEliminar.addActionListener(e -> new EliminarProducto(this).setVisible(true));
        btCargar.addActionListener(e -> new CargarProducto(this).setVisible(true));
        setVisible(true);
    }

    public void ActualizarTabla() {
        //los encabezados
        String[] nomColumna = {"Codigo", "Nombre", "Categoria"};

        //obtenemos los datos 
        Object[][] Ndatos = AdminDProductos.DatosTablaProd();

        if (tmodelo != null) {
            tmodelo.setDataVector(Ndatos, nomColumna);
            tmodelo.fireTableDataChanged();
        }
        this.revalidate();
        this.repaint();
    }

    private JPanel CargarTabla() {
//ponemos los datos sacados pa la tblita
        String[] nomColumna = {"Codigo", "Nombre", "Categoria"};
//obtenemos los datos del admin de productos
        Object[][] datosProd = AdminDProductos.DatosTablaProd();

//ahora no la voy a kgar
        this.tmodelo = new DefaultTableModel(datosProd, nomColumna) {

            @Override
            public boolean isCellEditable(int fila, int columnas) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnas) {
                if (columnas == 2) {
                    return Integer.class;
                }
                return super.getColumnClass(columnas);
            }
        };
        //la tabla en cuestion
        JTable tablaProd = new JTable(this.tmodelo);

        tablaProd.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablaProd.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaProd.setRowHeight(25);

        //contenedor de la tabla
        JPanel panelCont = new JPanel(new BorderLayout());
        JLabel subtitulo = new JLabel("Listado de productos en el inventario", SwingConstants.LEFT);
        subtitulo.setFont(new Font("Arial", Font.BOLD, 16));

        panelCont.add(subtitulo, BorderLayout.NORTH);

        panelCont.add(new JScrollPane(tablaProd), BorderLayout.CENTER);
        return panelCont;
    }
}
