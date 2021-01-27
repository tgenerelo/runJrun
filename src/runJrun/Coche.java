package runJrun;

import java.util.Random;

public class Coche {
	private String piloto;
	private String dorsal;
	private int velocidad;
	private int posicion;
	private float kms;
	private boolean enMarcha;
	private boolean accidentado;
	private boolean terminado;
	private boolean jugador;

	final int POTENCIA = 50;
	final int SEGUNDOSTURNO = 10;

	public Coche() {
		this.piloto = generarNombre();
		this.dorsal = generarDorsal();
		this.velocidad = 0;
		this.posicion=0;
		this.kms = 0;
		this.enMarcha = false;
		this.accidentado = false;
		this.terminado = false;
		this.jugador = false;
	}

	public Coche(String piloto, String dorsal, boolean jugador) {
		super();
		this.piloto = piloto;
		this.dorsal = dorsal;
		this.velocidad = 0;
		this.posicion=0;
		this.kms = 0;
		this.enMarcha = false;
		this.accidentado = false;
		this.terminado = false;
		this.jugador = jugador;
	}

	private String generarNombre() {
		Random r = new Random();
		String vNombres[] = { "Otis", "Blake", "Harley", "James", "Charles", "Wayne", "Sherman", "Floyd", "Ash", "Roy",
				"Ross", "Kurt", "Earl", "Jessie", "Robert", "Bob", "Baxter", "Torin", "Umair", "Roberta", "Ava-Mae",
				"Darien", "Keavy", "Abu", "Lucien", "Brenden", "Gregory", "Kaylie", "Nicola", "Nikola", "Beauden",
				"Layan", "Raheem", "Tasnia", "Devin", "Dennis", "Billy-Joe", "Valerie", "Valeria", "Valentina",
				"Valentine", "Milena", "Donte", "Dante", "Renesmae", "René", "Renée", "Fiza", "Sohail", "Keelan",
				"Shirley", "Devan", "Rosanna", "Nabeela", "Jenna", "Kya", "Tymoteusz", "Kaya", "Jolie", "Nola", "Nana",
				"Nora", "Norah", "Lyle", "Bethaney", "Bethany", "Brittany", "Bella-Rose", "Nyle", "Allegra", "Dwayne",
				"Dominika", "Dominic", "Dominique", "Amanda", "Zachery", "Wendy", "Livia", "Zunaira", "Jannat", "Ayra",
				"Anya", "Liara", "Katia", "Katja", "Tristan", "Billy", "Billie", "Kaitlin", "Grant", "Stan", "Stanley",
				"Mohamed", "Mohammed", "Lynne", "Camila", "Usnavi", "Miranda", "Rod", "Todd", "David", "Félix", "Felix",
				"Carmen", "Hector", "Héctor", "Gustavo", "Gustave", "Kermit", "Jeb", "Bob", "Bill" };
		String vApellidos[] = { "Bamberger", "Cleaver", "Centers", "Painter", "Snyder", "Podunk", "Falchuck",
				"Shackleford", "Hominy", "Chalk", "Chandler", "Pugh", "Prangburn", "Wheeler", "Cleveland", "Suggs",
				"Grainger", "Howells", "Fischer", "Fisher", "Parra", "Kline", "McGregor", "Whitmore", "Whitemore",
				"Hail", "Hurst", "Le", "Lee", "Britt", "Brett", "Friedman", "Lane", "Maynard", "Lucas", "Colon",
				"Gibbons", "Murphy", "Beck", "Mullen", "Muller", "Miller", "Mueller", "Müller", "Franklin", "Romero",
				"Stanley", "Kubrick", "Herman", "Hermann", "Mays", "Bonner", "Schroeder", "Lang", "Hays", "Chen",
				"Chee", "Buxton", "Hayden", "French", "Maxwell", "Glover", "Blair", "Lynn", "Hussain", "Hussein",
				"Kumar", "Driscoll", "Dyer", "Wall", "Massey", "Hulme", "Walker", "Brookes", "Brooke", "McNally",
				"Palacios", "Heaton", "Peel", "Penn", "Rollins", "McDonald", "Farley", "Flynn", "Wilkes", "Booth",
				"Booher", "Booker", "Waits", "Watts", "Hanks", "Miranda", "Jackson", "Johnson", "Berry", "Kerman" };

		return vNombres[r.nextInt(vNombres.length)] + " " + vApellidos[r.nextInt(vApellidos.length)];
	}

	private String generarDorsal() {
		Random r = new Random();

		return String.valueOf(r.nextInt(100));
	}

	public void arrancar() {
		enMarcha = true;
		accidentado = false;
	}

	public void acelerar() {
		Random r = new Random();

		if (!accidentado && isEnMarcha()) {
			velocidad += r.nextInt(POTENCIA);
			if (velocidad > 200) {
				kms += (((velocidad / 3.6f) * SEGUNDOSTURNO / 1000) / 3); // ((Conversión a m/s) * segundos que dura cada turno /
																// conversión a km)
				accidente();
			} else {
				kms += ((velocidad / 3.6f) * 10 / 1000); // ((Conversión a m/s) * 10 segundos cada turno / conversión a
															// km)
			}
		}

	}

	public void frenar() {
		Random r = new Random();

		velocidad -= r.nextInt(POTENCIA);

		if (velocidad < 0) {
			velocidad = 0;
		}
	}

	public void accidente() {
		enMarcha = false;
		velocidad = 0;
		accidentado = true;
	}

	public void estadoCoche() {
		String estado = "";

		if (accidentado) {
			estado = "  ACCIDENTADO   ";
		} else {
			if (!isEnMarcha()) {
				estado = "  NO ARRANCADO  ";
			} else {
				estado = "   EN CARRERA   ";
			}
		}

		System.out.println("╔═══════════╦══════════╦══════════╦════════════════╗");
		if (velocidad < 10) {
			System.out.println("║   " + velocidad + " km/h  ║  " + String.format("%.1f", kms) + " km  ║    --    ║"
					+ estado + "║");
		} else {
			if (velocidad > 99) {
				System.out.println("║ " + velocidad + " km/h  ║  " + String.format("%.1f", kms) + " km  ║    --    ║"
						+ estado + "║");
			} else {
				System.out.println("║  " + velocidad + " km/h  ║  " + String.format("%.1f", kms) + " km  ║    --    ║"
						+ estado + "║");
			}
		}
		System.out.println("╚═══════════╩══════════╩══════════╩════════════════╝");

	}

	public float getKms() {
		return kms;
	}

	public void setKms(float kms) {
		this.kms = kms;
	}

	public boolean isEnMarcha() {
		return enMarcha;
	}

	public void setMarcha(boolean marcha) {
		this.enMarcha = marcha;
	}

	public boolean isAccidentado() {
		return accidentado;
	}

	public void setAccidentado(boolean accidentado) {
		this.accidentado = accidentado;
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

	public String getPiloto() {
		return piloto;
	}

	public String getDorsal() {
		return dorsal;
	}

	public void setDorsal(String dorsal) {
		this.dorsal = dorsal;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public boolean isJugador() {
		return jugador;
	}

	public void setJugador(boolean jugador) {
		this.jugador = jugador;
	}

	@Override
	public String toString() {
		return "Coche " + dorsal + ": " + piloto + " | " + velocidad + " km/h | " + String.format("%.3f", kms)
				+ " km | En Marcha: " + enMarcha + " | Accidentado: " + accidentado + " | Terminado :" + terminado + " | " + posicion;
	}

}
