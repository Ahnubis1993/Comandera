package com.example.comandacamarero;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ResumenComandaControlador {
    public ListView listaComanda;
    public Label nombreCamarero;
    private Stage escenario;
    private Comanda comanda;

    public void establecerEscenaResumenComanda(Stage escenario, Comanda comanda) {
        this.escenario = escenario;
        this.comanda=comanda;
        this.nombreCamarero.setText("Camarero: "+this.comanda.getNombreCamarero());

        for (String comensal : this.comanda.getPedidosComensales()) {
            System.out.println("Comensal: " + comensal);
        }

        this.listaComanda.getItems().addAll(this.comanda.getPedidosComensales());
    }

    public void anadirComensal(ActionEvent actionEvent) {

        if(this.comanda.getPedidosComensales().size()<4){
            try {

                // Cargar el nuevo FXML
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
        }else{
            mostrarAlerta("Mesa Completo ", "Debes procesar la comanda, ya han pedido todos los comensales.");
        }

    }

    public void procesarComanda(ActionEvent actionEvent) {
        try {

            // Limpiamos el array de datos para empezar de nuevo
            this.comanda.getPedidosComensales().clear();

            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("seleccionMesaVista.fxml"));
            AnchorPane root = loader.load();
            colocarImagen(root);

            // Cargo todo en la siguiente clase
            SeleccionMesaControlador controller = loader.getController();
            controller.establecerEscenaSeleccionMesa(this.escenario, this.comanda);

            Scene nuevaEscena = new Scene(root, 600, 400);
            escenario.setTitle("Seleccion Mesa");

            // Configurar el Stage con la nueva escena
            escenario.setScene(nuevaEscena);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void colocarImagen(AnchorPane root){
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
    }
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    public void cancelarComanda(ActionEvent actionEvent) {
        try {

            // Limpiamos el array de datos para empezar de nuevo
            this.comanda.getPedidosComensales().clear();

            // Cargar el nuevo FXML
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

}
