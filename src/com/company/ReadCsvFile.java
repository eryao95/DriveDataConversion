package com.company;

import com.csvreader.CsvReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static com.company.Config.*;

public class ReadCsvFile {
    private ArrayList<Entry> entries = new ArrayList<>();
    private int size;
    private String missingID = "-10";

    public ReadCsvFile() {
        size = 0;
    }

    //using CsvReader to read the csv file and create a new entry with each record
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

        if(size > TIME_FOR_DELAY) {
            checkSpeedRequirements(outputData, size);
        }
    }

    private void checkSpeedRequirements(String [] outputData, int size) {
        int startIndex, endIndex, speed;
        float delay, startTime, endTime;
        String startID, endID;
        boolean trafficLight;

        startIndex = 0;
        speed = 0;
        endIndex = 1;
        while(startIndex + TIME_FOR_DELAY < size) {
            for (int i = startIndex; i < size; i++) {
                speed = entries.get(i).getSpeed();
                if (speed > SPEED_FOR_DELAY && i != 0) {
                    endIndex = i - 1;
                    break;
                }
                else {
                    endIndex = i;
                }
            }

            startTime = entries.get(startIndex).getFloatTime();
            endTime = entries.get(endIndex).getFloatTime();

            delay = calculateDelay(startTime, endTime);

            startID = entries.get(startIndex).getLinkID();
            endID = entries.get(endIndex).getLinkID();

            if (startID.equals(missingID)) {
                startID = mapLinkID(startIndex);
            }
            if (endID.equals(missingID)) {
                endID = mapLinkID(endIndex);
            }

            if(delay > TIME_FOR_DELAY) {
                outputData[0] = "CONGESTION";
                outputData[2] = entries.get(startIndex).getLatitude();
                outputData[3] = entries.get(startIndex).getLongitude();
                outputData[4] = startID;
                outputData[5] = entries.get(startIndex).getTime();
                outputData[6] = "0";
                outputData[7] = "END";
                outputData[9] = entries.get(endIndex).getLatitude();
                outputData[10] = entries.get(endIndex).getLongitude();
                outputData[11] = endID;
                outputData[12] = entries.get(endIndex).getTime();
                outputData[13] = String.valueOf(delay);
                outputData[14] = CONGESTION;

                try {
                    WriteCsvFile output = new WriteCsvFile();
                    output.writeCsvFile(outputData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if (delay > 5 && delay <= TIME_FOR_DELAY){
                trafficLight = checkForNextNSeconds(endIndex);

                if (startID.equals(endID)) {
                    if(trafficLight) {
                        outputData[14] = RULE_TWO;
                    }
                    else {
                        outputData[14] = RULE_ONE;
                    }
                }
                else {
                    outputData[14] = RULE_THREE;
                }
                outputData[0] = "TRAFFIC LIGHT";
                outputData[2] = entries.get(startIndex).getLatitude();
                outputData[3] = entries.get(startIndex).getLongitude();
                outputData[4] = startID;
                outputData[5] = entries.get(startIndex).getTime();
                outputData[6] = "0";
                outputData[7] = "end";
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

            if(speed > SPEED_FOR_DELAY) {
                startIndex = endIndex + 2;
            }
            else {
                startIndex = endIndex + 1;
            }
        }
    }

    //check for next 10s to see if the vehicle changes linkID
    private boolean checkForNextNSeconds(int endIndex) {

        for(int i = endIndex; i<BUFFER_FOR_LINK_CHANGE; i++) {
            if(!entries.get(i).getLinkID().equals(entries.get(endIndex).getLinkID())) {
                return true;
            }
        }

        return false;

    }

    //calculate time taken between 2 points in seconds
    private float calculateDelay(float startTime, float endTime) {
        float delay, startSecond, endSecond, startHour, endHour, startMinute, endMinute;

        startSecond = startTime % 100;
        startMinute = (int) (startTime / 100) % 100;
        startHour = (int) (startTime / 10000) % 100;
        endSecond = endTime % 100;
        endMinute = (int) (endTime / 100) % 100;
        endHour = (int) (endTime /10000) % 100;

        delay = ((endHour * 3600) + (endMinute * 60) + endSecond) -
                ((startHour * 3600) + (startMinute * 60) + startSecond);
        return delay;
    }


    // missing LinkIDs to the closest linkID
    private String mapLinkID(int index) {
        int forwardCount, backCount;
        String forwardID = "", backID = "", nearestID;

        String correctID;

        forwardCount = 0;
        backCount = 0;

        //if the previous linkID is the same as the linkID after the missing point, missing point will be the same
        for(int i = index; i>0; i--) {
            if(!(entries.get(i).getLinkID().equals(missingID))) {
                backID = entries.get(i).getLinkID();
                break;
            }
        }

        for(int j = index; j<entries.size(); j++) {
            if(!(entries.get(j).getLinkID().equals(missingID))) {
                forwardID = entries.get(j).getLinkID();
                break;
            }
        }

        if(backID.equals(forwardID) && !backID.equals("")) {
            correctID = backID;
        }
        else {
            //if previous linkID != to next linkID, map to closest
            for(int i= index + 1; i < entries.size(); i++) {
                forwardID = entries.get(i).getLinkID();
                forwardCount++;
                if(!forwardID.equals(missingID)) {
                    break;
                }
            }

            for(int j = index - 1; j > 0; j--) {
                backID = entries.get(j).getLinkID();
                backCount++;
                if(!backID.equals(missingID)) {
                    break;
                }
            }

            if(forwardCount == 0) {
                nearestID = entries.get(index - backCount).getLinkID();
            }
            else if(backCount == 0) {
                nearestID = entries.get(index + forwardCount).getLinkID();
            }
            else if(forwardCount <= backCount) {
                nearestID = entries.get(index + forwardCount).getLinkID();
            }
            else {
                nearestID = entries.get(index - backCount).getLinkID();
            }

            correctID = nearestID;
        }

        entries.get(index).setLinkID(correctID);
        return correctID;
    }
}