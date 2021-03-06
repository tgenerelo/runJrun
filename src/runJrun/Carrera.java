package runJrun;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	private int numTurno;
	private int numJugadores;
	private int dificultad;
	private boolean soloNpcs;

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
		this.numTurno = -1;
		this.numJugadores = 0;
		this.dificultad = 2;
		this.soloNpcs = false;

		Main.velocidadMax = 200;
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
	 * Comprueba que todos los ajustes de la carrera son válidos y controla el
	 * progreso de la carrera hasta que esta termina.
	 */
	public void comenzar() {
		prepararCarrera();

		if (Main.modoEspectador == true) {
			if (soloNpcs) {
				do {
					pintarGraficos();
					System.out.println("\n\n\n\n");
					turnoCarrera();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				} while (!isTerminada());
			} else {
				do {
					int jugTermin = 0;
					int jugActiv = 0;

					for (Coche coche : vCoches) {
						if (coche.isJugador() && (coche.isTerminado() || (coche.isAccidentado() && vOrdenLlegada[0]!=null)))
							jugTermin++;
					}
					
					jugActiv= numJugadores - jugTermin;
					
					if (jugActiv >0) {
						turnoCarrera();
					} else {
						pintarGraficos();
						System.out.println("\n\n\n\n");
						turnoCarrera();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					}

				} while (!isTerminada());

			}

		} else {
			do {
				turnoCarrera();
			} while (!isTerminada());

		}

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

		if (numJugadores == 0)
			soloNpcs = true;

		if (numCompetidores < numJugadores)
			numCompetidores = numJugadores;

		vParticipantes = new Coche[numCompetidores];
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
				}
			}
		}

		for (Coche coche : vCoches) {
			if (coche != null) {
				if (!coche.isJugador()) {
					turnoNpcs(coche);
				}
			}
		}

		ordenarPosiciones();
	}

	private void ordenarPosiciones() {

		int cochesOrdenados = 0;

		for (int i = 0; i < vCoches.length; i++) {
			if ((!vCoches[i].isTerminado()) && (vCoches[i].getKms() >= longitud)) {
				vCoches[i].setTerminado(true);
				for (int j = 0; j < vOrdenLlegada.length; j++) {
					if (vOrdenLlegada[j] == null) {
						vOrdenLlegada[j] = vCoches[i];
						calcularTiempo(vOrdenLlegada[j]);
						while ((j > 0) && (vCoches[i].getTiempo() < vOrdenLlegada[j - 1].getTiempo())) {
							Coche aux = vOrdenLlegada[j - 1];
							vOrdenLlegada[j - 1] = vCoches[i];
							vOrdenLlegada[j] = aux;
						}
						break;
					}
				}
			}
		}

		for (int i = 0; i < vOrdenLlegada.length; i++) {
			if (vOrdenLlegada[i] != null) {
				vOrdenLlegada[i].setPosicionFinal(i + 1);
				if ((i + 1) > cochesOrdenados) {
					cochesOrdenados = (i + 1);
				}
			}
		}

		Coche vAux[] = vCoches.clone();

		for (int i = 0; i < vCoches.length; i++) {
			if (vCoches[i] != null) {

				Coche cocheActual = vCoches[i];

				if (cocheActual.isTerminado()) {
					cocheActual.setPosicion(cocheActual.getPosicionFinal());
					vAux[cocheActual.getPosicion() - 1] = cocheActual;
				} else {
					float numKms = 0;
					Coche aux = null;

					for (int j = 0; j < vAux.length; j++) {
						if (vAux[j] != null && !vAux[j].isTerminado() && !vAux[j].isAccidentado()
								&& (vAux[j].getKms() > numKms)) {
							numKms = vAux[j].getKms();
							aux = vAux[j];
						}
					}

					for (int j = 0; j < vCoches.length; j++) {
						if (vCoches[j] != null && vCoches[j].equals(aux)) {
							vCoches[j].setPosicion(cochesOrdenados + 1);
							cochesOrdenados++;
							for (int m = 0; m < vAux.length; m++) {
								if (vAux[m] != null && vAux[m].equals(aux))
									vAux[m] = null;
							}
						}
					}

				}
			}

		}

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

		ordenarPosiciones();

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

			int jugTermin = 0;
			int jugActiv = 0;

			for (Coche coche : vCoches) {
				if (coche.isJugador() && (coche.isTerminado() || (coche.isAccidentado() && vOrdenLlegada[0]!=null)))
					jugTermin++;
			}
			
			jugActiv= numJugadores - jugTermin;
			
			if (jugActiv > 1 && numTurno > 0) {
				ordenarPosiciones();
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
		float probAceler = (Main.velocidadMax - coche.getVelocidad()) / Main.POTENCIA;

		if (!coche.isTerminado()) {
			if (coche.isEnMarcha()) {
				if (coche.getVelocidad() <= Main.velocidadMax - Main.POTENCIA) {
					if (dificultad == 1) {
						if (r.nextFloat() < 0.8) {
							coche.acelerar();
						} else {
							coche.frenar();
						}
					} else {
						coche.acelerar();
					}

				} else {
					switch (dificultad) {
					case 1:
						if (r.nextFloat() < 0.7f) {
							coche.acelerar();
						} else {
							coche.frenar();
						}
					case 2:
						if (r.nextInt() % 2 == 0) {
							coche.acelerar();
						} else {
							coche.frenar();
						}
						break;
					case 3:
						probAceler = ((Main.velocidadMax - coche.getVelocidad()) / (Main.POTENCIA * 0.9f));
						if (r.nextFloat() <= probAceler) {
							coche.acelerar();
						} else {
							coche.frenar();
						}
						break;
					case 4:
						probAceler = ((Main.velocidadMax - coche.getVelocidad()) / (Main.POTENCIA * 0.4f));
						if (r.nextFloat() <= probAceler) {
							coche.acelerar();
						} else {
							coche.frenar();
						}
						break;
					}

				}

			} else {
				if (vOrdenLlegada[0] == null)
					coche.arrancar();
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

		Main.anchoPista = Main.ANCHOTOTAL - (anchoNombresYDorsales(vCoches) + 22);

		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		pintarNombreCarrera();
		pintarLateralPista();

		for (Coche coche : vCoches) {
			if (coche != null) {
				distanciaRecorrida = (int) (coche.getKms() / unidadAvance);
				distanciaRestante = (int) (Main.anchoPista - (coche.getKms() / unidadAvance));

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

				if (distanciaRestante < 0) {
					distanciaRestante = 0;
					distanciaRecorrida = Main.anchoPista;
				}

				if (Main.anchoPista > (distanciaRecorrida + distanciaRestante))
					distanciaRestante++;

				if (distanciaRecorrida > Main.anchoPista) {
					distanciaRecorrida = Main.anchoPista;
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

				if ((!coche.isTerminado() && coche.isEnMarcha()) && numTurno > 1) {
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
		
//		float distMaxTurno = (Main.velocidadMax/3.6f)*10;

		coche.setTiempo((numTurno * Main.SEGUNDOSTURNO) - ((coche.getKms() - longitud) * 1000) / (Main.velocidadMax/3.6f));
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
				vOrdenLlegada[i].setPosicionFinal(i + 1);
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
		String ruta = "Puntuaciones.txt";
		PrintWriter pw = null;
//		FileReader fr = null;

		try {
			pw = new PrintWriter(new FileWriter(ruta, true));
			pw.println("");
			for (int i = 0; i < nombre.length() + patrocinador.length() + 21; i++) {
				pw.print("─");
			}

			pw.println("\n" + nombre.toUpperCase() + ", patrocinado por " + patrocinador);
			pw.print("Jugadores: " + numJugadores + " / " + vCoches.length + " - ");
			switch (dificultad) {
			case 1:
				pw.println("Dificultad: Fácil");
				break;
			case 2:
				pw.println("Dificultad: Clásica");
				break;
			case 3:
				pw.println("Dificultad: Humano");
				break;
			case 4:
				pw.println("Dificultad: Imprudente");
				break;
			}
			pw.println("Longitud del circuito: " + longitud + " kilómetros");
			for (int i = 0; i < nombre.length() + patrocinador.length() + 19; i++) {
				pw.print("-");
			}
			pw.println("");
			pw.close();
		} catch (IOException e) {

		}

		String titulo = "CLASIFICACIÓN FINAL";
		int anchoNombres = anchoNombres(vOrdenLlegada);
		int anchoTiempo = 11;
		int anchoRanking = 0;
		int anchoDorsales = 0;
		int anchoSeparadores = 13;

		for (Coche coche : vOrdenLlegada) {
			if (coche != null && String.valueOf(coche.getPosicionFinal()).length() + 1 > anchoRanking) {
				anchoRanking = (String.valueOf(coche.getPosicionFinal()).length() + 1);
			}
			if (coche != null && String.valueOf(coche.getDorsal()).length() > anchoDorsales) {
				anchoDorsales = coche.getDorsal().length();
			}
		}

		for (int i = vOrdenLlegada.length - 1; i > 0; i--) {
			if (vOrdenLlegada[i] != null) {
				if (vOrdenLlegada[i].getTiempo() >= 3600f) {
					anchoTiempo = 11;
					break;
				} else {
					anchoTiempo = 9;
					break;

				}
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
		for (int i = 0; i < ((Main.ANCHOTOTAL
				- (anchoRanking + anchoDorsales + anchoNombres + anchoTiempo + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("╔");

		for (int i = 0; i < (anchoRanking + anchoNombres + anchoDorsales + anchoTiempo + anchoSeparadores - 2); i++) {
			System.out.print("═");
		}

		System.out.println("╗");

		// CABECERA (TÍTULO)
		for (int i = 0; i < ((Main.ANCHOTOTAL
				- (anchoRanking + anchoDorsales + anchoNombres + anchoTiempo + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("║");

		if ((anchoRanking + anchoDorsales + anchoNombres + anchoTiempo + anchoSeparadores) % 2 == 0
				&& titulo.length() % 2 != 0) {
			titulo += " ";
		}

		for (int i = 0; i < (((anchoRanking + anchoDorsales + anchoNombres + anchoTiempo + anchoSeparadores - 2)
				- titulo.length()) / 2); i++) {
			System.out.print(" ");
		}

		System.out.print(titulo);

		for (int i = 0; i < (((anchoRanking + anchoDorsales + anchoNombres + anchoTiempo + anchoSeparadores - 2)
				- titulo.length()) / 2); i++) {
			System.out.print(" ");
		}

		System.out.println("║");

		// BORDE INFERIOR CABECERA
		for (int i = 0; i < ((Main.ANCHOTOTAL
				- (anchoRanking + anchoDorsales + anchoNombres + anchoTiempo + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("╚");

		for (int i = 0; i < (anchoRanking + anchoNombres + anchoDorsales + anchoTiempo + anchoSeparadores - 2); i++) {
			System.out.print("═");
		}

		System.out.println("╝");

		// SEPARADOR TABLA
		for (int i = 0; i < ((Main.ANCHOTOTAL
				- (anchoRanking + anchoDorsales + anchoNombres + anchoTiempo + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("┌");
		for (int i = 0; i < anchoRanking + 2; i++) {
			System.out.print("─");
		}

		System.out.print("┬");

		for (int i = 0; i < (anchoDorsales + anchoNombres + 5); i++) {
			System.out.print("─");
		}

		System.out.print("┬");

		for (int i = 0; i < (anchoTiempo + 2); i++) {
			System.out.print("─");
		}

		System.out.println("┐");

		// NOMBRES COLUMNAS
		for (int i = 0; i < ((Main.ANCHOTOTAL
				- (anchoRanking + anchoDorsales + anchoNombres + anchoTiempo + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("│");
		for (int i = 0; i < anchoRanking + 2; i++) {
			System.out.print(" ");
		}

		System.out.print("│");

		String titCol = "Piloto";

		if ((anchoDorsales + anchoNombres + 5) % 2 != 0 && titCol.length() % 2 == 0) {
			titCol += " ";
		}

		for (int i = 0; i < ((anchoDorsales + anchoNombres + 5) - titCol.length()) / 2; i++) {
			System.out.print(" ");
		}
		System.out.print(titCol);
		for (int i = 0; i < ((anchoDorsales + anchoNombres + 5) - titCol.length()) / 2; i++) {
			System.out.print(" ");
		}

		System.out.print("│");

		titCol = "Tiempo";

		if ((anchoTiempo + 2) % 2 != 0 && titCol.length() % 2 == 0) {
			titCol += " ";
		}

		for (int i = 0; i < ((anchoTiempo + 2) - titCol.length()) / 2; i++) {
			System.out.print(" ");
		}
		System.out.print(titCol);
		for (int i = 0; i < ((anchoTiempo + 2) - titCol.length()) / 2; i++) {
			System.out.print(" ");
		}

		System.out.println("│");

		// SEPARADOR TABLA
		for (int i = 0; i < ((Main.ANCHOTOTAL
				- (anchoRanking + anchoDorsales + anchoNombres + anchoTiempo + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("├");
		for (int i = 0; i < anchoRanking + 2; i++) {
			System.out.print("─");
		}

		System.out.print("┼");

		for (int i = 0; i < (anchoDorsales + anchoNombres + 5); i++) {
			System.out.print("─");
		}

		System.out.print("┼");

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

				while (rankingString.length() < anchoRanking) {
					rankingString = " " + rankingString;
				}

				if (coche.getTiempo() < 3600) {
					tiempoString = minutos + ":" + segundos;
					while (anchoTiempo > (tiempoString.length())) {
						tiempoString = " " + tiempoString;
					}
				} else {
					tiempoString = horas + ":" + minutos + ":" + segundos;
				}

				while (coche.getDorsal().length() < anchoDorsales) {
					coche.setDorsal(" " + coche.getDorsal());
				}

				for (int i = 0; i < ((Main.ANCHOTOTAL
						- (anchoRanking + anchoDorsales + anchoNombres + anchoTiempo + anchoSeparadores)) / 2); i++) {
					System.out.print(" ");
				}

				while (rankingString.length() < anchoRanking) {
					rankingString = " " + rankingString;
				}

				System.out
						.print("│ " + rankingString + " │ " + "[" + coche.getDorsal() + "]" + " " + coche.getPiloto());

				for (int i = 0; i < anchoNombres - coche.getPiloto().length(); i++) {
					System.out.print(" ");
				}
				System.out.print(" │ " + tiempoString);

				System.out.println(" │");

//				try {
//					pw = new PrintWriter(new FileWriter(ruta, true));
//					
//					if (coche != null) {
//						pw.println(rankingString + " [" + coche.getDorsal() + "] " + coche.getPiloto() + " - " + tiempoString);
//					}
//
//					pw.close();
//				} catch (IOException e) {
//
//				}

				try {
					pw = new PrintWriter(new FileWriter(ruta, true));
//					fr = new FileReader(ruta);

//					for (Coche coche2 : vOrdenLlegada) {
//						for (int i=0; i<ruta.length(); i++) {
//							if () {
//								pw.println("");
//								pw.println(rankingString + " [" + coche.getDorsal() + "] " + coche.getPiloto() + " - " + tiempoString);
//							}
//						}
//					}

					if (coche != null) {
						pw.println(rankingString + " [" + coche.getDorsal() + "] " + coche.getPiloto() + " - "
								+ tiempoString);
					}

					pw.close();
				} catch (IOException e) {

				}

			}
		}

		// TERCERA LÍNEA (BORDE INFERIOR)
		for (int i = 0; i < ((Main.ANCHOTOTAL
				- (anchoRanking + anchoDorsales + anchoNombres + anchoTiempo + anchoSeparadores)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("└");
		for (int i = 0; i < anchoRanking + 2; i++) {
			System.out.print("─");
		}

		System.out.print("┴");

		for (int i = 0; i < (anchoDorsales + anchoNombres + 5); i++) {
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

	public int getDificultad() {
		return dificultad;
	}

	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
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
