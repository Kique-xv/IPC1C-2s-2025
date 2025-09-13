Algoritmo Gestion_Inventario
	Definir opcion Como Entero
	Repetir
		Escribir 'Menu Principal'
		Escribir '1. Agregar Producto'
		Escribir '2. Buscar Producto'
		Escribir '3. Eliminar Producto'
		Escribir '4. Registrar venta'
		Escribir '5. Generar Reportes'
		Escribir '6. Datos del Estudiante'
		Escribir '7. Bitacora'
		Escribir '8. Salir'
		Escribir 'Seleccione una opción: '
		Leer opcion
		Según opcion Hacer
			1:
				Escribir 'Agregar Productos'
			2:
				Escribir 'Buscar Productos'
			3:
				Escribir 'Eliminar Productos'
			4:
				Escribir 'Registrar Ventas'
			5:
				Escribir 'Generar Reportes'
			6:
				Escribir 'Ver Datos del estudiante'
			7:
				Escribir 'Bitacora'
			8:
				Escribir 'Salir'
			De Otro Modo:
				Escribir 'Opción no válida. Intente nuevamente.'
		FinSegún
	Hasta Que opcion=8
FinAlgoritmo
