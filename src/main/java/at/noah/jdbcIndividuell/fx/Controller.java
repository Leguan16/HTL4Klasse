package at.noah.jdbcIndividuell.fx;

import at.noah.jdbcIndividuell.domain.Device;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView<Device> tableViewDevices;

    @FXML
    private Button buttonRemove;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonChangeOwner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewDevices.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        buttonEdit.setOnAction(actionEvent -> {

        });
    }

}
