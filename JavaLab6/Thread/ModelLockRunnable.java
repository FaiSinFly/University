package Thread;
import Transport.*;
import java.util.concurrent.locks.ReentrantLock;

public class ModelLockRunnable implements Runnable {
    private final Vehicle vehicle;
    private final ReentrantLock lock;

    public ModelLockRunnable(Vehicle vehicle, ReentrantLock lock) {
        this.vehicle = vehicle;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            String[] models = vehicle.getAllModelNames();
            for (String model : models) {
                System.out.println("[Lock] Модель: " + model);
            }
        } finally {
            lock.unlock();
        }
    }
}