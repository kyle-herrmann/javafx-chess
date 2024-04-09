package main;

import javafx.scene.layout.GridPane;

/**
 * A class which represents a chess board.
 * 
 * @author Kyle Herrmann
 * @version 01
 */
public class Board extends GridPane {
    
    /** 2D Array of squares on chess board. */
    public Square set[][];
    
    /** Stores board level. */
    public static int level;

    /**
     * Generate and add tiles to board object.
     */
    Board(int l) {
        
        // Instantiate array of squares
        set = new Square[8][8];
        
        // Instantiate level
        level = l;
        
        // Create squares and add them to the array
        for (int col = 0; col < set.length; col++) {
            for (int row = 0; row < set[col].length; row++) {
                Square tile = new Square(col, row, level); // New tile
                set[col][row] = tile; // Assign to array.
            }
        }
        
        // Add squares to board
        for (int col = 0; col < set.length; col++) {
            for (int row = 0; row < set[col].length; row++) {
                add(set[col][row], col, row);
            }
        }
    }
    
   
    
}
