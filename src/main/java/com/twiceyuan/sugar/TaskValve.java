package com.twiceyuan.sugar;


import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by twiceYuan on 4/14/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 * <p/>
 * 缓存需要先决条件才能执行的任务，等待条件满足后全部执行，线程无关
 */
public final class TaskValve {

    private List<Runnable> mTaskQueue    = new Vector<>(); // 任务序列
    private boolean        mIsBlockClose = false; // 阻塞是否关闭

    private final Object monitor = new Object();

    private TaskValve() {
    }

    public static TaskValve create() {
        return new TaskValve();
    }

    /**
     * 执行所有缓存的任务并清空任务队列。
     * <p/>
     * 执行过该方法后，新添加的任务也会直接执行
     */
    public void openValve() {
        synchronized (monitor) {
            mIsBlockClose = true; // 关闭阻塞
            for (Runnable c : new LinkedList<>(mTaskQueue)) {
                if (c != null) {
                    c.run();
                    mTaskQueue.remove(c);
                }
            }
        }
    }

    /**
     * addTask 中的 action 会在 openValve 方法执行过才会执行
     *
     * @param after openValve 方法执行过需要执行的代码
     */
    public void addTask(Runnable after) {
        synchronized (monitor) {
            // 如果阻塞关闭，则至今执行任务，否则添加到缓存序列中
            if (mIsBlockClose) {
                if (after != null) {
                    after.run();
                }
            } else {
                mTaskQueue.add(after);
            }
        }
    }
}
