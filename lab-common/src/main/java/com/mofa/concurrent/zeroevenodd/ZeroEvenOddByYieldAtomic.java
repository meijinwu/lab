package com.mofa.concurrent.zeroevenodd;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

public class ZeroEvenOddByYieldAtomic {


    /**
     * Thread.yield():使当前线程从执行状态（运行状态）变为可执行态（就绪状态）。
     * cpu会从众多的可执行态里选择，也就是说，当前也就是刚刚的那个线程还是有可能会被再次执行到的，并不是说一定会执行其他线程而该线程在下一次中不会执行到了。
     *
     */
    private int n;
    private AtomicInteger ai = new AtomicInteger(0);

    public ZeroEvenOddByYieldAtomic(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (ai.get() != 0 && ai.get() != 2) { //改成||走不通  既不是0也不是2时等待，简单理解就是0||2的时候执行，执行结束变成1||3
                Thread.yield();//就绪状态
            }
            printNumber.accept(0);
            ai.incrementAndGet();//ai+1 = 3或者1
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            while (ai.get() != 3) {//ai=3执行,执行结束ai=0
                Thread.yield();
            }
            printNumber.accept(i);
            ai.set(0);
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i+=2) {
            while (ai.get() != 1) { //ai=1执行,执行结束ai=2
                Thread.yield();
            }
            printNumber.accept(i);
            ai.set(2);
        }
    }


    public static void main(String[] args) {
        ZeroEvenOddByYieldAtomic zeo = new ZeroEvenOddByYieldAtomic(20);
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
