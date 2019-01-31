package multithreading.task1;

import java.util.concurrent.Callable;

public class BigExecution implements Callable <String> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public String call() throws Exception {
        long x = 0;
        for (long k = 0; k < 100000001; k++){
            x++;
        }
        Thread.sleep(700);
        return Long.toString(x);
    }
}
