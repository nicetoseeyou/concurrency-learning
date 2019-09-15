package lab.nice.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadWriteLockDemo.class);
    private static final ReentrantLock lock = new ReentrantLock();
    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final Lock readLock = readWriteLock.readLock();
    private static final Lock writeLock = readWriteLock.writeLock();
    private static volatile int value = 0;

    public static void handleRead(final Lock lock) {
        try {
            LOGGER.info("Read started.");
            lock.lock();
            LOGGER.info("Read locked.");
            TimeUnit.SECONDS.sleep(1L);
            LOGGER.info("Read value {}", value);
            LOGGER.info("Read finished");
        } catch (InterruptedException e) {
            LOGGER.info("Read interrupted.", e);
        } finally {
            lock.unlock();
            LOGGER.info("Read unlocked.");
        }
    }

    public static void handleWrite(final Lock lock, final int newValue) {
        try {
            LOGGER.info("Write started.");
            lock.lock();
            LOGGER.info("Write locked.");
            TimeUnit.SECONDS.sleep(1L);
            value = newValue;
            LOGGER.info("Write finished, value {}", value);
        } catch (InterruptedException e) {
            LOGGER.info("Write interrupted.", e);
        } finally {
            lock.unlock();
            LOGGER.info("Write unlocked.");
        }
    }

    public static void main(String[] args) {
        //Runnable read = () -> handleRead(lock);
        //Runnable write = () -> handleWrite(lock, new Random().nextInt(18));
        Runnable read = () -> handleRead(readLock);
        Runnable write = () -> handleWrite(writeLock, new Random().nextInt(18));
        for (int i = 0; i < 30; i++) {
            new Thread(read, "Read-" + i).start();
        }
        for (int i = 30; i < 32; i++) {
            new Thread(write, "Write-" + i).start();
        }
    }
}
