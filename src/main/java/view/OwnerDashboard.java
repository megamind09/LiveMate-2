package view;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.File;
import java.util.List;

import com.clutchcoders.Model.OwnerDashModel;
import com.clutchcoders.dao.AddOwnerDash;
import com.clutchcoders.dao.PostDAO;

public class OwnerDashboard  {

    private ComboBox<String> amenitiesCombo;
    private ComboBox<String> featuresCombo;
    private ComboBox<String> preferencesCombo;


    Stage primaryStage;
    Scene ownerdashScene,roomMatesScene;

    private VBox photoContainer;

    ImageView profilepic = new ImageView(new Image("assets\\images\\profile.png"));
    private ImageView profilePic = profilepic;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setOwnerdashScene(Scene ownerdashScene) {
        this.ownerdashScene = ownerdashScene;
    }

    public Parent createOwnerDash(Runnable ownerdashThread) {
      


         ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);



        // Profile Picture Section
        
        profilePic.setFitWidth(120);
        profilePic.setFitHeight(120);
        profilePic.setPreserveRatio(true);
        profilePic.setClip(new Circle(60, 60, 60));

        Button changePicBtn = new Button("Change Profile Picture");
        changePicBtn.setStyle("-fx-background-color: #039be5; -fx-text-fill: white; -fx-padding: 6 12; -fx-background-radius: 6;");
        changePicBtn.setOnAction(e -> {
            handleOwnerProfileImageUpload((Stage) changePicBtn.getScene().getWindow());
        });

        VBox profileBox = new VBox(10, profilePic, changePicBtn);
        profileBox.setAlignment(Pos.CENTER);


        VBox root = new VBox(30);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e0f7fa, #f8bbd0);");

        Label heading = new Label("\uD83C\uDFE0 Owner Profile Setup");
        heading.setFont(Font.font("Arial", 28));
        heading.setTextFill(Color.DARKSLATEGRAY);
        heading.setAlignment(Pos.CENTER);

