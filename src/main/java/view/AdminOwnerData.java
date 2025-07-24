package view;

import com.clutchcoders.dao.AddOwnerDash;
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

public class AdminOwnerData {
    Stage primaryStage;
    Scene adminOwnerScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setAdminOwnerScene(Scene adminOwnerScene) {
        this.adminOwnerScene = adminOwnerScene;
    }

    public Parent createAdminOwnerDataPage(Runnable adminOwnerThread) {

        // ========== Back Button ==========
        Image backImg = new Image("assets/images/back.png");
        ImageView backIcon = new ImageView(backImg);
        backIcon.setFitWidth(24);
        backIcon.setFitHeight(24);
        Button backButton = new Button();
        backButton.setGraphic(backIcon);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setOnAction(e -> adminOwnerThread.run());

        // ========== Title ==========
        Label title = new Label("Owner User Database");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#000000ff"));

        // ========== Search Bar ==========
        TextField searchField = new TextField();
        searchField.setPromptText("Search by name...");
        searchField.setPrefWidth(300);
        searchField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");

        HBox headerBar = new HBox(20, backButton, title, searchField);
        headerBar.setAlignment(Pos.CENTER_LEFT);
        headerBar.setPadding(new Insets(20));
        headerBar.setStyle("-fx-background-color: #494CA2;");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // ========== ListView ==========
        ListView<String> listView = new ListView<>();
        listView.setStyle("-fx-font-size: 16px;");
        listView.setPrefHeight(400);

        List<Map<String, Object>> owners = AddOwnerDash.getAllData();
        for (Map<String, Object> owner : owners) {
            String display = "Name: " + owner.getOrDefault("name", "N/A") +
                    " | Email: " + owner.getOrDefault("email", "N/A") +
                    " | Phone: " + owner.getOrDefault("contact", "N/A") +
                    " | Property: " + owner.getOrDefault("propertyType", "N/A");
            listView.getItems().add(display);
        }

        // ========== Search ==========
        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            listView.getItems().clear();
            for (Map<String, Object> owner : owners) {
                String name = (String) owner.getOrDefault("name", "");
                if (name.toLowerCase().contains(newValue.toLowerCase())) {
                    String display = "Name: " + name +
                            " | Email: " + owner.getOrDefault("email", "N/A") +
                            " | Phone: " + owner.getOrDefault("contact", "N/A") +
                            " | Property: " + owner.getOrDefault("propertyType", "N/A");
                    listView.getItems().add(display);
                }
            }
        });

        // ========== Double-Click to View Details ==========
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int index = listView.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    Map<String, Object> selectedOwner = owners.get(index);
                    showOwnerDetailsPopup(selectedOwner);
                }
            }
        });

        // ========== Delete Button ==========
        Button deleteBtn = new Button("Delete Selected User");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-background-radius: 10; -fx-padding: 10 20;");
        deleteBtn.setOnAction(e -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                String emailToDelete = (String) owners.get(index).get("email");
                AddOwnerDash.deleteData(emailToDelete);
                owners.remove(index);
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

    // ===== Popup to View Full Owner Details =====
    private void showOwnerDetailsPopup(Map<String, Object> owner) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Owner Details");

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label name = new Label("Name: " + owner.getOrDefault("name", "N/A"));
        Label email = new Label("Email: " + owner.getOrDefault("email", "N/A"));
        Label contact = new Label("Contact: " + owner.getOrDefault("contact", "N/A"));
        Label gender = new Label("Gender: " + owner.getOrDefault("gender", "N/A"));
        Label property = new Label("Property Type: " + owner.getOrDefault("propertyType", "N/A"));
        Label address = new Label("Address: " + owner.getOrDefault("address", "N/A") + ", " +
                owner.getOrDefault("city", "") + ", " +
                owner.getOrDefault("district", "") + ", " +
                owner.getOrDefault("state", "") + " - " +
                owner.getOrDefault("pincode", ""));
        Label rent = new Label("Rent: " + owner.getOrDefault("monthlyRent", "N/A"));
        Label deposit = new Label("Deposit: " + owner.getOrDefault("depositAmount", "N/A"));
        Label maintenance = new Label("Maintenance: " + owner.getOrDefault("maintenanceFee", "N/A"));

        Label amenities = new Label("Amenities: " + owner.getOrDefault("amenities", ""));
        Label features = new Label("Features: " + owner.getOrDefault("features", ""));
        Label preferences = new Label("Preferences: " + owner.getOrDefault("preferences", ""));

        // Photos
        VBox photoBox = new VBox(10);
        List<String> photos = (List<String>) owner.get("photoUrls");
        if (photos != null) {
            for (String url : photos) {
                ImageView img = new ImageView(new Image(url));
                img.setFitWidth(200);
                img.setPreserveRatio(true);
                photoBox.getChildren().add(img);
            }
        }

        content.getChildren().addAll(name, email, contact, gender, property, address, rent, deposit, maintenance, amenities, features, preferences, new Label("Photos:"), photoBox);

        Scene scene = new Scene(new ScrollPane(content), 500, 600);
        popup.setScene(scene);
        popup.showAndWait();
    }
}
