package com.example.comandacamarero;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginControlador {
    public TextField usuarioTextoLogin;
    public PasswordField usuarioTextoContrasenia;
    @FXML
    private Stage escenario;
    private Comanda comanda;


    // Establecer la escena de la Clase Login (Se usara en la misma)
    public void establecerEscenaLogin(Stage escenario, Comanda comanda) {
        this.escenario = escenario;
        this.comanda=comanda;
    }

    // Si los campos usuario y contrasenia contienen algo deinformacion, carga la nueva, si no, aparece una alerta
    @FXML
    protected void iniciarSesion() {

        // Si el campos Usuario y Contraneia contienen informacion accedemos
        if(!usuarioTextoLogin.getText().isEmpty() && !usuarioTextoContrasenia.getText().isEmpty()){
            // Estbleaco el nombre del camarero en la clase Comanda
            this.comanda.setNombreCamarero(usuarioTextoLogin.getText());
            cargarNuevaEscena();
        }else{
            mostrarAlerta("Campos Vacíos", "Los campos de usuario y contraseña deben estar rellenos.");
        }

    }

    // Alerta a mostrar
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    // Escena SeleccionMesa que se carga cuando se pulsa en el Boton "Iniciar Sesion"
    // Esta enlazado en el FXML onAction y llamnando al nombre del metodo IniciarSeion de arriba
    private void cargarNuevaEscena(){
        try {
            // Cargar el nuevo FXM
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

}