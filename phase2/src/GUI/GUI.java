package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public interface GUI {
    void start(Stage stage) throws IOException;
    static void main(String[] args){};
}
