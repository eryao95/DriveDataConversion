package com.company;

public class Config {
    public static final int UPPER_LIMIT_FOR_DELAY = 90;
    public static final int LOWER_LIMIT_FOR_DELAY = 5;
    public static final int SPEED_FOR_DELAY = 5;
    public static final int BUFFER_FOR_LINK_CHANGE = 10;
    public static final int TIME_TO_CALCULATE_AVERAGE_SPEED = 15; //time period to calculate average speed before/after delay
    public static final int LOWER_LIMIT_FOR_SPEED = 20; //speed limit to be considered low speed
    public static final double DISTANCE_LIMIT_DURING_DELAY = 15.0; //if distance travelled between start and end point of delay is more than limit, it is a congestion
    public static final double LIMIT_FOR_PERCENTAGE_OF_ZEROS = 50.0; //if the percentage of 0 is less than 80%, it is a congestion;
    public static final String CONGESTION = "-1"; //if delay time is more than upper limit of delay time.
    public static final String RULE_ONE = "1";  //speed condition met but linkID did not change even after buffer time
    public static final String RULE_TWO = "2";  //speed condition met but linkID changes during buffer time
    public static final String RULE_THREE = "3";    //speed condition met and linkID changes

}
