package com.twiceyuan.sugar;


import java.util.*;

/**
 * Created by twiceYuan on 4/14/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 * <p/>
 * 缓存需要先决条件才能执行的任务，等待条件满足后全部执行，线程无关
 */
public final class TaskValve {

    private Map<Integer, List<Runnable>> mTaskQueue = new TreeMap<>(); // 任务序列
    private boolean mIsBlockClose = false; // 阻塞是否关闭

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
            Set<Integer> keys = mTaskQueue.keySet();
            for (Integer key : keys) {
                List<Runnable> runnableList = mTaskQueue.get(key);
                if (runnableList != null && runnableList.size() > 0) {
                    //noinspection Convert2streamapi
                    for (Runnable runnable : runnableList) {
                        runnable.run();
                    }
                }
            }
        }
    }

    /**
     * addTask 中的 action 会在 openValve 方法执行过才会执行
     *
     * @param priority openValve 前加入的任务执行的优先级，对 openValve 之后的任务无效，默认为 100
     * @param after    openValve 方法执行过需要执行的代码
     */
    public void addTask(int priority, Runnable after) {
        synchronized (monitor) {
            // 如果阻塞关闭，则至今执行任务，否则添加到缓存序列中
            if (mIsBlockClose) {
                if (after != null) {
                    after.run();
                }
            } else {
                List<Runnable> runnableList = mTaskQueue.get(priority);
                if (runnableList == null) {
                    runnableList = new ArrayList<>();
                    runnableList.add(after);
                    mTaskQueue.put(priority, runnableList);
                } else {
                    runnableList.add(after);
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
        addTask(100, after);
    }
}
