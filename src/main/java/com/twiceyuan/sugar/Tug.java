package com.twiceyuan.sugar;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by twiceYuan on 18/10/2016.
 *
 * 拔河任务
 *
 * 假设有一异步场景，场景中需要多个任务A（A1,A2,A3,A4...）作为前提，当所有任务完成时，马上执行任务B
 */
public class Tug {

    private Map<Member, Boolean> memberStates;
    private boolean isStart = false;
    private final Object memberStateLock = new Object();

    private Tug() {
        memberStates = new HashMap<>();
    }

    public static Tug create() {
        return new Tug();
    }

    public Tug addMember(MemberProvider provider) {
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
