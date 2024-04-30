package main;

import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * A class which represents a chess piece.
 * 
 * @author Kyle Herrmann
 * @version 01
 */
public class Piece extends StackPane {
    
    /** Piece image view. */
    private final ImageView view;

    /** Piece type. */
    public final PieceType type;

    /** Piece color. */
    public PieceColor color;

    /** Current square. */
    public Square current;
    
    /** Piece height. */
    private final int height = 40;
    
    /** Piece width. */
    private final int width = 40;

    /**
     * Constructor sets image.
     * 
     * @param input of image
     */
    Piece(PieceType p, String input, Square c, PieceColor team) {

        // Instantiate type
        type = p;
        // Color
        color = team;
        // Tile position
        current = c;  
        
        // Set 'current' square to occupied
        current.occupied = true;

        // Stream graphic to image
        InputStream stream = getClass().getResourceAsStream(input);
        Image graphic = new Image(stream);
        // Apply 'graphic' to 'view' and format
        view = new ImageView(graphic);
        view.setFitHeight(height);
        view.setFitWidth(width);
        view.setOnMouseClicked(event -> GameLogic.move(this, view));
        getChildren().add(view);
    }

}