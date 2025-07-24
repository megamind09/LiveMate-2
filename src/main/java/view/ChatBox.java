package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatBox  {
    private VBox messagesBox;
    private ScrollPane scrollPane;
    private TextField messageField;

    Stage primaryStage;
    Scene chatScene;

    

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setChatScene(Scene chatScene) {
        this.chatScene = chatScene;
    }

   
    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            addMessage(message, true);
            messageField.clear();
        }
    }
    

    private void addMessage(String message, boolean sentByUser) {
        Label label = new Label(formatMessage(message));
        label.setWrapText(true);
        label.setMaxWidth(250);
        label.setStyle(sentByUser ?
                "-fx-background-color: #dcf8c6; -fx-padding: 10px; -fx-background-radius: 10;" :
                "-fx-background-color: white; -fx-padding: 10px; -fx-background-radius: 10; -fx-border-color: #ccc;");
        HBox messageContainer = new HBox(label);
        messageContainer.setAlignment(sentByUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        messagesBox.getChildren().add(messageContainer);

        // Auto-scroll to bottom
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }

    private String formatMessage(String message) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        return message + " [" + time + "]";
    
  }

    public Parent createChatScene(Runnable chatThread) {
      


          BorderPane root = new BorderPane();

                // Top bar with back arrow, user photo, and name
        Label backArrow = new Label("←");
        backArrow.setFont(Font.font("Arial", 20));
        backArrow.setStyle("-fx-text-fill: white; -fx-cursor: hand;");

        // Optional: set click event
        backArrow.setOnMouseClicked(e -> {
            System.out.println("chat backk"); // Add navigation logic here
            chatThread.run();
        });

        ImageView profilePic = new ImageView(new Image("https://i.pravatar.cc/40"));
        profilePic.setFitWidth(40);
        profilePic.setFitHeight(40);
        profilePic.setClip(new Circle(20, 20, 20)); // Circular avatar

        VBox userInfoBox = new VBox();
        Label userName = new Label("Ajit Kumar");
        userName.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        userInfoBox.getChildren().addAll(userName);
        userInfoBox.setSpacing(2);

        HBox topBar = new HBox(10, backArrow, profilePic, userInfoBox);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #494CA2;");
        root.setTop(topBar);


        // Center chat area
        messagesBox = new VBox(10);
        messagesBox.setPadding(new Insets(10));

        scrollPane = new ScrollPane(messagesBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: #8aba84ec;");
        root.setCenter(scrollPane);

        // Bottom input area
        messageField = new TextField();
        messageField.setPromptText("Write a message...");
        messageField.setPrefHeight(40);
        messageField.setFocusTraversable(false);
        

        Button sendButton = new Button("Send");
        sendButton.setPrefHeight(40);
        sendButton.setStyle("-fx-background-color: #30e439ef; -fx-text-fill: white; -fx-font-weight: bold;");
        sendButton.setOnAction(e -> sendMessage());

           

        HBox inputArea = new HBox(20,messageField,sendButton);
        inputArea.setPadding(new Insets(20));
        inputArea.setStyle("-fx-background-color: #f1ebebff;");
        HBox.setHgrow(messageField, Priority.ALWAYS);
        root.setBottom(inputArea);
        return root;

    }

   
}