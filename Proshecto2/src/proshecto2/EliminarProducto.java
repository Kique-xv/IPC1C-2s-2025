//la clase para eliminar productos ya se lasaben 
package proshecto2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author kiquemarroquin
 */
public class EliminarProducto  extends JFrame{
    private GestionProductos ventana2;
    private JTextField txtCodEli;
    private JButton btElim;
    
    public EliminarProducto(GestionProductos ventana2){
        this.ventana2 = ventana2;
        setTitle("Eliminar Producto");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        panel.add(new JLabel("Escribe el nombre del producto a eliminar :"));
        txtCodEli = new JTextField(15);
        panel.add(txtCodEli);
        
        panel.add(new JLabel(""));// espacio en blanco pa que se va mas bonito
        
        btElim = new JButton("Eliminar Producto: ");
        panel.add(btElim);
        //accion del boton
        btElim.addActionListener(e -> eliminarProductos());
        add(panel, BorderLayout.CENTER);
    }
    private void eliminarProductos(){
        String codigo = txtCodEli.getText().trim();
        
        if(codigo.isEmpty()){
            JOptionPane.showMessageDialog(this, "Debes de ingresar el nombre del producto para eliminarlo", "Error", JOptionPane.ERROR_MESSAGE);
       return;
        }
        Productos producto = AdminDProductos.BuscarProd(codigo);
        if(producto !=null){
            int Confirm = JOptionPane.showConfirmDialog(this, 
                    "¿Esta usted seguro de querer eliminar: " +producto.getNombre() + "?",
                    "Confirmar elimnacion" , JOptionPane.YES_NO_OPTION);
            if(Confirm == JOptionPane.YES_OPTION){
                if(AdminDProductos.EliminarProducto(codigo)){
                    JOptionPane.showMessageDialog(this, "El producto fue eliminado" , "Eliminacion Exitosa", JOptionPane.INFORMATION_MESSAGE);
                      if(ventana2 != null){
                   ventana2.ActualizarTabla();
               }
                    this.dispose(); //cerramos la ventana               
                } else {
                    JOptionPane.showMessageDialog(this, "Error al intentar eliminar el producto" , "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else{
            JOptionPane.showMessageDialog(this, "no se ha encontrado el produxto" , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
