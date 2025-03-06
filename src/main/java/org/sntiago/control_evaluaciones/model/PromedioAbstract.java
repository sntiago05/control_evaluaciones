package org.sntiago.control_evaluaciones.model;

public abstract class PromedioAbstract {
    protected double eva1, eva2, eva3, actitudinal;

    public PromedioAbstract() {
    }

    public PromedioAbstract(double eva1, double eva2, double eva3, double actitudinal) {
        this.eva1 = eva1;
        this.eva2 = eva2;
        this.eva3 = eva3;
        this.actitudinal = actitudinal;
    }

    public abstract double calcularPromedio();

    public double getEva1() {
        return eva1;
    }

    public void setEva1(double eva1) {
        this.eva1 = eva1;
    }

    public double getEva2() {
        return eva2;
    }

    public void setEva2(double eva2) {
        this.eva2 = eva2;
    }

    public double getEva3() {
        return eva3;
    }

    public void setEva3(double eva3) {
        this.eva3 = eva3;
    }

    public void setNotas(double eva1, double eva2, double eva3, double actitudinal) {
        this.setEva1(eva1);
        this.setEva2(eva2);
        this.setEva3(eva3);
        this.setActitudinal(actitudinal);
    }

    public double getActitudinal() {
        return actitudinal;
    }

    public void setActitudinal(double actitudinal) {
        this.actitudinal = actitudinal;
    }

    public Condicion aprobado() {
        return (calcularPromedio() >= 10) ? Condicion.APROBADO : Condicion.DESAPROBADO;
    }
}
