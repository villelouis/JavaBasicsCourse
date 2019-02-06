package multithreading.task1;

import java.util.concurrent.Callable;

public class Task<T> {
    private final Callable<? extends T> callable;
    private volatile RuntimeException exception;
    private volatile T result;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() throws Exception {
        if (result != null && exception == null) {
            System.out.print("Has result  ");
            return result;
        } else if (exception != null) {
            throw exception;
        } else synchronized (Task.class) {
            if (result != null && exception == null) {
                System.out.print("Has result  ");
                return result;
            } else if (exception != null) {
                throw exception;
            }
            try {
                System.out.print("Computing  ");
                result = callable.call();
                return result;
            } catch (Exception e) {
                exception = new MultithreadingTaskException("Task was interrupted  ", e);
                throw exception;
            }
        }
    }
}
