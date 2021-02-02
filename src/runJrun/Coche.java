package runJrun;

import java.util.Random;

/**
 * Un coche está compuesto por el nombre de su piloto y un código de dorsal.
 * Además, posee información sobre su estado actual, velocidad, kilómetros
 * recorridos, etc.
 * 
 * @author Tomás Generelo
 *
 */
public class Coche {
	private String piloto;
	private String dorsal;
	private int velocidad;
	private int velocidadMedia;
	private int posicion;
	private int turnoLlegada;
	private float kms;
	private float tiempo;
	private boolean enMarcha;
	private boolean accidentado;
	private boolean terminado;
	private boolean jugador;
	private int numJugador;

	/**
	 * Genera automáticamente todos los datos de inicio del coche, incluyendo un
	 * nombre del piloto y su dorsal al azar.
	 */
	public Coche() {
		this.piloto = generarNombre();
		this.dorsal = generarDorsal();
		this.velocidad = 0;
		this.velocidadMedia = 0;
		this.posicion = 0;
		this.turnoLlegada = 0;
		this.kms = 0;
		this.tiempo = 0;
		this.enMarcha = false;
		this.accidentado = false;
		this.terminado = false;
		this.jugador = false;
		this.numJugador = -1;
	}

	/**
	 * Genera un piloto personalizado que puede ser controlado por un jugador humano
	 * o por el programa.
	 * 
	 * @param piloto  El nombre del piloto.
	 * @param jugador true si el coche estará controlado por un jugador, false si
	 *                estará controlado por el programa.
	 * @param dorsal  El código del dorsal.
	 */
	public Coche(String piloto, boolean jugador, String dorsal) {
		this.piloto = piloto;
		this.dorsal = dorsal;
		this.velocidad = 0;
		this.velocidadMedia = 0;
		this.posicion = 0;
		this.turnoLlegada = 0;
		this.kms = 0;
		this.tiempo = 0;
		this.enMarcha = false;
		this.accidentado = false;
		this.terminado = false;
		this.jugador = jugador;
		this.numJugador = 0;
	}

