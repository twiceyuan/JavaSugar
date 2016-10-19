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

        for (int i = 1; i <= 1000; i++) {
            delayAddTask((long) (Math.random() * 5000), valve, "步骤" + i);
        }

        delayAndRun(2500, () -> {
            System.out.println("---> \t阀门打开\t\t" + markTime());
            valve.openValve();
        });

        sleep(5100);
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
            System.out.println(taskName + "\t被创建\t\t" + markTime());
            valve.addTask(() -> System.out.println(taskName + "\t被执行\t\t" + markTime()));
        }).start();
    }

    private String markTime() {
        return new SimpleDateFormat("HH:mm:ss SSS").format(new Date());
    }
}
