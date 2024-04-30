module comp2522_ASG02 {
    
    requires javafx.base;
    requires javafx.controls;
    requires transitive javafx.graphics;
    
    opens main to javafx.fxml;
    exports main;

}