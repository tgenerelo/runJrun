package runJrun;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Una carrera está compuesta por un número determinado de vehículos
 * (controlados o no por un usuario), una distancia que recorrer y un número de
 * turnos. Un objeto de tipo carrera recibe una representación gráfica en cada
 * turno.
 * 
 * @author Tomás Generelo
 *
 */
public class Carrera {
	private String nombre;
	private String patrocinador;
	private float longitud;
	private int numCompetidores;
	private Coche vCoches[];
	private Coche vOrdenLlegada[];
	private Coche vPosiciones[];
	private int numTurno;
	private int numJugadores;

	/**
	 * Genera un nuevo objeto de tipo Carrera.
	 * 
	 * @param nombre          El nombre de la competición (por ejemplo, "Gran
	 *                        Premio"). Puede ser modificado más adelante. Si la
	 *                        carrera da comienzo sin nombre, se genera uno
	 *                        automáticamente.
	 * @param longitud        La longitud de la carrera, en kilómetros.
	 * @param numCompetidores El número total de coches implicados en la carrera,
	 *                        incluyendo los controlados por el usuario y los
	 *                        controlados por el programa.
	 */
	public Carrera(String nombre, float longitud, int numCompetidores) {
		this.nombre = nombre;
		this.patrocinador = generarPatrocinador();
		this.longitud = longitud;
		this.numCompetidores = numCompetidores;
		this.vCoches = new Coche[numCompetidores];
		this.vOrdenLlegada = new Coche[numCompetidores];
		this.vPosiciones = new Coche[numCompetidores];
		this.numTurno = 0;
		this.numJugadores = 0;
	}

	/**
	 * Recibe un coche y lo incorpora a la carrera.
	 * 
	 * @param coche El coche que se añadirá a la carrera.
	 */
	public void agregarCoche(Coche coche) {
		Random r = new Random();
		int random = 0;
		boolean asignado = false;

		if (coche.isJugador()) {
			if (coche.getDorsal().equals("-?!#")) {
				coche.setDorsal(String.valueOf(r.nextInt(100)));
			}
			numJugadores++;
			coche.setNumJugador(numJugadores);
		}

		comprobarDorsal(coche);

		/*
		 * COLOCA LOS COCHES EN UN ORDEN AL AZAR EN vCoches Esto debería poderse
		 * eliminar porque esta operación ya se realiza como parte de prepararCarrera().
		 * Sin embargo, sin esta parte la carrera no se lanza. Revisar cuando sea
		 * posible.
		 */
		do {
			random = r.nextInt(vCoches.length);
			if (vCoches[random] == null) {
				vCoches[random] = coche;
				asignado = true;
			} else {
				asignado = false;
			}

		} while (asignado == false);
	}

	/**
	 * Comprueba si el dorsal del coche ya se encuentra dado de alta en la carrera.
	 * Si el código de dorsal ya existe, se genera uno nuevo al azar.
	 * 
	 * @param coche El coche del que se va a comprobar el dorsal.
	 */
	private void comprobarDorsal(Coche coche) {

		for (int j = 0; j < vCoches.length; j++) {
			if (vCoches[j] != null && coche.getDorsal().equalsIgnoreCase(vCoches[j].getDorsal())) {
				coche.setDorsal(generarDorsal());
				j = 0;
			}
		}
	}

	/**
	 * Genera un código de dorsal aleatorio entre 0 y el número de competidores en
	 * la carrera.
	 * 
	 * @return El número generado aleatoriamente en forma de String.
	 */
	public String generarDorsal() {
		Random r = new Random();

		return String.valueOf(r.nextInt(numCompetidores) + 2);
	}

	/**
	 * Genera un nombre aleatorio para la carrera.
	 */
	private void generarNombreGP() {
		Random r = new Random();
		String vEdicion[] = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };
		String vGP[] = { "Gran Premio", "Grand Prix", "Premio", "Carrera", "Campeonato" };
		String vLugar[] = { "Java", "Alcañiz", "Zaragoza", "Aragón", "España", "Motorland" };

