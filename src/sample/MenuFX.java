package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuFX extends Application {

    private TextField textField;
    private TextField textField2;
    private Button buttonSubmit;
    private StackPane root;


    @Override
    public void init() {
        textField = new TextField();
        textField.setMaxWidth(200);
        textField.setTranslateY(30);
        textField.setPromptText("Entrez la dimension de la carte");

        textField2 = new TextField();
        textField2.setMaxWidth(200);
        textField2.setTranslateY(70);
        textField2.setPromptText("Entrez le nombre d'agent");

        buttonSubmit = new Button ("Valider");
        buttonSubmit.setTranslateX(0);
        buttonSubmit.setTranslateY(110);
        buttonSubmit.setMaxWidth(80);

        root = new StackPane();
        Image img = new Image("https://www.le-taquin.fr/site/wp-content/uploads/2017/07/logox2.png", 400, 100, false, false);

        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);
        root.getChildren().add(textField);
        root.getChildren().add(textField2);
        root.getChildren().add(buttonSubmit);

    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(root, 400, 400);


        buttonSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int board_size = Integer.parseInt(String.valueOf(textField.getCharacters()));
                int nbAgent = Integer.parseInt(String.valueOf(textField2.getCharacters()));
                EnvironnementFX environnementFX = new EnvironnementFX(board_size, nbAgent);
                try {
                    environnementFX.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                primaryStage.close();

            }});

        primaryStage.setTitle("Menu Taquin");

        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
