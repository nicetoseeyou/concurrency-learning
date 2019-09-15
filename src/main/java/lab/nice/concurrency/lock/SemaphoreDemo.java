package lab.nice.concurrency.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(5);
        final ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            service.submit(new SemaphoreTask(semaphore));
        }
        service.shutdown();
    }
}
