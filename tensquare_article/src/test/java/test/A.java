package test;

import com.F;

/**
 * @Author luo
 * @Date 2020/4/18 9:32
 */
public class A extends F{

    public static void main(String[] args) {

        System.out.println();

        int a=1;
        int b=2;
        int c=3;

        a = b = c;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}
