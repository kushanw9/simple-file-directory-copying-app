package lk.ijse.dep10.filecopyapp.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

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
    void btnCopyOnAction(ActionEvent event) throws IOException {
        File targetFile = new File(targetFolder, sourceFile.getName());
        if (targetFile.exists()) {
            Optional<ButtonType> optResult = new Alert(Alert.AlertType.CONFIRMATION,
                    "File already exists, are you sure to replace the file?",
                    ButtonType.YES, ButtonType.NO).showAndWait();
            if (optResult.isEmpty() || optResult.get() == ButtonType.NO) {
                return;
            }
        }
        btnCopy.getScene().getWindow().setHeight(280);

        if (sourceFile.isDirectory()) {
            copyDirectory(sourceFile,targetFile);


        } else {
            copyFiles(sourceFile,targetFile);
        }

    }
    private void copyDirectory(File sourceFile, File targetDirectory) throws IOException {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                if (!targetDirectory.exists()) {
                    targetDirectory.mkdir();
                }
                File[] files = sourceFile.listFiles();
                double totalSize = getTotalSize(files);
                double write = 0.0;

                for (File file : files) {
                    if (file.isDirectory()) {
                        copyDirectory(file, new File(targetDirectory, file.getName()));

                    } else {

                        FileInputStream fis = new FileInputStream(file);
                        FileOutputStream fos = new FileOutputStream(new File(targetDirectory, file.getName()));

                        while (true) {
                            byte[] buffer = new byte[1024 * 1024 * 2];
                            int read = fis.read(buffer);
                            write += read;

                            if (read == -1) break;
                            fos.write(buffer, 0, read);


                            updateMessage(String.format("%2.2f", write / totalSize * 100).concat("% Complete"));
                            updateProgress(write,totalSize);
                        }
                        fis.close();
                        fos.close();
                    }
                }
                if (moveFiles == true) {

                    updateMessage("100.00% Complete");
                    btnDelete.fire();
                }
                return null;
            }
        };
        lblPercentage.textProperty().bind(task.messageProperty());
        prgBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private void copyFiles(File sourceFile, File targetFile) throws IOException {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                FileInputStream fis = new FileInputStream(sourceFile);
                FileOutputStream fos = new FileOutputStream(targetFile);
                double write = 0.0;
                while (true) {
                    byte[] buffer = new byte[1024 * 1024 * 5];
                    int read = fis.read(buffer);
                    write += read;
                    System.out.println(write);
                    if (read == -1) break;
                    fos.write(buffer, 0, read);
                    updateMessage(String.format("%2.2f", write / sourceFile.length() * 100).concat("% Complete"));
                    updateProgress(write,sourceFile.length());
                }
                fis.close();
                fos.close();
                if (moveFiles == true) {
                    btnDelete.fire();
                }
                return null;
            }
        };
        lblPercentage.textProperty().bind(task.messageProperty());
        prgBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private double getTotalSize(File[] files) {
        double total = 0;

        for (File file : files) {
            if (file.isDirectory()) {
                total += getTotalSize(file.listFiles());
            } else {
                total += file.length();
            }
        }
        return total;
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
