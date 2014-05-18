package com.sample.thread;

/**
 * 建议采用实现Runnalbe或Callable接口的实现
 * User: rayminr
 * Date: 3/18/14
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyThread extends Thread {

    private String threadName;

    public MyThread(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() +  "：" + this.threadName + ", i=" + i);
        }
    }

}
