package Thread;
import Transport.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class FileVehicleRunnable implements Runnable {
    private final String fileName;
    private final BlockingQueue<Vehicle> queue;
    private final boolean useAddMethod;

    public FileVehicleRunnable(String fileName, BlockingQueue<Vehicle> queue) {
        this(fileName, queue, false);
    }

    public FileVehicleRunnable(String fileName, BlockingQueue<Vehicle> queue, boolean useAddMethod) {
        this.fileName = fileName;
        this.queue = queue;
        this.useAddMethod = useAddMethod;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String mark = reader.readLine();
            if (mark != null && !mark.trim().isEmpty()) {
                Vehicle vehicle = new Car(mark.trim(), 3);
                if (useAddMethod) {
                    queue.add(vehicle);
                } else {
                    queue.put(vehicle);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла " + fileName + ": " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IllegalStateException e) {
            System.err.println("Очередь переполнена при вызове add() для марки из файла " + fileName + ": " + e.getMessage());
        }
    }
}