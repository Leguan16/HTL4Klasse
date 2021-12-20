package at.noah.wahl.fx;

import at.noah.wahl.reworked.Candidate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView<Candidate> tableView;

    @FXML
    private Button buttonFirstVote;

    @FXML
    private Button buttonSecondVote;

    @FXML
    private Button buttonUndo;

    private static Candidate selected;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tableView.setOnMouseClicked(mouseEvent -> {
            List<Candidate> kandidaten = tableView.getSelectionModel().getSelectedItems();

            if (kandidaten.size() > 0) {
                selected = kandidaten.get(0);
            }
        });

        buttonFirstVote.setOnMouseClicked(mouseEvent -> {
            if (selected == null) {
                return;
            }

            selected.addPoints(2);

        });

        buttonSecondVote.setOnMouseClicked(mouseEvent -> {
            if (selected == null) {
                return;
            }

            selected.addPoints(1);
        });


    }
}
