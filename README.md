# runJrun 1.1 #
### Descripción ###
runJrun es un juego de carreras por turnos que puede ser jugado en la consola de Eclipse.

### Características ###
- Representación gráfica del estado de la carrera en cada turno.

#### Carrera rápida ####
Se genera automáticamente una carrera para uno o dos jugadores, con una longitud de 5 km y un total de 9 competidores.

#### Carreras personalizables ####
- Número de jugadores. Cada jugador puede personalizar su nombre y el dorsal que lucirá durante la carrera.
- Número total de competidores. Todos los huecos libres cuando comience la carrera se rellenarán automáticamente con coches controlados por el programa.
- Longitud de la carrera en kilómetros. La longitud por defecto son 5 km.
- Nombre de la competición. Si no se especifica, se generará aleatoriamente.
- Nombre del patrocinador de la carrera. Si no se especifica, se generará aleatoriamente.

#### Ajustes configurables ####
- Ancho del programa: define el ancho (expresado en número de caracteres) que ocupará el programa. Afecta a la representación gráfica de la carrera y a la posición de los menús, que aparecen centrados.
- Escala de tiempo: Por defecto, cada turno de juego se corresponde con una duración de 10 segundos, pero es posible modificar este valor.
- Potencia de los coches: El avance de cada coche se determina mediante un número al azar que depende de su potencia. La potencia por defecto de todos los coches es 50, pero es posible modificar este valor. Nota: NO es posible modificar la potencia de un coche en particular.
