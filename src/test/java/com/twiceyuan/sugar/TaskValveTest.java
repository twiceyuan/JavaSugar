package com.twiceyuan.sugar;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by twiceYuan on 4/29/16.
 * <p>
 * TaskValve unit test
 */
public class TaskValveTest {

    @Test
    public void test() {
        final TaskValve valve = TaskValve.create();
        delayAddTask(1200, valve, "步骤1");
        delayAddTask(800, valve, "步骤2");
        delayAddTask(500, valve, "步骤3");
        delayAddTask(1500, valve, "步骤4");
        delayAndRun(1000, () -> {
            System.out.println("openValve at " + markTime());
            valve.openValve();
        });

        sleep(3000);
    }

    private void delayAndRun(final long ms, final Runnable runnable) {
        new Thread(() -> {
            sleep(ms);
            if (runnable != null) {
                runnable.run();
            }
        }).start();
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void delayAddTask(final long ms, TaskValve valve, String taskName) {
        new Thread(() -> {
            sleep(ms);
            System.out.println(taskName + " create at: " + markTime());
            valve.addTask(() -> System.out.println(taskName + " run at   : " + markTime()));
        }).start();
    }

    private String markTime() {
        return new SimpleDateFormat("HH:mm:ss SSS").format(new Date());
    }
}
