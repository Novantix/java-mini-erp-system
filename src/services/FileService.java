package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileService {

    // SAVE DATA INTO FILE
    public void saveData(String filePath, String data) {

        try {

            FileWriter writer
                    = new FileWriter(filePath, true);

            BufferedWriter bw
                    = new BufferedWriter(writer);

            bw.write(data);
            bw.newLine();

            bw.close();
            writer.close();

            System.out.println(
                    "Data Saved Successfully");

        } catch (IOException e) {

            System.out.println(
                    "Error : " + e.getMessage());
        }
    }

    // READ DATA FROM FILE
    public void readData(String filePath) {

        try {

            FileReader reader
                    = new FileReader(filePath);

            BufferedReader br
                    = new BufferedReader(reader);

            String line;

            System.out.println(
                    "\n===== FILE DATA =====");

            while ((line = br.readLine()) != null) {

                System.out.println(line);
            }

            br.close();
            reader.close();

        } catch (IOException e) {

            System.out.println(
                    "Error : " + e.getMessage());
        }
    }

    // DELETE USER BY ID
    public void deleteUserById(
            String filePath,
            int userId) {

        try {

            File inputFile
                    = new File(filePath);

            File tempFile
                    = new File("data/temp.txt");

            File deletedFile
                    = new File("data/deleted_users.txt");

            BufferedReader br
                    = new BufferedReader(
                            new FileReader(inputFile));

            BufferedWriter bw
                    = new BufferedWriter(
                            new FileWriter(tempFile));

            BufferedWriter deletedWriter
                    = new BufferedWriter(
                            new FileWriter(deletedFile, true));

            String line;

            boolean found = false;

            while ((line = br.readLine()) != null) {

                if (line.contains(
                        "ID : " + userId)) {

                    found = true;

                    // SAVE DELETED USER
                    deletedWriter.write(line);
                    deletedWriter.newLine();

                } else {

                    bw.write(line);
                    bw.newLine();
                }
            }

            br.close();
            bw.close();
            deletedWriter.close();

            inputFile.delete();

            tempFile.renameTo(inputFile);

            if (found) {

                System.out.println(
                        "User Deleted Successfully");

                System.out.println(
                        "Deleted User Stored in deleted_users.txt");

            } else {

                System.out.println(
                        "User ID Not Found");
            }

        } catch (Exception e) {

            System.out.println(
                    "Error : " + e.getMessage());
        }
    }
}
