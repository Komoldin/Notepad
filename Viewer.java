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
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Viewer represents all information. Viewer receives data from the Controller of the Model it and presents it to display.
 */

public class Viewer {

    /**
     * Viewer creates instance of Controller inside its constructor
     * JTextArea is used for text displaying and writing
     * JFrame is a top window with a title and a border.
     * JFileChooser opens file chooser when user needs to choose file to open, save or print
     * JPanel status space is panel where the number of lines and symbols can be monitored
     * JLabel symbols and lines are placed on status space
     */

    private Controller controller;
    private JTextArea textArea;
    private JFrame frame;
    private JFileChooser fileChooser;
    private JPanel statusSpace;
    private JLabel symbols;
    private JLabel lines;
    private Canvas canvas;
    private Model model;
    private JDialog fontDialog;
    private JLabel sampleText;
    private String SampleFont;
    private int SampleStyle, SampleSize;

    /**
     * Viewer intialization:
     * Creating instance of controller with reference to this Viewer class(this keyword)
     * Than creates a Model instance from controller
     * Creates Canvas instance with reference to Model
     * Other part is user interface part.
     */
    public Viewer() {
        controller = new Controller(this);
        model = controller.getModel();
        canvas = new Canvas(model);

        fontDialog = getJFontChooser();

        textArea = new JTextArea();
        textArea.setFont(new java.awt.Font("Monospaced", Font.BOLD | java.awt.Font.ITALIC, 25));
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.addCaretListener(controller);
        JMenuBar menuBar = getMenuBar();

        frame = new JFrame("Notepad");
        frame.setSize(500, 500);
        frame.setJMenuBar(menuBar);
        frame.add(scrollPane);
        frame.setVisible(true);

        statusSpace = new JPanel();
        symbols = new JLabel();
        lines = new JLabel();
        statusSpace.add(symbols);
        statusSpace.add(lines);
        statusSpace.setVisible(false);
        frame.add(BorderLayout.SOUTH, statusSpace);

        JToolBar toolBar = getToolBar();
        frame.add(BorderLayout.NORTH, toolBar);
    }

    /**
     * @return font JDialog
     */

    public JDialog getFontDialog() {
        return fontDialog;
    }

    /**
     * @return sample text Jlabel
     */

    public JLabel getSampleText() {
        return sampleText;
    }

    /**
     * @return sample Style
     */

    public int getSampleStyle() {
        return SampleStyle;
    }

    /**
     * @return sample Font
     */
    public String getSampleFont() {
        return SampleFont;
    }

    /**
     * @return sample Size
     */
    public int getSampleSize() {
        return SampleSize;
    }

    /**
     * @return set sample text Font
     */
    public void setSampleFont(String sampleFont) {
        SampleFont = sampleFont;
    }

    /**
     * @return set sample text Style
     */
    public void setSampleStyle(int sampleStyle) {
        SampleStyle = sampleStyle;
    }

    /**
     * @return set sample text Font
     */
    public void setSampleSize(int sampleSize) {
        SampleSize = sampleSize;
    }

    /**
     * @return Returns Symbols Jlabel to make it able to change text inside
     */
    public JLabel getSymbols() {
        return symbols;
    }

    /**
     * @return Returns Lines Jlabel to make it able to change text inside
     */

    public JLabel getLines() {
        return lines;
    }


    /**
     * Returns instance of Canvas class
     *
     * @return
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Returns textArea for some purposes
     *
     * @return text from Jtext
     */
    public JTextArea getTextArea() {
        return textArea;
    }

    /**
     * Updates text on text area
     */
    public void update(String text) {
        textArea.setText(text);
    }

    /**
     * Chooses file chooser window
     *
     * @return String with file absolute path
     */

