/*
Author:     Jason Smith
Project:    Inventory Management
Date:       8-17-17
WGU Class:  C482
 */
package classes.files;

import Views.AddPartController;
import Views.AddProductController;
import Views.ModifyPartController;
import Views.ModifyProductController;
import Views.mainFXMLController;
import Views.mainTablesController;
import java.io.IOException;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jason Smith
 */
public class Main extends Application {

    //DECLARE VARIABLES
    private Stage primaryStage;
    private BorderPane mainLayout;
    //THIS LIST IS FOR THE MAIN PARTS TABLE
    private ObservableList<Parts> partData = FXCollections.observableArrayList();
    //THIS LIST IS FOR THE MAIN PRODUCTS TABLE
    private ObservableList<Products> productData = FXCollections.observableArrayList();
    //THIS LIST IS FOR TRANSFERING THE ASSOCIATED PARTS TO BE MODIFIED WITH THE PRODUCT
    private ObservableList<Products> modifyProductData = FXCollections.observableArrayList();
    //THIS IS FOR THE AUTO ID OF A PART    
    private int autogeneratedID = partData.size();
    //THIS IS FOR THE AUTO ID OF A PRODUCT
    private int productAutoId = productData.size();
    //THIS IS USED FOR THE GET YES OR NO METHOD THAT IS USED TO ANSWER DELETION DIALOG POPUP
    private boolean delete;

    /*
    THESE ARE USED FOR MODIFY SET AND GET METHOD FOR PARTS AND PRODUCTS
    CONTROLLERS CALL METHOD ON SAVE BUTTON CLICK AND ASSIGN PROPERTIES SO THEY CAN
    TRANSFER TO OTHER CONTROLLERS FROM MAIN
     */
    private StringProperty name;
    private IntegerProperty inventoryCount;
    private DoubleProperty price;
    private IntegerProperty maxInventory;
    private IntegerProperty minInventory;
    private StringProperty source;
    private IntegerProperty ID;

    public int getProductAutoId() {//PRODUCT ID
        return productAutoId;
    }

    public void setProductAutoId(int productAutoId) {//PRODUCT ID
        this.productAutoId = productAutoId;
    }

    public int getAutogeneratedID() {//PART ID
        return autogeneratedID;
    }

    public void setAutogeneratedID(int autogeneratedID) {//PART ID
        this.autogeneratedID = autogeneratedID;
    }

    public ObservableList<Products> getModifyProductData() {
        return modifyProductData;
    }

    public void setModifyProductData(ObservableList<Products> modifyProductData) {  //THIS NEEDS A SET METHOD SO MODIFY CONTROLLER CAN CLEAR ON EXIT
        this.modifyProductData = modifyProductData;
    }

    public ObservableList<Parts> getPartData() {
        return partData;
    }

