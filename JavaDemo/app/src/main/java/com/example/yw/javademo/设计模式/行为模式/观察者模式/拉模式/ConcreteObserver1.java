package com.example.yw.javademo.设计模式.行为模式.观察者模式.拉模式;

/**
 * Created on 2017/11/1216:22.
 * Author jackyang
 * -------------------------------
 *
 * @description
 * @email 3180518198@qq.com
 */

public class ConcreteObserver1 implements Observer1 {

    @Override
    public void update(Subject1 subject1) {
        String state = ((ConcreteSubject11)subject1).getState();
    }
}
