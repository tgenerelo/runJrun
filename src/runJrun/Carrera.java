package runJrun;

import java.util.Random;

public class Carrera {
	private String nombre;
	private float longitud;
	private int numCompetidores;
	private Coche vCoches[];
	private Coche vOrdenLlegada[];
	private int numTurno;

	public Carrera(String nombre, float longitud, int numCompetidores) {
		this.nombre = nombre;
		this.longitud = longitud;
		this.numCompetidores = numCompetidores;
		this.vCoches = new Coche[numCompetidores];
		this.vOrdenLlegada = new Coche[numCompetidores];
		this.numTurno = 0;
	}

//	public void posicionCoches() {
//		for (int i=0; i<vCoches.length; i++) {
//			for (int j=1; j<vCoches.length; j++) {
//				if ()
//			}
//		}
//	}

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
					vCoches[i].setPosicion(numTurno);
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
				if (vOrdenLlegada[i] != null && vOrdenLlegada[i].getPosicion() == vOrdenLlegada[i - 1].getPosicion()) {
					if (vOrdenLlegada[i].getKms() > vOrdenLlegada[i - 1].getKms()) {
						cocheAux = vOrdenLlegada[i];
						vOrdenLlegada[i] = vOrdenLlegada[i - 1];
						vOrdenLlegada[i - 1] = cocheAux;
					}
				}
			}
		}

		for (int i = 0; i < vOrdenLlegada.length; i++) {
			if (vOrdenLlegada[i] != null)
				vOrdenLlegada[i].setPosicion(i + 1);
		}
	}

	public void imprimirOrdenLlegada() {

		for (Coche coche : vOrdenLlegada) {
			if (coche != null)
				System.out.println(coche);
		}
	}
	
	public void imprimirClasificacion () {
		for (Coche coche : vOrdenLlegada) {
			if (coche!=null)
				System.out.println(coche.getPosicion() + "º | " + coche.getPiloto() + "   | Dorsal: " + coche.getDorsal());
		}
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

	@Override
	public String toString() {
		for (Coche coche : vCoches) {
			if (coche != null)
				System.out.println(coche);
		}
		return "Carrera [nombre=" + nombre + ", longitud=" + longitud + ", numCompetidores=" + numCompetidores + "]";
	}

}
