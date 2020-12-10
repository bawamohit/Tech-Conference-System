package GUI;

import UseCases.UserManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public interface GUIController {
    //TODO MainController mainController = new MainController(); //static variable?

    void initData(MainController Controller);
}
