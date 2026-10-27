package Transport;

public class Main {
    public static void main(String[] args) {
        
        Vehicle[] vehicles = new Vehicle[2];
        vehicles[0] = new Car("Toyota", 3);
        vehicles[1] = new Motorbike("Yamaha", 2);

        for (Vehicle vehicle : vehicles) {
            try {
                System.out.println("===============================");
                System.out.println("Класс: " + vehicle.getClass().getSimpleName());
                System.out.println("Марка: " + vehicle.getMark());
                System.out.printf("Средняя цена моделей: %.2f\n\n", VehicleUtils.getAverageModelPrice(vehicle));
                VehicleUtils.printModelsAndPrices(vehicle);


                System.out.println("Текущая марка: " + vehicle.getMark());
                vehicle.setMark("BMW");
                System.out.println("Новая марка: " + vehicle.getMark());

                System.out.println("Количество моделей: " + vehicle.getModelsSize());

                String firstModel = vehicle.getAllModelNames()[0];
                System.out.printf("Цена модели '%s': %.2f\n", firstModel, vehicle.getModelPriceByName(firstModel));
                vehicle.setModelPriceByName(firstModel, 123456.78);
                System.out.printf("Измененная цена '%s': %.2f\n", firstModel, vehicle.getModelPriceByName(firstModel));

                String renamedModel = "Toyota";
                vehicle.changeModelName(firstModel, renamedModel);
                System.out.println("Переименовали модель в: " + renamedModel);
            
                vehicle.addModel("Toyota", 50000);
                System.out.println("Добавили модель 'Tesla'. Новый размер: " + vehicle.getModelsSize());

                vehicle.deleteModel("Tesla");
                System.out.println("Удалили модель 'Tesla'. Итоговый размер: " + vehicle.getModelsSize());

            } catch (DuplicateModelNameException e) {
                System.out.println("Ошибка дублирования: " + e.getMessage());
            } catch (NoSuchModelNameException e) {
                System.out.println("Ошибка отсутствия модели: " + e.getMessage());
            } catch (ModelPriceOutOfBoundsException e) {
                System.out.println("Ошибка неверной цены: " + e.getMessage());
            }
        }
    }
}