package lab.nice.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCondition implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReentrantLockCondition.class);

    private final ReentrantLock lock;
    private final Condition condition;

    public ReentrantLockCondition(final ReentrantLock lock) {
        this.lock = lock;
        this.condition = this.lock.newCondition();
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("{} is started", Thread.currentThread().getName());
            lock.lock();
            LOGGER.info("{} is locked", Thread.currentThread().getName());
            condition.await();
            LOGGER.info("{} is going on.", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            LOGGER.info("{} is unlocked", Thread.currentThread().getName());
        }
    }
}
