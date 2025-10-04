//una clase para eliminar vendedores
package proshecto2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author kiquemarroquin
 */
public class EliminarVendedor extends JFrame{
    private JTextField txtCodEliminar;
    private JButton btEliminar;
    
    public EliminarVendedor(){
        setTitle("Eliminiar Vendedor:");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        //componentes
        panel.add(new JLabel("Codigo del vendedor para ser Eliminado: " , SwingConstants.RIGHT));
        txtCodEliminar = new JTextField(15);
        panel.add(txtCodEliminar);
        
        panel.add(new JLabel("")); //espacio en blanco para mas orden
        btEliminar = new JButton("Eliminar Vendedor");
        panel.add(btEliminar);
        
        btEliminar.addActionListener(e -> eliminarVendedor());//el metodo de alli abajo
        add(panel, BorderLayout.CENTER);
    }
    private void eliminarVendedor(){
        String codigo = txtCodEliminar.getText().trim();
        
        if(codigo.isEmpty()){
            JOptionPane.showMessageDialog(this, "Debes de ingresar el codigo del vendedor" , "Error 11" , JOptionPane.ERROR_MESSAGE);
       return;
        }
        Vendedor vendedor = AdminDVendedores.BuscarVendedor(codigo);
        
        if(vendedor != null){
           int Confirmar = JOptionPane.showConfirmDialog(this, 
                   "¿Esta seguro de querer eliminar el vendedor:  " +vendedor.getNombre()+ "",
                   "¿Confirmar Eliminacion?" , JOptionPane.YES_NO_OPTION);
    
        if(Confirmar == JOptionPane.YES_OPTION){
            if(AdminDVendedores.EliminarVendedor(codigo)){
                JOptionPane.showMessageDialog(this, "El vendedor fue eliminado", "Eliminacion exitosa", JOptionPane.INFORMATION_MESSAGE);
           this.dispose(); //cerrar la ventana
           
            } else{
                JOptionPane.showMessageDialog(this, "Error al elimniar el vendedor", "Error 12", JOptionPane.ERROR_MESSAGE);
            }
        }   
        } else{
            JOptionPane.showMessageDialog(this, "No se ha encotrado al vendedor" ,"Error 13" , JOptionPane.ERROR_MESSAGE);
        }
    }
}
