package view;

import java.util.ArrayList;
import java.util.List;

import com.clutchcoders.Model.ProfileCardModel;
import com.clutchcoders.Model.UserProfileModel;
import com.clutchcoders.dao.SessionData;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

public class HomePage {



        private boolean isExploreUser = false;
    public void setExploreUser(boolean exploreUser) {
        this.isExploreUser = exploreUser;
    }


    Stage primaryStage;
    Scene homePagScene,ownerdashScene,bachelorScene,userScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setHomePagScene(Scene homePagScene) {
        this.homePagScene = homePagScene;
    }

   public Parent createHomePage(Runnable homeThread) {

    // Back Button
    Image backImg = new Image("assets/images/back.png");
    ImageView backIcon = new ImageView(backImg);
    backIcon.setFitWidth(40);
    backIcon.setFitHeight(40);

    Button backButton = new Button();
    backButton.setGraphic(backIcon);
    addHoverEffect(backButton);
    backButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
    backButton.setOnAction(e -> homeThread.run());

    // Title
    Label title = new Label("LiveMate");
    title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
    title.setTextFill(Color.WHITE);

    // Search Bar
    TextField searchBar = new TextField();
    searchBar.setPromptText("Search profiles...");
    searchBar.setPrefWidth(300);
    addHoverEffect(searchBar);
    searchBar.setFont(Font.font(14));
    searchBar.setStyle(
        "-fx-background-radius: 30;" +
        "-fx-border-radius: 30;" +
        "-fx-padding: 8 16 8 16;" +
        "-fx-border-color: gray;" +
        "-fx-background-color: white;"
    );

    ImageView searchimg = new ImageView(new Image("assets/images/search.png"));
    addHoverEffect(searchimg);
    searchimg.setFitWidth(40);
    searchimg.setFitHeight(40);
    searchimg.setPreserveRatio(true);

    HBox searchContainer = new HBox(20, searchBar, searchimg);
    searchContainer.setAlignment(Pos.CENTER_RIGHT);
    searchContainer.setPrefWidth(500);

    // Profile Icon
    ImageView loginProfileIcon = new ImageView(new Image("assets/images/profile.png"));
    loginProfileIcon.setFitWidth(40);
    loginProfileIcon.setFitHeight(40);
    Button loginProfileButton = new Button("", loginProfileIcon);
    loginProfileButton.setStyle("-fx-background-color: transparent;");
    addHoverEffect(loginProfileButton);
    loginProfileButton.setOnAction(e -> {
        intializeUserProfile();
        primaryStage.setScene(userScene);
    });
    HBox loginProfileContainer = new HBox(loginProfileButton);
    loginProfileContainer.setAlignment(Pos.CENTER_RIGHT);
    loginProfileContainer.setPadding(new Insets(10));

    // App Bar
    Region spacerMid = new Region();
    HBox.setHgrow(spacerMid, Priority.ALWAYS);
    HBox appBar = new HBox(20, backButton, title, spacerMid, searchContainer, loginProfileContainer);
    appBar.setAlignment(Pos.CENTER_LEFT);
    appBar.setPadding(new Insets(15));
    appBar.setStyle("-fx-background-color: #494CA2;");
    appBar.setEffect(new DropShadow(5, Color.GRAY));

    // Action Buttons
    Button regOwner = new Button("Register as Owner");
    regOwner.setFont(Font.font("Arial", 30));
    regOwner.setPrefSize(300, 40);
    addHoverEffect(regOwner);
    regOwner.setOnAction(e -> {
        if (isExploreUser) showLoginAlert();
        else {
            intializeOwnerDash();
            primaryStage.setScene(ownerdashScene);
        }
    });

    Button regBatchelorButton = new Button("Register as Bachelor");
    regBatchelorButton.setFont(Font.font("Arial", 30));
    regBatchelorButton.setPrefSize(300, 40);
    addHoverEffect(regBatchelorButton);
    regBatchelorButton.setOnAction(e -> {
        if (isExploreUser) showLoginAlert();
        else {
            initializeBachelorDash();
            primaryStage.setScene(bachelorScene);
        }
    });

    HBox buttons = new HBox(80, regOwner, regBatchelorButton);
    buttons.setAlignment(Pos.CENTER);
    buttons.setPadding(new Insets(20, 0, 20, 0));

    // **Profile List**
   List<ProfileCardModel> allProfiles = new ArrayList<>();
allProfiles.add(new ProfileCardModel("Anish Inamdar", "Pune, India", "₹10,000/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Akash Ravaji", "Pune, India", "₹12,000/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Raj Jadhav", "Mumbai, India", "₹15,000/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Ajit Ware", "Nagpur, India", "₹8,000/month", "assets/images/profile.png"));

// Added 10 more dummy cards
allProfiles.add(new ProfileCardModel("Sneha Patil", "Nashik, India", "₹11,500/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Saurabh Kulkarni", "Kolhapur, India", "₹9,500/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Priya Deshmukh", "Aurangabad, India", "₹14,000/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Rahul Chavan", "Solapur, India", "₹7,500/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Kiran Shinde", "Satara, India", "₹13,000/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Neha Pawar", "Thane, India", "₹16,000/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Vivek More", "Pimpri-Chinchwad, India", "₹10,500/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Komal Jagtap", "Sangli, India", "₹8,500/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Aditya Gaikwad", "Ahmednagar, India", "₹9,800/month", "assets/images/profile.png"));
allProfiles.add(new ProfileCardModel("Shweta Bhosale", "Latur, India", "₹12,500/month", "assets/images/profile.png"));


    // **Container for filtered profiles**
    VBox cardContainer = new VBox(30);
    cardContainer.setAlignment(Pos.TOP_CENTER);
    cardContainer.setPadding(new Insets(20));

    // **Render all initially**
    for (ProfileCardModel p : allProfiles) {
        cardContainer.getChildren().add(createProfile(p.name, p.address, p.rent, "Google_Map", p.imageUrl));
    }

    // **Add search functionality**
    searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
        String searchText = newValue.toLowerCase();
        cardContainer.getChildren().clear();
        for (ProfileCardModel p : allProfiles) {
            if (p.name.toLowerCase().contains(searchText) || p.address.toLowerCase().contains(searchText)) {
                cardContainer.getChildren().add(createProfile(p.name, p.address, p.rent, "Google_Map", p.imageUrl));
            }
        }
    });

    // Main Layout
    VBox mainVBox = new VBox(30, appBar, buttons, cardContainer);
    mainVBox.setAlignment(Pos.TOP_CENTER);
    mainVBox.setPadding(new Insets(50));
    mainVBox.setStyle("-fx-background-color: linear-gradient(to bottom,  #E3E7F1,  #E3E7F1);");

    ScrollPane scroll = new ScrollPane(mainVBox);
    scroll.setFitToWidth(true);
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    return scroll;
}

   

