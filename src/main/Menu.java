package main;

import java.io.InputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 01 The main menu of the chess game.
 * 
 * @author Kyle Herrmann
 * @version
 */
public class Menu extends StackPane {

    /** Button height. */
    private final int height = 55;

    /** Button width. */
    private final int width = 265;

    /** Background image view. */
    ImageView background;

    /** Input stream for background. */
    InputStream stream;

    /** Image file. */
    Image graphic;

    /** Menu options. */
    GridPane main;

    /** Start button. */
    private Button start;

    /** Credits button. */
    private Button credits;

    /** Exit button. */
    private Button exit;

    /**
     * Constructor creates menu objects.
     */
    Menu() {

        // Format pane
        setPrefHeight(Game.height);
        setPrefWidth(Game.width);

        // Generate menu options
        generate();

    }

    /**
     * Method generates menu options.
     */
    public void generate() {

        // Remove old objects
        getChildren().removeAll();
        
        // Stream background graphic
        stream = getClass().getResourceAsStream("images/menu_image.jpg");
        graphic = new Image(stream);
        background = new ImageView();
        background.setImage(graphic);
        background.setFitHeight(Game.height);
        background.setFitWidth(Game.width);

        // Format background image
        ColorAdjust adjust = new ColorAdjust();
        adjust.setHue(-0.01);
        adjust.setContrast(-0.05);
        setEffect(adjust);
        // Add image to pane
        getChildren().add(background);

        // Instantiate and format selection menu
        main = new GridPane();
        main.setPrefHeight(height);
        main.setPrefWidth(width);
        main.setAlignment(Pos.TOP_LEFT);
        main.setVgap(50);
        main.setPadding(new Insets(80, 0, 0, 50));

        // Stream button texture
        stream = getClass().getResourceAsStream("images/button_image.jpg");
        graphic = new Image(stream);
        BackgroundImage backgroundimage = new BackgroundImage(graphic, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background texture = new Background(backgroundimage);

        // Start button
        start = new Button();
        start.setText("Start");
        start.setTextFill(Color.WHITE);
        start.setFont(Font.font("Verdana", 24));
        start.setAlignment(Pos.CENTER);
        start.setBackground(texture);
        start.setPrefHeight(height);
        start.setPrefWidth(width);
        start.setWrapText(true);
        // Mouse events
        start.setOnMouseClicked(event -> startGame());
        start.setOnMouseEntered(event -> enter(event));
        start.setOnMouseExited(event -> exit(event));

        // Credits button
        credits = new Button();
        credits.setText("Credits");
        credits.setTextFill(Color.WHITE);
        credits.setFont(Font.font("Verdana", 24));
        credits.setAlignment(Pos.CENTER);
        credits.setBackground(texture);
        credits.setPrefHeight(height);
        credits.setPrefWidth(width);
        credits.setWrapText(true);
        // Mouse events
        credits.setOnMouseClicked(event -> credits());
        credits.setOnMouseEntered(event -> enter(event));
        credits.setOnMouseExited(event -> exit(event));

        // Exit button
        exit = new Button();
        exit.setText("Exit");
        exit.setTextFill(Color.WHITE);
        exit.setFont(Font.font("Verdana", 24));
        exit.setAlignment(Pos.CENTER);
        exit.setBackground(texture);
        exit.setPrefHeight(height);
        exit.setPrefWidth(width);
        exit.setWrapText(true);
        exit.setOnMouseClicked(event -> Game.window.close());
        exit.setOnMouseEntered(event -> enter(event));
        exit.setOnMouseExited(event -> exit(event));

        // Add to menu
        main.add(start, 0, 0);
        main.add(credits, 0, 1);
        main.add(exit, 0, 2);

        // Add selection menu to stack pane
        getChildren().addAll(main);

    }

    /**
     * Method handles start button.
     * 
     * @return
     */
    public void startGame() {

        // Start game.
        Game.level.build();
        Game.create();

    }

    /**
     * Method handles credits display.
     */
    public void credits() {

        // Remove menu buttons
        getChildren().removeAll(start, credits, exit);
        
        // Stream background graphic
        stream = getClass().getResourceAsStream("images/credits.jpg");
        graphic = new Image(stream);
        background = new ImageView();
        background.setImage(graphic);
        background.setFitHeight(Game.height);
        background.setFitWidth(Game.width);
        
        // Generate menu on mouse click
        background.setOnMouseClicked(event -> generate());
        
        // Add image to pane
        getChildren().add(background);

    }

    /**
     * Method handles button selection.
     * 
     * @param select highlighted
     */
    public void enter(MouseEvent select) {
        ((Button) select.getSource()).setBorder(new Border(
                new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));
    }

    /**
     * Method handles undoing of button selection.
     * 
     * @param button highlighted
     */
    public void exit(MouseEvent select) {
        ((Button) select.getSource()).setBorder(new Border(
                new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(0))));
    }

}
