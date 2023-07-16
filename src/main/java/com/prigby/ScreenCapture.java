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
    private int mouseX1, mouseX2, mouseY1, mouseY2;
    private boolean hasBeenShot;


    public ScreenCapture() throws IOException {
        robot = new Robot();
        hasBeenShot = false;
        this.mouseX1 = (int)Screen.getPrimary().getBounds().getMinX();
        this.mouseY1 = (int)Screen.getPrimary().getBounds().getMinY();
        this.mouseX2 = (int)Screen.getPrimary().getBounds().getMaxX();
        this.mouseY2 = (int)Screen.getPrimary().getBounds().getMaxY();
    }

    public ScreenCapture(double mouseX1, double mouseY1, double mouseX2, double mouseY2) throws IOException {
        robot = new Robot();
        hasBeenShot = false;
        this.mouseX1 = (int)mouseX1;
        this.mouseY1 = (int)mouseY1;
        this.mouseX2 = (int)mouseX2;
        this.mouseY2 = (int)mouseY2;
    }

    public void setMouseX1(double coordinate) {
        this.mouseX1 = (int)coordinate;
    }
    public void setMouseY1(double coordinate) {
        this.mouseY1 = (int)coordinate;
    }
    public void setMouseX2(double coordinate) {
        this.mouseX2 = (int)coordinate;
    }
    public void setMouseY2(double coordinate) {
        this.mouseY2 = (int)coordinate;
    }

    public int getMouseX1() {
        return mouseX1;
    }

    public int getMouseY1() {
        return mouseY1;
    }

    public int getMouseX2() {
        return mouseX2;
    }

    public int getMouseY2() {
        return mouseY2;
    }

    private int getShotWidth() {
        return Math.abs(getMouseX2()-getMouseX1());
    }

    private int getShotHeight() {
        return Math.abs(getMouseY2()-getMouseY1());
    }

    public void screenShot() throws IOException {
        Rectangle2D region = new Rectangle2D(getMouseX1(), getMouseY1(), getShotWidth(), getShotHeight());
        capturedScreen = robot.getScreenCapture(null, region, true);
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