	/**
	 * Genera un nombre aleatorio para un piloto.
	 * 
	 * @return Un nombre completo compuesto de nombre y apellido.
	 */
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
				"Carmen", "Hector", "Héctor", "Gustavo", "Gustave", "Kermit", "Jeb", "Bob", "Bill", "Kirk", "J.J.",
				"J.C.", "C.J.", "Bobby", "Bobbie", "Archibald", "Archibaldo", "Bernardo" };
		String vApellidos[] = { "Bamberger", "Cleaver", "Centers", "Painter", "Snyder", "Podunk", "Falchuck",
				"Shackleford", "Hominy", "Chalk", "Chandler", "Pugh", "Prangburn", "Wheeler", "Cleveland", "Suggs",
				"Grainger", "Howells", "Fischer", "Fisher", "Parra", "Kline", "McGregor", "Whitmore", "Whitemore",
				"Hail", "Hurst", "Le", "Lee", "Britt", "Brett", "Friedman", "Lane", "Maynard", "Lucas", "Colon",
				"Gibbons", "Murphy", "Beck", "Mullen", "Muller", "Miller", "Mueller", "Müller", "Franklin", "Romero",
				"Stanley", "Kubrick", "Herman", "Hermann", "Mays", "Bonner", "Schroeder", "Lang", "Hays", "Chen",
				"Chee", "Buxton", "Hayden", "French", "Maxwell", "Glover", "Blair", "Lynn", "Hussain", "Hussein",
				"Kumar", "Driscoll", "Dyer", "Wall", "Massey", "Hulme", "Walker", "Brookes", "Brooke", "McNally",
				"Palacios", "Heaton", "Peel", "Penn", "Rollins", "McDonald", "Farley", "Flynn", "Wilkes", "Booth",
				"Booher", "Booker", "Waits", "Watts", "Hanks", "Miranda", "Jackson", "Johnson", "Berry", "Kerman",
				"Draper", "Acevedo", "Russell", "Abrams", "McGee", "McGuffin", "Hitchcock", "Tate", "Bertolucci",
				"Da Silva", "Larson", "Larsson", "Black", "White", "Fernández", "Hernández", "Haddock" };
		return vNombres[r.nextInt(vNombres.length)] + " " + vApellidos[r.nextInt(vApellidos.length)];
	}

	/**
	 * Genera un código aleatorio de dorsal.
	 * 
	 * @return La representación en forma de String de un número entre 0 y 100. Este
	 *         método puede ser sobreescrito desde Carrera.
	 */
	public String generarDorsal() {
		Random r = new Random();
		return String.valueOf(r.nextInt(100));
	}

	/**
	 * Resetea el estado del coche a "enMarcha". El estado "accidentado" también se
	 * reinicia.
	 */
	public void arrancar() {
		enMarcha = true;
		accidentado = false;
	}

	/**
	 * La velocidad del coche se ve incrementada por un valor al azar entre 0 y
	 * POTENCIA. Si supera los 200 km/h el coche se accidentará y dejará de avanzar.
	 * En caso contrario, el coche avanzará la cantidad de metros correspondiente a
	 * los segundos que dura cada turno (definido en SEGUNDOSTURNO) a la velocidad
	 * actual.
	 */
	public void acelerar() {
		Random r = new Random();

		if (!accidentado && isEnMarcha()) {
			velocidad += r.nextInt(Main.POTENCIA);

			// El cálculo del avance se realiza mediante la siguiente fórmula:
			// ((Conversión a m/s) * segundos que dura cada turno / conversión a km)
			/*
			 * En caso de accidente (> 200 km/h), la distancia recorrida se divide entre 5
			 * como penalización.
			 */

			if (velocidad > 200) {
				kms += (((velocidad / 3.6f) * Main.SEGUNDOSTURNO / 1000) / 5);
				accidente();
			} else {
				kms += ((velocidad / 3.6f) * Main.SEGUNDOSTURNO / 1000);
			}
		}
	}

	/**
	 * La velocidad del coche se ve reducida por un valor al azar entre 0 y
	 * POTENCIA. El coche avanzará la cantidad de metros correspondiente a los
	 * segundos que dura cada turno (definido en SEGUNDOSTURNO) a la velocidad
	 * actual.
	 */
	public void frenar() {
		Random r = new Random();

		velocidad -= r.nextInt(Main.POTENCIA);

		if (velocidad < 0) {
			velocidad = 0;
		}

		kms += ((velocidad / 3.6f) * 10 / 1000);
	}

	/**
	 * Cambia el estado del coche a "accidentado" y resetea su velocidad a 0.
	 */
	public void accidente() {
		enMarcha = false;
		velocidad = 0;
		accidentado = true;
	}

	// GETTERS / SETTERS
	public int getNumJugador() {
		return numJugador;
	}

	public void setNumJugador(int numJugador) {
		this.numJugador = numJugador;
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

	public int getVelocidadMedia() {
		return velocidadMedia;
	}

	public void setVelocidadMedia(int velMedia) {
		velocidadMedia = velMedia;
	}

	public int getPosicion() {
		return posicion;
	}

	public int getPosicionFinal() {
		return turnoLlegada;
	}

	public void setPosicionFinal(int posicion) {
		this.turnoLlegada = posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public boolean isJugador() {
		return jugador;
	}

	public void setPiloto(String piloto) {
		this.piloto = piloto;
	}

	public void setJugador(boolean jugador) {
		this.jugador = jugador;
	}

	public float getTiempo() {
		return tiempo;
	}

	public void setTiempo(float tiempo) {
		this.tiempo = tiempo;
	}

	public int getTurnoLlegada() {
		return turnoLlegada;
	}

	public void setTurnoLlegada(int turnoLlegada) {
		this.turnoLlegada = turnoLlegada;
	}

	// toString
	@Override
	public String toString() {
		return "Coche " + dorsal + ": " + piloto + " | " + velocidad + " km/h | " + String.format("%.3f", kms)
				+ " km | En Marcha: " + enMarcha + " | Accidentado: " + accidentado + " | Terminado: " + terminado
				+ " | " + turnoLlegada + " | " + posicion + " | " + tiempo + " segundos";
	}

}
