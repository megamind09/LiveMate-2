package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ChooseBachelor {
    Stage primaryStage;
    Scene chooseBatchScene, roomScene, roomMatesScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setChooseBatchScene(Scene chooseBatchScene) {
        this.chooseBatchScene = chooseBatchScene;
    }

    public Parent createScene(Runnable chooseBatchThread) {

        Button backBtn = new Button(" ← ");
        backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-background-radius: 10;");
        backBtn.setOnAction(e -> chooseBatchThread.run());

        Text mateName = new Text("Live mate");
        mateName.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        mateName.setFill(Color.BLACK);

        HBox topBar = new HBox(20, backBtn, mateName);
        topBar.setPadding(new Insets(20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setBackground(new Background(new BackgroundFill(Color.web("#494CA2"), CornerRadii.EMPTY, Insets.EMPTY)));

        Button ownerBtn = new Button("Need Roommates");

        ownerBtn.setOnAction(e ->{
            initializeRoomMates();
            primaryStage.setScene(roomMatesScene);
            
        });
        Button bachelorBtn = new Button("Need Rooms");

        bachelorBtn.setOnAction(e -> {
            initializeRooms(); // creates roomScene with back handling
            primaryStage.setScene(roomScene);
        });

        String buttonStyle = "-fx-background-color: #C6CBFF; -fx-text-fill: BLACK; -fx-font-size: 16px; " +
                "-fx-background-radius: 30; -fx-padding: 15 40;";

        ownerBtn.setStyle(buttonStyle);
        bachelorBtn.setStyle(buttonStyle);

        ownerBtn.setEffect(new DropShadow());
        bachelorBtn.setEffect(new DropShadow());

        HBox buttonsBox = new HBox(50, ownerBtn, bachelorBtn);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(60, 20, 20, 20));

        VBox root = new VBox(50, topBar, buttonsBox);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #8186D5, #8186D5);");

        return root;
    }

    private void initializeRoomMates() {
        
        Roommate  roomMate = new Roommate();
        roomMate.setPrimaryStage(primaryStage);
        primaryStage.setFullScreen(true);
        Rectangle2D screen =Screen.getPrimary().getVisualBounds();
        roomMatesScene = new Scene(roomMate.createRoomMateScene(()->handleBack()),screen.getWidth(),screen.getHeight());
        roomMate.setRoomMatesScene(roomMatesScene);
        
    }
    

    public void initializeRooms() {
        Rooms rooms = new Rooms();
        rooms.setPrimaryStage(primaryStage);
        Rectangle2D screen =Screen.getPrimary().getVisualBounds();
        roomScene = new Scene(
                rooms.createRoomScene(()->handleBack()),screen.getWidth(),screen.getHeight());
                rooms.setRoomScene(roomScene);
               // primaryStage.setFullScreen(true);
               
        
    }

     private void handleBack() {
        System.out.println("In backkk");
        primaryStage.setScene(chooseBatchScene);
        return;

    }
}
