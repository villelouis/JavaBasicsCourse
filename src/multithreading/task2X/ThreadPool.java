package multithreading.task2X;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.Queue;

public class ThreadPool {
    private Thread[] threads;

    // завершены ли переданные задачи, за искл. callback'а ?
    private volatile boolean isFinished = false;
    // ещё не запущенные задачи:
    private Queue<Thread> unbegunTaskQueue;

    // количество прерванных и завершенных с ошибками задач:
    private volatile int InterruptedTaskCount;
    private volatile int failedTaskCount;


    public void execute(Runnable callback, Runnable... tasks) {

        threads = new Thread[tasks.length - 1];
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable throwable) {
                synchronized (this) {
                    if (throwable instanceof InterruptedException) {
                        InterruptedTaskCount++;
                    } else {
                        failedTaskCount++;
                    }
                }
            }
        };
        for (int k = 0; k < threads.length; k++) {
            threads[k] = new Thread(tasks[k]);
            threads[k].setUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
        unbegunTaskQueue = new LinkedList<>(Arrays.asList(threads));

        for (Thread thread : threads) {
            thread.start();
            unbegunTaskQueue.poll();
        }
        Thread masterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Thread thread: threads) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isFinished = true;
                Thread finalThread = new Thread(callback);
                finalThread.start();
            }
        });
        masterThread.start();
    }

    public int getFailedTaskCount() {
        return failedTaskCount;
    }

    public int getCompletedTaskCount() {
        int completedTaskCount = 0;
        // считаем все завершённые потоки
        for (Thread thread : threads) {
            if (!thread.isAlive()){
                completedTaskCount++;
            }
        }
        // вычитаем количество неудачно завершившихся или прерванных
        completedTaskCount = completedTaskCount - InterruptedTaskCount - failedTaskCount;

        return completedTaskCount;
    }


    public void interrupt() {
        for (Thread thread : unbegunTaskQueue) {
            thread.interrupt();
        }
    }

    public int getInterruptedTaskCount() {
        return InterruptedTaskCount;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
