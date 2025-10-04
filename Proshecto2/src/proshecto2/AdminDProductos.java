/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proshecto2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import static proshecto2.AdminDVendedores.BuscarVendedor;
import static proshecto2.AdminDVendedores.GuardarVendedor;

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
                        } else {
                            JOptionPane.showMessageDialog(null, "Error el fomato de linea o la categoria no es la correcta", "Error", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        listadProductos[CantProducto++] = Nproducto;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al crear el producto desde el csv", "Error", JOptionPane.ERROR_MESSAGE);
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
            if (listadProductos[i].getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    public static Productos BuscarProd(String codigo) {
        for (int i = 0; i < CantProducto; i++) {
            if (listadProductos[i].getCodigo().equals(codigo)) {
                return listadProductos[i];
            }
        }
        return null;
    }

    public static boolean CreacionProducto(String codigo, String nombre, String categoria, double precio, int stock, String Atributo) {
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
    public static boolean ModProductos(String Codigo, String id, String Nnombre, String Natributo) {
        Productos p = BuscarProd(Codigo);
        if (p != null) {
            p.setNombre(Nnombre);
            p.setStock(MProductos);
            return true;
        }
        try{
            if(p instanceof productoTec){
                productoTec Pt = (productoTec) p;
                int garantia = Integer.parseInt(Natributo);
                Pt.setMesesGarantia(garantia);
            } else if(p instanceof productoComida){
                productoComida Pa = (productoComida) p;
                Pa.setFechaVencer(Natributo);
            } else{
                //por si las moscas
                JOptionPane.showMessageDialog(null, "Tipo de producto desconocido", "Error", JOptionPane.ERROR_MESSAGE);
           return false;
            }
        }catch(NumberFormatException e){
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
}
