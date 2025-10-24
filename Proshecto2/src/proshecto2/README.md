README.md
# HIPShop

## Autor YOP
* **Nombre:** <Guillermo Enrique Marroquin Moran>
* **Carnet:** <202103527>
* **Curso:** Introducción a la Programación y Computación 1
* **Sección:** <C>
* **Año:** 2025


Sistema de gestión para una tienda en línea desarrollado como Proyecto 2 para el curso de Introducción a la Programación y Computación 1, Sección <C>, segundo semestre 2025.

## Descripción General 

HIPShop es una aplicación de escritorio desarrollada en Java con interfaz gráfica Swing que simula un sistema de gestión para una tienda. Permite administrar usuarios (administradores, vendedores, clientes), gestionar un catálogo de productos con diferentes categorías, controlar el inventario, procesar pedidos y generar diversos reportes en formato PDF. El sistema utiliza archivos CSV como respaldo y carga inicial, y serialización (`.ser`) para la persistencia del estado entre sesiones.

## Características Principales

* **Módulo de Autenticación:** Inicio de sesión seguro con roles diferenciados (Admin, Vendedor, Cliente).
* **Módulo de Administración:**
    * Gestión completa (CRUD) de Vendedores y Productos.
    * Carga masiva de Vendedores y Productos desde archivos CSV.
    * Visualización de detalles específicos por categoría de producto.
    * Generación de reportes PDF (Inventario, Más/Menos Vendidos, Ventas por Vendedor, Clientes Activos, Financiero, Por Caducar).
* **Módulo de Vendedor:**
    * Gestión de Stock (agregado manual y carga masiva CSV, generación de plantilla, historial de ingresos).
    * Gestión completa (CRUD) de Clientes (asociados al vendedor).
    * Carga masiva de Clientes desde archivos CSV.
    * Visualización y Confirmación de pedidos pendientes.
* **Módulo de Cliente:**
    * Visualización del catálogo de productos con stock disponible.
    * Carrito de compras (agregar, eliminar, actualizar cantidad).
    * Realización de pedidos (quedan pendientes de confirmación).
    * Historial de compras confirmadas.
* **Bitácora del Sistema:** Registro automático de acciones importantes en `bitacora.csv` con opción de exportar a PDF.
* **Persistencia:** Guarda y carga el estado de la aplicación usando Serialización (`.ser`) con fallback a archivos CSV.
* **Hilos:** Monitores en consola para sesiones activas, pedidos pendientes y estadísticas básicas.

## Requisitos Previos 

* **Java Development Kit (JDK):** Versión 8 o superior instalada,
* **IDE (Opcional, para desarrollo):** NetBeans IDE 
* **Librería iText 7 Core:** El archivo `.jar`.

## ¿Cómo Ejecutar el Programa? 

1.  **Descargar el Proyecto:** Clona o descarga el repositorio desde GitHub.
2.  **Desde el JAR:**
    * Ejecuta el archivo `.jar` haciendo doble clic o usando el comando: `java -jar TuNombreDeJar.jar`
3.  **Desde NetBeans (si tienes el código fuente):**
    * Abre el proyecto en NetBeans.
    * Haz clic derecho en el proyecto y selecciona "Run" o presiona .

**Importante:** La primera vez que ejecutes, el programa intentará cargar datos desde los archivos `.csv` si los archivos `.ser` no existen. Asegúrate de que los archivos `Productos.csv`, `Vendedores.csv`, `Clientes.csv`  estén en la carpeta raíz del proyecto, y que ESTEN LIMPIOs
