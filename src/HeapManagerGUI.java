import javax.swing.*;

public class HeapManagerGUI extends JFrame {
    private JPanel mainPanel;

    public HeapManagerGUI(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
    }
}
