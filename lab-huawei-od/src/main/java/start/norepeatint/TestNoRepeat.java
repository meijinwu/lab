package start.norepeatint;

import java.util.*;

public class TestNoRepeat {
    //提取不重复的整数
    public static void main(String[] args) {
        //test1
//        Scanner scanner = new Scanner(System.in);
//        StringBuffer s = new StringBuffer(scanner.nextLine()).reverse();
//        String result = Arrays.asList(s.toString().trim().split("")).stream().distinct().collect(Collectors.joining());
//        System.out.println(result);
        //test2
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        char[] chars = s.toCharArray();
        //有序
        LinkedHashSet ls = new LinkedHashSet();
        for (int i = chars.length-1; i >=0;i--) {
            ls.add(chars[i]);
        }
        s = "";
        Iterator it = ls.iterator();
        while(it.hasNext()){
            s +=  it.next();
        }
        System.out.println(s);
    }
}
