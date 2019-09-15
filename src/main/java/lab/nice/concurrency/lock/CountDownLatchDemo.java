package lab.nice.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountDownLatchDemo.class);
    private static final CountDownLatch countDownLatch = new CountDownLatch(10);
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    public static void main(String[] args) throws InterruptedException {
        final Runnable checkTask = () -> {
            try {
                LOGGER.info("{} Checking", Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(random.nextInt(2, 5));
                countDownLatch.countDown();
                LOGGER.info("{} Ready", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                LOGGER.warn("{} interrupted", Thread.currentThread().getName());
            }
        };

        final ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            service.submit(checkTask);
        }
        countDownLatch.await();
        LOGGER.info("Fire");
        service.shutdown();
    }
}