        GridPane form = new GridPane();
        form.setHgap(20);
        form.setVgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 4);");

        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField contactField = new TextField();

        ComboBox<String> genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Male", "Female", "Other", "Add...");
        genderCombo.setOnAction(e -> handleAddOption(genderCombo));

        ComboBox<String> propertyTypeCombo = new ComboBox<>();
        propertyTypeCombo.getItems().addAll("Flat", "PG", "Hostel", "Add...");
        propertyTypeCombo.setOnAction(e -> handleAddOption(propertyTypeCombo));

        TextField stateField = new TextField();
        TextField districtField = new TextField();
        TextField cityField = new TextField();
        TextField addressField = new TextField();
        TextField pincodeField = new TextField();
        TextField mapLinkField = new TextField();

        form.add(new Label("Name:"), 0, 0);         form.add(nameField, 1, 0);
        form.add(new Label("Email:"), 0, 1);        form.add(emailField, 1, 1);
        form.add(new Label("Contact No.:"), 0, 2);  form.add(contactField, 1, 2);
        form.add(new Label("Gender:"), 0, 3);       form.add(genderCombo, 1, 3);
        form.add(new Label("Property Type:"), 0, 4);form.add(propertyTypeCombo, 1, 4);
        form.add(new Label("State:"), 0, 5);        form.add(stateField, 1, 5);
        form.add(new Label("District:"), 0, 6);     form.add(districtField, 1, 6);
        form.add(new Label("City:"), 0, 7);         form.add(cityField, 1, 7);
        form.add(new Label("Address Line 1:"), 0, 8);form.add(addressField, 1, 8);
        form.add(new Label("Pincode:"), 0, 9);      form.add(pincodeField, 1, 9);
        form.add(new Label("Google Map Link:"), 0, 10); form.add(mapLinkField, 1, 10);


        amenitiesCombo = createComboBoxWithAdd("Amenity", "WiFi", "AC", "Parking", "Laundry", "Fridge", "TV", "Power Backup");
        featuresCombo = createComboBoxWithAdd("Feature", "Furnished", "Semi-Furnished", "Single Room", "Shared Room", "Attached Bathroom", "Common Bathroom");
        preferencesCombo = createComboBoxWithAdd("Preference", "Only Boys", "Only Girls", "Bachelor", "Family", "Working Professionals");

        TitledPane rentPane = styledPane("💰 Rent & Charges", createRentSection());
        TitledPane amenitiesPane = styledPane("✅ Amenities", new VBox(amenitiesCombo));
        TitledPane featuresPane = styledPane("🏡 Property Features", new VBox(featuresCombo));
        TitledPane preferencePane = styledPane("👤 Preferences", new VBox(preferencesCombo));


        Label photoLabel = new Label("\uD83D\uDCF7 Upload Room Photos:");
        Button uploadButton = new Button("Choose Photos");
        uploadButton.setStyle("-fx-background-color: #0097a7; -fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 6;");

        photoContainer = new VBox(10);
        photoContainer.setPadding(new Insets(10));
        photoContainer.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-background-radius: 8;");
        photoContainer.setPrefHeight(120);

        uploadButton.setOnAction(e -> openFileChooser(primaryStage));

        HBox buttonBar = new HBox(20);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        Button saveBtn = new Button("\uD83D\uDCBE Save");
        Button backBtn = new Button("\uD83D\uDD19 Back");

        saveBtn.setStyle("-fx-background-color: #43a047; -fx-text-fill: white; -fx-padding: 8 20; -fx-font-weight: bold; -fx-background-radius: 8;");
        backBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 8 20; -fx-font-weight: bold; -fx-background-radius: 8;");

  saveBtn.setOnAction(e -> {
    OwnerDashModel profile = new OwnerDashModel();

    // Basic Info
    profile.name = nameField.getText();
    profile.email = emailField.getText();
    profile.contact = contactField.getText();
    profile.gender = genderCombo.getValue();
    profile.propertyType = propertyTypeCombo.getValue();

    // Location Info
    profile.state = stateField.getText();
    profile.district = districtField.getText();
    profile.city = cityField.getText();
    profile.address = addressField.getText();
    profile.pincode = pincodeField.getText();
    profile.mapLink = mapLinkField.getText();

    // Rent Info
    GridPane rentGrid = (GridPane) rentPane.getContent();
    TextField rentField = (TextField) rentGrid.getChildren().get(1);
    TextField depositField = (TextField) rentGrid.getChildren().get(3);
    TextField maintenanceField = (TextField) rentGrid.getChildren().get(5);
    profile.monthlyRent = rentField.getText();
    profile.depositAmount = depositField.getText();
    profile.maintenanceFee = maintenanceField.getText();

    // Amenities, Features, Preferences
    String amenity = amenitiesCombo.getValue();
    profile.amenities = (amenity != null && !amenity.isEmpty()) ? List.of(amenity) : List.of();

    String feature = featuresCombo.getValue();
    profile.features = (feature != null && !feature.isEmpty()) ? List.of(feature) : List.of();

    String preference = preferencesCombo.getValue();
    profile.preferences = (preference != null && !preference.isEmpty()) ? List.of(preference) : List.of();

    // Photos
    profile.photoUrls = photoContainer.getChildren().stream()
        .filter(node -> node instanceof ImageView)
        .map(node -> ((ImageView) node).getImage().getUrl())
        .toList();

    // Save to Firestore
    AddOwnerDash.putData(profile.getMap(), profile.email);
    System.out.println("✅ Profile saved for: " + profile.name);

    // Redirect to roommate scene
    initializeRoomMate();
    primaryStage.setScene(roomMatesScene);
});


        backBtn.setOnAction(e -> {
            ownerdashThread.run();
        });

        buttonBar.getChildren().addAll(backBtn, saveBtn);

        root.getChildren().addAll(
                heading,
                profileBox,
                form,
                rentPane,
                amenitiesPane,
                featuresPane,
                preferencePane,
                photoLabel,
                uploadButton,
                photoContainer,
                buttonBar
        );

        scrollPane.setContent(root);
        return scrollPane;
    }

    //=========================================
    public void handleOwnerProfileImageUpload(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Owner Profile Picture");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg")
    );

    File file = fileChooser.showOpenDialog(stage);

    if (file != null) {
        // Set preview in ImageView
        profilePic.setImage(new Image(file.toURI().toString()));

                ImageView imgView = new ImageView(new Image(file.toURI().toString()));
                imgView.setFitWidth(120);
                imgView.setFitHeight(90);
                imgView.setPreserveRatio(true);
                photoContainer.getChildren().add(imgView);

        // Upload to Firebase using your existing DAO
        PostDAO dao = new PostDAO();
        try {
        // Upload profile image using Firebase DAO
        String uploadedUrl = dao.OwnerProfileuploadImage(file);

            // Show confirmation with URL
            System.out.println("✅ Profile Image Uploaded Successfully:");
            System.out.println(uploadedUrl);
        } catch (Exception e) {
            System.err.println("❌ Error uploading profile image:");
            e.printStackTrace();
        }
    }
}
    //=========================================

    private void initializeRoomMate() {
        Roommate roommate = new Roommate();
        roommate.setPrimaryStage(primaryStage);
        primaryStage.setMaximized(true);
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        roomMatesScene = new Scene(roommate.createRoomMateScene(()->handleBack()),screen.getWidth(),screen.getHeight());
        roommate.setRoomMatesScene(roomMatesScene);
    }

    public void handleBack() {
        primaryStage.setScene(ownerdashScene);
        return ;
    }

   private ComboBox<String> createComboBoxWithAdd(String title, String... items) {
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.getItems().addAll(items);
    comboBox.getItems().add("Add...");
    comboBox.setPromptText("Select or add " + title);
    comboBox.setOnAction(e -> handleAddOption(comboBox));
    return comboBox;
}


    private void handleAddOption(ComboBox<String> comboBox) {
        if ("Add...".equals(comboBox.getValue())) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add New Option");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter new value:");

            dialog.showAndWait().ifPresent(input -> {
                if (!input.trim().isEmpty()) {
                    comboBox.getItems().add(comboBox.getItems().size() - 1, input);
                    comboBox.setValue(input);
                } else {
                    comboBox.setValue(null);
                }
            });
        }
    }

    private GridPane createRentSection() {
        GridPane rentGrid = new GridPane();
        rentGrid.setHgap(15);
        rentGrid.setVgap(10);
        rentGrid.setPadding(new Insets(10));

        TextField rentField = new TextField();
        TextField depositField = new TextField();
        TextField maintenanceField = new TextField();

        rentGrid.add(new Label("Monthly Rent:"), 0, 0);     rentGrid.add(rentField, 1, 0);
        rentGrid.add(new Label("Deposit Amount:"), 0, 1);   rentGrid.add(depositField, 1, 1);
        rentGrid.add(new Label("Maintenance Fee:"), 0, 2);  rentGrid.add(maintenanceField, 1, 2);

        return rentGrid;
    }

    public TitledPane styledPane(String title, Node content) {
        TitledPane pane = new TitledPane(title, content);
        pane.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
        pane.setExpanded(false);
        return pane;
    }


    private void openFileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Room Photos");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", ".jpg", ".jpeg", "*.png")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        if (files != null) {
            photoContainer.getChildren().clear();
            for (File file : files) {
                try{

                    PostDAO postDAO = new PostDAO();
                    postDAO.OwnerRoomuploadImage(file);
                } catch(Exception e){
                    System.out.println("Failedd");
                }
                ImageView imgView = new ImageView(new Image(file.toURI().toString()));
                imgView.setFitWidth(120);
                imgView.setFitHeight(90);
                imgView.setPreserveRatio(true);
                photoContainer.getChildren().add(imgView);
            }
        }
    }
}