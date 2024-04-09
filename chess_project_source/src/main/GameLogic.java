package main;

import javafx.scene.image.ImageView;

/**
 * A class which controls how chess pieces move.
 * 
 * @author Kyle Herrmann
 * @version 01
 */

public class GameLogic {

    /** Stores current board. */
    private static Board current;

    /**
     * Method checks if move is valid.
     * 
     * @return if valid
     */
    public static boolean isValid(Square target) {

        // Assign tile set of current level
        if (target.level == 0)
            current = Game.level.first;

        // Determine which logic method to run
        switch (Game.selected.type) {

        case PAWN: // Pawn logic
            return getPawnLogic(target);

        case CASTLE: // Castle logic
            return getCastleLogic(target);

        case HORSE: // Horse logic
            return getHorseLogic(target);

        case BISHOP_L: // Left bishop logic
            return getBishopLogicLeft(target);

        case BISHOP_R: // Right bishop logic
            return getBishopLogicRight(target);

        case QUEEN: // Queen logic
            return getQueenLogic(target);

        case KING: // King logic
            return getKingLogic(target);

        default: // Should not get here
            System.out.println("GameLogic: validation failure");
            return false;
        }
    }

    /**
     * Method validates and moves piece to an empty square.
     * 
     * @param target square
     */
    public static void move(Square target) {

        System.out.println("Attempting tile selection...");

        // Valid selection
        if (Game.inSelect && GameLogic.isValid(target)) {

            // Update initial tile to vacant
            Game.selected.current.occupied = false;

            // Update initial tile position to provided target
            Game.selected.current = target;

            // Update target tile to occupied
            target.occupied = true;

            // Update board graphics
            Game.selected.setOpacity(1.0);
            Game.update();

            // Print selection to console
            System.out.println("Selection (" + target.column + "," + target.row + ") was succesfull.");

            // Swap turn.
            nextTurn();

        } else { // Invalid selection
            System.out.println("Error: Defies game logic.");
            if (Game.inSelect) {
                Game.selected.setOpacity(1.0);
                Game.update();
            }
        }

    }

    /**
     * Method handles piece selection.
     * 
     * @param target piece
     */
    public static void move(Piece target, ImageView view) {

        // Detect initial piece section and ensure correct turn
        if (!Game.inSelect && target.color == Game.turn) {
            System.out.println(target.type + " is currently selected.");
            target.setOpacity(0.5);
            Game.selected = target;
            Game.inSelect = true;

            // Detect secondary selection of pawn (Special permissions)
        } else if (Game.inSelect && Game.selected.type == PieceType.PAWN && Game.selected.color != target.color
                && getPawnAttackLogic(target.current)) {

            // Print
            System.out.println("Attempting selection: " + Game.selected.color + " " + Game.selected.type + " --> "
                    + target.color + " " + target.type);

            // Remove target piece
            target.getChildren().remove(view);

            // Update initial tile to vacant
            Game.selected.current.occupied = false;

            // Update initial tile position to provided target
            Game.selected.current = target.current;

            // Update target tile to occupied
            target.current.occupied = true;

            // Update board graphics
            Game.selected.setOpacity(1.0);
            Game.update();

            // Print selection to console
            System.out.println("Selection was succesfull.");

            // Swap turn.
            nextTurn();

            // Detect secondary selection of remaining pieces
        } else if (Game.inSelect && GameLogic.isValid(target.current) && Game.selected.color != target.color) {
            // Print.
            System.out.println("Attempting selection: " + Game.selected.color + " " + Game.selected.color + " --> "
                    + target.color + " " + target.type);

            // Remove target piece
            target.getChildren().remove(view);

            // Update initial tile to vacant
            Game.selected.current.occupied = false;

            // Update initial tile position to provided target
            Game.selected.current = current.set[target.current.column][target.current.row];

            // Update target tile to occupied
            Game.selected.current.occupied = true;

            // Update board graphics
            Game.selected.setOpacity(1.0);
            Game.update();

            // Print selection to console
            System.out.println("Selection was succesfull.");

            // Swap turn.
            nextTurn();

        } else if (Game.inSelect) { // Invalid selection
            System.out.println("Error: Defies game logic.");
            Game.selected.setOpacity(1.0);
            Game.update();
        }

    }

    /**
     * Method handles game turns.
     */
    public static void nextTurn() {
        if (Game.turn == PieceColor.BLACK) {
            System.out.println("White team has turn.");
            Game.turn = PieceColor.WHITE;
        } else {
            Game.turn = PieceColor.BLACK;
            System.out.println("Black team has turn.");
        }
    }

    /**
     * Method checks level changing on path.
     * 
     * @param target square
     * @return if valid move
     */
    public static boolean checkLevelPath(Square target) {

        if (Math.abs(Game.selected.current.level - target.level) == Math
                .abs(Game.selected.current.column - target.column)
                || Math.abs(Game.selected.current.level - target.level) == Math
                        .abs(Game.selected.current.row - target.row))
            return true;
        else
            return false;

    }

