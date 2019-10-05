/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import pos.entities.food.Food;
import pos.entities.order.Sales;

/**
 *
 * @author fpatel
 */
public class FileManager {
    private static String getDate(){
        return new SimpleDateFormat("dd-MM-YYYY").format(new Date());
    }
    public static void writeFood(Food in) throws IOException{   
        write(in, "items");
    }
    
    public static Food readFood() throws IOException, ClassNotFoundException{
        return (Food) readBinary("items");  
    }
    
    public static void writeSales(Sales in) throws IOException{
        write(in, "sales_"+getDate());
    }
    
    public static Sales readSales() throws IOException, ClassNotFoundException{
        return (Sales) readBinary("sales_"+getDate());
    }
    //http://www.mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/
    private static void write(Object in, String fileName) throws IOException{
        ObjectOutputStream objoutput = new ObjectOutputStream(new FileOutputStream("datasets/"+fileName+".data"));
        objoutput.writeObject(in);
        Gson gson = new Gson();
        String json = gson.toJson(in);
        FileWriter writer = new FileWriter("datasets/"+fileName+".json");
        writer.write(json);
        writer.close();
    }
    
    private static Object readBinary(String fileName) throws IOException, ClassNotFoundException{
        ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("datasets/"+fileName+".data"));
        return objectInput.readObject();
    }
}
