package com.company;

import com.csvreader.CsvWriter;

import java.io.FileWriter;
import java.io.IOException;

public class WriteCsvFile {

    public WriteCsvFile() {
    }

    public void writeCsvFile(String [] outputData) throws IOException {
        String outputFile = "outputFile.csv";

        try {
            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

            for (int i = 0; i < 18; i++) {
                csvOutput.write(outputData[i]);
                if (i == 6) {
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
