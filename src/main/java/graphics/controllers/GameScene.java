package graphics.controllers;

import graphics.BattleCity;
import graphics.GameController;
import graphics.GameRepainter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameScene {
    private GameController controller;
    private GameRepainter repainter;

    @FXML
    private StackPane root;

    @FXML
    private AnchorPane gameField;

    @FXML
    private VBox escapePauseBox;

    @FXML
    private Label pauseLabel;

    @FXML
    private HBox pauseButtons;

    @FXML
    private Button continueButton;

    @FXML
    private Button newGameButton;

    @FXML
    private Button menuButton;

    @FXML
    private ScrollPane chooseLevelPane;

    @FXML
    private VBox levelsBox;

    @FXML
    void newStart(ActionEvent event) {
        if (controller.isBusy()) {
            controller.launch();
        }
        pauseLabel.setText("PAUSE");
        continueButton.setDisable(false);
        start();
    }

    @FXML
    void start() {
        controller.startGame();
        continuePaint();
    }

    private void continuePaint() {
        setVisible(gameField);
        repainter.start();
    }

    private void pausePaint() {
        repainter.stop();
        setVisible(escapePauseBox);
    }

    @FXML
    void menu() {
        controller.save();
        setVisible(chooseLevelPane);
        BattleCity.app.setRoot(SceneType.MENU);
    }

    @FXML
    void checkEscape(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            BattleCity.app.setRoot(SceneType.MENU);
        }
    }

    @FXML
    void press(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.ESCAPE) {
            if (controller.tryPause()) {
                continueButton.setDisable(false);
                pausePaint();
            }
        } else {
            controller.checkEvent(code, true);
        }
    }

    @FXML
    void release(KeyEvent event) {
        KeyCode code = event.getCode();
        controller.checkEvent(code, false);
    }

    @FXML
    void initialize() {
        controller = new GameController();
        fillLevelsBox(controller.getLevels());
        setFieldBounds();
        repainter = new GameRepainter(controller, this);
    }

    private void fillLevelsBox(String[] list) {
        for (String name : list) {
            levelsBox.getChildren().add(createButton(name));
        }
    }

    private Button createButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font("System", FontWeight.BOLD,16));
        button.setMaxWidth(1.7976931348623157E308);
        button.setOnAction(event -> launch(button.getText()));
        return button;
    }

    private void launch(String levelName) {
        continueButton.setDisable(!controller.launchSave(levelName));
        pauseLabel.setText("PAUSE");
        setVisible(escapePauseBox);
    }

    private void setVisible(Region region) {
        gameField.setVisible(false);
        gameField.setDisable(true);
        chooseLevelPane.setVisible(false);
        chooseLevelPane.setDisable(true);
        escapePauseBox.setVisible(false);
        escapePauseBox.setDisable(true);

        region.setVisible(true);
        region.setDisable(false);
    }

    private void setFieldBounds() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Line line = new Line(0, j * gameField.getPrefHeight(), gameField.getPrefWidth(), j * gameField.getPrefHeight());
                line.setStroke(Color.WHITE);
                gameField.getChildren().add(line);

                line = new Line(i * gameField.getPrefWidth(), 0, i * gameField.getPrefWidth(), gameField.getPrefHeight());
                line.setStroke(Color.WHITE);
                gameField.getChildren().add(line);
            }
        }
    }

    public void gameOver() {
        pauseLabel.setText(controller.getGameOverText());
        continueButton.setDisable(true);
        pausePaint();
    }

    public void remove(ImageView imageView) {
        gameField.getChildren().remove(imageView);
    }

    public Pane getGameField() {
        return gameField;
    }
}