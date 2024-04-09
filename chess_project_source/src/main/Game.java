package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * A class which runs the chess game.
 * 
 * @author Kyle Herrmann
 * @version 01
 */
public class Game extends Application {

    /** Main application window. */
    static Stage window;
    
    /** Width of application. */
    public final static int height = 470;
    
    /** Height of application. */
    public final static int width = 660;

    /** Object which represents chess board. */
    public static LevelManager level;
    
    /** Flag indicates game start. */
    public static boolean startGame = false;

    /** ArrayList of all chess pieces. */
    public static PieceSet allPiece;

    /** Game logic for current move. */
    public static GameLogic logic;

    /** Stores if game is in select. */
    public static boolean inSelect = false;

    /** Stores selected piece. */
    public static Piece selected;

    /** Stores current player turn. */
    public static PieceColor turn;

    @Override
    public void start(Stage stage) throws Exception {

        
        // Instantiate level manager
        level = new LevelManager();
        
        // Append board to scene and format
        Scene scene = new Scene(level, width, height, Color.rgb(26, 13, 0));

        // Set and show scene
        window = stage;
        window.setTitle("Chess Game");
        stage.setScene(scene);
        stage.show();

        // Set first turn to white
        turn = PieceColor.WHITE;

    }

    /**
     * Instantiates and places all chess game objects.
     * 
     * @param b
     */
    public static void create() {
      
        // Load game logic
        logic = new GameLogic();

        // Instantiate piece set
        allPiece = new PieceSet(level.first.set);

        // Set taken squares to occupied
        for (Piece index : allPiece)
            index.current.occupied = true;

        // Place pieces
        for (Piece index : allPiece) {
            level.first.add(index, index.current.column, index.current.row);
        }

    }

    /**
     * Method updates graphics and re-sets selection variables.
     */
    public static void update() {
        
        if (selected != null) {
            if (selected.current.level == 0) {
                level.first.getChildren().remove(selected);
                level.first.add(selected, selected.current.column, selected.current.row);
            }
            selected = null;
        }
        
        inSelect = false;
        
    }

    /**
     * Method ends game.
     */
    public static void gameOver() {
        level.first.getChildren().removeAll();
    }

    /**
     * Drives the program.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        launch();
    }

}
