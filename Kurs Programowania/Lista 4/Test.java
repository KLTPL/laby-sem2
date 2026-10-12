import javax.swing.*;
import java.awt.*;

public class Test {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak argumentu");
            return;
        }
        int n;
        try {
            n = Integer.parseInt(args[0]);
            if (n < 0) {
                System.out.println(args[0] + " - Nieprawidłowy zakres");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(args[0] + " - nieprawidłowa dana");
            return;
        }
        JFrame frame = new JFrame("Trójkąt Pascala");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        for (int i = 0; i <= n; i++) {
            PascalTriangle pascal = new PascalTriangle(i);
            JLabel rowLabel = new JLabel(pascal.toString());
            rowLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
            rowLabel.setForeground(Color.WHITE);
            rowLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(rowLabel);
        }
        frame.add(panel);
        frame.pack(); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}