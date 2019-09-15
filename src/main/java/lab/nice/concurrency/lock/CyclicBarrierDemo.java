package lab.nice.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(CyclicBarrierDemo.class);

    public static void main(String[] args) {
        final CyclicBarrier barrier = new CyclicBarrier(10, new BarrierRun(10, false));
        for (int i = 0; i < 10; i++) {
            new Thread(new Solider(barrier, "Solider-" + i), "Solider-" + i).start();
        }
    }

    static class BarrierRun implements Runnable {
        private final int count;
        private boolean completed;

        BarrierRun(final int count, final boolean completed) {
            this.count = count;
            this.completed = completed;
        }

        @Override
        public void run() {
            if (completed) {
                LOGGER.info("Sir, {} solider(s), mission completed", count);
            } else {
                LOGGER.info("Sir, {} solider(s), team assembled", count);
                completed = true;
            }
        }
    }

    static class Solider implements Runnable {

        private final CyclicBarrier barrier;
        private final String name;

        Solider(final CyclicBarrier barrier, final String name) {
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                LOGGER.info("{} standby", name);
                barrier.await();
                doMission();
                LOGGER.info("{} re-standby", name);
                barrier.await();
            } catch (InterruptedException e) {
                LOGGER.warn("{} interrupted", name);
            } catch (BrokenBarrierException e) {
                LOGGER.warn("{} mission broken", name);
            }
        }

        void doMission() throws InterruptedException {
            LOGGER.info("{} - Mission started", name);
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(2, 5));
            LOGGER.info("{} - Mission completed", name);
        }
    }
}
