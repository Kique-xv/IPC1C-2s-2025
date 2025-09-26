//domir esta sobrevalorado ayudaaaaaa
//me pregunto... como le hare para explicar todo esto en tirmpo record, sin morir en el intento
//spolier, no debi hacer esa pantalla de bienvenida xd
package practica2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author kiquemarroquin
 */
public class historialCombate extends JFrame {

    private JTextArea Harea;
    private JLabel LbFecha;

    public historialCombate() {
        //ya se la saben... el titulo de la ventana y el tamaño de la misma... quiero domrir...
        setTitle("Historial de Combates");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Este es el historial de combates realizados");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(titulo, BorderLayout.NORTH); //esto va a estar ARRIBA

        //agregamos el panel a la fecha y hora
        JPanel panelCentro = new JPanel(new BorderLayout());

        //Inciamos la etiqueta de la fecha
        LbFecha = new JLabel("", SwingConstants.CENTER);
        panelCentro.add(LbFecha, BorderLayout.NORTH);

        Harea = new JTextArea();
        Harea.setEditable(false);
        JScrollPane panelAbajo = new JScrollPane(Harea);
        panelCentro.add(panelAbajo, BorderLayout.CENTER);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);// aca la kgue xd

//el boton para que el historial se actualice 
        JButton btActualizar = new JButton("Actualizar historial");
        panelPrincipal.add(btActualizar, BorderLayout.SOUTH);
        add(panelPrincipal);

        Cargarhist();//un llamado de un metodo que esta alli abajo

        btActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cargarhist();//la misma cosa que dije ariba..
            }
        });
    }

    private void Cargarhist() {
        SimpleDateFormat fechahora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Factual = new Date();
        String HFformat = fechahora.format(Factual);
        
        LbFecha.setText("Ultima actualizacion: " +HFformat);

        StringBuilder Hist = new StringBuilder();

        Hist.append("Historial de Victorias y Derrotas \n");
        Hist.append("-------------------------------------------- \n");//es innecesario, pero se ve mas bonito asi 

        if (Personajes.CantPersonajes == 0) {
            JOptionPane.showMessageDialog(this, "No hay personajes registrados, como para que se madreen", "Error 027", JOptionPane.ERROR_MESSAGE);
                                                                              validarAccion.regisAccion("ver historial de madrazos entre personajes" , false, "Salio MAL APROPOSITO");

        } else {

            for (int i = 0; i < Personajes.CantPersonajes; i++) {
                String nombre = Personajes.personaje[i][Personajes.NOMBRE];
                String vicDerr = Personajes.personaje[i][Personajes.VICDERR];

                String[] datos = new String[5];
                
                if(vicDerr.contains("|")){
                    datos = vicDerr.split("\\|");
                }else {
                    datos[0] = "0";
                    datos[1] = "0";
                    datos[2] = "N/A";
                    datos[3] = "---";
                    datos[4] = "--:--:--";
                }
                //para que se vea mejor el hisotial 
                String victorias = datos.length > 0 ? datos[0] : "0";
                String derrotas = datos.length > 1 ? datos[1] : "0";
                String turnos = datos.length > 2 ? datos[2] : "N/A";
                String fecha = datos.length > 3 ? datos[3] : "----";
                String hora = datos.length > 4 ? datos[4] : "--:--:--";

                Hist.append("Personaje: ").append(nombre).append("\n");//salto de linea
                Hist.append("Vicotorias: ").append(victorias).append("Derrotas: ").append(derrotas).append("\n");
                Hist.append("Ultimo Combate: ").append(turnos).append(" turnos \n");
                Hist.append("fecha  :").append(fecha).append("  |Hora:   ").append(hora).append("\n");
                Hist.append("---------------------------------------------------------\n");
                                                                                  validarAccion.regisAccion("ver historial de madrazos entre personajes" , true, "Salio bien");

            }
        }
        Harea.setText(Hist.toString());
    }
}
