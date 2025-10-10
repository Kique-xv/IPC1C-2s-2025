//para la parte de stock, la tabla y lo de... el registro de lo que sea
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
public class ProductosAlmacen extends JFrame {
    private JTable tablaProd;
    private DefaultTableModel tmodelo;
    private JButton btActualizar;
    
    
    public ProductosAlmacen(){
        setTitle("Gestion de productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        JPanel panelb = new JPanel();
        panelb.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel tituloAcc = new JLabel("Botones de accion");
        tituloAcc.setFont(new Font("Arial", Font.BOLD, 16));
        panelb.add(tituloAcc);//fila 1 titulo del panel

        JButton btAgg = new JButton("Agregar Stock ");
        JButton btCargar = new JButton("Carga del stock en almacen");
        JButton btHistorial = new JButton(" Historial de ingresos");
      //  JButton btCargarProd = new JButton("Carga Masiva de productos");
        btActualizar = new JButton("Actualizar tabla");
        

        panelb.add(btAgg);
        panelb.add(btCargar);
        panelb.add(btHistorial);
       // panelb.add(btCargarProd);
        panelb.add(btActualizar);
        add(panelb, BorderLayout.WEST); //ahora a la derecha
        
      //  la tabla de los productos
            JPanel tabla = CargarTabla();//metodo para eso.. cargar la tabla
        add(tabla, BorderLayout.CENTER);

//la misma cosa que en la de vendedores solo que aca xd
        JLabel titulo2 = new JLabel("Gestion y Listado de Productos", SwingConstants.CENTER);
        titulo2.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo2, BorderLayout.NORTH);

  

        //las acciones de los botones
       btAgg.addActionListener(e -> new AgregStock(this).setVisible(true));        
       btCargar.addActionListener(e -> new CargarStock(this).setVisible(true));        
       btActualizar.addActionListener(e -> ActualizarTabla());
     //   btCargarProd.addActionListener(e ->  CargarProducto().setVisible(true));

         setVisible(true);

    }
  private JPanel  CargarTabla(){
      //definirmos los nombre de las columnas, con el esotkc y el precio
      String[] columnas = {"Codigo" , "Nombre" , "Categoria" , "Precio" , "Stock"};
      
      tmodelo = new DefaultTableModel(AdminDProductos.DatosTablaProd(), columnas){
          
          @Override
          public boolean isCellEditable(int row, int column){
             return false; //ESTA MIERDA NO SE VA EDITAR 
          }
      };
      
      tablaProd = new JTable(tmodelo);
      JScrollPane sp = new JScrollPane(tablaProd);
      
      JPanel panel = new JPanel(new BorderLayout());
      panel.add(sp, BorderLayout.CENTER);
      return panel;
  }
  //recargar los datos del modelo de la tabla
  public void ActualizarTabla(){
      //obtenemos los nuevos datos 
      Object[][] Ndatos =AdminDProductos.DatosTablaProd();
      
      //Limpiar el modelo que ya esta 
      tmodelo.setRowCount(0);
      
      //Agregar las finlas nuevas
      for(Object[] fila : Ndatos ){
          tmodelo.addRow(fila);
      }
  }
}
