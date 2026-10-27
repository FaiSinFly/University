package Transport;
import java.util.Arrays;
import java.util.Random;

public class Car implements Vehicle {

    private class Model {
        private String name;
        private double price;

        public Model(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return this.price;
        }

        public void setPrice(double price) {
            if (price < 0) {
                throw new ModelPriceOutOfBoundsException("Цена не может быть отрицательной");
            }
            this.price = price;
        }
    }
    
    private String mark;
    private Model[] models;

    public Car(String mark, int count) {
        this.mark = mark;
        this.models = new Model[count];
        Random randomizer = new Random();
        
        for (int i = 0; i < count; i++) {
            double rndPrice = 1000.0 + (randomizer.nextDouble() * 99000.0);
            this.models[i] = new Model(mark + "_" + (i + 1), rndPrice);
        }
    }

    @Override
    public String getMark() {
        return this.mark;
    }

    @Override
    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public void changeModelName(String oldName, String newName) throws DuplicateModelNameException, NoSuchModelNameException {
        int targetIndex = -1;
        
        for (int i = 0; i < models.length; i++) {
            if (models[i].getName().equals(newName)) {
                throw new DuplicateModelNameException("Model с таким именем уже существует: " + newName);
            }
            if (models[i].getName().equals(oldName)) {
                targetIndex = i;
            }
        }
        
        if (targetIndex == -1) {
            throw new NoSuchModelNameException("Model не найдена: " + oldName);
        }
        
        models[targetIndex].setName(newName);
    }

    @Override
    public String[] getAllModelNames() {
        String[] namesArray = new String[models.length];
        int index = 0;
        for (Model m : models) {
            namesArray[index++] = m.getName();
        }
        return namesArray;
    }

    @Override
    public double getModelPriceByName(String name) throws NoSuchModelNameException {
        for (Model m : models) {
            if (m.getName().equals(name)) {
                return m.getPrice();
            }
        }
        throw new NoSuchModelNameException("Model не найдена: " + name);
    }

    @Override
    public void setModelPriceByName(String name, double newPrice) throws NoSuchModelNameException {
        if (newPrice < 0) {
            throw new ModelPriceOutOfBoundsException("Цена не может быть меньше нуля");
        }
        
        boolean result = false;
        for (Model m : models) {
            if (m.getName().equals(name)) {
                m.setPrice(newPrice);
                result = true;
                break;
            }
        }
        
        if (!result) {
            throw new NoSuchModelNameException("Model не найдена: " + name);
        }
    }

    @Override
    public double[] getAllModelPrices() {
        double[] pricesArray = new double[models.length];
        int index = 0;
        for (Model m : models) {
            pricesArray[index++] = m.getPrice();
        }
        return pricesArray;
    }

    @Override
    public void addModel(String name, double price) throws DuplicateModelNameException {
        if (price < 0) {
            throw new ModelPriceOutOfBoundsException("Недопустимая цена");
        }
        for (Model m : models) {
            if (m.getName().equals(name)) {
                throw new DuplicateModelNameException("Дублирование имени модели: " + name);
            }
        }
        
        this.models = Arrays.copyOf(this.models, this.models.length + 1);
        this.models[this.models.length - 1] = new Model(name, price);
    }

    @Override
    public void deleteModel(String name) throws NoSuchModelNameException {
        int removeIndex = -1;
        for (int i = 0; i < models.length; i++) {
            if (models[i].getName().equals(name)) {
                removeIndex = i;
                break;
            }
        }
        
        if (removeIndex == -1) {
            throw new NoSuchModelNameException("Не удалось удалить. Model не найдена: " + name);
        }
        
        System.arraycopy(models, removeIndex + 1, models, removeIndex, models.length - removeIndex - 1);
        this.models = Arrays.copyOf(this.models, this.models.length - 1);
    }

    @Override
    public int getModelsSize() {
        return models.length;
    }
}