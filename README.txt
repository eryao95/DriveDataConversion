This program is designed to differentiate Traffic Lights and Congestion from delays using 5 Rules.

Rule 1: The time of delay at which the speed of the vehicle is below a specified amount.
Rule 2: LinkID changes
Rule 3: Distance travelled during slowdown
Rule 4: Percentage of time at which the vehicle is stationary.
Rule 5: Speed of vehicle before and after slow down.

Using of program:
In Main, please change C:\Users\Bo Yang\Desktop\DriveDataConversion\Files\ to the correct file path of the file that you wish to read.
In WriteCsvFile, please change outputFile.csv to the desired name of output.

Explanation of Code:
The Entry class stores the values of each column in the input folder of each record.

The WriteCsvFile class writes the output of the program in the desired format.

The Config class is a file to store all the configurables variables so that values can be changed easily.

In ReadCsvFile:
readCsvFile : reads the record line by line.
checkSpeedRequirements: uses the values in the Entry class and parses through the 5 rules to determine if the vehicle is in a congestion or a traffic light
calculateSpeedBeforeAndAfter: use to compare the speed of the vehicle before and after the slow down
calculatePercentage: use to calculate the percentage of time of which the vehicle is stationary.
checkForNextNSeconds: use to check if the vehicle changes link ID within the buffer time.
calculateDelay: use to calculate the delay of the vehicle in seconds.
mapLinkID: use to map missing Link ID to the predicted Link ID.
createHashMap: use to create a hashmap to store the data of the road categories.

Format of output data:
TRAFFIC LIGHT/CONGESTION, DRIVE ID, LATITUDE, LONGITUDE, LINK ID, TIME, 0
END, DRIVE ID, LATITUDE, LONGITUDE, LINK ID, TIME, DELAY, CODE OF CONGESTION/TRAFFIC LIGHTS, CONGESTION DUE TO PERCENTAGE, CONGESTION DUE TO DISTANCE, CONGESTION DUE TO SPEED