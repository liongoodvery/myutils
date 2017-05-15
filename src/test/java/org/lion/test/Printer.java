package org.lion.test;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by more on 2016-05-08 21:35:18.
 */
public class Printer {
    List<Integer> list;
    Lock lock;
    Condition c1;
    Condition c2;
    Condition c3;
    Condition c4;
    private int flag;

    public Printer() {
        this(null);
    }

    public Printer(List<Integer> list) {
        lock = new ReentrantLock();
        c1 = lock.newCondition();
        c2 = lock.newCondition();
        c3 = lock.newCondition();
        c4 = lock.newCondition();
        this.list = list;
        flag = 1;
    }

    public void print1() throws InterruptedException {

        lock.lock();
        if (flag != 1)
            c1.await();
        list.add(1);
        System.out.println(1);
        flag = 2;
        c2.signal();

        lock.unlock();
    }

    public void print2() throws InterruptedException {
        lock.lock();
        if (flag != 2) {
            c2.await();
        }
        list.add(2);
        System.out.println(2);
        flag = 3;
        c3.signal();
        lock.unlock();
    }


    public void print3() throws InterruptedException {
        lock.lock();
        if (flag != 3)
            c3.await();
        list.add(3);
        System.out.println(3);
        flag = 4;
        c4.signal();
        lock.unlock();
    }

    public void print4() throws InterruptedException {
        lock.lock();
        if (flag != 4)
            c4.await();
        list.add(4);
        System.out.println(4);
        flag = 1;
        c1.signal();
        lock.unlock();
    }


}
