package project.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import project.model.Crawler;
import project.model.ImageSearch;
import project.model.WordSearch;

public class WordsCrawlerController implements Initializable {

    private changescene scene = new changescene();

    @FXML
    private TextField URLTf;
    @FXML
    private TextArea URLTa;

    public void backButtonAction(ActionEvent event) throws IOException {
        scene.change(event, "FXMLDocument.fxml");
    }

    public void searchButtonAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input");
        dialog.setHeaderText("Please Enter The Word:");
        dialog.setResizable(false);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Thread thread = new Thread(new WordSearch(URLTa, result.get()));
            thread.start();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        URLTf.setText(Crawler.URL);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setTitle("Timer");
        dialog.setContentText("please enter minutes:");
        dialog.setResizable(false);
        Optional<String> time = dialog.showAndWait();
        if (time.isPresent()) {
            Thread thread = new Thread(new Crawler(1, this.URLTa, Integer.parseInt(time.get())));
            thread.start();
        }
    }
}
