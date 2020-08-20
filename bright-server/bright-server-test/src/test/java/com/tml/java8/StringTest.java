package com.tml.java8;

/**
 * @Description ==与equals(重要)
 * @Author TuMingLong
 * @Date 2020/3/16 17:55
 */
public class StringTest {
    /**
     * == 它的作用是判断两个对象的地址是否相等。即判断两个对象是不是同一个对象（基本数据类型==比较的值，引用数据类型==比较的是内存地址）。
     * equals() 它的作用是也是判断两个对象是否相等。但它一般有两种使用情况：
     * 情况1：类没有覆盖equals()方法。则通过equals()比较该类的两个对象时，等价于通过“==”比较者两个对象。
     * 情况2：类覆盖了equals()。一般，我们都覆盖equals()方法来比较两个对象的内容是否相等；若它们的内容相等，则返回true(即认为这两个对象相等)。
     */
    /**
     * String中的equals方法是被重写过的，因为object的equals方法是比较的对象的内存地址，
     * 而String的equals方法比较的是对象的值
     * 当创建String类型的对象时，虚拟机会在常量池中查找有没有已经存在的值和要创建的值相同的对象，
     * 如果有就把它赋给当前引用。如果没有就在常量池中重新创建一个String对象。
     */
    public static void main(String[] args) {
        String a=new String("ab"); //a 为一个引用
        String b=new String("ab"); //b为另一个引用，对象的内容一样
        String aa="ab"; //放在常量池中
        String bb="ab"; //从常量池中查找
        if(a==aa){
            System.out.println("a==aa");
        }
        if(aa==bb){ //true
            System.out.println("aa==bb");
        }
        if(a==b){ //false,非同一对象
            System.out.println("a==b");
        }
        if(a.equals(b)){//true
            System.out.println("aEQb");
        }
        if(42==42.0){//true
            System.out.println("true");
        }
    }
}
