package com.sample.thread;

import java.util.concurrent.Callable;

/**
 * concurrent中新增线程接口，一般需要配合FutureTask使用
 * @see java.util.concurrent.Callable
 * User: rayminr
 * Date: 3/18/14
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyCallable implements Callable<String> {

    private String threadName;

    public MyCallable(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public String call() throws Exception {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() +  "：" + this.threadName + ", i=" + i);
        }
        return Thread.currentThread().getName() +  "：" + this.threadName + " is Done";
    }
}
