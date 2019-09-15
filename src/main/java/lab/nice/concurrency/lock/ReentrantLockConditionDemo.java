package lab.nice.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        final ReentrantLockCondition task = new ReentrantLockCondition(lock);
        final Thread thread = new Thread(task, "ConditionTask");
        thread.start();
        TimeUnit.SECONDS.sleep(2L);
        lock.lock();
        task.getCondition().signal();
        lock.unlock();
    }
}
