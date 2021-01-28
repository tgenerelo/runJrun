package runJrun;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

public class Carrera {
	private String nombre;
	private String patrocinador;
	private float longitud;
	private int numCompetidores;
	private Coche vCoches[];
	private Coche vOrdenLlegada[];
	private Coche vPosiciones[];
	private int numTurno;

	private final int ANCHOTOTAL = 160;
	private final int ANCHOPISTA = ANCHOTOTAL - 40;

	public Carrera(String nombre, float longitud, int numCompetidores) {
		this.nombre = nombre;
		this.patrocinador = generarPatrocinador();
		this.longitud = longitud;
		this.numCompetidores = numCompetidores;
		this.vCoches = new Coche[numCompetidores];
		this.vOrdenLlegada = new Coche[numCompetidores];
		this.vPosiciones = new Coche[numCompetidores];

		this.numTurno = 0;
	}

	public void posicionCoches() {
		Coche aux = null;

		vPosiciones = vCoches.clone();

		for (int i = 0; i < vPosiciones.length; i++) {
			for (int j = 1; j < vPosiciones.length; j++) {
				if (!vPosiciones[j].isTerminado() && !vPosiciones[j].isAccidentado()
						&& !vPosiciones[j - 1].isTerminado()
						&& (vPosiciones[j].getKms() > vPosiciones[j - 1].getKms())) {
					aux = vPosiciones[j - 1];
					vPosiciones[j - 1] = vPosiciones[j];
					vPosiciones[j] = aux;
				}
			}
		}

		asignarPosicionesCoches();
	}

	public void agregarCoche(Coche coche) {

		for (int i = 0; i < vCoches.length; i++) {
			if (vCoches[i] == null) {
				vCoches[i] = coche;
				vCoches[i].setDorsal(String.valueOf(i + 1));
				break;
			} else {
				if (i == vCoches.length - 1)
					System.out.println("La carrera está completa. " + coche.getPiloto() + " no podrá competir.");
			}
		}
	}

	private void asignarPosicionesCoches() {

		for (int i = 0; i < vCoches.length; i++) {
			for (int j = 0; j < vPosiciones.length; j++) {
				if (vCoches[i].equals(vPosiciones[j])) {
					vCoches[i].setPosicion(j + 1);
					break;
				}
			}
		}
	}

