import factory.UserFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class HeapManagerGUI extends JFrame {
    private final Font heapOutputFont = new Font("Consolas", Font.PLAIN, 14);
    private final JComboBox<Object> userType;
    private final ArrayList<JButton> jButtonsContainer = new ArrayList<>();
    private final ArrayList<JTextField> jTextFieldsContainer = new ArrayList<>();
    private final JTextArea heapOutputArea;
    private Heap heap;

    HeapManagerGUI() {
        this.setTitle("Heap Manager");
        heap = new Heap("integer");

        /* Main frame panel
         * */
        JPanel mainFrame = new JPanel();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setBackground(Color.LIGHT_GRAY);

        /* Tools menu panel
         * */
        JPanel toolsMenuPanel = new JPanel(new FlowLayout());
        toolsMenuPanel.setPreferredSize(new Dimension(200, 0));
        mainFrame.add(toolsMenuPanel, BorderLayout.WEST);


        /* Heap output (view) panel
         * */
        heapOutputArea = new JTextArea(0,30);
        heapOutputArea.setEditable(false);
        heapOutputArea.setFont(heapOutputFont);
        heapOutputArea.setBackground(Color.WHITE);
        mainFrame.add(heapOutputArea, BorderLayout.EAST);
        mainFrame.add(new JScrollPane(heapOutputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        /* Combo box
         * */
        userType = new JComboBox<>(UserFactory.getTypeNameList().toArray());
        userType.setPreferredSize(new Dimension(170, 20));
        toolsMenuPanel.add(userType);
        userType.addActionListener(e -> {
            heap = new Heap((String) userType.getSelectedItem());
            heapOutputArea.setText("");
            heapOutputArea.append(" > now selected " + userType.getSelectedItem() + " type" + "\n");
        });


        /* Insertion panel
         * */
        JTextField nodeInsertionTextField = new JTextField("enter data here", 18);
        JButton nodeInsertionButton = new JButton("insert");
        nodeInsertionButton.addActionListener(e -> heapOutputArea.append(String.valueOf(heap.insertNode(nodeInsertionTextField.getText()))));
        jButtonsContainer.add(nodeInsertionButton);
        jTextFieldsContainer.add(nodeInsertionTextField);
        createJPanel("Insert", 90, jButtonsContainer, jTextFieldsContainer, toolsMenuPanel);

        /* Insertion at index panel
         * */
        JTextField indexTextField = new JTextField("enter data here", 18);
        JTextField dataTextField = new JTextField("enter index here", 18);
        JButton nodeByIndexInsertionButton = new JButton("insert");
        nodeByIndexInsertionButton.addActionListener(e -> heapOutputArea.append(String.valueOf(heap.insertNode(Integer.parseInt(indexTextField.getText()), dataTextField.getText()))));
        jButtonsContainer.add(nodeByIndexInsertionButton);
        Collections.addAll(jTextFieldsContainer, indexTextField, dataTextField);
        createJPanel("Insert by index", 115, jButtonsContainer, jTextFieldsContainer, toolsMenuPanel);

        /* Node getter and remover panel
         * */
        JTextField nodeGetterTextField = new JTextField("enter index here", 18);
        JButton nodeGetterButton = new JButton("get");
        nodeGetterButton.addActionListener(e -> heapOutputArea.append(String.valueOf(heap.getElementByIndex(Integer.parseInt(nodeGetterTextField.getText())))));

        JButton nodeRemoverButton = new JButton("remove");
        nodeRemoverButton.addActionListener(e -> heapOutputArea.append(String.valueOf(heap.removeNode(Integer.parseInt(nodeGetterTextField.getText())))));
        Collections.addAll(jButtonsContainer, nodeGetterButton, nodeRemoverButton);
        jTextFieldsContainer.add(nodeGetterTextField);
        createJPanel("Getter & remover", 90, jButtonsContainer, jTextFieldsContainer, toolsMenuPanel);

        /* Additional options panel
         * TODO: export, import, heapOutputFieldCleaner
         * */
        JButton exportButton = new JButton("export");
        exportButton.addActionListener(e -> {
            // TODO: export
        });
        JButton importButton = new JButton("import");
        importButton.addActionListener(e -> {
            // TODO: import
        });
        JButton printHeap = new JButton("print");
        printHeap.addActionListener(e -> heapOutputArea.append(String.valueOf(heap.printHeap())));
        JButton clearButton = new JButton("clear");
        clearButton.addActionListener(e -> heapOutputArea.setText(""));
        Collections.addAll(jButtonsContainer, exportButton, importButton, clearButton, printHeap);
        ArrayList<JTextField> nullJFiller = new ArrayList<>();
        createJPanel("Additional options", 95, jButtonsContainer, nullJFiller, toolsMenuPanel);

        /* JFrame settings
         * */
        changeFont(mainFrame, heapOutputFont);
        changeFont(this, heapOutputFont);

        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainFrame);
        this.setVisible(true);
    }

    protected void changeFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                changeFont(child, font);
            }
        }
    }

    protected void createJPanel(String title, int height, ArrayList<JButton> targetButton, ArrayList<JTextField> targetTextField, JPanel targetBox) {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        jPanel.setPreferredSize(new Dimension(180, height));
        jPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                heapOutputFont,
                Color.BLACK
        ));

        if (targetTextField.size() != 0) {
            for (JTextField textField : targetTextField) {
                jPanel.add(textField);
            }
        }

        if (targetButton.size() != 0) {
            for (JButton button : targetButton) {
                jPanel.add(button);
            }
        }

        targetBox.add(jPanel);
        jButtonsContainer.clear();
        jTextFieldsContainer.clear();
    }
}