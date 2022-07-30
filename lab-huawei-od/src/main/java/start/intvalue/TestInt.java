package start.intvalue;

import java.util.Scanner;

public class TestInt {
    public static void main(String[] args) {
        //取近似值
        Scanner scanner = new Scanner(System.in);
        double nextDouble = scanner.nextDouble();
        System.out.println((int)(nextDouble + 0.5));
    }
}
