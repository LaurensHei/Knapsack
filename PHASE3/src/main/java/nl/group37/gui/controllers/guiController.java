package nl.group37.gui.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import nl.group37.algorithm.Search3D;
import nl.group37.algorithm.matrix.ParcelMatrix3D;
import nl.group37.algorithm.matrix.PentominoMatrix3D;
import nl.group37.gui.GUI;

public class GUIController {

    @FXML
    Label cargoValueLabel, gapsLabel, timeLabel;

    private GUI gui;

    @FXML
    public void initialize() throws FileNotFoundException {
        gui = new GUI();

        Font font = Font.loadFont(new FileInputStream(new File("PHASE3/src/main/resources/n" + //
                "l/group37/CheapPotatoesBlack.ttf")), 10);
        cargoValueLabel.setFont(font);
        gapsLabel.setFont(font);
        timeLabel.setFont(font);
    }

    public void updateStats(int cargoValue, int gaps, long time) {
        cargoValueLabel.setText("Cargo Value: " + cargoValue);
        gapsLabel.setText("Gaps: " + gaps);
        timeLabel.setText("Time: "+ time + "ms.");
    }

    @FXML
    private void runPentominoSolver(ActionEvent e) {
        System.out.println("solving...");

        if (gui.started)
            return;
        gui.started = true;

        long time = System.currentTimeMillis();

        PentominoMatrix3D m = new PentominoMatrix3D(8, 5, 33);
        m.buildMatrix();

        System.out.println("Matrix built in " + (System.currentTimeMillis() - time));
        Search3D pentominoSearch = new Search3D(m.getRoot());

        new Thread(pentominoSearch::search).start();
    }

    @FXML
    private void runParcelSolver(ActionEvent e) {
        System.out.println("solving...");

        if (gui.started)
            return;
        gui.started = true;

        long time = System.currentTimeMillis();

        ParcelMatrix3D m = new ParcelMatrix3D(8, 5, 33);
        m.buildMatrix();

        System.out.println("Matrix built in " + (System.currentTimeMillis() - time));
        Search3D parcelSearch = new Search3D(m.getRoot());

        new Thread(parcelSearch::search).start();
    }

    @FXML
    private void reset(ActionEvent e) {
        int[][][] grid = new int[70][5][8];
        GUI.update(grid, 50);
        gui.started = false;
    }

    @FXML
    private void blue(ActionEvent e) {
        gui.toggleBlueBlocks();
    }

    @FXML
    private void red(ActionEvent e) {
        gui.toggleRedBlocks();
    }

    @FXML
    private void green(ActionEvent e) {
        gui.toggleGreenBlocks();
    }
}
