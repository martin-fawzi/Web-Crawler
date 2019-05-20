package project.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import project.model.Crawler;

public class DeadLinksCrawlerController implements Initializable {

    private changescene scene = new changescene();

    @FXML
    private TextField URLTf;

    @FXML
    private TextArea URLTa;

    public void backButtonAction(ActionEvent event) throws IOException {
        scene.change(event, "FXMLDocument.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        URLTf.setText(Crawler.URL);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Timer");
        dialog.setHeaderText("please enter minutes:");
        dialog.setResizable(false);
        Optional<String> time = dialog.showAndWait();
        if (time.isPresent()) {
            Thread thread = new Thread(new Crawler(3, this.URLTa, Integer.parseInt(time.get())));
            thread.start();
        }
    }
}
