package ru.sbt.jschool.session9;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class ContextImpl implements Context {
    private List<Future> futures;

    public ContextImpl(List<Future> futures) {
        this.futures = futures;
    }

    @Override
    public int getCompletedTaskCount() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (Future elem : futures) {
            if (elem.isDone()) {
                atomicInteger.incrementAndGet();
            }
        }
        return atomicInteger.get();
    }

    @Override
    public int getFailedTaskCount() {
        return 0;
    }

    @Override
    public int getInterruptedTaskCount() {
        return 0;
    }

    @Override
    public void interrupt() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
