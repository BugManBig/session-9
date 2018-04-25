package ru.sbt.jschool.session9;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutionManagerImpl implements ExecutionManager {
    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        ExecutorService executorService = new ScheduledThreadPoolExecutor(tasks.length);
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < tasks.length; i++) {
            futures.add(executorService.submit(tasks[i]));
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                executorService.shutdown();
                try {
                    if (!executorService.awaitTermination(15, TimeUnit.SECONDS)) {
                        executorService.shutdownNow();
                        if (!executorService.awaitTermination(15, TimeUnit.SECONDS)) {
                            System.out.println("Can`t shut down");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (executorService.isTerminated()) {
                        callback.run();
                        break;
                    }
                }
            }
        }).start();

        return new ContextImpl(futures);
    }
}
