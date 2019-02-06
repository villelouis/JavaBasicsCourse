package multithreading.task2X;

public class ExecutionManagerTest {
    public static void main(String[] args) {
        Runnable callback = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Было вызывано прерывание");
                }
            }
        };
        Runnable[] tasks = new Runnable[10];
        for (int k=0; k < tasks.length; k++) {
            tasks[k] = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Было вызывано прерывание");
                    }
                }
            };
        }
        ExecutionManagerImpl executionManager = new ExecutionManagerImpl();
        Context context = executionManager.execute(callback, tasks);
        while (!context.isFinished()) {
                System.out.println(
                                " context.getCompletedTaskCount(): " +
                                context.getCompletedTaskCount()+
                                "\n context.getFailedTaskCount(): " +
                                context.getFailedTaskCount()+
                                "\n context.getInterruptedTaskCount(): "+
                                context.getInterruptedTaskCount()
                );
        }
        System.out.println("Вызываем 2 раз");
        System.out.println(
                " context.getCompletedTaskCount(): " +
                        context.getCompletedTaskCount()+
                        "\n context.getFailedTaskCount(): " +
                        context.getFailedTaskCount()+
                        "\n context.getInterruptedTaskCount(): "+
                        context.getInterruptedTaskCount()
        );
    }
}
