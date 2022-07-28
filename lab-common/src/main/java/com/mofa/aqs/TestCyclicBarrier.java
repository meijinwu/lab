package com.mofa.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestCyclicBarrier {
    // 请求的数量
    private static final int requestCount = 550;
    // 需要同步的线程数量
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < requestCount; i++) {
            final  int requstNum = i;
            Thread.sleep(1000);
            executorService.execute(()->{
                test(requstNum);
            });
            
        }
        executorService.shutdown();
    }

    private static void test(int requstNum) {
        System.out.println("requestNum:" + requstNum + "is ready");
        try {
            /**等待60秒，保证子线程完全执行结束*/
            cyclicBarrier.await(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("-----CyclicBarrierException------");
        }
        System.out.println("requestNum:" + requstNum + "is finish");
    }
}
