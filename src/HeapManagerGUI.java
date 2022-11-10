import factory.UserFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class HeapManagerGUI extends JFrame {
    JPanel mainFrame = new JPanel();
    Font heapOutputFont = new Font("Consolas", Font.PLAIN, 14);
    JPanel toolsMenuPanel;
    Box toolboxBox;
    JComboBox<Object> userType;

    JTextArea heapOutputArea;

    JLabel indexInsertLbl, valueInsertLbl, indexRemoveLbl;

    HeapManagerGUI() {
        this.setTitle("Heap Manager");

        /* Main frame panel
         * */
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setBackground(Color.LIGHT_GRAY);

        /* Tools menu panel
         * */
        toolsMenuPanel = new JPanel();
        //toolsMenuPanel.setBackground(Color.GREEN);
        toolsMenuPanel.setPreferredSize(new Dimension(200, 0));
        mainFrame.add(toolsMenuPanel, BorderLayout.WEST);

        /* Toolbox Box
         * */
        toolboxBox = Box.createVerticalBox();
        toolsMenuPanel.add(toolboxBox);

        /* Heap output (view) panel
         * TODO: scroll, offset
         * */
        heapOutputArea = new JTextArea();
        heapOutputArea.setEditable(false);
        heapOutputArea.setFont(heapOutputFont);
        heapOutputArea.setBackground(Color.WHITE);
        heapOutputArea.setPreferredSize(new Dimension(580, 0));
        mainFrame.add(heapOutputArea, BorderLayout.EAST);

        /* Combo box
         * */
        userType = new JComboBox<>(UserFactory.getTypeNameList().toArray());
        userType.setPreferredSize(new Dimension(170,20));
        toolboxBox.add(userType);
        userType.addActionListener(e -> {
            // TODO: Heap initialization
            heapOutputArea.append(" > Now selected " + userType.getSelectedItem() + " type" + "\n");
        });
        toolboxBox.add(Box.createRigidArea(new Dimension(0,20))); // gap num 1

        /* TODO: Insertion panel
        * */

        /* Node getter and remover panel
        * */
        JTextField nodeGetterTextField = new JTextField("enter index here", 18);
        JButton nodeGetterButton = new JButton("get");
        nodeGetterButton.addActionListener(e -> {
            // TODO: get node by index
        });

        JButton nodeRemoverButton = new JButton("remove");
        nodeRemoverButton.addActionListener(e -> {
            // TODO: remove node by index
        });
        JButton[] jButtons = {nodeGetterButton, nodeRemoverButton};
        jPanelFactory("Getter & remover", jButtons, nodeGetterTextField, toolboxBox);

        /* Additional options panel
        * TODO: export, import, heapOutputFieldCleaner
        * */
        JPanel additionalOptionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,5));
        additionalOptionsPanel.setPreferredSize(new Dimension(0,120));
        additionalOptionsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Additional options",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                heapOutputFont,
                Color.BLACK
        ));
        toolboxBox.add(additionalOptionsPanel);

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

    // TODO: new class gui controller
    protected void jPanelFactory(String title, JButton[] targetButton, JTextField targetTextField, Box targetBox){
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,5));
        jPanel.setPreferredSize(new Dimension(0,90));
        jPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                heapOutputFont,
                Color.BLACK
        ));

        jPanel.add(targetTextField);

        for (JButton button : targetButton) {
            jPanel.add(button);
            if (targetButton.length > 1){
                jPanel.add(Box.createRigidArea(new Dimension(7,0)));
            }
        }

        targetBox.add(jPanel);
        targetBox.add(Box.createRigidArea(new Dimension(0,10)));
    }
}
