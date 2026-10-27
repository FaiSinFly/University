package Transport;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Scooter implements Vehicle {
    private String mark;
    private HashMap<String, Double> models;

    public Scooter(String mark, int count) {
        this.mark = mark;
        this.models = new HashMap<>();
        Random randomizer = new Random();

        for (int i = 0; i < count; i++) {
            double rndPrice = 1000.0 + (randomizer.nextDouble() * 99000.0);
            this.models.put(mark + "_" + (i + 1), rndPrice);
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
        if (!models.containsKey(oldName)) {
            throw new NoSuchModelNameException("Модель не найдена: " + oldName);
        }
        if (!oldName.equals(newName) && models.containsKey(newName)) {
            throw new DuplicateModelNameException("Модель с таким именем уже существует: " + newName);
        }
        Double price = models.remove(oldName);
        models.put(newName, price);
    }

    @Override
    public String[] getAllModelNames() {
        return models.keySet().toArray(new String[0]);
    }

    @Override
    public double getModelPriceByName(String name) throws NoSuchModelNameException {
        if (!models.containsKey(name)) {
            throw new NoSuchModelNameException("Модель не найдена: " + name);
        }
        return models.get(name);
    }

    @Override
    public void setModelPriceByName(String name, double newPrice) throws NoSuchModelNameException {
        if (newPrice < 0) {
            throw new ModelPriceOutOfBoundsException("Цена не может быть меньше нуля");
        }
        if (!models.containsKey(name)) {
            throw new NoSuchModelNameException("Модель не найдена: " + name);
        }
        models.put(name, newPrice);
    }

    @Override
    public double[] getAllModelPrices() {
        double[] prices = new double[models.size()];
        int index = 0;
        for (Double price : models.values()) {
            prices[index++] = price;
        }
        return prices;
    }

    @Override
    public void addModel(String name, double price) throws DuplicateModelNameException {
        if (price < 0) {
            throw new ModelPriceOutOfBoundsException("Недопустимая цена");
        }
        if (models.containsKey(name)) {
            throw new DuplicateModelNameException("Дублирование имени модели: " + name);
        }
        models.put(name, price);
    }

    @Override
    public void deleteModel(String name) throws NoSuchModelNameException {
        if (!models.containsKey(name)) {
            throw new NoSuchModelNameException("Не удалось удалить. Модель не найдена: " + name);
        }
        models.remove(name);
    }

    @Override
    public int getModelsSize() {
        return models.size();
    }

    @Override
    public String toString() {
        @SuppressWarnings("StringBuffer вместо StringBuilder, IDE ругается")
        StringBuffer sb = new StringBuffer();
        sb.append("Scooter{mark='").append(mark).append("', models=[");
        int i = 0;
        for (Map.Entry<String, Double> entry : models.entrySet()) {
            sb.append(entry.getKey()).append("=").append(String.format("%.2f", entry.getValue()));
            if (i < models.size() - 1) {
                sb.append(", ");
            }
            i++;
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
        if (this.mark == null ? other.getMark() != null : !this.mark.equals(other.getMark())) {
            return false;
        }
        if (this.getModelsSize() != other.getModelsSize()) {
            return false;
        }
        String[] thisNames = this.getAllModelNames();
        String[] otherNames = other.getAllModelNames();
        double[] thisPrices = this.getAllModelPrices();
        double[] otherPrices = other.getAllModelPrices();

        for (int i = 0; i < thisNames.length; i++) {
            if (!thisNames[i].equals(otherNames[i]) || Double.compare(thisPrices[i], otherPrices[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = mark != null ? mark.hashCode() : 0;
        for (String name : getAllModelNames()) {
            result = 31 * result + (name != null ? name.hashCode() : 0);
        }
        for (double price : getAllModelPrices()) {
            long temp = Double.doubleToLongBits(price);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
        }
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Scooter cloned = (Scooter) super.clone();
        cloned.models = new HashMap<>(this.models);
        return cloned;
    }
}