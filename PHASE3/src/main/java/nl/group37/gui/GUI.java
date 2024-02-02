package nl.group37.gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import nl.group37.App;
import nl.group37.gui.controllers.GUIController;

import java.io.IOException;
import java.util.Locale;

public class GUI {
    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;

    static GUIController controller;

    static Group group;
    static Group blueGroup;
    static Group redGroup;
    static Group greenGroup;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty (0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    public boolean started;

    public static void updateStats(int cargoValue, int gaps, long time) {
        controller.updateStats(cargoValue, gaps, time);
    }

     public static void update(int[][][] grid,int scale) {

        blueGroup.getChildren().clear();
        redGroup.getChildren().clear();
        greenGroup.getChildren().clear();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                for (int k = 0; k < grid[i][j].length; k++) {

                    int color = grid[i][j][k];
                    if (color < 1 || color > 3)
                        continue;
                    Box box = createBox(color);

                    box.translateXProperty().set(scale * i - (double) (50 * grid.length) / 2);
                    box.translateYProperty().set(scale * j - (double) (50 * grid[i].length) / 2);
                    box.translateZProperty().set(scale * k);
                }
            }
        }
    }

    private static Box createBox(int color) {
        Box box = new Box(50, 50, 50);
        switch (color){
            case 1: //blue
                box.setMaterial(new PhongMaterial(new Color(.1,.7, 1, .6)));
                blueGroup.getChildren().add(box);
                break;
            case 2: //red
                box.setMaterial(new PhongMaterial(new Color(.7, 0, 0, .6)));
                redGroup.getChildren().add(box);
                break;
            case 3: //green
                box.setMaterial(new PhongMaterial(new Color(.1, .9, .1, .6)));
                greenGroup.getChildren().add(box);
                break;
        }
        return box;
    }

    public void toggleBlueBlocks(){
        blueGroup.setVisible(!blueGroup.isVisible());
    }
    public void toggleRedBlocks(){  //red
        redGroup.setVisible(!redGroup.isVisible());
    }
    public void toggleGreenBlocks(){
        greenGroup.setVisible(!greenGroup.isVisible());
    }

    public Scene getScene() {
        Locale.setDefault(Locale.ENGLISH);

        this.started = false;
        // Create box
        // Prepare transformable Group container
        group = new Group();
        blueGroup = new Group();
        group.getChildren().add(blueGroup);
        redGroup = new Group();
        group.getChildren().add(redGroup);
        greenGroup = new Group();
        group.getChildren().add(greenGroup);

        update(new int[33][5][8],50);

        Camera c = new PerspectiveCamera();

        c.translateZProperty().set(-2500);
        c.translateYProperty().set(-WIDTH/2+500);
        c.translateXProperty().set(-HEIGHT +450);

        FXMLLoader loader = new FXMLLoader(App.class.getResource("GUI.fxml"));

        Parent r;
        try {
            r = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller = loader.getController();

        Pane mainPane = (Pane) ((AnchorPane) loader.getRoot()).getChildren().get(0);
        Pane guiPane = (Pane) mainPane.getChildren().get(mainPane.getChildren().size()-1);

        Scene s = new Scene(r);

        SubScene scene = new SubScene( group, 800, 400,true,SceneAntialiasing.BALANCED);
        scene.setFill(Color.WHITE);
        scene.setCamera(c);
        guiPane.getChildren().add(scene);

        initMouseControl(group, s);

        return s;
    }

    private void initMouseControl(Group group, Scene scene) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, group.getLayoutBounds().getCenterX(), group.getLayoutBounds().getCenterY(), group.getLayoutBounds().getCenterZ(), Rotate.X_AXIS),
                yRotate = new Rotate(0,group.getLayoutBounds().getCenterX(), group.getLayoutBounds().getCenterY(), group.getLayoutBounds().getCenterZ(), Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });
    }
}


