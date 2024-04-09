package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * A class which represents a chess board.
 * 
 * @author Kyle Herrmann
 * @version 01
 */
public class LevelManager extends GridPane {

    /** Game menu. */
    public static Menu home;

    /** Chess board. */
    public Board first;

    /**
     * Constructor generate boards and add them to the grid pane.
     */
    LevelManager() {

        // Format
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));

        // Instantiate and add menu
        home = new Menu();
        add(home, 0, 0);

    }

    /**
     * Method creates chess board.
     */
    public void build() {

        // Remove menu and set background
        getChildren().remove(home);
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(26, 13, 0), CornerRadii.EMPTY, Insets.EMPTY);
        Background texture = new Background(background_fill);
        setBackground(texture);

        // Instantiate level and add it to the grid pane
        first = new Board(0);
        add(first, 0, 0);

    }
}
