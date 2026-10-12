package cpp;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Pascal3 {
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Brak argumentu");
      return;
    }
    int n = 0;
    try {
      n = Integer.parseInt(args[0]);
      if (n < 0) {
        System.out.println(args[0] + " - nieprawidlowy zakres");
        return;
      }
    } catch (NumberFormatException e) {
      System.out.println(args[0] + " - nieprawidlowa dana");
      return;
    }

    String wynik = "";
    try {
      ProcessBuilder pb = new ProcessBuilder("./cpp/program", String.valueOf(n));
      Process proces = pb.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(proces.getInputStream()));
      wynik = reader.readLine();

      if (wynik == null || wynik.isEmpty()) {
        wynik = "Błąd";
      }
      proces.waitFor();
    } catch (Exception e) {
      wynik = "Błąd" + e.getMessage();
    }
    JFrame frame = new JFrame("Trójkąt Pascala");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JLabel label = new JLabel(wynik, SwingConstants.CENTER);
    label.setFont(new Font("Monospaced", Font.BOLD, 24));
    label.setForeground(Color.WHITE);
    label.setBackground(Color.BLACK);
    label.setOpaque(true);
    label.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

    frame.add(label);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
