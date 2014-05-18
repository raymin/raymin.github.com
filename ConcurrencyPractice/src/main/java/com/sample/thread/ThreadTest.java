package com.sample.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 三种线程实现的测试
 * User: rayminr
 * Date: 3/18/14
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThreadTest {

    public static void main(String[] args) {
        testMyThread();
        testMyRunnable();
        testMyCallable();
    }

    public static void testMyThread(){
        System.out.println("-------------------MyThread Start---------------------");
        MyThread mt1 = new MyThread("MyThread线程a");
        MyThread mt2 = new MyThread("MyThread线程b");
        // 真正启动线程，有JVM调用此线程的run()方法
        mt1.start();
        // 从打印的日志可以看到并未启动线程
        mt2.run();
        System.out.println("-------------------MyThread End---------------------");
    }

    public static void testMyRunnable(){
        System.out.println("-------------------MyRunnable Start---------------------");
        MyRunnable mr1 = new MyRunnable("MyRunnable线程a");
        MyRunnable mr2 = new MyRunnable("MyRunnable线程b");
        // 真正启动线程，有JVM调用此线程的run()方法
        new Thread(mr1).start();
        // 从打印的日志可以看到并未启动线程
        new Thread(mr2).run();
        System.out.println("-------------------MyRunnable End---------------------");
    }

    public static void testMyCallable(){
        System.out.println("-------------------MyCallable Start---------------------");
        Callable mc1 = new MyCallable("MyRunnable线程a");
        Callable mc2 = new MyCallable("MyRunnable线程b");

        FutureTask<String> f1 = new FutureTask<String>(mc1);
        FutureTask<String> f2 = new FutureTask<String>(mc2);

        // 真正启动线程，有JVM调用此线程的run()方法
        new Thread(f1).start();
        try {
            // 使用FutureTask.get后主线程将堵塞，一直等子线程返回结果，不用FutureTask.get非阻塞
            System.out.println(f1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // 从打印的日志可以看到并未启动线程
        new Thread(f2).run();
        try {
            System.out.println(f2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("-------------------MyCallable End---------------------");
    }
}
