package project.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import project.model.Crawler;
import project.model.ShortestPath;

public class FXMLDocumentController implements Initializable {

    private ObservableList<String> items = FXCollections.observableArrayList("Crawl For Words", "Crawl For Images", "Crawl For Dead Links", "Find Shortest Path");

    private changescene scene = new changescene();
    @FXML
    private ComboBox crawlingType;
    @FXML
    public TextField URLTf;

    public void CrawlButtonAction(ActionEvent event) throws IOException {

        switch (items.indexOf(crawlingType.getValue())) {
            case 0:
                Crawler.URL = URLTf.getText();
                scene.change(event, "WordsCrawler.fxml");
                break;
            case 1:
                Crawler.URL = URLTf.getText();
                scene.change(event, "ImageCrawler.fxml");
                break;
            case 2:
                Crawler.URL = URLTf.getText();
                scene.change(event, "DeadLinksCrawler.fxml");
                break;
            case 3:
                ShortestPath.URL = URLTf.getText();
                scene.change(event, "ShortestPath.fxml");
                break;
            default:
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Note");
                alert.setHeaderText(null);
                alert.setContentText("Please choose type of crawling.");
                alert.showAndWait();

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crawlingType.getItems().addAll(items);

    }

}
