package at.noah.jdbc.jdbcIndividuell.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/layout/jdbcIndividuell.fxml")));
        stage.setTitle("Geräteverwaltung");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
