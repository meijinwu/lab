package middle.testip;

import java.util.*;

public class Main {

    private final int N = 4;
    public Main() {
    }

    public String convert(String str) {
        // ipv4 -> int
        if (str.contains(".")) {
            String[] fields = str.split("\\.");
            long result = 0;
            for (int i = 0; i < N; i++) {
                result = result * 256 + Integer.parseInt(fields[i]);
            }
            return "" + result;
        }
        // int -> ipv4
        else {
            long ipv4 = Long.parseLong(str);
            String result = "";
            for (int i = 0; i < N; i++) {
                result = ipv4 % 256 + "." + result;
                ipv4 /= 256;
            }
            return result.substring(0, result.length() - 1);
        }
    }

    public static void main(String[] args) {
        Main solution = new Main();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String str = in.next();
            String res = solution.convert(str);
            System.out.println(res);
        }
    }
}