package com.example;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        List<String> args = getParameters().getRaw();
        if (args.isEmpty()) {
            System.out.println("Brak argumentu");
            Platform.exit();
            return;
        }
        int n = 0;
        try {
            n = Integer.parseInt(args.get(0));
            if (n < 0) {
                System.out.println(args.get(0) + " - Nieprawidłowy zakres");
                Platform.exit();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(args.get(0) + " - nieprawidłowa dana");
            Platform.exit();
            return;
        }
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: BLACK; -fx-padding: 20px;");
        for (int i = 0; i < n; i++) {
            PascalTriangleFX pascal = new PascalTriangleFX(i);
            Label rowLabel = new Label(pascal.toString());
            rowLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 18));
            rowLabel.setTextFill(Color.WHITE);
            root.getChildren().add(rowLabel);
        }
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Trójkąt Pascala");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene(); 
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}