package runJrun;

public class Main {

	static Carrera carrera = new Carrera("", 5, 9);
	static Coche coche = new Coche("", true, "");

	static int ANCHOTOTAL = 120;
	static int anchoPista = ANCHOTOTAL - 40;
	static int POTENCIA = 50;
	static int SEGUNDOSTURNO = 10;
	static int velocidadMax = 200;
	static boolean modoEspectador = false;
	final static String version = "1.5.1";

	public static void main(String[] args) {
		int userInput = 0;

//	MENÚ PRINCIPAL
		do {
			velocidadMax = 200;
			carrera = new Carrera("", 5, 9);
			userInput = 0;
			String titulo = "Menú principal";
			String vOpciones[] = { "Carrera rápida (1 jugador)", "Carrera rápida (2 jugadores)", "Configurar carrera",
					"Ajustes", "Acerca de runJrun", "Salir" };

			userInput = Menu.genMenu(titulo, vOpciones);

			switch (userInput) {

//	MENÚ PRINCIPAL > CARRERA RÁPIDA (1 JUGADOR)
			case 1:
				carrera = new Carrera("", 5, 9);
				carrera.agregarCoche(new Coche("JUGADOR", true, "01"));
				carrera.comenzar();

				break;

//	MENÚ PRINCIPAL > CARRERA RÁPIDA (2 JUGADORES)
			case 2:
				carrera = new Carrera("", 5, 9);
				carrera.agregarCoche(new Coche("", true, "01"));
				carrera.agregarCoche(new Coche("", true, "02"));
				carrera.comenzar();
				break;

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA
			case 3:
				do {
					String tituloOpcion = "Nueva carrera";
					String vOpcionesCarrera[] = { "Añadir jugador", "Nombre de la carrera", "Número de participantes",
							"Distancia de la carrera", "Velocidad máxima", "Patrocinador de la carrera", "Dificultad",
							"Comenzar carrera", "Cancelar" };

					userInput = Menu.genMenu(tituloOpcion, vOpcionesCarrera);

					switch (userInput) {

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > AÑADIR UN JUGADOR
					case 1:
						do {
							titulo = "Añadir un jugador";
							String vOpcionesJugador[] = { "Nombre del jugador", "Número de dorsal", "Guardar",
									"Cancelar" };
							userInput = 0;

							userInput = Menu.genMenu(titulo, vOpcionesJugador);

							switch (userInput) {

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > AÑADIR UN JUGADOR > AJUSTE: NOMBRE JUGADOR
							case 1:
								coche.setPiloto(Menu.genMenuAjuste(("Nombre del jugador"),
										new String[] { ("Nombre del piloto controlado por el JUGADOR "
												+ (carrera.getNumJugadores() + 1) + ".") },
										"un nombre"));
								break;

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > AÑADIR UN JUGADOR > AJUSTE: DORSAL JUGADOR
							case 2:
								coche.setDorsal(Menu.genMenuAjuste(("Número de dorsal"),
										new String[] {
												("Número de dorsal para el JUGADOR " + (carrera.getNumJugadores() + 1)
														+ "."),
												"Se recomienda un máximo de 2 caracteres alfanuméricos",
												"(p. ej.: \"45\", \"AB\", \"4P\")" },
										"un código de dorsal"));
								break;

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > AÑADIR UN JUGADOR > GUARDAR JUGADOR
							case 3:
								if (coche.getDorsal().equalsIgnoreCase("")) {
									coche.setDorsal("01");
								}
								carrera.agregarCoche(coche);
								coche = new Coche("", true, "");
								userInput = 4;
								break;

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > AÑADIR UN JUGADOR > CANCELAR
							case 4:
								break;
							}
						} while (userInput != 4);
						break;

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > AJUSTE: NOMBRE DE LA CARRERA
					case 2:
						carrera.setNombre(String.valueOf(Menu.genMenuAjuste(vOpcionesCarrera[1],
								new String[] { "El nombre que recibirá la carrera.", }, "un nombre")));
						break;

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > AJUSTE: NÚMERO TOTAL DE COMPETIDORES
					case 3:
						carrera.setNumCompetidores(Menu.genMenuAjuste(vOpcionesCarrera[2], new String[] {
								"El número de coches que competirán en la carrera.",
								"Si el número total es mayor que el número de",
								"coches controlados por el jugador, se rellenarán",
								"los sitios libres automáticamente con competidores", "controlados por la máquina.",
								" ", "Ten en cuenta que una cantidad elevada de participantes",
								"puede incrementar el tiempo de carga de cada turno.", "  ",
								"[ Actual: " + carrera.getNumCompetidores() + " ]", "Mínimo: 1, máximo: 2000." }, 1,
								2000));
						break;

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > AJUSTE: LONGITUD DE LA CARRERA
					case 4:
						carrera.setLongitud(Menu.genMenuAjuste(vOpcionesCarrera[3],
								new String[] { "La longitud en kilómetros que tendrá la carrera.", "  ",
										"[ Actual: " + carrera.getLongitud() + " km. ]",
										"Mínimo: 0.1 km., máximo: 500 km. Valor por defecto: 5 km." },
								0.1f, 500f));
						break;
// MENÚ PRINCIPAL > CONFIGURAR CARRERA > AJUSTE: VELOCIDAD MÁXIMA
					case 5:
						velocidadMax = Menu.genMenuAjuste(vOpcionesCarrera[4],
								new String[] { "La velocidad máxima que puede alcanzar un",
										"vehículo sin sufrir un accidente o avería.", " ",
										"[ Actual: " + velocidadMax + " km/h ]", "Mínimo: 100 km/h, máximo: 500 km/h.",
										"Valor por defecto: 200 km/h." },
								100, 500);
						break;

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > AJUSTE: PATROCINADOR DE LA CARRERA
					case 6:
						carrera.setPatrocinador(Menu.genMenuAjuste(vOpcionesCarrera[5],
								new String[] { "La empresa o marca que patrocina la carrera." }, "un patrocinador"));
						break;

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > AJUSTE: DIFICULTAD
					case 7:
						carrera.setDificultad(Menu.genMenuAjuste(vOpcionesCarrera[6], new String[] { "1. MODO FÁCIL",
								"Los rivales pueden frenar incluso cuando no hay riesgo de",
								"accidente, y es probable que aceleren cuando sí que lo hay.", " ", "2. MODO CLÁSICO",
								"Los rivales no frenan si no existe riesgo de accidente.",
								"Cuando hay riesgo, eligen su acción aleatoriamente.",
								"Este es el nivel de dificultad por defecto.", " ", "3. MODO INTELIGENTE",
								"Los rivales escogen su acción de una forma parecida a cómo",
								"lo hace un humano, calculando la probabilidad de accidente.", " ",
								"4. MODO IMPRUDENTE", "Los rivales tienen una confianza excesiva en la seguridad de",
								"su coche. Es muy probable que se accidenten pero, si la",
								"suerte les acompaña, pueden sacar muchísima ventaja.", " " }, 1, 4));
						break;

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > COMENZAR CARRERA
					case 8:
						carrera.comenzar();
						carrera = new Carrera("", 5, 9);
						userInput = 9;
						break;

//	MENÚ PRINCIPAL > CONFIGURAR CARRERA > CANCELAR
					case 9:
						break;
					}
				} while (userInput != 9);
				break;

//	MENÚ PRINCIPAL > AJUSTES
			case 4:
				do {
					titulo = "Ajustes del juego";
					String vOpcionesAjustes[] = { "Configurar ancho del programa", "Cambiar la escala de tiempo",
							"Cambiar la potencia del coche", "Configurar modo espectador", "Volver al menú" };
					userInput = 0;
					userInput = Menu.genMenu(titulo, vOpcionesAjustes);

					switch (userInput) {

//	MENÚ PRINCIPAL > AJUSTES > ANCHO DEL PROGRAMA
					case 1:
						ANCHOTOTAL = Menu.genMenuAjuste(vOpcionesAjustes[0],
								new String[] { "El ancho máximo que tendrá la ventana de juego.", "  ",
										("[ Actual: " + ANCHOTOTAL + " ]"),
										"Mínimo: 80, máximo: 400. Valor por defecto: 120." },
								80, 400);
						break;

//	MENÚ PRINCIPAL > AJUSTES > ESCALA DE TIEMPO
					case 2:
						Main.SEGUNDOSTURNO = Menu.genMenuAjuste(vOpcionesAjustes[1],
								new String[] { "La duración en segundos de cada turno de juego.", "  ",
										("[ Actual: " + SEGUNDOSTURNO + " ]"),
										"Mínimo: 5, máximo: 30. Valor por defecto: 10.", },
								5, 30);
						break;

//	MENÚ PRINCIPAL > AJUSTES > POTENCIA
					case 3:
						Main.POTENCIA = Menu.genMenuAjuste(vOpcionesAjustes[2],
								new String[] { "La potencia que tendrán todos los coches del juego.",
										"Ten en cuenta que la distancia recorrida se calcula",
										"aleatoriamente en base a la potencia del motor, por lo",
										"que una mayor potencia implica una mayor probabilidad de",
										"accidente a altas velocidades.", " ", ("[ Actual: " + POTENCIA + " ]"),
										"Mínimo: 20, máximo: 80. Valor por defecto: 50" },
								20, 80);
						break;

// MENÚ PRINCIPAL > AJUSTES > CONFIGURAR MODO ESPECTADOR
					case 4:
						switch (Menu.genMenuAjuste(vOpcionesAjustes[3],
								new String[] { "Cuando el Modo Espectador está activo, las carreras que no cuenten",
										"con ningún jugador humano se mostrarán turno a turno, en lugar de ",
										"presentar el resultado directamente al usuario.", "",
										"1. ACTIVAR MODO ESPECTADOR   ", "2. DESACTIVAR MODO ESPECTADOR",

								}, 1, 2)) {
						case 1:
							Main.modoEspectador = true;
							break;
						case 2:
							Main.modoEspectador = false;
							break;
						}
						break;

//	MENÚ PRINCIPAL > AJUSTES > VOLVER AL MENÚ
					case 5:
						break;
					}
				} while (userInput != 5);
				break;

//	MENÚ PRINCIPAL > ACERCA DE RUNJRUN
			case 5:
				Menu.about();
				break;

//	MENÚ PRINCIPAL > SALIR
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
