//la clase para cargar stock
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
public class CargarStock extends JFrame {

    private JTextArea result;
    private JButton btCargar;
    private ProductosAlmacen ventana;

    public CargarStock(ProductosAlmacen ventana) {
        this.ventana = ventana;
        setTitle("Cargar el stock en el csv");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btCargar = new JButton("Seleccionar y cargar archivo csv de Stock");
        panel.add(btCargar, BorderLayout.NORTH);

        result = new JTextArea("Formato esperado codigo,cantidad\nResultados de la carga saldrán aquí abajo: ");
        result.setEditable(false);

        JScrollPane Sp = new JScrollPane(result);
        panel.add(Sp, BorderLayout.CENTER);

        btCargar.addActionListener(e -> CargarDatoS());
        add(panel);
    }

    private void CargarDatoS() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Selecciona el achivo para la carga del stock: ");

        int Seleccion = fc.showOpenDialog(this);

        if (Seleccion == JFileChooser.APPROVE_OPTION) {
            File ArchivoS = fc.getSelectedFile();
            ProcesarDatos(ArchivoS);
        }
    }

    private void ProcesarDatos(File Archiv) {
        result.setText("Iniciando Carga  desde el archivo:  " + Archiv.getName() + "\n");
        int Actualizaciones = 0;
        int FallasForm = 0;
        int Noencontrado = 0;

        try (BufferedReader Lector = new BufferedReader(new FileReader(Archiv))) {
            String linea;
            int numlinea = 0;

            //la validacion del formato de caodigo y cantidad 
            while ((linea = Lector.readLine()) != null) {
                numlinea++;
                String[] datos = linea.split(",");

                if (datos.length == 2) {
                    try {
                        String codigo = datos[0].trim();
                        int cant = Integer.parseInt(datos[1].trim());

                        boolean Exito = AdminDProductos.ActualizarStock(codigo, cant);

                        if (Exito) {
                            Actualizaciones++;
                            result.append("Linea " + numlinea + ": stock actualizado para el producto con el codigo: " + codigo + "\n");
                        } else {
                            Noencontrado++;
                            result.append("Linea " + numlinea + "Error  el " + codigo + " no fue encontrado\n");

                        }
                    } catch (NumberFormatException e) {
                        FallasForm++;
                        result.append("Linea " + numlinea + "Error por el formato incorrecto\n");
                    }
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Muestra el resumen de la carga
        JOptionPane.showMessageDialog(this,
                "Carga de Stock Terminada:\n"
                + "Total productos actualizados: " + Actualizaciones + "\n"
                + "Total errores de formato: " + FallasForm + "\n"
                + "Total códigos no encontrados: " + Noencontrado,
                "Resumen de la Carga",
                JOptionPane.INFORMATION_MESSAGE);
        if (ventana != null) {
            ventana.ActualizarTabla();
        }
        this.dispose();
    }
}
