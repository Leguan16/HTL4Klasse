package at.noah.wahl.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/layout/wahl.fxml")));
        stage.setTitle("Wahlprogramm");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
