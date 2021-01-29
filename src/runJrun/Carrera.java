package runJrun;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Carrera {
	private String nombre;
	private String patrocinador;
	private float longitud;
	private int numCompetidores;
	private Coche vCoches[];
	private Coche vCochesCopia[];
	private Coche vOrdenLlegada[];
	private Coche vPosiciones[];
	private int numTurno;
	private int numJugadores;

	private final int ANCHOTOTAL = 160;
	private int anchoPista = ANCHOTOTAL - 40;

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

	public void posicionCoches() {
		Coche aux = null;

		vPosiciones = vCoches.clone();

		for (int i = 0; i < (vPosiciones.length - 1); i++) {
			int j;
			if (vPosiciones[i] == null) {
				for (j = i + 1; j < vPosiciones.length - 1; j++) {
					if (vPosiciones[j] != null)
						break;
				}
				vPosiciones[i] = vPosiciones[j];
				vPosiciones[j] = null;

			}
		}

		for (Coche coche : vPosiciones) {

			for (int i = 1; i < vPosiciones.length; i++) {
				if (vPosiciones[i] != null) {
					if (!vPosiciones[i].isTerminado() && !vPosiciones[i].isAccidentado()
							&& !vPosiciones[i - 1].isTerminado()
							&& (vPosiciones[i].getKms() > vPosiciones[i - 1].getKms())) {
						aux = vPosiciones[i - 1];
						vPosiciones[i - 1] = vPosiciones[i];
						vPosiciones[i] = aux;
					}
				}

			}
		}

		asignarPosicionesCoches();
	}

	private void comprobarDorsal(Coche coche) {

		for (int j = 0; j < vCoches.length; j++) {
			if (vCoches[j] != null && coche.getDorsal().equalsIgnoreCase(vCoches[j].getDorsal())) {
				coche.generarDorsal();
				j = 0;
			}
		}

	}
	
	public void prepararCarrera() {
		for (int i=0; i<vCoches.length; i++) {
			if (vCoches[i] == null) {
				agregarCoche(new Coche());
				i--;
			}
		}
	}

	public void agregarCoche(Coche coche) {
		Random r = new Random();
		int random = 0;
		boolean asignado = false;
		
		if (coche.isJugador()) {
			numJugadores++;
			coche.setNumJugador(numJugadores);
		}

		comprobarDorsal(coche);

		// COLOCA LOS COCHES EN UN ORDEN AL AZAR EN vCoches
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

	private void asignarPosicionesCoches() {

		for (Coche coche : vCoches) {
			if (coche != null) {
				for (int j = 0; j < vPosiciones.length; j++) {
					if (coche.getDorsal().equals(vPosiciones[j].getDorsal())) {
						coche.setPosicion(j + 1);
						break;
					}
				}
			}

		}

	}

	private void turnoJugador(Coche jugador) {
		Scanner leer = new Scanner(System.in);
		int userInput = 0;
		boolean accionValida = true;

		if (jugador.isEnMarcha() || vOrdenLlegada[0] == null) {

			do {
				pintarGraficos();
				pintarSalpicadero(jugador);
				if (jugador.isEnMarcha()) {
					System.out.print("                                  [ 2-Acelerar  |  3-Frenar ]  > ");
				} else {
					System.out.print("                                        [ 1-Arrancar ]  > ");
				}
				do {
					try {
						userInput = leer.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("** ERROR: Opción no válida **");
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

				if (accionValida == false)
					System.out.println("** ERROR: Acción no válida **");

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
		}
	}

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
				coche.setVelocidadMedia((coche.getVelocidadMedia()+coche.getVelocidad())/numTurno);
			} else {
				if (vOrdenLlegada[0] == null)
					coche.arrancar();
			}
		}
	}

	public void turnoCarrera() {
//		Coche vAux[] = vCoches.clone();

		numTurno++;

		for (int i=1; i<=numJugadores; i++) {
			for (int j=0; j<vCoches.length; j++) {
				if (vCoches[j].getNumJugador() == i) {
					turnoJugador(vCoches[j]);
				}
			}
		}

//		for (Coche coche : vCoches) {
//			if (coche != null) {
//				if (coche.isJugador()) {
//					turnoJugador(coche);
//				}
//			}
//		}
		
//		vCoches=vAux.clone();

		for (Coche coche : vCoches) {
			if (coche != null) {
				if (!coche.isJugador()) {
					turnoNpcs(coche);
				}
			}
		}

		for (Coche coche : vCoches) {
			if (coche.getKms() >= longitud) {
				coche.setTerminado(true);
				coche.setMarcha(false);
				calcularTiempo(coche);
				coche.setPosicionFinal(numTurno);
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

		posicionCoches();
	}

	private void calcularTiempo(Coche coche) {
		if (coche.isTerminado()) {
			coche.setTiempo((numTurno * 10) - (coche.getKms() - longitud / 5)); // El tiempo que ha logrado el coche en
																				// segundos.
		} else {
//			coche.setTiempo((numTurno * 10) - (longitud - coche.getKms() / -5));
		}

	}

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

	private void reordenarLlegada() {
		Coche cocheAux = null;

		for (int m = 0; m < vOrdenLlegada.length; m++) {
			for (int i = 1; i < vOrdenLlegada.length; i++) {
				if (vOrdenLlegada[i] != null
						&& vOrdenLlegada[i].getPosicionFinal() == vOrdenLlegada[i - 1].getPosicionFinal()) {
					if (vOrdenLlegada[i].getTiempo() < vOrdenLlegada[i - 1].getTiempo()) {
						cocheAux = vOrdenLlegada[i];
						vOrdenLlegada[i] = vOrdenLlegada[i - 1];
						vOrdenLlegada[i - 1] = cocheAux;
					}
				}
			}
		}

		for (int i = 0; i < vOrdenLlegada.length; i++) {
			if (vOrdenLlegada[i] != null)
				vOrdenLlegada[i].setPosicionFinal(i + 1);
		}
	}

	public void imprimirOrdenLlegada() {

		for (Coche coche : vOrdenLlegada) {
			if (coche != null)
				System.out.println(coche);
		}
	}

	private int anchoNombres() {
		int anchoNombres = 0;

		for (Coche coche : vCoches) {
			if (coche != null) {
				if (coche.getPiloto().length() > anchoNombres)
					anchoNombres = coche.getPiloto().length();
			}

		}
		return anchoNombres;
	}

	public void imprimirClasificacion() {
		DecimalFormat df = new DecimalFormat("##.####");
		df.setRoundingMode(RoundingMode.HALF_DOWN);

		String titulo = "CLASIFICACIÓN FINAL";
		int anchoNombres = anchoNombres();
		int anchoTiempo = 23;
		int anchoRanking = 3;

		pintarGraficos();

		System.out.println();

//		CABECERA (BORDE SUPERIOR)
		for (int i = 0; i < ((ANCHOTOTAL - (anchoRanking + anchoNombres + anchoTiempo + 11)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("┌");
		for (int i = 0; i < (anchoRanking + anchoNombres + anchoTiempo + 9); i++) {
			System.out.print("─");
		}

		System.out.println("┐");

//		CABECERA (TÍTULO)
		for (int i = 0; i < ((ANCHOTOTAL - (anchoRanking + anchoNombres + anchoTiempo + 11)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("│");
		for (int i = 0; i < (((anchoRanking + anchoNombres + anchoTiempo + 9) - titulo.length()) / 2); i++) {
			System.out.print(" ");
		}

		System.out.print(titulo);

		if ((anchoNombres + anchoTiempo + 12) % 2 != 0) {
			for (int i = 0; i < (((anchoRanking + anchoNombres + anchoTiempo + 10) - titulo.length()) / 2); i++) {
				System.out.print(" ");
			}
		} else {
			for (int i = 0; i < (((anchoRanking + anchoNombres + anchoTiempo + 11) - titulo.length()) / 2); i++) {
				System.out.print(" ");
			}
		}

		System.out.println("│");

//		 SEPARADOR TABLA

		for (int i = 0; i < ((ANCHOTOTAL - (anchoRanking + anchoNombres + anchoTiempo + 11)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("├");
		for (int i = 0; i < anchoRanking + 2; i++) {
			System.out.print("─");
		}

		System.out.print("┬");

		for (int i = 0; i < (anchoNombres + 3); i++) {
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
				String tiempoString = "Tiempo: " + (df.format((coche.getTiempo() / 60)).toString()) + " minutos";
				String rankingString = String.valueOf(coche.getPosicionFinal() + "º");

				for (int i = 0; i < ((ANCHOTOTAL - (anchoRanking + anchoNombres + anchoTiempo + 11)) / 2); i++) {
					System.out.print(" ");
				}

				if (rankingString.length() < anchoRanking) {
					System.out.print("│ " + rankingString + "  │ " + coche.getPiloto());
				} else {
					System.out.print("│ " + rankingString + " │ " + coche.getPiloto());
				}

				for (int i = 0; i < anchoNombres - coche.getPiloto().length(); i++) {
					System.out.print(" ");
				}
				System.out.print("  │ " + tiempoString + " ");
				for (int i = 0; i < (anchoTiempo - tiempoString.length()); i++) {
					System.out.print(" ");
				}
				System.out.println("│");
			}

		}

		// TERCERA LÍNEA (BORDE INFERIOR)
		for (int i = 0; i < ((ANCHOTOTAL - (anchoRanking + anchoNombres + anchoTiempo + 11)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("└");
		for (int i = 0; i < anchoRanking + 2; i++) {
			System.out.print("─");
		}

		System.out.print("┴");

		for (int i = 0; i < (anchoNombres + 3); i++) {
			System.out.print("─");
		}

		System.out.print("┴");

		for (int i = 0; i < (anchoTiempo + 2); i++) {
			System.out.print("─");
		}

		System.out.println("┘");
	}

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

	private void pintarNombreCarrera() {
		String patrocinadoPor = "Patrocinado por:";

		// PRIMERA LÍNEA (borde)
		for (int i = 0; i < ((ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8))
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
		for (int i = 0; i < ((ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8)) / 2)
				- 1; i++) {
			System.out.print("░");
		}
		System.out.print(" │ " + nombre.toUpperCase() + " │ " + patrocinadoPor + " " + patrocinador + " │ ");
		if ((ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8)) % 2 == 0) {
			for (int i = 0; i < ((ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8))
					/ 2) - 1; i++) {
				System.out.print("░");
			}
		} else {
			for (int i = 0; i < ((ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8))
					/ 2); i++) {
				System.out.print("░");
			}
		}
		System.out.println();

		// TERCERA LÍNEA (borde)
		for (int i = 0; i < ((ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8)) / 2)
				- 1; i++) {
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

		if ((ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8)) % 2 == 0) {
			for (int i = 0; i < ((ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8))
					/ 2) - 1; i++) {
				System.out.print("░");
			}
		} else {
			for (int i = 0; i < ((ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8))
					/ 2); i++) {
				System.out.print("░");
			}
		}

		System.out.println();
	}

	public void pintarSalpicadero(Coche coche) {
		String velocidadString = String.valueOf(coche.getVelocidad()) + " km/h";
		String kmsString = String.format("%.1f", coche.getKms()) + " km";
		String posicionString = String.valueOf(coche.getPosicion()) + "º";
		String estado = "";
		String numJugadorString;

		numJugadorString = "JUGADOR " + coche.getNumJugador() + ": " + coche.getPiloto();

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
		for (int i = 0; i < ((ANCHOTOTAL - ((velocidadString.length() + kmsString.length() + posicionString.length()
				+ estado.length() + numJugadorString.length()) + 18)) / 2); i++) {
			System.out.print(" ");
		}

		System.out.print("╔");
		for (int i = 0; i < numJugadorString.length() + 2; i++) {
			System.out.print("═");
		}
		System.out.print("╗ ");

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
		for (int i = 0; i < ((ANCHOTOTAL - ((velocidadString.length() + kmsString.length() + posicionString.length()
				+ estado.length() + numJugadorString.length()) + 18)) / 2); i++) {
			System.out.print(" ");
		}

		System.out.print("║ ");
		System.out.print(numJugadorString);
		System.out.print(" ║ ");

		System.out.println("║ " + velocidadString + " ║ " + kmsString + " ║ " + posicionString + " ║ " + estado + " ║");

		// SALPICADERO (BORDE INFERIOR)
		for (int i = 0; i < ((ANCHOTOTAL - ((velocidadString.length() + kmsString.length() + posicionString.length()
				+ estado.length() + numJugadorString.length()) + 18)) / 2); i++) {
			System.out.print(" ");
		}

		System.out.print("╚");
		for (int i = 0; i < numJugadorString.length() + 2; i++) {
			System.out.print("═");
		}
		System.out.print("╝ ");

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

	private void pintarLateralPista() {
		for (int i = 0; i < ANCHOTOTAL; i++) {
			System.out.print("■");
		}
		System.out.println();
	}

	public void pintarGraficos() {

		anchoPista = ANCHOTOTAL - (anchoNombres() + 23);
		int distanciaRecorrida = 0;
		int distanciaRestante = 0;
		float unidadAvance = longitud / anchoPista;
		String cocheNpc = "[ D)";
		String cochePc = "[»D)";
		String cocheAcc = "[XX)";
		String cochecito = "";

		pintarNombreCarrera();
		pintarLateralPista();

		for (Coche coche : vCoches) {
			if (coche != null) {
				distanciaRecorrida = Math.round((coche.getKms() / unidadAvance));
				distanciaRestante = Math.round(anchoPista - (coche.getKms() / unidadAvance));
				if (anchoPista - (distanciaRecorrida + distanciaRestante) > 0)
					distanciaRestante--;

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
				if (coche.getKms() >= longitud)
					distanciaRecorrida = anchoPista;

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

				// Elimina problemas poco frecuentes en la representación provocados por los
				// redondeos.
				if ((distanciaRestante + distanciaRecorrida) < anchoPista) {
					distanciaRestante++;
				} else {
					if ((distanciaRestante + distanciaRecorrida) > anchoPista) {
						distanciaRestante--;
					}
				}

				for (int i = 0; i < distanciaRestante; i++) {
					System.out.print(" ");
				}

				String ranking = String.valueOf(coche.getPosicion() + "º");

				if (ranking.length() < 3)
					ranking += " ";

				System.out.println("║ ▀▄▀▄ " + ranking + " [" + coche.getDorsal() + " - " + coche.getPiloto() + "]");
			}

		}

		pintarLateralPista();

		pintarSalpicadero();

	}

	private void pintarSalpicadero() {

	}

	@Override
	public String toString() {
		for (Coche coche : vCoches) {
			if (coche != null)
				System.out.println(coche);
		}
		return "Carrera [nombre=" + nombre + ", longitud=" + longitud + ", numCompetidores=" + numCompetidores + "]";
	}

}
