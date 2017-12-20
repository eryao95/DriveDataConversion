package com.company;

public class Config {
    public static final int TIME_FOR_DELAY = 90;
    public static final int SPEED_FOR_DELAY = 5;
    public static final int BUFFER_FOR_LINK_CHANGE = 10;
    public static final String CONGESTION = "-1";
    public static final String RULE_ONE = "1";  //speed condition met but linkID did not change even after buffer time
    public static final String RULE_TWO = "2";  //speed condition met but linkID changes only after buffer time
    public static final String RULE_THREE = "3";    //speed condition met and linkID changes

}
