package com;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @Author luo
 * @Date 2020/4/7 10:49
 */
public class Main {
    public static void main(String[] args) {
        //牛客编程题1
//        String line = new Scanner(System.in).nextLine();
//        String[] strs = line.split(" ");
//        System.out.println(strs[strs.length-1].length());

        //牛客编程题2
        Scanner sc = new Scanner(System.in);
//        String str = sc.nextLine();
//        String ch = sc.nextLine();
//        int count=0;
//        for (int i = 0; i < str.length(); i++) {
//            if ((str.charAt(i)+"").equalsIgnoreCase(ch)){
//                count++;
//            }
//        }
//        System.out.println(count);

        //牛客编程题3 明明的随机数
//               Scanner sc = new Scanner(System.in);
//        Integer num1 = sc.nextInt();
//        TreeSet<Integer> set = new TreeSet<>();
//        for (Integer i = 0; i < num1; i++) {
//            set.add(sc.nextInt());
//        }
//
//        for (Integer aSet : set) {
//            System.out.println(aSet);
//        }

        //牛客编程题4 字符串分隔 输入任意长度字符串 不足8位在末尾补0 超过8位 按8位依次打印
        //大佬代码
//        while(sc.hasNext()){
//            String s = sc.nextLine();
//            if(s.length()%8 !=0 )
//                s = s + "00000000";
//
//            while(s.length()>=8){
//                System.out.println(s.substring(0, 8));
//                s = s.substring(8);
//            }
//        }
        //自己写的垃圾代码
//        while (sc.hasNext()){
//            String str1 = sc.nextLine();
//            int leng1 = str1.length();
//            StringBuilder sb = new StringBuilder();
//            if (leng1 <= 8) {
//                sb.append(str1);
//                for (int i = 0; i < 8 - leng1; i++) {
//                    sb.append("0");
//                }
//                System.out.println(sb.toString());
//            } else {
//                //8的整数倍
//                int count = leng1 / 8;
//                //余数的个数
//                int leftNum = leng1 % 8;
//                //将字符串照8位进行分隔打印
//                for (int i = 0; i < count; i++) {
//                    System.out.println(str1.substring(8 * i, (i + 1) * 8));
//                }
//                //对余数个数进行判断
//                if (leftNum > 0) {
//                    sb.append(str1.substring(count * 8, str1.length()));
//                    for (int i = 0; i < 8 - leftNum; i++) {
//                        sb.append("0");
//                    }
//                }
//                System.out.println(sb.toString());
//            }
//        }

        //牛客编程题5 给定有一个正整数  按照从小到大顺序输出所有得质因子
        //大佬代码
//        long num = sc.nextLong();
//
//        //初始质因子
//        int initNum=2;
//        //返回结果
//        String result="";
//        while (num!=1){
//            //能够整除
//            while (num%initNum==0){
//                //让num等于整除后值
//                num=num/initNum;
//                result = result+initNum+" ";
//            }
//            initNum++;
//        }
//
//        System.out.println(result);

        //牛客编程题6 接收一个正浮点数 要求四舍五入后输出整数
        float num = sc.nextFloat();
        System.out.println(Math.round(num));
    }
}