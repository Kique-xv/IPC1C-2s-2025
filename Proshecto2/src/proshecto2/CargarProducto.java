//una clase para cargar los productos al archivo csv
package proshecto2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author kiquemarroquin
 */
public class CargarProducto extends JFrame {

    //los apartados para buscar el archivaldo
    private JTextField txtRutaArchi;
    private JButton btSeleccionar;
    private JButton btCargar;
    private JTextArea Result;
    private GestionProductos ventana2;

    public CargarProducto(GestionProductos ventana2) {
        this.ventana2 = ventana2;
        setTitle("Carga de productos al archivo csv");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        txtRutaArchi = new JTextField();
        txtRutaArchi.setEditable(false); //por si las moscas
        btSeleccionar = new JButton("Elige el archivo para la carga de productos");
        btCargar = new JButton("Cargar Archivo");

        panel.add(new JLabel("Ruta del archivo csv: ", SwingConstants.LEFT), BorderLayout.NORTH);
        panel.add(txtRutaArchi, BorderLayout.CENTER);
        //para los botones :D
        JPanel panelBot = new JPanel(new GridLayout(1, 2, 5, 5));
        panelBot.add(btSeleccionar);
        panelBot.add(btCargar);
        panel.add(panelBot, BorderLayout.SOUTH);

        //area de resultado
        Result = new JTextArea("esperado arhivo, con el formato de cada linea \n"
                + "Codigo, Nombre, precio, Stock, Categoria, Atributo_Especifico\n"
        );
        Result.setEditable(false);
        JScrollPane Js = new JScrollPane(Result);

        add(panel, BorderLayout.NORTH);
        add(Js, BorderLayout.CENTER);
        //acciones de los botones, haciendo llamado a sus metodos
        btSeleccionar.addActionListener(e -> SeleccionArchi());
        btCargar.addActionListener(e -> IniciarCarga());
    }

    //para buscar el archivo por uno mismo
    private void SeleccionArchi() {
        JFileChooser Fc = new JFileChooser();
        int result = Fc.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = Fc.getSelectedFile();
            txtRutaArchi.setText(archivo.getAbsolutePath());
            Result.setText("Archivo Seleccionado, dale click al boton de iniciar carga, pd: recuerda que es OBLIGATORIO que el formato es de 6 campos ");
        }
    }

    public void IniciarCarga() {
        String ruta = txtRutaArchi.getText().trim();
        if (ruta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes de seleccionar, un archivo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        btCargar.setEnabled(false);
        Result.setText("Inciando carga desde el archivo \n");

        //llamado al gestor de productos
        String Cargar = AdminDProductos.CargadProductos(ruta); //enserio le puse cargad....
        if (ventana2 != null) {
            ventana2.ActualizarTabla();
        }
        Result.append(Cargar);
        btCargar.setEnabled(true);

        //mostrar el resumen el en sabroso JOPtionPane
        if (Cargar.startsWith("Carga de productos exitosa :D")) {
            JOptionPane.showMessageDialog(this, Cargar, "Exito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else if (Cargar.startsWith("Resumen")) {
            JOptionPane.showMessageDialog(this, "Cargar con errores, revisa los detalles en la ventana de resultados", "Advertencia", JOptionPane.WARNING_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, Cargar, "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }
}
