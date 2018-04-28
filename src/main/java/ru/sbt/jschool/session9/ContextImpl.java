package ru.sbt.jschool.session9;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class ContextImpl implements Context {
    private List<Future> futures;

    public ContextImpl(List<Future> futures) {
        this.futures = futures;
    }

    @Override
    public int getCompletedTaskCount() {
        AtomicInteger counter = new AtomicInteger();
        for (Future elem : futures) {
            if (elem.isDone()) {
                counter.incrementAndGet();
            }
        }
        return counter.get();
    }

    @Override
    public int getFailedTaskCount() {
        AtomicInteger counter = new AtomicInteger();
        for (Future elem : futures) {
            if (elem.isDone()) {
                try {
                    elem.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    counter.incrementAndGet();
                }
            }
        }
        return counter.get();
    }

    @Override
    public int getInterruptedTaskCount() {
        AtomicInteger counter = new AtomicInteger();
        for (Future elem : futures) {
            if (elem.isCancelled()) {
                counter.incrementAndGet();
            }
        }
        return counter.get();
    }

    @Override
    public void interrupt() {
        for (Future elem : futures) {
            elem.cancel(false);
        }
    }

    @Override
    public boolean isFinished() {
        AtomicInteger counter = new AtomicInteger();
        for (Future elem : futures) {
            if (elem.isDone()) {
                counter.incrementAndGet();
            }
        }
        return counter.get() == futures.size();
    }
}
