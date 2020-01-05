package graphics;

import graphics.controllers.SceneType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BattleCity extends Application {
    public static BattleCity app;
    private Stage stage;
    private Scene scene;

    private Parent menu;
    private Parent game;
    private Parent builder;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        app = this;
        this.stage = stage;
        initScenes();
        stage.setTitle("BATTLE CITY");
        Scene scene = new Scene(menu);
        this.scene = scene;
        stage.setScene(scene);
        stage.show();
    }

    private void setRoot(Parent parent) {
        scene.setRoot(parent);
    }

    private void initScenes() throws IOException {
        menu = FXMLLoader.load(getClass().getClassLoader().getResource("fxml\\menu.fxml"));
        game = FXMLLoader.load(getClass().getClassLoader().getResource("fxml\\game.fxml"));
        builder = FXMLLoader.load(getClass().getClassLoader().getResource("fxml\\builder.fxml"));
    }

    public void setRoot(SceneType type) {
        switch (type) {
            case GAME:
                setRoot(game);
                break;
            case MENU:
                setRoot(menu);
                break;
            case BUILDER:
                setRoot(builder);
                break;
        }
    }

    public Stage getStage() {
        return stage;
    }
}
