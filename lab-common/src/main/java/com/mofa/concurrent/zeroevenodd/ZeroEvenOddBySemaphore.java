package com.mofa.concurrent.zeroevenodd;

import java.util.concurrent.Semaphore;
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
public class ZeroEvenOddBySemaphore {
    /**
     * Semaphore是一个计数信号量。
     * 从概念上将，Semaphore包含一组许可证。
     * 如果有需要的话，每个acquire()方法都会阻塞，直到获取一个可用的许可证。
     * 每个release()方法都会释放持有许可证的线程，并且归还Semaphore一个可用的许可证。
     * 然而，实际上并没有真实的许可证对象供线程使用，Semaphore只是对可用的数量进行管理维护
     * 总结：如果线程要访问一个资源就必须先获得信号量。如果信号量内部计数器大于0，信号量减1，然后允许共享这个资源；否则，如果信号量的计数器等于0，信号量将会把线程置入休眠直至计数器大于0.当信号量使用完时，必须释放
     */
    private Semaphore zeroSema = new Semaphore(1);
    private Semaphore oddSema = new Semaphore(0);//奇数
    private Semaphore evenSema = new Semaphore(0);//偶数
    private  int n;

    public ZeroEvenOddBySemaphore(int n) {
        this.n = n;
    }

    public void printZero(IntConsumer intConsumer)throws InterruptedException{
        for (int i = 1; i <= n; i++) {
            zeroSema.acquire();
            intConsumer.accept(0);
            if((i & 1) == 1){//奇数
                oddSema.release();
            }else{
                evenSema.release();
            }
        }
    }
    public void printEven(IntConsumer intConsumer)throws InterruptedException{
        for (int i = 1; i <= n; i++) {
            if ((i & 1) == 0) {//偶数 打印偶数 并释放zero的线程
                evenSema.acquire();
                intConsumer.accept(i);
                zeroSema.release();
            }
        }
    }
    public void printOdd(IntConsumer intConsumer)throws InterruptedException{
        for (int i=1; i<=n; i++) {
            if ((i & 1) == 1) {//奇数，打印奇数，并释放zero的线程
                oddSema.acquire();
                intConsumer.accept(i);
                zeroSema.release();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException{
        ZeroEvenOddBySemaphore zeo = new ZeroEvenOddBySemaphore(20);
        Runnable printEven = () -> {
            try {
                zeo.printEven((value)->{
                    System.out.println(value);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
            }
        };
        Runnable printOdd = () -> {
            try {
                zeo.printOdd((value)->{
                    System.out.println(value);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
            }
        };
        Runnable printZero = ()->{
            try {
                zeo.printZero((value)->{
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
