package multithreading.task1;
import java.util.concurrent.Callable;

public class Task <T>{

    private volatile boolean hasReuslt = false;
    private volatile boolean hasError = false;
    private Callable <? extends T> callable;
    private volatile RuntimeException exception;
    private volatile T result;

    public Task(Callable <? extends T> callable){
        this.callable = callable;
    }

    public T get() throws Exception{
        if (hasReuslt && !hasError){
            System.out.print("Has result  ");
            return result;
        } else if (hasError){
            throw exception;
        } else {
            synchronized (Task.class){
                if (hasReuslt && !hasError){
                    System.out.print("Has result  ");
                    return result;
                } else if (hasError){
                    throw exception;
                }
                try {
                    System.out.print("Computing  ");
                    result = callable.call();
                    hasReuslt = true;
                    return result;
                }
                catch (Exception e){
                    hasError = true;
                    exception = new MultithreadingTaskException("Task was interrupted  ",e);
                    throw exception;
                }
            }
        }
    }
}
