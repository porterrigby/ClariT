package com.prigby;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.robot.Robot;

public class ScreenCapture extends Pane {
    private Robot robot;
    private WritableImage capturedScreen;
    private int mouseX1, mouseX2, mouseY1, mouseY2;
    private boolean hasBeenCropped, hasBeenShot;


    public ScreenCapture() throws IOException {
        this.setPrefSize(2560, 1440);
        robot = new Robot();
        hasBeenCropped = hasBeenShot = false;
    }

    public void cropScreen() {
        EventHandler<MouseEvent> mouseClickHandler = (MouseEvent event) -> {handleMouseClick(event);};
        EventHandler<MouseEvent> mouseDragHandler = (MouseEvent event) -> {handleMouseDrag(event);};
        this.setOnMousePressed(mouseClickHandler);
        this.setOnMouseReleased(mouseDragHandler);
    }

    public void screenShot() {
        if (!hasBeenCropped) {throw new RuntimeException("No screen selection was made.");}
        capturedScreen = robot.getScreenCapture(null, new Rectangle2D(mouseX1, mouseY1, mouseX2, mouseY2));
    }

    public void saveShot() throws IOException {
        if (!hasBeenShot) {throw new RuntimeException("No screenshot was captured.");}
        File outputImage = new File("./src/main/java/com/prigby/tmp/screenshot.png");
        ImageIO.write(SwingFXUtils.fromFXImage(capturedScreen, null), "png", outputImage);    
    }


    //TODO fix this shitssssVVV
    private void handleMouseClick(MouseEvent event) {
        mouseX1 = (int)(event.getX());
        mouseY1 = (int)(event.getY());
    }

    private void handleMouseDrag(MouseEvent event) {
        mouseX2 = (int)(event.getX());
        mouseY2 = (int)(event.getY());
    }
}
