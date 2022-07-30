package start.substring;

import java.util.Scanner;

public class TestSubString {
    //截取字符串
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s1 = scanner.nextLine();
        System.out.println("第一次输入" + s1);
        int s2 = scanner.nextInt();
        System.out.println("第二次输入" + s2);
        String result;
        System.out.println(s1.substring(0,s2));
    }
}
