package runJrun;

public class Main {
	
	static int ANCHOTOTAL = 120;
	static int POTENCIA = 50;
	static int SEGUNDOSTURNO = 10;
	static Carrera carrera = new Carrera("", 5, 9);

	public static void main(String[] args) {
		
		carrera = new Carrera("", 5, 9);
		
		Menu.menuPrincipal();
		
		
//		Carrera carrera = new Carrera("I Gran Premio de Java", 5f, 10);
//		
//		carrera.agregarCoche(new Coche("Tomás Generelo", true, "17"));
//		
//		carrera.prepararCarrera();
//
//		do {
//			carrera.turnoCarrera();
//		} while (carrera.isTerminada()==false);
//
//		carrera.imprimirClasificacion();
		
	}

}