private void intializeUserProfile() {
    UserProfile userProfile = new UserProfile();
    userProfile.setPrimaryStage(primaryStage);
    primaryStage.setMaximized(true);
    Rectangle2D screen = Screen.getPrimary().getVisualBounds();

    // Use SessionData.loggedInUser
    userScene = new Scene(
        userProfile.createUserProfileScene(
            SessionData.loggedInUser,   // Current logged-in user data
            () -> handleBack()          // Back button action
        ),
        screen.getWidth(),
        screen.getHeight()
    );
}



    private void initializeBachelorDash() {
       BachelorDashBoard bachelorDashBoard = new BachelorDashBoard();
       bachelorDashBoard.setPrimaryStage(primaryStage);
       primaryStage.setMaximized(true);
     Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    bachelorScene = new Scene(
        bachelorDashBoard.createBachelorDash(() -> handleBack()),
        screenBounds.getWidth(),
        screenBounds.getHeight()
    );

    bachelorDashBoard.setBachelorScene(bachelorScene);
    }

    private void intializeOwnerDash() {
        OwnerDashboard ownerDashboard = new OwnerDashboard();
        ownerDashboard.setPrimaryStage(primaryStage);
        primaryStage.setMaximized(true);
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        ownerdashScene = new Scene(ownerDashboard.createOwnerDash(()->handleBack()),screen.getWidth(),screen.getHeight());
        ownerDashboard.setOwnerdashScene(ownerdashScene);
    }

    private void handleBack() {
        primaryStage.setScene(homePagScene);
        return ;
    }

    private HBox createProfile(String name, String address, String rent, String location,String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(140);
        imageView.setFitHeight(140);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        Circle clip = new Circle(70);
        clip.setCenterX(70);
        clip.setCenterY(70);
        imageView.setClip(clip);

        VBox imgBox = new VBox(imageView);
        imgBox.setAlignment(Pos.CENTER);
        imgBox.setPadding(new Insets(10));

        Label nameLabel = new Label("Name: " + name);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        nameLabel.setTextFill(Color.BLACK);

        Label addressLabel = new Label("Address: " + address);
        addressLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        addressLabel.setTextFill(Color.BLACK);

        Label rentLabel = new Label("Rent: " + rent);
        rentLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        rentLabel.setTextFill(Color.BLACK);

        Label locationLabel = new Label("Location: "+location );
        locationLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        locationLabel.setTextFill(Color.BLACK);


        Image locationIcon = new Image("assets/images/loc.png");
        ImageView locationIconView = new ImageView(locationIcon);
        locationIconView.setFitWidth(20);
        locationIconView.setFitHeight(20);
        locationIconView.setPreserveRatio(true);

        HBox locationBox = new HBox(8, locationIconView, locationLabel);
        locationBox.setAlignment(Pos.CENTER_LEFT);

        VBox infoBox = new VBox(15, nameLabel, addressLabel, rentLabel,locationBox);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setPadding(new Insets(10,10,10,5));

        HBox card = new HBox(30, imgBox, infoBox);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefSize(400,200);
        card.setStyle(
            "-fx-background-color: #8186D5 ;" +
            "-fx-border-color:transparent;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 15;" +
            "-fx-background-radius: 15;"
        );

        //DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(4);
        dropShadow.setColor(Color.BLACK);
        card.setEffect(dropShadow);



        card.setOnMouseClicked(e -> {
    if (isExploreUser) {
        showLoginAlert();
    } else {
        System.out.println("Open detailed profile...");
        // Future detailed profile navigation
    }
});

        return card;
    }




    private void showLoginAlert() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Login Required");
    alert.setHeaderText(null);
    alert.setContentText("Please log in or sign up to access this feature.");
    alert.showAndWait();
}


    // Hover Effect
    private void addHoverEffect(Node node) {
    DropShadow shadow = new DropShadow(10, Color.BLACK);

    node.setOnMouseEntered(e -> {
        node.setScaleX(1.05);
        node.setScaleY(1.05);
        node.setEffect(shadow);
        node.setStyle(node.getStyle() + "; -fx-cursor: hand;");
    });

    node.setOnMouseExited(e -> {
        node.setScaleX(1);
        node.setScaleY(1);
        node.setEffect(null);
    });

}
}










