package com.mofa.concurrent.zeroevenodd;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * 给你类 ZeroEvenOdd 的一个实例，该类中有三个函数：zero、even 和 odd 。ZeroEvenOdd 的相同实例将会传递给三个不同线程：
 *
 * 线程 A：调用 zero() ，只输出 0
 * 线程 B：调用 even() ，只输出偶数
 * 线程 C：调用 odd() ，只输出奇数
 * 修改给出的类，以输出序列 "010203040506..." ，其中序列的长度必须为 2n 。
 *
 * 实现 ZeroEvenOdd 类：
 *
 * ZeroEvenOdd(int n) 用数字 n 初始化对象，表示需要输出的数。
 * void zero(printNumber) 调用 printNumber 以输出一个 0 。
 * void even(printNumber) 调用printNumber 以输出偶数。
 * void odd(printNumber) 调用 printNumber 以输出奇数。
 *
 */
public class ZeroEvenOddByReentrantLock {

    /**
     * ReentrantLock主要利用CAS+AQS队列来实现。它支持公平锁和非公平锁，两者的实现类似。
     *
     * CAS：Compare and Swap，比较并交换。CAS有3个操作数：内存值V、预期值A、要修改的新值B。当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。该操作是一个原子操作，被广泛的应用在Java的底层实现中。在Java中，CAS主要是由sun.misc.Unsafe这个类通过JNI调用CPU底层指令实现
     *
     * ReentrantLock主要利用CAS+AQS队列来实现。它支持公平锁和非公平锁，两者的实现类似。
     *
     * CAS：Compare and Swap，比较并交换。CAS有3个操作数：内存值V、预期值A、要修改的新值B。当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。该操作是一个原子操作，被广泛的应用在Java的底层实现中。在Java中，CAS主要是由sun.misc.Unsafe这个类通过JNI调用CPU底层指令实现
     *
     *
     */
    private volatile int start = 1;
    private volatile int state;
    private int n;

    Lock lock = new ReentrantLock();
    Condition zero = lock.newCondition();
    Condition even = lock.newCondition();
    Condition odd = lock.newCondition();

    public ZeroEvenOddByReentrantLock(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (start <= n) {
                if (state != 0) {
                    zero.await();
                }
                printNumber.accept(0);
                if (start % 2 == 0) {
                    state = 2;
                    even.signal();
                } else {
                    state = 1;
                    odd.signal();
                }
                zero.await();
            }
            odd.signal();
            even.signal();
        } finally {
            lock.unlock();
        }
    }

    //偶数
    public void even(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (start <= n) {
                if (state != 2) {
                    even.await();
                } else {
                    printNumber.accept(start++);
                    state = 0;
                    zero.signal();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    //奇数
    public void odd(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (start <= n) {
                if (state != 1) {
                    odd.await();
                } else {
                    printNumber.accept(start++);
                    state = 0;
                    zero.signal();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOddByReentrantLock zeo = new ZeroEvenOddByReentrantLock(20);
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
