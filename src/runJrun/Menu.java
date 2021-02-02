package runJrun;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * La clase Menu gestiona todos los menús. En lugar de confeccionar cada menú de
 * forma individual, se generan de forma dinámica a partir de un título y un
 * vector que puede contener las diferentes opciones o cada línea de un texto
 * descriptivo. De este modo, cada menú puede modificarse de forma
 * extremadamente sencilla añadiendo, cambiando o eliminando posiciones del
 * vector y ajustando el switch que controla su comportamiento.
 * 
 * @author Tomás Generelo
 *
 */
public class Menu {
	static Scanner leer = new Scanner(System.in);
	static Carrera carrera = new Carrera("", 5, 9);
	static Coche coche = new Coche("", true, "-?!#");

	/**
	 * Genera un menú a partir de un título y un vector con sus posibles opciones,
	 * consulta al usuario y devuelve la opción escogida por este.
	 * 
	 * @param titulo    El título que se mostrará como cabecera del menú.
	 * @param vOpciones Un vector con todas las opciones del menú.
	 * @return Devuelve la opción escogida por el usuario.
	 */
	static int genMenu(String titulo, String vOpciones[]) {
		String mensError = "Opción no válida. Vuelve a intentarlo.";
		int userInput = 0;

		do {

			pintarMenu(titulo, vOpciones);

			if (userInput == -1) {
				for (int i = 0; i < (Main.ANCHOTOTAL - mensError.length()) / 2; i++) {
					System.out.print(" ");
				}
				System.out.println(mensError);
			}

			userInput = pintarEscaner(vOpciones);
		} while (userInput == -1);
		return userInput;
	}

	/**
	 * Genera una pantalla para modificar un ajuste de tipo entero, consulta al
	 * usuario y devuelve el valor introducido.
	 * 
	 * @param titulo       El título que tendrá la pantalla de configuración.
	 * @param vDescripcion Un vector que contiene, línea a línea, la descripción del
	 *                     ajuste a modificar.
	 * @param valorMin     El valor mínimo que puede tener el ajuste.
	 * @param valorMax     El valor máximo que puede tener el ajuste.
	 * @return Devuelve el valor establecido por el usuario.
	 */
	static int genMenuAjuste(String titulo, String vDescripcion[], int valorMin, int valorMax) {
		String mensError = "Opción no válida. Vuelve a intentarlo.";
		int userInput = 0;

		do {

			pintarMenuAjuste(titulo, vDescripcion);

			if (userInput == -1) {
				for (int i = 0; i < (Main.ANCHOTOTAL - mensError.length()) / 2; i++) {
					System.out.print(" ");
				}
				System.out.println(mensError);
			}

			userInput = pintarEscaner(valorMin, valorMax);
		} while (userInput == -1);

		return userInput;
	}

	/**
	 * Genera una pantalla para modificar un ajuste de tipo float, consulta al
	 * usuario y devuelve el valor introducido.
	 * 
	 * @param titulo       El título que tendrá la pantalla de configuración.
	 * @param vDescripcion Un vector que contiene, línea a línea, la descripción del
	 *                     ajuste a modificar.
	 * @param valorMin     El valor mínimo que puede tener el ajuste.
	 * @param valorMax     El valor máximo que puede tener el ajuste.
	 * @return Devuelve el valor establecido por el usuario.
	 */
	static float genMenuAjuste(String titulo, String vDescripcion[], float valorMin, float valorMax) {
		String mensError = "Opción no válida. Vuelve a intentarlo.";
		float userInput = 0;

		do {

			pintarMenuAjuste(titulo, vDescripcion);

			if (userInput == -1) {
				for (int i = 0; i < (Main.ANCHOTOTAL - mensError.length()) / 2; i++) {
					System.out.print(" ");
				}
				System.out.println(mensError);
			}

			userInput = pintarEscanerFloat(valorMin, valorMax);
		} while (userInput == -1);

		return userInput;
	}

