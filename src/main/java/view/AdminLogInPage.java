package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdminLogInPage extends Application{

    @Override
    public void start(Stage stage) {

        // ===== Back Button =====
        Image backImg = new Image("assets/images/back.png"); // Update image path if needed
        ImageView backIcon = new ImageView(backImg);
        backIcon.setFitWidth(24);
        backIcon.setFitHeight(24);
        Button backButton = new Button();
        backButton.setGraphic(backIcon);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setOnAction(e -> System.out.println("Back clicked"));

        // ===== Title =====
        Label title = new Label("Bachelor User Database");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#020202ff"));

        // ===== Search Bar =====
        TextField searchField = new TextField();
        searchField.setPromptText("Search by name...");
        searchField.setPrefWidth(300);
        searchField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");

        HBox headerBar = new HBox(20, backButton, title, searchField);
        headerBar.setAlignment(Pos.CENTER_LEFT);
        headerBar.setPadding(new Insets(20));
        headerBar.setStyle("-fx-background-color:#494CA2");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // ===== ListView =====
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(
                "Name: Rohit Sharma | Email: rohit@bachelor.com | Phone: 9012345678",
                "Name: Anjali Verma | Email: anjali@bachelor.com | Phone: 9123456780",
                "Name: Kabir Khan | Email: kabir@bachelormail.com | Phone: 9988776655",
                "Name: Meena Joshi | Email: meena@mail.com | Phone: 9876543211"
        );
        listView.setStyle("-fx-font-size: 16px;");
        listView.setPrefHeight(400);

        // ===== Delete Button =====
        Button deleteBtn = new Button("Delete Selected User");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px;" +
                "-fx-background-radius: 10; -fx-padding: 10 20;");
        deleteBtn.setOnAction(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                listView.getItems().remove(selected);
            }
        });

        VBox listSection = new VBox(20, listView, deleteBtn);
        listSection.setAlignment(Pos.CENTER);
        listSection.setPadding(new Insets(20));
        listSection.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15), Insets.EMPTY)));
        listSection.setEffect(new DropShadow(10, Color.GRAY));

        VBox mainContent = new VBox(30, headerBar, listSection);
        mainContent.setPadding(new Insets(40));

        BorderPane root = new BorderPane(mainContent);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #E3E7F1, #E3E7F1);");

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Bachelor Data List");
        stage.setFullScreen(true);
        stage.show();
    }

   
}
