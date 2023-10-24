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
    private double X1, X2, Y1, Y2;
    private boolean hasBeenShot;


    public ScreenCapture() throws IOException {
        robot = new Robot();
        hasBeenShot = false;
        this.X1 = (int)Screen.getPrimary().getBounds().getMinX();
        this.Y1 = (int)Screen.getPrimary().getBounds().getMinY();
        this.X2 = (int)Screen.getPrimary().getBounds().getMaxX();
        this.Y2 = (int)Screen.getPrimary().getBounds().getMaxY();
    }

    public ScreenCapture(int X1, int Y1, int X2, int Y2) throws IOException {
        robot = new Robot();
        hasBeenShot = false;
        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
    }

    public void setX1(double coordinate) {
        this.X1 = coordinate;
    }
    public void setY1(double coordinate) {
        this.Y1 = coordinate;
    }
    public void setX2(double coordinate) {
        this.X2 = coordinate;
    }
    public void setY2(double coordinate) {
        this.Y2 = coordinate;
    }

    public double getX1() {
        return X1;
    }

    public double getY1() {
        return Y1;
    }

    public double getX2() {
        return X2;
    }

    public double getY2() {
        return Y2;
    }

    private double getShotWidth() {
        return Math.abs(getX2()-getX1());
    }

    private double getShotHeight() {
        return Math.abs(getY2()-getY1());
    }

    public void screenShot() throws IOException {
        Rectangle2D region = new Rectangle2D(getX1(), getY1(), getShotWidth(), getShotHeight());
        capturedScreen = robot.getScreenCapture(null, region, false);
        hasBeenShot = true;
    }

    public void saveShot() throws IOException {
        if (!hasBeenShot) {throw new RuntimeException("No screenshot was captured.");}
        File outputImage = new File("./src/main/java/com/prigby/tmp/screenshot.png");
        ImageIO.write(SwingFXUtils.fromFXImage(capturedScreen, null), "png", outputImage);
        hasBeenShot = false;    
    }

    public boolean getShotStatus() {
        return hasBeenShot;
    }
}