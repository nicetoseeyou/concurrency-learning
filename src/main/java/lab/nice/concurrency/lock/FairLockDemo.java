package lab.nice.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

public class FairLockDemo {
    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock(true);
        Thread thread1 = new Thread(new FairLockTask(lock), "FairTask-1");
        Thread thread2 = new Thread(new FairLockTask(lock), "FairTask-2");
        thread1.start();
        thread2.start();
    }
}
