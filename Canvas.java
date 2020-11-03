import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

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
public class Canvas implements Printable {

    private Viewer viewer;

    public Canvas(Model model) {
        /**
         * Canvas actually draws smth on frame
         */
        this.viewer = model.getViewer();
    }


    /**
     * Prints text to File or pdf
     * Creates new instance of PrinterJob with reference to WriteAndRead object
     * If everything OK , shows print window and calls WriteAndRead's print method
     * If no printer found , shows "Printer not found" window
     */
    public void printOnPaper() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * Main printing method described
     * Gets text to print from Model. That is why we need Model reference here
     *
     * @param g    the context into which the page is drawn
     * @param pf   the size and orientation of the page being drawn
     * @param page the zero based index of the page to be drawn
     * @return PAGE_EXISTS if the page is rendered successfully or NO_SUCH_PAGE if pageIndex specifies a non-existent page.
     * @throws PrinterException thrown when the print job is terminated.
     */

    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        String textToPrint = viewer.getTextArea().getText();
        System.out.println(textToPrint);
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        int x = (int) pf.getImageableX() + 10;
        int y = (int) pf.getImageableY() + 10;
        int pageWidth = (int) pf.getWidth();

        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int heightRow = fontMetrics.getHeight();
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        for (int i = 0; i < textToPrint.length(); i++) {
            char symbol = textToPrint.charAt(i);
            int widthSymbol = fontMetrics.charWidth(symbol);
            g2d.drawString(" " + symbol, x, y);
            x = x + widthSymbol;
            if (x > pageWidth - 10 || symbol == '\n') {
                x = (int) pf.getImageableX() + 10;
                y = y + heightRow;
            }
        }
        return PAGE_EXISTS;
    }

}
