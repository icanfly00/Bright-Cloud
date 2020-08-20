package com.tml.java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description com.tml.entity
 * @Author TuMingLong
 * @Date 2019/10/16 11:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Human {
    private Long id;
    private String name;
    private int age;

    public static void main(String[] args) {
      Human human=new Human();
      Human human2=new Human();
      Human human3=new Human();

        Class<? extends Human> class1=human.getClass();
        ClassLoader classLoader=class1.getClassLoader();
        System.out.println(classLoader.getParent());
    }
}
