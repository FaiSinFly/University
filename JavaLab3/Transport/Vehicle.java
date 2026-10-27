package Transport;

import java.io.Serializable;

public interface Vehicle extends Serializable {
    public abstract String getMark();
    public abstract void setMark(String mark);
    
    public abstract void changeModelName(String oldName, String newName) throws DuplicateModelNameException, NoSuchModelNameException;
    public abstract String[] getAllModelNames();
    
    public abstract double getModelPriceByName(String name) throws NoSuchModelNameException;
    public abstract void setModelPriceByName(String name, double newPrice) throws NoSuchModelNameException;
    public abstract double[] getAllModelPrices();
    
    public abstract void addModel(String name, double price) throws DuplicateModelNameException;
    public abstract void deleteModel(String name) throws NoSuchModelNameException;
    
    public abstract int getModelsSize();
}