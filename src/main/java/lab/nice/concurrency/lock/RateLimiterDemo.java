package lab.nice.concurrency.lock;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class RateLimiterDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterDemo.class);
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(2.0D);

    public static void main(String[] args) {
        Runnable task = () -> LOGGER.info("{} - {}", Thread.currentThread().getName(), Instant.now());
        for (int i = 0; i < 20; i++) {
            RATE_LIMITER.acquire();
            new Thread(task, "task-" + i).start();
        }
    }

}
