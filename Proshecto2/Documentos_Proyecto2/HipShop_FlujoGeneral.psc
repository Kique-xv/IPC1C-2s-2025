Algoritmo HipShop_FlujoGeneral
	// --- INICIO Y CARGA INICIAL ---
	Escribir 'INICIAR PROGRAMA SANCARLISTA SHOP'
	Escribir 'Llamar a AdminD...inicializar() para todos los m�dulos'
	Escribir '(Cargar estado desde archivos .ser o .csv)'
	Escribir 'ESTADO INICIAL CARGADO'
	Escribir 'Iniciar Hilos Monitores (Sesiones, Pedidos, Estad�sticas)'
	Escribir 'HILOS INICIADOS'
	// --- BUCLE PRINCIPAL (Login o Salir) ---
	Definir salirAplicacion Como L�gico
	salirAplicacion <- Falso
	Mientras  NO salirAplicacion Hacer
	
	FinMientras
	// --- PANTALLA DE LOGIN ---
	Escribir 'Mostrar Ventana de Login (Pedir ID y Contrase�a)' // <-- Inicia Mientras (L�nea 15 aprox)
	Leer idIngresado, contrasenaIngresada
	// Simular opci�n de salida directa desde el login
	Si idIngresado=='SALIR' Entonces // Simula entrada
	
	FinSi
	salirAplicacion <- Verdadero // <-- Inicia Si (L�nea 22 aprox)
	// --- AUTENTICACI�N ---
	Escribir 'Llamar a AdminDUsuarios.autenticazion(id, contrase�a)'
	Definir usuarioValidado Como L�gico
	Definir tipoUsuario Como Cadena
	// (Simulaci�n: Determinar si 'usuarioValidado' es Verdadero y obtener 'tipoUsuario')
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
		// --- DECISI�N POST-AUTENTICACI�N ---
		Si usuarioValidado Entonces // Cierra el Si de la simulaci�n
			Escribir 'Llamar a AdminDUsuarios.incrementarSesiones()' // <-- Inicia Si (L�nea 43 aprox, relacionado con error 31)
			Escribir 'Llamar a Bitacora.registrarEvento(LOGIN_EXITOSO)'
			// --- NAVEGACI�N SEG�N TIPO DE USUARIO ---
			Seg�n tipoUsuario Hacer
				'ADMIN':
					Escribir 'Abrir Men� Administrador'
					Escribir 'Usuario interact�a con Men� Admin hasta Logout...'
					Escribir 'Al hacer Logout, llamar a AdminDUsuarios.decrementarSesiones()'
				'VENDEDOR': // Simula acci�n Logout
					Escribir 'Abrir Men� Vendedor'
					Escribir 'Usuario interact�a con Men� Vendedor hasta Logout...'
					Escribir 'Al hacer Logout, llamar a AdminDUsuarios.decrementarSesiones()'
				'CLIENTE': // Simula acci�n Logout
					Escribir 'Abrir Men� Cliente'
					Escribir 'Usuario interact�a con Men� Cliente hasta Logout...'
					Escribir 'Al hacer Logout, llamar a AdminDUsuarios.decrementarSesiones()'
				De Otro Modo: // Simula acci�n Logout
					Escribir 'Mostrar Error: Tipo de usuario no reconocido'
					// Decrementar si se increment� por error
					Escribir 'Llamar a AdminDUsuarios.decrementarSesiones()'
			FinSeg�n
		SiNo
			// Despu�s del Logout en cada men�, el flujo vuelve al inicio del bucle Mientras
			Escribir 'Llamar a Bitacora.registrarEvento(LOGIN_FALLIDO)' // Usuario NO validado
			Escribir 'Mostrar Mensaje: Credenciales incorrectas'
			// El bucle Mientras vuelve a mostrar el Login
		FinSi
	FinSi // <-- CORRECCI�N: Cierra el Si 'usuarioValidado' (Error 31)
	// --- FIN DEL PROGRAMA ---
	Escribir 'CERRANDO APLICACI�N...' // <-- CORRECCI�N: Cierra el Si 'idIngresado == "SALIR"' (Error 22)
	Escribir '(Detener Hilos Monitores si es necesario)'
	Escribir 'FIN DEL PROGRAMA'
FinAlgoritmo
