package view;

import com.clutchcoders.dao.AddBachelorDash;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class AdminBachelorData {

    Stage primaryStage;
    Scene adminBatchScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setAdminBatchScene(Scene adminBatchScene) {
        this.adminBatchScene = adminBatchScene;
    }

    public Parent createAdminBatchData(Runnable adminBatchThread) {

        // ===== Back Button =====
        Image backImg = new Image("assets/images/back.png");
        ImageView backIcon = new ImageView(backImg);
        backIcon.setFitWidth(24);
        backIcon.setFitHeight(24);
        Button backButton = new Button();
        backButton.setGraphic(backIcon);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setOnAction(e -> adminBatchThread.run());

        // ===== Title =====
        Label title = new Label("Bachelor User Database");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#0d0d0eff"));

        // ===== Search Bar =====
        TextField searchField = new TextField();
        searchField.setPromptText("Search by name...");
        searchField.setPrefWidth(300);
        searchField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");

        HBox headerBar = new HBox(20, backButton, title, searchField);
        headerBar.setAlignment(Pos.CENTER_LEFT);
        headerBar.setStyle("-fx-background-color:#494CA2");
        headerBar.setPadding(new Insets(20));
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // ===== ListView =====
        ListView<String> listView = new ListView<>();
        listView.setStyle("-fx-font-size: 16px;");
        listView.setPrefHeight(400);

        // Load bachelors from Firestore
        List<Map<String, Object>> bachelors = AddBachelorDash.getAllData();
        for (Map<String, Object> bachelor : bachelors) {
            String display = "Name: " + bachelor.getOrDefault("name", "N/A") +
                    " | Email: " + bachelor.getOrDefault("email", "N/A") +
                    " | Phone: " + bachelor.getOrDefault("contact", "N/A") +
                    " | Profession: " + bachelor.getOrDefault("profession", "N/A");
            listView.getItems().add(display);
        }

        // ===== Search =====
        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            listView.getItems().clear();
            for (Map<String, Object> bachelor : bachelors) {
                String name = (String) bachelor.getOrDefault("name", "");
                if (name.toLowerCase().contains(newValue.toLowerCase())) {
                    String display = "Name: " + name +
                            " | Email: " + bachelor.getOrDefault("email", "N/A") +
                            " | Phone: " + bachelor.getOrDefault("contact", "N/A") +
                            " | Profession: " + bachelor.getOrDefault("profession", "N/A");
                    listView.getItems().add(display);
                }
            }
        });

        // ===== Double-Click to View Details =====
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int index = listView.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    Map<String, Object> selectedBachelor = bachelors.get(index);
                    showBachelorDetailsPopup(selectedBachelor);
                }
            }
        });

        // ===== Delete Button =====
        Button deleteBtn = new Button("Delete Selected User");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px;" +
                "-fx-background-radius: 10; -fx-padding: 10 20;");
        deleteBtn.setOnAction(e -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                String emailToDelete = (String) bachelors.get(index).get("email");
                AddBachelorDash.deleteData(emailToDelete);
                bachelors.remove(index);
                listView.getItems().remove(index);
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

        return root;
    }

    // ===== Popup to View Full Bachelor Details =====
    private void showBachelorDetailsPopup(Map<String, Object> bachelor) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Bachelor Details");

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label name = new Label("Name: " + bachelor.getOrDefault("name", "N/A"));
        Label email = new Label("Email: " + bachelor.getOrDefault("email", "N/A"));
        Label contact = new Label("Contact: " + bachelor.getOrDefault("contact", "N/A"));
        Label gender = new Label("Gender: " + bachelor.getOrDefault("gender", "N/A"));
        Label profession = new Label("Profession: " + bachelor.getOrDefault("profession", "N/A"));
        Label shift = new Label("Working Shift: " + bachelor.getOrDefault("shift", "N/A"));
        Label rentRange = new Label("Rent Range: " + bachelor.getOrDefault("rentRange", "N/A"));
        Label address = new Label("Address: " + bachelor.getOrDefault("address", "N/A"));

        Label hobbies = new Label("Hobbies: " + bachelor.getOrDefault("hobbies", ""));

        VBox photoBox = new VBox(10);
        List<String> photos = (List<String>) bachelor.get("photoUrls");
        if (photos != null) {
            for (String url : photos) {
                ImageView img = new ImageView(new Image(url));
                img.setFitWidth(200);
                img.setPreserveRatio(true);
                photoBox.getChildren().add(img);
            }
        }

        content.getChildren().addAll(name, email, contact, gender, profession, shift, rentRange, address, hobbies, new Label("Photos:"), photoBox);

        Scene scene = new Scene(new ScrollPane(content), 500, 600);
        popup.setScene(scene);
        popup.showAndWait();
    }
}
