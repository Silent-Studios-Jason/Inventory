/*
Author:     Jason Smith
Project:    Inventory Management
Date:       8-17-17
WGU Class:  C482
 */
package Views;

import classes.files.Main;
import classes.files.Parts;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jason Smith
 */
public class ModifyPartController implements Initializable {

    private Main main;
    private Stage modifyPartStage;
    private Toggle selectedToggle;

    @FXML
    private Label partSourceLabel;

    @FXML
    private TextField partNameField, IDField, inventoryField, priceField, partSourceField, minInventoryField, maxInventoryField;

    @FXML
    private Button saveBtn, cancelBtn;
    @FXML
    private RadioButton inHouseField, outSourcedField;

    @FXML
    private ToggleGroup partSourceBtnGroup;
    private Parts partObject;
    private String dialogMessage;
    boolean inHouse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setStage(Stage stage) {
        this.modifyPartStage = stage;

    }

    @FXML
    private void inHouseClicked() {
        partSourceLabel.setText("Machine ID");

    }

    @FXML
    private void outSourcedClicked() {
        partSourceLabel.setText("Company Name");

    }

    @FXML
    private void cancelBtn() {
        dialogMessage = "\n"
                + "Are you sure you want"
                + "\n"
                + "to cancel modifying this part?"
                + "\n";
        main.showDeleteWarning(dialogMessage);
        if (main.getYesOrNo()) {
            modifyPartStage.close();
        }
    }

    @FXML
    private void saveBtn() {
        try {
            partObject = new Parts();
            inHouse = inHouseField.isSelected();
            partObject.setName(partNameField.getText());
            partObject.setInventoryCount(Integer.parseInt(inventoryField.getText()));
            partObject.setPrice(Double.parseDouble(priceField.getText()));
            partObject.setMaxInventory(Integer.parseInt(maxInventoryField.getText()));
            partObject.setMinInventory(Integer.parseInt(minInventoryField.getText()));
            partObject.setPartSource(partSourceField.getText());
            partObject.setID(Integer.parseInt(IDField.getText()));
            partObject.setIsInHouse(inHouse);
            int index = Integer.parseInt(IDField.getText()) - 1;
            //TEST INVENTORY VALIDATION

            if (partObject.testValidInventoryNumbers()) {
                //ADD TO ORIGINAL SLOT IN LIST MOVES ORIGINAL ONE TO THE RIGHT
                main.getPartData().add(index, partObject);
                //DELETE ORIGINAL THAT MOVED IN LIST
                main.getPartData().remove((index + 1));

                modifyPartStage.close();
            } else {
                dialogMessage = partObject.getMessage();
                main.showWarning(dialogMessage);
            }
            //CLEAR ALL THE FIELDS
            partNameField.clear();
            inventoryField.clear();
            priceField.clear();
            partSourceField.clear();
            minInventoryField.clear();
            maxInventoryField.clear();
        } catch (NumberFormatException error) {
            dialogMessage = ("\n"
                    + "The data you have entered is\n"
                    + "incorrect or is missing.\n"
                    + "Please fix the information");
            main.showWarning(dialogMessage);
        }
    }

    public void setupFields(StringProperty name, IntegerProperty inventoryCount, IntegerProperty ID, DoubleProperty price, StringProperty source, IntegerProperty maxInventory, IntegerProperty minInventory) {
        //THESE VALUES ARE PASSED IN FROM MAIN THROUGH THE CONTROLLER
        partNameField.setText(name.get());
        IDField.setText(String.valueOf(ID.get()));
        inventoryField.setText(String.valueOf(inventoryCount.get()));
        priceField.setText(String.valueOf(price.get()));
        partSourceField.setText(source.get());
        if ((main.getPartData().get(ID.get() - 1).getIsInHouse())) {
            inHouseField.setSelected(true);
            outSourcedField.setSelected(false);
            partSourceLabel.setText("Machine ID");
            inHouse = true;
        } else if ((!main.getPartData().get(ID.get() - 1).getIsInHouse())) {
            inHouseField.setSelected(false);
            outSourcedField.setSelected(true);
            partSourceLabel.setText("Company Name");
            inHouse = false;
        }
        minInventoryField.setText(String.valueOf(minInventory.get()));
        maxInventoryField.setText(String.valueOf(maxInventory.get()));
    }
}
