package Transport;

public class VehicleUtils {

    public static double getAverageModelPrice(Vehicle vehicle) {
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

    public static void printAllModelNames(Vehicle vehicle) {
        System.out.println("Названия моделей для марки " + vehicle.getMark() + ":");
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

    public static void printAllModelPrices(Vehicle vehicle) {
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

    public static void printModelsAndPrices(Vehicle vehicle) {
        System.out.println("Модели и их цены для транспортного средства марки " + vehicle.getMark() + ":");
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
}