		nombre = vEdicion[r.nextInt(vEdicion.length)] + " " + vGP[r.nextInt(vGP.length)] + " de "
				+ vLugar[r.nextInt(vLugar.length)];
	}

	/**
	 * Escoge un nombre de patrocinador de entre varios disponibles en
	 * vPatrocinadores.
	 * 
	 * @return
	 */
	private String generarPatrocinador() {
		Random r = new Random();
		String vPatrocinadores[] = { "Beedle Corporation", "Pearl Corp", "Cruise Technologies", "Valkyrecords",
				"Pixelimited", "Raptolutions", "Aprico", "Spherepaw", "Cannonfly", "Motiongate", "Cave Solutions",
				"Forest Security", "Oak Microsystems", "Bridgelectrics", "Cavernetworks", "Fairiprises", "Spideradio",
				"Lifelife", "Marblepaw", "Forestex", "Marble Industries", "Web Foods", "Orc Co.", "Apexi",
				"Fairiprises", "Parableutions", "Smilectronics", "Typhoonwater", "Hooklight", "Greenworld",
				"Marble Industries", "Web Foods", "Orc Co.", "Apexi", "Fairiprises", "Parableutions", "Smilectronics",
				"Typhoonwater", "Hooklight", "Greenworld", "Globe Softwares", "Fairy Lighting", "Motion Microsystems",
				"Crypticorps", "Marsoftwares", "Silverecords", "Ansoft", "Sphereshine", "Oystertube", "Apexwheels",
				"Typhoonwater", "Hooklight", "Greenworld" };

		return vPatrocinadores[r.nextInt(vPatrocinadores.length)];
	}

	/**
	 * Comprueba los nombres de todos los pilotos en vCoches y devuelve la longitud
	 * del más largo.
	 * 
	 * @return La longitud del nombre de piloto más largo en vCoches.
	 */
	private int anchoNombres(Coche vCoches[]) {
		int anchoNombres = 0;

		for (Coche coche : vCoches) {
			if (coche != null) {
				if ((coche.getPiloto().length()) > anchoNombres)
					anchoNombres = coche.getPiloto().length();
			}
		}

		return anchoNombres;
	}

	/**
	 * Comprueba los nombres de todos los pilotos en vCoches (incluyendo su número
	 * de dorsal) y devuelve la longitud del más largo.
	 * 
	 * @return La longitud del nombre de piloto más largo en vCoches.
	 */
	private int anchoNombresYDorsales(Coche vCoches[]) {
		int anchoNombres = 0;

		for (Coche coche : vCoches) {
			if (coche != null) {
				if ((coche.getPiloto().length() + coche.getDorsal().length()) > anchoNombres)
					anchoNombres = coche.getPiloto().length() + coche.getDorsal().length();
			}
		}

		return anchoNombres;
	}

	/**
	 * Controla cada turno de la carrera. Los jugadores humanos realizan sus
	 * acciones en orden y luego se calcula el progreso de los coches controlados
	 * por el programa. Al final del turno se calcula la posición de cada coche en
	 * la carrera.
	 */
	public void turnoCarrera() {

		numTurno++;

		for (int i = 1; i <= numJugadores; i++) {
			for (int j = 0; j < vCoches.length; j++) {
				if (vCoches[j] != null && vCoches[j].getNumJugador() == i) {
					turnoJugador(vCoches[j]);
					vCoches[j]
							.setVelocidadMedia((vCoches[j].getVelocidadMedia() + vCoches[j].getVelocidad()) / numTurno);
				}
			}
		}

		for (Coche coche : vCoches) {
			if (coche != null) {
				if (!coche.isJugador()) {
					turnoNpcs(coche);
					coche.setVelocidadMedia((coche.getVelocidadMedia() + coche.getVelocidad()) / numTurno);
				}
			}
		}

		for (Coche coche : vCoches) {
			if (coche.isEnMarcha() && coche.getKms() >= longitud) {
				coche.setTerminado(true);
				coche.setMarcha(false);
				calcularTiempo(coche);
				coche.setTurnoLlegada(numTurno);
				for (int i = 0; i < vOrdenLlegada.length; i++) {
					if (vOrdenLlegada[i] == null) {
						vOrdenLlegada[i] = coche;
						break;
					} else {
						if (vOrdenLlegada[i].equals(coche))
							break;
					}
				}
			}
		}
	}

	/**
	 * Comprueba que todos los ajustes de la carrera son válidos y controla el
	 * progreso de la carrera hasta que esta termina.
	 */
	public void comenzar() {
		prepararCarrera();
		do {
			turnoCarrera();
		} while (!isTerminada());

		imprimirClasificacion();
	}

	/**
	 * Realiza los preparativos para evitar errores cuando se inicie la carrera.
	 * Comprueba que el número de competidores sea compatible con el número de
	 * jugadores humanos y coloca a todos los participantes en un orden al azar.
	 */
	private void prepararCarrera() {

		Random r = new Random();
		int random = 0;
		boolean asignado = false;

		Coche vParticipantes[];

		if (numCompetidores < numJugadores)
			numCompetidores = numJugadores;

		vParticipantes = new Coche[numCompetidores];
		vPosiciones = new Coche[numCompetidores];
		vOrdenLlegada = new Coche[numCompetidores];

		// COLOCA LOS COCHES EN UN ORDEN AL AZAR EN vCoches
		for (Coche coche : vCoches) {
			if (coche != null) {
				do {
					if (coche.getPiloto().equalsIgnoreCase("")) {
						coche.setPiloto("JUGADOR " + coche.getNumJugador());
					}
					random = r.nextInt(vParticipantes.length);
					if (vParticipantes[random] == null) {
						vParticipantes[random] = coche;
						asignado = true;
					} else {
						asignado = false;
					}
				} while (asignado == false);
			}
		}

		vCoches = vParticipantes;

		for (int i = 0; i < vCoches.length; i++) {
			if (vCoches[i] == null) {
				agregarCoche(new Coche());
				i--;
			}
		}

		if (nombre.equals(""))
			generarNombreGP();
		
		if (patrocinador.equals(""))
			patrocinador = generarPatrocinador();

	}

	/**
	 * Controla el turno del jugador humano activo.
	 * 
	 * @param jugador El jugador activo. Su atributo "jugador" debería ser true.
	 */
	private void turnoJugador(Coche jugador) {
		Scanner leer = new Scanner(System.in);
		int userInput = 0;
		boolean accionValida = true;
		String opc1 = "1 Arrancar";
		String opc2 = "2 Acelerar";
		String opc3 = "3 Frenar";
		
		posicionCoches();

		if (jugador.isEnMarcha() || vOrdenLlegada[0] == null) {
			do {
				pintarGraficos();
				pintarSalpicadero(jugador);
				System.out.println();
				if (jugador.isEnMarcha()) {
					for (int i = 0; i < (Main.ANCHOTOTAL - (opc2.length() + opc3.length() + 11)) / 2; i++) {
						System.out.print(" ");
					}

					System.out.print("[ " + opc2 + " ] [ " + opc3 + " ]" + " > ");
				} else {
					for (int i = 0; i < (Main.ANCHOTOTAL - (opc1.length() + 8)) / 2; i++) {
						System.out.print(" ");
					}

					System.out.print("[ " + opc1 + " ]" + " > ");
				}
				do {
					try {
						userInput = leer.nextInt();
					} catch (InputMismatchException e) {
						accionValida = false;
						leer = new Scanner(System.in);
					}
					accionValida = true;
				} while (accionValida == false);

				System.out.println();

				if (userInput == 1 && jugador.isEnMarcha()) {
					accionValida = false;
				} else {
					accionValida = true;
					if ((userInput < 1 || userInput > 3)
							|| ((userInput == 2 || userInput == 3) && !jugador.isEnMarcha())) {
						accionValida = false;
					} else {
						accionValida = true;
					}
				}

			} while (accionValida == false);

			switch (userInput) {
			case 1:
				jugador.arrancar();
				break;
			case 2:
				jugador.acelerar();
				break;
			case 3:
				jugador.frenar();
				break;
			}

			if (numJugadores > 1 && numTurno > 1) {
				pintarGraficos();
				pintarSalpicadero(jugador);
				System.out.println();
				System.out.println();
				try {
					Thread.sleep(1600);
				} catch (InterruptedException e) {

				}
			}
		}
	}

	/**
	 * Controla el turno del coche activo controlado por el programa.
	 * 
	 * @param coche El coche activo. Su atributo "jugador" debería ser false.
	 */
	private void turnoNpcs(Coche coche) {
		Random r = new Random();

		if (!coche.isTerminado()) {
			if (coche.isEnMarcha()) {
				if (coche.getVelocidad() <= 150) {
					coche.acelerar();
				} else {
					if (r.nextInt() % 2 == 0) {
						coche.acelerar();
					} else {
						coche.frenar();
					}
				}

			} else {
				if (vOrdenLlegada[0] == null)
					coche.arrancar();
			}
		}
		
		posicionCoches();

	}

	/**
	 * Cambia la posición del coche en la carrera, en función de la distancia que ha
	 * recorrido y comparando con todos los demás coches que compiten.
	 */
	public void posicionCoches() {

		Coche vAux[] = new Coche[vCoches.length];
		float exch = 0f;
		int posicion = 1;
		vAux = vCoches.clone();
		int primeraPosicionvOrdenLlegada = 0;

		for (int i = 0; i < vOrdenLlegada.length; i++) {
			if (vOrdenLlegada[i] == null) {
				posicion = i + 1;
				break;
			}
		}

		for (int i = 0; i < vOrdenLlegada.length; i++) {
			if (vOrdenLlegada[i] != null) {
				vOrdenLlegada[i].setPosicionFinal(i + 1);
			}
		}

		for (int i = 0; i < vOrdenLlegada.length; i++) {
			Coche aux = new Coche();
			for (int j = 1; j < vOrdenLlegada.length; j++) {
				if (vOrdenLlegada[j] != null && vOrdenLlegada[j].getTiempo() < vOrdenLlegada[j - 1].getTiempo()) {
					aux = vOrdenLlegada[j - 1];
					vOrdenLlegada[j - 1] = vOrdenLlegada[j];
					vOrdenLlegada[j] = aux;
					vOrdenLlegada[i].setPosicionFinal(i + 1);
				}
			}
		}

		for (int m = primeraPosicionvOrdenLlegada; m < vPosiciones.length; m++) { // UNA VEZ POR CADA COCHE EN vAux
			exch = 0;
			for (int i = 0; i < vAux.length; i++) { // BUSCA LA MAYOR DISTANCIA RECORRIDA
				if (vAux[i] != null && (vAux[i].getKms() > exch)) {
					exch = vAux[i].getKms(); // ALMACENA LA MAYOR DISTANCIA RECORRIDA
				}
			}
			for (int j = 0; j < vAux.length; j++) { // ENCUENTRA EL COCHE CON LA MAYOR DISTANCIA RECORRIDA QUE NO ESTÉ
													// ACCIDENTADO
				if (vAux[j] != null && vAux[j].getKms() == exch) {
					vPosiciones[m] = vAux[j]; // ASIGNA EL COCHE EN EL PRIMER HUECO LIBRE DE vPosiciones
					vAux[j] = null; // ELIMINA EL COCHE DE vAux PARA QUE NO LO VUELVA A ENCONTRAR
					if (!vPosiciones[m].isTerminado() && !vPosiciones[m].isAccidentado()) {
						vPosiciones[m].setPosicion(posicion);
						posicion++;
					} else {
						vPosiciones[m].setPosicion(0);
					}

					break;
				}
			}

			for (int i = 0; i < vPosiciones.length; i++) {
				for (int j = 0; j < vCoches.length; j++) {
					if ((vPosiciones[i] != null && vCoches[j] != null)
							&& vPosiciones[i].getDorsal().equals(vCoches[j].getDorsal())) {
						vCoches[j].setPosicion(vPosiciones[i].getPosicion());
						break;

					}
				}
			}
		}
	}

	/**
	 * Imprime la representación gráfica de la carrera.
	 */
	private void pintarGraficos() {

		int distanciaRecorrida = 0;
		int distanciaRestante = 0;
		float unidadAvance = longitud / Main.anchoPista;
		String cocheNpc = "[ D)";
		String cochePc = "[»D)";
		String cocheAcc = "[XX)";
		String cochecito = "";
		String rankingString;

		posicionCoches();

		Main.anchoPista = Main.ANCHOTOTAL - (anchoNombresYDorsales(vCoches) + 22);

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		pintarNombreCarrera();
		pintarLateralPista();

		for (Coche coche : vCoches) {
			if (coche != null) {
				distanciaRecorrida = (int) (coche.getKms() / unidadAvance);
				distanciaRestante = (int) (Main.anchoPista - (coche.getKms() / unidadAvance));
				if (Main.anchoPista > (distanciaRecorrida + distanciaRestante))
					distanciaRestante++;

				if (coche != null)
					if (coche.isAccidentado()) {
						cochecito = cocheAcc;
					} else {
						if (coche.isJugador()) {
							cochecito = cochePc;
						} else {
							cochecito = cocheNpc;
						}
					}

				System.out.print("║");
				if (coche.getKms() >= longitud) {
					distanciaRecorrida = Main.anchoPista;
					distanciaRestante = 0;
				}

				// Controla la representación de la distancia recorrida, ajustando manualmente
				// los casos especiales para evitar que se descuadre la imagen.
				switch (distanciaRecorrida) {
				case 0:
					System.out.print(" ");
					break;
				case 1:
					System.out.print(" =");
					break;
				default:
					System.out.print(" ");

					for (int i = 0; i < distanciaRecorrida; i++) {
						System.out.print("=");
					}
					break;
				}

				System.out.print(cochecito);

				for (int i = 0; i < distanciaRestante; i++) {
					System.out.print(" ");
				}

				if ((!coche.isTerminado() && coche.isEnMarcha()) && numTurno > 2) {
					rankingString = String.valueOf(coche.getPosicion() + "º");
					do {
						if (rankingString.length() < (String.valueOf(numCompetidores).length() + 1)) {
							rankingString = " " + rankingString;
						} else {
							if (rankingString.length() == (String.valueOf(numCompetidores).length() + 1))
								break;
						}
					} while (true);

				} else {
					if (coche.isTerminado()) {
						rankingString = coche.getPosicionFinal() + "º";
						while ((rankingString.length() - 1) < String.valueOf(numCompetidores).length()) {
							rankingString = " " + rankingString;
						}

					} else {
						if (coche.isAccidentado()) {
							rankingString = "⚠ ";
						} else {
							rankingString = "--";
						}

					}
				}

				if (rankingString.length() < 3)
					rankingString += " ";

				System.out.println(
						"║ ▀▄▀▄ " + rankingString + " [" + coche.getDorsal() + " - " + coche.getPiloto() + "]");
			}
		}

		pintarLateralPista();
	}

	/**
	 * Imprime el lateral de la pista en función del ancho total establecido para la
	 * pantalla.
	 */
	private void pintarLateralPista() {
		for (int i = 0; i < Main.ANCHOTOTAL; i++) {
			System.out.print("■");
		}
		System.out.println();
	}

	/**
	 * Imprime las gradas y el cartel con el nombre y patrocinador de la carrera.
	 */
	private void pintarNombreCarrera() {
		String patrocinadoPor = "Patrocinado por:";

		// PRIMERA LÍNEA (borde)
		for (int i = 0; i < ((Main.ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8))
				/ 2); i++) {
			System.out.print(" ");
		}
		System.out.print("┌");
		for (int i = 0; i < nombre.length() + 2; i++) {
			System.out.print("─");
		}

		System.out.print("┬");

		for (int i = 0; i < (patrocinadoPor.length() + patrocinador.length() + 3); i++) {
			System.out.print("─");
		}

		System.out.println("┐");

		// SEGUNDA LÍNEA (contenido)
		for (int i = 0; i < ((Main.ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8))
				/ 2) - 1; i++) {
			System.out.print("░");
		}
		System.out.print(" │ " + nombre.toUpperCase() + " │ " + patrocinadoPor + " " + patrocinador + " │ ");
		if ((Main.ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8)) % 2 == 0) {
			for (int i = 0; i < ((Main.ANCHOTOTAL
					- (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8)) / 2) - 1; i++) {
				System.out.print("░");
			}
		} else {
			for (int i = 0; i < ((Main.ANCHOTOTAL
					- (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8)) / 2); i++) {
				System.out.print("░");
			}
		}
		System.out.println();

		// TERCERA LÍNEA (borde)
		for (int i = 0; i < ((Main.ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8))
				/ 2) - 1; i++) {
			System.out.print("░");
		}

		System.out.print(" └");
		for (int i = 0; i < nombre.length() + 2; i++) {
			System.out.print("─");
		}

		System.out.print("┴");

		for (int i = 0; i < (patrocinadoPor.length() + patrocinador.length() + 3); i++) {
			System.out.print("─");
		}

		System.out.print("┘ ");

		if ((Main.ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8)) % 2 == 0) {
			for (int i = 0; i < ((Main.ANCHOTOTAL
					- (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8)) / 2) - 1; i++) {
				System.out.print("░");
			}
		} else {
			for (int i = 0; i < ((Main.ANCHOTOTAL
					- (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8)) / 2); i++) {
				System.out.print("░");
			}
		}

		System.out.println();
	}

	/**
	 * Muestra el salpicadero del coche activo. El salpicadero muestra la velocidad
	 * actual, la distancia recorrida, la posición en la carrera y el estado del
	 * coche. Si hay varios jugadores humanos, también se muestra el número de
	 * jugador y su nombre de piloto.
	 * 
	 * @param coche
	 */
	private void pintarSalpicadero(Coche coche) {
		String velocidadString = String.valueOf(coche.getVelocidad()) + " km/h";
		String kmsString = String.format("%.1f", coche.getKms()) + " km";
		String posicionString;
		String estado = "";
		String numJugadorString = "";
		int anchoSeparadores = 0;

		if (numJugadores > 1) {
			anchoSeparadores = 18;
		} else {
			anchoSeparadores = 13;
		}

		// CONTROLA QUE LA POSICIÓN SE MUESTRE ÚNICAMENTE SI ES VÁLIDA
		if (numTurno > 2 && coche.getPosicion() != 0) {
			posicionString = String.valueOf(coche.getPosicion()) + "º";
		} else {
			posicionString = "--";
		}

		if (numJugadores > 1)
			if (coche.getPiloto().equalsIgnoreCase(("JUGADOR " + coche.getNumJugador()))) {
				numJugadorString = "JUGADOR " + coche.getNumJugador();
			} else {
				numJugadorString = "JUGADOR " + coche.getNumJugador() + ": " + coche.getPiloto();
			}

		if (coche.isAccidentado()) {
			estado = "ACCIDENTADO";
			coche.setPosicion(0);
		} else {
			if (!coche.isEnMarcha()) {
				estado = "NO ARRANCADO";
				coche.setPosicion(0);
			} else {
				estado = "EN CARRERA";
			}
		}

		// SALPICADERO (BORDE SUPERIOR)
		for (int i = 0; i < ((Main.ANCHOTOTAL - ((velocidadString.length() + kmsString.length()
				+ posicionString.length() + estado.length() + numJugadorString.length()) + anchoSeparadores))
				/ 2); i++) {
			System.out.print(" ");
		}

		if (numJugadores > 1) {
			System.out.print("╔");
			for (int i = 0; i < numJugadorString.length() + 2; i++) {
				System.out.print("═");
			}
			System.out.print("╗ ");
		}

		System.out.print("╔");

		for (int i = 0; i < velocidadString.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.print("╦");

		for (int i = 0; i < kmsString.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.print("╦");

		for (int i = 0; i < posicionString.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.print("╦");

		for (int i = 0; i < estado.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.println("╗");

		// SALPICADERO (CONTENIDO)
		for (int i = 0; i < ((Main.ANCHOTOTAL - ((velocidadString.length() + kmsString.length()
				+ posicionString.length() + estado.length() + numJugadorString.length()) + anchoSeparadores))
				/ 2); i++) {
			System.out.print(" ");
		}

		if (numJugadores > 1) {
			System.out.print("║ ");
			System.out.print(numJugadorString);
			System.out.print(" ║ ");

		}

		System.out.println("║ " + velocidadString + " ║ " + kmsString + " ║ " + posicionString + " ║ " + estado + " ║");

		// SALPICADERO (BORDE INFERIOR)
		for (int i = 0; i < ((Main.ANCHOTOTAL - ((velocidadString.length() + kmsString.length()
				+ posicionString.length() + estado.length() + numJugadorString.length()) + anchoSeparadores))
				/ 2); i++) {
			System.out.print(" ");
		}

		if (numJugadores > 1) {
			System.out.print("╚");
			for (int i = 0; i < numJugadorString.length() + 2; i++) {
				System.out.print("═");
			}
			System.out.print("╝ ");
		}

		System.out.print("╚");

		for (int i = 0; i < velocidadString.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.print("╩");

		for (int i = 0; i < kmsString.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.print("╩");

		for (int i = 0; i < posicionString.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.print("╩");

		for (int i = 0; i < estado.length() + 2; i++) {
			System.out.print("═");
		}

		System.out.println("╝");

	}

	/**
	 * Calcula el tiempo que ha tardado el coche en completar la carrera y lo
	 * almacena en sus atributos. Si el coche no ha terminado la carrera, el cálculo
	 * no es real pero puede utilizarse igualmente.
	 * 
	 * @param coche El coche del que se quiere calcular el tiempo.
	 */
	private void calcularTiempo(Coche coche) {

		coche.setTiempo((numTurno * 10) - (coche.getKms() - longitud) * 10); // El tiempo que ha logrado el coche en
																				// segundos.
	}

	/**
	 * Reordena vOrdenLlegada en función del tiempo realizado. Esto evita que
	 * algunos coches que llegan en un turno posterior se coloquen antes que otros
	 * por haber recorrido más metros al cruzar la meta.
	 */
	private void reordenarLlegada() {
		Coche cocheAux = null;

		for (int m = 0; m < vOrdenLlegada.length; m++) {
			if (vOrdenLlegada[m] != null) {
				vOrdenLlegada[m].setPosicionFinal(m + 1);
			}
			for (int i = 1; i < vOrdenLlegada.length; i++) {
				if (vOrdenLlegada[i] != null
						&& vOrdenLlegada[i].getTurnoLlegada() == vOrdenLlegada[i - 1].getTurnoLlegada()) {
					if (vOrdenLlegada[i].getTiempo() < vOrdenLlegada[i - 1].getTiempo()) {
						cocheAux = vOrdenLlegada[i];
						vOrdenLlegada[i] = vOrdenLlegada[i - 1];
						vOrdenLlegada[i - 1] = cocheAux;
					}
				}
			}
		}

		for (int i = 0; i < vOrdenLlegada.length; i++) {
			if (vOrdenLlegada[i] != null) {
				vOrdenLlegada[i].setTurnoLlegada(i + 1);
			}
		}
	}

	/**
	 * Imprime la clasificación de la carrera, precedida de la representación
	 * gráfica del último turno. Esta se mantiene durante cinco segundos en
	 * pantalla. Únicamente se muestran los coches que han conseguido llegar a la
	 * meta.
	 */
	private void imprimirClasificacion() {
		DecimalFormat df = new DecimalFormat("##.###");
		df.setRoundingMode(RoundingMode.HALF_DOWN);

		String titulo = "CLASIFICACIÓN FINAL";
		int anchoNombres = anchoNombres(vOrdenLlegada);
		int anchoTiempo = 19;
		int anchoRanking = 0;

		for (Coche coche : vOrdenLlegada) {
			if (coche != null && String.valueOf(coche.getPosicion()).length() + 1 > anchoRanking) {
				anchoRanking = (String.valueOf(coche.getPosicion()).length() + 1);
			}
		}

		pintarGraficos();
		System.out.println("\n\n\n\n");

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
		}

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

		// CABECERA (BORDE SUPERIOR)
		for (int i = 0; i < ((Main.ANCHOTOTAL - (anchoRanking + anchoNombres + anchoTiempo + 10)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("┌");

		for (int i = 0; i < (anchoRanking + anchoNombres + anchoTiempo + 8); i++) {
			System.out.print("─");
		}

		System.out.println("┐");

		// CABECERA (TÍTULO)
		for (int i = 0; i < ((Main.ANCHOTOTAL - (anchoRanking + anchoNombres + anchoTiempo + 10)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("│");
		for (int i = 0; i < (((anchoRanking + anchoNombres + anchoTiempo + 8) - titulo.length()) / 2); i++) {
			System.out.print(" ");
		}

		System.out.print(titulo);

		for (int i = 0; i < (((anchoRanking + anchoNombres + anchoTiempo + 9) - titulo.length()) / 2); i++) {
			System.out.print(" ");
		}

		System.out.println("│");

		// SEPARADOR TABLA
		for (int i = 0; i < ((Main.ANCHOTOTAL - (anchoRanking + anchoNombres + anchoTiempo + 10)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("├");
		for (int i = 0; i < anchoRanking + 2; i++) {
			System.out.print("─");
		}

		System.out.print("┬");

		for (int i = 0; i < (anchoNombres + 2); i++) {
			System.out.print("─");
		}

		System.out.print("┬");

		for (int i = 0; i < (anchoTiempo + 2); i++) {
			System.out.print("─");
		}

		System.out.println("┤");

		// RANKING COMPETIDORES
		for (Coche coche : vOrdenLlegada) {
			if (coche != null) {
				String tiempoString = "";
				int horasInt = (int) ((coche.getTiempo() / 60) / 60);
				int minutosInt = ((int) ((coche.getTiempo() / 60))) - (horasInt * 60);
				float segundosFloat = (coche.getTiempo() % 60);

				String horas = String.valueOf(horasInt);
				String minutos = String.valueOf(minutosInt);
				String segundos = df.format(segundosFloat);

				if (minutosInt < 10)
					minutos = "0" + minutos;

				if (segundosFloat < 10)
					segundos = "0" + segundos;

				switch (segundos.length()) {
				case 2:
					segundos += ",000";
					break;
				case 4:
					segundos += "00";
					break;
				case 5:
					segundos += "0";
					break;
				}

				String rankingString = String.valueOf(coche.getPosicionFinal() + "º");

				if (coche.getTiempo() < 3600) {
					tiempoString = "Tiempo: " + minutos + ":" + segundos;
				} else {
					tiempoString = "Tiempo: " + horas + ":" + minutos + ":" + segundos;
				}

				for (int i = 0; i < ((Main.ANCHOTOTAL - (anchoRanking + anchoNombres + anchoTiempo + 10)) / 2); i++) {
					System.out.print(" ");
				}

				while (rankingString.length() < anchoRanking) {
					rankingString = " " + rankingString;
				}

				System.out.print("│ " + rankingString + " │ " + coche.getPiloto());

				for (int i = 0; i < anchoNombres - coche.getPiloto().length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" │ " + tiempoString);

				for (int i = 0; i < (anchoTiempo - (tiempoString.length())); i++) {
					System.out.print(" ");
				}

				System.out.println(" │");
			}
		}

		// TERCERA LÍNEA (BORDE INFERIOR)
		for (int i = 0; i < ((Main.ANCHOTOTAL - (anchoRanking + anchoNombres + anchoTiempo + 10)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("└");
		for (int i = 0; i < anchoRanking + 2; i++) {
			System.out.print("─");
		}

		System.out.print("┴");

		for (int i = 0; i < (anchoNombres + 2); i++) {
			System.out.print("─");
		}

		System.out.print("┴");

		for (int i = 0; i < (anchoTiempo + 2); i++) {
			System.out.print("─");
		}

		System.out.println("┘");

		System.out.println();

		pintarContinuar();

	}

	/**
	 * Bloquea el programa hasta que el jugador presione Enter.
	 */
	private void pintarContinuar() {
		String texto = "";
		texto = "[ Presiona Enter para continuar ]";

		for (int i = 0; i < (Main.ANCHOTOTAL - texto.length()) / 2; i++) {
			System.out.print(" ");
		}
		System.out.print(texto);
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

	/**
	 * Comprueba si la carrera reúne los requisitos para considerarse finalizada.
	 * 
	 * @return true si la carrera ha terminado, false en caso de que todavía queden
	 *         coches enMarcha.
	 */
	public boolean isTerminada() {
		if (vOrdenLlegada[0] == null) {
			return false;
		} else {
			for (Coche coche : vCoches) {
				if (coche != null && coche.isEnMarcha()) {
					return false;
				}
			}
		}
		reordenarLlegada();
		return true;
	}

	// GETTERS / SETTERS
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getLongitud() {
		return longitud;
	}

	public int getNumCompetidores() {
		return numCompetidores;
	}

	public Coche[] getvOrdenLlegada() {
		return vOrdenLlegada;
	}

	public void setPatrocinador(String patrocinador) {
		this.patrocinador = patrocinador;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	public void setNumCompetidores(int numCompetidores) {
		this.numCompetidores = numCompetidores;
	}

	public int getNumJugadores() {
		return numJugadores;
	}

	public Coche[] getvCoches() {
		return vCoches;
	}

	public void setNumJugadores(int numJugadores) {
		this.numJugadores = numJugadores;
	}

	/**
	 * Muestra la lista de coches que han completado la carrera. No se utiliza salvo
	 * para realizar pruebas.
	 */
	public void imprimirOrdenLlegada() {

		for (Coche coche : vOrdenLlegada) {
			if (coche != null)
				System.out.println(coche);
		}
	}

	// toString
	@Override
	public String toString() {
		for (Coche coche : vCoches) {
			if (coche != null)
				System.out.println(coche);
		}
		return "Carrera [nombre=" + nombre + ", longitud=" + longitud + ", numCompetidores=" + numCompetidores + "]";
	}

}
