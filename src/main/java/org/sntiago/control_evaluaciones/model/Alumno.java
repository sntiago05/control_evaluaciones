package org.sntiago.control_evaluaciones.model;

public class Alumno {
    public enum TipoAlumno {REGULAR, PREGRADO}

    private String nombre;
    private TipoAlumno tipo;
    private PromedioAbstract promedio;

    public Alumno(String nombre, TipoAlumno tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        if (tipo.equals(TipoAlumno.REGULAR)) {
            this.promedio = new PromedioRegular();
        } else {
            this.promedio = new PromedioPregrado();
        }
    }

    public double calcularPromedio() {
        return this.promedio.calcularPromedio();
    }

    public Condicion aprobado() {
        return this.promedio.aprobado();
    }

    public String getNombre() {
        return nombre;
    }

    public PromedioAbstract getPromedio() {
        return promedio;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
