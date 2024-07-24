package com.example.comandacamarero;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SeleccionMesaControlador {
    @FXML
    private Stage escenario;
    private Comanda comanda;

    public void establecerEscenaSeleccionMesa(Stage escenario, Comanda comanda) {
        this.escenario = escenario;
        this.comanda=comanda;
    }

    public void seleccionMesa(MouseEvent event) {
        // Obtener el rectángulo que ha sido clicado
        StackPane stackPaneClicado = (StackPane) event.getSource();

        // Obtén todos los hijos del StackPane
        ObservableList<Node> hijosStackPane = stackPaneClicado.getChildren();

        // Itera sobre los hijos para encontrar el Text
        for (Node hijo : hijosStackPane) {
            if (hijo instanceof Text) {
                // Se ha encontrado un nodo de tipo Text
                Text textoMesa = (Text) hijo;

                // Obtén el contenido del Text
                String contenidoTextoMesa = textoMesa.getText();
                this.comanda.setNumeroMesa(contenidoTextoMesa);
            }
        }

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
    }
}
