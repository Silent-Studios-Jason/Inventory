/*
Author:     Jason Smith
Project:    Inventory Management
Date:       8-17-17
WGU Class:  C482
 */
package Views;

import classes.files.Main;
import classes.files.Parts;
import classes.files.Products;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jason Smith
 */
public class ModifyProductController implements Initializable {

    private Main main;
    private Stage modifyProductStage;
    private int ID;
    private ObservableList<Parts> productPartsData = FXCollections.observableArrayList();

    @FXML
    private TextField idField, nameField, inventoryField, priceField, maxField, minField, searchField;

    @FXML
    private Button saveBtn, cancelBtn, deleteBtn, searchBtn, addBtn;
    private Products productObject;
    private String dialogMessage;

    @FXML
    private TableView<Parts> partTable;

    @FXML
    private TableColumn<Parts, Integer> allPartID;

    @FXML
    private TableColumn<Parts, String> allPartName;

    @FXML
    private TableColumn<Parts, Integer> allPartInventory;

    @FXML
    private TableColumn<Parts, Integer> allPartMax;

    @FXML
    private TableColumn<Parts, Integer> allPartMin;

    @FXML
    private TableColumn<Parts, Double> allPartPrice;
//OTHER TABLE

    @FXML
    private TableView<Parts> productPartTable;

    @FXML
    private TableColumn<Parts, Integer> newPartID;

    @FXML
    private TableColumn<Parts, String> newPartName;

    @FXML
    private TableColumn<Parts, Integer> newPartInventory;

    @FXML
    private TableColumn<Parts, Double> newPartPrice;

    @FXML
    private TableColumn<Parts, Integer> newPartMax;

    @FXML
    private TableColumn<Parts, Integer> newPartMin;

    String message;

    public void setMain(Main main) {
        this.main = main;
        partTable.setItems(main.getPartData());
        productPartTable.setItems(productPartsData);

    }

    public void setStage(Stage stage) {
        this.modifyProductStage = stage;

    }

    @FXML
    private void cancelBtn() {
        message = "\n"
                + "Are you sure you want"
                + "\n"
                + "to cancel modifying product?"
                + "\n";
        main.showDeleteWarning(message);
        if (main.getYesOrNo()) {
            main.getModifyProductData().clear();
            modifyProductStage.close();
        }
    }

