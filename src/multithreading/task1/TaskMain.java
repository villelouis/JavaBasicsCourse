package multithreading.task1;

public class TaskMain {

    private Task<String> task;
    static final boolean withInterruption = true;

    public TaskMain() {
        if (withInterruption) {
            task = new Task<>(null);
        } else {
            task = new Task<>(new BigExecution());
        }
    }

    public static void main(String[] args) {
        TaskMain taskMain = new TaskMain();
        Thread[] threads = new Thread[100];
        for (Thread t : threads) {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(taskMain.task.get());
                    } catch (Exception e) {
                        System.out.println("Thread interrupted with exception");
                    }
                }
            });
            t.start();
        }
    }
}