// List<ProfileCardModel> allProfiles = new ArrayList<>();

// // Fetch and convert owners
// for (OwnerDashModel owner : FetchOwnerProfiles.getAllOwners()) {
//     String imageUrl = (owner.getProfileImageUrl() != null && !owner.getProfileImageUrl().isEmpty()) ?
//                       owner.getProfileImageUrl().get(0) :
//                       "assets/images/LiveMate_Logo.png";
//     allProfiles.add(new ProfileCardModel(
//         owner.name,
//         owner.address,
//         "₹" + owner.rent + "/month",
//         imageUrl
//     ));
// }

// // Fetch and convert bachelors
// for (BachelorDashModel bachelor : FetchBachelorProfiles.getAllBachelors()) {
//     String imageUrl = (bachelor.getProfileImageUrl() != null && !bachelor.getProfileImageUrl().isEmpty()) ?
//                       bachelor.getProfileImageUrl().get(0) :
//                       "assets/images/LiveMate_Logo.png";
//     allProfiles.add(new ProfileCardModel(
//         bachelor.name,
//         bachelor.address,
//         "₹" + bachelor.rent + "/month",
//         imageUrl
//     ));
// }

// // Shuffle list to randomize order
// Collections.shuffle(allProfiles);

// // Create dynamic cards
// VBox cardContainer = new VBox(30);
// cardContainer.setAlignment(Pos.TOP_CENTER);
// cardContainer.setPadding(new Insets(20));

// int cardsPerRow = 2;
// HBox currentRow = new HBox(30);
// currentRow.setAlignment(Pos.CENTER);

// for (int i = 0; i < allProfiles.size(); i++) {
//     ProfileCardModel p = allProfiles.get(i);
//     HBox card = createProfile(p.name, p.address, p.rent, p.imageUrl);
//     currentRow.getChildren().add(card);

//     if ((i + 1) % cardsPerRow == 0 || i == allProfiles.size() - 1) {
//         cardContainer.getChildren().add(currentRow);
//         currentRow = new HBox(30);
//         currentRow.setAlignment(Pos.CENTER);
//     }
// }