    @FXML
    private void addToProductBtn(ActionEvent event) {
        Parts selected = partTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productPartsData.add(selected);
            productPartTable.setItems(productPartsData);
        } else {
            message = "\n"
                    + "Please select an item"
                    + "\n"
                    + "to add to the product";
            main.showWarning(message);
        }
    }

    @FXML
    private void removeFromProductBtn(ActionEvent event) {
        Parts selected = productPartTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            message = "\n"
                    + "Please select an item"
                    + "\n"
                    + "to remove from the product";
            main.showWarning(message);
        } else {
            message = "\n"
                    + "Are you sure you want"
                    + "\n"
                    + "to remove this part?\n";
            main.showDeleteWarning(message);
            if (main.getYesOrNo()) {
                productPartsData.remove(selected);
            }
        }
    }

    @FXML
    private void saveBtn() {
        try {

            productObject = new Products();
            productObject.setName(nameField.getText());
            productObject.setInventoryCount(Integer.parseInt(inventoryField.getText()));
            productObject.setPrice(Double.parseDouble(priceField.getText()));
            productObject.setMaxInventory(Integer.parseInt(maxField.getText()));
            productObject.setMinInventory(Integer.parseInt(minField.getText()));
            productObject.setID(Integer.parseInt(idField.getText()));
            productObject.setObserveList(productPartsData);
            int index = Integer.parseInt(idField.getText()) - 1;

//TEST INVENTORY VALIDATION
            if (productObject.testValidInventoryNumbers()) {
                main.getModifyProductData().clear();
                main.getProductData().add(index, productObject);//AFTER ADDITION ORIGINAL MOVES 1 TO THE RIGHT
                //REMOVE ORIGINAL
                main.getProductData().remove(index + 1);
                modifyProductStage.close();

            } else {
                dialogMessage = productObject.getMessage();
                main.showWarning(dialogMessage);
            }

        } catch (NumberFormatException error) {
            dialogMessage = ("\n"
                    + "The data you have entered is\n"
                    + "incorrect or is missing.\n"
                    + "Please fix the information");
            main.showWarning(dialogMessage);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //THIS WILL SET UP THE COLUMNS WITH VALUES FROM ADD PART CONTROLLER
        allPartID.setCellValueFactory(partData -> partData.getValue().getID().asObject());
        allPartName.setCellValueFactory(partData -> partData.getValue().getName());
        allPartInventory.setCellValueFactory(partData -> partData.getValue().getInventoryCount().asObject());
        allPartPrice.setCellValueFactory(partData -> partData.getValue().getPrice().asObject());
        allPartMax.setCellValueFactory(partData -> partData.getValue().getMaxInventory().asObject());
        allPartMin.setCellValueFactory(partData -> partData.getValue().getMinInventory().asObject());
        //THIS SET IS FOR THE PARTS THAT ARE NEEDED FOR THE PRODUCT
        newPartID.setCellValueFactory(addedPartData -> addedPartData.getValue().getID().asObject());
        newPartName.setCellValueFactory(addedPartData -> addedPartData.getValue().getName());
        newPartInventory.setCellValueFactory(addedPartData -> addedPartData.getValue().getInventoryCount().asObject());
        newPartPrice.setCellValueFactory(addedPartData -> addedPartData.getValue().getPrice().asObject());
        newPartMax.setCellValueFactory(addedPartData -> addedPartData.getValue().getMaxInventory().asObject());
        newPartMin.setCellValueFactory(addedPartData -> addedPartData.getValue().getMinInventory().asObject());

    }

    public void setupFields(StringProperty name, IntegerProperty inventoryCount, IntegerProperty ID, DoubleProperty price, IntegerProperty maxInventory, IntegerProperty minInventory) {
        //THIS DATA IS PASSED FROM MAIN VIA CONTROLLER
        idField.setText(String.valueOf(ID.get()));
        nameField.setText(name.get());
        inventoryField.setText(String.valueOf(inventoryCount.get()));
        priceField.setText(String.valueOf(price.get()));
        maxField.setText(String.valueOf(maxInventory.get()));
        minField.setText(String.valueOf(minInventory.get()));
        //THIS LOOP GETS THE SELECTED PRODUCT PARTS AND ADDS THEM TO THE LIST OF PARTS
        for (int i = 0; i < main.getModifyProductData().size(); i++) {
            productPartsData.addAll(main.getModifyProductData().get(i).getProductParts());

        }
    }

    @FXML
    public void searchParts() {

        //SETUP THE SEARCH / FILTER
        FilteredList<Parts> filtered = new FilteredList<>(main.getPartData(), e -> true); //SET ALL DATA TO SHOW OR TRUE
        //SET UP THE FILTER CRITERIA FOR THE TEXT BOX
        searchField.textProperty().addListener((data, oldData, newData) -> {//ADD LISTENER TO SEARCH BOX
            filtered.setPredicate(part -> {//WHAT IS THE BOX LOOKING FOR
                //CHECK IF TEXTBOX IS EMPTY
                if (newData == null || newData.isEmpty()) {
                    return true;
                }
                //COMPARE PART NAME
                String partNme = newData.toUpperCase();
                return part.getName().get().toUpperCase().contains(partNme); //IF LIST CONTAINS RETURN TRUE
            });
        });
        //WRAP THE FILTER INTO A SORTED LIST TO DISPLAY ON THE FLY FILTERED LIST IS WRAPPED TO ALLOW BINDING
        SortedList<Parts> sortlist = new SortedList<>(filtered);
        //BIND THE LIST TO THE TABLE TO ALLOW DYNAMIC CHANGING OF THE LIST
        sortlist.comparatorProperty().bind(partTable.comparatorProperty());
        //SET TABLE ITEMS
        partTable.setItems(sortlist);

    }
}
