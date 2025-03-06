package org.sntiago.control_evaluaciones.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.sntiago.control_evaluaciones.model.Alumno;
import org.sntiago.control_evaluaciones.model.Condicion;

import java.util.ArrayList;
import java.util.List;


public class PrincipalController {
    @FXML
    private TextField txtEva1, txtEva2, txtEva3, txtAct, txtEstudiante;

    @FXML
    private TextArea areaEstadistica;

    @FXML
    private TableView<Alumno> tableNotas;

    @FXML
    private TableColumn<Alumno, String> colEstudiante;
    @FXML
    private TableColumn<Alumno, Double> colEva1, colEva2, colEva3, colAct, colPromedio;
    @FXML
    private TableColumn<Alumno, Condicion> colCondicion;


    @FXML
    private Button btnNuevoDato, btnGuardar;

    private List<Alumno> alumnos;

    @FXML
    public void initialize() {
        alumnos = new ArrayList<>();

        configurarCeldas();


        tableNotas.setItems(FXCollections.observableArrayList(alumnos));

        restringirEntrada(txtEva1);
        restringirEntrada(txtEva2);
        restringirEntrada(txtEva3);
        restringirEntrada(txtAct);
        restringirNombreEstudiante(txtEstudiante);
    }

    private void configurarCeldas() {
        colCondicion.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().aprobado()));
        colEstudiante.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));

        colEva1.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPromedio().getEva1()).asObject());
        configurarColumnaDecimal(colEva1);

        colEva2.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPromedio().getEva2()).asObject());
        configurarColumnaDecimal(colEva2);

        colEva3.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPromedio().getEva3()).asObject());
        configurarColumnaDecimal(colEva3);

        colAct.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPromedio().getActitudinal()).asObject());
        configurarColumnaDecimal(colAct);

        colPromedio.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().calcularPromedio()).asObject());
        configurarColumnaDecimal(colPromedio);
    }

    @FXML
    protected void nuevoDato() {
        txtEva1.setText("");
        txtEva2.setText("");
        txtEva3.setText("");
        txtAct.setText("");
        txtEstudiante.setText("");
    }

    @FXML
    protected void guardar() {
        Alert alerta;
        if (camposVacios()) {
            alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos Vacios");
            alerta.setHeaderText("Los campos no pueden estar vacios");
            alerta.show();
        } else {
            alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Seleccionar Tipo de Alumno");
            alerta.setHeaderText("Por favor, seleccione un tipo de alumno:");

            ComboBox<Alumno.TipoAlumno> comboBox = new ComboBox<>();
            comboBox.getItems().addAll(Alumno.TipoAlumno.values());
            comboBox.setValue(Alumno.TipoAlumno.REGULAR);
            alerta.getDialogPane().setContent(comboBox);
            alerta.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            alerta.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Alumno.TipoAlumno seleccion = comboBox.getValue();
                    Alumno alumno = new Alumno(txtEstudiante.getText(), seleccion);
                    alumno.getPromedio().setNotas(parsearNota(txtEva1.getText())
                            , parsearNota(txtEva2.getText()),
                            parsearNota(txtEva3.getText()),
                            parsearNota(txtAct.getText()));
                    alumnos.add(alumno);

                    tableNotas.setItems(FXCollections.observableArrayList(alumnos));
                    tableNotas.refresh();
                    areaEstadistica.setText(calcularEstadisticas());
                }
            });
        }


    }

    private double parsearNota(String nota) {
        return Double.parseDouble(nota);
    }

    private void restringirNombreEstudiante(TextField campo) {
        campo.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                campo.setText(oldValue);
            }
        });
    }

    private void restringirEntrada(TextField campo) {
        campo.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                campo.setText(oldValue);
            }
        });
    }

    private double sumarPromedios() {
        return alumnos.stream().mapToDouble(Alumno::calcularPromedio).sum();
    }

    private double mayorPromedio() {
        return alumnos.stream().mapToDouble(Alumno::calcularPromedio).max().getAsDouble();
    }

    private double menorPromedio() {
        return alumnos.stream().mapToDouble(Alumno::calcularPromedio).min().getAsDouble();
    }

    private Long totalAprobados() {
        return alumnos.stream().map(Alumno::aprobado).filter(a -> a.equals(Condicion.APROBADO)).count();
    }

    private Long totalDesAprobados() {
        return alumnos.stream().map(Alumno::aprobado).filter(a -> a.equals(Condicion.DESAPROBADO)).count();
    }

    private String calcularEstadisticas() {
        return String.format("""
                Suma de promedios: %.2f
                Promedio mas alto: %.2f
                Promedio mas bajo: %.2f
                Total de aprobados: %d
                Total de desaprobados: %d
                """, sumarPromedios(), mayorPromedio(), menorPromedio(), totalAprobados(), totalDesAprobados());
    }

    private void configurarColumnaDecimal(TableColumn<Alumno, Double> columna) {
        columna.setCellFactory(column -> new TableCell<Alumno, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : String.format("%.2f", item));
            }
        });
    }

    private boolean camposVacios() {
        return txtEva1.getText().isBlank()
               || txtEva2.getText().isBlank()
               || txtEva3.getText().isBlank()
               || txtAct.getText().isBlank()
               || txtEstudiante.getText().isBlank();
    }
}