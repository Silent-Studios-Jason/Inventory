/*
Author:     Jason Smith
Project:    Inventory Management
Date:       8-17-17
WGU Class:  C482
 */
package Views;

import classes.files.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jason Smith
 */
public class mainFXMLController implements Initializable {

    //THIS CONTROLLER IS FOR MENUBAR AND CLOSE BUTTON MAIN IS HOLDER FOR OTHER SCENES
    @FXML
    private MenuItem closeItem;

    @FXML
    private MenuItem addPartMenuItem;

    @FXML
    private MenuItem addProductItem;

    @FXML
    private MenuItem aboutItem;

    private Main main;
    private Stage mainFXMLStage;

    @FXML
    private void closeBtnClicked(ActionEvent event) {
        Platform.exit();
        System.exit(0);

    }

    public void setStage(Stage stage) {
        this.mainFXMLStage = stage;
    }

    @FXML
    private void addPartMenuItemClicked(ActionEvent event) throws IOException {
        main.showAddPart();

    }

    @FXML
    private void addProductMenuItemClicked(ActionEvent event) throws IOException {
        main.showAddProduct();
    }

    @FXML
    private void aboutMenuItemClicked(ActionEvent event) throws IOException {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("About");
        dialogStage.setMinHeight(175);
        dialogStage.setMinWidth(310);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        VBox pane = new VBox(50);
        pane.setPrefSize(310, 195);
        Button closeBtn = new Button("Close");
        Label message = new Label();
        message.setText("\n"
                + "Author: Jason Smith \n"
                + "Creation Date: 8-17-17 \n"
                + "Application: Inventory Management System \n"
                + "Purpose: WGU software 1 project \n"
                + "Website: " + "http://www.silentstudiosmediagroup.com");
        message.setAlignment(Pos.CENTER);
        closeBtn.setAlignment(Pos.BOTTOM_CENTER);

        pane.getChildren().add(message);
        pane.getChildren().add(closeBtn);
        pane.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(pane);
        closeBtn.setOnAction(e -> dialogStage.close());
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
