
import algoritmos.CuadradosMedios;
import algoritmos.GeneradorNumPseudoaleatorios;
import algoritmos.MultiplicadorConstante;
import algoritmos.ProductosMedios;
import java.util.List;
import java.util.Scanner;
import pruebas.PruebaEstadistica;
import pruebas.PruebaMedias;
import pruebas.PruebaUniformidad;
import pruebas.PruebaVarianza;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mari
 */
public class MainConsola {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Generador de Números Pseudoaleatorios ===");
        System.out.println("Seleccione un algoritmo:");
        System.out.println("1. Cuadrados Medios");
        System.out.println("2. Productos Medios");
        System.out.println("3. Multiplicador Constante");
        System.out.print("Opción: ");
        int opcion = sc.nextInt();

        GeneradorNumPseudoaleatorios generador = null;

        switch (opcion) {
            case 1 -> {
                System.out.print("Ingrese semilla : ");
                int semilla = sc.nextInt();
                generador = new CuadradosMedios(semilla);
            }
            case 2 -> {
                System.out.print("Ingrese semilla 1: ");
                int s1 = sc.nextInt();
                System.out.print("Ingrese semilla 2 : ");
                int s2 = sc.nextInt();
                generador = new ProductosMedios(s1, s2);
            }
            case 3 -> {
                System.out.print("Ingrese semilla: ");
                int semilla = sc.nextInt();
                System.out.print("Ingrese constante : ");
                int constante = sc.nextInt();
                generador = new MultiplicadorConstante(semilla, constante);
            }
            default -> {
                System.out.println("Opción no válida.");
                return;
            }
        }

        System.out.print("Cantidad de números a generar: ");
        int cantidad = sc.nextInt();

        List<Double> numeros = generador.generar(cantidad);

        System.out.println("\nNúmeros generados:");
        for (int i = 0; i < numeros.size(); i++) {
            System.out.printf("r%2d: %.4f%n", i + 1, numeros.get(i));
        }

        // Nivel de confianza
        System.out.print("\nIngrese nivel de confianza (90, 95 o 99): ");
        double nivel = sc.nextDouble() / 100.0;

        System.out.println("\n=== Resultados de las Pruebas ===\n");

        PruebaEstadistica medias = new PruebaMedias();
        PruebaEstadistica varianza = new PruebaVarianza();
        PruebaEstadistica uniformidad = new PruebaUniformidad();

        System.out.println(medias.ejecutar(numeros, nivel));
        System.out.println(varianza.ejecutar(numeros, nivel));
        System.out.println(uniformidad.ejecutar(numeros, nivel));
    }
}
