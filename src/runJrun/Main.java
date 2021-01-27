package runJrun;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner leer = new Scanner(System.in);
		int userInput=0;
		boolean accionValida=true;
		
		Coche c1 = new Coche();
		Coche c2 = new Coche();
		Coche c3 = new Coche();
		Coche c4 = new Coche();
//		Coche c5 = new Coche();
//		Coche c6 = new Coche();
//		Coche c7 = new Coche();
//		Coche c8 = new Coche();
		Coche jugador = new Coche("Tomás Generelo", true);
		
		Carrera carrera = new Carrera("I Gran Premio de Java", 2f, 5);
		
		carrera.agregarCoche(c1);
		carrera.agregarCoche(c2);
		carrera.agregarCoche(c3);
		carrera.agregarCoche(c4);
		carrera.agregarCoche(jugador);	
		
		do {
			if (jugador.isEnMarcha() || carrera.getvOrdenLlegada()[0]==null) {

				do {
					carrera.toString();
					jugador.estadoCoche();
					if (jugador.isEnMarcha()) {
						System.out.print(" 2-Acelerar  |  3-Frenar  |  > ");
					} else {
						System.out.print(" 1-Arrancar  |  > ");
					}
					do {
						try {
							userInput=leer.nextInt();
						} catch (InputMismatchException e) {
							System.out.println("** ERROR: Opción no válida **");
							accionValida=false;
							leer=new Scanner(System.in);
						}
						accionValida=true;
					} while (accionValida==false);
					
					System.out.println();
					
					if (userInput==1 && jugador.isEnMarcha()) {
						accionValida=false;
					} else {
						accionValida=true;
						if ((userInput<1 || userInput>3) || ((userInput==2 || userInput==3) && !jugador.isEnMarcha())) {
							accionValida=false;
						} else {
							accionValida=true;
						}
					}
					
					if (accionValida==false)
						System.out.println("** ERROR: Acción no válida **");
					
				} while (accionValida==false);
				
				
				
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
			
		} while (carrera.isTerminada()==false);
		System.out.println("CLASIFICACIÓN:");
		carrera.imprimirClasificacion();

		leer.close();
		
	}

}
