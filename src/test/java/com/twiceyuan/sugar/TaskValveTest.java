package com.twiceyuan.sugar;

import org.junit.Test;

/**
 * Created by twiceYuan on 4/29/16.
 *
 * TaskValve unit test
 */
public class TaskValveTest {

    @Test
    public void test() {
        final TaskValve valve = TaskValve.create();
        delayAndRun((long) (Math.random() * 1000), new Runnable() {
            @Override
            public void run() {
                valve.addTask(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("步骤1, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Runnable() {
            @Override
            public void run() {
                valve.addTask(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("步骤2, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Runnable() {
            @Override
            public void run() {
                valve.addTask(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("步骤3, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Runnable() {
            @Override
            public void run() {
                valve.addTask(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("步骤4, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Runnable() {
            @Override
            public void run() {
                valve.addTask(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("步骤0");
                    }
                });
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void delayAndRun(final long ms, final Runnable runnable) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (runnable != null) {
                    runnable.run();
                }
            }
        }).start();
    }
}
