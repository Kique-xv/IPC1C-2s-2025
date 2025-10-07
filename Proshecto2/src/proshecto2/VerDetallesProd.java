//la calse para el boton de ver detalles del producto
package proshecto2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author kiquemarroquin
 */
public class VerDetallesProd extends JDialog {

    public VerDetallesProd(Productos p) {
        //configurams el JDialog
        setTitle(" Detalles del producto " + p.getNombre());
        setSize(500, 400);
        setModal(true); //para que solo esta ventana se use mientras este abierta
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelc = new JPanel(new BorderLayout());
        panelc.setBorder(new EmptyBorder(15, 15, 15, 15));

        //el titulo
        JLabel lbTitulo = new JLabel("Datos completos del proucto", SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelc.add(lbTitulo, BorderLayout.NORTH);

        //un panel con los datos con el gridlayout de 6 filas y 2 columnas
        JPanel panelDat = new JPanel(new GridLayout(6, 2, 5, 10));
        panelDat.setBorder(new EmptyBorder(20, 0, 0, 0));//espacio superior

        //extraemor los datos     
        String atriDet = ObtenerDatos(p);

        //codigg
        panelDat.add(new JLabel("El codigo del producto es: ", SwingConstants.RIGHT));
        panelDat.add(new JLabel(p.getCodigo()));

        //el nombre
        panelDat.add(new JLabel("el nombre del producto es:  ", SwingConstants.RIGHT));
        panelDat.add(new JLabel(p.getNombre()));

        //precio
        panelDat.add(new JLabel("El precio del producto es: ", SwingConstants.RIGHT));
        panelDat.add(new JLabel("Q. " + String.format("%.2f", p.getPrecio())));

        //el stock
        panelDat.add(new JLabel("La cantidad disponible del producto es: ", SwingConstants.RIGHT));
        panelDat.add(new JLabel(String.valueOf(p.getStock())));

        //La categoria
        panelDat.add(new JLabel("La categoria del producto es: ", SwingConstants.RIGHT));
        panelDat.add(new JLabel(p.getCategoria()));

        //el atributo especifico la garantia, el vencimiento(saber como decirlo), y si no tiene 
        JLabel LbAtributo = new JLabel("El atributo especifico del producto es: ", SwingConstants.RIGHT);
        LbAtributo.setFont(new Font("Arial", Font.BOLD, 13));
        panelDat.add(LbAtributo);

        JLabel LbAtriVal = new JLabel(atriDet);
        LbAtriVal.setFont(new Font("Arial", Font.BOLD, 14));
        panelDat.add(LbAtriVal);

        panelc.add(panelDat, BorderLayout.CENTER);
        add(panelc);
    }
    //un metodo/modulo para obtener el atributo especifico(la garantia o fehca de vencimineto)

    private String ObtenerDatos(Productos p) {
        if (p.getCategoria().equals("TECNOLOGIA")) {
            productoTec pT = (productoTec) p;
            return pT.getMesesGarantia() + " Meses de garantia ";
        } else if (p.getCategoria().equals(" ALIMENTO ")) {
            productoComida pCom = (productoComida) p;
            return pCom.getFechaVencer();
        } else {
            return "N/A";
        }
    }
}
