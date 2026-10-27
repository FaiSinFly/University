package Transport;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("===Класс Car===");

            Vehicle car1 = new Car("Toyota", 3);
            System.out.println("Оригинал (car1):");
            System.out.println(car1);

            Vehicle car2 = (Vehicle) car1.clone();
            System.out.println("\nКлон (car2):");
            System.out.println(car2);

            System.out.println("\nПроверка equals(): " + car1.equals(car2));
            System.out.println("Хэш-код car1: " + car1.hashCode());
            System.out.println("Хэш-код car2: " + car2.hashCode());
            System.out.println("Хэш-коды равны: " + (car1.hashCode() == car2.hashCode()));

            String[] car2Models = car2.getAllModelNames();
            if (car2Models.length > 0) {
                car2.changeModelName(car2Models[0], "Toyota_Modified");
            }
            
            System.out.println("После изменения клона:");
            System.out.println("Оригинал (car1): " + car1);
            System.out.println("Клон (car2): " + car2);
            System.out.println("Проверка equals() после изменения: " + car1.equals(car2));

            System.out.println("\n\n===Тест Motorbike===");
            
            Vehicle moto1 = new Motorbike("Yamaha", 2);
            System.out.println("Оригинал (moto1):");
            System.out.println(moto1.toString());

            Vehicle moto2 = (Vehicle) moto1.clone();
            System.out.println("\nКлон (moto2):");
            System.out.println(moto2.toString());

            System.out.println("\nПроверка equals() (moto1 == moto2): " + moto1.equals(moto2));
            System.out.println("Хэш-код moto1: " + moto1.hashCode());
            System.out.println("Хэш-код moto2: " + moto2.hashCode());
            System.out.println("Хэш-коды равны: " + (moto1.hashCode() == moto2.hashCode()));

            System.out.println("\nИзменяем цену первой модели у клона (moto2)...");
            String[] moto2Models = moto2.getAllModelNames();
            if (moto2Models.length > 0) {
                moto2.setModelPriceByName(moto2Models[0], 99999.99);
            }

            System.out.println("После изменения клона:");
            System.out.println("Оригинал (moto1): " + moto1.toString());
            System.out.println("Клон (moto2): " + moto2.toString());
            System.out.println("Проверка equals() после изменения: " + moto1.equals(moto2));
        } catch (CloneNotSupportedException | DuplicateModelNameException | NoSuchModelNameException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }
}