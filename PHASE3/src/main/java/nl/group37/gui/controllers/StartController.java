package nl.group37.gui.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import nl.group37.App;
import nl.group37.gui.GUI;


public class StartController {
    @FXML
    Button startButton;
    @FXML
    Label startLabel;

    @FXML
    public void initialize() throws FileNotFoundException {
        Font font = Font.loadFont(new FileInputStream(new File("PHASE3/src/main/resources/n" + //
        "l/group37/CheapPotatoesBlack.ttf")), 24);
        startLabel.setFont(font);

        Font font2 = Font.loadFont(new FileInputStream(new File("PHASE3/src/main/resources/n" + //
                "l/group37/CheapPotatoesBlack.ttf")), 18);
        startButton.setFont(font2);
    }

    @FXML
    private void switchToKnapsack(ActionEvent e) throws IOException {
        GUI gui = new GUI();
        Scene s = gui.getScene();
        App.setScene(s);
    }
}
