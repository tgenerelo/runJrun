package runJrun;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	static Scanner leer = new Scanner(System.in);
	static Carrera carrera = new Carrera("", 5, 9);
	static Coche coche = new Coche("", true, "-?!#");

	private static int ANCHOTOTAL = Main.ANCHOTOTAL;

	public static void menuPrincipal() {
		String titulo = "Menú principal";
		String vOpciones[] = { "Nueva carrera", "Configuración", "Acerca de runJrun", "Salir" };
		int userInput = 0;
		carrera = new Carrera("", 5, 9);
		coche = new Coche("", true, "-?!#");

		userInput = genMenu(titulo, vOpciones);

		switch (userInput) {
		case 1:
			nuevaCarrera();
			break;
		case 2:
			menuConfig();
			break;
		case 3:
			about();
			menuPrincipal();
			break;
		case 4:
			String mensaje = "Gracias por jugar a runJrun. El programa se cerrará.";
			System.out.println();
			for (int i = 0; i < (ANCHOTOTAL - mensaje.length()) / 2; i++) {
				System.out.print(" ");
			}
			System.out.println(mensaje);
			System.exit(0); // CAMBIAR EN CUANTO SEA POSIBLE
			
			break;
		}
	}

	private static void nuevoJugador() {
		String titulo = "Añadir un jugador";
		String vOpciones[] = { "Nombre del jugador", "Número de dorsal", "Guardar", "Cancelar" };
		int userInput = 0;

		userInput = genMenu(titulo, vOpciones);

		switch (userInput) {
		case 1:
			coche.setPiloto(genMenuAjuste(("Nombre del jugador"),
					new String[] {
							("Nombre del piloto controlado por el JUGADOR " + (carrera.getNumJugadores() + 1) + ".") },
					"un nombre"));
			break;
		case 2:
			coche.setDorsal(genMenuAjuste(("Número de dorsal"),
					new String[] { ("Número de dorsal para el JUGADOR " + (carrera.getNumJugadores() + 1) + "."),
							"Se recomienda un máximo de 2 caracteres alfanuméricos",
							"(p. ej.: \"45\", \"AB\", \"4P\")" },
					"un código de dorsal"));
			break;
		case 3:
			carrera.agregarCoche(coche);
			coche = new Coche("", true, "-?!#");
			nuevaCarrera();
			break;
		case 4:
			nuevaCarrera();
			break;
		}
		nuevoJugador();
	}

	public static void nuevaCarrera() {
		String titulo = "Nueva carrera";
		String vOpciones[] = { "Añadir jugador", "Nombre de la carrera", "Número de participantes",
				"Distancia de la carrera", "Patrocinador de la carrera", "Comenzar carrera", "Cancelar" };
		int userInput = 0;

		userInput = genMenu(titulo, vOpciones);

		switch (userInput) {
		case 1:
			nuevoJugador();
			break;
		case 2:
			carrera.setNombre(String.valueOf(
					genMenuAjuste(vOpciones[1], new String[] { "El nombre que recibirá la carrera.", }, "un nombre")));
			break;
		case 3:
			carrera.setNumCompetidores(genMenuAjuste(vOpciones[2],
					new String[] { "El número de coches que competirán en la carrera.",
							"Si el número total es mayor que el número de",
							"coches controlados por el jugador, se rellenarán",
							"los sitios libres automáticamente con competidores", "controlados por la máquina.", "  ",
							"Mínimo: 1, máximo: 200." },
					1, 200));
			break;
		case 4:
			carrera.setLongitud(
					genMenuAjuste(vOpciones[3], new String[] { "La longitud en kilómetros que tendrá la carrera.", "  ",
							"Mínimo: 0.1 km. Máximo: 300 km. Valor por defecto: 5 km" }, 0.1f, 300f));

			break;
		case 5:
			carrera.setPatrocinador(genMenuAjuste(vOpciones[4],
					new String[] { "La empresa o marca que patrocina la carrera." }, "un patrocinador"));
			break;
		case 6:
			carrera.comenzar();
			carrera = new Carrera("", 5, 9);

			menuPrincipal();
		case 7:
			menuPrincipal();
		}

		nuevaCarrera();

	}

	private static void menuConfig() {
		String titulo = "Configuración del juego";
		String vOpciones[] = { "Configurar ancho del programa", "Cambiar la escala de tiempo",
				"Cambiar la potencia del coche", "Volver al menú" };
		int userInput = 0;

		userInput = genMenu(titulo, vOpciones);

		switch (userInput) {
		case 1:
			Main.ANCHOTOTAL = genMenuAjuste(vOpciones[0],
					new String[] { "El ancho máximo que tendrá la ventana de juego.", "  ",
							"Mínimo: 80, máximo 400. Valor por defecto: 120." },
					80, 400);
			ANCHOTOTAL = Main.ANCHOTOTAL;
			break;
		case 2:
			Main.SEGUNDOSTURNO = genMenuAjuste(vOpciones[1],
					new String[] { "La duración en segundos de cada turno de juego.", "  ",
							"Mínimo: 5, máximo 30. Valor por defecto: 10.", },
					5, 30);
			break;
		case 3:
			Main.POTENCIA = genMenuAjuste(vOpciones[2],
					new String[] { "La potencia que tendrán todos los coches del juego.",
							"Ten en cuenta que la distancia recorrida se calcula",
							"aleatoriamente en base a la potencia del motor, por",
							"lo que una mayor potencia implica una mayor",
							"probabilidad de accidente a altas velocidades.", " ",
							"Mínimo: 20, máximo: 80. Valor por defecto: 50" },
					20, 80);
			break;
		case 4:
			menuPrincipal();
			break;
		}
		menuConfig();
	}

	private static int pintarEscaner(String vOpciones[]) {
		int userInput = 0;
		String texto = "Introduce una opción: > ";
		leer = new Scanner(System.in);

		for (int i = 0; i < (ANCHOTOTAL - texto.length()) / 2; i++) {
			System.out.print(" ");
		}
		System.out.print(texto);

		try {
			leer = new Scanner(System.in);
			userInput = leer.nextInt();
		} catch (InputMismatchException e) {
			leer = new Scanner(System.in);
			return -1;
		}

		if (userInput < 1 || userInput > vOpciones.length) {
			return -1;
		} else {
			return userInput;
		}
	}

	public static int pintarEscaner(int valorMin, int valorMax) {
		int userInput = 0;
		String texto = "";
		leer = new Scanner(System.in);

		if (valorMin == valorMax) {
			texto = "[ 1. Volver ] > ";
		} else {
			texto = "Introduce un valor: > ";
		}

		for (int i = 0; i < (ANCHOTOTAL - texto.length()) / 2; i++) {
			System.out.print(" ");
		}
		System.out.print(texto);

		try {
			leer = new Scanner(System.in);
			userInput = leer.nextInt();
		} catch (InputMismatchException e) {
			leer = new Scanner(System.in);
			return -1;
		}

		if (userInput < valorMin || userInput > valorMax) {
			return -1;
		} else {
			return userInput;
		}
	}

	private static float pintarEscanerFloat(float valorMin, float valorMax) {
		String userInput = "";
		float inputFloat = 0f;
		String texto = "Introduce un valor: > ";
		leer = new Scanner(System.in);

		for (int i = 0; i < (ANCHOTOTAL - texto.length()) / 2; i++) {
			System.out.print(" ");
		}
		System.out.print(texto);

		try {
			leer = new Scanner(System.in);
			inputFloat = Float.valueOf(userInput = leer.next());
		} catch (InputMismatchException e) {
			leer = new Scanner(System.in);
			return -1;
		} catch (Exception e) {
			leer = new Scanner(System.in);
			return -1;
		}

		if (inputFloat < valorMin || inputFloat > valorMax) {
			return -1;
		} else {
			return inputFloat;
		}
	}

	private static String pintarEscaner(String nombreNombre) {
		String userInput = "";
		String texto = "Introduce " + nombreNombre + ": > ";
		leer = new Scanner(System.in);

		for (int i = 0; i < (ANCHOTOTAL - texto.length()) / 2; i++) {
			System.out.print(" ");
		}
		System.out.print(texto);

		do {
			try {
				userInput = leer.nextLine();
			} catch (InputMismatchException e) {
				leer = new Scanner(System.in);
				return ".!#$?";
			} catch (Exception e) {
				return ".!#$?";
			}

		} while (userInput == ".!#$?");

		return userInput;
	}

	private static int calcularAnchoOpciones(String vOpciones[]) {
		int anchoMax = 0;

		for (String opcion : vOpciones) {
			if (opcion.length() > anchoMax)
				anchoMax = opcion.length();
		}
		return anchoMax + 3;
	}

	private static int genMenu(String titulo, String vOpciones[]) {
		String mensError = "Opción no válida. Vuelve a intentarlo.";
		int userInput = 0;

		do {

			pintarMenu(titulo, vOpciones);

			if (userInput == -1) {
				for (int i = 0; i < (ANCHOTOTAL - mensError.length()) / 2; i++) {
					System.out.print(" ");
				}
				System.out.println(mensError);
			}

			userInput = pintarEscaner(vOpciones);
		} while (userInput == -1);
		return userInput;
	}

	private static int genMenuAjuste(String titulo, String vDescripcion[], int valorMin, int valorMax) {
		String mensError = "Opción no válida. Vuelve a intentarlo.";
		int userInput = 0;

		do {

			pintarMenuAjuste(titulo, vDescripcion);

			if (userInput == -1) {
				for (int i = 0; i < (ANCHOTOTAL - mensError.length()) / 2; i++) {
					System.out.print(" ");
				}
				System.out.println(mensError);
			}

			userInput = pintarEscaner(valorMin, valorMax);
		} while (userInput == -1);

		return userInput;
	}

	private static float genMenuAjuste(String titulo, String vDescripcion[], float valorMin, float valorMax) {
		String mensError = "Opción no válida. Vuelve a intentarlo.";
		float userInput = 0;

		do {

			pintarMenuAjuste(titulo, vDescripcion);

			if (userInput == -1) {
				for (int i = 0; i < (ANCHOTOTAL - mensError.length()) / 2; i++) {
					System.out.print(" ");
				}
				System.out.println(mensError);
			}

			userInput = pintarEscanerFloat(valorMin, valorMax);
		} while (userInput == -1);

		return userInput;
	}

	private static String genMenuAjuste(String titulo, String vDescripcion[], String tipoDeDatoAIntroducir) {
		String mensError = "Nombre no válido. Vuelve a intentarlo.";
		String userInput = "";

		do {

			pintarMenuAjuste(titulo, vDescripcion);

			if (userInput == ".!#$?") {
				for (int i = 0; i < (ANCHOTOTAL - mensError.length()) / 2; i++) {
					System.out.print(" ");
				}
				System.out.println(mensError);
			}

			userInput = pintarEscaner(tipoDeDatoAIntroducir);
		} while (userInput == ".!#$?");
		return userInput;
	}

	private static void pintarMenuAjuste(String titulo, String vDescripcion[]) {
		int anchoSeparadores = 4;
		int anchoOpciones = calcularAnchoOpciones(vDescripcion);

		pintarTitulo(titulo);

		// PRIMERA LÍNEA (borde)
		for (int i = 0; i < ((ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("┌");

		for (int i = 0; i < anchoOpciones + 3; i++) {
			System.out.print("─");
		}

		System.out.println("┐");

		// SEGUNDA LÍNEA (contenido)
		for (int j = 0; j < vDescripcion.length; j++) {
			for (int i = 0; i < ((ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
				System.out.print(" ");
			}
			System.out.print("│");

			for (int i = 0; i < (anchoOpciones - vDescripcion[j].length() + anchoSeparadores) / 2; i++) {
				System.out.print(" ");
			}

			System.out.print(vDescripcion[j]);

			for (int i = 0; i < (((anchoOpciones - vDescripcion[j].length() + anchoSeparadores - 1) / 2)); i++) {
				System.out.print(" ");
			}

			System.out.println("│");
		}

		// TERCERA LÍNEA (borde)
		for (int i = 0; i < ((ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("└");

		for (int i = 0; i < anchoOpciones + 3; i++) {
			System.out.print("─");
		}

		System.out.println("┘");

		System.out.println();
	}

	private static void pintarTitulo(String titulo) {
		int anchoSeparadores = 4;

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		// PRIMERA LÍNEA (borde)
		for (int i = 0; i < ((ANCHOTOTAL - (titulo.length() + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("╔");

		for (int i = 0; i < titulo.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.println("╗");

		// SEGUNDA LÍNEA (contenido)
		for (int i = 0; i < ((ANCHOTOTAL - (titulo.length() + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("║ " + titulo.toUpperCase() + " ║");
		if ((ANCHOTOTAL - (titulo.length() + anchoSeparadores)) % 2 == 0) {
			for (int i = 0; i < ((ANCHOTOTAL - (titulo.length() + anchoSeparadores)) / 2) - 1; i++) {
				System.out.print(" ");
			}
		} else {
			for (int i = 0; i < ((ANCHOTOTAL - (titulo.length() + anchoSeparadores)) / 2); i++) {
				System.out.print(" ");
			}
		}
		System.out.println();

		// TERCERA LÍNEA (borde)
		for (int i = 0; i < ((ANCHOTOTAL - (titulo.length() + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("╚");

		for (int i = 0; i < titulo.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.println("╝");
	}

	private static void pintarMenu(String titulo, String vOpciones[]) {
		int anchoSeparadores = 4;
		int anchoOpciones = calcularAnchoOpciones(vOpciones);

		pintarTitulo(titulo);

		// PRIMERA LÍNEA (borde)
		for (int i = 0; i < ((ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("┌");

		for (int i = 0; i < anchoOpciones + 3; i++) {
			System.out.print("─");
		}

		System.out.println("┐");

		// SEGUNDA LÍNEA (contenido)
		for (int j = 0; j < vOpciones.length; j++) {
			for (int i = 0; i < ((ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
				System.out.print(" ");
			}
			System.out.print("│ " + (j + 1) + ". " + vOpciones[j].toUpperCase());

			for (int i = 0; i <= anchoOpciones - vOpciones[j].length() - 3; i++) {
				System.out.print(" ");
			}

			System.out.print(" │");
			System.out.println();
		}

		// TERCERA LÍNEA (borde)
		for (int i = 0; i < ((ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("└");

		for (int i = 0; i < anchoOpciones + 3; i++) {
			System.out.print("─");
		}

		System.out.println("┘");

		System.out.println();
	}

	private static void about() {
		genMenuAjuste("Acerca de runJrun",
				new String[] { "runJrun es un juego de carreras por turnos. En cada turno el jugador",
						"activo puede arrancar, acelerar o frenar en función del estado de su",
						"coche. Cada vez que el coche acelera o frena, se genera un número",
						"aleatorio de hasta 50 km/h que se incrementan o reducen sobre la",
						"velocidad actual. La potencia del coche se puede modificar en el menú",
						"CONFIGURACIÓN. Si un coche supera los 200 km/h sufrirá un accidente",
						"y deberá volver a arrancar.", " ",
						"Es posible añadir varios jugadores en la pantalla de creación de la",
						"carrera para activar el modo multijugador.", " ", "¡Conduce con cuidado!", " ",
						"Desarrollado por Tomás Generelo en enero de 2021." },
				1, 1);
	}
}
