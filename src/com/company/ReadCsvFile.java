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

    /*condition for traffic light: speed < 5km/h for more than 90s and LinkID changes.
     *If size of data is less than 90, means no congestion at all.
     */
    public void checkForTrafficLights(String [] outputData) {
        size = entries.size();

        if(size > 90) {
            checkSpeedRequirements(outputData, size);
        }
    }

    private void checkSpeedRequirements(String [] outputData, int size) {
        int startIndex, endIndex, speed;
        float delay, startTime, endTime;

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

            delay = calculateDelay(startTime, endTime);

            if(delay >= 90) {
                String startID = entries.get(startIndex).getLinkID();
                String endID = entries.get(endIndex).getLinkID();

                if(startID.equals("-10")) {
                    startID = mapLinkID(startID, startIndex);
                }
                if(endID.equals("-10")) {
                    endID = mapLinkID(endID, endIndex);
                }
                if(!startID.equals(endID)) {
                    outputData[2] = entries.get(startIndex).getLatitude();
                    outputData[3] = entries.get(startIndex).getLongitude();
                    outputData[4] = startID;
                    outputData[5] = entries.get(startIndex).getTime();
                    outputData[6] = "0";
                    outputData[9] = entries.get(endIndex).getLatitude();
                    outputData[10] = entries.get(endIndex).getLongitude();
                    outputData[11] = endID;
                    outputData[12] = entries.get(endIndex).getTime();
                    outputData[13] = String.valueOf(delay);

                    try {
                        WriteCsvFile output = new WriteCsvFile();
                        output.writeCsvFile(outputData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    private float calculateDelay(float startTime, float endTime) {
        float delay, startSecond, endSecond, startHour, endHour, startMinute, endMinute;

        startSecond = startTime % 100;
        startMinute = (int) (startTime / 100) % 100;
        startHour = (int) (startTime /10000) % 100;
        endSecond = endTime % 100;
        endMinute = (int) (endTime / 100) % 100;
        endHour = (int) (endTime /10000) % 100;

        delay = ((endHour * 3600) + (endMinute * 60) + endSecond) -
                ((startHour * 3600) + (startMinute * 60) + startSecond);
        return delay;
    }

    private String mapLinkID(String ID, int index) {
        int forwardCount, backCount;
        String forwardID, backID;

        String nearestID;

        forwardCount = 0;
        backCount = 0;

        for(int i= index + 1; i< index + 9; i++) {
            forwardID = entries.get(i).getLinkID();
            forwardCount++;
            if(!forwardID.equals("-10")) {
                break;
            }
        }

        for(int j = index - 1 ; j > index - 9; j--) {
            backID = entries.get(j).getLinkID();
            backCount++;
            if(!backID.equals("-10")) {
                break;
            }
        }

        if(forwardCount <= backCount) {
            nearestID = entries.get(index + forwardCount).getLinkID();
        }
        else {
            nearestID = entries.get(index - backCount).getLinkID();
        }
        return nearestID;
    }
}

