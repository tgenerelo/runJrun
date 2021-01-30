package runJrun;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner leer = new Scanner(System.in);
		
		Carrera carrera = new Carrera("I Gran Premio de Java", 5f, 12);
		
		carrera.agregarCoche(new Coche("Tomás Generelo", true, "17"));
		
		carrera.prepararCarrera();

		do {
			carrera.turnoCarrera();
		} while (carrera.isTerminada()==false);

		carrera.imprimirClasificacion();

		leer.close();
		
	}

}
