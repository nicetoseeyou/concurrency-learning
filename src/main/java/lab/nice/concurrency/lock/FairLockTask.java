package lab.nice.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

public class FairLockTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FairLockTask.class);
    private final ReentrantLock lock;

    public FairLockTask(final ReentrantLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                LOGGER.info("{} get the lock.", Thread.currentThread().getName());
            } finally {
                lock.unlock();
            }
        }
    }
}
