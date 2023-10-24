package com.prigby;

import org.bytedeco.javacpp.*;
import org.bytedeco.leptonica.*;
import org.bytedeco.tesseract.*;
import static org.bytedeco.leptonica.global.leptonica.*;

public class App {
    public static void main( String[] args ) {
        BytePointer outputText;
        TessBaseAPI tessAPI = new TessBaseAPI();

        // Runs GrabImage to collect user-specified screenshot
        ScreenSelect.main(null);

        // Sets traineddata folder path and language to parse
        tessAPI.Init("src/main/java/com/prigby/tessdata", "eng");

        // Creates PIX images object and passes it into tesseract
        PIX image = pixRead("src/main/java/com/prigby/tmp/screenshot.png");
        tessAPI.SetImage(image);

        // Prints parsed text to terminal
        outputText = tessAPI.GetUTF8Text();
        System.out.println("\nOCR output:\n" + outputText.getString());

        // Frees resources? VS Code was complaining without these.
        tessAPI.End();
        tessAPI.close();
        outputText.deallocate();
        pixDestroy(image);
    }
}
