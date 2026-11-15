package Thread;
import Transport.*;

import java.util.concurrent.locks.ReentrantLock;

public class PriceLockRunnable implements Runnable {
    private final Vehicle vehicle;
    private final ReentrantLock lock;

    public PriceLockRunnable(Vehicle vehicle, ReentrantLock lock) {
        this.vehicle = vehicle;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            double[] prices = vehicle.getAllModelPrices();
            for (double price : prices) {
                System.out.printf("[Lock] Цена: %.2f%n", price);
            }
        } finally {
            lock.unlock();
        }
    }
}