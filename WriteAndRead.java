/**
 * <p>
 * Notepad program made with Java and Swing with MVC pattern
 * Main features:
 * Open documents;
 * Create documents;
 * Save documents into text file;
 * Print document on paper or pdf file.
 * And a lot of other features!
 *
 * @author Komoldin Khuzhamberdiev
 * 
 */
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * WriteAndRead class which makes all of the work with text saving,opening files and text printing actually
 * This class needs Model reference to get text to print
 * WriteAndRead implements Printable interface to be able to Print text to paper of any pdf
 */
public class WriteAndRead{

    private Model model;

    /**
     * Write and read constuctor
     * @param model asks for Model reference
     */

    public WriteAndRead(Model model) {
        this.model = model;
    }

    /**
     * THIS METHOD IS STATIC, SO YOU CAN CALL IT WITHOUT CREATING NEW WRITEANDREAD INSTANCE
     * Reads a file and represents all text from file to User on frame
     * @param fileName absolute path taken from file chooser
     * @return text from file to frame
     * Catches exception if cannot read file
     */
    public static String readFromFile(String fileName) {
        String text = "";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String l;
            while ((l = bufferedReader.readLine()) != null) {
                text = text + l + "\n";
            }

            bufferedReader.close();
        } catch (IOException ioe) {
            System.out.println(ioe);

        }

        return text;
    }

    /**
     * THIS METHOD IS STATIC, SO YOU CAN CALL IT WITHOUT CREATING NEW WRITEANDREAD INSTANCE
     * Writes text from frame to File
     * @param filename absolute path to File taken from file chooser
     * @param text Actual text , which will be written to File
     * Catches exception if cannot write on File (maybe you choosed wrong format?
     */
    public static void writeToFile(String filename, String text) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

}
