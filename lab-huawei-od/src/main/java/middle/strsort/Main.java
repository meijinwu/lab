package middle.strsort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * 编写一个程序，将输入字符串中的字符按如下规则排序。
 *
 * 规则 1 ：英文字母从 A 到 Z 排列，不区分大小写。
 *
 * 如，输入： Type 输出： epTy
 *
 * 规则 2 ：同一个英文字母的大小写同时存在时，按照输入顺序排列。
 *
 * 如，输入： BabA 输出： aABb
 *
 * 规则 3 ：非英文字母的其它字符保持原来的位置。
 *
 *
 * 如，输入： By?e 输出： Be?y
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String result = sort(str);
        System.out.println(result);
    }

    private static String sort(String str) {
        //收集字母
        List<Character> letters = new ArrayList<Character>();
        for (char c : str.toCharArray()) {
            if(Character.isLetter(c)){
                letters.add(c);
            }
        }
        //排序
        letters.sort(new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return Character.toLowerCase(o1) - Character.toLowerCase(o2);
            }
        });
        //非字母直接添加
        StringBuilder sb = new StringBuilder();
        for (int i = 0,j = 0; i < str.toCharArray().length; i++) {
            if(Character.isLetter(str.charAt(i))){
                sb.append(letters.get(j++));
            }else{
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }
}
