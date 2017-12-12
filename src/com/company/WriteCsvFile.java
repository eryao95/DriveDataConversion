package com.company;

import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteCsvFile {

    public WriteCsvFile() {
    }

    public void writeCsvFile(String [] outputData) throws IOException {
        String outputFile = "outputFile.csv";

        try {
            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

            csvOutput.write("START TL");
            csvOutput.write("Route ID");
            csvOutput.write("P0-Lat");
            csvOutput.write("P0-Long");
            csvOutput.write("Link ID");
            csvOutput.write("Time");
            csvOutput.write("Delay");
            csvOutput.endRecord();

            for (int i = 0; i < 14; i++) {
                csvOutput.write(outputData[i]);
                if (i == 6) {
                    csvOutput.endRecord();
                    csvOutput.write("END TL");
                    csvOutput.write("Route ID");
                    csvOutput.write("P1-Lat");
                    csvOutput.write("P1-Long");
                    csvOutput.write("Link ID");
                    csvOutput.write("Time");
                    csvOutput.write("Delay");
                    csvOutput.endRecord();
                }
            }
            csvOutput.endRecord();
            csvOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
