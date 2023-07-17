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

public class GrabImage extends Application {
    private boolean mousePressed;
    private double x1, y1, x2, y2;
    private ScreenCapture screenCapture;
    private double screenWidth, screenHeight;
    private Rectangle screenUnshade, screenShade;
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
      
        stage.setTitle("project-alpha");

        // screen shading
        screenShade = new Rectangle(0, 0, screenWidth, screenHeight);
        screenShade.setFill(new Color(0, 0, 0, .4));

        group = new Group(screenShade, screenCapture);
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

        screenUnshade = new Rectangle(x1, y1, 0, 0);
        screenUnshade.setFill(new Color(1, 1, 1, .3));
        group.getChildren().setAll(screenUnshade, screenShade, screenCapture);

        screenCapture.setMouseX1(x1);
        screenCapture.setMouseY1(y1);
        // System.out.println("\nx1: " + x1 + "\ny1: " + y1);
    }
    
    private void handleMouseRelease(MouseEvent event) {
        if (!mousePressed) {throw new RuntimeException("Mouse not pressed.");}
        x2 = event.getX();
        y2 = event.getY();
        screenCapture.setMouseX2(x2);
        screenCapture.setMouseY2(y2);
        // System.out.println("x2: " + x2 + "\ny2: " + y2);
        // System.out.println();
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
            screenUnshade.setWidth(event.getX()-x1);
            screenUnshade.setHeight(event.getY()-y1);
        }
    }
}