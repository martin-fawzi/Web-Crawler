package project.view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class changescene {

    public void change(ActionEvent event, String str)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(str));
        Scene scene = new Scene(root);
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }
    

}