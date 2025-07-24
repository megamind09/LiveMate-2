package view;

import java.util.List;

import com.clutchcoders.Model.BachelorDashModel;
import com.clutchcoders.Model.OwnerDashModel;
import com.clutchcoders.dao.FetchBachelorProfiles;


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
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Node; // For Node
import com.clutchcoders.Model.BachelorDashModel;
import com.clutchcoders.dao.FetchBachelorProfiles;


public class Roommate {


   private FlowPane cardPane = new FlowPane();


    Stage primaryStage;
    Scene roomMatesScene,bachelorProfScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setRoomMatesScene(Scene roomMatesScene) {
        this.roomMatesScene = roomMatesScene;
    }
public Parent createRoomMateScene(Runnable roommateThread) {
    Label title = new Label("LiveMate");
    title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
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
    backButton.setOnAction(e -> roommateThread.run());

    HBox backButtonContainer = new HBox(backButton);
    backButtonContainer.setAlignment(Pos.CENTER);
    backButtonContainer.setPadding(new Insets(0, 0, 20, 0));

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
    appBar.setStyle("-fx-background-color: #494CA3;");
    appBar.setEffect(new DropShadow(5, Color.GRAY));

    Label resultLabel = new Label("Result for Roommate");
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
    System.out.println("City Selected: " + selectedCity);
    if (selectedCity != null && !selectedCity.isEmpty()) {
        updateRoommateCards(selectedCity);
    }
});


cardPane.setHgap(30);
cardPane.setVgap(30);
cardPane.setPadding(new Insets(30));
cardPane.setAlignment(Pos.TOP_CENTER);
cardPane.setPrefWrapLength(1000);



    ImageView filterIcon = new ImageView(new Image("assets/images/filter.png"));
    filterIcon.setFitWidth(20);
    filterIcon.setFitHeight(20);
    Button filterButton = new Button("", filterIcon);
    filterButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 5;");

    HBox locationFilter = new HBox(5, filterButton, cityDropdown);

    VBox topSection = new VBox(10);
    topSection.getChildren().addAll(appBar, resultLabel, locationFilter);
    topSection.setAlignment(Pos.TOP_LEFT);
    topSection.setPadding(new Insets(10));

    HBox filterBar = new HBox(10, topSection);
    filterBar.setAlignment(Pos.CENTER_LEFT);
    filterBar.setPadding(new Insets(10));

    //  Card Pane (UNLIMITED dynamic cards)
    
    cardPane.setHgap(30);
    cardPane.setVgap(30);
    cardPane.setPadding(new Insets(30));
    cardPane.setAlignment(Pos.TOP_CENTER);
    cardPane.setPrefWrapLength(1000); // Auto-wrap based on width

    //  Fetch Bachelor Profiles
    List<BachelorDashModel> profiles = FetchBachelorProfiles.getBachelorProfiles();

    for (BachelorDashModel model : profiles) {
        Image img;
        try {
            String photoUrl = (model.photoUrls != null && !model.photoUrls.isEmpty())
                    ? model.photoUrls.get(0)
                    : "assets/images/profile.png";
            img = new Image(photoUrl);
        } catch (Exception e) {
            System.out.println("Image not found - fallback used.");
            img = new Image("assets/images/profile.png");
        }

        Node card = createProfileCard(
    model.name,
    model.address,
    model.monthlyRent,
    model.city,
    img,
    model
);
// Make sure this method exists
        cardPane.getChildren().add(card);
    }

    VBox mainVBox = new VBox(20, appBar, filterBar, cardPane);
    mainVBox.setAlignment(Pos.TOP_CENTER);
    mainVBox.setPadding(new Insets(20));
    mainVBox.setStyle("-fx-background-color: linear-gradient(to bottom, #E3E7F1,#E3E7F2);");

    ScrollPane scroll = new ScrollPane(mainVBox);
    scroll.setFitToWidth(true);
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scroll.setPrefSize(1200, 900);

    return scroll;
}



       private void updateRoommateCards(String city) {
    List<BachelorDashModel> bachelors = FetchBachelorProfiles.getBachelorsByCity(city);
    cardPane.getChildren().clear();  // clear existing cards

    for (BachelorDashModel bachelor : bachelors) {
        Node card = createRoommateCard(bachelor);
        cardPane.getChildren().add(card);
    }
}



        private Node createRoommateCard(BachelorDashModel model) {
    String name = model.getName();
    String address = model.getAddress();
    String rent = model.getMonthlyRent();
    String city = model.getCity();

    String imageUrl = (model.getProfileImageUrl() != null && !model.getProfileImageUrl().isEmpty())
                      ? model.getProfileImageUrl().get(0)
                      : "assets/images/profile.png";

    Image img = new Image(imageUrl, true);

    return createProfileCard(name, address, rent, city, img, model);
}


    

    
     private Node createProfileCard(String name, String address, String monthlyRent, String city, Image img,
        BachelorDashModel model) {
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(180);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        Circle clip = new Circle(80, 80, 80);
        imageView.setClip(clip);

        VBox imgBox = new VBox(imageView);
        imgBox.setAlignment(Pos.CENTER);
        imgBox.setPadding(new Insets(20));

        Label nameLabel = new Label("Name: " + name);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nameLabel.setTextFill(Color.BLACK);

        Label addressLabel = new Label("Address: " + address);
        addressLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        addressLabel.setTextFill(Color.BLACK);

        Label rentLabel = new Label("Rent: ₹" + monthlyRent);
        rentLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        rentLabel.setTextFill(Color.BLACK);

        Label locationLabel = new Label("Location: " + city);
        locationLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        locationLabel.setTextFill(Color.BLACK);

        Image locationIcon = new Image("assets/images/loc.png");
        ImageView locationIconView = new ImageView(locationIcon);
        locationIconView.setFitWidth(20);
        locationIconView.setFitHeight(20);
        locationIconView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        HBox locationBox = new HBox(8, locationIconView, locationLabel);
        locationBox.setAlignment(Pos.CENTER_LEFT);

        VBox infoBox = new VBox(15, nameLabel, addressLabel, rentLabel, locationBox);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setPadding(new Insets(10));

        HBox card = new HBox(20, imgBox, infoBox);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #8186D5,#8186D6);" +
                        "-fx-border-color: #444;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 15;" +
                        "-fx-background-radius: 15;"
        );
        card.setPrefHeight(200);
        card.setPrefWidth(600);

        card.setOnMouseClicked(e->{
            initializeBachelorProfile(model);
            primaryStage.setScene(bachelorProfScene);
        });

        return card;
    }

   private void initializeBachelorProfile(BachelorDashModel model) {
    BachelorProfile bachelorProfile = new BachelorProfile();
    bachelorProfile.setPrimaryStage(primaryStage); // optional, if needed

    // Create full-screen scene using BachelorDashModel
    Rectangle2D screen = Screen.getPrimary().getVisualBounds();
    bachelorProfScene = new Scene(
        bachelorProfile.createBachProf(model, this::handleBack),
        screen.getWidth(), screen.getHeight()
    );

    bachelorProfile.setBachelorProfScene(bachelorProfScene); // if your class uses this
}



    public void handleBack() {
       primaryStage.setScene(roomMatesScene);
       return ;
    }
}