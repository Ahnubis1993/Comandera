package com.example.comandacamarero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

// Esta clase solo sirve para iniciar la app, la logica y demas me lo llevo a la clase LoginControlador
public class Login extends Application {
    @Override
    public void start(Stage escenario) throws IOException {

        // Me creo la Clase comanda solo aqui al comienzo de app, y la ire pasando por parametro entre clases
        // para que la informacion persista
        Comanda comanda = new Comanda("","");

        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("loginVista.fxml"));
        // Adquiero el panel padre del FXML para poner una imagen
        VBox root = fxmlLoader.load();
        colocarImagen(root);

        // Igualo controlador para el FXML y establezco el escenario. La clase LoginControlador tendra un
        // atributo Stage que igualare a este primero donde inicia la app, siendo el mismo para el resto de clases
        // Entonces adquiero el controlador del FXML para la implementar la logica en la clase LoginControlador
        // y paso como parametros el Escenario y la Comanda para que sean el mismo en todas las clases
        LoginControlador controller = fxmlLoader.getController();
        controller.establecerEscenaLogin(escenario, comanda);

        // La escena sera el FXML y la logica de esta primera escena estara en la clase LoginControlador
        Scene scene = new Scene(root, 600, 400);
        escenario.setTitle("Login");
        escenario.setScene(scene);
        escenario.show();
    }

    public void colocarImagen(VBox root){
        // Cargar la imagen directamente en tu controlador
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
    }

    public static void main(String[] args) {
        launch();
    }
}