	public void turnoCarrera() {
		Random r = new Random();

		numTurno++;

		for (int i = 0; i < vCoches.length; i++) {
			if (!vCoches[i].isTerminado()) {
				if (!vCoches[i].isJugador()) {
					if (vCoches[i].isEnMarcha()) {
						if (vCoches[i].getVelocidad() <= 150) {
							vCoches[i].acelerar();
						} else {
							if (r.nextInt() % 2 == 0) {
								vCoches[i].acelerar();
							} else {
								vCoches[i].frenar();
							}
						}
					} else {
						if (vOrdenLlegada[0] == null)
							vCoches[i].arrancar();
					}
				}
				if (vCoches[i].getKms() >= longitud) {
					vCoches[i].setTerminado(true);
					vCoches[i].setMarcha(false);
					calcularTiempo(vCoches[i]);
					vCoches[i].setPosicionFinal(numTurno);
					for (int j = 0; j < vOrdenLlegada.length; j++) {
						if (vOrdenLlegada[j] == null) {
							vOrdenLlegada[j] = vCoches[i];
							break;
						} else {
							if (vOrdenLlegada[j].equals(vCoches[i]))
								break;
						}
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
		}

	}

	public boolean isTerminada() {
		for (Coche coche : vCoches) {
			if (vOrdenLlegada[0] == null || coche.isEnMarcha()) {
				return false;
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

	public void imprimirClasificacion() {
		DecimalFormat df = new DecimalFormat("##.####");
		df.setRoundingMode(RoundingMode.HALF_DOWN);
		
		String titulo = "CLASIFICACIÓN";
		int anchoNombres = 0;
		int anchoTiempo = "Tiempo: ".length() + 7 + "minutos".length();
		


		for (Coche coche : vCoches) {
			if (coche.getPiloto().length() > anchoNombres)
				anchoNombres = coche.getPiloto().length();
		}

		pintarGraficos();

		System.out.println();
		
//		CABECERA (PRIMERA LÍNEA)
		for (int i = 0; i < ((ANCHOTOTAL - (anchoNombres + anchoTiempo + 12)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("┌");
		for (int i = 0; i < 4; i++) {
			System.out.print("─");
		}

		System.out.print("┬");

		for (int i = 0; i < (anchoNombres+2); i++) {
			System.out.print("─");
		}
		
		System.out.print("┬");

		for (int i = 0; i < (anchoTiempo+2); i++) {
			System.out.print("─");
		}

		System.out.println("┐");
		
//		CABECERA (TÍTULO)
		for (int i = 0; i < ((ANCHOTOTAL - (anchoNombres + anchoTiempo + 12)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("┌");
		

//		 PRIMERA LÍNEA (borde)
		for (int i = 0; i < ((ANCHOTOTAL - (anchoNombres + anchoTiempo + 12)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("┌");
		for (int i = 0; i < 4; i++) {
			System.out.print("─");
		}

		System.out.print("┬");

		for (int i = 0; i < (anchoNombres+2); i++) {
			System.out.print("─");
		}
		
		System.out.print("┬");

		for (int i = 0; i < (anchoTiempo+2); i++) {
			System.out.print("─");
		}

		System.out.println("┐");

		// SEGUNDA LÍNEA (contenido)
		for (Coche coche : vOrdenLlegada) {
			if (coche != null) {
				for (int i = 0; i < ((ANCHOTOTAL - (anchoNombres + anchoTiempo + 12)) / 2); i++) {
					System.out.print(" ");
				}
				System.out.print("│ " + coche.getPosicionFinal() + "º │ " + coche.getPiloto());
				for (int i = 0; i < anchoNombres - coche.getPiloto().length(); i++) {
					System.out.print(" ");
				}
				System.out.println(" │ Tiempo: " + df.format((coche.getTiempo() / 60)) + " minutos │");
			}

		}

		// TERCERA LÍNEA (borde)
		for (int i = 0; i < ((ANCHOTOTAL - (anchoNombres + anchoTiempo + 12)) / 2); i++) {
			System.out.print(" ");
		}
		System.out.print("└");
		for (int i = 0; i < 4; i++) {
			System.out.print("─");
		}

		System.out.print("┴");

		for (int i = 0; i < (anchoNombres+2); i++) {
			System.out.print("─");
		}
		
		System.out.print("┴");

		for (int i = 0; i < (anchoTiempo+2); i++) {
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
		for (int i = 0; i < ((ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8))
				/ 2); i++) {
			System.out.print(" ");
		}
		System.out.println("│ " + nombre.toUpperCase() + " │ " + patrocinadoPor + " " + patrocinador + " │");

		for (int i = 0; i < ((ANCHOTOTAL - (nombre.length() + patrocinador.length() + patrocinadoPor.length() + 8))
				/ 2); i++) {
			System.out.print(" ");
		}

		// TERCERA LÍNEA (borde)
		System.out.print("└");
		for (int i = 0; i < nombre.length() + 2; i++) {
			System.out.print("─");
		}

		System.out.print("┴");

		for (int i = 0; i < (patrocinadoPor.length() + patrocinador.length() + 3); i++) {
			System.out.print("─");
		}

		System.out.println("┘");
	}

	private void pintarLateralPista() {
		for (int i = 0; i < ANCHOTOTAL; i++) {
			System.out.print("■");
		}
		System.out.println();
	}

	public void pintarGraficos() {

		int distanciaRecorrida = 0;
		int distanciaRestante = 0;
		float unidadAvance = longitud / ANCHOPISTA;
		String cocheNpc = "[ D)";
		String cochePc = "[»D)";
		String cocheAcc = "[XX)";
		String cochecito = "";

		pintarNombreCarrera();
		pintarLateralPista();

		for (Coche coche : vCoches) {
			distanciaRecorrida = Math.round((coche.getKms() / unidadAvance));
			distanciaRestante = Math.round(ANCHOPISTA - (coche.getKms() / unidadAvance));
			if (ANCHOPISTA - (distanciaRecorrida + distanciaRestante) > 0)
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
				distanciaRecorrida = ANCHOPISTA;

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
			if ((distanciaRestante + distanciaRecorrida) < ANCHOPISTA) {
				distanciaRestante++;
			} else {
				if ((distanciaRestante + distanciaRecorrida) > ANCHOPISTA) {
					distanciaRestante--;
				}
			}

			for (int i = 0; i < distanciaRestante; i++) {
				System.out.print(" ");
			}
			System.out.println(
					"║ ▀▄▀▄ " + coche.getPosicion() + "º [" + coche.getDorsal() + " - " + coche.getPiloto() + "]");
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
