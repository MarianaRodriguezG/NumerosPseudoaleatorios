/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author Mari
 */
import algoritmos.GeneradorNumPseudoaleatorios;
import pruebas.PruebaEstadistica;

import java.util.ArrayList;
import java.util.List;

public class ControladorSimulacion {
    private GeneradorNumPseudoaleatorios generador;
    private List<PruebaEstadistica> pruebas;
    private double nivelConfianza;
    private int cantidad;

    public ControladorSimulacion() {
        pruebas = new ArrayList<>();
    }

    public void setGenerador(GeneradorNumPseudoaleatorios generador) {
        this.generador = generador;
    }

    public void setPruebas(List<PruebaEstadistica> pruebas) {
        this.pruebas = pruebas;
    }

    public void setNivelConfianza(double nivelConfianza) {
        this.nivelConfianza = nivelConfianza;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String ejecutarSimulacion() {
        if (generador == null || pruebas == null || cantidad <= 0) {
            return "Error: Falta configurar el generador, pruebas o cantidad válida.";
        }

        List<Double> numeros = generador.generar(cantidad);
        StringBuilder resultado = new StringBuilder();

        resultado.append("Resultados de la simulación\n");
        resultado.append("Números generados:\n");

        for (int i = 0; i < numeros.size(); i++) {
            resultado.append(String.format("%2d: %.4f%n", i + 1, numeros.get(i)));
        }

        resultado.append("\n🔍 Resultados de pruebas estadísticas:\n");

        for (PruebaEstadistica prueba : pruebas) {
            resultado.append(prueba.ejecutar(numeros, nivelConfianza)).append("\n");
        }

        return resultado.toString();
    }
}