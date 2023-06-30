package lk.ijse.dep10.filecopyapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.File;

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
    private File sourceFile;
    private File targetFolder;
    private boolean moveFiles = false;

    public void initialize() {
        btnCopy.setDisable(true);
        btnMove.setDisable(true);
        btnDelete.setDisable(true);
        txtSource.setEditable(false);
        txtDestination.setEditable(false);
    }

    @FXML
    void btnSourceBrowseOnAction(ActionEvent event) {
        resetProgress();
        txtSource.setText("");
        sourceFile = null;
        moveFiles = false;
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setDialogTitle("Select a file or directory");
        chooser.showOpenDialog(null);
        sourceFile = chooser.getSelectedFile();
        enableButtons();
        if (sourceFile == null) return;
        txtSource.setText(chooser.getSelectedFile().toString());

    }
    private void enableButtons() {
        Button[] buttons = {btnCopy, btnMove};
        for (Button button : buttons) {
            button.setDisable(sourceFile == null || targetFolder == null ||
                    sourceFile.getParentFile().equals(targetFolder));
        }
        btnDelete.setDisable(sourceFile == null);
    }
    private void resetProgress() {
        btnSourceBrowse.getScene().getWindow().setHeight(200);
        prgBar.progressProperty().unbind();
        prgBar.setProgress(0);
        lblPercentage.textProperty().unbind();
        lblPercentage.setText("0.00%");
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

}
