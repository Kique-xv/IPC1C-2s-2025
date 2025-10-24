// ya se la saben la clase pa cargar los clientes
package proshecto2;

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author kiquemarroquin
 */
public class CargaClientes extends JFrame {

    private JTextArea Resultado;
    private JButton btCargar;
    private GestionClientes ventana;
    private Vendedor vendedorAct;

    public CargaClientes(GestionClientes ventana, Vendedor vendedor) {
        this.vendedorAct = vendedor;
        this.ventana = ventana;

        setTitle("Carga Masiva de Clientes");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelSup = new JPanel();
        btCargar = new JButton("Seleccionar Archivo");
        panelSup.add(btCargar);

        Resultado = new JTextArea("Formato esperado: Codigo,Nombre,Genero,Cumpleaños,Contraseña");
        Resultado.setEditable(false); //no editarlo :3

        add(panelSup, BorderLayout.NORTH);
        add(new JScrollPane(Resultado), BorderLayout.CENTER);

        btCargar.addActionListener(e -> InciarCarga());

    }

    private void InciarCarga() {
        JFileChooser seleccionar = new JFileChooser();
        seleccionar.setDialogTitle("Selecciona el archivo de los clientes");
        int resultado = seleccionar.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccion = seleccionar.getSelectedFile();
            Resultado.setText("Cargando los Clientes desde: " + archivoSeleccion.getName() + "\n\n");//eso son dos saltos de linea xd gracias yutu

            //llamamos a cargar clientes del admin de clientes
            String reporte = AdminDClientes.CargarClientes(archivoSeleccion, vendedorAct.getId());

            Resultado.append(reporte);

            if (ventana != null) {
                ventana.actualizarTabla();
            }
            JOptionPane.showMessageDialog(this, "Proceso de carga ya teermino", "Carga realizada", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
