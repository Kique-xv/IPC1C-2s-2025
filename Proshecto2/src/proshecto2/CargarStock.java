//la clase para cargar stock
package proshecto2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author kiquemarroquin
 */
public class CargarStock extends JFrame {

    private JTextArea result;
    private JButton btCargar;
    private ProductosAlmacen ventana;
    private JButton btGenerarCsv;

    public CargarStock(ProductosAlmacen ventana) {
        this.ventana = ventana;
        setTitle("Cargar el stock en el csv");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelBot = new JPanel(new GridLayout(1, 2, 10, 10));

        btCargar = new JButton("Seleccionar y cargar archivo csv de Stock");
        btGenerarCsv = new JButton("Generar Csv para el stock");
        panelBot.add(btGenerarCsv);
        panelBot.add(btCargar);
        panel.add(panelBot, BorderLayout.NORTH);

        result = new JTextArea("Formato esperado codigo,cantidad\nResultados de la carga saldrán aquí abajo: ");
        result.setEditable(false);

        JScrollPane Sp = new JScrollPane(result);
        panel.add(Sp, BorderLayout.CENTER);

        btCargar.addActionListener(e -> CargarDatoS());
        btGenerarCsv.addActionListener(e -> AdminDProductos.GenerarCsvStock());
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

            //saltamos el encabezdo
            Lector.readLine();
            numlinea++;

            //la validacion del formato de caodigo y cantidad 
            while ((linea = Lector.readLine()) != null) {
                numlinea++;
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    try {
                        String codigo = datos[0].trim();
                        int cant = Integer.parseInt(datos[1].trim());

                        if (AdminDProductos.ActualizarStock(codigo, cant)) {
                            AdminDProductos.RegistrarStock(codigo, cant);
                            Actualizaciones++;
                            result.append("Linea " + numlinea + ": stock actualizado para el producto con el codigo: " + codigo + "\n");
                        } else {

                            Noencontrado++;
                            result.append("Linea " + numlinea + "Error  el " + codigo + " no fue encontrado\n");

                        }
                    } catch (NumberFormatException e) {
                        FallasForm++;
                        result.append("Linea " + numlinea + " Error la cantidad no es el numero valido\n");
                    }
                } else {
                    FallasForm++;
                    result.append("Linea " + numlinea + "Error el formato es incorrecto \n");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Actualizaciones > 0) {
            AdminDProductos.GuardarProductos();
            result.append("\n¡Guardado finalizado con " + Actualizaciones + " productos actualizados!");
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
