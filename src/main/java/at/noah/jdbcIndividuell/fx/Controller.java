package at.noah.jdbcIndividuell.fx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setOnMouseClicked(mouseEvent -> {
            Runtime rt = Runtime.getRuntime();
            String totallyNotARickRoll = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
            try {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + totallyNotARickRoll);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
