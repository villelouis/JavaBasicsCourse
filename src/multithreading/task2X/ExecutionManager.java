package multithreading.task2X;

public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks);
}
