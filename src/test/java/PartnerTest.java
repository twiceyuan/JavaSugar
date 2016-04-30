import func.Call;
import org.junit.Test;

/**
 * Created by twiceYuan on 4/29/16.
 *
 * Partner unit test
 */
public class PartnerTest {

    @Test
    public void test() {
        final Partner partner = Partner.build();
        delayAndRun((long) (Math.random() * 1000), new Call() {
            @Override
            public void call() {
                partner.after(new Call() {
                    @Override
                    public void call() {
                        System.out.println("步骤1, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Call() {
            @Override
            public void call() {
                partner.after(new Call() {
                    @Override
                    public void call() {
                        System.out.println("步骤2, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Call() {
            @Override
            public void call() {
                partner.after(new Call() {
                    @Override
                    public void call() {
                        System.out.println("步骤3, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Call() {
            @Override
            public void call() {
                partner.after(new Call() {
                    @Override
                    public void call() {
                        System.out.println("步骤4, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Call() {
            @Override
            public void call() {
                partner.before(new Call() {
                    @Override
                    public void call() {
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

    @Test
    public void test2() {
        final Partner partner2 = Partner.build();
        final Partner partner = Partner.build(partner2);
        delayAndRun((long) (Math.random() * 1000), new Call() {
            @Override
            public void call() {
                partner.after(new Call() {
                    @Override
                    public void call() {
                        System.out.println("步骤1, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Call() {
            @Override
            public void call() {
                partner.after(new Call() {
                    @Override
                    public void call() {
                        System.out.println("步骤2, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Call() {
            @Override
            public void call() {
                partner.after(new Call() {
                    @Override
                    public void call() {
                        System.out.println("步骤3, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Call() {
            @Override
            public void call() {
                partner.after(new Call() {
                    @Override
                    public void call() {
                        System.out.println("步骤4, 等待 0 完成");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Call() {
            @Override
            public void call() {
                partner2.before(new Call() {
                    @Override
                    public void call() {
                        System.out.println("步骤0(Partner2)");
                    }
                });
            }
        });
        delayAndRun((long) (Math.random() * 1000), new Call() {
            @Override
            public void call() {
                partner.before(new Call() {
                    @Override
                    public void call() {
                        System.out.println("步骤0(Partner1)");
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

    public void delayAndRun(final long ms, final Call call) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (call != null) {
                    call.call();
                }
            }
        }).start();
    }
}
