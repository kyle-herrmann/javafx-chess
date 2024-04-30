package main;

import java.io.InputStream;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A class which represents a chess board tile.
 * 
 * @author Kyle Herrmann
 * @version 01
 */
public class Square extends ImageView {

    /** Square row. */
    public int row;

    /** Square column. */
    public int column;
    
    /** Board level. */
    public int level;

    /** If square is occupied. */
    boolean occupied = false;
    
    /** Height of square. */
    private final int height = 50;
    
    /** Width of square. */
    private final int width = 50;

    /**
     * Tile constructor
     * 
     * @param column, row.
     */
    Square(int c, int r, int l) {

        // Instantiate column
        column = c;
        // Row
        row = r;
        // Level
        level = l;

        // Stream image to 'tile'
        InputStream stream = getClass().getResourceAsStream("images/tile.jpg");
        Image tile = new Image(stream);
        setImage(tile);
        // Format
        setFitHeight(height);
        setFitWidth(width);
        
        // Pattern algorithm
        ColorAdjust adjust = new ColorAdjust();
        if ((row % 2 == 0) == (column % 2 == 0)) {
            adjust.setContrast(-0.2);
            setEffect(adjust);
        } else {
            adjust.setContrast(0.1);
            setEffect(adjust);
        }
        
        // Change opacity on mouse entry and exit events
        setOnMouseEntered(event -> setOpacity(0.5));
        setOnMouseExited(event -> setOpacity(1.0));
        
        // Assign tile selection mouse event handler in game logic
        setOnMouseClicked(event -> GameLogic.move(this));
        
    }

}
