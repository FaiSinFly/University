package Thread;
import Transport.*;

public class PriceThread extends Thread {
    private final Vehicle vehicle;

    public PriceThread(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void run() {
        double[] prices = vehicle.getAllModelPrices();
        for (double price : prices) {
            System.out.printf("[PriceThread] Цена: %.2f%n", price);
        }
    }
}