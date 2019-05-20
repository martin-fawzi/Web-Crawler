package project.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import project.model.MyImageView;
import project.model.SaveImages;

public class ImageViewerController implements Initializable {

    private File[] listofimages;
    private int index = 0;
    @FXML
    private StackPane imagestackpane;
    @FXML
    private ScrollPane imageslider;
    @FXML
    private ImageView mainImage;
    @FXML
    private Button right;
    @FXML
    private Button left;
    @FXML
    private TilePane tilePane;

    public void leftbtn() {
        if (index > 0) {
            Image img = new Image(listofimages[--index].toURI().toString());
            ImageView mainImage = new ImageView(img);
            if (img.getHeight() > 540 || img.getWidth() > 960) {
                mainImage.setFitHeight(imagestackpane.getHeight());
                mainImage.setFitWidth(imagestackpane.getWidth());
            }
            imagestackpane.getChildren().clear();
            imagestackpane.getChildren().add(mainImage);
        }
    }

    public void rightbtn() {
        if (index < listofimages.length) {
            Image img = new Image(listofimages[++index].toURI().toString());
            ImageView mainImage = new ImageView(img);
            if (img.getHeight() > 540 || img.getWidth() > 960) {
                mainImage.setFitHeight(imagestackpane.getHeight());
                mainImage.setFitWidth(imagestackpane.getWidth());
            }
            imagestackpane.getChildren().clear();
            imagestackpane.getChildren().add(mainImage);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File file = SaveImages.downloadPath;
        listofimages = file.listFiles();
        for (int i = 0; i < listofimages.length; i++) {
            Image image = new Image(listofimages[i].toURI().toString(), 100, 100, true, true);
            MyImageView imageview = new MyImageView(image);
            imageview.setImagePath(listofimages[i].toURI().toString());
            tilePane.getChildren().addAll(imageview);
            addEventToImageView(imageview);
        }
        Image img = new Image(listofimages[0].toURI().toString());
        setImage(img);
        imageslider.setContent(tilePane);
        imagestackpane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case RIGHT:
                    rightbtn();
                    break;
                case LEFT:
                    leftbtn();
                    break;
            }
        });
    }

    public void addEventToImageView(ImageView img) {
        img.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Image img = new Image(((MyImageView) event.getSource()).getImagePath());
                setImage(img);
            }
        });
    }

    public void setImage(Image img) {
        mainImage = new ImageView(img);
        if (img.getHeight() > 540 || img.getWidth() > 960) {
            mainImage.setFitHeight(imagestackpane.getHeight());
            mainImage.setFitWidth(imagestackpane.getWidth());
        }
        imagestackpane.getChildren().clear();
        imagestackpane.getChildren().add(mainImage);
    }
}