    public String openFileChooser() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        fileChooser.showOpenDialog(frame);
        return fileChooser.getSelectedFile().getAbsolutePath();
    }

    /**
     * Opens dialog of file closing confirmation
     * Has three options:
     * Close and save current text to file(file is being choosed by user in filechooser)
     * Close without saving
     * Return without any changes
     *
     * @return answer
     */
    public int showYesNoCancelDialog() {
        String[] options = {"YES", "NO", "CANCEL"};
        return JOptionPane.showOptionDialog(new JFrame(),
                "All non-saved data will be erased!",
                "Save this text somewhere?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
    }

    /**
     * Opens "Help window"
     */
    public void showHelp() {
        JOptionPane.showMessageDialog(new JFrame(),
                "This is Help Window!",
                "Need some help?", JOptionPane.QUESTION_MESSAGE);
    }


    /**
     * Opens "About" window
     */
    public void aboutWindow() {
        JOptionPane.showMessageDialog(new JFrame(),
                " Authors:\n Ravshan Khamidov \n Khuzhamberdiev Komoldin \n Bishkek 2020",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Opens JDialog window with input word
     *
     * @return String to find
     */
    public String inputWord() {
        return JOptionPane.showInputDialog(frame, "Enter text to find");
    }

    /**
     * If status bar is visible makes it invisible
     * If status bar is invisible makes it visible
     */
    public void enableStatusBar() {
        if (statusSpace.isVisible()) {
            statusSpace.setVisible(false);
        } else
            statusSpace.setVisible(true);
    }

    /**
     * If font dialog is visible makes it invisible
     * If font dialog is invisible makes it visible
     */
    public void enableFontDialog() {
        if (fontDialog.isVisible()) {
            fontDialog.setVisible(false);
        } else
            fontDialog.setVisible(true);
    }

    /**
     * Tool bar
     * Contains JButtons with Icons
     * Icons are being converted from gif images
     * All the converting part is wrapped into try/catch
     * User can drag toolbar by his own preferences
     */

    private JToolBar getToolBar() {
        JButton newFileButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/new.gif"));
            newFileButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        newFileButton.setActionCommand("New Document");
        newFileButton.addActionListener(controller);

        JButton openFileButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/open.gif"));
            openFileButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        openFileButton.setActionCommand("Open Document");
        openFileButton.addActionListener(controller);

        JButton saveFileButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/save.gif"));
            saveFileButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        saveFileButton.setActionCommand("Save Document");
        saveFileButton.addActionListener(controller);

        JButton cutTextButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/cut.gif"));
            cutTextButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        cutTextButton.setActionCommand("Cut Text");
        cutTextButton.addActionListener(controller);

        JButton copyTextButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/copy.gif"));
            copyTextButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        copyTextButton.setActionCommand("Copy Document");
        copyTextButton.addActionListener(controller);

        JButton pasteTextButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/past.gif"));
            pasteTextButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        pasteTextButton.setActionCommand("Paste Document");
        pasteTextButton.addActionListener(controller);

        JButton fontTextButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/color.gif"));
            fontTextButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        fontTextButton.setActionCommand("Font");
        fontTextButton.addActionListener(controller);

        JToolBar toolBar = new JToolBar();
        toolBar.add(newFileButton);
        toolBar.add(openFileButton);
        toolBar.add(saveFileButton);
        toolBar.addSeparator();
        toolBar.add(cutTextButton);
        toolBar.add(copyTextButton);
        toolBar.add(pasteTextButton);
        toolBar.add(fontTextButton);

        return toolBar;
    }

    /**
     * Menu bar
     * Contains Jmenubars
     * Jmenubars contain JmenuItems
     * JmenuItems have Name, ImageIcon and Hotkeys
     * When JmenuItems are choosen , they send ActionCommands to Model
     */
    private JMenuBar getMenuBar() {
        /**
         * CTRL + N hotkey
         */
        JMenuItem createDocumentJMenuItem = new JMenuItem("New", new ImageIcon("images/new.gif"));
        createDocumentJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        createDocumentJMenuItem.addActionListener(controller);
        createDocumentJMenuItem.setActionCommand("New Document");

        /**
         * CTRL + O hotkey
         */
        JMenuItem openDocumentJMenuItem = new JMenuItem("Open ...", new ImageIcon("images/open.gif"));
        openDocumentJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openDocumentJMenuItem.addActionListener(controller);
        openDocumentJMenuItem.setActionCommand("Open Document");

        /**
         * CTRL + S hotkey
         */
        JMenuItem saveDocumentJMenuItem = new JMenuItem("Save", new ImageIcon("images/save.gif"));
        saveDocumentJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveDocumentJMenuItem.addActionListener(controller);
        saveDocumentJMenuItem.setActionCommand("Save Document");

        /**
         * CTRL + P hotkey
         */
        JMenuItem printDocumentJMenuItem = new JMenuItem("Print ...", new ImageIcon("images/print.gif"));
        printDocumentJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        printDocumentJMenuItem.addActionListener(controller);
        printDocumentJMenuItem.setActionCommand("Print Document");

        /**
         * No hotkey
         */
        JMenuItem closeJMenuItem = new JMenuItem("Exit", new ImageIcon("images/delit.gif"));
        closeJMenuItem.addActionListener(controller);
        closeJMenuItem.setActionCommand("Close Program");

        /**
         * Creates file menu and add items initalized above
         */
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        fileMenu.add(createDocumentJMenuItem);
        fileMenu.add(openDocumentJMenuItem);
        fileMenu.add(saveDocumentJMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(printDocumentJMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(closeJMenuItem);

        /**
         * CTRL + X hotkey
         */
        JMenuItem cutTextJMenuItem = new JMenuItem("Cut", new ImageIcon("images/cut.gif"));
        cutTextJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cutTextJMenuItem.addActionListener(controller);
        cutTextJMenuItem.setActionCommand("Cut Text");

        /**
         * CTRL + C hotkey
         */
        JMenuItem copyTextJMenuItem = new JMenuItem("Copy", new ImageIcon("images/copy.gif"));
        copyTextJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copyTextJMenuItem.addActionListener(controller);
        copyTextJMenuItem.setActionCommand("Copy Text");

        /**
         * CTRL + V hotkey
         */
        JMenuItem pasteTextJMenuItem = new JMenuItem("Paste", new ImageIcon("images/past.gif"));
        pasteTextJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        pasteTextJMenuItem.addActionListener(controller);
        pasteTextJMenuItem.setActionCommand("Paste Text");

        /**
         * CTRL + D hotkey
         */
        JMenuItem clearTextJMenuItem = new JMenuItem("Clear", new ImageIcon("images/delit.gif"));
        clearTextJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        clearTextJMenuItem.addActionListener(controller);
        clearTextJMenuItem.setActionCommand("Clear Text");

        /**
         * CTRL + F hotkey
         */
        JMenuItem findTextItem = new JMenuItem("Find Text", new ImageIcon("images/find.gif"));
        findTextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        findTextItem.addActionListener(controller);
        findTextItem.setActionCommand("Find Text");

        /**
         * CTRL + G hotkey
         */
        JMenuItem goTextItem = new JMenuItem("Go Text", new ImageIcon("images/go.gif"));
        goTextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        goTextItem.addActionListener(controller);
        goTextItem.setActionCommand("Go Text");

        /**
         * CTRL + A hotkey
         */
        JMenuItem markAllTextItem = new JMenuItem("Marker All", new ImageIcon("images/marker.gif"));
        markAllTextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        markAllTextItem.addActionListener(controller);
        markAllTextItem.setActionCommand("Marker All");

        /**
         * F5 hotkey
         */
        JMenuItem timeAndDateTextItem = new JMenuItem("Time and Date", new ImageIcon("images/time.gif"));
        timeAndDateTextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        timeAndDateTextItem.addActionListener(controller);
        timeAndDateTextItem.setActionCommand("Time and Date");

        /**
         * Creates edit Menu and adds items inialized above
         */
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        editMenu.add(cutTextJMenuItem);
        editMenu.add(copyTextJMenuItem);
        editMenu.add(pasteTextJMenuItem);
        editMenu.add(clearTextJMenuItem);
        editMenu.add(new JSeparator());
        editMenu.add(findTextItem);
        editMenu.add(goTextItem);
        editMenu.add(new JSeparator());
        editMenu.add(markAllTextItem);
        editMenu.add(timeAndDateTextItem);


        /**
         * No hotkey
         */
        JMenuItem wordSpace = new JCheckBoxMenuItem("Word space", new ImageIcon("images/wordSpace.gif"));
        wordSpace.addActionListener(controller);
        wordSpace.setActionCommand("Word Space");

        /**
         * CTRL + T hotkey
         */
        JMenuItem font = new JMenuItem("Font", new ImageIcon("images/font.gif"));
        font.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        font.addActionListener(controller);
        font.setActionCommand("Font");

        /**
         * Creates format Menu and adds items initalized above
         */
        JMenu formatMenu = new JMenu("Format");
        formatMenu.add(wordSpace);
        formatMenu.add(font);

        /**
         * CTRL + I hotkey
         */
        JMenuItem statusSpace = new JCheckBoxMenuItem("Status space", new ImageIcon("images/options.gif"));
        statusSpace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        statusSpace.addActionListener(controller);
        statusSpace.setActionCommand("Status Space");

        /**
         * Creates view Menu and adds items initalized above
         */
        JMenu viewMenu = new JMenu("View");
        viewMenu.add(statusSpace);


        /**
         * CTRL + N hotkey
         */
        JMenuItem showHelpWindow = new JMenuItem("View Help");
        showHelpWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        showHelpWindow.addActionListener(controller);
        showHelpWindow.setActionCommand("View Help");

        /**
         * CTRL + N hotkey
         */
        JMenuItem showAboutWindow = new JMenuItem("About");
        showAboutWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        showAboutWindow.addActionListener(controller);
        showAboutWindow.setActionCommand("About");

        /**
         * CTRL + N hotkey
         */
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(showHelpWindow);
        helpMenu.add(showAboutWindow);

        /**
         * CTRL + N hotkey
         */
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        return menuBar;

    }

    /**
     * JFont Chooser
     * Contains JList fonts, styles,sizes
     * Jmenubars contain JmenuItems
     * JmenuItems have Name, ImageIcon and Hotkeys
     * When JmenuItems are choosen , they send ActionCommands to Model
     */

    private JDialog getJFontChooser() {
        fontDialog = new JDialog();
        fontDialog.setModal(true);
        fontDialog.setTitle("Font");
        fontDialog.setSize(434, 260);
        fontDialog.setLayout(new FlowLayout());
        fontDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JLabel fontLabel = new JLabel("Font:");
        JLabel styleLabel = new JLabel("Font Style:");
        JLabel sizeLabel = new JLabel("Size:");
        JButton okButton = new JButton("OK");
        fontDialog.getRootPane().setDefaultButton(okButton);
        JButton cancelButton = new JButton("Cancel");
        JButton colorButton = new JButton("Color");
        sampleText = new JLabel("AaBbYyZz");
        SampleFont = "Lucida Console";
        SampleStyle = Font.PLAIN;
        SampleSize = 12;
        JList fontList = new JList(model.getFontList());
        JList styleList = new JList(model.getStyleList());
        JList sizeList = new JList(model.getSizes());
        fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        styleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel okCancelButton = new JPanel();
        okCancelButton.setLayout(new BoxLayout(okCancelButton, BoxLayout.Y_AXIS));
        okButton.setMaximumSize(cancelButton.getMaximumSize());
        okCancelButton.add(okButton);
        okCancelButton.add(Box.createRigidArea(new Dimension(0, 5)));
        okCancelButton.add(cancelButton);
        JPanel samplePanel = new JPanel();
        TitledBorder sampleBorder = BorderFactory.createTitledBorder("Sample");
        samplePanel.setBorder(sampleBorder);
        samplePanel.add(sampleText);
        samplePanel.setPreferredSize(new Dimension(140, 48));
        fontList.addListSelectionListener(controller);
        styleList.addListSelectionListener(controller);
        sizeList.addListSelectionListener(controller);
        okButton.addActionListener(controller);
        cancelButton.addActionListener(controller);
        colorButton.addActionListener(controller);

        JPanel fontPanel = new JPanel(new GridBagLayout());
        JPanel stylePanel = new JPanel(new GridBagLayout());
        JPanel sizePanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        fontPanel.add(fontLabel, constraints);
        stylePanel.add(styleLabel, constraints);
        sizePanel.add(sizeLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridy = 2;
        JScrollPane fontScroll = new JScrollPane(fontList);
        JScrollPane styleScroll = new JScrollPane(styleList);
        JScrollPane sizeScroll = new JScrollPane(sizeList);
        styleScroll.setPreferredSize(new Dimension(125, 110));
        sizeScroll.setPreferredSize(new Dimension(59, 110));
        fontScroll.setPreferredSize(new Dimension(135, 110));
        fontPanel.add(fontScroll, constraints);
        stylePanel.add(styleScroll, constraints);
        sizePanel.add(sizeScroll, constraints);
        fontDialog.add(fontPanel);
        fontDialog.add(stylePanel);
        fontDialog.add(sizePanel);
        fontDialog.add(okCancelButton);
        fontDialog.add(colorButton);
        fontDialog.add(Box.createRigidArea(new Dimension(30, 0)));
        fontDialog.add(samplePanel);
        fontDialog.setResizable(false);

        return fontDialog;
    }


}

