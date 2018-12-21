package com.sigma.sigmacore.utils;

import lombok.Getter;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/8-12:46
 * desc:
 **/
public class EnumUtilTest {

    @Test
    public void indexOf() {
        Assert.assertEquals(Color.Blue, EnumUtil.indexOf(Color.class, 2));
    }

    @Test
    public void nameOf() {
        Assert.assertEquals(Color.Green, EnumUtil.nameOf(Color.class, "Green"));
    }

    @Test
    public void codeOf() {
        Assert.assertEquals(Color.Green, EnumUtil.codeOf(Color.class, 95));
        Assert.assertEquals(Color.Red, EnumUtil.codeOf(Color.class, 110));
        Assert.assertEquals(Fruit.Cat, EnumUtil.codeOf(Fruit.class, "C"));
    }

    public enum Fruit {
        Apple("A"),
        Banana("B"),
        Cat("C");

        @Getter
        private String code;

        Fruit(String code) {
            this.code = code;
        }
    }

    public enum Color {
        Red(110),
        Green(95),
        Blue(23);

        @Getter
        private int code;

        Color(int code) {
            this.code = code;
        }
    }
}