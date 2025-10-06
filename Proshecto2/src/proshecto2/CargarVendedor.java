package proshecto2;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author kiquemarroquin
 */
public class CargarVendedor extends JFrame {

    private JTextArea resulArea;
    private JButton btCargar;
private GestionVendedor ventana1;
    public CargarVendedor(GestionVendedor ventana1) {
        this.ventana1 = ventana1;
        setTitle("Cargar vendedores");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //el panel con el borderlayout
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //el boton de cargar
        btCargar = new JButton("Seleccionar y Cargar Achivo");
        panel.add(btCargar, BorderLayout.NORTH);

        //area de resultado
        resulArea = new JTextArea("Resultados de la carga saldran aca: ");
        resulArea.setEditable(false);

        JScrollPane sP = new JScrollPane(resulArea);
        panel.add(sP, BorderLayout.CENTER);

        //la accion del boton xd
        btCargar.addActionListener(e -> CargarDatos());
        add(panel);

    }

    private void CargarDatos( ) {
        JFileChooser fC = new JFileChooser();
        fC.setDialogTitle("Selecciona el archivo csv de vendedores");

        int seleccion = fC.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archiseleccion = fC.getSelectedFile();
            CargarDatosCsv(archiseleccion);
        }
    }

    private void CargarDatosCsv(File archivo) {
        resulArea.setText("Inciando carga desde" + archivo.getName() + "\n");
        int CargadosB = 0;
        int FallasForm = 0;
        int ErrorLogic = 0;

        try (BufferedReader Lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numLinea = 0;
            while ((linea = Lector.readLine()) != null) {
                numLinea++;
                String[] datos = linea.split(",");
                //validamos el formato usado la clase de dalidar datos
                if (!ValidarDatos.ValidarFormVendedor(datos)) {
                    resulArea.append("Linea " + numLinea + " fallido por formato o campos faltantes \n");
                    FallasForm++;
                    continue;
                }
                //extraemos y limpiadlos datos 
                String codigo = datos[0].trim();
                String nombre = datos[1].trim();
                String genero = datos[2].trim().toUpperCase();
                String contraseña = datos[3].trim();
                //las ventas ya estan confirmadas ya las tenemos validadas
                //validamos la logica el codigo unico y creacion
                if (AdminDVendedores.CodRepetido(codigo)) {
                    resulArea.append("Linea" + numLinea + " porque el codigo: " + codigo + " ya existe \n");
                    ErrorLogic++;
                } else {
                    if (AdminDVendedores.CreacionVendedor(codigo, nombre, genero, contraseña)) {
                        CargadosB++;
                        resulArea.append(" Linea " + numLinea + " Vendedor " + nombre + "agregado \n");
                    } else {
                        resulArea.append("Linea  " + numLinea + "Error desconcido al intentar crear \n");
                        ErrorLogic++;
                    }
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo" + ex.getMessage(), "Error 14", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this,
                "Carga de vendedores Terminada:\n"
                + "total lineas exitosas: " + CargadosB + "\n"
                + "total errores de formato: " + FallasForm + "\n"
                + "total de errores lógicos duplicados u utros " + ErrorLogic,
                "Resumen de Carga",
                JOptionPane.INFORMATION_MESSAGE  ); 
       
         if (ventana1 != null) {
           ventana1.actualizarTabla();
    }
         this.dispose();
}
       }