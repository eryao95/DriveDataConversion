package com.company;

import com.csvreader.CsvReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String outputData[] = new String[14];
        String parts[] = new String[3];
        String fileName;

        File f = new File("00rihyl67gpsd711xiaa1r76.txt.csv");
        fileName = f.getName();

        parts = fileName.split(".txt");
        String routeID = parts[0];

        outputData[0] = "START";
        outputData[1] = routeID;
        outputData[7] = "END";
        outputData[8] = routeID;

        try{
            CsvReader file = new CsvReader(fileName);
            ReadCsvFile input = new ReadCsvFile();
            input.readCsvFile(file);
            input.calculateDelay(outputData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

