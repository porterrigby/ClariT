package com.prigby;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScreenSelect extends Application {
    private final int MOUSE_Y_OFFSET = 16;
    private boolean mousePressed;
    private double x1, y1, x2, y2;
    private ScreenCapture screenCapture;
    private double screenWidth, screenHeight;
    private Rectangle screenShade1, screenShade2, screenShade3, screenShade4; 
    private Rectangle screenShade5, screenShade6, screenShade7, screenShade8;
    private Group group;

    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        screenCapture = new ScreenCapture();
        mousePressed = false;
        screenWidth = Screen.getPrimary().getBounds().getMaxX();
        screenHeight = Screen.getPrimary().getBounds().getMaxY();


        //XXX Event Handler Lambdas
        EventHandler<MouseEvent> mousePressHandler = (MouseEvent event) -> {handleMousePress(event);};
        EventHandler<MouseEvent> mouseReleaseHandler = (MouseEvent event) -> {handleMouseRelease(event);};
        EventHandler<MouseEvent> mouseDragHandler = (MouseEvent event) -> {handleMouseDrag(event);};
      
        stage.setTitle("ClariT");

        // screen shading
        screenShade1 = new Rectangle(0, 0, screenWidth, screenHeight);
        screenShade2 = new Rectangle(0, 0, 0, 0);
        screenShade3 = new Rectangle(0, 0, 0, 0);
        screenShade4 = new Rectangle(0, 0, 0, 0);
        screenShade5 = new Rectangle(0, 0, 0, 0);
        screenShade6 = new Rectangle(0, 0, 0, 0);
        screenShade7 = new Rectangle(0, 0, 0, 0);
        screenShade8 = new Rectangle(0, 0, 0, 0);
        turnOnTargetShaders();

        group = new Group(screenShade1, screenShade2, screenShade3, screenShade4,
                            screenShade5, screenShade6, screenShade7, screenShade8, screenCapture);
        Scene scene = new Scene(group, screenWidth, screenHeight);
        scene.setFill(Color.TRANSPARENT);

        // assigning events to handlers
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressHandler);
        scene.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleaseHandler);
        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragHandler);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    //XXX Event Handler Methods
    private void handleMousePress(MouseEvent event) {
        mousePressed = true;
        x1 = event.getX();
        y1 = event.getY();
    }
    
    private void handleMouseRelease(MouseEvent event) {
        if (!mousePressed) {throw new RuntimeException("Mouse not pressed.");}
        x2 = event.getX();
        y2 = event.getY();
        turnOffTargetShaders();

        if (x1 < x2) { // If cursor is dragged top left to bottom right
            screenCapture.setX1(x1);
            screenCapture.setX2(x2);
        } else { // If cursor is dragged bottom right to top left
            screenCapture.setX1(x2);
            screenCapture.setX2(x1);
        }

        if (y1 < y2) {
            screenCapture.setY1(y1-MOUSE_Y_OFFSET);
            screenCapture.setY2(y2-MOUSE_Y_OFFSET);
        } else {
            screenCapture.setY1(y2-MOUSE_Y_OFFSET);
            screenCapture.setY2(y1-MOUSE_Y_OFFSET);
        }

        try {
            screenCapture.screenShot();
            screenCapture.saveShot();
            System.out.println("Screen captured.");
        } catch (IOException e) {
            System.out.println("Failed to capture screen.");
        }
        mousePressed = false;
        Platform.exit();    
    }

    private void handleMouseDrag(MouseEvent event) {
        if (mousePressed) {
            // top left box
            screenShade1.setX(0);
            screenShade1.setY(0);
            if (x1 < event.getX()) {
                screenShade1.setWidth(x1);
            } else {
                screenShade1.setWidth(event.getX());
            }

            if (y1 < event.getY()) {
                screenShade1.setHeight(y1);
            } else {
                screenShade1.setHeight(event.getY());
            }


            // top middle box
            screenShade2.setX(x1);
            screenShade2.setY(0);
            if (x1 < event.getX()) {
                screenShade2.setWidth(event.getX()-x1);
            } else {
                screenShade2.setX(event.getX());
                screenShade2.setWidth(x1-event.getX());
            }

            if (y1 < event.getY()) {
                screenShade2.setHeight(y1);
            } else {
                screenShade2.setHeight(event.getY());
            }

           
            // top right box
            screenShade3.setX(event.getX());
            screenShade3.setY(0);
            if (x1 < event.getX()) {
                screenShade3.setWidth(screenWidth-event.getX());
            } else {
                screenShade3.setX(x1);
                screenShade3.setWidth(screenWidth-x1);
            }

            if (y1 < event.getY()) {
                screenShade3.setHeight(y1);
            } else {
                screenShade3.setHeight(event.getY());
            }


            // middle right box
            screenShade4.setX(event.getX());
            screenShade4.setY(y1);
            if (x1 < event.getX()) {
                screenShade4.setWidth(screenWidth-event.getX());
            } else {
                screenShade4.setX(x1);
                screenShade4.setWidth(screenWidth-x1);
            }

            if (y1 < event.getY()) {
                screenShade4.setHeight(event.getY()-y1);
            } else {
                screenShade4.setY(event.getY());
                screenShade4.setHeight(y1-event.getY());
            }


            // bottom right box
            screenShade5.setX(event.getX());
            screenShade5.setY(event.getY());
            if (x1 < event.getX()) {
                screenShade5.setWidth(screenWidth-event.getX());
            } else {
                screenShade5.setX(x1);
                screenShade5.setWidth(screenWidth-x1);
            }

            if (y1 < event.getY()) {
                screenShade5.setHeight(screenHeight-event.getY());
            } else {
                screenShade5.setY(y1);
                screenShade5.setHeight(screenHeight-y1);
            }


            // bottom middle box
            screenShade6.setX(x1);
            screenShade6.setY(event.getY());
            if (x1 < event.getX()) {
                screenShade6.setWidth(event.getX()-x1);
            } else {
                screenShade6.setX(event.getX());
                screenShade6.setWidth(x1-event.getX());
            }

            if (y1 < event.getY()) {
                screenShade6.setHeight(screenHeight-event.getY());
            } else {
                screenShade6.setY(y1);
                screenShade6.setHeight(screenHeight-y1);
            }


            // bottom left box
            screenShade7.setX(0);
            screenShade7.setY(event.getY());
            if (x1 < event.getX()) {
                screenShade7.setWidth(x1);
            } else {
                screenShade7.setWidth(event.getX());
            }

            if (y1 < event.getY()) {
                screenShade7.setHeight(screenHeight-event.getY());
            } else {
                screenShade7.setY(y1);
                screenShade7.setHeight(screenHeight-y1);
            }


            // middle left box
            screenShade8.setX(0);
            screenShade8.setY(y1);
            if (x1 < event.getX()) {
                screenShade8.setWidth(x1);
            } else {
                screenShade8.setWidth(event.getX());
            }

            if (y1 < event.getY()) {
                screenShade8.setHeight(event.getY()-y1);
            } else {
                screenShade8.setY(event.getY());
                screenShade8.setHeight(y1-event.getY());
            }

        }
    }

    private void turnOffTargetShaders() {

        screenShade1.setFill(new Color(0, 0, 0, .0));
        screenShade2.setFill(new Color(0, 0, 0, .0));
        screenShade3.setFill(new Color(0, 0, 0, .0));
        screenShade4.setFill(new Color(0, 0, 0, .0));
        screenShade5.setFill(new Color(0, 0, 0, .0));
        screenShade6.setFill(new Color(0, 0, 0, .0));
        screenShade7.setFill(new Color(0, 0, 0, .0));
        screenShade8.setFill(new Color(0, 0, 0, .0));
    }

    private void turnOnTargetShaders() {

        screenShade1.setFill(new Color(0, 0, 0, .5));
        screenShade2.setFill(new Color(0, 0, 0, .5));
        screenShade3.setFill(new Color(0, 0, 0, .5));
        screenShade4.setFill(new Color(0, 0, 0, .5));
        screenShade5.setFill(new Color(0, 0, 0, .5));
        screenShade6.setFill(new Color(0, 0, 0, .5));
        screenShade7.setFill(new Color(0, 0, 0, .5));
        screenShade8.setFill(new Color(0, 0, 0, .5));
    }
}