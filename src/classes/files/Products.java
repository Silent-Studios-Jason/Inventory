/*
Author:     Jason Smith
Project:    Inventory Management
Date:       8-17-17
WGU Class:  C482
 */
package classes.files;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jason Smith
 */
public class Products extends MotherClass {//MOTHER CLASS HAS EVERYTHING THAT PART AND PRODUCT SHARE SUCH AS NAME,PRICE AND SO ON
//SETUP VARIABLES PROPERTY DATA TYPES ARE USED FOR OBSERVELIST CAN PASS VALUES INTO TABLES

    private IntegerProperty ID;
    private String message = null; //THIS IS USED FOR WARNING DIALOG
    //THIS LIST IS USED FOR SAVING THE PARTS THAT BELONG TO EACH PRODUCT INSTANSIATED
    private ObservableList<Parts> productParts = FXCollections.observableArrayList();
    //PRICE IS TRACKED FOR ERROR DETECTION AND DIALOG POPUP
    private double totalPartPrice = 0;

    public Products(IntegerProperty ID) {
        this.ID = ID;
    }

    public Products() {

    }

    public IntegerProperty getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = new SimpleIntegerProperty(ID);
    }

    public boolean testValidInventoryNumbers() { //CALLED FROM CONTROLLER ON SAVE TO CHECK DATA
        int partCount = productParts.size();

        double productPrice = getPrice().doubleValue();

        for (int i = 0; i < partCount; i++) {//ADD UP THE PARTS THAT THIS PRODUCT CONTAINS
            totalPartPrice = getProductParts().get(i).getPrice().get() + totalPartPrice;
        }
        //USED TO THROW A WARNING POPUP
        boolean isValid = true;
        int caseMessage;
//THIS WILL TEST THE INVNETORY NUMBERS FOR CORRECTNESS USE ITS RETURN BOOLEAN SET MESSAGE INT
        if (getInventoryCount().get() > getMaxInventory().get()) {
            caseMessage = 1;
            setMessage(caseMessage);
            isValid = false;
        }
        if (getInventoryCount().get() < getMinInventory().get()) {
            caseMessage = 2;
            setMessage(caseMessage);
            isValid = false;
        }
        if (getMinInventory().get() > getMaxInventory().get()) {
            caseMessage = 3;
            setMessage(caseMessage);
            isValid = false;
        }
        if (getMaxInventory().get() < getMinInventory().get()) {
            caseMessage = 4;
            setMessage(caseMessage);
            isValid = false;
        }
        if (productParts.isEmpty()) {
            caseMessage = 5;
            setMessage(caseMessage);
            isValid = false;
        }

        if (productPrice < totalPartPrice) {
            caseMessage = 6;
            setMessage(caseMessage);
            isValid = false;
        }

        return isValid;
    }

    public String getMessage() {

        return message;
    }

    public String setMessage(int caseMessage) {//SETS MESSAGE ACCORDING TO VALIDATION TEST
        switch (caseMessage) {
            case 1: {
                this.message = "\n"
                        + "Inventory entered is greater \n"
                        + "than the maximum allowed inventory";
                break;
            }
            case 2: {
                this.message = "\n"
                        + "Inventory entered is lower \n"
                        + "than the minimum allowed inventory";
                break;
            }
            case 3: {
                this.message = "\n"
                        + "Minimum entered is greater \n"
                        + "than the maximum field";
                break;
            }
            case 4: {
                this.message = "\n"
                        + "Maximum entered is lower \n"
                        + "than the minimum field";
                break;
            }
            case 5: {
                this.message = "\n"
                        + "There has to be at least"
                        + "\n"
                        + "one part to save product";
                break;
            }
            case 6: {
                this.message = "\n"
                        + "The total of all the parts"
                        + "\n"
                        + "exceeds the price of the product."
                        + "\n"
                        + "The total of parts is : $" + totalPartPrice
                        + "\n"
                        + "Please adjust product price"
                        + "\n";
                break;
            }
            default:
                break;
        }

        return message;
    }

    public void setObserveList(ObservableList<Parts> productPartsData) {
        this.productParts = productPartsData;
    }

    public ObservableList<Parts> getProductParts() {
        return productParts;
    }

}
