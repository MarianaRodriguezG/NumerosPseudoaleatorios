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
public class PruebaVarianza implements PruebaEstadistica {

    @Override
    public String ejecutar(List<Double> numeros, double nivelConfianza) {
        int n = numeros.size();
        double promedio = numeros.stream().mapToDouble(Double::doubleValue).sum() / n;

        // Calcular varianza muestral
        double sumaCuadrados = 0;
        for (double ri : numeros) {
            sumaCuadrados += Math.pow(ri - promedio, 2);
        }
        double varianza = sumaCuadrados / (n - 1);

        double z = calcularZ(nivelConfianza);
        double esperada = 1.0 / 12.0;
        double margen = z * Math.sqrt(1.0 / (180.0 * n));
        double li = esperada - margen;
        double ls = esperada + margen;

        return String.format("""
            Prueba de varianza:
            Varianza muestral: %.4f
            Límite inferior: %.4f
            Límite superior: %.4f
            Resultado: %s
            """, varianza, li, ls,
                (varianza >= li && varianza <= ls)
                        ? "Pasa la prueba de varianza."
                        : "No pasa la prueba de varianza.");
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
                z = 1.96;
                break;
        }

        return z;
    }
}


