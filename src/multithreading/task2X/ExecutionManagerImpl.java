package multithreading.task2X;

public class ExecutionManagerImpl implements ExecutionManager {

    private boolean isFirstStart = true;
    private ThreadPool threadPool;

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {

        if (isFirstStart) {
            isFirstStart = false;
            threadPool = new ThreadPool();
            threadPool.execute(callback, tasks);
        }

        return new Context() {
            @Override
            public int getCompletedTaskCount() {
                return threadPool.getCompletedTaskCount();
            }

            @Override
            public int getFailedTaskCount() {
                return threadPool.getFailedTaskCount();
            }

            @Override
            public int getInterruptedTaskCount() {
                return threadPool.getInterruptedTaskCount();
            }

            @Override
            public void interrupt() {
                threadPool.interrupt();
            }

            @Override
            public boolean isFinished() {
                return threadPool.isFinished();
            }
        };
    }
}
