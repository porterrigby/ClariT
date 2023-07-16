package com.prigby;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class GrabImage extends Application {
    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello");
        System.out.println( "Totally functioning screenshotting software!" );

        try {
            ScreenCapture screenCapture = new ScreenCapture();        
            screenCapture.screenShot();
            screenCapture.saveShot();
            System.out.println("Image saved to: tmp");
        } catch (IOException e) {
            System.out.println("Failed to capture screen.");
        }
        Platform.exit();
    }
}