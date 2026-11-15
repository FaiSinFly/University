package Transport;
import java.io.*;

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
        System.out.println("Названия моделей для транспортного средства марки " + vehicle.getMark() + ":");
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
        System.out.println("Модели и их цены для марки " + vehicle.getMark() + ":");
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

    public static void outputVehicle(Vehicle v, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        
        byte[] classBytes = v.getClass().getSimpleName().getBytes();
        dos.writeInt(classBytes.length);
        dos.write(classBytes);
        
        byte[] markBytes = v.getMark().getBytes();
        dos.writeInt(markBytes.length);
        dos.write(markBytes);
        
        int count = v.getModelsSize();
        dos.writeInt(count);
        
        String[] names = v.getAllModelNames();
        double[] prices = v.getAllModelPrices();
        for (int i = 0; i < count; i++) {
            byte[] nameBytes = names[i].getBytes();
            dos.writeInt(nameBytes.length);
            dos.write(nameBytes);
            dos.writeDouble(prices[i]);
        }
        dos.flush();
    }

    public static Vehicle inputVehicle(InputStream in) throws IOException, DuplicateModelNameException {
        DataInputStream dis = new DataInputStream(in);
        
        int classLen = dis.readInt();
        byte[] classBytes = new byte[classLen];
        dis.readFully(classBytes);
        String className = new String(classBytes);

        int markLen = dis.readInt();
        byte[] markBytes = new byte[markLen];
        dis.readFully(markBytes);
        String mark = new String(markBytes);
        
        int count = dis.readInt();
        
        Vehicle vehicle;
        if ("Motorbike".equalsIgnoreCase(className)) {
            vehicle = new Motorbike(mark, 0);
        } else {
            vehicle = new Car(mark, 0);
        }
        
        for (int i = 0; i < count; i++) {
            int nameLen = dis.readInt();
            byte[] nameBytes = new byte[nameLen];
            dis.readFully(nameBytes);
            String name = new String(nameBytes);
            
            double price = dis.readDouble();
            vehicle.addModel(name, price);
        }
        
        return vehicle;
    }

    public static void writeVehicle(Vehicle v, Writer out) {
        PrintWriter pw = new PrintWriter(out);
        
        pw.println(v.getClass().getSimpleName());
        pw.println(v.getMark());
        
        int count = v.getModelsSize();
        pw.println(count);
        
        String[] names = v.getAllModelNames();
        double[] prices = v.getAllModelPrices();
        for (int i = 0; i < count; i++) {
            pw.println(names[i]);
            pw.println(prices[i]);
        }
        pw.flush();
    }

    public static Vehicle readVehicle(Reader in) throws IOException, DuplicateModelNameException {
        BufferedReader br = new BufferedReader(in);
        
        String className = br.readLine();
        String mark = br.readLine();
        int count = Integer.parseInt(br.readLine());
        
        Vehicle vehicle;
        if ("Motorbike".equalsIgnoreCase(className)) {
            vehicle = new Motorbike(mark, 0);
        } else {
            vehicle = new Car(mark, 0);
        }
        
        for (int i = 0; i < count; i++) {
            String name = br.readLine();
            double price = Double.parseDouble(br.readLine());
            vehicle.addModel(name, price);
        }
        
        return vehicle;
    }
}