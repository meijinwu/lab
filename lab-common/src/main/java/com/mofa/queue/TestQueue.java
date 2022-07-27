package com.mofa.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TestQueue {
    public static void testQueue(){
        Queue q1 = new ConcurrentLinkedDeque();
        q1.offer("1");
        q1.offer("2");
        q1.offer("3");
        System.out.println(q1.poll().toString());
        System.out.println(q1.poll().toString());
        System.out.println(q1.poll().toString());
        System.out.println(q1.size());
    }

    public static void main(String[] args) {
        testQueue();
    }
}
