
import java.util.Scanner;

public class JuegoMazmorra {

    // Guarda las dimensiones actuales del tablero.
    static int FILAS = 10;
    static int COLUMNAS = 10;

    // Define los valores iniciales y la dificultad del juego.
    static int vidasIniciales = 5;
    static int tamanoMapa = 10;
    static int dificultad = 2;

    static int cantidadTrampas = 10;
    static int cantidadEnemigos = 10;
    static int monedasCofre = 300;

    // Caracteres
    static final char PUNTO = '.';
    static final char JUGADOR = 'P';
    static final char TRAMPA = 'X';
    static final char ENEMIGO = 'E';

    static final char VIDA = 'V';
    static final char MONEDA = 'M';
    static final char LLAVE = 'L';
    static final char COFRE = 'C';
    static final char MERCADO = '$';

    // Tablero
    static char[][] tablero;

    // Posicion jugador
    static int filaJugador;
    static int colJugador;

    // Variables partida
    static int vidas;
    static int monedas;

    static boolean tieneLlave;
    static boolean cofreAbierto;
    static boolean victoria;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n=== LA MAZMORRA INFINITA ===");
            System.out.println("1. Nueva Partida");
            System.out.println("2. Configuracion");
            System.out.println("3. Instrucciones");
            System.out.println("4. Creditos");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = leerNumero();

