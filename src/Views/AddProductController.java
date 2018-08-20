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
public class AddProductController implements Initializable {

    private Main main;
    private Stage addProductStage;
    private int ID;
    //USED TO CREATE A LIST OF PARTS ASSOCIATED WITH PRODUCT 
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

//////////////////BOTTOM TABLE PARTS THAT ARE ADDED TO THE PRODUCT///////////
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

    public void setStage(Stage stage) {
        this.addProductStage = stage;

    }

    @FXML
    private void cancelBtn() {
        message = "\n"
                + "Are you sure you want"
                + "\n"
                + "to cancel adding product?"
                + "\n";
        main.showDeleteWarning(message);
        if (main.getYesOrNo()) {
            addProductStage.close();
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
            ID = main.getProductAutoId();
            ID += 1;
            main.setProductAutoId(ID);
            productObject = new Products();
            productObject.setName(nameField.getText());
            productObject.setInventoryCount(Integer.parseInt(inventoryField.getText()));
            productObject.setPrice(Double.parseDouble(priceField.getText()));
            productObject.setMaxInventory(Integer.parseInt(maxField.getText()));
            productObject.setMinInventory(Integer.parseInt(minField.getText()));
            productObject.setID(ID);
            productObject.setObserveList(productPartsData);

//TEST INVENTORY VALIDATION
            if (productObject.testValidInventoryNumbers()) {//IF TRUE ADD TO MAIN LIST
                main.getProductData().add(productObject);
                addProductStage.close();
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

    public void setMain(Main main) {
        this.main = main;
        partTable.setItems(main.getPartData());

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
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

    @FXML
    public void searchParts() {

        //SETUP THE SEARCH / FILTER
        FilteredList<Parts> filtered = new FilteredList<>(main.getPartData(), e -> true); //SET ALL DATA TO SHOW INITIALLY
        //SET UP THE FILTER CRITERIA FOR THE TEXT BOX
        searchField.textProperty().addListener((data, oldData, newData) -> {
            filtered.setPredicate(part -> {//PREDICATE TELLS THE BOX WHAT IT IS LOOKING FOR
                //CHECK IF TEXTBOX IS EMPTY
                if (newData == null || newData.isEmpty()) {//NEW DATA IS DATA IN SEARCH BOX
                    return true;
                }
                //COMPARE PART NAME
                String partNme = newData.toUpperCase();
                return part.getName().get().toUpperCase().contains(partNme);//RETURN BOOLEAN BASED OFF OF LIST CONTAINS
            });//END PREDICATE LAMEDA
        });//END LISTENER LAMEDA
        //WRAP THE FILTER INTO A SORTED LIST TO DISPLAY ON THE FLY CANNOT BIND FILTEREDLIST
        SortedList<Parts> sortlist = new SortedList<>(filtered);
        //BIND THE LIST TO THE TABLE TO ALLOW DYNAMIC CHANGING OF THE LIST
        sortlist.comparatorProperty().bind(partTable.comparatorProperty());
        //SET TABLE ITEMS
        partTable.setItems(sortlist);

    }

}
