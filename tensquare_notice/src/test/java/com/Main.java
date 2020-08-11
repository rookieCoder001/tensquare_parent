package com;

import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * @Author luo
 * @Date 2020/4/20 12:40
 */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //获取第一个点序列A
        String line = sc.nextLine();
        String[] ANums = line.substring(3, line.length() - 1).split(",");

        //第二个点序列B
        String lineB = sc.nextLine();
        String[] BNums = line.substring(3, line.length() - 1).split(",");

        //获取距离R
        int R = Integer.parseInt(sc.nextLine().substring(2));

        //创建map存储数据
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();

        int alen=0;int blen=0;
        while (alen<ANums.length&&blen<BNums.length){

            int a = Integer.parseInt(ANums[alen]);
            int b = Integer.parseInt(BNums[alen]);

            //将符合条件的数添加到map中
            if (a<= b){
              if ((b-a)<=R) map.put(a,b);
              else {

              }
            }
        }
    }
}
