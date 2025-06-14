/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mari
 */
public class MultiplicadorConstante implements GeneradorNumPseudoaleatorios {

    private int semilla;
    private int constante;

    public MultiplicadorConstante() {
    }

    public MultiplicadorConstante(int semilla, int constante) {
        this.semilla = semilla;
        this.constante = constante;
    }

    public int getSemilla() {
        return semilla;
    }

    public void setSemilla(int semilla) {
        this.semilla = semilla;
    }

    public int getConstante() {
        return constante;
    }

    public void setConstante(int constante) {
        this.constante = constante;
    }

    @Override
    public List<Double> generar(int cantidad) {
         List<Double> resultados = new ArrayList<>();
        int x = semilla;

        for (int i = 0; i < cantidad; i++) {
            int y = constante * x;
            String yStr = String.format("%08d", y);
            int inicio = (yStr.length() - 4) / 2;
            int xSiguiente = Integer.parseInt(yStr.substring(inicio, inicio + 4));
            double ri = xSiguiente / 10000.0;
            resultados.add(ri);
            x = xSiguiente;
        }

        return resultados;
    }

    @Override
    public String toString() {
        return "MultiplicadorConstante{" + "semilla=" + semilla + ", constante=" + constante + '}';
    }

}
