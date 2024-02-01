module nl.group37 {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens nl.group37 to javafx.fxml;
    exports nl.group37;
    opens nl.group37.algorithm to javafx.fxml;
    exports nl.group37.algorithm;
    opens nl.group37.algorithm.matrix to javafx.fxml;
    exports nl.group37.algorithm.matrix;
    opens nl.group37.algorithm.pent3d to javafx.fxml;
    exports nl.group37.algorithm.pent3d;
    opens nl.group37.gui to javafx.fxml;
    exports nl.group37.gui;
    opens nl.group37.gui.controllers to javafx.fxml;
    exports nl.group37.gui.controllers;
}
