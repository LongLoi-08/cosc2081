package models;
import java.io.*;
import java.util.ArrayList;

public class FileIO {
    public static void write(String filePath, String attributes, String obj) throws IOException {
        File file = new File(filePath);
        FileWriter csvFile = new FileWriter(file, true);

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        if(reader.readLine() == null){
            csvFile.append(attributes);
            csvFile.append("\n");
        }

        reader.close();

        try {
            ArrayList<String> data = new ArrayList<>();

            data.add(obj);

            for (String s : data) {
                csvFile.append(String.valueOf(s));
                csvFile.append("\n");
            }
            csvFile.close();
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
