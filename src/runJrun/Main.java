package runJrun;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner leer = new Scanner(System.in);
		int userInput=0;
		
		Coche c1 = new Coche();
		Coche c2 = new Coche();
		Coche c3 = new Coche();
		Coche c4 = new Coche();
		Coche jugador = new Coche("Tomás Generelo", "", true);
		
		Carrera carrera = new Carrera("I Gran Premio de Java", 6.5f, 5);
		
		carrera.agregarCoche(c1);
		carrera.agregarCoche(c2);
		carrera.agregarCoche(c3);
		carrera.agregarCoche(c4);
		carrera.agregarCoche(jugador);
		
		carrera.toString();
		
		
		do {
			if (jugador.isTerminado()==false) {
				jugador.estadoCoche();
				System.out.print("1-Arrancar  |  2-Acelerar  |  3-Frenar  |  > ");
				userInput=leer.nextInt();
				System.out.println();
				
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
			
			
			carrera.turnoCarrera();
			carrera.toString();
			System.out.println();
			
		} while (carrera.isTerminada()==false);
		
		carrera.imprimirOrdenLlegada();

		leer.close();
		
	}

}
