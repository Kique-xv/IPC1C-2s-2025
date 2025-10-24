//para el menu de admin
package proshecto2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import proshecto2.Bitacora;

/**
 *
 * @author kiquemarroquin
 */
public class MenuAdmin extends JFrame {

    public MenuAdmin() {
        setTitle("Hip Shop---- Modulo Administrador");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JLabel titulo = new JLabel("Modulo administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 19));
        panel.add(titulo);

        JButton btVendedores = new JButton(" Gestion de Vendedores");
        JButton btProductos = new JButton(" Gestion de Productos");
        JButton btReportes = new JButton("Generar Reportes");
        JButton btBicacora = new JButton("Exportar Bitacora a pdf");
        JButton btBorrar = new JButton("Borrar bitacora");
        JButton btYo = new JButton("Datos del estudiante");
        JButton btCerrar = new JButton("Cerrar sesion");

        panel.add(btVendedores);
        panel.add(btProductos);
        panel.add(btReportes);
        panel.add(btBicacora);
        panel.add(btBorrar);
        panel.add(btYo);

        add(btCerrar, BorderLayout.SOUTH);
        //acciones de los botones aca
        btVendedores.addActionListener(e -> {
            new GestionVendedor().setVisible(true);
        });
        btProductos.addActionListener(e -> {
            new GestionProductos().setVisible(true);
        });

        btReportes.addActionListener(e -> MenuReportes());

        add(panel, BorderLayout.CENTER);

        btCerrar.addActionListener(e -> CerrarSesion());

        btBicacora.addActionListener(e->  Bitacora.BitacoraPdf());
        
        btYo.addActionListener(e ->{
            new SoyViejo().setVisible(true);
        });
        btBorrar.addActionListener(e -> Bitacora.BorrarBitacora());
        
    }

    private void CerrarSesion() {
        //cerrar esta ventana y regresar a la ventana de ingreso
        AdminDUsuarios.BajarSesiones();
        this.dispose();
        new IngresarUsuarios().setVisible(true);
    }

    private void MenuReportes() {
        //para los reportes
        String[] opciones = {"Inventario",
            "Productos mas vendidos",
            "Productos menos vendidos",
            "Ventas por vendedor",
            "Clientes activos",
            "Reporte financiero",
            "Productos por caducar"};
        String seleccion = (String) JOptionPane.showInputDialog(this,
                "Selecciona el reporte que desees generar",
                "Generar Reportes",
                JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]);

        if (seleccion != null) {
            switch (seleccion) {
                case "Inventario":
                    GenerarReportes.GenerarReporteInventario();
                    Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Generar_Reporte_Inventario, Bitacora.ESTADO_EXITOSA, "Generacion de  reporte exitosa");
                    break;
                case "Productos mas vendidos":
                    GenerarReportes.GenerarReporteMasVen();
                    Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Generar_Reporte_Prod_Mas, Bitacora.ESTADO_EXITOSA, "Generacion de reporte exitosa");
                    break;
                case "Productos menos vendidos":
                    GenerarReportes.GenerarReporteMenosVen();
                    Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Generar_Reporte_Prod_Menos, Bitacora.ESTADO_EXITOSA, "Generacion de  reporte exitosa");
                    break;
                case "Ventas por vendedor":
                    GenerarReportes.ReporteVentasVendedor();
                    Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Generar_Reporte_Ventas_Vendedor, Bitacora.ESTADO_EXITOSA, "Generacion de  reporte exitosa");
                    break;
                case "Clientes activos":
                    GenerarReportes.ReporteClientesActivos();
                    Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Generar_Reporte_Clientes_Activos, Bitacora.ESTADO_EXITOSA, "Generacion de  reporte exitosa");
                    break;
                case "Reporte financiero":
                    GenerarReportes.ReporteFinanciero();
                    Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Generar_Reporte_Financiero, Bitacora.ESTADO_EXITOSA, "Generacion de  reporte exitosa");
                    break;
                case "Productos por caducar":
                    GenerarReportes.ReporteProductoCaducar();
                    Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "Admin", Bitacora.OP_Generar_Reporte_Prod_Caducar, Bitacora.ESTADO_EXITOSA, "Generacion de  reporte exitosa");
                    break;
                //los demas
                default:
                    JOptionPane.showMessageDialog(this, "Opcion no existente");
            }
        }
    }
}
