class Test {
  public static void main(String args[]) {
    PrimeNumbers primesGenerator;
    try {
      int n;
      n = Integer.parseInt(args[0]);

      if (n < 2) {
        System.out.println(args[0] + " - nieprawidłowy zakres");
        return;
      }

      primesGenerator = new PrimeNumbers(n);
    } catch (NumberFormatException ex) {
      System.out.println(args[0] + " - nieprawidłowa dana");
      return;
    }

    for (int i = 1; i < args.length; i++) {
      int res;
      int m;
      try {
        m = Integer.parseInt(args[i]);
        res = primesGenerator.getNumber(m);

        if (res >= 2) {
          System.out.println(m + " - " + res);
        } else {
          System.out.println(m + " - liczba spoza zakresu");
        }
      } catch (NumberFormatException ex) {
        System.out.println(args[i] + " - nieprawidłowa dana");
      }
    }
  }
}
