package view;

import java.util.List;

import com.clutchcoders.Model.BachelorDashModel;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.application.HostServices;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BachelorProfile {

    BachelorDashModel model = new BachelorDashModel(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

    Stage primaryStage;
    Scene bachelorProfScene,chatScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setBachelorProfScene(Scene bachelorProfScene) {
        this.bachelorProfScene = bachelorProfScene;
    }

  
    private void addGridRow(GridPane grid, int row, String labelText, String valueText) {
        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label value = new Label(valueText);
        value.setFont(Font.font("Arial", 14));
        grid.add(label, 0, row);
        grid.add(value, 1, row);
    }

   public Parent createBachProf(BachelorDashModel model, Runnable bachProfThread) {
    BorderPane root = new BorderPane();
    root.setPadding(new Insets(20));

    // ---------- TOP SECTION ----------
    HBox topSection = new HBox(20);
    topSection.setAlignment(Pos.CENTER_LEFT);
    topSection.setPadding(new Insets(20));
    topSection.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

    // Back button
    ImageView backIcon = new ImageView(new Image("assets/images/back.png"));
    backIcon.setFitWidth(40);
    backIcon.setFitHeight(40);
    Button backBtn = new Button("", backIcon);
    backBtn.setStyle("-fx-background-color: transparent;");
    backBtn.setOnAction(e -> bachProfThread.run());

    // Profile picture
    ImageView profileImage = new ImageView();
    profileImage.setFitHeight(100);
    profileImage.setFitWidth(100);
    if (model.getProfileImageUrl() != null && !model.getProfileImageUrl().isEmpty()) {
    profileImage.setImage(new Image(model.getProfileImageUrl().get(0)));
} else {
    profileImage.setImage(new Image("assets/images/profile.png")); // default
}


    // Name, Email, Edit
    Label nameLabel = new Label(model.getName());
    nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    Label emailLabel = new Label(model.getEmail());
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

    addGridRow(infoGrid, 0, "Gender:", model.getGender());
    addGridRow(infoGrid, 1, "Contact:", model.getContact());
    addGridRow(infoGrid, 2, "Property Type:", model.getPropertyType());
    addGridRow(infoGrid, 3, "State:", model.getState());
    addGridRow(infoGrid, 4, "District:", model.getDistrict());
    addGridRow(infoGrid, 5, "City:", model.getCity());
    addGridRow(infoGrid, 6, "Address Line 1:", model.getAddress());
    addGridRow(infoGrid, 7, "Pincode:", model.getPincode());

    Hyperlink mapLink = new Hyperlink("Open Google Map");
    mapLink.setOnAction(e -> getHostServices().showDocument("https://maps.google.com"));
    infoGrid.add(new Label("Map Link:"), 0, 8);
    infoGrid.add(mapLink, 1, 8);

    // ---------- AMENITIES ----------
    Label amenitiesLabel = new Label("Amenities");
    amenitiesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

    FlowPane amenitiesPane = new FlowPane(10, 10);
    amenitiesPane.setPadding(new Insets(10));
    if (model.getAmenities() != null) {
        for (String amenity : model.getAmenities()) {
            Label aLabel = new Label(amenity);
            aLabel.setStyle("-fx-background-color: lightgreen; -fx-padding: 5 10 5 10; -fx-border-radius: 10; -fx-background-radius: 10;");
            amenitiesPane.getChildren().add(aLabel);
        }
    }

    // ---------- FEATURES ----------
    Label featuresLabel = new Label("Property Features");
    featuresLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

    VBox featuresBox = new VBox();
    if (model.getFeatures() != null) {
        for (String f : model.getFeatures()) {
            featuresBox.getChildren().add(new Label("• " + f));
        }
    }

    // ---------- PREFERENCES ----------
    Label prefsLabel = new Label("Preferences");
    prefsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

    VBox prefsBox = new VBox();
    if (model.getPreferences() != null) {
        for (String p : model.getPreferences()) {
            prefsBox.getChildren().add(new Label("• " + p));
        }
    }

    // ---------- REVIEWS ----------
    Label reviewLabel = new Label("User Reviews");
    reviewLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

    VBox reviewBox = new VBox(10);
    reviewBox.setPadding(new Insets(10));
    reviewBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8;");
    List<String> reviews = List.of(
        "Great property with all amenities!",
        "Very responsive owner.",
        "Clean and peaceful environment."
    );
    for (String review : reviews) {
        Label reviewItem = new Label("• " + review);
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

    // ---------- CHAT BUTTON ----------
    Button chatBtn = new Button("Chat");
    chatBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    chatBtn.setPrefWidth(200);
    chatBtn.setStyle("""
        -fx-background-color: #6c5ce7;
        -fx-text-fill: white;
        -fx-padding: 10 0 10 0;
        -fx-alignment: center;
    """);
    chatBtn.setAlignment(Pos.CENTER);
    chatBtn.setOnAction(e -> {
        intializeChat();
        primaryStage.setScene(chatScene);
    });

    // ---------- BOTTOM SECTION ----------
    VBox bottomSection = new VBox(20);
    bottomSection.setPadding(new Insets(10));
    bottomSection.getChildren().addAll(
        amenitiesLabel, amenitiesPane,
        featuresLabel, featuresBox,
        prefsLabel, prefsBox,
        reviewSection, chatBtn
    );

    // ---------- SCROLLABLE CENTER ----------
    VBox scrollableContent = new VBox(20);
    scrollableContent.setPadding(new Insets(20));
    scrollableContent.getChildren().addAll(topSection, infoGrid, bottomSection);

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
       chatScene = new Scene(chatBox.createChatScene(()->handleBack()),screen.getWidth(),screen.getHeight());
       chatBox.setChatScene(chatScene);
    }

   public void handleBack() {
   primaryStage.setScene(bachelorProfScene);
   return;
}

   private HostServices getHostServices() {
        return new Application() {
            @Override
            public void start(Stage primaryStage) {}
        }.getHostServices();
    }
}
