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
public class PruebaMedias implements PruebaEstadistica {

    @Override
    public String ejecutar(List<Double> numeros, double nivelConfianza) {
        int n = numeros.size();
        double suma = numeros.stream().mapToDouble(Double::doubleValue).sum();
        double promedio = suma / n;

        double z = calcularZ(nivelConfianza);
        double li = 0.5 - z * (1 / Math.sqrt(12 * n));
        double ls = 0.5 + z * (1 / Math.sqrt(12 * n));

        return String.format("""
            Prueba de medias:
            Promedio: %.4f
            Límite inferior: %.4f
            Límite superior: %.4f
            Resultado: %s
            """, promedio, li, ls,
                (promedio >= li && promedio <= ls)
                        ? "Pasa la prueba de medias."
                        : "No pasa la prueba de medias.");
    }

    private double calcularZ(double nivelConfianza) {
        int confianza = (int) (nivelConfianza * 100);
        double z;

        switch (confianza) {
            case 90:
                z = 1.64;
                break;
            case 95:
                z = 1.96;
                break;
            case 99:
                z = 2.58;
                break;
            default:
                z = 1.96; // Valor por defecto si no coincide
                break;
        }

        return z;
    }
}
