package view;

import java.util.List;

import com.clutchcoders.Model.OwnerDashModel;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class OwnerProfile {

  


    Stage primaryStage;
    Scene ownerScene, chatScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setOwnerScene(Scene ownerScene) {
        this.ownerScene = ownerScene;
    }

    public Parent createOwnerRoomScene(OwnerDashModel ownerData ,Runnable ownerProfileThread) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // ---------- TOP SECTION ----------
        HBox topSection = new HBox(20);
        topSection.setAlignment(Pos.CENTER_LEFT);
        topSection.setPadding(new Insets(20));
        topSection.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        ImageView backIcon = new ImageView(new Image("assets/images/back.png"));
        backIcon.setFitWidth(24);
        backIcon.setFitHeight(24);
        Button backBtn = new Button("", backIcon);
        backBtn.setStyle("-fx-background-color: transparent;");
        backBtn.setOnAction(e -> ownerProfileThread.run());

        ImageView profileImage = new ImageView(new Image("assets/images/profile.png"));
        profileImage.setFitHeight(100);
        profileImage.setFitWidth(100);

        Label nameLabel = new Label(ownerData.name);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label emailLabel = new Label(ownerData.email);
        emailLabel.setFont(Font.font("Arial", 14));

        ImageView editIcon = new ImageView(new Image("assets/images/edit.png"));
        editIcon.setFitWidth(16);
        editIcon.setFitHeight(16);

        Label editLabel = new Label("Edit Profile");
        HBox editBox = new HBox(5, editIcon, editLabel);
        editBox.setAlignment(Pos.CENTER_LEFT);

        Button editBtn = new Button();
        editBtn.setGraphic(editBox);
        editBtn.setStyle("-fx-background-color: #74b9ff; -fx-text-fill: white;");

        VBox userDetailsBox = new VBox(10, nameLabel, emailLabel, editBtn);
        userDetailsBox.setAlignment(Pos.CENTER_LEFT);

        topSection.getChildren().addAll(backBtn, profileImage, userDetailsBox);

        // ---------- INFO GRID ----------
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(20);
        infoGrid.setVgap(15);
        infoGrid.setPadding(new Insets(20));

        addGridRow(infoGrid, 0, "Gender:", ownerData.gender);
        addGridRow(infoGrid, 1, "Contact:", ownerData.contact);
        addGridRow(infoGrid, 2, "Property Type:", ownerData.propertyType);
        addGridRow(infoGrid, 3, "State:", ownerData.state);
        addGridRow(infoGrid, 4, "District:", ownerData.district);
        addGridRow(infoGrid, 5, "City:", ownerData.city);
        addGridRow(infoGrid, 6, "Address:", ownerData.address);
        addGridRow(infoGrid, 7, "Pincode:", ownerData.pincode);

 // ---------- FEATURES, PREFERENCES, PHOTOS ----------
VBox featuresBox = new VBox(5);
featuresBox.setPadding(new Insets(10));
featuresBox.setStyle("-fx-background-color: #f0f8ff;");

if (ownerData.features != null) {
    for (String feature : ownerData.features) {
        featuresBox.getChildren().add(new Label("\u2022 " + feature));
    }
}

VBox prefsBox = new VBox(5);
prefsBox.setPadding(new Insets(10));
prefsBox.setStyle("-fx-background-color: #f8f0ff;");

if (ownerData.preferences != null) {
    for (String pref : ownerData.preferences) {
        prefsBox.getChildren().add(new Label("\u2022 " + pref));
    }
}

HBox photoGallery = new HBox(10);
photoGallery.setPadding(new Insets(10));
photoGallery.setStyle("-fx-background-color: #ffffff;");

if (ownerData.photoUrls != null) {
    for (String path : ownerData.photoUrls) {
        try {
            ImageView photo = new ImageView(new Image(path, true));
            photo.setFitWidth(150);
            photo.setFitHeight(100);
            photo.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");
            photoGallery.getChildren().add(photo);
        } catch (Exception e) {
            System.out.println("Error loading image: " + path);
        }
    }
}

// ---------- AMENITIES ----------
Label amenitiesLabel = new Label("Amenities");
amenitiesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

FlowPane amenitiesPane = new FlowPane(10, 10);
amenitiesPane.setPadding(new Insets(10));

if (ownerData.amenities != null) {
    for (String amenity : ownerData.amenities) {
        Label aLabel = new Label(amenity);
        aLabel.setStyle("-fx-background-color: lightgreen; -fx-padding: 5 10; -fx-border-radius: 10; -fx-background-radius: 10;");
        amenitiesPane.getChildren().add(aLabel);
    }
}


        // ---------- REVIEW SECTION ----------
        Label reviewLabel = new Label("User Reviews");
        reviewLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox reviewBox = new VBox(10);
        reviewBox.setPadding(new Insets(10));
        reviewBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8;");
        List<String> reviews = List.of("Great property with all amenities!", "Very responsive owner.", "Clean and peaceful environment.");
        for (String review : reviews) {
            Label reviewItem = new Label("\u2022 " + review);
            reviewItem.setWrapText(true);
            reviewItem.setFont(Font.font("Arial", 13));
            reviewBox.getChildren().add(reviewItem);
        }

        TextArea addReviewArea = new TextArea();
        addReviewArea.setPromptText("Write your review...");
        addReviewArea.setPrefRowCount(3);

        Button submitReviewBtn = new Button("Submit Review");
        submitReviewBtn.setStyle("-fx-background-color: #55efc4; -fx-text-fill: black;");

        VBox addReviewBox = new VBox(10, addReviewArea, submitReviewBtn);
        addReviewBox.setPadding(new Insets(10));

        VBox reviewSection = new VBox(15, reviewLabel, reviewBox, addReviewBox);
        reviewSection.setPadding(new Insets(10));
        reviewSection.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 8;");

        // ---------- MAP LINK ----------
        Hyperlink mapLink = new Hyperlink("Open Google Map");
        mapLink.setOnAction(e -> getHostServices().showDocument("https://maps.google.com"));
        infoGrid.add(new Label("Map Link:"), 0, 8);
        infoGrid.add(mapLink, 1, 8);

        // ---------- CHAT BUTTON ----------
        Button chatBtn = new Button("Chat");
        chatBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        chatBtn.setPrefWidth(200);
        chatBtn.setStyle("-fx-background-color: #6c5ce7; -fx-text-fill: white; -fx-padding: 10 0;");
        chatBtn.setOnAction(e -> {
            intializeChat();
            primaryStage.setScene(chatScene);
        });

        // ---------- ASSEMBLE UI ----------
        VBox bottomSection = new VBox(20);
        bottomSection.setPadding(new Insets(10));
        bottomSection.getChildren().addAll(
                amenitiesLabel, amenitiesPane,
                new Label("Property Features"), featuresBox,
                new Label("Preferences"), prefsBox,
                new Label("Room / Flat Photos"), photoGallery,
                reviewSection, chatBtn
        );

        VBox scrollableContent = new VBox(20);
        scrollableContent.setPadding(new Insets(20));
        scrollableContent.getChildren().addAll(topSection, infoGrid, bottomSection);
        VBox.setVgrow(scrollableContent, Priority.ALWAYS);

        ScrollPane scrollPane = new ScrollPane(scrollableContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color:transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root.setCenter(scrollPane);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #a8edea, #fed6e3);");

        return root;
    }

    private void intializeChat() {
        ChatBox chatBox = new ChatBox();
        chatBox.setPrimaryStage(primaryStage);
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        chatScene = new Scene(chatBox.createChatScene(() -> handleBack()), screen.getWidth(), screen.getHeight());
        chatBox.setChatScene(chatScene);
    }

    public Object handleBack() {
        primaryStage.setScene(ownerScene);
        return ownerScene;
    }

    private HostServices getHostServices() {
        return new Application() {
            @Override
            public void start(Stage primaryStage) {}
        }.getHostServices();
    }

    private void addGridRow(GridPane grid, int row, String labelText, String valueText) {
        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label value = new Label(valueText);
        value.setFont(Font.font("Arial", 14));
        grid.add(label, 0, row);
        grid.add(value, 1, row);
    }
}
