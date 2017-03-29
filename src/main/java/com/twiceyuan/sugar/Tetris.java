package com.twiceyuan.sugar;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by twiceYuan on 18/10/2016.
 *
 * 类似俄罗斯方块消除的多任务协调策略
 *
 * 假设每个方块到达是一个任务，{@link this#addMember(MemberProvider)} 就是添加一个方块任务，调用 {@link Member#ready()} 来标记
 * 该任务到达完成（例如到达底部）
 *
 * 所有任务添加进去之后可以通过 {@link this#setAllReadyListener(AllReadyListener)} 来监听所有任务完成的事件，以触发下面的操作（例
 * 如消除本行）
 */
public class Tetris {

    private Map<Member, Boolean> memberStates;
    private boolean isStart = false;
    private final Object memberStateLock = new Object();

    private Tetris() {
        memberStates = new HashMap<>();
    }

    public static Tetris create() {
        return new Tetris();
    }

    public Tetris addMember(MemberProvider provider) {
        if (provider != null) {
            Member member = new Member() {
                @Override
                public void ready() {
                    synchronized (memberStateLock) {
                        memberStates.put(this, true);
                        if (isStart) {
                            checkAll();
                        }
                    }
                }
            };
            synchronized (memberStateLock) {
                memberStates.put(member, false);
            }
            provider.member(member);
        }
        return this;
    }

    public void checkAll() {

        boolean isAllReady = true;
        for (Boolean isReady : memberStates.values()) {
            if (isReady == null || (!isReady)) {
                isAllReady = false;
                break;
            }
        }
        if (isAllReady && mAllReadyListener != null) {
            mAllReadyListener.call();
        }
    }

    public void start() {
        isStart = true;
    }

    public interface MemberProvider {
        void member(Member member);
    }

    public interface Member {
        void ready();
    }

    public interface AllReadyListener {
        void call();
    }

    private AllReadyListener mAllReadyListener;

    public void setAllReadyListener(AllReadyListener allReadyListener) {
        this.mAllReadyListener = allReadyListener;
    }
}
