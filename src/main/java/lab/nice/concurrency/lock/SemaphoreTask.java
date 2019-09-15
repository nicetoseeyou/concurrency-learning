package lab.nice.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SemaphoreTask.class);

    private final Semaphore semaphore;

    public SemaphoreTask(final Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("{} is started.", Thread.currentThread().getName());
            semaphore.acquire();
            LOGGER.info("{} is acquired.", Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(2L);
            LOGGER.info("{} is done.", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            LOGGER.warn("{} got Interrupted.", Thread.currentThread().getName(), e);
        } finally {
            semaphore.release();
            LOGGER.info("{} is released.", Thread.currentThread().getName());
        }
    }
}
