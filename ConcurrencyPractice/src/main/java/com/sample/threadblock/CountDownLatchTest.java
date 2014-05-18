package com.sample.threadblock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * 使用CountDownLatch等待所有子线程处理完成
 * User: rayminr
 * Date: 12/13/13
 * Time: 10:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class CountDownLatchTest {

    static int threadNum = 8;
    static int totalSize = 1800;
    static int listSize = totalSize/threadNum;
    static int sleepTime = 1000;

    ExecutorService exec = Executors.newFixedThreadPool(threadNum);

    public class Processor implements Runnable{

        private CountDownLatch latch;
        private List<String> list;


        public Processor(CountDownLatch latch, List<String> list){
            this.latch = latch;
            this.list = list;
        }

        public void run(){
            long thread_start = System.currentTimeMillis();
            try {
                for(String str:list){
                    System.out.println(Thread.currentThread().getName() + " " + str);
                }
//                try  {
//                    Thread.sleep(( long ) (Math.random() *  1000));
//                } catch  (InterruptedException e) {
//                }
            }catch (Exception e){

            }finally {
                latch.countDown();
            }
            long thread_end = System.currentTimeMillis();
            System.out.println( String.format("%s, listSize=%s, time=%s" ,Thread.currentThread().getName() ,list.size(), (thread_end - thread_start)));
        }

    }

    public void excute(){

        long start = System.currentTimeMillis();
        System.out.println("Start excute "  );

        List<String> list = new ArrayList<String>(listSize*threadNum);
        for(int i=0; i<listSize*threadNum; i++){
            list.add(""+i);
        }
        CountDownLatch latch=new CountDownLatch(threadNum);

        for(int i=0; i<threadNum; i++){
            exec.submit(new Processor(latch, list.subList(i*listSize,(i+1)*listSize)));
        }

        long out_mid = System.currentTimeMillis();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        long out_end = System.currentTimeMillis();
        System.out.println(String.format("End excute totalSize=%s, time=%s",totalSize,(out_end - start)));

        exec.shutdown();

    }

    public  static  void  main(String args[])  throws  InterruptedException, ExecutionException,TimeoutException {

        CountDownLatchTest test = new CountDownLatchTest();
        test.excute();

    }

}
