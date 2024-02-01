package nl.group37.gui.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import nl.group37.algorithm.Search3D;
import nl.group37.algorithm.matrix.ParcelMatrix3D;
import nl.group37.algorithm.matrix.PentominoMatrix3D;
import nl.group37.gui.GUI;

public class guiController {
    
    @FXML
    Pane guiPane;
    @FXML
    Label testLabel, valueLabel;

    private GUI gui;

    @FXML
    public void initialize() throws FileNotFoundException {
        gui = new GUI();

        Font font = Font.loadFont(new FileInputStream(new File("PHASE3/src/main/resources/n" + //
                "l/group37/CheapPotatoesBlack.ttf")), 10);
        testLabel.setFont(font);
        valueLabel.setFont(font);
    }

    public void updateValue(String value) {
        valueLabel.setText("Total Value: " + value);
    }

    @FXML
    private void runAlgorithm(ActionEvent e) {
        //testLabel.setText("Running the algorithm");
        System.out.println("solving...");
        
        if(gui.started)
            return;
        gui.started = true;

        long time = System.currentTimeMillis();

        PentominoMatrix3D m = new PentominoMatrix3D(8, 5, 33);
        m.buildMatrix();

        System.out.println("Matrix built in " + (System.currentTimeMillis() - time));
        Search3D parcelSearch = new Search3D(m.getRoot());
        //parcelSearch.search();
        //System.out.println(Colors.RESET + "Found in " + (System.currentTimeMillis() - time));

        new Thread(parcelSearch::search).start();
        //testLabel.setText("Done");
    }

    @FXML
    private void runAlgorithm2(ActionEvent e) {
        //testLabel.setText("Running the algorithm");
        System.out.println("solving...");
        
        if(gui.started)
            return;
        gui.started = true;

        long time = System.currentTimeMillis();

        ParcelMatrix3D m = new ParcelMatrix3D(8, 5, 33);
        m.buildMatrix();

        System.out.println("Matrix built in " + (System.currentTimeMillis() - time));
        Search3D parcelSearch = new Search3D(m.getRoot());
        //parcelSearch.search();
        //System.out.println(Colors.RESET + "Found in " + (System.currentTimeMillis() - time));

        new Thread(parcelSearch::search).start();
        //testLabel.setText("Done");
    }

    @FXML
    private void reset(ActionEvent e) {
        int[][][] grid = new int[70][5][8];
        GUI.update(grid, 50);
        gui.started = false;
    }

    @FXML
    private void method1(ActionEvent e) {
        if (gui.s4) {
            for(int i =0;i<=5;i++){
            GUI.update(Search3D.output1,50*i);
            gui.s4 = false;
            }
        } else if (!gui.s4) {
            GUI.update(Search3D.output1,50);
            gui.s4 = true;
        }
    }

    @FXML
    private void method2(ActionEvent e) {
        gui.toggleColor1();
    }

    @FXML
    private void method3(ActionEvent e) {
        gui.toggleColor2();
    }

    @FXML
    private void method4(ActionEvent e) {
        gui.toggleColor3();
    }
}
