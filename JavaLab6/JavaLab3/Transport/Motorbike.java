package Transport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Random;

public class Motorbike implements Vehicle{

    private class Model implements Serializable {
        String nameModel = null;
        double price = Double.NaN;
        Model prev = null;
        Model next = null;

        public Model() {}

        public Model(String nameModel, double price) {
            this.nameModel = nameModel;
            this.price = price;
        }

        public Model(String name, double price, Model prev, Model next) {
            this.nameModel = name;
            this.price = price;
            this.prev = prev;
            this.next = next;
        }
    }

    private String mark;
    private int size = 0;
    private Model head;
    
    private transient long lastModified;

    {
        lastModified = System.currentTimeMillis();
    }

    public Motorbike(String mark, int count) {
        this.mark = mark;
        
        this.head = new Model();
        this.head.next = this.head;
        this.head.prev = this.head;

        Random randomizer = new Random();
        for (int i = 0; i < count; i++) {
            double rndPrice = 1000.0 + (randomizer.nextDouble() * 99000.0);
            addNode(mark + "_" + (i + 1), rndPrice);
        }
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.lastModified = System.currentTimeMillis();
    }

    private void updateLastModified() {
        this.lastModified = System.currentTimeMillis();
    }

    private void addNode(String name, double price) {
        Model newNode = new Model(name, price);
        Model tail = head.prev;
        
        tail.next = newNode;
        newNode.prev = tail;
        newNode.next = head;
        head.prev = newNode;
        
        size++;
    }

    @Override
    public String getMark() {
        return this.mark;
    }

    @Override
    public void setMark(String mark) {
        this.mark = mark;
        updateLastModified();
    }

    @Override
    public void changeModelName(String oldName, String newName) throws DuplicateModelNameException, NoSuchModelNameException {
        Model current = head.next;
        Model target = null;
        
        while (current != head) {
            if (current.nameModel.equals(newName)) {
                throw new DuplicateModelNameException("Модель с таким именем уже существует: " + newName);
            }
            if (current.nameModel.equals(oldName)) {
                target = current;
            }
            current = current.next;
        }
        
        if (target == null) {
            throw new NoSuchModelNameException("Модель не найдена: " + oldName);
        }
        
        target.nameModel = newName;
        updateLastModified();
    }

    @Override
    public String[] getAllModelNames() {
        String[] namesArray = new String[size];
        Model current = head.next;
        int index = 0;
        
        while (current != head) {
            namesArray[index++] = current.nameModel;
            current = current.next;
        }
        return namesArray;
    }

    @Override
    public double getModelPriceByName(String name) throws NoSuchModelNameException {
        Model current = head.next;
        while (current != head) {
            if (current.nameModel.equals(name)) {
                return current.price;
            }
            current = current.next;
        }
        throw new NoSuchModelNameException("Модель не найдена: " + name);
    }

    @Override
    public void setModelPriceByName(String name, double newPrice) throws NoSuchModelNameException {
        if (newPrice < 0) {
            throw new ModelPriceOutOfBoundsException("Цена не может быть меньше нуля");
        }
        
        Model current = head.next;
        while (current != head) {
            if (current.nameModel.equals(name)) {
                current.price = newPrice;
                updateLastModified();
                return;
            }
            current = current.next;
        }
        throw new NoSuchModelNameException("Модель не найдена: " + name);
    }

    @Override
    public double[] getAllModelPrices() {
        double[] pricesArray = new double[size];
        Model current = head.next;
        int index = 0;
        
        while (current != head) {
            pricesArray[index++] = current.price;
            current = current.next;
        }
        return pricesArray;
    }

    @Override
    public void addModel(String name, double price) throws DuplicateModelNameException {
        if (price < 0) {
            throw new ModelPriceOutOfBoundsException("Недопустимая цена");
        }
        
        Model current = head.next;
        while (current != head) {
            if (current.nameModel.equals(name)) {
                throw new DuplicateModelNameException("Дублирование имени модели: " + name);
            }
            current = current.next;
        }
        
        addNode(name, price);
        updateLastModified();
    }

    @Override
    public void deleteModel(String name) throws NoSuchModelNameException {
        Model current = head.next;
        
        while (current != head) {
            if (current.nameModel.equals(name)) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                updateLastModified();
                return;
            }
            current = current.next;
        }
        
        throw new NoSuchModelNameException("Не удалось удалить. Модель не найдена: " + name);
    }

    @Override
    public int getModelsSize() {
        return size;
    }
    
    public long getLastModified() {
        return lastModified;
    }
}