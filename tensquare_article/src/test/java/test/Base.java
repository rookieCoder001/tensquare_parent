package test;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author luo
 * @Date 2020/4/18 9:46
 */
public class Base {

}

class child extends Base{
    public static void main(String[] args) {

        Base base = new Base();
        child child = new child();
        System.out.println(child.getClass()==base.getClass());
        System.out.println(child instanceof Base);

        String a =null;
        System.out.println(0.0+a+false);

        for (int i = 0; i < 6; i++) {
            new Thread(){
                private  volatile AtomicInteger integer = new AtomicInteger(0);
                @Override
                public void run() {
                    for (int i = 0; i < 5000; i++) {
                        integer.incrementAndGet();
                        System.out.println(Thread.currentThread().getName()+" "+integer.get());
                    }
                }
            }.start();
        }
    }
}

