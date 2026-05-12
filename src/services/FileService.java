
import java.io.*;

public class FileService {

    public void saveData(String data) {

        try {
            FileWriter writer = new FileWriter("data.txt");

            writer.write(data);

            writer.close();

            System.out.println("Data Saved Successfully");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }
}
