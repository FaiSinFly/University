package Thread;
import Transport.*;

public class ModelNameThread extends Thread {
    private final Vehicle vehicle;

    public ModelNameThread(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void run() {
        String[] models = vehicle.getAllModelNames();
        for (String model : models) {
            System.out.println("[ModelNameThread] Модель: " + model);
        }
    }
}