package com.sample.threadpool;

import java.util.Date;
import java.util.concurrent.*;


/**
 * 几种线程池管理创建方式，通过线程池的方式来提交和管理线程
 * User: rayminr
 * Date: 12/13/13
 * Time: 10:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExecutorServiceTest {

    public   static   void  main(String args[])  throws  InterruptedException, ExecutionException,TimeoutException {
        ExecutorServiceTest test = new ExecutorServiceTest();

        System.out.println("Start Main Thread: [" + Thread.currentThread().getName() + "]");
        //test.testSingleThreadExecutor();
        //test.testFixedThreadExecutor();
        //test.testScheduledThreadExecutor();
        test.testCachedThreadExecutor();
        //test.testCallableAndFuture();
        System.out.println("End Main Thread: [" + Thread.currentThread().getName() + "]");
    }

    public void testSingleThreadExecutor(){
        //创建一个使用单个工作线程的Executor，以无界队列方式来运行该线程，可以保证线程安全的因为只有一个线程。
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for ( int  index =  0 ; index <  10 ; index++) {
            Runnable run = new  Runnable() {
                public   void  run() {
                    long  time = ( long ) (Math.random() *  1000 );
                    System.out.println("Sub Thread: [" + Thread.currentThread().getName() + "] Sleeping " + time + "ms");
                    try  {
                        Thread.sleep(time);
                    } catch  (InterruptedException e) {
                    }
                }
            };
            // 将runnable接口的实现交有Executor管理的线程（可能在新的线程、已入池的线程或者正调用的线程）中执行
            exec.execute(run);
        }
        // 执行之前提交的任务，但不接受新任务
        exec.shutdown();
    }

    public void testFixedThreadExecutor(){
        // 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程
        ExecutorService exec = Executors.newFixedThreadPool(3);
        for ( int  index =  0 ; index <  10 ; index++) {
            Runnable run = new  Runnable() {
                public   void  run() {
                        long  time = ( long ) (Math.random() *  1000 );
                    System.out.println("Sub Thread: [" + Thread.currentThread().getName() + "] Sleeping " + time + "ms");
                    try  {
                        Thread.sleep(time);
                    } catch  (InterruptedException e) {
                    }
                }
            };
            // 将runnable接口的实现交有Executor管理的线程（可能在新的线程、已入池的线程或者正调用的线程）中执行
            exec.execute(run);
        }
        // must shutdown
        exec.shutdown();
    }

    public void testCachedThreadExecutor(){
        // 创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们
        ExecutorService exec = Executors.newCachedThreadPool();
        for ( int  index =  0 ; index <  10 ; index++) {
            Runnable run = new  Runnable() {
                public   void  run() {
                    long  time = ( long ) (Math.random() *  1000 );
                    System.out.println("Sub Thread: [" + Thread.currentThread().getName() + "] Sleeping " + time + "ms");
                    try  {
                        Thread.sleep(time);
                    } catch  (InterruptedException e) {
                    }
                }
            };
            // 将runnable接口的实现交有Executor管理的线程（可能在新的线程、已入池的线程或者正调用的线程）中执行
            exec.execute(run);
        }
        // must shutdown
        exec.shutdown();
    }

    public void testScheduledThreadExecutor(){
        // 创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        final Runnable beeper = new Runnable() {
            int count = 0;
            public void run() {
                System.out.println("Sub Thread: [" + Thread.currentThread().getName() + "]  " + new Date() + " beep " + (++count));
            }
        };
        // 创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期(1秒钟后运行，并每隔2秒运行一次)
        final ScheduledFuture beeperHandle1 = scheduler.scheduleAtFixedRate(beeper, 1, 2, TimeUnit.SECONDS);
        // 创建并执行一个在给定初始延迟后首次启用的定期操作，随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟
        // (2秒钟后运行，并每次在上次任务运行完后等待5秒后重新运行)
        final ScheduledFuture beeperHandle2 = scheduler.scheduleWithFixedDelay(beeper, 2, 5, TimeUnit.SECONDS);

        // 创建并执行在给定延迟后启用的一次性操作(30秒后结束关闭任务，并且关闭Scheduler)
        scheduler.schedule(new Runnable() {
                                public void run() {
                                    beeperHandle1.cancel(true);
                                    beeperHandle2.cancel(true);
                                    scheduler.shutdown();
                                }
                            }, 30, TimeUnit.SECONDS);
    }

    public void testCallableAndFuture() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService exec = Executors.newFixedThreadPool(3);
        CompletionService serv = new ExecutorCompletionService(exec);
        for (int index = 0; index < 100; index++) {
            final int NO = index;
            Callable<String> downImg = new Callable<String>() {
                public String call() throws Exception {
                    Thread.sleep((long) (Math.random() * 100));
                    System.out.println( "Sub Thread: [" + Thread.currentThread().getName() + "] Downloaded Image " + NO);
                    return "Downloaded Image " + NO;
                }
            };
            serv.submit(downImg);
        }
        //Thread.sleep(1000 * 2);
        // 在主线程获取callable线程返回值时，如果callable线程还存在线程没有执行完，则主线程会等待callable线程全部执行完毕获取所有的返回值之后才会继续执行下面的代码。
        // 如果不调用Future的get方法则不会阻塞主线程.
        // 如果任务已经完成，get会立即返回或者抛出一个Exception,如果任务没有完成，get会阻塞直到它完成。如果任务抛出了异常。get会将改异常封装为ExecutionException.然后重新抛出。
        // 如果任务被取消，get会抛出CancellationException。当抛出ExecutionException时。可以用getCause重新获得被封装的原始异常。
        System.out.println("Show web content");
        for (int index = 0; index < 100; index++) {
            Future<String> task = serv.take();
            //get 返回Callable任务里的call方法的返回值，调用该方法将导致线程阻塞，必须等到子线程结束才得到返回值
            String img = task.get();
            //get(long timeout, TimeUnit unit)：返回Callable任务里的call方法的返回值，该方法让程序最多阻塞timeout和unit指定的时间。
            //String img = task.get(2,TimeUnit.MILLISECONDS);
            System.out.println(img);
        }

        System.out.println("End");
        exec.shutdown();
    }
}
