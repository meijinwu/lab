package com.mofa.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class TestSemaphore {
    // 排队总人数（请求总数）
    public static int clientTotal = 10;
    // 可同时受理业务的窗口数量（同时并发执行的线程数）
    public static int threadTotal = 2;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(300);
        //最多只允许2个信号量共同执行
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
           final int threadNum = i;
           threadPool.execute(()->{
               try {
                   //假如semaphore=20 ，一次获得5个许可acquire(5),相当于最多允许4个线程同时执行 20/5=4
                   semaphore.acquire();//获得许可
                   test(threadNum);
                   semaphore.release();//释放许可
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           });
        }
        countDownLatch.await();
        threadPool.shutdown();
    }

    public static void test(int i) throws InterruptedException {
        System.out.println("服务号{}，业务受理中" + i);
        Thread.sleep(1000);// 模拟请求的耗时操作
        System.out.println("========================================================================");
    }
}
