package Transport;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Scanner;

public class VehicleUtils {

    // Задание 2
    public static Vehicle createVehicle(String mark, int size, Vehicle v) 
    {
        if (v == null) {
            return null;
        }
        try {
            Class<?> c = v.getClass();
            Constructor<?> constructor = c.getConstructor(String.class, int.class);
            return (Vehicle) constructor.newInstance(mark, size);
        } catch (Exception e) {
            return null;
        }
    }

    public static double getAverageModelPrice(Vehicle vehicle) 
    {
        double[] prices = vehicle.getAllModelPrices();
        if (prices.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (double price : prices) {
            sum += price;
        }
        return sum / prices.length;
    }

    // Задание 6
    public static double getAverageVehiclesPrice(Vehicle... vehicles) 
    {
        if (vehicles == null || vehicles.length == 0) {
            return 0.0;
        }
        double totalSum = 0.0;
        int totalModels = 0;

        for (Vehicle vehicle : vehicles) {
            if (vehicle != null) {
                double[] prices = vehicle.getAllModelPrices();
                for (double price : prices) {
                    totalSum += price;
                    totalModels++;
                }
            }
        }
        return totalModels == 0 ? 0.0 : totalSum / totalModels;
    }

    public static void printAllModelNames(Vehicle vehicle) 
    {
        System.out.println("Названия моделей для транспортного средства марки " + vehicle.getMark() + ":");
        String[] names = vehicle.getAllModelNames();
        if (names.length == 0) {
            System.out.println("Модели отсутствуют");
            return;
        }
        for (String name : names) {
            System.out.println("- " + name);
        }
        System.out.println();
    }

    public static void printAllModelPrices(Vehicle vehicle) 
    {
        System.out.println("Цены на модели для марки " + vehicle.getMark() + ":");
        double[] prices = vehicle.getAllModelPrices();
        if (prices.length == 0) {
            System.out.println("Цены отсутствуют (нет моделей)");
            return;
        }
        for (double price : prices) {
            System.out.printf("- %.2f\n", price);
        }
        System.out.println();
    }

    public static void printModelsAndPrices(Vehicle vehicle) 
    {
        System.out.println("Модели и их цены для марки " + vehicle.getMark() + ":");
        String[] names = vehicle.getAllModelNames();
        double[] prices = vehicle.getAllModelPrices();
        if (names.length == 0) {
            System.out.println("Данные отсутствуют");
            return;
        }
        for (int i = 0; i < names.length; i++) {
            System.out.printf("- Модель: %s | Цена: %.2f\n", names[i], prices[i]);
        }
        System.out.println();
    }

    public static void outputVehicle(Vehicle v, OutputStream out) throws IOException 
    {
        DataOutputStream dos = new DataOutputStream(out);

        byte[] classBytes = v.getClass().getSimpleName().getBytes();
        dos.writeInt(classBytes.length);
        dos.write(classBytes);

        byte[] markBytes = v.getMark().getBytes();
        dos.writeInt(markBytes.length);
        dos.write(markBytes);

        int count = v.getModelsSize();
        dos.writeInt(count);

        String[] names = v.getAllModelNames();
        double[] prices = v.getAllModelPrices();
        for (int i = 0; i < count; i++) {
            byte[] nameBytes = names[i].getBytes();
            dos.writeInt(nameBytes.length);
            dos.write(nameBytes);
            dos.writeDouble(prices[i]);
        }
        dos.flush();
    }

    public static Vehicle inputVehicle(InputStream in) throws IOException, DuplicateModelNameException 
    {
        DataInputStream dis = new DataInputStream(in);

        int classLen = dis.readInt();
        byte[] classBytes = new byte[classLen];
        dis.readFully(classBytes);
        String className = new String(classBytes);

        int markLen = dis.readInt();
        byte[] markBytes = new byte[markLen];
        dis.readFully(markBytes);
        String mark = new String(markBytes);

        int count = dis.readInt();
        Vehicle vehicle = instantiateByClassName(className, mark);

        for (int i = 0; i < count; i++) {
            int nameLen = dis.readInt();
            byte[] nameBytes = new byte[nameLen];
            dis.readFully(nameBytes);
            String name = new String(nameBytes);
            double price = dis.readDouble();
            vehicle.addModel(name, price);
        }
        return vehicle;
    }

    private static Vehicle instantiateByClassName(String className, String mark) 
    {
        switch (className) {
            case "Motorbike":
                return new Motorbike(mark, 0);
            case "Scooter":
                return new Scooter(mark, 0);
            case "QuadBike":
                return new QuadBike(mark, 0);
            case "Moped":
                return new Moped(mark, 0);
            default:
                return new Car(mark, 0);
        }
    }

    // Задание 7
    public static void writeVehicle(Vehicle v, Writer out) 
    {
        PrintWriter pw = new PrintWriter(out);
        pw.printf("%s%n", v.getClass().getSimpleName());
        pw.printf("%s%n", v.getMark());

        int count = v.getModelsSize();
        pw.printf("%d%n", count);

        String[] names = v.getAllModelNames();
        double[] prices = v.getAllModelPrices();
        for (int i = 0; i < count; i++) {
            pw.printf("%s%n", names[i]);
            pw.printf("%f%n", prices[i]);
        }
        pw.flush();
    }

    // Задание 7
    public static Vehicle readVehicle(Reader in) throws DuplicateModelNameException 
    {
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\R");

        String className = scanner.next();
        String mark = scanner.next();
        int count = scanner.nextInt();

        Vehicle vehicle = instantiateByClassName(className, mark);

        for (int i = 0; i < count; i++) {
            String name = scanner.next();
            double price = scanner.nextDouble();
            vehicle.addModel(name, price);
        }
        return vehicle;
    }
}