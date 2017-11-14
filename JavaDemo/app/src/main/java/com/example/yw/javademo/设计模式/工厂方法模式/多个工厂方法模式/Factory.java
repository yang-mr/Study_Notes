package com.example.yw.javademo.设计模式.工厂方法模式.多个工厂方法模式;

import com.example.yw.javademo.设计模式.工厂方法模式.普通工厂方法模式.Banana;
import com.example.yw.javademo.设计模式.工厂方法模式.普通工厂方法模式.Fruit;
import com.example.yw.javademo.设计模式.工厂方法模式.普通工厂方法模式.Orange;

/**
 * Created on 2017/11/923:02.
 * Author jackyang
 * -------------------------------
 *
 * @description
 * @email 3180518198@qq.com
 */

public class Factory {
    public Fruit produceBanana() {
        return new Banana();
    }

    public Orange produceOrange() {
        return new Orange();
    }
}
