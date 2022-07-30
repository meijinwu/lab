package easy;

import java.util.Scanner;

public class TestListNode {

    //输出单向链表中倒数第k个结点不完全准确
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            int num = scanner.nextInt();
            ListNode header = new ListNode();
            for (int i = 0; i < num; i++) {
                int value = scanner.nextInt();
                ListNode node = new ListNode(value,header.next);
                header.next = node;
            }
            int target = scanner.nextInt();
            for (int i = 0; i < target; i++) {
                header = header.next;
            }
            System.out.println(header.value);
        }
    }

    static class ListNode{
        int value;
        ListNode next;
        public ListNode(){

        }
        public ListNode(int value,ListNode next){
            this.value = value;
            this.next = next;
        }
    }
}
