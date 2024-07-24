package com.example.comandacamarero;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SeleccionComensal {

    public Label etiquetaMesa;
    @FXML
    private Stage escenario;
    @FXML
    private ListView listaComensales;
    private Comanda comanda;

    public void establecerEscenaSeleccionComensal(Stage escenario, Comanda comanda) {
        this.escenario = escenario;
        this.comanda=comanda;
        etiquetaMesa.setText(String.valueOf("Nª Mesa: "+this.comanda.getNumeroMesa()));

        // Creo lista de los 4 comensales que siempre habra en cada mesa y los establezco como elementos en la ListView
        ObservableList<String> items = FXCollections.observableArrayList("Comensal 1", "Comensal 2", "Comensal 3", "Comensal 4");
        listaComensales.setItems(items);

        // Actualizo los elementos que contiene el ArrayList
        listaComensales.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setDisable(false);
                    setStyle(""); // Restablece el estilo por defecto
                } else {
                    setText(item);

                    for (String pedido : comanda.getPedidosComensales()) {
                        if (pedido.contains(item)) {
                            setDisable(true);
                            setStyle("-fx-opacity: 0.5;");  // Cambia el estilo para indicar que está bloqueado
                            return;  // Salir del bucle si se encuentra una coincidencia
                        }
                    }

                    // Restablecer el estilo si no se encuentra el elemento en la lista
                    setDisable(false);
                    setStyle("");
                }
            }
        });
    }

    public void elegirComensal(){

        String comensalSeleccionado = (String) listaComensales.getSelectionModel().getSelectedItem();

        if (comensalSeleccionado != null) {

            // Aniado el comensal si pasamos ala siguiente escena
            this.comanda.agregarPedido(comensalSeleccionado);

            // Realizar acciones según el comensal seleccionado
            try {
                // Cargar el nuevo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("configuracionComanda.fxml"));
                AnchorPane root = loader.load();

                ConfiguracionComandaControlador controller = loader.getController();
                controller.establecerConfiguracionComanda(this.escenario, this.comanda);

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
                escenario.setTitle("Configuracion Comanda");

                // Configurar el Stage con la nueva escena
                escenario.setScene(nuevaEscena);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            mostrarAlerta("Seleccion Comensal", "Debes seleccionar un comensal.");
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    public void volverEleccionMesa(ActionEvent actionEvent) {

        try {

            // Limpio el arraylist si volvemos atras
            this.comanda.getPedidosComensales().clear();

            // Carga el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("seleccionMesaVista.fxml"));
            AnchorPane root = loader.load();

            SeleccionMesaControlador controller = loader.getController();
            controller.establecerEscenaSeleccionMesa(this.escenario, this.comanda);

            Image image = new Image(Objects.requireNonNull(getClass().getResource("images/seleccionComanda.jpg")).toExternalForm(), 600, 400, false, true);

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

            // Configurar la nueva escena
            Scene nuevaEscena = new Scene(root, 600, 400);
            escenario.setTitle("Seleccion Mesa");

            // Configurar el Stage con la nueva escena
            escenario.setScene(nuevaEscena);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
