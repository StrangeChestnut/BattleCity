package graphics.controllers;

import game.utils.DataConverter;
import game.entities.statics.Block;
import graphics.BattleCity;
import graphics.draws.DrawBlock;
import game.entities.statics.BlockType;
import game.level.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;

import java.util.ArrayList;
import java.util.List;

public class BuilderScene {
    private final FileChooser fileChooser = new FileChooser();

    private BlockType brush = BlockType.BRICK;
    private DrawBlock[][] map;
    private List<Line> grid;
    private Level level;

    @FXML
    private VBox root;

    @FXML
    private TextField nameField;

    @FXML
    private AnchorPane gameField;

    @FXML
    void air(ActionEvent event) {
        brush = BlockType.AIR;
    }

    @FXML
    void base(ActionEvent event) {
        brush = BlockType.BASE;
    }

    @FXML
    void brick(ActionEvent event) {
        brush = BlockType.BRICK;
    }

    @FXML
    void water(ActionEvent event) {
        brush = BlockType.WATER;
    }

    @FXML
    void ice(ActionEvent event) {
        brush = BlockType.ICE;
    }

    @FXML
    void iron(ActionEvent event) {
        brush = BlockType.IRON;
    }

    @FXML
    void leaf(ActionEvent event) {
        brush = BlockType.LEAF;
    }

    @FXML
    public void press(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.ESCAPE) {
            BattleCity.app.setRoot(SceneType.MENU);
        }
    }

    @FXML
    void changeName(KeyEvent event) {
        int length = nameField.getText().length();
        if (length > 20) {
            nameField.deleteText(20, length);
        }
    }

    @FXML
    public void about(ActionEvent event) {
    }

    @FXML
    public void load(ActionEvent event) {
        level = DataConverter.read(fileChooser.showOpenDialog(BattleCity.app.getStage()));
        initMap();
        nameField.setText(level.getName());
    }

    @FXML
    void clear(ActionEvent event) {
        nameField.clear();
        for (int i = 0; i < Level.SIZE; i++) {
            for (int j = 0; j < Level.SIZE; j++) {
                map[i][j].repaint(BlockType.AIR, gameField);
                map[i][j] = new DrawBlock(BlockType.AIR.createBlock(i, j));
            }
        }
        repaintGrid();
    }

    @FXML
    void save(ActionEvent event) {
        Level level = new Level();
        for (int i = 0; i < Level.SIZE; i++) {
            for (int j = 0; j < Level.SIZE; j++) {
                level.getMap()[i][j] = map[i][j];
            }
        }

        if (!nameIsCorrect()) {
            return;
        }

        level.setName(nameField.getText());
        DataConverter.writeLevel(level);
        alert(Alert.AlertType.INFORMATION, "Successful ^_^");
    }

    private void alert(Alert.AlertType type, String text) {
        Alert alert = new Alert(type, text, ButtonType.OK);
        alert.showAndWait();
    }

    private boolean nameIsCorrect() {
        String text = nameField.getText();
        if (text == null || text.length() == 0) {
            alert(Alert.AlertType.WARNING, "Please enter correct the level name");
            return false;
        }
        return true;
    }

    @FXML
    void paint(MouseEvent event) {
        int x = (int) (event.getX() / gameField.getWidth() * Level.SIZE);
        int y = (int) (event.getY() / gameField.getHeight() * Level.SIZE);
        if (0 <= x && x < Level.SIZE && 0 <= y && y < Level.SIZE) {
            if (event.isPrimaryButtonDown()) {
                map[x][y].repaint(brush, gameField);
                map[x][y] = new DrawBlock(brush.createBlock(x, y));
                repaintGrid();
            }
            if (event.isSecondaryButtonDown()) {
                map[x][y].repaint(BlockType.AIR, gameField);
                map[x][y] = new DrawBlock(BlockType.AIR.createBlock(x, y));
                repaintGrid();
            }
        }
    }

    @FXML
    public void initialize() {
        fileChooser.setInitialDirectory(DataConverter.LEVELS);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        level = new Level();
        map = new DrawBlock[Level.SIZE][Level.SIZE];
        initMap();
    }

    private void setGrid() {
        grid = new ArrayList<>(54);
        double width = gameField.getPrefWidth() / Level.SIZE;
        double height = gameField.getPrefHeight() / Level.SIZE;
        for (int i = 0; i <= Level.SIZE; i++) {
            Line line = new Line(0, i * height, gameField.getPrefWidth(), i * height);
            line.setStroke(Color.WHITE);
            gameField.getChildren().add(line);
            grid.add(line);
        }
        for (int i = 0; i <= Level.SIZE; i++) {
            Line line = new Line(i * width, 0, i * width, gameField.getPrefHeight());
            line.setStroke(Color.WHITE);
            gameField.getChildren().add(line);
            grid.add(line);
        }
    }

    private void repaintGrid() {
        for (Line line: grid) {
            gameField.getChildren().remove(line);
            gameField.getChildren().add(line);
        }
    }

    private void initMap() {
        Block[][] map = level.getMap();
        gameField.getChildren().clear();
        for (int i = 0; i < Level.SIZE; i++) {
            for (int j = 0; j < Level.SIZE; j++) {
                this.map[i][j] = new DrawBlock(map[i][j]);
                this.map[i][j].draw(gameField);
            }
        }
        setGrid();
    }
}
