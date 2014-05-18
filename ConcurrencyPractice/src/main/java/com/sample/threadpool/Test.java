package com.sample.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created with IntelliJ IDEA.
 * User: rayminr
 * Date: 3/27/14
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public  static  void  main(String args[]) {

        String tmp = "10811631" + (long)(Math.random() * 100000000);
        System.out.print(tmp);
    }
}
