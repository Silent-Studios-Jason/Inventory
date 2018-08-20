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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Jason Smith
 */
public class mainTablesController implements Initializable {

    private Main main;

////////////////////PART TABLE AND COLUMNS/////////////////
    @FXML
    private TableView<Parts> PartTable;

    @FXML
    private TableColumn<Parts, Integer> partIDColumn;

    @FXML
    private TableColumn<Parts, String> partNameColumn;

    @FXML
    private TableColumn<Parts, Integer> partInventoryColumn;

    @FXML
    private TableColumn<Parts, Double> partPriceColumn;

    @FXML
    private TableColumn<Parts, Integer> partMaxColumn;

    @FXML
    private TableColumn<Parts, Integer> partMinColumn;

    @FXML
    private TableColumn<Parts, String> partSourceColumn;

////////////////////PRODUCT TABLE AND COLUMNS/////////////////
    @FXML
    private TableView<Products> ProductTable;

    @FXML
    private TableColumn<Products, Integer> productIDColumn;

    @FXML
    private TableColumn<Products, Integer> productMaxColumn;

    @FXML
    private TableColumn<Products, Integer> productMinColumn;

    @FXML
    private TableColumn<Products, String> productNameColumn;

    @FXML
    private TableColumn<Products, Integer> productInventoryColumn;

    @FXML
    private TableColumn<Products, Double> productPriceColumn;

    @FXML
    private Button modPartBtn, delProductBtn, delPartBtn, addProductBtn, modProductBtn;

    @FXML
    private TextField partSearch, productSearch;

    @FXML
    private void addPartBtnClicked(ActionEvent event) {
        main.showAddPart();
    }

    @FXML
    private void addProductBtnClicked(ActionEvent event) {
        main.showAddProduct();
    }

