package com.sample.thread;

/**
 * @see java.lang.Runnable
 * User: rayminr
 * Date: 3/18/14
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyRunnable implements Runnable {

    private String threadName;

    public MyRunnable(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() +  "：" + this.threadName + ", i=" + i);
        }
    }
}
