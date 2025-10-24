package proshecto2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

import proshecto2.Productos;

/**
 *
 * @author kiquemarroquin
 */
public class AdminDProductos {

    private static final String ArchiProd = "Productos.csv"; //csv
    private static final String Estado_Producto = "Productos.ser"; //ser

    private static final int MProductos = 100; //numero maximo de productos aunque... realmente, para este proyecto es demasiado, igual que esta descripcion totalmente incesesaria, sabes que estas leyendo esto y que yo me estoy poniendo inspirado para roper la 4ta pared, unicamente para que alguien lea esta babosad
    private static final String ArchiStock = "Stock.csv";
    //la matriz para los productos tecnologicos como esta macbook air de 256gb con el chip m1 y la comida como este sabroso tortrix  de limon ©️
    private static Productos[] listadProductos = new Productos[MProductos];
    private static int CantProducto = 0;
    
// para guardar en el csv
    public static void Guardarestado() {
        System.out.println("Serializando estado de Productos "); 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Estado_Producto))) {
            oos.writeInt(CantProducto); // Guardar el contador primero
            for (int i = 0; i < CantProducto; i++) {
                oos.writeObject(listadProductos[i]); // Guardar cada objeto
            }
            System.out.println("Estado de productos guardado en " + Estado_Producto); 
        } catch (IOException e) {
            System.err.println("Error al guardar estado de productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void cargarEstado() {
        File archivoEstado = new File(Estado_Producto);
        System.out.println("Deserializando estado de Productos"); 
        if (!archivoEstado.exists()) {
            System.out.println("El Archivo " + Estado_Producto + " no encontradose cargara desde el csv"); 
            CargarProductos(); // cargar desde CSV como respaldo
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoEstado))) {
            CantProducto = ois.readInt(); // Leer el contador
            listadProductos = new Productos[MProductos]; // Crear el arreglo
            for (int i = 0; i < CantProducto; i++) {
                listadProductos[i] = (Productos) ois.readObject(); //cada objeto
            }
            System.out.println("Estado de productos cargado desde " + Estado_Producto + "Total: " + CantProducto); 
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("Error al cargar estado de producto: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar datos guardados de productos, se cargara  desde el csv", "Error", JOptionPane.WARNING_MESSAGE);
            // Si falla la, intenta cargar desde CSV
            CargarProductos();
        }
    }

    public static void inicializar() {
        System.out.println("INICIALIZANDO SISTEMA DE PRODUCTOS...");
        cargarEstado();
        System.out.println("Sistema de productos listo");
    }

    public static void CargarProductos() {
        File Archivo = new File(ArchiProd);
        if (!Archivo.exists()) {
            JOptionPane.showMessageDialog(null, "El archivo de productos, no se encontro, pero aun asi lo inciamos vacio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        CantProducto = 0;
        try (BufferedReader lector = new BufferedReader(new FileReader(Archivo))) {
            String Linea;

            while ((Linea = lector.readLine()) != null && CantProducto < listadProductos.length) {
                String[] datos = Linea.split(",");

                if (datos.length >= 6) {
                    try {
                        String codigo = datos[Productos.CODIGO].trim();
                        String nombre = datos[Productos.NOMBRE].trim();
                        String categoria = datos[Productos.CATEGORIA].trim();
                        double precio = Double.parseDouble(datos[Productos.PRECIO].trim());
                        int stock = Integer.parseInt(datos[Productos.STOCK].trim());
                        Productos Nproducto = null;
                        if (categoria.equals("TECNOLOGIA")) {
                            //cargamos los datos Ya se la saben, no quiero explicar mas
                            int garantia = Integer.parseInt(datos[5].trim());//el indice directo
                            Nproducto = new productoTec(codigo, nombre, precio, stock, garantia);
                        } else if (categoria.equals("ALIMENTO")) {
                            //cargamos los datos, del producto de comida
                            String caducidad = datos[5].trim();
                            Nproducto = new productoComida(codigo, nombre, precio, stock, caducidad);
                        } else if (categoria.equals("OTROS")) {
                            Nproducto = new productoOtros(codigo, nombre, precio, stock);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error el fomato de linea o la categoria no es la correcta", "Error", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        listadProductos[CantProducto++] = Nproducto;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al procesar una línea en Productos.csv.\nLínea con problemas: '" + Linea + "'\nError: " + e.getMessage(), "Error de Formato en Archivo", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo CSV de productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void GuardarProductos() {
        try (PrintWriter escribir = new PrintWriter(new FileWriter(ArchiProd))) {
            for (int i = 0; i < CantProducto; i++) {
                Productos p = listadProductos[i];
                //usamos el tocsvline para obtener liena comleta
                escribir.println(p.toCsLine());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los productos en el csv", "Error", JOptionPane.ERROR_MESSAGE);
        }
        Guardarestado();
    }

    public static boolean CodRepetido(String codigo) {
        for (int i = 0; i < CantProducto; i++) {
            if (listadProductos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    public static Productos BuscarProd(String codigo) {
        //buscamos los productos en la matriz de productos
        for (int i = 0; i < CantProducto; i++) {
            String codigoEnLista = listadProductos[i].getCodigo();

            if (codigoEnLista.equalsIgnoreCase(codigo)) {
                return listadProductos[i];
            }
        }
        return null;
    }

    public static boolean CreacionProducto(String codigo, String nombre, double precio, int stock, String categoria, String Atributo) {
        if (CantProducto >= MProductos) {
            JOptionPane.showMessageDialog(null, "Se llego al limite de productos", "Error", JOptionPane.ERROR_MESSAGE);
            Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "admin", Bitacora.OP_Crear_Producto, Bitacora.ESTADO_FALLIDA, "Limite de productos alcanzado");

            return false;
        }
        if (CodRepetido(codigo)) {
            JOptionPane.showMessageDialog(null, "Ese codigo ya esta en uso", "Error", JOptionPane.ERROR_MESSAGE);
            Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "admin", Bitacora.OP_Crear_Producto, Bitacora.ESTADO_FALLIDA, "Codigo de producto repetido");
            return false;
        }
        Productos Nproducto = null;

        String catego = categoria.toUpperCase();
        try {
            if (catego.equals("TECNOLOGIA")) {
                int garantia = Integer.parseInt(Atributo);
                Nproducto = new productoTec(codigo, nombre, precio, stock, garantia);
            } else if (catego.equals("ALIMENTO")) {
                //la fecha de vencimineto ya viene como estring
                Nproducto = new productoComida(codigo, nombre, precio, stock, Atributo);
            } else if (catego.equals("OTROS")) {
                Nproducto = new productoOtros(codigo, nombre, precio, stock);
            } else {
                JOptionPane.showMessageDialog(null, "Caetgoria de producto no valida", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El atributo especifico no es valido para la categoria", "Error", JOptionPane.ERROR_MESSAGE);
            Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "admin", Bitacora.OP_Crear_Producto, Bitacora.ESTADO_FALLIDA, "Creacion de producto fallida");
            return false;
        }

        if (Nproducto != null) {
            listadProductos[CantProducto++] = Nproducto;
            GuardarProductos();
            Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "admin", Bitacora.OP_Crear_Producto, Bitacora.ESTADO_EXITOSA, "Creacion de producto exitosa");
            return true;
        }
        return false;
    }

    //los demas metodos faltan todvia xd
    public static boolean ModProductos(String Codigo, String Nnombre, String Natributo) {
        Productos p = BuscarProd(Codigo);
        if (p != null) {
            p.setNombre(Nnombre);
            p.setStock(MProductos);
            return true;
        }
        try {
            if (p instanceof productoTec) {
                productoTec Pt = (productoTec) p;
                int garantia = Integer.parseInt(Natributo);
                Pt.setMesesGarantia(garantia);
            } else if (p instanceof productoComida) {
                productoComida Pa = (productoComida) p;
                Pa.setFechaVencer(Natributo);
            } else {
                //por si las moscas
                JOptionPane.showMessageDialog(null, "Tipo de producto desconocido", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "el nuevo valo especifico (La garantia o la caducidad) no es valido", "Error", JOptionPane.ERROR_MESSAGE);
        }
        GuardarProductos();
        return false;
    }

    //un metodo de eliminacion por el codigo
    public static boolean EliminarProducto(String codigo) {
        for (int i = 0; i < CantProducto; i++) {
            if (listadProductos[i].getCodigo().equals(codigo)) {
                //el todo confiable ciclo for
                for (int j = i; j < CantProducto - 1; j++) {
                    listadProductos[j] = listadProductos[j + 1];
                }
                listadProductos[CantProducto - 1] = null; //eliminamos la ultima referencia
                CantProducto--;
                GuardarProductos();
                Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "admin", Bitacora.OP_Eli_Producto, Bitacora.ESTADO_EXITOSA, "Eliminacion de producto exitosa");
                return true;
            }
        }
        return false;
    }

    public static String CargadProductos(String Archiv) {
        int producCargado = 0;
        int lineaFail = 0;
        StringBuilder Error = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new FileReader(Archiv))) {
            String linea;
            int numLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numLinea++;
                String lineat = linea.trim();
                if (lineat.isEmpty()) {
                    continue;
                }

                String[] datos = lineat.split(",");

                //validamos que haya almenos 6 campos los 5 normales + el atributo unico
                if (datos.length != Productos.CAMPOS_Prod) {
                    Error.append("Linea").append(numLinea).append(": formato incorrecto, se esperan 6 lineas").append(datos.length).append("\n");
                    lineaFail++;
                    continue;
                }
                try {
                    //mapeamos los datos
                    String codigo = datos[0].trim();
                    String nombre = datos[1].trim();
                    String precioStr = datos[2].trim();
                    String stockStr = datos[3].trim();
                    String categoria = datos[4].trim().toUpperCase();
                    String atributo = datos[5].trim();
//pareseamos los numeros
                    double precio = Double.parseDouble(precioStr);
                    int stock = Integer.parseInt(stockStr);

//validaciones de los valores de precio y stcock
                    if (precio <= 0 || stock <= 0) {
                        throw new Exception("El precio y el stock deben ser valores positivos.");
                    }
                    //previsualizar el atributo y el formato
                    if (categoria.equals("TECNOLOGIA")) {
                        try {
                            int garantia = Integer.parseInt(atributo);
                            if (garantia <= 0) {
                                throw new Exception("La garantia debe de ser un numero entero");
                            }
                        } catch (NumberFormatException e) {
                            throw new Exception("La garantia no es un valor valido");
                        }
                    } else if (categoria.equals("OTROS")) {
                        //por si las moscas, aunque la categoria otros ignora el atributo especifico xd, pero en fin, para que esta mrd no se caiga a pedazos
                        atributo = "N/A"; //no atributo
                    } else if (categoria.equals("ALIMENTO")) {
                        if (!productoComida.FechaValida(atributo)) {
                            throw new Exception("Formato de fecha no valido, formato esperado(YYYY-MM-DD)");
                        }
                    }
                    //llamamos al metodo de crear producto, para el manejo de  errores como el codigo repetido, y el fomato de fecha y garantia
                    if (CreacionProducto(codigo, nombre, precio, stock, categoria, atributo)) {
                        producCargado++;
                    } else {
                        //por si falla debe de salir el mensaje de las validaciones pero por si las moscas
                        Error.append("Linea ").append(numLinea).append(": fallo al crearse, posiblemente, por el codigo duplicado, el precio o stock on invalidos o el limite de productos ya se alcanzo \n ");
                        lineaFail++;
                    }
                } catch (Exception e) {
                    //capturamos errore en el precio o stock o las validaciones de gestion de productos
                    Error.append("Linea ").append(numLinea).append(": error de datos").append(e.getMessage()).append("\n");
                    lineaFail++;
                }
            }
            if (producCargado > 0) {
                GuardarProductos();
            }
        } catch (FileNotFoundException e) {
            return "Error al no enocontrar al arhivo ";
        } catch (IOException e) {
            return "Error al leer el archivo " + e.getMessage();

        }
        if (lineaFail > 0) {
            Error.insert(0, "resumen: " + producCargado + ": productos cargados " + lineaFail + " lineas fallaron \n");
            return Error.toString();
        } else {
            Bitacora.RegistrarEvento(Bitacora.Tipo_Admin, "admin", Bitacora.OP_Cargar_Producto, Bitacora.ESTADO_EXITOSA, "Carga de productos exitosa");
            return "carga de archivos exitosa :D " + producCargado + " productos creados ";
        }
    }

    //para la tabla
    public static Object[][] DatosTablaProd() {
        //las 3 columnas codigo, nombre y la categoria
        if (listadProductos == null || CantProducto == 0) {
            return new Object[0][5];
        }
        Object[][] datos = new Object[CantProducto][5];

        for (int i = 0; i < CantProducto; i++) {
            Productos p = listadProductos[i];

            datos[i][0] = p.getCodigo();
            datos[i][1] = p.getNombre();
            datos[i][2] = p.getCategoria();
            datos[i][3] = p.getPrecio();
            datos[i][4] = p.getStock();

        }
        return datos;
    }

    //para agregar el stock
    public static boolean agregarStock(String IdProd, int Cant, String usuarioId, String nombreUsur) {
        //buscamos el producto por su codigo
        Productos p = BuscarProd(IdProd);
        if (p != null) {
            //obtener el stock actual u sumar lo que vamos a meter
            int NStock = p.getStock() + Cant;

            //actualizamos el objeto en la memoria
            p.setStock(NStock);

            //hacemos nuestro llamado para que se guarde
            RegistrarStock(IdProd, Cant, usuarioId, nombreUsur);
            GuardarProductos();
            Bitacora.RegistrarEvento(Bitacora.Tipo_Vendedor, usuarioId, Bitacora.OP_Agregar_Stock, Bitacora.ESTADO_EXITOSA, "Se agrego stock a los productos");
            return true;
        }
        return false;
    }

//lo podria poner en el de agregar stock pero no quiero mas lios... ya me canse
    //un metodo para eso del csv de los producot alm
    public static boolean ActualizarStock(String codigo, int Cant, String usuarioId, String nombreUsur) {
        Productos p = BuscarProd(codigo);
        if (p != null) {

            int Nstock = p.getStock() + Cant;
            //añadimos el stock acutal mas el que agreguemos
            p.setStock(Nstock);
            return true;
        }
        return false;
    }

    public static void RegistrarStock(String codigo, int Cantidad, String usuarioId, String nombreUsur) {
        File ArchivoHis = new File("HistorialStock.csv");
        boolean Narchivo = !ArchivoHis.exists();
        try (PrintWriter escribit = new PrintWriter(new FileWriter(ArchivoHis, true))) {
            //obtener la fecha

            if (Narchivo) {
                escribit.println("fecha_hora_iso,codigo_producto,cantidad_agregada,usuario_id,usuario_nombre");
            }
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String horafecha = ahora.toString();

            //creamos la line para guardar
            String lineacsv = horafecha + "," + codigo + "," + Cantidad + "," + usuarioId + "," + nombreUsur;
            escribit.println(lineacsv);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo registrar el movimiento en el historial de stock.", "Error de Historial", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean GenerarCsvStock() {
        String ruta = System.getProperty("user.home") + "/Desktop/plantilla_stock.csv";
        File Archiv = new File(ruta);
        try (PrintWriter escribir = new PrintWriter(new FileWriter(Archiv))) {
            // Escribimos el encabezado de la plantilla
            escribir.println("codigo,cantidad_a_agregar");
            for (int i = 0; i < CantProducto; i++) {
                Productos p = listadProductos[i];
                // Para cada producto, escribimos su código, nombre, y un '0'
                escribir.println(p.getCodigo() + ",0");
            }
            JOptionPane.showMessageDialog(null,
                    "¡Plantilla generada con éxito!\nEsta en el escritorio con el nombre: plantilla_stock.csv",
                    "Plantilla Creada",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al generar la plantilla de stock: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //para generar el csv de ingresos xd
    public static void generarHisIngreso() {
        File archivHist = new File("HistorialStock.csv");
        if (!archivHist.exists()) {
            JOptionPane.showMessageDialog(null, "Aun no exite ningun archivo de historial", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String rutaAchiv = System.getProperty("user.home") + "/Desktop/Historial_Ingresos.csv";
        File archivRep = new File(rutaAchiv);

        try (BufferedReader lector = new BufferedReader(new FileReader(archivHist)); PrintWriter escribir = new PrintWriter(new FileWriter(archivRep))) {

            String linea;
            while ((linea = lector.readLine()) != null) {
                escribir.println(linea);
            }
            JOptionPane.showMessageDialog(null,
                    "El reporte de Stock ha sido generado. \nEste se encuentra en el escritorio",
                    "Reporte Creado",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //para la tabla del cliente xd
    public static Object[][] DatosTablaCliente() {
        //creamos un arreglo de 4 columnas, codigo, nombre categoria y el sotkc disponibla
        Object[][] datos = new Object[CantProducto][4];

        for (int i = 0; i < CantProducto; i++) {
            Productos p = listadProductos[i];
            //los datos de la tabla xd
            datos[i][0] = p.getCodigo();
            datos[i][1] = p.getNombre();
            datos[i][2] = p.getCategoria();
            datos[i][3] = p.getStock();
        }
        return datos;
    }

    //Para el wn del vendedor xd
    public static void ReservaStock(String codigoProducto, int CantComprar) {
        Productos p = BuscarProd(codigoProducto);
        if (p != null) {
            p.setStock(p.getStock() - CantComprar);
        }
    }

    //devolvemos una copar del arrglo de productos 
    public static Productos[] getListadProductos() {
        return listadProductos;
    }

    //y esto es para obtenerlos 
    public static int getCantProducto() {
        return CantProducto;
    }
}
