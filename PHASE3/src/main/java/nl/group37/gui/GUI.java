package nl.group37.gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import nl.group37.App;
import nl.group37.algorithm.Blocks;
import nl.group37.gui.controllers.guiController;

import java.io.IOException;
import java.util.Locale;

public class GUI {
    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;

    static guiController controller;

    static SmartGroup group;
    static SmartGroup group1;
    static SmartGroup group2;
    static SmartGroup group3;

    boolean s1 = true;
    boolean s2 = true;
    boolean s3 = true;
    public boolean s4 =true;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty (0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    public boolean started;

    public static void updateMax(int i) {
        controller.updateValue(i+"");
    }

     public static void update(int[][][] grid,int scale) {

        group1.getChildren().clear();
        group2.getChildren().clear();
        group3.getChildren().clear();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                for (int k = 0; k < grid[i][j].length; k++) {
                    if (grid[i][j][k] == 1) {

                        Box box = makebox1();
                        group1.getChildren().add(box);
                        box.translateXProperty().set(scale * i - (50 * grid.length) / 2);
                        box.translateYProperty().set(scale * j - (50 * grid[i].length) / 2);
                        box.translateZProperty().set(scale * k);
                    }
                    if (grid[i][j][k] == 2) {
                        Box box = makebox2();
                        group2.getChildren().add(box);
                        box.translateXProperty().set(scale * i - (50 * grid.length) / 2);
                        box.translateYProperty().set(scale * j - (50 * grid[i].length) / 2);
                        box.translateZProperty().set(scale * k);
                    }
                    if (grid[i][j][k] == 3) {
                        Box box = makebox3();
                        group3.getChildren().add(box);
                        box.translateXProperty().set(scale * i - (50 * grid.length) / 2);
                        box.translateYProperty().set(scale * j - (50 * grid[i].length) / 2);
                        box.translateZProperty().set(scale * k);
                    }
                }
            }
        }
    }

    public void toggleColor1(){
        if (s1) {
            group1.setVisible(false);
            s1 = false;
        } else if (!s1) {
            group1.setVisible(true);
            s1 = true;
        }
    }
    public void toggleColor2(){
        if (s2) {
            group2.setVisible(false);
            s2 = false;
        } else if (!s2) {
            group2.setVisible(true);
            s2 = true;
        }
    }
    public void toggleColor3(){
        if (s3) {
            group3.setVisible(false);
            s3 = false;
        } else if (!s3) {
            group3.setVisible(true);
            s3 = true;
        }
    }

    public Scene getScene() {
        Locale.setDefault(Locale.ENGLISH);

        this.started = false;
        // Create box
        // Prepare transformable Group container
        group = new SmartGroup();
        group1 = new SmartGroup();
        group.getChildren().add(group1);
        group2 = new SmartGroup();
        group.getChildren().add(group2);
        group3 = new SmartGroup();
        group.getChildren().add(group3);

        update(Blocks.grid,50);

        Camera c = new PerspectiveCamera();

        c.translateZProperty().set(-2500);
        c.translateYProperty().set(-WIDTH/2+500);
        c.translateXProperty().set(-HEIGHT*1+450);


        FXMLLoader loader = new FXMLLoader(App.class.getResource("GUI.fxml"));


        Parent r = null;
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

    private void initMouseControl(SmartGroup group, Scene scene) {
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

    private static Box makebox1() {
        Box box = new Box(50, 50, 50);
        box.setMaterial(new PhongMaterial(new Color(.1,.7, 1, .6)));
        return box;
    }


    private static Box makebox2() {
        Box box = new Box(50, 50, 50);
        box.setMaterial(new PhongMaterial(new Color(.7, 0, 0, .6)));
        return box;
    }

    private static Box makebox3() {
        Box box = new Box(50, 50, 50);
        box.setMaterial(new PhongMaterial(new Color(.1, .9, .1, .6)));
        return box;
    }
}


