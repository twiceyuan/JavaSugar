package com.twiceyuan.sugar;

import org.junit.Test;

/**
 * Created by twiceYuan on 18/10/2016.
 *
 * Tug usecase
 */
public class TugTest {

    @Test
    public void test() throws Exception {
        Tug tug = Tug.create();

        for (int i = 1; i <= 10; i++) {
            System.out.printf("添加任务%d%n", i);
            final int index= i;
            tug.addMember(member -> new Thread(() -> {
                sleep((long) (Math.random() * 30000));
                System.out.printf("任务%d准备就绪%n", index);
                member.ready();
            }).start());
        }

        tug.setAllReadyListener(() -> System.out.println("所有任务准备就绪"));
        tug.start();

        // wait for task all finish
        sleep(31000);
    }

    private void sleep(long timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
