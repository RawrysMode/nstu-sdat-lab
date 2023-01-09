package modules;

import factory.UserFactory;
import factory.UserType;
import utils.Serialization;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class GUI extends JFrame {
    private final Font heapOutputFont = new Font("Consolas", Font.PLAIN, 14);
    private final JComboBox<Object> userTypeComboBox;
    private final ArrayList<JButton> jButtonsContainer = new ArrayList<>();
    private final ArrayList<JTextField> jTextFieldsContainer = new ArrayList<>();
    private final JTextArea heapOutputArea;


    public GUI(IHeap heap) {
        this.setTitle(heap.getLanguageType() + " Heap Manager");

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
        userTypeComboBox = new JComboBox<>(UserFactory.getTypeNameList().toArray());
        userTypeComboBox.setPreferredSize(new Dimension(170, 20));
        toolsMenuPanel.add(userTypeComboBox);
        userTypeComboBox.addActionListener(e -> {
            heap.setCurrentSizeToZero();
            heapOutputArea.setText("");
            heapOutputArea.append(" > now selected " + userTypeComboBox.getSelectedItem() + " type" + "\n");
        });

        /* Insertion panel
         * */
        JTextField nodeInsertionTextField = new JTextField("enter data here", 18);
        JButton nodeInsertionButton = new JButton("insert");
        nodeInsertionButton.addActionListener(e -> {
            UserType object = UserFactory.getBuilderByName((String) userTypeComboBox.getSelectedItem());
            object.parseValue(nodeInsertionTextField.getText());
            heapOutputArea.append(String.valueOf(heap.insertNode(object)));
        });
        jButtonsContainer.add(nodeInsertionButton);
        jTextFieldsContainer.add(nodeInsertionTextField);
        createJPanel("Insert", 90, jButtonsContainer, jTextFieldsContainer, toolsMenuPanel);

        /* Insertion at index panel
         * */
        JTextField indexTextField = new JTextField("enter index here", 18);
        JTextField dataTextField = new JTextField("enter data here", 18);
        JButton nodeByIndexInsertionButton = new JButton("insert");
        nodeByIndexInsertionButton.addActionListener(e -> {
            UserType object = UserFactory.getBuilderByName((String) userTypeComboBox.getSelectedItem());
            object.parseValue(dataTextField.getText());
            heapOutputArea.
                    append(String.valueOf(
                            heap.insertNode(Integer.parseInt(indexTextField.getText()), object))
                    );
        });
        jButtonsContainer.add(nodeByIndexInsertionButton);
        Collections.addAll(jTextFieldsContainer, indexTextField, dataTextField);
        createJPanel("Insert by index", 115, jButtonsContainer, jTextFieldsContainer, toolsMenuPanel);

        /* Node getter and remover panel
         * */
        JTextField nodeGetterTextField = new JTextField("enter index here", 18);
        JButton nodeGetterButton = new JButton("get");
        nodeGetterButton.addActionListener(e -> heapOutputArea.append(String.valueOf(
                heap.getElementByIndex(Integer.parseInt(nodeGetterTextField.getText())))));

        JButton nodeRemoverButton = new JButton("remove");
        nodeRemoverButton.addActionListener(e -> heapOutputArea.
                append(String.valueOf(heap.removeNode(Integer.parseInt(nodeGetterTextField.getText())))));
        Collections.addAll(jButtonsContainer, nodeGetterButton, nodeRemoverButton);
        jTextFieldsContainer.add(nodeGetterTextField);
        createJPanel("Getter & remover", 90, jButtonsContainer, jTextFieldsContainer, toolsMenuPanel);

        /* Additional options panel
         * */
        JButton exportButton = new JButton("export");
        exportButton.addActionListener(e -> heapOutputArea.append(Serialization.loadToFile(heap)));
        JButton importButton = new JButton("import");
        importButton.addActionListener(e -> {
            if (userTypeComboBox.getSelectedItem() == "integer"){
                Serialization.readFromFile("saved_integer.txt", heap);
                heapOutputArea.append(Serialization.returnedValue);
            } else {
                Serialization.readFromFile("saved_date.txt", heap);
                heapOutputArea.append(Serialization.returnedValue);
            }
        });
        JButton printHeap = new JButton("print");
        printHeap.addActionListener(e -> heapOutputArea.append(String.valueOf(heap.printHeap())));
        JButton clearButton = new JButton("clear log");
        clearButton.addActionListener(e -> heapOutputArea.setText(""));
        JButton defaultSortButton = new JButton("default sort");
        defaultSortButton.addActionListener(e -> {
            heapOutputArea.append(String.valueOf(heap.pyramidSort()));
            heapOutputArea.append(String.valueOf(heap.printArray()));
        });
        JButton maxHeapSortButton = new JButton("max-heap sort");
        maxHeapSortButton.addActionListener(e -> heapOutputArea.append(String.valueOf(heap.sortToMaxHeap())));
        JButton clearHeapButton = new JButton("clear heap");
        clearHeapButton.addActionListener(e -> {
            heap.setCurrentSizeToZero();
            heapOutputArea.append(" > heap empty now\n");
        });
        Collections.addAll(jButtonsContainer, exportButton, importButton, printHeap, clearButton, defaultSortButton, maxHeapSortButton, clearHeapButton);
        ArrayList<JTextField> nullJFiller = new ArrayList<>();
        createJPanel("Additional options", 225, jButtonsContainer, nullJFiller, toolsMenuPanel);

        /* JFrame settings
         * */
        changeFont(mainFrame, heapOutputFont);
        changeFont(this, heapOutputFont);

        this.setSize(800, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainFrame);
        this.setVisible(true);
    }

    private void changeFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                changeFont(child, font);
            }
        }
    }

    private void createJPanel(String title, int height, ArrayList<JButton> targetButton, ArrayList<JTextField> targetTextField, JPanel targetBox) {
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