class divisor {
  public static void main(String args[]) {
    for (int i = 0; i < args.length; i++) {
      int n;
      try {
        n = Integer.parseInt(args[i]);

        System.out.println(n + " " + div(n));

      } catch (NumberFormatException ex) {
        System.out.println(args[i] + " nie jest liczbą całkowitą");
      }

    }
  }

  public static int div(int n) {
    n = Math.abs(n);
    if (n <= 1) {
      return 1;
    }

    for (int i = 2; i <= Math.sqrt(n); i++) {
      if (n % i == 0) {
        return n / i;
      }

    }
    return 1;
  }
}
