package com.example.yw.javademo.设计模式.装饰模式.透明;

/**
 * Created on 2017/11/1122:38.
 * Author jackyang
 * -------------------------------
 *
 * @description
 * @email 3180518198@qq.com
 */

public class OrangDecorate extends Decorate {
    public OrangDecorate(Fruit fruit) {
        super(fruit);
    }

    @Override
    public void getName() {
        System.out.println("OrangDecorate name");
    }
}
