import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
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
public class Model {
    /**
     * Model directly manages the data, logic and rules of the application.
     * Model needs Viewer to send data to Viewer directly
     * String text stores text from Viewer's text area
     * Highligher class is used to mark text with another color
     * Painter is used to choose color and type of marking
     */
    private Viewer viewer;
    private String text;
    private Highlighter highlighter;
    private Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
    private String[] fontList, styleList, sizes;


    /**
     * Model needs viewer in it's constructor as parameter
     */
    public Model(Viewer viewer) {
        this.viewer = viewer;
        fontList = getAllFonts();
        styleList = getAllStyles();
        sizes = Arrays.toString(getAllSizes()).split("[\\[\\]]")[1].split(", ");
    }


    public String[] getFontList() {
        return fontList;
    }

    public String[] getStyleList() {
        return styleList;
    }

    public String[] getSizes() {
        return sizes;
    }

    private String[] getAllFonts() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        return fonts;
    }

    private String[] getAllStyles() {
        String[] styleArray = {"Regular", "Italic", "Bold", "Bold Italic"};
        return styleArray;
    }

    private int[] getAllSizes() {
        int[] sizes = new int[16];
        for (int i = 0; i < 5; i++) {
            sizes[i] = i + 8;
        }
        for (int i = 5, j = 9; i < 13; i++, j++) {
            sizes[i] = i + j;
        }
        sizes[13] = 36;
        sizes[14] = 48;
        sizes[15] = 72;
        return sizes;
    }



    /**
     * Main logic of the program
     * When Controller sends some String command Model analyzes it and does action
     * Switch operator is used for efficent activity
     * When case is found - break operator works
     */
    public void doAction(String value) {
        switch (value) {
            case "New Document":
                newDocument();
                break;
            case "Open Document":
                openDocument();
                break;
            case "Save Document":
                saveDocument();
                break;
            case "Print Document":
                print();
                break;
            case "Close Program":
                exit();
            case "Cut Text":
                cutText();
                break;
            case "Copy Text":
                copyText();
                break;
            case "Paste Text":
                pasteText();
                break;
            case "Clear Text":
                clearText();
                break;
            case "Find Text":
                findText();
                break;
            case "Go Text":
                goText();
                break;
            case "Marker All":
                markerAll();
                break;
            case "Time and Date":
                timeAndDate();
                break;
            case "View Help":
                viewHelp();
                break;
            case "About":
                viewAboutWindow();
                break;
            case "Word Space":
                wordSpace();
                break;
            case "Font":
                viewFontDialog();
                break;
            case "Status Space":
                viewStatusBar();
                break;
            case "OK":
                changeFont();
                break;
            case "Cancel":
                viewFontDialog();
                break;
            case "Color":
                changeTextColor();
                break;
            default:
        }
    }

    public void doCaretAction() {
        viewer.getSymbols().setText("Symbols: " + viewer.getTextArea().getText().length());
        if (!viewer.getTextArea().getText().equals(""))
            viewer.getLines().setText("            Lines: " + viewer.getTextArea().getLineCount());
    }

    public void doListAction(JList list) {
        String selectedValue = (String) list.getSelectedValue();
        int sampleStyle;
        if (Arrays.stream(fontList).anyMatch(selectedValue::equals)) {
            viewer.setSampleFont(selectedValue);
        } else if (Arrays.stream(styleList).anyMatch(selectedValue::equals)) {
            sampleStyle = styleList[list.getSelectedIndex()].equals("Regular") ? Font.PLAIN
                    : styleList[list.getSelectedIndex()].equals("Italic") ? Font.ITALIC
                    : styleList[list.getSelectedIndex()].equals("Bold") ? Font.BOLD
                    : Font.BOLD + Font.ITALIC;
            viewer.setSampleStyle(sampleStyle);
        } else {
            viewer.setSampleSize(Integer.parseInt(sizes[list.getSelectedIndex()]));
        }
        viewer.getSampleText().setFont(new Font(viewer.getSampleFont(), viewer.getSampleStyle(), viewer.getSampleSize()));
    }


    /**
     * Returns viewer
     *
     * @return viewer instance of class Viewer
     */
    public Viewer getViewer() {
        return viewer;
    }

    /**
     * Opens text from any document
     *
     * @param fileName absolute path
     * @return text from a File
     */
    private String open(String fileName) {
        return WriteAndRead.readFromFile(fileName);
    }

    /**
     * Saves text from panel to file
     *
     * @param fileName absolute path to File where text will be saved
     * @param text     text from panel
     */

    private void save(String fileName, String text) {
        WriteAndRead.writeToFile(fileName, text);
    }

    /**
     * Prints text from panel to paper or pdf
     */
    private void print() {
        Canvas canvas = viewer.getCanvas();
        canvas.printOnPaper();
    }

    /**
     * Close program
     */

    private void exit() {
        System.exit(0);
    }


    /**
     * Clears text area
     * If text area contains something:
     * Opens confirmation window to choose save or not save your text somewhere.
     * If yes - opens filechooser
     * If no - just clears text
     * If cancel - returns last text without any changes
     */
    private void newDocument() {
        String text = viewer.getTextArea().getText();
        if (!text.equals("")) {
            int returnValue = viewer.showYesNoCancelDialog();
            if (returnValue == 0) {
                if (saveDocument()) {
                    viewer.update("");
                }
            } else if (returnValue == 1) {
                viewer.update("");
            }
        }
    }

    /**
     * Opens filechooser and imports text from any textfile
     */
    private void openDocument() {
        try {
            String fileName = viewer.openFileChooser();
            text = open(fileName);
            viewer.update(text);
        } catch (NullPointerException npe) {
            System.out.println("File was not choosen!");
        }
    }

    /**
     * Saves data from text panel to any document
     *
     * @return true or false - depends on result of saving procedure
     */

    private boolean saveDocument() {
        try {
            String fileName = viewer.openFileChooser();
            text = viewer.getTextArea().getText();
            save(fileName, text);
            return true;
        } catch (NullPointerException npe) {
            System.out.println("File was not choosen!");
            return false;
        }
    }

    /**
     * Cuts and saves selected text to buffer
     */
    private void cutText() {
        viewer.getTextArea().cut();
    }

    /**
     * Copies selected copy to text buffer
     */

    private void copyText() {
        viewer.getTextArea().copy();
    }

    /**
     * Pastes text from buffer
     */

    private void pasteText() {
        viewer.getTextArea().paste();
    }

    /**
     * Clear selected text
     */
    private void clearText() {
        int start = viewer.getTextArea().getSelectionStart();
        int end = viewer.getTextArea().getSelectionEnd();
        viewer.getTextArea().replaceRange("", start, end);
    }

    /**
     * Find and highlight specific word in all text
     */

    private void findText() {
        try {
            String word = viewer.inputWord();
            highlighter = viewer.getTextArea().getHighlighter();
            String content = viewer.getTextArea().getText();
            while (content.lastIndexOf(word) >= 0) {
                int startOfWord = content.lastIndexOf(word);
                int endOfWord = startOfWord + word.length();
                try {
                    highlighter.addHighlight(startOfWord, endOfWord, painter);
                } catch (BadLocationException e) {
                    System.out.println("Nothing found!");
                }
                content = content.substring(0, startOfWord - 1);
            }
        } catch (NullPointerException npe) {
            System.out.println("No input");
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Index out of bounds");
        }
    }

    /**
     * Find ,highlight and move to the next specific word
     */

    private void goText() {
        try {
            String word = viewer.inputWord();
            highlighter = viewer.getTextArea().getHighlighter();
            int startOfWord = viewer.getTextArea().getText().indexOf(word);
            int endOfWord = startOfWord + word.length();
            highlighter.addHighlight(startOfWord, endOfWord, painter);
            viewer.getTextArea().setCaretPosition(startOfWord);
        } catch (BadLocationException e) {
            System.out.println("Nothing found!");
        } catch (NullPointerException npe) {
            System.out.println("No input");
        }

    }

    /**
     * Marks all text on textarea
     */

    private void markerAll() {
        viewer.getTextArea().selectAll();
    }

    /**
     * Inputs current time and date
     */

    private void timeAndDate() {
        viewer.getTextArea().insert(Date.from(Instant.now()).toString(), viewer.getTextArea().getCaretPosition());
    }

    /**
     * Enables/Disables line wrapping
     */

    private void wordSpace() {
        if (viewer.getTextArea().getLineWrap()) {
            viewer.getTextArea().setLineWrap(false);
        } else {
            viewer.getTextArea().setLineWrap(true);
        }
    }

    /**
     * Changes font , font size and color of text
     */

    private void viewFontDialog() {
        viewer.enableFontDialog();
    }

    private void changeFont() {
        viewer.getTextArea().setFont((viewer.getSampleText().getFont()));
        viewer.getTextArea().setForeground(viewer.getSampleText().getForeground());
        viewFontDialog();
    }

    /**
     * Asks viewer to open "Help" window
     */

    private void viewHelp() {
        viewer.showHelp();
    }

    /**
     * Asks viewer to open "About" window
     */

    private void viewAboutWindow() {
        viewer.aboutWindow();
    }

    /**
     * Enables/Disables Status bar with symbols and lines count
     */

    private void viewStatusBar() {
        viewer.enableStatusBar();
    }

//    private void viewFontDialog() {
//        viewer.enableFontDialog();
//    }

    private void changeTextColor() {
        Color color = JColorChooser.showDialog(viewer.getFontDialog(), "Font", Color.BLACK);
        System.out.println(color);
        if (color != null) {
            viewer.getSampleText().setForeground(color);
        }
    }
}
