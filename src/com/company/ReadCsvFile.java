package com.company;

import com.csvreader.CsvReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ReadCsvFile {
    private ArrayList<Entry> entries = new ArrayList<>();
    private int size;

    public ReadCsvFile() {
        size = 0;
    }

    public void readCsvFile(CsvReader file) throws IOException {

        try {
            while(file.readRecord()) {
                entries.add(new Entry(file.get(0), file.get(1), file.get(2), file.get(3), file.get(4), file.get(5),
                        file.get(6), file.get(7), file.get(8), file.get(9)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*condition for congestion: speed < 5km/h for more than 90s.
     *If size of data is less than 90, means no congestion at all.
     */
    public void calculateDelay(String [] outputData) {
        size = entries.size();

        if(size > 90) {
            checkSpeedRequirements(outputData, size);
        }
        else {
            outputData[0] = "-";
            outputData[1] = null;
            outputData[2] = "-";
            outputData[3] = "-";
            outputData[4] = "-";
            outputData[5] = "-";
            outputData[6] = "-";
            outputData[7] = "-";
            outputData[8] = null;
            outputData[9] = "-";
            outputData[10] = "-";
            outputData[11] = "-";
            outputData[12] = "-";
            outputData[13] = "0";
        }
    }

    private void checkSpeedRequirements(String [] outputData, int size) {
        int startIndex, endIndex, speed, startHour, endHour, startMinute, endMinute;
        float delay, startSecond, endSecond, startTime, endTime;

        startIndex = 0;
        speed = 0;
        endIndex = 1;
        while(startIndex + 90 < size) {
            for (int i = startIndex; i < size; i++) {
                speed = entries.get(i).getSpeed();
                if (speed > 5 && i != 0) {
                    endIndex = i - 1;
                    break;
                }
                else {
                    endIndex = i;
                }
            }

            startTime = entries.get(startIndex).getIntTime();
            endTime = entries.get(endIndex).getIntTime();

            startSecond = startTime % 100;
            startMinute = (int) (startTime / 100) % 100;
            startHour = (int) (startTime /10000) % 100;
            endSecond = endTime % 100;
            endMinute = (int) (endTime / 100) % 100;
            endHour = (int) (endTime /10000) % 100;

            delay = ((endHour * 3600) + (endMinute * 60) + endSecond) -
                    ((startHour * 3600) + (startMinute * 60) + startSecond);
            if(delay >= 90) {
                outputData[2] = entries.get(startIndex).getLatitude();
                outputData[3] = entries.get(startIndex).getLongitude();
                outputData[4] = entries.get(startIndex).getLinkID();
                outputData[5] = entries.get(startIndex).getTime();
                outputData[6] = "0";
                outputData[9] = entries.get(endIndex).getLatitude();
                outputData[10] = entries.get(endIndex).getLongitude();
                outputData[11] = entries.get(endIndex).getLinkID();
                outputData[12] = entries.get(endIndex).getTime();
                outputData[13] = String.valueOf(delay);

                try {
                    WriteCsvFile output = new WriteCsvFile();
                    output.writeCsvFile(outputData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(speed > 5) {
                startIndex = endIndex + 2;
            }
            else {
                startIndex = endIndex + 1;
            }
        }
    }
}