            switch (opcion) {

                case 1:
                    iniciarPartidas();
                    break;

                case 2:
                    configuracion();
                    break;

                case 3:
                    instrucciones();
                    break;

                case 4:
                    creditos();
                    break;

                case 5:
                    salir = true;
                    System.out.println("Gracias por jugar.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }

        sc.close();
    }

    // Leer numeros y validar entradas
    static int leerNumero() {

        while (!sc.hasNextInt()) {
            System.out.println("Introduce un numero valido:");
            sc.nextLine();
        }

        int numero = sc.nextInt();
        sc.nextLine();

        return numero;
    }

    // MENU DE CONFIGURACION (Permite cambiar las vidas, el mapa y la dificultad.)
    static void configuracion() {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n=== CONFIGURACION ===");

            System.out.println("1. Vidas iniciales: " + vidasIniciales);
            System.out.println("2. Tamano del mapa: " + tamanoMapa + "x" + tamanoMapa);

            String nombreDificultad;

            if (dificultad == 1) {
                nombreDificultad = "Facil";
            } else if (dificultad == 2) {
                nombreDificultad = "Normal";
            } else {
                nombreDificultad = "Dificil";
            }

            System.out.println("3. Dificultad: " + nombreDificultad);
            System.out.println("4. Volver");
            System.out.print("Seleccione una opcion: ");

            int opcion = leerNumero();

            switch (opcion) {

                case 1:
                    System.out.println("\nElige las vidas iniciales:");
                    System.out.println("1. 5 vidas");
                    System.out.println("2. 6 vidas");
                    System.out.println("3. 7 vidas");

                    int vidasElegidas = leerNumero();

                    if (vidasElegidas >= 1 && vidasElegidas <= 3) {
                        vidasIniciales = vidasElegidas + 4;
                        System.out.println("Vidas actualizadas.");
                    } else {
                        System.out.println("Opcion no valida.");
                    }
                    break;

                case 2:
                    System.out.println("\nElige el tamano del mapa:");
                    System.out.println("1. 6x6");
                    System.out.println("2. 8x8");
                    System.out.println("3. 10x10");

                    int mapaElegido = leerNumero();

                    if (mapaElegido == 1) {
                        tamanoMapa = 6;
                    } else if (mapaElegido == 2) {
                        tamanoMapa = 8;
                    } else if (mapaElegido == 3) {
                        tamanoMapa = 10;
                    } else {
                        System.out.println("Opcion no valida.");
                    }
                    break;

                case 3:
                    System.out.println("\nElige la dificultad:");
                    System.out.println("1. Facil");
                    System.out.println("2. Normal");
                    System.out.println("3. Dificil");

                    int dificultadElegida = leerNumero();

                    if (dificultadElegida >= 1 &&
                        dificultadElegida <= 3) {

                        dificultad = dificultadElegida;

                        if (dificultad == 1) {
                            cantidadEnemigos = 5;
                            cantidadTrampas = 5;
                            monedasCofre = 500;

                        } else if (dificultad == 2) {
                            cantidadEnemigos = 10;
                            cantidadTrampas = 10;
                            monedasCofre = 300;

                        } else {
                            cantidadEnemigos = 15;
                            cantidadTrampas = 15;
                            monedasCofre = 200;
                        }

                        System.out.println("Dificultad actualizada.");

                    } else {
                        System.out.println("Opcion no valida.");
                    }
                    break;

                case 4:
                    volver = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    // Gestiona las partidas y permite volver a jugar.
    static void iniciarPartidas() {

        boolean volverAlMenu = false;

        while (!volverAlMenu) {

            iniciarPartida();

            System.out.println("\n=== FIN DE LA PARTIDA ===");

            if (victoria) {
                System.out.println("¡VICTORIA! Has completado la mazmorra.");

            } else if (vidas <= 0) {
                System.out.println("DERROTA. Te has quedado sin vidas.");

            } else {
                System.out.println("Has abandonado la partida.");
            }

            mostrarTableroFinal();

            System.out.println("\n¿Desea jugar otra vez?");
            System.out.println("1. Si");
            System.out.println("2. No");
            System.out.print("Seleccione una opcion: ");

            int opcion = leerNumero();

            if (opcion == 1) {
                System.out.println("\nIniciando nueva partida...");

            } else if (opcion == 2) {
                volverAlMenu = true;

            } else {
                System.out.println("Opcion no valida. Volviendo al menu principal.");
                volverAlMenu = true;
            }
        }
    }

    // Prepara el tablero y controla el desarrollo de la partida.
    static void iniciarPartida() {

        FILAS = tamanoMapa;
        COLUMNAS = tamanoMapa;

        vidas = vidasIniciales;
        monedas = 0;

        tieneLlave = false;
        cofreAbierto = false;
        victoria = false;

        tablero = new char[FILAS][COLUMNAS];

        inicializarTablero();
        colocarElementos();

        boolean salir = false;

        while (!salir && vidas > 0 && !victoria) {

            mostrarTablero();

            System.out.println("Mover: W S A D | Q (salir)");
            System.out.println("T = Abrir mercado");
            System.out.print("Opcion: ");

            String opcion = sc.nextLine().trim().toLowerCase();

            switch (opcion) {

                case "w":
                    mover(-1, 0);
                    break;

                case "s":
                    mover(1, 0);
                    break;

                case "a":
                    mover(0, -1);
                    break;

                case "d":
                    mover(0, 1);
                    break;

                case "t":
                    abrirMercado();
                    break;

                case "q":
                    salir = true;
                    break;

                default:
                    System.out.println("Movimiento no valido.");
            }
        }
    }

    // Rellena el tablero y coloca al jugador en el centro.
    static void inicializarTablero() {

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tablero[i][j] = PUNTO;
            }
        }

        filaJugador = FILAS / 2;
        colJugador = COLUMNAS / 2;

        tablero[filaJugador][colJugador] = JUGADOR;
    }

    // Coloca trampas, enemigos y objetos al azar.
    static void colocarElementos() {

        int trampasColocadas = 0;
        int enemigosColocados = 0;

        while (trampasColocadas < cantidadTrampas ||
               enemigosColocados < cantidadEnemigos) {

            int f = (int) (Math.random() * FILAS);
            int c = (int) (Math.random() * COLUMNAS);

            if (tablero[f][c] == PUNTO) {

                if (trampasColocadas < cantidadTrampas) {
                    tablero[f][c] = TRAMPA;
                    trampasColocadas++;

                } else {
                    tablero[f][c] = ENEMIGO;
                    enemigosColocados++;
                }
            }
        }

        colocarAleatorios(VIDA, 4);
        colocarAleatorios(MONEDA, 3);
        colocarAleatorios(LLAVE, 1);
        colocarAleatorios(COFRE, 1);
        colocarAleatorios(MERCADO, 1);
    }

    // Coloca un elemento en casillas vacías al azar.
    static void colocarAleatorios(char elemento, int cantidad) {

        int colocados = 0;

        while (colocados < cantidad) {

            int f = (int) (Math.random() * FILAS);
            int c = (int) (Math.random() * COLUMNAS);

            if (tablero[f][c] == PUNTO) {
                tablero[f][c] = elemento;
                colocados++;
            }
        }
    }

    // Muestra el tablero ocultando algunos elementos.
    static void mostrarTablero() {

        System.out.println("\n--- VIDAS: " + vidas +
                           " | MONEDAS: " + monedas + " ---");

        System.out.println("Llave: " +
                           (tieneLlave ? "SI" : "NO"));

        for (int i = 0; i < FILAS; i++) {

            for (int j = 0; j < COLUMNAS; j++) {

                if (tablero[i][j] == TRAMPA ||
                    tablero[i][j] == ENEMIGO ||
                    tablero[i][j] == VIDA ||
                    tablero[i][j] == MONEDA) {

                    System.out.print(PUNTO + " ");

                } else {
                    System.out.print(tablero[i][j] + " ");
                }
            }

            System.out.println();
        }
    }

    // MOSTRAR TABLERO FINAL
    static void mostrarTableroFinal() {

        System.out.println("\n--- TABLERO REVELADO ---");

        for (int i = 0; i < FILAS; i++) {

            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(tablero[i][j] + " ");
            }

            System.out.println();
        }
    }

