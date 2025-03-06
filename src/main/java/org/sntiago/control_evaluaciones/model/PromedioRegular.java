package org.sntiago.control_evaluaciones.model;

public class PromedioRegular extends PromedioAbstract {
    public PromedioRegular(double eva1, double eva2, double eva3, double actitudinal) {
        super(eva1, eva2, eva3, actitudinal);
    }
    public PromedioRegular() {

    }
    @Override
    public double calcularPromedio() {
        return (eva1 + eva2 + eva3 + actitudinal) / 4;
    }
}
