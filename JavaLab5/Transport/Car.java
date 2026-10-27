package Transport;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Car implements Vehicle {

    private class Model implements Serializable, Cloneable {
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

    @Override
    public String toString() {
        @SuppressWarnings("StringBuffer вместо StringBuilder, IDE ругается")
        StringBuffer sb = new StringBuffer();
        sb.append("Car{mark='").append(mark).append("', models=[");
        
        for (int i = 0; i < models.length; i++) {
            sb.append(models[i].getName()).append("=").append(models[i].getPrice()); 
            if (i < models.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Vehicle)) {
            return false;
        }

        Vehicle other = (Vehicle) obj;

        if (this.mark == null) {
            if (other.getMark() != null) {
                return false;
            }
        } else if (!this.mark.equals(other.getMark())) {
            return false;
        }

        if (this.getModelsSize() != other.getModelsSize()) {
            return false;
        }

        String[] thisModelNames = this.getAllModelNames();
        String[] otherModelNames = other.getAllModelNames();
        
        double[] thisModelPrices = this.getAllModelPrices();
        double[] otherModelPrices = other.getAllModelPrices();

        for (int i = 0; i < thisModelNames.length; i++) {
            if (!thisModelNames[i].equals(otherModelNames[i])) {
                return false;
            }
            if (Double.compare(thisModelPrices[i], otherModelPrices[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = mark != null ? mark.hashCode() : 0;
        
        String[] modelNames = this.getAllModelNames();
        for (String name : modelNames) {
            result = 31 * result + (name != null ? name.hashCode() : 0);
        }
        
        double[] modelPrices = this.getAllModelPrices();
        for (double price : modelPrices) {
            long temp = Double.doubleToLongBits(price);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
        }
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Car cloned = (Car) super.clone();
        
        cloned.models = new Model[this.models.length];
        for (int i = 0; i < this.models.length; i++) {
            cloned.models[i] = cloned.new Model(this.models[i].getName(), this.models[i].getPrice());
        }
        return cloned;
    }
}