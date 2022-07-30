package easy;

import java.util.Scanner;

public class TestPasswordStrong {
    //密码强度等级
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] chars = scanner.nextLine().toCharArray();
        //长度判断
        int c1 = chars.length;
        //分数
        int score = 0;
        //长度
        if(c1<=4 ){
            score += 5;
        }else if (c1 >= 8){
            score += 25;
        }else{
            score += 10;
        }
        //字母统计
        int upCount = 0;
        int lowCount = 0;
        int numCount = 0;
        int signCount = 0;
        for (int i = 0; i < chars.length; i++) {
            if(Character.isUpperCase(chars[i])){
                ++upCount;
            }else if(Character.isLowerCase(chars[i])){
                ++lowCount;
            }else if(Character.isDigit(chars[i])){
                ++numCount;
            }else{
                ++signCount;
            }
        }
        //字母分数
        if((upCount> 0 && lowCount == 0 || (upCount ==  0 && lowCount > 0))){
            score+=10;
        }else if(upCount >0 && lowCount>0){
            score+=20;
        }else{
            score+=0;
        }
        //数字分数
        if(numCount>1){
            score+=20;
        }else if(numCount == 1){
            score+=10;
        }else {
            score+=0;
        }
        //符号分数
        if(signCount>1){
            score+=20;
        }else if(signCount == 1){
            score+=10;
        }else {
            score+=0;
        }
        //奖励分
        if(upCount >0 && lowCount >0 && numCount>0 && signCount > 0){
            score += 5;
        }else if(numCount >0 && signCount >0 && (upCount >0 || lowCount >0)){
            score += 3;
        }else if(numCount >0 && (upCount>0 || lowCount >0)){
            score +=2;
        }
        if(score>= 90){
            System.out.println("VERY_SECURE");
        }else if(score>= 80){
            System.out.println("SECURE");
        }else if(score>= 70){
            System.out.println("VERY_STRONG");
        }else if(score>= 60){
            System.out.println("STRONG");
        }else if(score>= 50){
            System.out.println("AVERAGE");
        }else if(score>= 25){
            System.out.println("WEAK");
        }else if(score>= 0){
            System.out.println("VERY_WEAK");
        }
    }
}
