package com.tml.java8;


import org.apache.commons.compress.utils.Lists;
import org.junit.Assert;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @Description com.tml.java8
 * @Author TuMingLong
 * @Date 2019/10/16 11:58
 */
public class Java8ListTest {

    public static void main(String[] args) {
        // testSortByName();
        testSortByNameWithLambda();
    }

    /**
     * 使用Java8之前方法进行排序
     */
    public static void testSortByName() {
        List<Human> humans = Lists.newArrayList();
        humans.add(new Human(1L, "tomy", 25));
        humans.add(new Human(2L, "li", 26));
        Collections.sort(humans, new Comparator<Human>() {
            @Override
            public int compare(Human o1, Human o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        Assert.assertThat(humans.get(0), equalTo(new Human(2L, "li", 26)));
    }

    /**
     * 使用Lambda进行排序
     *
     * @throws Exception
     */
    public static void testSortByNameWithLambda() {
        List<Human> humans = Lists.newArrayList();
        humans.add(new Human(1L, "tomy", 25));
        humans.add(new Human(2L, "li", 26));
        humans.sort((Human h1, Human h2) -> h1.getName().compareTo(h2.getName()));

        Assert.assertThat("tomy", equalTo(humans.get(1).getName()));

    }

}
