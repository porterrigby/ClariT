package com.prigby;

import org.bytedeco.javacpp.*;
import org.bytedeco.leptonica.*;
import org.bytedeco.tesseract.*;
import static org.bytedeco.leptonica.global.leptonica.*;
import static org.bytedeco.tesseract.global.tesseract.*;

public class App {
    public static void main( String[] args ) {
        BytePointer outputText;
        TessBaseAPI tessAPI = new TessBaseAPI();

        GrabImage.main(null);



        //TODO fix this shit vvv
        // tessAPI.Init("C:/DevKit/TesseractOCR/tessdata-main", "eng");
        tessAPI.Init("src/main/java/com/prigby/tessdata", "eng");

        PIX image = pixRead(args.length > 0 ? args[0] : "src/main/java/com/prigby/tmp/test-image.png");
        tessAPI.SetImage(image);

        outputText = tessAPI.GetUTF8Text();

        System.out.println("OCR output:\n" + outputText.getString());

        tessAPI.End();
        outputText.deallocate();
        pixDestroy(image);
    }
}
