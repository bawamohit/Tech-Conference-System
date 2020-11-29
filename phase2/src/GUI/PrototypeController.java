package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PrototypeController {
    @FXML
    private Text sike;

    @FXML protected void handleSignInButtonAction(ActionEvent event) {
        sike.setText("Wrong");
    }

    @FXML protected void handleSingUpButtonAction(ActionEvent event) {
        sike.setText("SIKE");
    }
}
