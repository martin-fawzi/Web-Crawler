package project.view;

import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import project.model.*;

public class ImageCrawlerController implements Initializable {

    @FXML
    private TextField URLTf;
    @FXML
    private TextArea URLTa;
    private changescene scene = new changescene();
    private DirectoryChooser chooser;
    private boolean saved;

    public void backButtonAction(ActionEvent event) throws IOException {
        scene.change(event, "FXMLDocument.fxml");
    }

    public void chooseFile() throws IOException {
        chooser = new DirectoryChooser();
        chooser.setTitle("Open File");
        File file = chooser.showDialog(URLTa.getScene().getWindow());
        Thread thread = new Thread(new SaveImages(URLTa, file));
        thread.start();
        saved = true;

    }

    public void imageViewer(ActionEvent event) throws IOException {
        if (saved) {
            Parent root = FXMLLoader.load(getClass().getResource("ImageViewer.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Image Viewer");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Save Images");
            alert.setHeaderText(null);
            alert.setContentText("You must save images first");
            alert.showAndWait();
        }
    }

    public void ImageSearch() throws InterruptedException {
        if (saved) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Input");
            dialog.setHeaderText("Please Enter Image name:");
            dialog.setResizable(false);
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                Thread thread = new Thread(new ImageSearch(URLTa, result.get()));
                thread.start();
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Save Images");
            alert.setHeaderText(null);
            alert.setContentText("You must save images first");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        URLTf.setText(Crawler.URL);
        saved = false;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Timer");
        dialog.setHeaderText("please enter minutes:");
        dialog.setResizable(false);
        Optional<String> time = dialog.showAndWait();
        if (time.isPresent()) {
            Thread thread = new Thread(new Crawler(2, this.URLTa, Integer.parseInt(time.get())));
            thread.start();
        }
    }
}
