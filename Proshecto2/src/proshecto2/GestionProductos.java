package proshecto2;
//la gestion de productos es exaaaactamente igual al de vendedores

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

    private JTable tablaProd;
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
        JButton btBuscar = new JButton("ver detalles del producto");

        panel.add(btCrear);
        panel.add(btModificar);
        panel.add(btEliminar);
        panel.add(btCargar);
        panel.add(btBuscar);
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
        btBuscar.addActionListener(e -> verDatProd());

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
        this.tablaProd = new JTable(this.tmodelo);
        this.tablaProd.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        this.tablaProd.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        this.tablaProd.setRowHeight(25);

        //contenedor de la tabla
        JPanel panelCont = new JPanel(new BorderLayout());
        JLabel subtitulo = new JLabel("Listado de productos en el inventario", SwingConstants.LEFT);
        subtitulo.setFont(new Font("Arial", Font.BOLD, 16));

        panelCont.add(subtitulo, BorderLayout.NORTH);

        panelCont.add(new JScrollPane(this.tablaProd), BorderLayout.CENTER);
        return panelCont;
    }
    //esto es para ver los detalles del producto
    private void verDatProd() {
        int Fila = this.tablaProd.getSelectedRow();

        // Validación de que seleccione una fila
        if (Fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un producto de la tabla para ver sus detalles.",
                    "Selecciona un producto",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // obtenermos el codigo para Leer el valor de la columna 0 
        String codigoBuscar = tablaProd.getValueAt(Fila, 0).toString();

        // Buscar el objeto
        Productos p = AdminDProductos.BuscarProd(codigoBuscar);

        if (p != null) {
            // Lanzar la ventana de los detalles gracias a VerDetallesProd
            VerDetallesProd detallesVentana = new VerDetallesProd(p);
            detallesVentana.setVisible(true); // Mostrará el JDialog modal
        } else {
            // Este es un caso de error raro producto en tabla, pero no en memoria, gracias utub
            JOptionPane.showMessageDialog(
                    this,
                    "El producto seleccionado no pudo ser recuperado del administrador.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}