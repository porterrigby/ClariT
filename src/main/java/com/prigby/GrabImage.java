package com.prigby;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GrabImage extends Application {

    private double screenMinX, screenMinY, screenMaxX, screenMaxY;
    private ScreenCapture screenCapture;

    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        screenCapture = new ScreenCapture();


        //XXX Event Handlers
        EventHandler<MouseEvent> mousePressHandler = (MouseEvent event) -> {handleMousePress(event);};
        EventHandler<MouseEvent> mouseReleaseHandler = (MouseEvent event) -> {handleMouseRelease(event);};

       
        stage.setTitle("Hello");
        System.out.println( "Totally functioning screenshotting software!" );

        Scene scene = new Scene(screenCapture);
        scene.setFill(Color.TRANSPARENT);
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressHandler);
        scene.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleaseHandler);

        stage.setScene(scene);
        stage.show();


    }


    private void handleMousePress(MouseEvent event) {
        screenCapture.setMouseX1(event.getX());
        screenCapture.setMouseY1(event.getY());
    }
    
    private void handleMouseRelease(MouseEvent event) {
        screenCapture.setMouseX2(event.getX());
        screenCapture.setMouseY2(event.getY());
        try {
            screenCapture.screenShot();
            screenCapture.saveShot();
            System.out.println("Screen captured.");
        } catch (IOException e) {
            System.out.println("Failed to capture screen.");
        }
        Platform.exit();    
    }
}