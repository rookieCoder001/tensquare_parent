package com;

/**
 * @Author luo
 * @Date 2020/4/30 7:08
 */
public class H implements Cloneable{

    private int age;
    private String name;
    private Addr addr;

    public Addr getAddr() {
        return addr;
    }

    public void setAddr(Addr addr) {
        this.addr = addr;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "H{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", addr=" + addr +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        H h = null;
        try{
            h = (H) super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return h;
    }



    public static void main(String[] args) throws CloneNotSupportedException {


        Addr addr = new Addr("洪山区");

        //原始H
        H rowH = new H();
        rowH.setAge(18);
        rowH.setAddr(addr);
        rowH.setName("张三");

        //拷贝后的H
        H cloneH = (H) rowH.clone();

        System.out.println(rowH);
        System.out.println(cloneH);
        System.out.println(rowH==cloneH);

        addr.setValue("武昌区");
//        cloneH.setAddr(new Addr("武昌区"));
        System.out.println(rowH);
        System.out.println(cloneH);
    }


}

class Addr{

    private String value;

    public Addr(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Addr{" +
                "value='" + value + '\'' +
                '}';
    }
}
