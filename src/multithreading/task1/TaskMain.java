package multithreading.task1;

public class TaskMain{

    private Task<String> task;

    public TaskMain(){
        task = new Task<>(new BigExecution());
    }

    public static void main(String[] args) {
        TaskMain taskMain = new TaskMain();
        Thread [] threads = new Thread [100];
        for (Thread t: threads) {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(taskMain.task.get());
                    }
                    catch (Exception e){
                        System.out.println("Error has occured");
                    }
                }
            });
            t.start();
        }
    }
}
