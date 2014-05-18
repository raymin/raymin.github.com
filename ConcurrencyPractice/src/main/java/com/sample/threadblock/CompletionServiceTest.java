package com.sample.threadblock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * Created with IntelliJ IDEA.
 * User: rayminr
 * Date: 12/13/13
 * Time: 10:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompletionServiceTest {

    static int threadNum = 8;
    static int totalSize = 1800;
    static int listSize = totalSize/threadNum;
    static int sleepTime = 1000;

    ExecutorService exec = Executors.newFixedThreadPool(threadNum);

    public class Processor implements Callable<String>{

        private List<String> list;

        public Processor( List<String> list){
            this.list = list;

        }

        public String call(){
            long thread_start = System.currentTimeMillis();
            try {
                for(String str:list){
                    System.out.println(Thread.currentThread().getName() + " " + str);
                }
//                try  {
//                    Thread.sleep(( long ) (Math.random() *  this.sleepTime ));
//                } catch  (InterruptedException e) {
//                }
            }catch (Exception e){

            }
            long thread_end = System.currentTimeMillis();
            System.out.println( String.format("%s, listSize=%s, time=%s" ,Thread.currentThread().getName() ,list.size(), (thread_end - thread_start)));
            return "ok";
        }

    }

    public void excute(){

        long start = System.currentTimeMillis();
        System.out.println("Start excute "  );

        List<String> list = new ArrayList<String>(listSize*threadNum);
        for(int i=0; i<listSize*threadNum; i++){
            list.add(""+i);
        }
        CompletionService serv = new ExecutorCompletionService(exec);


        for(int i=0; i<threadNum; i++){
            serv.submit(new Processor( list.subList(i*listSize,(i+1)*listSize)));
        }

        long out_mid = System.currentTimeMillis();
        try {
            for (int index = 0; index < threadNum; index++) {
                Future<String> task = serv.take();
                //get 返回Callable任务里的call方法的返回值，调用该方法将导致线程阻塞，必须等到子线程结束才得到返回值
                String img = task.get();
                //get(long timeout, TimeUnit unit)：返回Callable任务里的call方法的返回值，该方法让程序最多阻塞timeout和unit指定的时间。
                //String img = task.get(2,TimeUnit.MILLISECONDS);
                long end = System.currentTimeMillis();
                //System.out.println( String.format("Thread %s, time=%s" ,index,(end - out_start)));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        long out_end = System.currentTimeMillis();
        System.out.println(String.format("End excute totalSize=%s, time=%s",totalSize,(out_end - start)));

        exec.shutdown();

    }

    public  static  void  main(String args[])  throws  InterruptedException, ExecutionException,TimeoutException {

        CompletionServiceTest test = new CompletionServiceTest();
        test.excute();

    }



}