	/**
	 * Genera una pantalla para modificar un ajuste de tipo String, consulta al
	 * usuario y devuelve el valor introducido.
	 * 
	 * @param titulo                El título que tendrá la pantalla de
	 *                              configuración.
	 * @param vDescripcion          Un vector que contiene, línea a línea, la
	 *                              descripción del ajuste a modificar.
	 * @param tipoDeDatoAIntroducir El tipo de dato para el que se pide el valor.
	 *                              Este parámetro se concatenará detrás de
	 *                              "Introduce ", por lo que debería especificar lo
	 *                              que se espera del usuario.
	 * @return Devuelve el valor establecido por el usuario.
	 */
	static String genMenuAjuste(String titulo, String vDescripcion[], String tipoDeDatoAIntroducir) {
		String mensError = "Nombre no válido. Vuelve a intentarlo.";
		String userInput = "";

		do {

			pintarMenuAjuste(titulo, vDescripcion);

			if (userInput == ".!#$?") {
				for (int i = 0; i < (Main.ANCHOTOTAL - mensError.length()) / 2; i++) {
					System.out.print(" ");
				}
				System.out.println(mensError);
			}

			userInput = pintarEscaner(tipoDeDatoAIntroducir);
		} while (userInput == ".!#$?");
		return userInput;
	}

