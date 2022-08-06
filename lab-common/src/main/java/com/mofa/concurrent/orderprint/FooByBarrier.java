package com.mofa.concurrent.orderprint;


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
public class FooByBarrier {
    /**
     * 此方法使用执行屏障实现
     * 构造 2 道屏障，second 线程等待 first 屏障，third 线程等待 second 屏障
     * 用线程等待的方式实现执行屏障，使用释放线程等待的方式实现屏障消除
     */
    //把true和false当做判断条件
    private boolean firstFinished;
    private boolean secondFinished;
    private Object lock = new Object();

    public FooByBarrier() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        synchronized (lock) {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            firstFinished = true;
            lock.notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {

        synchronized (lock) {
            while (!firstFinished) {
                lock.wait();
            }

            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            secondFinished = true;
            lock.notifyAll();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {

        synchronized (lock) {
            while (!secondFinished) {
                lock.wait();
            }

            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
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