    // Mueve al jugador y aplica el efecto de la casilla.
    static void mover(int deltaFila, int deltaCol) {

        int nuevaFila = filaJugador + deltaFila;
        int nuevaCol = colJugador + deltaCol;

        if (nuevaFila < 0 || nuevaFila >= FILAS ||
            nuevaCol < 0 || nuevaCol >= COLUMNAS) {

            System.out.println("No puedes salir del tablero.");
            return;
        }

        tablero[filaJugador][colJugador] = PUNTO;

        filaJugador = nuevaFila;
        colJugador = nuevaCol;

        if (tablero[filaJugador][colJugador] == TRAMPA) {

            vidas--;
            System.out.println("PISASTE UNA TRAMPA. Pierdes una vida.");

        } else if (tablero[filaJugador][colJugador] == ENEMIGO) {

            vidas -= 2;
            System.out.println("TE GOLPEO UN ENEMIGO. Pierdes dos vidas.");

        } else if (tablero[filaJugador][colJugador] == VIDA) {

            vidas++;
            System.out.println("¡Has encontrado una vida!");

        } else if (tablero[filaJugador][colJugador] == MONEDA) {

            monedas += 50;
            System.out.println("¡Has encontrado 50 monedas!");

        } else if (tablero[filaJugador][colJugador] == LLAVE) {

            tieneLlave = true;
            System.out.println("¡Has conseguido la llave!");

        } else if (tablero[filaJugador][colJugador] == COFRE) {

            if (tieneLlave && !cofreAbierto) {

                monedas += monedasCofre;
                vidas++;
                cofreAbierto = true;

                System.out.println("¡COFRE ABIERTO!");
                System.out.println("Has conseguido " +
                                   monedasCofre + " monedas y 1 vida.");

            } else if (!tieneLlave) {

                System.out.println("Necesitas una llave para abrir el cofre.");

            } else {

                System.out.println("El cofre ya esta abierto.");
            }
        }

        tablero[filaJugador][colJugador] = JUGADOR;
    }

    // MERCADO (Permite comprar vidas usando las monedas.)
    static void abrirMercado() {

        System.out.println("\n------ MERCADO ------");
        System.out.println("Monedas disponibles: " + monedas);
        System.out.println("1. Comprar 2 vidas - 250 monedas");
        System.out.println("2. Salir del mercado");
        System.out.print("Elige una opcion: ");

        int opcion = leerNumero();

        if (opcion == 1) {

            if (monedas >= 250) {

                monedas -= 250;
                vidas += 2;

                System.out.println("¡Compra realizada!");
                System.out.println("Has comprado 2 vidas.");

                victoria = true;

            } else {

                System.out.println("No tienes suficientes monedas.");
            }

        } else if (opcion == 2) {

            System.out.println("Has salido del mercado.");

        } else {

            System.out.println("Opcion no valida.");
        }
    }

    // INSTRUCCIONES (Muestra los controles y el objetivo del juego.)
    static void instrucciones() {

        System.out.println("\n=== INSTRUCCIONES ===");

        System.out.println("W: Mover hacia arriba.");
        System.out.println("S: Mover hacia abajo.");
        System.out.println("A: Mover hacia la izquierda.");
        System.out.println("D: Mover hacia la derecha.");
        System.out.println("T: Abrir el mercado.");
        System.out.println("Q: Abandonar la partida.");

        System.out.println("\nElementos del tablero:");
        System.out.println("P = Jugador");
        System.out.println("X = Trampa");
        System.out.println("E = Enemigo");
        System.out.println("V = Vida");
        System.out.println("M = Monedas");
        System.out.println("L = Llave");
        System.out.println("C = Cofre");
        System.out.println("$ = Mercado");

        System.out.println("\nNecesitas una llave para abrir el cofre.");
        System.out.println("El objetivo es llegar al mercado y comprar 2 vidas.");
    }

    // CREDITOS
    static void creditos() {

        System.out.println("\n=== CREDITOS ===");
        System.out.println("LA MAZMORRA INFINITA");
        System.out.println("Juego desarrollado en Java.");
        System.out.println("¡Gracias por jugar!");
    }
}