	/**
	 * Genera la representación gráfica del título que encabezará un menú, rodeado
	 * por un recuadro con doble línea.
	 * 
	 * @param titulo El título del menú.
	 */
	private static void pintarTitulo(String titulo) {
		int anchoSeparadores = 4;

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		// PRIMERA LÍNEA (borde)
		for (int i = 0; i < ((Main.ANCHOTOTAL - (titulo.length() + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("╔");

		for (int i = 0; i < titulo.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.println("╗");

		// SEGUNDA LÍNEA (contenido)
		for (int i = 0; i < ((Main.ANCHOTOTAL - (titulo.length() + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("║ " + titulo.toUpperCase() + " ║");
		if ((Main.ANCHOTOTAL - (titulo.length() + anchoSeparadores)) % 2 == 0) {
			for (int i = 0; i < ((Main.ANCHOTOTAL - (titulo.length() + anchoSeparadores)) / 2) - 1; i++) {
				System.out.print(" ");
			}
		} else {
			for (int i = 0; i < ((Main.ANCHOTOTAL - (titulo.length() + anchoSeparadores)) / 2); i++) {
				System.out.print(" ");
			}
		}
		System.out.println();

		// TERCERA LÍNEA (borde)
		for (int i = 0; i < ((Main.ANCHOTOTAL - (titulo.length() + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("╚");

		for (int i = 0; i < titulo.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.println("╝");
	}

	/**
	 * Genera la representación gráfica de las opciones de un menú, rodeadas por un
	 * recuadro.
	 * 
	 * @param titulo    El título del menú.
	 * @param vOpciones Un vector que contiene las opciones del menú, que se
	 *                  numerarán automáticamente.
	 */
	private static void pintarMenu(String titulo, String vOpciones[]) {
		int anchoSeparadores = 4;
		int anchoOpciones = calcularAnchoOpciones(vOpciones);

		pintarTitulo(titulo);

		// PRIMERA LÍNEA (borde)
		for (int i = 0; i < ((Main.ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("┌");

		for (int i = 0; i < anchoOpciones + 3; i++) {
			System.out.print("─");
		}

		System.out.println("┐");

		// SEGUNDA LÍNEA (contenido)
		for (int j = 0; j < vOpciones.length; j++) {
			for (int i = 0; i < ((Main.ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
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
		for (int i = 0; i < ((Main.ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("└");

		for (int i = 0; i < anchoOpciones + 3; i++) {
			System.out.print("─");
		}

		System.out.println("┘");

		System.out.println();
	}

	/**
	 * Genera la representación gráfica de una pantalla de ajuste a partir de un
	 * título y un vector con la descripción, línea a línea, del ajuste a modificar.
	 * 
	 * @param titulo       El título que tendrá la pantalla de configutración.
	 * @param vDescripcion Un vector con la descripción, línea a línea, del ajuste a
	 *                     modificar.
	 */
	private static void pintarMenuAjuste(String titulo, String vDescripcion[]) {
		int anchoSeparadores = 4;
		int anchoOpciones = calcularAnchoOpciones(vDescripcion);

		pintarTitulo(titulo);

		// PRIMERA LÍNEA (borde)
		for (int i = 0; i < ((Main.ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("┌");

		for (int i = 0; i < anchoOpciones + 3; i++) {
			System.out.print("─");
		}

		System.out.println("┐");

		// SEGUNDA LÍNEA (contenido)
		for (int j = 0; j < vDescripcion.length; j++) {
			for (int i = 0; i < ((Main.ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
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
		for (int i = 0; i < ((Main.ANCHOTOTAL - (anchoOpciones + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("└");

		for (int i = 0; i < anchoOpciones + 3; i++) {
			System.out.print("─");
		}

		System.out.println("┘");

		System.out.println();
	}

	/**
	 * Genera un escáner para seleccionar una opción del menú que lo invoca.
	 * 
	 * @param vOpciones El vector que contiene las opciones del menú.
	 * @return La opción escogida por el usuario.
	 */
	private static int pintarEscaner(String vOpciones[]) {
		int userInput = 0;
		String texto = "Introduce una opción: > ";
		leer = new Scanner(System.in);

		for (int i = 0; i < (Main.ANCHOTOTAL - texto.length()) / 2; i++) {
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

	/**
	 * Genera un escáner para establecer una valor de tipo entero en la pantalla de
	 * ajuste que lo invoca.
	 * 
	 * @param valorMin El valor mínimo que puede tener el ajuste.
	 * @param valorMax El valor máximo que puede tener el ajuste.
	 * @return Un valor de tipo entero para establecer como el nuevo ajuste.
	 */
	private static int pintarEscaner(int valorMin, int valorMax) {
		int userInput = 0;
		String texto = "";
		leer = new Scanner(System.in);

		if (valorMin == valorMax) {
			texto = "[ 1. Volver ] > ";
		} else {
			texto = "Introduce un valor: > ";
		}

		for (int i = 0; i < (Main.ANCHOTOTAL - texto.length()) / 2; i++) {
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

	/**
	 * Genera un escáner para establecer una valor de tipo float en la pantalla de
	 * ajuste que lo invoca.
	 * 
	 * @param valorMin El valor mínimo que puede tener el ajuste.
	 * @param valorMax El valor máximo que puede tener el ajuste.
	 * @return Un valor de tipo float para establecer como el nuevo ajuste.
	 */
	private static float pintarEscanerFloat(float valorMin, float valorMax) {
		float inputFloat = 0f;
		String texto = "Introduce un valor: > ";
		leer = new Scanner(System.in);

		for (int i = 0; i < (Main.ANCHOTOTAL - texto.length()) / 2; i++) {
			System.out.print(" ");
		}
		System.out.print(texto);

		try {
			leer = new Scanner(System.in);
			inputFloat = Float.valueOf(leer.next());
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

	/**
	 * Genera un escáner para establecer una valor de tipo String en la pantalla de
	 * ajuste que lo invoca.
	 * 
	 * @param nombreNombre El tipo de dato para el que se pide el valor. Este
	 *                     parámetro se concatenará detrás de "Introduce ", por lo
	 *                     que debería especificar lo que se espera del usuario.
	 * @return Un valor de tipo String para establecer como el nuevo ajuste.
	 */
	private static String pintarEscaner(String nombreNombre) {
		String userInput = "";
		String texto = "Introduce " + nombreNombre + ": > ";
		leer = new Scanner(System.in);

		for (int i = 0; i < (Main.ANCHOTOTAL - texto.length()) / 2; i++) {
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

	/**
	 * A partir de un vector que contiene las opciones del menú que lo invoca,
	 * devuelve la longitud de la más larga.
	 * 
	 * @param vOpciones Un vector que contiene las opciones de un menú.
	 * @return Un valor de tipo entero con la longitud de la opción más larga.
	 */
	private static int calcularAnchoOpciones(String vOpciones[]) {
		int anchoMax = 0;

		for (String opcion : vOpciones) {
			if (opcion.length() > anchoMax)
				anchoMax = opcion.length();
		}
		return anchoMax + 3;
	}

	/**
	 * Genera una pantalla de información sobre el programa.
	 */
	static void about() {
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
