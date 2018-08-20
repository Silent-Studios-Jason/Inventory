/*
Author:     Jason Smith
Project:    Inventory Management
Date:       8-17-17
WGU Class:  C482
 */
package classes.files;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jason Smith
 */
public abstract class MotherClass { //THIS CLASS IS USED FOR INHERITANCE BOTH PARTS AND PRODUCTS HAVE THIS STUFF AND CAN USE SAME METHODS
//PROPERTY DATA TYES USED FOR OBSERVELIST AND TABLEVIEWS

    private StringProperty name;
    private IntegerProperty inventoryCount;
    private DoubleProperty price;
    private IntegerProperty maxInventory;
    private IntegerProperty minInventory;

    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public IntegerProperty getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(Integer inventoryCount) {
        this.inventoryCount = new SimpleIntegerProperty(inventoryCount);
    }

    public DoubleProperty getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    public IntegerProperty getMaxInventory() {
        return maxInventory;
    }

    public void setMaxInventory(Integer maxInventory) {
        this.maxInventory = new SimpleIntegerProperty(maxInventory);
    }

    public IntegerProperty getMinInventory() {
        return minInventory;
    }

    public void setMinInventory(Integer minInventory) {
        this.minInventory = new SimpleIntegerProperty(minInventory);
    }

}
