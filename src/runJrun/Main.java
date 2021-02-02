package runJrun;

public class Main {

	static Carrera carrera = new Carrera("", 5, 9);
	static Coche coche = new Coche("", true, "");

	static int ANCHOTOTAL = 120;
	static int anchoPista = ANCHOTOTAL - 40;
	static int POTENCIA = 50;
	static int SEGUNDOSTURNO = 10;

	public static void main(String[] args) {
		int userInput = 0;

		do {
			userInput = 0;
			String titulo = "Menú principal";
			String vOpciones[] = { "Carrera rápida (1 jugador)", "Carrera rápida (2 jugadores)", "Configurar carrera",
					"Ajustes", "Acerca de runJrun", "Salir" };

			userInput = Menu.genMenu(titulo, vOpciones);

			switch (userInput) {
			case 1:
				carrera = new Carrera("", 5, 9);
				carrera.agregarCoche(new Coche("JUGADOR", true, "01"));
				carrera.comenzar();
				break;
			case 2:
				carrera = new Carrera("", 5, 9);
				carrera.agregarCoche(new Coche("", true, "01"));
				carrera.agregarCoche(new Coche("", true, "02"));
				carrera.comenzar();
				break;
			case 3:
				do {
					String tituloOpcion = "Nueva carrera";
					String vOpcionesCarrera[] = { "Añadir jugador", "Nombre de la carrera", "Número de participantes",
							"Distancia de la carrera", "Patrocinador de la carrera", "Comenzar carrera", "Cancelar" };

					userInput = Menu.genMenu(tituloOpcion, vOpcionesCarrera);

					switch (userInput) {
					case 1:

						do {
							titulo = "Añadir un jugador";
							String vOpcionesJugador[] = { "Nombre del jugador", "Número de dorsal", "Guardar",
									"Cancelar" };
							userInput = 0;

							userInput = Menu.genMenu(titulo, vOpcionesJugador);

							switch (userInput) {
							case 1:
								coche.setPiloto(Menu.genMenuAjuste(("Nombre del jugador"),
										new String[] { ("Nombre del piloto controlado por el JUGADOR "
												+ (carrera.getNumJugadores() + 1) + ".") },
										"un nombre"));
								break;
							case 2:
								coche.setDorsal(Menu.genMenuAjuste(("Número de dorsal"),
										new String[] {
												("Número de dorsal para el JUGADOR " + (carrera.getNumJugadores() + 1)
														+ "."),
												"Se recomienda un máximo de 2 caracteres alfanuméricos",
												"(p. ej.: \"45\", \"AB\", \"4P\")" },
										"un código de dorsal"));
								break;
							case 3:
								carrera.agregarCoche(coche);
								coche = new Coche("", true, "-?!#");
								break;
							case 4:
								break;
							}
						} while (userInput != 3 && userInput != 4);
						break;
					case 2:
						carrera.setNombre(String.valueOf(Menu.genMenuAjuste(vOpciones[1],
								new String[] { "El nombre que recibirá la carrera.", }, "un nombre")));
						break;
					case 3:
						carrera.setNumCompetidores(Menu.genMenuAjuste(vOpciones[2],
								new String[] { "El número de coches que competirán en la carrera.",
										"Si el número total es mayor que el número de",
										"coches controlados por el jugador, se rellenarán",
										"los sitios libres automáticamente con competidores",
										"controlados por la máquina.", "  ", "Mínimo: 1, máximo: 200." },
								1, 200));
						break;
					case 4:
						carrera.setLongitud(
								Menu.genMenuAjuste(vOpciones[3],
										new String[] { "La longitud en kilómetros que tendrá la carrera.", "  ",
												"Mínimo: 0.1 km., Máximo: 300 km. Valor por defecto: 5 km" },
										0.1f, 300f));
						break;
					case 5:
						carrera.setPatrocinador(Menu.genMenuAjuste(vOpciones[4],
								new String[] { "La empresa o marca que patrocina la carrera." }, "un patrocinador"));
						break;
					case 6:
						carrera.comenzar();
						carrera = new Carrera("", 5, 9);
						break;
					case 7:
						break;
					}
				} while (userInput != 7);

				break;
			case 4:
				do {
					titulo = "Ajustes del juego";
					String vOpcionesAjustes[] = { "Configurar ancho del programa", "Cambiar la escala de tiempo",
							"Cambiar la potencia del coche", "Volver al menú" };
					userInput = 0;
					userInput = Menu.genMenu(titulo, vOpcionesAjustes);

					switch (userInput) {
					case 1:
						ANCHOTOTAL = Menu.genMenuAjuste(vOpcionesAjustes[0],
								new String[] { "El ancho máximo que tendrá la ventana de juego.", "  ",
										"Mínimo: 80, máximo: 400. Valor por defecto: 120." },
								80, 400);
						break;
					case 2:
						Main.SEGUNDOSTURNO = Menu.genMenuAjuste(vOpcionesAjustes[1],
								new String[] { "La duración en segundos de cada turno de juego.", "  ",
										"Mínimo: 5, máximo: 30. Valor por defecto: 10.", },
								5, 30);
						break;
					case 3:
						Main.POTENCIA = Menu.genMenuAjuste(vOpcionesAjustes[2],
								new String[] { "La potencia que tendrán todos los coches del juego.",
										"Ten en cuenta que la distancia recorrida se calcula",
										"aleatoriamente en base a la potencia del motor, por",
										"lo que una mayor potencia implica una mayor",
										"probabilidad de accidente a altas velocidades.", " ",
										"Mínimo: 20, máximo: 80. Valor por defecto: 50" },
								20, 80);
						break;
					case 4:
						break;
					}
				} while (userInput != 4);
				break;
			case 5:
				Menu.about();
				break;
			case 6:
				String mensaje = "Gracias por jugar a runJrun. El programa se cerrará.";
				System.out.println();
				for (int i = 0; i < (ANCHOTOTAL - mensaje.length()) / 2; i++) {
					System.out.print(" ");
				}
				System.out.println(mensaje);
				break;
			}
		} while (userInput != 6);
	}

}
