Algoritmo HipShop_FlujoGeneral
	// --- INICIO Y CARGA INICIAL ---
	Escribir 'INICIAR PROGRAMA SANCARLISTA SHOP'
	Escribir 'Llamar a AdminD...inicializar() para todos los módulos'
	Escribir '(Cargar estado desde archivos .ser o .csv)'
	Escribir 'ESTADO INICIAL CARGADO'
	Escribir 'Iniciar Hilos Monitores (Sesiones, Pedidos, Estadísticas)'
	Escribir 'HILOS INICIADOS'
	// --- BUCLE PRINCIPAL (Login o Salir) ---
	Definir salirAplicacion Como Lógico
	salirAplicacion <- Falso
	Mientras  NO salirAplicacion Hacer
	
	FinMientras
	// --- PANTALLA DE LOGIN ---
	Escribir 'Mostrar Ventana de Login (Pedir ID y Contraseña)' // <-- Inicia Mientras (Línea 15 aprox)
	Leer idIngresado, contrasenaIngresada
	// Simular opción de salida directa desde el login
	Si idIngresado=='SALIR' Entonces // Simula entrada
	
	FinSi
	salirAplicacion <- Verdadero // <-- Inicia Si (Línea 22 aprox)
	// --- AUTENTICACIÓN ---
	Escribir 'Llamar a AdminDUsuarios.autenticazion(id, contraseña)'
	Definir usuarioValidado Como Lógico
	Definir tipoUsuario Como Cadena
	// (Simulación: Determinar si 'usuarioValidado' es Verdadero y obtener 'tipoUsuario')
	// Ejemplo simple:
	Si idIngresado=='admin' Y contrasenaIngresada=='passA' Entonces
	
	FinSi
	usuarioValidado <- Verdadero
	tipoUsuario <- 'ADMIN'
	Si idIngresado=='vend1' Y contrasenaIngresada=='passV' Entonces
		usuarioValidado <- Verdadero
		tipoUsuario <- 'VENDEDOR'
	SiNo
		Si idIngresado=='cli1' Y contrasenaIngresada=='passC' Entonces
			usuarioValidado <- Verdadero
			tipoUsuario <- 'CLIENTE'
		SiNo
			usuarioValidado <- Falso
			tipoUsuario <- 'DESCONOCIDO'
		FinSi
		// --- DECISIÓN POST-AUTENTICACIÓN ---
		Si usuarioValidado Entonces // Cierra el Si de la simulación
			Escribir 'Llamar a AdminDUsuarios.incrementarSesiones()' // <-- Inicia Si (Línea 43 aprox, relacionado con error 31)
			Escribir 'Llamar a Bitacora.registrarEvento(LOGIN_EXITOSO)'
			// --- NAVEGACIÓN SEGÚN TIPO DE USUARIO ---
			Según tipoUsuario Hacer
				'ADMIN':
					Escribir 'Abrir Menú Administrador'
					Escribir 'Usuario interactúa con Menú Admin hasta Logout...'
					Escribir 'Al hacer Logout, llamar a AdminDUsuarios.decrementarSesiones()'
				'VENDEDOR': // Simula acción Logout
					Escribir 'Abrir Menú Vendedor'
					Escribir 'Usuario interactúa con Menú Vendedor hasta Logout...'
					Escribir 'Al hacer Logout, llamar a AdminDUsuarios.decrementarSesiones()'
				'CLIENTE': // Simula acción Logout
					Escribir 'Abrir Menú Cliente'
					Escribir 'Usuario interactúa con Menú Cliente hasta Logout...'
					Escribir 'Al hacer Logout, llamar a AdminDUsuarios.decrementarSesiones()'
				De Otro Modo: // Simula acción Logout
					Escribir 'Mostrar Error: Tipo de usuario no reconocido'
					// Decrementar si se incrementó por error
					Escribir 'Llamar a AdminDUsuarios.decrementarSesiones()'
			FinSegún
		SiNo
			// Después del Logout en cada menú, el flujo vuelve al inicio del bucle Mientras
			Escribir 'Llamar a Bitacora.registrarEvento(LOGIN_FALLIDO)' // Usuario NO validado
			Escribir 'Mostrar Mensaje: Credenciales incorrectas'
			// El bucle Mientras vuelve a mostrar el Login
		FinSi
	FinSi // <-- CORRECCIÓN: Cierra el Si 'usuarioValidado' (Error 31)
	// --- FIN DEL PROGRAMA ---
	Escribir 'CERRANDO APLICACIÓN...' // <-- CORRECCIÓN: Cierra el Si 'idIngresado == "SALIR"' (Error 22)
	Escribir '(Detener Hilos Monitores si es necesario)'
	Escribir 'FIN DEL PROGRAMA'
FinAlgoritmo
