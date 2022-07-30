package start.sort;

import java.util.Arrays;
import java.util.Scanner;

public class TestSort {
    //输入整型数组和排序标识，对其元素按照升序或降序进行排序
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        int order = scanner.nextInt();
       Arrays.sort(arr);
       if(order == 0){
           for (int i = 0; i < arr.length; i++) {
               System.out.println(arr[i] + "");
           }
       }else{
           for (int i = arr.length - 1; i >= 0; i--) {
               System.out.println(arr[i] + "");
           }
       }

    }
}
