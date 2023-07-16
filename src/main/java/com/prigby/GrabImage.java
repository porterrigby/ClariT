package com.prigby;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GrabImage extends Application {
    private boolean mousePressed;
    private double x1, y1, x2, y2;
    private ScreenCapture screenCapture;

    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        screenCapture = new ScreenCapture();
        mousePressed = false;


        //XXX Event Handlers
        EventHandler<MouseEvent> mousePressHandler = (MouseEvent event) -> {handleMousePress(event);};
        EventHandler<MouseEvent> mouseReleaseHandler = (MouseEvent event) -> {handleMouseRelease(event);};

       
        stage.setTitle("Hello");
        System.out.println( "Totally functioning screenshotting software!" );

        Scene scene = new Scene(screenCapture, 
                            Screen.getPrimary().getBounds().getMaxX(),
                            Screen.getPrimary().getBounds().getMaxY());
        scene.setFill(new Color(0, 0, 1, .5));
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressHandler);
        scene.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleaseHandler);

        stage.setFullScreen(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setOpacity(.5);
        stage.setScene(scene);
        stage.show();


    }

    private void handleMousePress(MouseEvent event) {
        mousePressed = true;
        x1 = event.getX();
        y1 = event.getY();
        screenCapture.setMouseX1(x1);
        screenCapture.setMouseY1(y1);
        System.out.println("\nx1: " + x1 + "\ny1: " + y1);
    }
    
    private void handleMouseRelease(MouseEvent event) {
        if (!mousePressed) {throw new RuntimeException("Mouse not pressed.");}
        x2 = event.getX();
        y2 = event.getY();
        screenCapture.setMouseX2(x2);
        screenCapture.setMouseY2(y2);
        System.out.println("x2: " + x2 + "\ny2: " + y2);
        System.out.println();
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
}