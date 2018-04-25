package ru.sbt.jschool.session9;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Runnable callback = new Runnable() {
            @Override
            public void run() {
                System.out.println("Finish");
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("Fin");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable[] runnables = new Runnable[10];
        for (int i = 0; i < runnables.length; i++) {
            runnables[i] = runnable;
        }
        ExecutionManager executionManager = new ExecutionManagerImpl();
        executionManager.execute(callback, runnables);
    }
}
