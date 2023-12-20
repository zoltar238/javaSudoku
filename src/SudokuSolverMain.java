import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class SudokuSolverMain {
    public static void main(String[] args) {
        Ventana ventana = new Ventana();

        File imageFile = new File("C:\\Users\\dabac\\Proton Drive\\Protoandrei\\My files\\SudokuSolver\\untitled\\fupa.jpg");
        Tesseract tesseract = new Tesseract();
        try {

            tesseract.setDatapath("C:\\Users\\dabac\\Proton Drive\\Protoandrei\\My files\\SudokuSolver\\Tess4J\\tessdata");

            // the path of your tess data folder
            // inside the extracted file
            String text = tesseract.doOCR(imageFile);
            // path of your image file
            System.out.print(text);
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }

    }
}


