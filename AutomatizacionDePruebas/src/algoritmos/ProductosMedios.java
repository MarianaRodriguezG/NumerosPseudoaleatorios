/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rogma
 */
public class ProductosMedios implements GeneradorNumPseudoaleatorios {

    private int semilla1 = 0;
    private int semilla2 = 0;

    public ProductosMedios() {
    }

    public ProductosMedios(int semilla1, int semilla2) {
        this.semilla1 = semilla1;
        this.semilla2 = semilla2;
    }

    public int getSemilla1() {
        return semilla1;
    }

    public void setSemilla1(int semilla1) {
        this.semilla1 = semilla1;
    }

    public int getSemilla2() {
        return semilla2;
    }

    public void setSemilla2(int semilla2) {
        this.semilla2 = semilla2;
    }

    @Override
    public List<Double> generar(int cantidad) {
        List<Double> resultados = new ArrayList<>();
        int x0 = semilla1;
        int x1 = semilla2;
        for (int i = 0; i < cantidad; i++) {
            int y = x0 * x1;
            String yStr = String.format("%08d", y);
            int inicio = (yStr.length() - 4) / 2;
            int x2 = Integer.parseInt(yStr.substring(inicio, inicio + 4));
            double ri = x2 / 10000.0;

            resultados.add(ri);
            //Los nums se van a desplazar para ir tomando el lugar del otro y poder realizar las operaciones
            x0=x1;
            x1=x2;

        }
        return resultados;
    }

    @Override
    public String toString() {
        return "ProductosMedios{" + "semilla1=" + semilla1 + ", semilla2=" + semilla2 + '}';
    }

}
