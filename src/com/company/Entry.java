package com.company;

public class Entry {
    private String pLat;
    private String north;
    private String pLong;
    private String east;
    private String bearings;
    private String time;
    private String speed;
    private String date;
    private String linkID;
    private String accuracy;


    public Entry(String pLat, String north, String pLong, String east, String bearings, String time, String date,
                 String accuracy, String speed, String linkID) {
        this.pLat = pLat;
        this.north = north;
        this.pLong = pLong;
        this.east = east;
        this.bearings = bearings;
        this.time = time;
        this.date = date;
        this.accuracy = accuracy;
        this.speed = speed;
        this.linkID = linkID;
    }

    public String getLatitude() {
        String parts[] = new String[3];
        int degree;
        float second, minute, decimalDegrees;

        parts = pLat.split(":");
        degree = Integer.parseInt(parts[0]);
        minute = Float.parseFloat(parts[1]);
        second = Float.parseFloat(parts[2]);
        decimalDegrees = degree + (minute/60) + (second/3600);

        return String.valueOf(decimalDegrees);
    }

    public String getLongitude() {
        String parts[] = new String[3];
        int degree;
        float second, minute, decimalDegrees;

        parts = pLong.split(":");
        degree = Integer.parseInt(parts[0]);
        minute = Float.parseFloat(parts[1]);
        second = Float.parseFloat(parts[2]);
        decimalDegrees = degree + (minute/60) + (second/3600);

        return String.valueOf(decimalDegrees);
    }

    public float getIntTime() {
        return Float.parseFloat(time);
    }

    public String getTime() {
        return time;
    }

    public int getSpeed() {
        return Integer.parseInt(speed);
    }

    public String getDate() {
        return date;
    }

    public String getLinkID() {
        return linkID;
    }

    public String getEast() {
        return east;
    }

    public String getNorth() {
        return north;
    }

    public String getUnknown() {
        return bearings;
    }

    public String getUnknown2() {
        return accuracy;
    }
}
