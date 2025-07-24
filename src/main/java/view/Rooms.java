package view;

import java.util.List;

import com.clutchcoders.Model.OwnerDashModel;
import com.clutchcoders.dao.FetchOwnerProfiles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Rooms {

    VBox contentBox = new VBox(20); 
    Stage primaryStage;
    Scene roomScene, ownerScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setRoomScene(Scene roomScene) {
        this.roomScene = roomScene;
    }

    public Parent createRoomScene(Runnable roomThread) {

        Label title = new Label("LiveMate");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        title.setTextFill(Color.WHITE);

        Region spacerLeft = new Region();
        Region spacerRight = new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        ImageView backIcon = new ImageView(new Image("assets/images/back.png"));
        backIcon.setFitWidth(40);
        backIcon.setFitHeight(40);
        Button backButton = new Button("", backIcon);
        backButton.setStyle("-fx-background-color: transparent;");
       
        HBox backButtonContainer = new HBox(backButton);
        backButtonContainer.setAlignment(Pos.CENTER);
        backButtonContainer.setPadding(new Insets(0, 0, 20, 0));
        backButtonContainer.setOnMouseClicked(e->{
                System.out.println("backkkkk!");
                roomThread.run();       
        });

        ImageView loginProfileIcon = new ImageView(new Image("assets/images/profile.png"));
        loginProfileIcon.setFitWidth(40);
        loginProfileIcon.setFitHeight(40);
        Button loginProfileButton = new Button("", loginProfileIcon);
        loginProfileButton.setStyle("-fx-background-color: transparent;");
        HBox loginProfileContainer = new HBox(loginProfileButton);
        loginProfileContainer.setAlignment(Pos.CENTER);
        loginProfileContainer.setPadding(new Insets(0, 0, 20, 0));

        HBox appBar = new HBox(10, backButtonContainer, title, spacerRight, loginProfileContainer);
        appBar.setAlignment(Pos.CENTER_LEFT);
        appBar.setPadding(new Insets(20));
        appBar.setStyle("-fx-background-color: linear-gradient(to right, #494CA2, #494CA3);");
        appBar.setEffect(new DropShadow(5, Color.GRAY));

        Label resultLabel = new Label("Results for Room");
        resultLabel.setStyle("-fx-font-size: 18px; -fx-underline: true; -fx-font-weight: bold; -fx-text-fill: black;");
        resultLabel.setPadding(new Insets(10, 0, 5, 20));

        ComboBox<String> cityDropdown = new ComboBox<>();
        cityDropdown.setPromptText("Select City");
        cityDropdown.getItems().addAll(
                "Mumbai", "Delhi", "Bengaluru", "Hyderabad", "Ahmedabad",
                "Chennai", "Kolkata", "Pune", "Jaipur", "Surat"
        );
        cityDropdown.setStyle("-fx-font-size: 13; -fx-background-radius: 5;");

        cityDropdown.setOnAction(e -> {
            String selectedCity = cityDropdown.getValue();
            if (selectedCity != null && !selectedCity.isEmpty()) {
                updateRoomCards(selectedCity); 
            }
        });

        ImageView filterIcon = new ImageView(new Image("assets/images/filter.png"));
        filterIcon.setFitWidth(20);
        filterIcon.setFitHeight(20);
        Button filterButton = new Button("", filterIcon);
        filterButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 5;");

        HBox locationFilter = new HBox(10, filterButton, cityDropdown);

        VBox topSection = new VBox(10, appBar, resultLabel, locationFilter);
        topSection.setAlignment(Pos.TOP_LEFT);
        topSection.setPadding(new Insets(10));

        contentBox = new VBox(20);
        contentBox.setAlignment(Pos.TOP_CENTER);

        List<OwnerDashModel> ownerProfiles = FetchOwnerProfiles.getOwnerProfiles();
        int index = 0;
        HBox row = new HBox(30);
        for (OwnerDashModel model : ownerProfiles) {
            String photoUrl = (model.photoUrls != null && !model.photoUrls.isEmpty()) ?
                    model.photoUrls.get(0) : "assets/images/room2.jpg";
            Image img = new Image(photoUrl);

            HBox card = createProfileCard(model.name, model.city + ", " + model.state,
                    model.monthlyRent, model.mapLink, img, model);

            if (index % 2 == 0) {
                row = new HBox(30);
                row.setAlignment(Pos.CENTER);
                row.setPadding(new Insets(10));
                contentBox.getChildren().add(row);
            }
            row.getChildren().add(card);
            index++;
        }

        VBox mainVBox = new VBox(30, topSection, contentBox);
        mainVBox.setAlignment(Pos.TOP_CENTER);
        mainVBox.setPadding(new Insets(30));
        mainVBox.setStyle("-fx-background-color: linear-gradient(to bottom,#E3E7F1, #E3E7F2);");

        ScrollPane scroll = new ScrollPane(mainVBox);
        scroll.setPrefSize(1200, 900);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        return scroll;
    }

    private HBox createProfileCard(String a,String b,String c, String d,Image abc,OwnerDashModel model) {
        Image img;
        if (model.photoUrls != null && !model.photoUrls.isEmpty()) {
            img = new Image(model.photoUrls.get(0), true);  
        } else {
            img = new Image("assets/images/room2.jpg");  
        }

        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(180);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        Rectangle clip = new Rectangle(180, 180);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);

        VBox imgBox = new VBox(imageView);
        imgBox.setAlignment(Pos.CENTER);
        imgBox.setPadding(new Insets(10, 5, 10, 5));

        Label nameLabel = new Label("Name: " + model.name);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nameLabel.setTextFill(Color.BLACK);

        String address = model.city + ", " + model.state;
        Label addressLabel = new Label("Address: " + address);
        addressLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        addressLabel.setTextFill(Color.BLACK);

        Label rentLabel = new Label("Rent: " + model.monthlyRent);
        rentLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        rentLabel.setTextFill(Color.BLACK);

        Label locationLabel = new Label("Location: " + model.mapLink);
        locationLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        locationLabel.setTextFill(Color.BLACK);

        ImageView locationIconView = new ImageView(new Image("assets/images/loc.png"));
        locationIconView.setFitWidth(20);
        locationIconView.setFitHeight(20);

        HBox locationBox = new HBox(8, locationIconView, locationLabel);
        locationBox.setAlignment(Pos.CENTER_LEFT);

        VBox infoBox = new VBox(15, nameLabel, addressLabel, rentLabel, locationBox);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setPadding(new Insets(10, 10, 10, 5));

        HBox card = new HBox(30, imgBox, infoBox);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: linear-gradient(to bottom, #8186D5, #8186D6);"
                + "-fx-border-color: #444; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15;");
        card.setPrefHeight(200);
        card.setPrefWidth(500);

        card.setOnMouseClicked(e -> {
            intializeOwnerProfile(model); 
            primaryStage.setScene(ownerScene);
        });

        return card;
    }

    private void updateRoomCards(String city) {
        List<OwnerDashModel> owners = FetchOwnerProfiles.getOwnersByCity(city);

        contentBox.getChildren().clear();

        int index = 0;
        HBox row = new HBox(30);
        for (OwnerDashModel owner : owners) {
            Image img = (owner.photoUrls != null && !owner.photoUrls.isEmpty())
                    ? new Image(owner.photoUrls.get(0))
                    : new Image("assets/images/room2.jpg");

            HBox card = createProfileCard(
                    owner.name,
                    owner.city + ", " + owner.state,
                    owner.monthlyRent,
                    owner.mapLink,
                    img,
                    owner
            );

            if (index % 2 == 0) {
                row = new HBox(30);
                row.setAlignment(Pos.CENTER);
                row.setPadding(new Insets(10));
                contentBox.getChildren().add(row);
            }
            row.getChildren().add(card);
            index++;
        }
    }

    private void intializeOwnerProfile(OwnerDashModel model) {
        OwnerProfile ownerProfile = new OwnerProfile();
        ownerProfile.setPrimaryStage(primaryStage);

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        ownerScene = new Scene(
            ownerProfile.createOwnerRoomScene(model, this::handleBack),
            screen.getWidth(), screen.getHeight()
        );

        ownerProfile.setOwnerScene(ownerScene);
    }

    public void handleBack() {
        primaryStage.setScene(roomScene);
    }
}
