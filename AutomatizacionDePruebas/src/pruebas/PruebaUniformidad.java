/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import java.util.List;

/**
 *
 * @author Mari
 */
public class PruebaUniformidad implements PruebaEstadistica {

    @Override
    public String ejecutar(List<Double> numeros, double nivelConfianza) {
        int n = numeros.size();
        int k = 10; // número de intervalos

        // Encontrar mínimo y máximo
        double min = numeros.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double max = numeros.stream().mapToDouble(Double::doubleValue).max().orElse(1);

        double ancho = (max - min) / k;

        int[] frecuencias = new int[k];

        // Contar ocurrencias por intervalo [li, ls)
        for (double ri : numeros) {
            for (int i = 0; i < k; i++) {
                double li = min + i * ancho;
                double ls = li + ancho;
                if (ri >= li && ri < ls || (i == k - 1 && ri == max)) {
                    frecuencias[i]++;
                    break;
                }
            }
        }

        double fe = n / (double) k;
        double chiCuadrada = 0;
        for (int fo : frecuencias) {
            chiCuadrada += Math.pow(fo - fe, 2) / fe;
        }

        double chiCritico = obtenerChiCritico(nivelConfianza);

        return String.format("""
            Prueba de uniformidad (Chi cuadrada):
            Intervalos calculados entre %.4f y %.4f con ancho %.4f
            Chi cuad calculado: %.4f
            Chi cuad crítico (gl=%d, %.0f%%): %.4f
            Resultado: %s
            """, min, max, ancho, chiCuadrada, k - 1, nivelConfianza * 100, chiCritico,
                (chiCuadrada <= chiCritico)
                        ? "Pasa la prueba de uniformidad."
                        : "No pasa la prueba de uniformidad.");
    }

    private double obtenerChiCritico(double nivelConfianza) {
        int confianza = (int)(nivelConfianza * 100);
        double valor;

        switch (confianza) {
            case 90:
                valor = 14.684;
                break;
            case 95:
                valor = 16.919;
                break;
            case 99:
                valor = 21.666;
                break;
            default:
                valor = 16.919;
                break;
        }

        return valor;
    }
}
