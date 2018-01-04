package com.company;

import com.csvreader.CsvReader;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        String outputData[] = new String[18];
        String parts[] = new String[3];
        String fileName;

        File folder = new File("Files");
        File [] listOfFiles = folder.listFiles();


        for(int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            fileName = file.getName();
            parts = fileName.split("_");
            String routeID = parts[0];

            outputData[1] = routeID;
            outputData[8] = routeID;

            try{
                CsvReader roadFileToRead = new CsvReader("link_cat.txt");
                CsvReader fileToRead = new CsvReader("C:\\Users\\Bo Yang\\Desktop\\DriveDataConversion\\Files\\" + fileName);
                ReadCsvFile input = new ReadCsvFile();
                input.createHashMap(roadFileToRead);
                input.readCsvFile(fileToRead);
                input.checkForTrafficLights(outputData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}