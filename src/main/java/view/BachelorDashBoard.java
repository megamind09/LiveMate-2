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

import com.clutchcoders.Model.BachelorDashModel;
import com.clutchcoders.dao.AddBachelorDash;
import com.clutchcoders.dao.PostDAO;
import com.clutchcoders.dao.SessionData;

public class BachelorDashBoard {

    Stage primaryStage;
    Scene bachelorScene,choosebatchScene;

    private VBox photoContainer;
    ImageView profilepic = new ImageView(new Image("assets\\images\\profile.png"));
    private ImageView profilePic = profilepic;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setBachelorScene(Scene bachelorScene) {
        this.bachelorScene = bachelorScene;
    }

    public Parent createBachelorDash(Runnable bachelorBackThread) {

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
            handleBachelorProfileImageUpload((Stage) changePicBtn.getScene().getWindow());
        });

        VBox profileBox = new VBox(10, profilePic, changePicBtn);
        profileBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(30);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e0f7fa, #f8bbd0);");

        Label heading = new Label("\uD83D\uDC64 Bachelor Profile Setup");
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

        ComboBox<String> occupationCombo = new ComboBox<>();
        occupationCombo.getItems().addAll("Student", "Working Professional", "Add...");
        occupationCombo.setOnAction(e -> handleAddOption(occupationCombo));

        TextField stateField = new TextField();
        TextField districtField = new TextField();
        TextField cityField = new TextField();
        TextField addressField = new TextField();
        TextField pincodeField = new TextField();
        TextField mapLinkField = new TextField();

        form.add(new Label("Name:"), 0, 0);         form.add(nameField, 1, 0);
        form.add(new Label("Email:"), 0, 1);        form.add(emailField, 1, 1);  
        form.add(new Label("Contact No.:"), 0, 2); form.add(contactField, 1, 2);
        form.add(new Label("Gender:"), 0, 3);       form.add(genderCombo, 1, 3);
        form.add(new Label("Occupation:"), 0, 4);   form.add(occupationCombo, 1, 4);
        form.add(new Label("State:"), 0, 5);        form.add(stateField, 1, 5);
        form.add(new Label("District:"), 0, 6);     form.add(districtField, 1, 6);
        form.add(new Label("City:"), 0, 7);         form.add(cityField, 1, 7);
        form.add(new Label("Address Line 1:"), 0, 8);form.add(addressField, 1, 8);
        form.add(new Label("Pincode:"), 0, 9);      form.add(pincodeField, 1, 9);
        form.add(new Label("Google Map Link:"), 0, 10); form.add(mapLinkField, 1, 10);

        TitledPane rentPane = styledPane("\uD83D\uDCB0 Rent & Charges", createRentSection());
        TitledPane amenitiesPane = styledPane("\u2705 Amenities", createComboBoxWithAdd("Amenity", "WiFi", "AC", "Parking", "Laundry", "Fridge", "TV", "Power Backup"));
        TitledPane featuresPane = styledPane("\uD83C\uDFE1 Property Features", createComboBoxWithAdd("Feature", "Furnished", "Semi-Furnished", "Single Room", "Shared Room", "Attached Bathroom", "Common Bathroom", null));
        TitledPane preferencePane = styledPane("\uD83D\uDC64 Preferences", createComboBoxWithAdd("Preference", "Only Boys", "Only Girls", "Bachelor", "Family", "Working Professionals", null, null));

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
    // 1. Collect all values
    String name = nameField.getText();
    String email = emailField.getText();
    String contact = contactField.getText();
    String gender = genderCombo.getValue();
    String occupation = occupationCombo.getValue();
    String state = stateField.getText();
    String district = districtField.getText();
    String city = cityField.getText();
    String address = addressField.getText();
    String pincode = pincodeField.getText();
    String mapLink = mapLinkField.getText();

    // 2. Rent fields
            GridPane rentSection = (GridPane) ((TitledPane) root.getChildren().get(3)).getContent();
        TextField rentField = (TextField) rentSection.getChildren().get(1);         // Column 1, Row 0
        TextField depositField = (TextField) rentSection.getChildren().get(3);      // Column 1, Row 1
        TextField maintenanceField = (TextField) rentSection.getChildren().get(5);  // Column 1, Row 2


    String monthlyRent = rentField.getText();
    String depositAmount = depositField.getText();
    String maintenanceFee = maintenanceField.getText();

    // 3. Amenities, Features, Preferences
    List<String> amenities = extractComboBoxValues((VBox) ((TitledPane) root.getChildren().get(4)).getContent());
    List<String> features = extractComboBoxValues((VBox) ((TitledPane) root.getChildren().get(5)).getContent());
    List<String> preferences = extractComboBoxValues((VBox) ((TitledPane) root.getChildren().get(6)).getContent());

    // 4. Photo URLs (in-memory; no upload to Firebase Storage here)
    List<String> photoUrls = photoContainer.getChildren().stream()
        .filter(node -> node instanceof ImageView)
        .map(node -> ((ImageView) node).getImage().getUrl()) // Get image URI
        .toList();

    // 5. Create and fill model
    BachelorDashModel model = new BachelorDashModel(
    name, email, contact, gender, occupation,
    state, district, city, address, pincode, mapLink,
    monthlyRent, depositAmount, maintenanceFee,
    amenities, features, preferences, photoUrls
);

    model.name = name;
    model.email = email;
    model.contact = contact;
    model.gender = gender;
    model.propertyType = occupation;
    model.state = state;
    model.district = district;
    model.city = city;
    model.address = address;
    model.pincode = pincode;
    model.mapLink = mapLink;
    model.monthlyRent = monthlyRent;
    model.depositAmount = depositAmount;
    model.maintenanceFee = maintenanceFee;
    model.amenities = amenities;
    model.features = features;
    model.preferences = preferences;
    model.photoUrls = photoUrls;

    // 6. Save to Firestore
    boolean success = AddBachelorDash.putData(model.getMap(), model.email);

    if (success) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Data saved successfully!", ButtonType.OK);
        alert.showAndWait();
        intializeChooseBatch();
        primaryStage.setScene(choosebatchScene);
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save data!", ButtonType.OK);
        alert.showAndWait();
    }
});


        backBtn.setOnAction(e -> {
            bachelorBackThread.run();
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

    //==============================================
    public void handleBachelorProfileImageUpload(Stage stage) {
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
    String uploadedUrl = dao.BachelorImageuploadImage(file);

            // Show confirmation with URL
            System.out.println("✅ Profile Image Uploaded Successfully:");
            System.out.println(uploadedUrl);
        } catch (Exception e) {
            System.err.println("❌ Error uploading profile image:");
            e.printStackTrace();
        }

    }
    }
    //==============================================

    private VBox createComboBoxWithAdd(String labelPrefix, String... options) {
    VBox container = new VBox(10);
    for (String opt : options) {
        if (opt != null) {
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll(options);
            comboBox.getItems().add("Add...");
            comboBox.setValue(opt);
            comboBox.setOnAction(e -> handleAddOption(comboBox));
            container.getChildren().add(comboBox);
        }
    }
    return container;
}


    private void intializeChooseBatch() {
   
       ChooseBachelor chooseBachelor = new ChooseBachelor();
       chooseBachelor.setPrimaryStage(primaryStage);
       primaryStage.setFullScreen(true);
       Rectangle2D screen = Screen.getPrimary().getVisualBounds();
       choosebatchScene = new Scene(chooseBachelor.createScene(this::handleBack),screen.getWidth(),screen.getHeight());
       chooseBachelor.setChooseBatchScene(choosebatchScene);

    }

    private void handleBack() {
        System.out.println("BachelorDash Clicked");
        primaryStage.setScene(bachelorScene);
        return ;
    }

   private List<String> extractComboBoxValues(VBox container) {
    return container.getChildren().stream()
        .filter(node -> node instanceof ComboBox)
        .map(node -> ((ComboBox<?>) node).getValue())
        .filter(val -> val != null && !val.toString().isEmpty())
        .map(Object::toString)
        .toList();
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

    private TitledPane styledPane(String title, Node content) {
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
                    postDAO.BachelorRoomuploadImage(file);
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