    /**
     * Method checks level changing on angle.
     * 
     * @target square
     * @return if valid move
     */
    public static boolean checkLevelAngle(Square target) {

        if (Math.abs(Game.selected.current.level - target.level) == Math
                .abs(Game.selected.current.column - target.column)
                && Math.abs(Game.selected.current.level - target.level) == Math
                        .abs(Game.selected.current.row - target.row))
            return true;
        else
            return false;
    }

    /**
     * Method handles direct path collisions.
     * 
     * @param target
     */
    public static boolean checkPath(Square target) {

        // Moving up board
        if (target.row < Game.selected.current.row) {
            for (int row = target.row + 1; row < Game.selected.current.row; row++) {
                if (current.set[Game.selected.current.column][row].occupied)
                    return false;
            }
            // Moving down board
        } else if (target.row > Game.selected.current.row) { // Moving down board.
            for (int row = target.row - 1; row > Game.selected.current.row; row--) {
                if (current.set[Game.selected.current.column][row].occupied)
                    return false;
            }
        }

        // Moving left
        if (target.column < Game.selected.current.column) {
            for (int col = target.column + 1; col < Game.selected.current.column; col++) {
                if (current.set[col][Game.selected.current.row].occupied)
                    return false;
            }

            // Moving right
        } else if (target.column > Game.selected.current.column) { // Moving down board.
            for (int col = target.column - 1; col > Game.selected.current.column; col--) {
                if (current.set[col][Game.selected.current.row].occupied)
                    return false;
            }

        }

        // No collision detected
        return true;
    }

    /**
     * Method handles angled path collision.
     * 
     * @param target
     */
    public static boolean checkAngle(Square target) {

        // Ensure angled path for bishops
        if (target.column == Game.selected.current.column || target.row == Game.selected.current.row)
            return false;

        // Moving up and right
        if (target.row < Game.selected.current.row && target.column > Game.selected.current.column) {

            for (int col = target.column - 1; col > Game.selected.current.column; col--) {
                for (int row = target.row + 1; row < Game.selected.current.row; row++) {

                    System.out.println("Moving up and right \nChecking (" + col + "," + row + ")");
                    if (current.set[col][row].occupied)
                        return false;
                }
            }

            // Moving up and left
        } else if (target.row < Game.selected.current.row && target.column < Game.selected.current.column) {

            for (int col = target.column + 1; col < Game.selected.current.column; col++) {
                for (int row = target.row + 1; row < Game.selected.current.row; row++) {

                    System.out.println("Moving up and left \nChecking (" + col + "," + row + ")");
                    if (current.set[col][row].occupied)
                        return false;
                }
            }

            // Moving down and right
        } else if (target.row > Game.selected.current.row && target.column > Game.selected.current.column) {

            for (int col = target.column - 1; col > Game.selected.current.column; col--) {
                for (int row = target.row - 1; row > Game.selected.current.row; row--) {

                    System.out.println("Moving down and right \nChecking (" + col + "," + row + ")");
                    if (current.set[col][row].occupied)
                        return false;
                }
            }

            // Moving down and left
        } else if (target.row > Game.selected.current.row && target.column < Game.selected.current.column) {

            for (int col = target.column + 1; col < Game.selected.current.column; col++) {
                for (int row = target.row - 1; row > Game.selected.current.row; row--) {

                    System.out.println("Moving down and left \nChecking (" + col + "," + row + ")");
                    if (current.set[col][row].occupied)
                        return false;
                }
            }
        }

        // Otherwise return true
        return true;
    }

    /**
     * Method returns pawn logic.
     * 
     * @return pawn logic
     */
    public static boolean getPawnLogic(Square target) {

        // Player one: BLACK
        if (Game.selected.color == PieceColor.BLACK) {

            // First move
            if (Game.selected.current.row == 6 && Game.selected.current.row - target.row == 2
                    && Game.selected.current.column == target.column && checkPath(target)) {
                // Detect level change
                if (Game.selected.current.level != target.level
                        && Math.abs(Game.selected.current.level - target.level) > 2 || !checkLevelPath(target))
                    return false;
                else
                    return true;

                // In-game move
            } else {
                if (Game.selected.current.row - target.row == 1 && Game.selected.current.column == target.column
                        && checkPath(target)) {
                    // Detect level change.
                    if (Game.selected.current.level != target.level
                            && Math.abs(Game.selected.current.level - target.level) > 1 || !checkLevelPath(target))
                        return false;
                    else
                        return true;
                }
            }
        }

        // Player two: WHITE
        if (Game.selected.color == PieceColor.WHITE) {

            // First move
            if (Game.selected.current.row == 1 && target.row - Game.selected.current.row == 2
                    && Game.selected.current.column == target.column && checkPath(target)) {
                // Detect level change
                if (Game.selected.current.level != target.level
                        && Math.abs(Game.selected.current.level - target.level) > 2 || !checkLevelPath(target))
                    return false;
                else
                    return true;
                // In game move
            } else {
                if (target.row - Game.selected.current.row == 1 && Game.selected.current.column == target.column
                        && checkPath(target)) {
                    // Detect level change
                    if (Game.selected.current.level != target.level
                            && Math.abs(Game.selected.current.level - target.level) > 1 || !checkLevelPath(target))
                        return false;
                    else
                        return true;
                }
            }
        }

        // Otherwise false
        return false;

    }

