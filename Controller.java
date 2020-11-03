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

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Controller accepts input and converts it to commands for the Moud vibor del or View.
 * Implements ActionListener interface to listen to Actions from Viewer
 * Implements CaretListener interface to listen to Caret events from Viewer(TextArea)
 * Implements Printable interface to be able to print text on paper or any other file
 */
public class Controller implements ActionListener, CaretListener, ListSelectionListener {


    private Model model;

    /**
     * Controller initialization
     * Creates new Model instance
     */

    public Controller(Viewer viewer) {
        model = new Model(viewer);
    }

    /**
     * @return Model reference
     */

    public Model getModel() {
        return model;
    }

    /**
     * Listens to some actions and sends String commands to model
     */
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        model.doAction(command);
    }

    /**
     * Called when the caret in
     * the listened-to component moves or when the selection in
     * the listened-to component changes.
     *
     * @param caretEvent
     */

    public void caretUpdate(CaretEvent caretEvent) {
        model.doCaretAction();
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            JList list = (JList) e.getSource();
            model.doListAction(list);
        }
    }
}

