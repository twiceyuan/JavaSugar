package com.twiceyuan.sugar;

import org.junit.Test;

/**
 * Created by twiceYuan on 18/10/2016.
 *
 * Tetris usecase
 */
public class TetrisTest {

    @Test
    public void test() throws Exception {
        Tetris tetris = Tetris.create();

        for (int i = 1; i <= 5; i++) {
            System.out.printf("添加任务%d%n", i);
            final int index= i;
            tetris.addMember(member -> new Thread(() -> {
                sleep((long) (Math.random() * 5000));
                System.out.printf("任务%d准备就绪%n", index);
                member.ready();
            }).start());
        }

        tetris.setAllReadyListener(() -> System.out.println("所有任务准备就绪"));
        tetris.start();

        // wait for task all finish
        sleep(5100);
    }

    private void sleep(long timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
