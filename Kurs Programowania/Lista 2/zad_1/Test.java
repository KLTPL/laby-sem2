class Test {
  public static void main(String args[]) {
    PascalTriangleRow rowGenerator;
    try {
      int n = Integer.parseInt(args[0]);

      if (n < 0) {
        System.out.println(args[0] + " - Nieprawidłowy number wiersza");
        return;
      }

      rowGenerator = new PascalTriangleRow(n);
    } catch (NumberFormatException ex) {
      System.out.println(args[0] + " - nieprawidłowa dana");
      return;
    }

    for (int i = 1; i < args.length; i++) {
      try {
        int k = Integer.parseInt(args[i]);
        int res = rowGenerator.getVal(k);
        if (res != -1) {
          System.out.println(k + " - " + res);
        } else {
          System.out.println(args[i] + " - nieprawidłowy zakres");
        }
      } catch (NumberFormatException ex) {
        System.out.println(args[i] + " - nieprawidłowa dana");
        return;
      }
    }
  }
}
