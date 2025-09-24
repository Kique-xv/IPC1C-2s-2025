//Ya se la saben, una clase para ver a los personajes y ya ;3
package practica2;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kiquemarroquin
 */
public class verPersonaje extends JFrame {
    private JTable tablaPerson;
    private JScrollPane panelS;
    
    public verPersonaje(){
        //titulo de la ventana
        setTitle("Personajes Registrados");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //nombres de las columnas
        String[] ncolumns ={"ID", "Nombre", "Arma", "Hp", "Ataque", "Velocidad", "Agilidad", "Defensa", "Historial"};
        
        //para arreglar las filas usamos defaultable Model, esto lo encontre en satackoverflow :3
        DefaultTableModel modTabla = new DefaultTableModel(ncolumns, 0);
        tablaPerson = new JTable(modTabla);
              
        //lenamos la tabla con los personajes del metodo que esta alli abajo
        TablaPersonajes(modTabla);
        
        panelS = new JScrollPane(tablaPerson);
        add(panelS, BorderLayout.CENTER);
    }
        private void TablaPersonajes(DefaultTableModel mod){
            //vamos a loimpiar las filas que ya esten, cuadno se agreguen o  borren personajes
            mod.setRowCount(0);
            
            //un hermoso ciclo for para recorrer la matriz de personajes y agregar cad fila a la tabla de personajes
            for(int i=0; i < Personajes.CantPersonajes; i++){
               // Creamos un arreglo con los datos de cada personaje en la poscion de i
               Object[] filas = new Object[Personajes.personaje[i].length];
               for(int j = 0; j < Personajes.personaje[i].length; j++){
                   filas[j] = Personajes.personaje[i][j];
               }
               mod.addRow(filas);
            }
        }
    }