    /**
     * Method returns logic for a pawn already in selection.
     * 
     * @return pawn logic
     */
    public static boolean getPawnAttackLogic(Square target) {

        // Player one: BLACK
        if (Game.selected.color == PieceColor.BLACK
                // Ensure forward direction
                && Math.abs(Game.selected.current.row - target.row) == 1
                // Disregard columns
                && Math.abs(Game.selected.current.column - target.column) == 1
                // Check for collision
                && checkPath(target))
            return true;

        // Player two: WHITE
        else if (Game.selected.color == PieceColor.WHITE
                // Ensure forward direction
                && target.row - Game.selected.current.row == 1
                // Disregard columns
                && Math.abs(target.column - Game.selected.current.column) == 1
                // Check for collision
                && checkPath(target))
            return true;
        else
            return false;

    }

    /**
     * Method returns castle logic.
     * 
     * @param target square
     * @return castle logic
     */
    public static boolean getCastleLogic(Square target) {

        // Ensure straight path along with level logic
        if ((target.column == Game.selected.current.column || target.row == Game.selected.current.row)
                && checkPath(target)) {

            // If same level, return true
            if (Game.selected.current.level == target.level) {
                System.out.println("match");
                return true;
                // If different level, run level logic
            } else if (checkLevelPath(target))
                return true;

        }
        return false;

    }

    /**
     * Method returns horse logic.
     * 
     * @return horse logic
     */
    public static boolean getHorseLogic(Square target) {

        // Confirm straight two square
        if ((Math.abs(Game.selected.current.row - target.row) == 2
                || Math.abs(Game.selected.current.column - target.column) == 2)
                // Confirm one square 'corner'
                && (Math.abs(Game.selected.current.row - target.row) == 1
                        || Math.abs(Game.selected.current.column - target.column) == 1))
            return true;
        else
            return false;
    }

    /**
     * Method returns left bishop logic.
     * 
     * @param target square
     * @return bishop logic
     */
    public static boolean getBishopLogicLeft(Square target) {

        // Check level movement
        if (Game.selected.current.level != target.level)
            return checkLevelPath(target);

        // Player one: BLACK
        if (Game.selected.color == PieceColor.BLACK && (target.column % 2 == 0) != (target.row % 2 == 0)
                && checkAngle(target))
            return true;
        // Player two: WHITE
        else if (Game.selected.color == PieceColor.WHITE && (target.row % 2 == 0) == (target.column % 2 == 0)
                && checkAngle(target))
            return true;
        else
            return false;
    }

    /**
     * Method returns right bishop logic.
     * 
     * @param target square
     * @return bishop logic
     */
    public static boolean getBishopLogicRight(Square target) {

        // Check level movement
        if (Game.selected.current.level != target.level)
            return checkLevelPath(target);

        // Player one: BLACK
        if (Game.selected.color == PieceColor.BLACK && (target.row % 2 == 0) == (target.column % 2 == 0)
                && checkAngle(target))
            return true;
        // Player two: WHITE
        else if (Game.selected.color == PieceColor.WHITE && (target.column % 2 == 0) != (target.row % 2 == 0)
                && checkAngle(target))
            return true;
        else
            return false;
    }

    /**
     * Method returns queen logic. Queen permissions are a combination of the bishop
     * and castle.
     * 
     * @param target square
     * @return queen logic
     */
    public static boolean getQueenLogic(Square target) {

        // Check level movement
        if (Game.selected.current.level != target.level)
            return checkLevelAngle(target);

        // Check straight path
        if (getCastleLogic(target))
            return true;

        // Player one: BLACK (Angle path)
        else if (Game.selected.color == PieceColor.BLACK && getBishopLogicLeft(target))
            return true;
        // Player two: WHITE (Angle path)
        else if (Game.selected.color == PieceColor.WHITE && getBishopLogicRight(target))
            return true;
        else
            return false;
    }

    /**
     * Method returns king logic.
     * 
     * @param target square
     * @return king logic
     */
    public static boolean getKingLogic(Square target) {

        // Restrict movement to one tile
        if ((Math.abs(Game.selected.current.row - target.row) <= 1
                && Math.abs(Game.selected.current.column - target.column) <= 1)
                // Check for direct collision
                && checkPath(target))
            return true;
        else
            return false;
    }

}
