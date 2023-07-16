package com.prigby;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.WritableImage;
import javafx.scene.robot.Robot;

public class ScreenCapture {
    private Robot robot;
    private WritableImage capturedScreen;


    public ScreenCapture() throws IOException {
        robot = new Robot();
    }

    public void screenShot() {
        
        capturedScreen = robot.getScreenCapture(null, new Rectangle2D(0, 0, 2560, 1440));
    }

    public void saveShot() throws IOException {
        File outputImage = new File("./src/main/java/com/prigby/tmp/screenshot.png");
        ImageIO.write(SwingFXUtils.fromFXImage(capturedScreen, null), "png", outputImage);    
    }
}
