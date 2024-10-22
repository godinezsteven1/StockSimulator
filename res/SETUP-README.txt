# Running the Stock Simulation Program Created by Steven Godinez and Alex Voung.

1. Please download the java jar zip.

2. place jar in a directory of your running preferred IDE or terminal.

3. Run the program from terminal using
java -jar Program.jar -text for TUI
or
java -jar Program.jar for GUI

# FOR GUI USAGE SCROLL TO LINE 127

# Creating a Portfolio in TUI

To create a portfolio with 3 different stocks 3 different dates : #SKIP TO LINE 90 TO USE EXISTING STUFF#

0. if you want to use existing stocks to ease program use, scroll down to line 23 to skip to step 4

1. Run the program as described above.

2. Enter amount of days to back track.

3. Enter desired Company ticker.

4. Repeat two more times with different tickers.

4. Enter 'p'

5. Enter a portfolio name of your choice

6. Enter one of the three in Program options to save: we recommend 'res'

7. Success! now lets add stocks

8. Enter 'buy'

9. Enter stock Ticker you called back or one we provided at the bottom of file or you can do
'GOOG'  'META'    'MMM'

10. Enter the year in yyyy format. Make sure it is a valid year that you called back or you can do
'2024'(for GOOG)   '2024'(for META)    '2024'(for MMM)

11.
Enter the month in mm format. Make sure it is a valid month that you called back or you can do
'06'(for GOOG)     '05' (for META)      '03'(for MMM)

12. Enter date in dd format. Make sure its a valid day that you called back and a date the stock market
was opened. or you can do
'06'(for GOOG)    '23' (for META)       '28'(for MMM)

13. Enter amount of shares you want to buy, Please enter only a whole number

14. Enter Portfolio name from line 25.

15. Enter Location from line 27.

16. repeat from step 8... 2 more times

17. Please feel free to add more stocks for the next step.

18. Now to get value at 2 different dates

19. Enter 'port-value'

20. Enter Portfolio name

21. Enter location

22. Enter the year in yyyy format. Make sure it is a valid year that you called back, or you can do
'2024'(for GOOG)   '2024'(for META)    '2024'(for MMM)

23.Enter the month in mm format. Make sure it is a valid month that you called back, or you can do
'06'(for GOOG)     '05' (for META)      '03'(for MMM)

24. Enter date in dd format. Make sure its a valid day that you called back and a date the stock market
was opened. or you can do
'06'(for GOOG)    '23' (for META)       '28'(for MMM)

25. Value! Repeat from 19 another time with a different date to get new values.


# NOTE # : quitting program in version 2.1.0 will save progress, just remember folder location.

# Supported Stocks (stocks that already exist)

The program supports the following stocks:

- GOOG: Available data from 2014-03-27 to 2024-06-06
- META: Available data from 2012-05-18 to 2024-06-06
- MMM: Available data from 2024-05-31 to 2024-06-06

Please note that the program may not be able to determine the value of a stock on a date for which data is not
available.
# EXISTING PORTFOLIO WITH STOCKS #
1. 'dp'
2. 'secret-folder'
3. 'res'
4. '2024'
5. '06'
6. '06'
_________
1. 'port-value'
2. 'secret-folder'
3. 'res'
4. '2024'
5. '05'
6. '31'
___________
1. 'comp'
2. 'secret-folder'
3. 'res'
4. '2024'
5. '06'
6. '06'
____________
1. 'ch'
2. 'secret-folder'
3. 'res'
4. '2024'
5. '06'
6. '06'
7. '2024'
8. '03'
9. '19'

--- GUI USAGE ---

# TO MAKE A PORTFOLIO
1. Click "Make Portfolio" button

2. Choose location of preference from the dropdown menu.

3. Enter desired portfolio name

4. Click 'Create'

5. Success! Portfolio created, click 'OK' in confirmation page to go back to main menu.

#TO GET STOCK API CALL
1. Enter Stock Ticker.

2. Enter amount of days, make sure it is a positive integer.

3. Success! call back succeeded, automatically returns you to main menu.

4. OR Click 'Back' to go back to main menu

#TO BUY A STOCK
1. Enter Stock from a dropdown menu.

2. Enter year from drop down menu.

3. Enter month from drop down menu.

4. Enter day from drop down menu.

5. Enter number of shares as a positive integer.

6. Enter portfolio name.

7. Enter Portfolio location from the dropdown menu.

8. Click 'Purchase'

9. Success! closing cost and total cost is displayed as confirmation.

10. Click 'Back' to go back to main menu

#TO SELL A STOCK
1. Enter Stock from a dropdown menu.

2. Enter year from drop down menu.

3. Enter month from drop down menu.

4. Enter day from drop down menu.

5. Enter number of shares as a positive integer.

6. Enter portfolio name.

7. Enter Portfolio location from the dropdown menu.

8. Click 'Sell'.

9. Success! Confirmation message displayed

10. Click 'Back' to go back to main menu

#TO GET VALUE AND COMPOSITION OF A FOLDER AT A GIVEN DATE.
1. Enter Portfolio name.

2. Enter Portfolio Location from the dropdown menu.

3. Select year from the dropdown menu.

4. Select month from the dropdown menu.

5. Select day from the dropdown menu.

6. Click 'Calculate'.

7.Success! Composition and Total value displayed on panel.

8. Click 'Back' to go back to main menu

#TO EXIT PROGRAM
1. If in a feature, click 'Back'.

2. Then/Else : Click 'Exit'.