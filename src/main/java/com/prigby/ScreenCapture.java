package com.prigby;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.robot.Robot;
import javafx.stage.Screen;

public class ScreenCapture extends Pane {
    private Robot robot;
    private WritableImage capturedScreen;
    private double mouseX1, mouseX2, mouseY1, mouseY2;
    private boolean hasBeenShot;


    public ScreenCapture() throws IOException {
        robot = new Robot();
        hasBeenShot = false;
        this.mouseX1 = Screen.getPrimary().getBounds().getMinX();
        this.mouseY1 = Screen.getPrimary().getBounds().getMinY();
        this.mouseX2 = Screen.getPrimary().getBounds().getMaxX();
        this.mouseY2 = Screen.getPrimary().getBounds().getMaxY();
    }

    public ScreenCapture(double mouseX1, double mouseY1, double mouseX2, double mouseY2) throws IOException {
        robot = new Robot();
        hasBeenShot = false;
        this.mouseX1 = mouseX1;
        this.mouseY1 = mouseY1;
        this.mouseX2 = mouseX2;
        this.mouseY2 = mouseY2;
    }

    public void setMouseX1(double coordinate) {
        this.mouseX1 = coordinate;
    }
    public void setMouseY1(double coordinate) {
        this.mouseX1 = coordinate;
    }
    public void setMouseX2(double coordinate) {
        this.mouseX1 = coordinate;
    }
    public void setMouseY2(double coordinate) {
        this.mouseX1 = coordinate;
    }

    public void screenShot() throws IOException {
        // if (!hasBeenCropped) {throw new RuntimeException("No screen selection was made.");}
        capturedScreen = robot.getScreenCapture(null, new Rectangle2D(mouseX1, mouseY1, mouseX2, mouseY2));
        hasBeenShot = true;
    }

    public void saveShot() throws IOException {
        // if (!hasBeenShot) {throw new RuntimeException("No screenshot was captured.");}
        File outputImage = new File("./src/main/java/com/prigby/tmp/screenshot.png");
        ImageIO.write(SwingFXUtils.fromFXImage(capturedScreen, null), "png", outputImage);
        hasBeenShot = false;    
    }

    public boolean getShotStatus() {
        return hasBeenShot;
    }
}