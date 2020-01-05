package graphics.controllers;

import graphics.BattleCity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuScene {

    @FXML
    void builder(ActionEvent event) {
        BattleCity.app.setRoot(SceneType.BUILDER);
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void play(ActionEvent event) {
        BattleCity.app.setRoot(SceneType.GAME);
    }
}
