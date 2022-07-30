package start.minofk;

import java.util.Arrays;
import java.util.Scanner;

public class TestMinOfK {
    public static void main(String[] args) {
        //输入n个整数，输出其中最小的k个
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            int n = scanner.nextInt();
            int k = scanner.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scanner.nextInt();
            }
            Arrays.sort(arr);
            for (int i = 0; i < k; i++) {
                System.out.println(arr[i] + "");
            }
            System.out.println();
        }
    }
}
