package project.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import project.model.Crawler;
import project.model.ShortestPath;
import project.model.WordSearch;

public class ShortestPathController implements Initializable {

    private changescene scene = new changescene();

    @FXML
    private TextField URLTf;
    @FXML
    private TextField URLTf2;
    @FXML
    private TextArea URLTa;
    

    public void backButtonAction(ActionEvent event) throws IOException {
        scene.change(event, "FXMLDocument.fxml");
    }

    public void Pathbtn() {
        ShortestPath.URL2 = URLTf2.getText();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setTitle("Timer");
        dialog.setContentText("please enter minutes:");
        dialog.setResizable(false);
        Optional<String> time = dialog.showAndWait();
        if (time.isPresent()) {
            Thread thread = new Thread(new ShortestPath(URLTa,Integer.parseInt(time.get())));
            thread.start();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        URLTf.setText(ShortestPath.URL);
    }

}
