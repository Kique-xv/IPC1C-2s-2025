//SIP SOY YO :D
//ni de chiste pongo musica aca, ya es la 1 am, ni de chiste me pogngo hacer estas boludesdes, sin tiempo
package practica2;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author kiquemarroquin
 */
public class YOOOOO extends JFrame {

    public YOOOOO() {
        //ya no quiero explicar esto
        setTitle("SOY YOOOOOOOOO");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new GridLayout(6, 1, 10, 10));
        //el titulo
        JLabel titulo = new JLabel("MIS DATOS :3", SwingConstants.CENTER);
        titulo.setFont(new Font("Papyrus", Font.BOLD, 25));
        panelPrincipal.add(titulo);

        panelPrincipal.add(new JLabel("Mi nombre es: Guillermo Enrique Marroquin Morán", SwingConstants.CENTER));
        panelPrincipal.add(new JLabel("Mi Carnet es 202103527", SwingConstants.CENTER));
        panelPrincipal.add(new JLabel("Este programa tiene kilos y kilos de copy :3, mas la musica de undertale xddddd"));

        //bton para que se cierre esta madre
        JButton btcerrar = new JButton("salir");
        btcerrar.addActionListener(e -> dispose());
        panelPrincipal.add(btcerrar);

        add(panelPrincipal);
        validarAccion.regisAccion("Ver mis datos", true, "Salio bien, como demonios debe de salir mal esto?");
    }
}
