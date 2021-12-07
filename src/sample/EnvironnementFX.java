package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class EnvironnementFX extends Application {

    private GridPane gameBoard = new GridPane();
    private StackPane root;
    private Environnement environnement;
    private int board_size;
    private int nbAgent;

    public EnvironnementFX(int board_size, int nbAgent) {
        this.board_size = board_size;
        this.nbAgent = nbAgent;
    }

    @Override
    public void init() {

//        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
//        System.out.println("Enter dimension of the game");
//
//        board_size = myObj.nextInt();  // Read user input
//
//        System.out.println("Enter the number of agents");
//
//        nbAgent = myObj.nextInt();

        environnement = new Environnement(board_size, board_size, nbAgent);
        gameBoard.setPrefSize(board_size*300, board_size * 300);

        root = new StackPane();
        gameBoard.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(gameBoard);

    }

    public void textureInit() {
        Object[][] map = environnement.getGrille();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {

                Rectangle tile = new Rectangle(80, 80);

                tile.setStroke(Color.BLACK);
                if (map[i][j] instanceof Agent) {
                    tile.setFill(Color.RED);
                    Text text = new Text();
                    text.setFont(Font.font(40));
                    gameBoard.add(new StackPane(tile, text), j, i);

                    text.setText(((Agent) map[i][j]).getNom());
                    text.setFill(Color.BLACK);
                } else {
                    tile.setFill(Color.POWDERBLUE);

                    gameBoard.add(tile, j, i);
                }
            }


        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init();
        Scene scene = new Scene(root, board_size * 80, board_size * 80);


        Thread thread = new Thread(() -> {
            Runnable updater = this::textureInit;

            while (!this.environnement.isFinished()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }

                Platform.runLater(updater);
            }
        });


        thread.setDaemon(true);
        thread.start();
        for (Agent agent :this.environnement.getAgents()) {
            agent.setEnvironnement(environnement);
            agent.start();
        }
        primaryStage.setTitle("Taquin Game");

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
