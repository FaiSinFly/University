package Transport;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Moped implements Vehicle {

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
    private LinkedList<Model> models;

    public Moped(String mark, int count) {
        this.mark = mark;
        this.models = new LinkedList<>();
        Random randomizer = new Random();

        for (int i = 0; i < count; i++) {
            double rndPrice = 1000.0 + (randomizer.nextDouble() * 99000.0);
            this.models.add(new Model(mark + "_" + (i + 1), rndPrice));
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
        Model target = null;
        for (Model m : models) {
            if (!oldName.equals(newName) && m.getName().equals(newName)) {
                throw new DuplicateModelNameException("Модель с таким именем уже существует: " + newName);
            }
            if (m.getName().equals(oldName)) {
                target = m;
            }
        }
        if (target == null) {
            throw new NoSuchModelNameException("Модель не найдена: " + oldName);
        }
        target.setName(newName);
    }

    @Override
    public String[] getAllModelNames() {
        String[] names = new String[models.size()];
        int index = 0;
        for (Model m : models) {
            names[index++] = m.getName();
        }
        return names;
    }

    @Override
    public double getModelPriceByName(String name) throws NoSuchModelNameException {
        for (Model m : models) {
            if (m.getName().equals(name)) {
                return m.getPrice();
            }
        }
        throw new NoSuchModelNameException("Модель не найдена: " + name);
    }

    @Override
    public void setModelPriceByName(String name, double newPrice) throws NoSuchModelNameException {
        if (newPrice < 0) {
            throw new ModelPriceOutOfBoundsException("Цена не может быть меньше нуля");
        }
        for (Model m : models) {
            if (m.getName().equals(name)) {
                m.setPrice(newPrice);
                return;
            }
        }
        throw new NoSuchModelNameException("Модель не найдена: " + name);
    }

    @Override
    public double[] getAllModelPrices() {
        double[] prices = new double[models.size()];
        int index = 0;
        for (Model m : models) {
            prices[index++] = m.getPrice();
        }
        return prices;
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
        models.add(new Model(name, price));
    }

    @Override
    public void deleteModel(String name) throws NoSuchModelNameException {
    Iterator<Model> iterator = models.iterator();
    while (iterator.hasNext()) {
        if (iterator.next().getName().equals(name)) {
            iterator.remove();
            return;
        }
    }
    throw new NoSuchModelNameException("Не удалось удалить. Модель не найдена: " + name);
}

    @Override
    public int getModelsSize() {
        return models.size();
    }

    @Override
    public String toString() {
        @SuppressWarnings("StringBuffer вместо StringBuilder, IDE ругается")
        StringBuffer sb = new StringBuffer();
        sb.append("Moped{mark='").append(mark).append("', models=[");
        int i = 0;
        for (Model m : models) {
            sb.append(m.getName()).append("=").append(String.format("%.2f", m.getPrice()));
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
        Moped cloned = (Moped) super.clone();
        cloned.models = new LinkedList<>();
        for (Model m : this.models) {
            cloned.models.add(cloned.new Model(m.getName(), m.getPrice()));
        }
        return cloned;
    }
}