package com.mofa.concurrent.zeroevenodd;

import java.util.concurrent.CountDownLatch;
import java.util.function.IntConsumer;

public class ZeroEvenOddByCountDownLatch {

    /**
     * CountDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
     *
     * 是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，计数器的值就-1，当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了
     *
     *
     * //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
     * public void await() throws InterruptedException { };
     * 和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
     * public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };
     * 将count值减1
     * public void countDown() { };
     *
     */
    private int n;
    private CountDownLatch zeroLatch = new CountDownLatch(0);
    private CountDownLatch evenLatch = new CountDownLatch(1);//偶数
    private CountDownLatch oddLatch = new CountDownLatch(1);//奇数

    public ZeroEvenOddByCountDownLatch(int n){
        this.n = n;
    }
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zeroLatch.await();
            printNumber.accept(0);//打印0
            zeroLatch = new CountDownLatch(1);
            if ((i & 1) == 1) oddLatch.countDown();//如果是奇数，就打印奇数
            else evenLatch.countDown();

        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if ((i & 1) == 0) {
                evenLatch.await();//开始打印偶数
                printNumber.accept(i);
                evenLatch = new CountDownLatch(1);
                zeroLatch.countDown();//是否zero线程
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if ((i & 1) == 1) {
                oddLatch.await();//开始打印奇数
                printNumber.accept(i);
                oddLatch = new CountDownLatch(1);
                zeroLatch.countDown();//是否zero线程
            }
        }
    }

    public static void main(String[] args) {
        ZeroEvenOddByCountDownLatch zeo = new ZeroEvenOddByCountDownLatch(20);
        Runnable printEven = () -> {
            try {
                zeo.even((value)->{
                    System.out.println(value);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
            }
        };
        Runnable printOdd = () -> {
            try {
                zeo.odd((value)->{
                    System.out.println(value);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
            }
        };
        Runnable printZero = ()->{
            try {
                zeo.zero((value)->{
                    System.out.println(value);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }
        };
        new Thread(printEven).start();
        new Thread(printOdd).start();
        new Thread(printZero).start();
        System.out.println("done.....");
    }
}
