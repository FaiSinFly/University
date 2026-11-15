package Transport;

import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=Задание 1=");
            Vehicle car = new Car("Toyota", 3);
            VehicleUtils.printModelsAndPrices(car);

            System.out.println("=Байтовые потоки=");
            try (FileOutputStream fos = new FileOutputStream("vehicle.bin")) {
                VehicleUtils.outputVehicle(car, fos);
            }
            System.out.println("=Восстановление=");
            try (FileInputStream fis = new FileInputStream("vehicle.bin")) {
                Vehicle binCar = VehicleUtils.inputVehicle(fis);
                VehicleUtils.printModelsAndPrices(binCar);
            }

            System.out.println("=Символьные потоки=");
            try (FileWriter fw = new FileWriter("vehicle.txt")) {
                VehicleUtils.writeVehicle(car, fw);
            }
            try (FileReader fr = new FileReader("vehicle.txt")) {
                Vehicle txtCar = VehicleUtils.readVehicle(fr);
                VehicleUtils.printModelsAndPrices(txtCar);
            }

            System.out.println("Вывод в System.out");
            VehicleUtils.writeVehicle(car, new OutputStreamWriter(System.out));
            System.out.println();

            System.out.println("Чтение из System.in");
            System.out.println("Введите по строкам: Марка, Кол-во моделей, затем пары (Имя, Цена):");
            Vehicle consoleCar = VehicleUtils.readVehicle(new InputStreamReader(System.in));
            VehicleUtils.printModelsAndPrices(consoleCar);

            System.out.println("\n=Задание 2=");
            Motorbike motorbike = new Motorbike("Yamaha", 3);
            System.out.println("Время lastModified (до): " + motorbike.getLastModified());
            VehicleUtils.printModelsAndPrices(motorbike);

            String datFile = "motorbike.dat";
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(datFile))) {
                oos.writeObject(motorbike);
            }

            Thread.sleep(20);

            Motorbike deserializedMotorbike;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(datFile))) {
                deserializedMotorbike = (Motorbike) ois.readObject();
            }

            System.out.println("\nДесериализованный объект");
            System.out.println("Время lastModified (после): " + deserializedMotorbike.getLastModified());
            VehicleUtils.printModelsAndPrices(deserializedMotorbike);

            System.out.println("Результаты:");
            System.out.println("Марки совпадают: " + motorbike.getMark().equals(deserializedMotorbike.getMark()));
            System.out.println("Модели совпадают: " + Arrays.equals(motorbike.getAllModelNames(), deserializedMotorbike.getAllModelNames()));
            System.out.println("Цены совпадают: " + Arrays.equals(motorbike.getAllModelPrices(), deserializedMotorbike.getAllModelPrices()));
            System.out.println("Transient-поле переинициализировано: " + (motorbike.getLastModified() != deserializedMotorbike.getLastModified()));

        } catch (IOException | ClassNotFoundException | DuplicateModelNameException e) {
            System.err.println("Произошла ошибка при выполнении операции: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Выполнение программы было прервано: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}