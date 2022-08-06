package com.mofa.concurrent.orderprint;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 三个不同的线程 A、B、C 将会共用一个 Foo 实例。
 *
 * 线程 A 将会调用 first() 方法
 * 线程 B 将会调用 second() 方法
 * 线程 C 将会调用 third() 方法
 * 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。
 *
 * 提示：
 *
 * 尽管输入中的数字似乎暗示了顺序，但是我们并不保证线程在操作系统中的调度顺序。
 * 你看到的输入格式主要是为了确保测试的全面性。
 *
 * 示例一
 * 输入：nums = [1,2,3]
 * 输出："firstsecondthird"
 * 解释：
 * 有三个线程会被异步启动。输入 [1,2,3] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 second() 方法，线程 C 将会调用 third() 方法。正确的输出是 "firstsecondthird"。
 *
 * 示例二
 * 输入：nums = [1,3,2]
 * 输出："firstsecondthird"
 * 解释：
 * 输入 [1,3,2] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 third() 方法，线程 C 将会调用 second() 方法。正确的输出是 "firstsecondthird"。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/print-in-order
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Foo {
    //简单理解就是把0和1当做判断条件
    private AtomicInteger firstJobDone = new AtomicInteger();
    private AtomicInteger secondJobDone = new AtomicInteger();


    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first".
        printFirst.run();
        // mark the first job as done, by increasing its count.
        firstJobDone.incrementAndGet();
    }
    public void second(Runnable printSecond) throws InterruptedException {
        while (firstJobDone.get() != 1) {
            // waiting for the first job to be done.
        }
        // printSecond.run() outputs "second".
        printSecond.run();
        // mark the second as done, by increasing its count.
        secondJobDone.incrementAndGet();
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (secondJobDone.get() != 1) {
            // waiting for the second job to be done.
        }
        // printThird.run() outputs "third".
        printThird.run();
    }

    public static void main(String[] args) throws InterruptedException{
        Foo foo = new Foo();
        int[] order = {1, 3, 2};
        Runnable first = () -> System.out.printf("first");
        Runnable second = () -> System.out.printf("second");
        Runnable third = () -> System.out.printf("third");
        for (int i = 0; i <=2 ; i++) {
            int num = order[i];
            if (num == 1){
                new Thread(() -> {
                    try {
                        foo.first(first);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else if (num == 2){
                new Thread(() -> {
                    try {
                        foo.second(second);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else if (num == 3){
                new Thread(() -> {
                    try {
                        foo.third(third);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}
