package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileService {

    // SAVE DATA INTO FILE
    public void saveData(String data) {

        try {

            FileWriter writer
                    = new FileWriter("data/users.txt", true);

            BufferedWriter bw
                    = new BufferedWriter(writer);

            bw.write(data);
            bw.newLine();

            bw.close();
            writer.close();

            System.out.println("Data Saved Successfully");

        } catch (IOException e) {

            System.out.println("Error : " + e.getMessage());
        }
    }

    // READ DATA FROM FILE
    public void readData() {

        try {

            FileReader reader
                    = new FileReader("data/users.txt");

            BufferedReader br
                    = new BufferedReader(reader);

            String line;

            System.out.println("\n===== USER DATA =====");

            while ((line = br.readLine()) != null) {

                System.out.println(line);
            }

            br.close();
            reader.close();

        } catch (IOException e) {

            System.out.println("Error : " + e.getMessage());
        }
    }
}