    public ObservableList<Products> getProductData() {
        return productData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Inventory Management");

        createLayout();
        showMain();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void createLayout() {
        try {
            //THIS METHOD LOADS THE HOLDER FOR THE REST OF THE FXML VIEWS
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/mainFXML.fxml"));
            //THIS VIEW HAS THE MENU BAR ASSOCIATED WITH IT SO IT CAN TRAVERSE THE SCENES
            mainLayout = (BorderPane) loader.load();
            //ASSIGN LAYOUT NODE TO SCENE
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            //SET UP CONTROLLER TO GAIN ACCESS TO THE CONTROLLING CLASS EX. CONTROLLER.GETNAME()
            mainFXMLController controller = loader.getController();
            //SET MAIN GIVES CONTROLLER ACCESS TO THE MAIN CLASS
            controller.setMain(this);
        } catch (IOException e) {
            showWarning(e.getMessage());
        }
    }

    private void showMain() {
        try {
            //THIS SHOWS THE TWO MAIN TABLES AND REALLY THE MAIN SCREEN
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/mainTables.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            //SET THE VIEW TO TAKE UP THE CENTER OF THE MAIN BORDERPANE
            mainLayout.setCenter(pane);

            mainTablesController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException error) {
            showWarning(error.getMessage());
        }
    }

    public void showAddPart() {
        try {
            //CALLED TO SHOW POPUP OF ADD PART DIALOG
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/addPart.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Part");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            AddPartController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setMain(this);
            //SHOW THE DIALOG AND WAIT FOR RESPONSE
            dialogStage.showAndWait();

        } catch (IOException e) {
            showWarning(e.getMessage());
        }
    }

    public void showAddProduct() {
        try {
            //THIS SHOWS THE ADD PRODUCT DIALOG
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/addProduct.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            AddProductController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setMain(this);

            dialogStage.showAndWait();

        } catch (IOException e) {
            showWarning(e.getMessage());
        }
    }

    public void showModifyPart() {
        try {
            //THIS SHOWS THE MODIFY POPUP FOR PART
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/modifyPart.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modify Part");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            ModifyPartController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setMain(this);
            //CONTROLLER IS CALLED TO SETUP INITAL VALUES SETUP FIELDS IS METHOD IN CONTROLLER CLASS
            controller.setupFields(name, inventoryCount, ID, price, source, maxInventory, minInventory);
            dialogStage.showAndWait();

        } catch (IOException e) {
            showWarning(e.getMessage());
        }
    }

    public void showModifyProduct() {
        try {
            //SHOW THE SCENE FOR PRODUCT MODIFICATION
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/modifyProduct.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modify Product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            ModifyProductController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setMain(this);
            //CONTROLLER IS CALLED TO SETUP INITAL VALUES SETUP FIELDS IS METHOD IN CONTROLLER CLASS
            controller.setupFields(name, inventoryCount, ID, price, maxInventory, minInventory);
            dialogStage.showAndWait();

        } catch (IOException e) {
            showWarning(e.getMessage());
        }
    }

    public void showWarning(String dialogMessage) {
        //THIS IS CALLED FROM MULTIPLE AREAS AS A GENERIC POPUP USING A STRING PASSED IN
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Warning!");
        dialogStage.setMinHeight(125);
        dialogStage.setMinWidth(310);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        VBox pane = new VBox(10);
        pane.setPrefSize(310, 125);
        Button closeBtn = new Button("Close");
        Label message = new Label();
        message.setText(dialogMessage);
        message.setAlignment(Pos.CENTER);
        closeBtn.setAlignment(Pos.BOTTOM_CENTER);

        pane.getChildren().add(message);
        pane.getChildren().add(closeBtn);
        pane.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(pane);
        //LAMEDA SET THE ACTION FOR CLOSE BUTTON
        closeBtn.setOnAction(e -> dialogStage.close());
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    public void showDeleteWarning(String dialogMessage) {
        //CALLED FROM MULTIPLE AREAS AS GENERIC DELETE POPUP USING PASSED IN STRING
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Warning!");
        dialogStage.setMinHeight(125);
        dialogStage.setMinWidth(310);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        VBox pane = new VBox(10);
        pane.setPrefSize(310, 125);
        Button closeBtn = new Button("No");
        Button okayBtn = new Button("Yes");
        Label message = new Label();
        message.setText(dialogMessage);
        message.setAlignment(Pos.CENTER);
        HBox hpane = new HBox(10);
        okayBtn.setAlignment(Pos.BOTTOM_LEFT);
        closeBtn.setAlignment(Pos.BOTTOM_RIGHT);

        pane.getChildren().add(message);
        pane.getChildren().add(hpane);
        hpane.getChildren().addAll(okayBtn, closeBtn);
        hpane.setAlignment(Pos.BOTTOM_CENTER);
        pane.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(pane);
        //SET THE ON CLICK ACTIONS
        okayBtn.setOnAction((ActionEvent e) -> {
            setYesOrNo(true);
            dialogStage.close();
        });
        closeBtn.setOnAction((ActionEvent e) -> {
            setYesOrNo(false);
            dialogStage.close();
        });
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

    }

    //THE GET YES NO METHOD ARE USED TWO ANSWER THE GENERIC DELETE POPUP
    public boolean getYesOrNo() {
        return delete;
    }

    public void setYesOrNo(boolean delete) {
        this.delete = delete;
    }

    public void setPartToModify(Parts selectedPart) { // THIS IS TWO PASS THE SELECTED PART TO MAIN THEN INTO NEW CONTROLLER

        name = selectedPart.getName();
        inventoryCount = selectedPart.getInventoryCount();
        price = selectedPart.getPrice();
        maxInventory = selectedPart.getMaxInventory();
        minInventory = selectedPart.getMinInventory();
        source = selectedPart.getPartSource();
        ID = selectedPart.getID();

    }

    public void setProductToModify(Products selectedProduct) {//THIS PASSES SELECTED PRODUCT INTO MAIN SO VALUES ARE PASSED INTO CALLING CONTROLLER

        name = selectedProduct.getName();
        inventoryCount = selectedProduct.getInventoryCount();
        price = selectedProduct.getPrice();
        maxInventory = selectedProduct.getMaxInventory();
        minInventory = selectedProduct.getMinInventory();
        ID = selectedProduct.getID();
        modifyProductData.add(selectedProduct);
    }

}