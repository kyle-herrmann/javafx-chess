package main;

import java.util.ArrayList;

/**
 * A class which represents a set of chess pieces.
 * 
 * @author Kyle Herrmann
 * @verison 01
 */
public class PieceSet extends ArrayList<Piece> {
    
    private static final long serialVersionUID = 1L;
    
    /** 2D Array of squares on chess board. */
    private Square squares[][];
    
    /**
     * Constructor fills list with all chess pieces.
     */
    public PieceSet(Square s[][]) {
        
        //Instantiate squares.
        squares = s;
        
        //Generate piece sets.
        setPlayerOne();
        System.out.println("Player(1) PieceSet Generated!");
        setPlayerTwo();
        System.out.println("Player(2) PieceSet Generated!");
    }
    
    
    /**
     * Method creates player one piece set.
     */
    public void setPlayerOne() {
     // Add pawns.
        for (int i = 0; i < 8; i++) {
            Piece pawn = new Piece(PieceType.PAWN, "images/blackPawn.png", squares[i][6], PieceColor.BLACK);
            add(pawn);
           
        }
        //Add castles.
        Piece castleL = new Piece(PieceType.CASTLE, "images/blackCastle.png", squares[0][7], PieceColor.BLACK);
        Piece castleR = new Piece(PieceType.CASTLE, "images/blackCastle.png", squares[7][7], PieceColor.BLACK);
        add(castleL);
        add(castleR);
        //Add horses.
        Piece horseL = new Piece(PieceType.HORSE, "images/blackHorse.png", squares[1][7], PieceColor.BLACK);
        Piece horseR = new Piece(PieceType.HORSE, "images/blackHorse.png", squares[6][7], PieceColor.BLACK);
        add(horseL);
        add(horseR);
        //Add bishops.
        Piece bishopL = new Piece(PieceType.BISHOP_L, "images/blackBishop.png", squares[2][7], PieceColor.BLACK);
        Piece bishopR = new Piece(PieceType.BISHOP_R, "images/blackBishop.png", squares[5][7], PieceColor.BLACK);
        add(bishopL);
        add(bishopR);
        //Add king.
        Piece king = new Piece(PieceType.KING, "images/blackKing.png", squares[3][7], PieceColor.BLACK);
        add(king);
        //Add queen.
    Piece queen = new Piece(PieceType.QUEEN, "images/blackQueen.png", squares[4][7], PieceColor.BLACK);
        add(queen);
    }
    
   
    
    /**
     * Method creates and returns player two piece set.
     * @return all pieces
     */
    public void setPlayerTwo() {
     // Add pawns.
        for (int i = 0; i < 8; i++) {
            Piece pawn = new Piece(PieceType.PAWN, "images/whitePawn.png", squares[i][1], PieceColor.WHITE);
            add(pawn);
        }
        //Add castles.
        Piece castleL = new Piece(PieceType.CASTLE, "images/whiteCastle.png", squares[7][0], PieceColor.WHITE);
        Piece castleR = new Piece(PieceType.CASTLE, "images/whiteCastle.png", squares[0][0], PieceColor.WHITE);
        add(castleL);
        add(castleR);
        //Add horses.
        Piece horseL = new Piece(PieceType.HORSE, "images/whiteHorse.png", squares[1][0], PieceColor.WHITE);
        Piece horseR = new Piece(PieceType.HORSE, "images/whiteHorse.png", squares[6][0], PieceColor.WHITE);
        add(horseL);
        add(horseR);
        //Add bishops.
        Piece bishopL = new Piece(PieceType.BISHOP_L, "images/whiteBishop.png", squares[2][0], PieceColor.WHITE);
        Piece bishopR = new Piece(PieceType.BISHOP_R, "images/whiteBishop.png", squares[5][0], PieceColor.WHITE);
        add(bishopL);
        add(bishopR);
        //Add king.
        Piece king = new Piece(PieceType.KING, "images/whiteKing.png",squares[3][0], PieceColor.WHITE);
        add(king);
        //Add queen.
        Piece queen = new Piece(PieceType.KING, "images/whiteQueen.png", squares[4][0], PieceColor.WHITE);
        add(queen);
    }
    
    

}
