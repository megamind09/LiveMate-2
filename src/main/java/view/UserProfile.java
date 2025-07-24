package view;

import com.clutchcoders.Model.BachelorDashModel;
import com.clutchcoders.Model.OwnerDashModel;
import com.clutchcoders.dao.ProfileDao;
import com.clutchcoders.dao.SessionData;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class UserProfile {

    Stage primaryStage;
    Scene profileScene;
    
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setProfileScene(Scene profileScene) {
        this.profileScene = profileScene;
    }

    public Parent createUserProfileScene(com.clutchcoders.Model.UserProfileModel user, Runnable ProfileThread) {
 



        Image backImg = new Image("assets/images/back.png");
        ImageView backIcon = new ImageView(backImg);
        backIcon.setFitWidth(40);
        backIcon.setFitHeight(40);

        Button backButton = new Button();
        backButton.setGraphic(backIcon);
        backButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        backButton.setOnAction(e -> {
            System.out.println("Back button is clicked");
            ProfileThread.run();
        });

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f4f8, #dfe9f3);");

        Label heading = new Label("👤 User Profile");
        heading.setFont(Font.font("Arial", 30));
        heading.setTextFill(Color.DARKSLATEGRAY);

        // Fetch from session
        // Use passed user
            String email = user.email;
            boolean isOwner = user.isOwner;


        // Fetch data from Firestore
        BachelorDashModel bachelorProfile = null;
        OwnerDashModel ownerProfile = null;
        if (isOwner) {
            ownerProfile = ProfileDao.getOwnerProfile(email);
        } else {
            bachelorProfile = ProfileDao.getBachelorProfile(email);
        }

        // No data found
        if ((isOwner && ownerProfile == null) || (!isOwner && bachelorProfile == null)) {
            root.getChildren().add(new Label("❌ No profile data found for " + email));
            return root;
        }

        // Extract fields
        String name = isOwner ? ownerProfile.name : bachelorProfile.name;
        String contact = isOwner ? ownerProfile.contact : bachelorProfile.contact;
        String gender = isOwner ? ownerProfile.gender : bachelorProfile.gender;
        String type = isOwner ? ownerProfile.propertyType : bachelorProfile.propertyType;
        String state = isOwner ? ownerProfile.state : bachelorProfile.state;
        String district = isOwner ? ownerProfile.district : bachelorProfile.district;
        String city = isOwner ? ownerProfile.city : bachelorProfile.city;
        String address = isOwner ? ownerProfile.address : bachelorProfile.address;
        String pincode = isOwner ? ownerProfile.pincode : bachelorProfile.pincode;
        String mapLink = isOwner ? ownerProfile.mapLink : bachelorProfile.mapLink;
        String rent = isOwner ? ownerProfile.monthlyRent : bachelorProfile.monthlyRent;
        String deposit = isOwner ? ownerProfile.depositAmount : bachelorProfile.depositAmount;
        String maintenance = isOwner ? ownerProfile.maintenanceFee : bachelorProfile.maintenanceFee;
        List<String> amenities = isOwner ? ownerProfile.amenities : bachelorProfile.amenities;
        List<String> features = isOwner ? ownerProfile.features : bachelorProfile.features;
        List<String> preferences = isOwner ? ownerProfile.preferences : bachelorProfile.preferences;
        List<String> photos = isOwner ? ownerProfile.photoUrls : bachelorProfile.photoUrls;

        // Profile Picture
        ImageView profilePic = new ImageView();
        if (photos != null && !photos.isEmpty()) {
            profilePic.setImage(new Image(photos.get(0), true));
        } else {
            profilePic.setImage(new Image("assets/images/profile.png")); // default
        }
        profilePic.setFitWidth(150);
        profilePic.setFitHeight(150);
        profilePic.setPreserveRatio(true);
        profilePic.setClip(new Circle(75, 75, 75));
        profilePic.setStyle("-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.3), 8, 0, 0, 4);");

        // Info Grid
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(20);
        infoGrid.setVgap(10);
        infoGrid.setPadding(new Insets(20));
        infoGrid.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 4);");

        addRow(infoGrid, 0, "Name:", name);
        addRow(infoGrid, 1, "Email:", email);
        addRow(infoGrid, 2, "Contact:", contact);
        addRow(infoGrid, 3, "Gender:", gender);
        addRow(infoGrid, 4, isOwner ? "Property Type:" : "Occupation:", type);
        addRow(infoGrid, 5, "State:", state);
        addRow(infoGrid, 6, "District:", district);
        addRow(infoGrid, 7, "City:", city);
        addRow(infoGrid, 8, "Address:", address);
        addRow(infoGrid, 9, "Pincode:", pincode);
        addRow(infoGrid, 10, "Google Map Link:", mapLink);
        addRow(infoGrid, 11, "Monthly Rent:", rent);
        addRow(infoGrid, 12, "Deposit:", deposit);
        addRow(infoGrid, 13, "Maintenance Fee:", maintenance);
        addRow(infoGrid, 14, "Amenities:", String.join(", ", amenities));
        addRow(infoGrid, 15, "Features:", String.join(", ", features));
        addRow(infoGrid, 16, "Preferences:", String.join(", ", preferences));

        // Photo Gallery
        FlowPane gallery = new FlowPane();
        gallery.setHgap(10);
        gallery.setVgap(10);
        gallery.setPadding(new Insets(10));
        if (photos != null) {
            for (String url : photos) {
                ImageView img = new ImageView(new Image(url, true));
                img.setFitWidth(120);
                img.setFitHeight(90);
                img.setPreserveRatio(true);
                img.setStyle("-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.2), 4, 0, 0, 2);");
                gallery.getChildren().add(img);
            }
        }

        Label galleryLabel = new Label("📸 Uploaded Photos");
        galleryLabel.setFont(Font.font("Arial", 20));

        root.getChildren().addAll(heading, profilePic, infoGrid, galleryLabel, gallery);
        return new ScrollPane(root);
    }

    private void addRow(GridPane grid, int rowIndex, String label, String value) {
        Label key = new Label(label);
        key.setFont(Font.font("Arial", 16));
        key.setTextFill(Color.DARKSLATEGRAY);

        Label val = new Label(value != null && !value.isEmpty() ? value : "N/A");
        val.setFont(Font.font("Arial", 16));
        val.setTextFill(Color.BLACK);

        grid.add(key, 0, rowIndex);
        grid.add(val, 1, rowIndex);
    }
}
