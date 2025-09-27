Algoritmo Gestion_Personaje
	Definir opcion Como Entero
	Repetir
		Escribir 'Menu Principal'
		Escribir '1. Agregar Personaje'
		Escribir '2. Modificar Personaje'
		Escribir '3. Eliminar personaje'
		Escribir '4. Ver Personajes registrados'
		Escribir '5. Simulacion de Combate'
		Escribir '6. Ver Historial de Comabte'
		Escribir '7. Guardar y cargar Estado del sistema'
		Escribir '8. Limpiar Estado del sistema'
		Escribir '9. Limpiar Bitacora de acciones'
		Escribir '10. Ver datos del Estudiante'
		Escribir '11. Salir'
		Escribir 'Seleccione una opción: '
		Leer opcion
		Según opcion Hacer
			1:
				Escribir 'Agregar Personaje'
			2:
				Escribir 'Modificar Personaje'
			3:
				Escribir 'Eliminar personaje'
			4:
				Escribir 'Ver Personajes registrados'
			5:
				Escribir 'Simulacion de Combate'
			6:
				Escribir 'Ver Historial de Combate'
			7:
				Escribir 'Guardar y cargar Estado del sistema'
			8:
				Escribir 'Limpiar estado del sistema'
			9:
				Escribir 'Limpiar Bitacora de acciones'
			10:
				Escribir 'Guillermo Hizo esto :3'
			De Otro Modo:
				Escribir 'Opción no válida. Intente nuevamente.'
		FinSegún
	Hasta Que opcion=11
FinAlgoritmo
