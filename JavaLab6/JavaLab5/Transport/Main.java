package Transport;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("===Задание 1===");
            if (args.length < 4) {
            System.out.println("Ошибка. Вы не ввели 4 аргумента: полное название класса, метод, аргумент1 метода, аргумент2 метода. Поэтому будут использоваться значения по умолчанию:");
                args = new String[]{"Transport.Car", "setModelPriceByName", "Toyota_1", "77777.7"};
            }

            String className = args[0];
            String methodName = args[1];
            String targetModelName = args[2];
            double newPrice = Double.parseDouble(args[3]);

            System.out.printf("Параметры: класс=%s, метод=%s, модель=%s, новая цена=%.2f%n", className, methodName, targetModelName, 
            newPrice);

            Class<?> c = Class.forName(className);
            Constructor<?> constructor = c.getConstructor(String.class, int.class);
            Vehicle reflectionCar = (Vehicle) constructor.newInstance("Toyota", 3);

            System.out.println("До вызова метода:");
            System.out.println(reflectionCar);

            Method method = c.getMethod(methodName, String.class, double.class);
            method.invoke(reflectionCar, targetModelName, newPrice);

            System.out.println("После вызова метода через рефлексию:");
            System.out.println(reflectionCar);
            VehicleUtils.printModelsAndPrices(reflectionCar);

            System.out.println("=== Задание 2===");
            Vehicle createdCar = VehicleUtils.createVehicle("Mazda", 2, reflectionCar);
            System.out.println("Созданный объект того же класса: " + createdCar.getClass());

            System.out.println("\n===Задания 3, 4, 5===");

            Vehicle scooter = new Scooter("Vespa", 2);
            scooter.addModel("Vespa_Sprint", 3200.0);
            scooter.changeModelName("Vespa_1", "Vespa_Prim");
            scooter.setModelPriceByName("Vespa_Prim", 4100.0);
            scooter.deleteModel("Vespa_2");
            System.out.println("Скутер: " + scooter);

            Vehicle quadBike = new QuadBike("Polaris", 2);
            quadBike.addModel("Polaris_Sport", 8500.0);
            quadBike.changeModelName("Polaris_1", "Polaris_Justy");
            quadBike.setModelPriceByName("Polaris_Justy", 9200.0);
            quadBike.deleteModel("Polaris_2");
            System.out.println("Квадроцикл: " + quadBike);

            Vehicle moped = new Moped("Alpha", 2);
            moped.addModel("Alpha_RX", 1500.0);
            moped.changeModelName("Alpha_1", "Alpha_7UP");
            moped.setModelPriceByName("Alpha_7UP", 1800.0);
            moped.deleteModel("Alpha_2");
            System.out.println("Мопед: " + moped);

            System.out.println("\n=== Задание 6===");
            double avgAll = VehicleUtils.getAverageVehiclesPrice(scooter, quadBike, moped);
            System.out.printf("Средняя цена всех моделей (Scooter, QuadBike, Moped): %.2f%n", avgAll);

            System.out.println("\n===Задание 7===");
            
            File file = new File("vehicle.txt");
            try (FileWriter fileWriter = new FileWriter(file)) 
            {
                VehicleUtils.writeVehicle(quadBike, fileWriter);
                System.out.println("Данные успешно записаны в файл: " + file.getAbsolutePath());
            }

            Vehicle restoredQuadBike;
            try (FileReader fileReader = new FileReader(file)) 
            {
                restoredQuadBike = VehicleUtils.readVehicle(fileReader);
                System.out.println("Объект успешно прочитан из файла.");
                System.out.println(restoredQuadBike);
                System.out.println("Объекты равны (equals): " + quadBike.equals(restoredQuadBike));
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("Ошибка при выполнении: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка при выполнении: " + e.getMessage());
        }
    }
}