    @FXML
    private void deletePart(ActionEvent event) {
        String message;
        Parts selectedPart = PartTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            message = "\n"
                    + "Warning a part is about\n"
                    + "to be deleted.\n"
                    + "Are you sure?\n";
            main.showDeleteWarning(message);
            if (main.getYesOrNo()) {
                main.getPartData().remove(selectedPart);
            }
        } else {
            message = "\n"
                    + "Please make a selection to delete\n";
            main.showWarning(message);
        }
        PartTable.setItems(main.getPartData());

    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        String message;
        Products selectedProduct = ProductTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null && !selectedProduct.getProductParts().isEmpty()) {
            message = "\n"
                    + "There are parts associated"
                    + "\n"
                    + "with this product are you"
                    + "\n"
                    + "sure you want to delete the product?";
            main.showDeleteWarning(message);
            if (main.getYesOrNo()) {
                main.getProductData().remove(selectedProduct);
            }
        } else {
            message = "\n"
                    + "Please make a selection to delete\n";
            main.showWarning(message);
        }
        ProductTable.setItems(main.getProductData());

    }

    @FXML
    private void modifyPart(ActionEvent event) {
        String message;
        Parts selectedPart = PartTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            message = "\n"
                    + "Warning a part is about\n"
                    + "to be Modified.\n"
                    + "Are you sure?\n";
            main.showDeleteWarning(message);
            if (main.getYesOrNo()) {
                //IF THE USER CLICKS YES THEN FILL MODIFY SCREEN WITH SELECTED DATA
                main.setPartToModify(selectedPart);

                main.showModifyPart();
            }
        } else {
            message = "\n"
                    + "Please make a selection to modify\n";
            main.showWarning(message);
        }
        //PartTable.setItems(main.getPartData());
    }

    @FXML
    private void modifyProduct(ActionEvent event) {
        String message;
        Products selectedProduct = ProductTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            message = "\n"
                    + "Warning a product is about\n"
                    + "to be Modified.\n"
                    + "Are you sure?\n";
            main.showDeleteWarning(message);
            if (main.getYesOrNo()) {
                //IF THE USER CLICKS YES THEN FILL MODIFY SCREEN WITH SELECTED DATA
                main.setProductToModify(selectedProduct);

                main.showModifyProduct();
            }
        } else {
            message = "\n"
                    + "Please make a selection to modify\n";
            main.showWarning(message);
        }
        ProductTable.setItems(main.getProductData());
    }

    public void setMain(Main main) {
        this.main = main;
        PartTable.setItems(main.getPartData());
        ProductTable.setItems(main.getProductData());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //THIS WILL SET UP THE COLUMNS WITH VALUES FROM ADD PART CONTROLLER
        partIDColumn.setCellValueFactory(partData -> partData.getValue().getID().asObject());
        partNameColumn.setCellValueFactory(partData -> partData.getValue().getName());
        partInventoryColumn.setCellValueFactory(partData -> partData.getValue().getInventoryCount().asObject());
        partPriceColumn.setCellValueFactory(partData -> partData.getValue().getPrice().asObject());
        partMaxColumn.setCellValueFactory(partData -> partData.getValue().getMaxInventory().asObject());
        partMinColumn.setCellValueFactory(partData -> partData.getValue().getMinInventory().asObject());
        partSourceColumn.setCellValueFactory(partData -> partData.getValue().getPartSource());

        //SET UP THE PRODUCT TABLE
        productIDColumn.setCellValueFactory(productPartData -> productPartData.getValue().getID().asObject());
        productNameColumn.setCellValueFactory(productPartData -> productPartData.getValue().getName());
        productInventoryColumn.setCellValueFactory(productPartData -> productPartData.getValue().getInventoryCount().asObject());
        productPriceColumn.setCellValueFactory(productPartData -> productPartData.getValue().getPrice().asObject());
        productMaxColumn.setCellValueFactory(productPartData -> productPartData.getValue().getMaxInventory().asObject());
        productMinColumn.setCellValueFactory(productPartData -> productPartData.getValue().getMinInventory().asObject());
    }

    @FXML
    public void searchParts() {

        //SETUP THE SEARCH / FILTER
        FilteredList<Parts> filtered = new FilteredList<>(main.getPartData(), e -> true); //SET ALL DATA TO SHOW ON START TO TRUE
        //SET UP THE FILTER CRITERIA FOR THE TEXT BOX
        partSearch.textProperty().addListener((data, oldData, newData) -> { //ADD LISTENER TO THE TEXT BOX
            filtered.setPredicate(part -> {//SET WHAT TO LOOK FOR
                //CHECK IF TEXTBOX IS EMPTY
                if (newData == null || newData.isEmpty()) {
                    return true;
                }
                //COMPARE PART NAME
                String partNme = newData.toUpperCase();
                return part.getName().get().toUpperCase().contains(partNme); //RETRUN TRUE IF CONTAINS
            });//CLOSE PREDICATE 
        });//CLOSE LISTENER
        //WRAP THE FILTER INTO A SORTED LIST TO DISPLAY ON THE FLY NEEDS TO BE WRAPPED TO ALLOW BINDING
        SortedList<Parts> sortlist = new SortedList<>(filtered);
        //BIND THE LIST TO THE TABLE TO ALLOW DYNAMIC CHANGING OF THE LIST
        sortlist.comparatorProperty().bind(PartTable.comparatorProperty());
        //SET TABLE ITEMS
        PartTable.setItems(sortlist);

    }

    @FXML
    public void searchProducts() {

        //SETUP THE SEARCH / FILTER
        FilteredList<Products> filtered = new FilteredList<>(main.getProductData(), e -> true); //SET ALL DATA TO SHOW OR TRUE
        //SET UP THE FILTER CRITERIA FOR THE TEXT BOX
        productSearch.textProperty().addListener((data, oldData, newData) -> {
            filtered.setPredicate(product -> {
                //CHECK IF TEXTBOX IS EMPTY
                if (newData == null || newData.isEmpty()) {
                    return true;
                }
                //COMPARE PART NAME
                String productNme = newData.toUpperCase();
                if (product.getName().get().toUpperCase().contains(productNme)) {
                    return true;
                }
                return false;
            });
        });
        //WRAP THE FILTER INTO A SORTED LIST TO DISPLAY ON THE FLY
        SortedList<Products> sortlist = new SortedList<>(filtered);
        //BIND THE LIST TO THE TABLE TO ALLOW DYNAMIC CHANGING OF THE LIST
        sortlist.comparatorProperty().bind(ProductTable.comparatorProperty());
        //SET TABLE ITEMS
        ProductTable.setItems(sortlist);

    }

}
