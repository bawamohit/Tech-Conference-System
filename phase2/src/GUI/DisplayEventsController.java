package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

public abstract class DisplayEventsController extends Observable implements GUIController, Observer {
    private SubScene subScene;
    @FXML private GridPane gridPane;
    @FXML private GridPane subGridPane;
    FXMLLoader loader;

    public void generateEventButtons(String path, List<List<String>> eventsInfo){
        EventHolder.getInstance().setButtonClicked(false);
        int i = 0, j = 0;
        for(List<String> eventInfo: eventsInfo) {
            Button button = new Button(eventInfo.get(1) + "\nStarts: " + eventInfo.get(3) + "\nEnds:  " + eventInfo.get(4));
            button.setPrefHeight(75);
            button.setPrefWidth(200);
            button.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    EventHolder.getInstance().setButtonClicked(true);
                    EventHolder.getInstance().setEvent(UUID.fromString(eventInfo.get(0)));
                    loadSubScene(path);
                    if (path.equals("/GUI/AdminGUI/EventDeleteInfo")){
                        observeEventDelete();
                    }
                }
            });
            subGridPane.add(button, i, j);
            if(i < 3){ i++; }else{ i = 0; j++;}
        }
        loadSubScene("/GUI/Empty");

        gridPane.add(subScene, 0, 1);
    }

    private void loadSubScene(String path){
        loader = new FXMLLoader(getClass().getResource(path + ".fxml"));
        Parent root = null;
        try {
            root = loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(subScene == null) {
            subScene = new SubScene(root, 700, 100); //TODO initData maybe
        }else{
            subScene.setRoot(root);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        int x = countObservers();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("I have " + x + "observers");
        a.showAndWait();
        notifyObservers();
    }

    @Override
    public void initData(MainController mainController) {

    }

    public void observeEventDelete(){
        EventInfoController controller = getLoader().getController();
        controller.addObserver(this);
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
