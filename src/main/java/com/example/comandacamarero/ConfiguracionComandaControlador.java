package com.example.comandacamarero;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Objects;

public class ConfiguracionComandaControlador {
    public RadioButton radioPizza;
    public RadioButton radioHamburguesa;
    public RadioButton radioPlatoCombinado;
    public ToggleGroup grupoComida;
    public RadioButton radioRefresco;
    public RadioButton radioAgua;
    public RadioButton radioZumo;
    public ToggleGroup grupoBebida;
    public TextArea areaTexto;
    public CheckBox descuento;
    public CheckBox tarjeta;
    public Label mesa;
    public Label comensal;
    @FXML
    private Stage escenario;
    private Comanda comanda;
    @FXML
    private Button botonCancelar;

    public void establecerConfiguracionComanda(Stage escenario, Comanda comanda) {
        this.escenario = escenario;
        this.comanda=comanda;
        mesa.setText("Nª Mesa: "+this.comanda.getNumeroMesa());
        if (!this.comanda.getPedidosComensales().isEmpty()) {
            String ultimoElemento = this.comanda.getPedidosComensales().getLast();
            comensal.setText(ultimoElemento);
        } else {
            System.out.println("La lista está vacía");
        }

    }

    public void resumenComanda(ActionEvent actionEvent) {

        RadioButton botonComida = (RadioButton) grupoComida.getSelectedToggle();
        RadioButton botonBebida = (RadioButton) grupoBebida.getSelectedToggle();

        if(botonComida!=null && botonBebida!=null){

            // Aniadimos los seleccionado al comensal
            String texto = this.comanda.getPedidosComensales().getLast()+"\n\tComida: "+botonComida.getText()+
                    "\n\tBebida: "+botonBebida.getText();

            if(descuento.isSelected()){
                texto = texto+"\n\tPago: "+descuento.getText();
            }else if(tarjeta.isSelected()){
                texto = texto+"\n\tPago: "+tarjeta.getText();
            }

            if(!areaTexto.getText().isEmpty()){
                texto = texto+"\n\tComentarios: "+areaTexto.getText().replaceAll("\\s+", " ");
            }

            this.comanda.getPedidosComensales().set(this.comanda.getPedidosComensales().size() - 1, texto);

            try {

                // Crear un TextFormatter para limitar la longitud a 30 caracteres
                int maxLength = 30;
                areaTexto.setTextFormatter(new TextFormatter<>(new StringConverter<String>() {
                    @Override
                    public String toString(String object) {
                        return object;
                    }

                    @Override
                    public String fromString(String string) {
                        if (string.length() > maxLength) {
                            return string.substring(0, maxLength);
                        }
                        return string;
                    }
                }));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("resumenComanda.fxml"));
                AnchorPane root = loader.load();

                ResumenComandaControlador controller = loader.getController();
                controller.establecerEscenaResumenComanda(this.escenario, this.comanda);

                Image image = new Image(Objects.requireNonNull(getClass().getResource("images/fotoLogin.jpg")).toExternalForm(), 600, 400, false, true);

                // Crear un fondo con la imagen
                BackgroundImage backgroundImage = new BackgroundImage(
                        image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT
                );

                // Crear un objeto Background y establecerlo en el VBox
                Background background = new Background(backgroundImage);
                root.setBackground(background);

                Scene nuevaEscena = new Scene(root, 600, 400);
                escenario.setTitle("Resumen Comanda");

                // Configurar el Stage con la nueva escena
                escenario.setScene(nuevaEscena);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            mostrarAlerta("Pedir Comida y Bebida", "Debes elegir la comida y la bebida.");

        }

    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    // Volver a escena Seleecion Comensal mediante el boton cancelar
    public void volverSeleccionComensal(ActionEvent actionEvent) {

        try {

            // Borro el ultimo elemento del ArrayList que sera el ultimo comensal seleccionado
            this.comanda.getPedidosComensales().removeLast();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("seleccionComensal.fxml"));
            AnchorPane root = loader.load();

            SeleccionComensal controller = loader.getController();
            controller.establecerEscenaSeleccionComensal(this.escenario, this.comanda);

            Image image = new Image(Objects.requireNonNull(getClass().getResource("images/fotoLogin.jpg")).toExternalForm(), 600, 400, false, true);

            // Crear un fondo con la imagen
            BackgroundImage backgroundImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            );

            // Crear un objeto Background y establecerlo en el VBox
            Background background = new Background(backgroundImage);
            root.setBackground(background);

            Scene nuevaEscena = new Scene(root, 600, 400);
            escenario.setTitle("Seleccion Comensal");

            // Configurar el Stage con la nueva escena
            escenario.setScene(nuevaEscena);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Metodo adicional que sirve para realizar operaciones auxilialres en los elementos del FXML
    public void initialize() {
        grupoComida = new ToggleGroup();
        grupoComida.getToggles().addAll(radioPizza, radioHamburguesa, radioPlatoCombinado);

        grupoBebida = new ToggleGroup();
        grupoBebida.getToggles().addAll(radioRefresco, radioAgua, radioZumo);

        descuento.setOnAction(event -> cambiarCheck(tarjeta));
        tarjeta.setOnAction(event -> cambiarCheck(descuento));

        // Transicion del boton Cancelar
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), botonCancelar);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);

        botonCancelar.setOnMouseEntered(event -> scaleTransition.play());
        botonCancelar.setOnMouseExited(event -> scaleTransition.pause());
    }

    private void cambiarCheck(CheckBox checkBox) {
        // Desmarcar el otro CheckBox cuando uno se selecciona
        if (checkBox.isSelected()) {
                checkBox.setSelected(false);
        }
    }

}
