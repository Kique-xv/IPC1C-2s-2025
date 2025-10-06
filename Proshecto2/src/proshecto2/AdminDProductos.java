
package proshecto2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

import proshecto2.Productos;
/**
 *
 * @author kiquemarroquin
 */
public class AdminDProductos {

    private static final String ArchiProd = "Productos.csv";
    private static final int MProductos = 100; //numero maximo de productos aunque... realmente, para este proyecto es demasiado, igual que esta descripcion totalmente incesesaria, sabes que estas leyendo esto y que yo me estoy poniendo inspirado para roper la 4ta pared, unicamente para que alguien lea esta babosad

    //la matriz para los productos tecnologicos como esta macbook air de 256gb con el chip m1 y la comida como este sabroso tortrix  de limon ©️
    private static Productos[] listadProductos = new Productos[MProductos];
    private static int CantProducto = 0;

//cargamos los datos
    static {
        CargarProductos();
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
                if (datos.length >= Productos.CAMPOS_Prod) {
                    try {
                        String codigo = datos[Productos.CODIGO].trim();
                        String nombre = datos[Productos.NOMBRE].trim();
                        String categoria = datos[Productos.CATEGORIA].trim();
                        double precio = Double.parseDouble(datos[Productos.PRECIO].trim());
                        int stock = Integer.parseInt(datos[Productos.STOCK].trim());
                        Productos Nproducto = null;

                        if (categoria.equals("TECNOLOGIA") && datos.length == productoTec.CAMPOSTEC) {
                            //cargamos los datos Ya se la saben, no quiero explicar mas 
                            int garantia = Integer.parseInt(datos[productoTec.GARANTIA].trim());
                            Nproducto = new productoTec(codigo, nombre, precio, stock, garantia);
                        } else if (categoria.equals("ALIMENTO") && datos.length == productoComida.CAMPOCOM) {
                            //cargamos los datos, del producto de comida
                            String caducidad = datos[productoComida.VENCIMIENTO].trim();
                            Nproducto = new productoComida(codigo, nombre, precio, stock, caducidad);
                        } else if (categoria.equals("OTROS") && datos.length == productoOtros.CAMPORT) {
                            Nproducto = new productoOtros(codigo, nombre, precio, stock);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error el fomato de linea o la categoria no es la correcta", "Error", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        listadProductos[CantProducto++] = Nproducto;
                    } catch (Exception e) {
                     //   JOptionPane.showMessageDialog(null, "Error al crear el producto desde el csv", "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Erroe al guardar los productos en el csv", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        for (int i = 0; i < CantProducto; i++) {
            if (listadProductos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return listadProductos[i];
            }
        }
        return null;
    }

    public static boolean CreacionProducto(String codigo, String nombre, double precio, int stock, String categoria, String Atributo) {
        if (CantProducto >= MProductos) {
            JOptionPane.showMessageDialog(null, "Se llego al limite de productos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (CodRepetido(codigo)) {
            JOptionPane.showMessageDialog(null, "Ese codigo ya esta en uso", "Error", JOptionPane.ERROR_MESSAGE);
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
            return false;
        }

        if (Nproducto != null) {
            listadProductos[CantProducto++] = Nproducto;
          GuardarProductos(); 
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
  if(producCargado > 0 ){
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
            return "carga de archivos exitosa :D " + producCargado + " productos creados ";
        }
    }
    //para la tabla
    public static Object[][] DatosTablaProd(){
        //las 3 columnas codigo, nombre y la categoria
        if(listadProductos == null || CantProducto == 0){
            return new Object[0][3];
        }
        Object[][] datos = new Object[CantProducto][3];
        
        for(int i=0; i<CantProducto; i++){
            Productos p = listadProductos[i];
            
            datos[i][0] = p.getCodigo();
            datos[i][1] = p.getNombre();
            datos[i][2] = p.getCategoria();
        }
        return datos;
    }
}
