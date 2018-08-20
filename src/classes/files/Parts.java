/*
Author:     Jason Smith
Project:    Inventory Management
Date:       8-17-17
WGU Class:  C482
 */
package classes.files;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jason Smith
 */
public class Parts extends MotherClass {//INHERITS METHODS THAT BOTH PART AND PRODUCT SHARE SUCH AS NAME PRICE AND SO ON
//USE PROPERTY DATA TYPES FOR OBSERVELIST AND TABLEVIEWS

    private StringProperty partSource;
    private IntegerProperty ID;
    private String message = null;//USED FOR VALIDATION MESSAGE
    //THIS SETS THE IN HOUSE OR OUTSOURCED DATA
    private boolean isInHouse;

    public Parts(StringProperty name, IntegerProperty inventoryCount, DoubleProperty price, IntegerProperty maxInventory, IntegerProperty minInventory, StringProperty partSource, IntegerProperty ID) {
        this.partSource = partSource;
        this.ID = ID;
    }

    public Parts() {

    }

    public StringProperty getPartSource() { //PART SOURCE IS FOR THE SOURCE OF THE PART IN HOUSE OR OUTSOURCED
        return partSource;
    }

    public void setPartSource(String partSource) {
        this.partSource = new SimpleStringProperty(partSource);
    }

    public IntegerProperty getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = new SimpleIntegerProperty(ID);
    }

    public boolean testValidInventoryNumbers() {//TEST THE DATA AGAINST PROVIDED SETS
        //USED TO THROW THE VALIDATION POPUP
        boolean isValid = true;
        int caseMessage;
//THIS WILL TEST THE INVNETORY NUMBERS FOR CORRECTNESS USE IFS RETURN BOOLEAN SET MESSAGE INT
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

        return isValid;
    }

    public String getMessage() {

        return message;
    }

    public String setMessage(int caseMessage) { //SET THE MESSAGE TO DISPLAY
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
            default:
                break;
        }

        return message;
    }

    public boolean getIsInHouse() {//JUST IN HOUSE IS USED BECAUSE IF NOT TRUE IT IS IMPLIED THAT OUTSOURCED IS TRUE
        return isInHouse;
    }

    public void setIsInHouse(boolean isInHouse) {
        this.isInHouse = isInHouse;
    }

}
