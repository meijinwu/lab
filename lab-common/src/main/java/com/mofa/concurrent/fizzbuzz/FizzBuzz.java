package com.mofa.concurrent.fizzbuzz;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * 编写一个可以从 1 到 n 输出代表这个数字的字符串的程序，但是：
 *
 * 如果这个数字可以被 3 整除，输出 "fizz"。
 * 如果这个数字可以被 5 整除，输出 "buzz"。
 * 如果这个数字可以同时被 3 和 5 整除，输出 "fizzbuzz"。
 * 例如，当 n = 15，输出： 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz。
 *
 * 请你实现一个有四个线程的多线程版  FizzBuzz， 同一个 FizzBuzz 实例会被如下四个线程使用：
 *
 * 线程A将调用 fizz() 来判断是否能被 3 整除，如果可以，则输出 fizz。
 * 线程B将调用 buzz() 来判断是否能被 5 整除，如果可以，则输出 buzz。
 * 线程C将调用 fizzbuzz() 来判断是否同时能被 3 和 5 整除，如果可以，则输出 fizzbuzz。
 * 线程D将调用 number() 来实现输出既不能被 3 整除也不能被 5 整除的数字。
 *
 */
public class FizzBuzz {

    Lock lock = new ReentrantLock();
    Condition fizz = lock.newCondition();
    Condition buzz = lock.newCondition();
    Condition fizzbuzz = lock.newCondition();
    Condition num = lock.newCondition();
    private int n;
    private volatile int index;

    public FizzBuzz(int n) {
        this.n = n;
        index = 1;
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
        lock.lock();
        while (index <= n){
            if(index % 3 == 0 && index % 5 !=0){
                printFizz.run();
                index++;
                buzz.signalAll();
                fizzbuzz.signalAll();
                num.signalAll();
            }else{
                //不满足条件 进入条件队列等待被唤醒
                fizz.await();
            }
        }
        lock.unlock();
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        lock.lock();
        while (index <= n){
            if(index % 5 == 0 && index % 3 !=0){
                printBuzz.run();
                index++;
                fizz.signalAll();
                fizzbuzz.signalAll();
                num.signalAll();
            }else{
                //不满足条件 进入条件队列等待被唤醒
                buzz.await();
            }
        }
        lock.unlock();
    }
    public void fizzBuzz(Runnable printFizzBuzz) throws InterruptedException {
        lock.lock();
        while (index <= n){
            if(index % 3 == 0 && index % 5 ==0){
                printFizzBuzz.run();
                index++;
                buzz.signalAll();
                fizz.signalAll();
                num.signalAll();
            }else{
                //不满足条件 进入条件队列等待被唤醒
                fizzbuzz.await();
            }
        }
        lock.unlock();
    }
    public void number(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        while (index <= n){
            if(index % 3 != 0 && index % 5 !=0){
                printNumber.accept(index);
                index++;
                fizz.signalAll();
                buzz.signalAll();
                fizzbuzz.signalAll();
            }else{
                num.await();
            }
        }
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        FizzBuzz fizzBuzz  = new FizzBuzz(20);
        CountDownLatch countDownLatch = new CountDownLatch(4);
        Runnable fizz = () -> {
            try {
                fizzBuzz.fizz(() -> System.out.println("fizz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        };
        Runnable buzz = () -> {
            try {
                fizzBuzz.buzz(() -> System.out.println("buzz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        };
        Runnable fizzbuzz = () -> {
            try {
                fizzBuzz.fizzBuzz(() -> System.out.println("fizzBuzz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        };
        Runnable num = () -> {
            try {
                fizzBuzz.number(value -> System.out.println(value));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        };
        new Thread(fizz).start();
        new Thread(buzz).start();
        new Thread(fizzbuzz).start();
        new Thread(num).start();
        countDownLatch.await();
        System.out.println("done...");
    }
}
