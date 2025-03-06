package org.sntiago.control_evaluaciones.model;

public enum Condicion {
    APROBADO("Aprobado"),
    DESAPROBADO("Desaprobado");
    private String descripcion;

    Condicion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
      return descripcion;
    }
}
