package Thread;
import Transport.*;

public class MarkRunnable implements Runnable {
    private final Vehicle vehicle;

    public MarkRunnable(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void run() {
        System.out.println("Марка ТС: " + vehicle.getMark() + " (выполнено потоком " + Thread.currentThread().getName() + ")");
    }
}