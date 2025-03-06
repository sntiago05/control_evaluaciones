package org.sntiago.control_evaluaciones.model;

public class PromedioPregrado extends PromedioAbstract {
    public PromedioPregrado(double eva1, double eva2, double eva3, double actitudinal) {
        super(eva1, eva2, eva3, actitudinal);
    }

    public PromedioPregrado() {
    }

    @Override
    public double calcularPromedio() {
        return (eva1 * 0.15) + (eva2 * 0.20) + (eva3 * 0.25) + (actitudinal * 0.10);
    }
}
