package lk.ijse.dep10.filecopyapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class CopySceneController {
    public Button btnCopy;
    public Button btnDelete;
    public Button btnDestinationBrowse;
    public Button btnMove;
    public Button btnSourceBrowse;
    public Label lblPercentage;
    public ProgressBar prgBar;
    public TextField txtDestination;
    public TextField txtSource;

    public void initialize() {
        btnCopy.setDisable(true);
        btnMove.setDisable(true);
        btnDelete.setDisable(true);

        txtSource.setEditable(false);
        txtDestination.setEditable(false);
    }

    @FXML
    void btnCopyOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnDestinationBrowseOnAction(ActionEvent event) {

    }

    @FXML
    void btnMoveOnAction(ActionEvent event) {

    }

    @FXML
    void btnSourceBrowseOnAction(ActionEvent event) {

    }

}
