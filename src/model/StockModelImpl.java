package model;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * The {@code Model.StockModelImpl} class represents the model methods of
 * how a stock should behave.
 * Stocks, or more specifically this class would need a ticker,
 * and dates to access the CSV file.
 */
public class StockModelImpl implements StockModel {

  /**
   * fixes the order of date with LocalDate class, makes sure end date is the oldest one as
   * CSV file works from most recent to least recent. If wrong functions flips them.
   *
   * @param startingDate is the younger date, or the starting date.
   * @param endDate      is the oldest date, or the most recent date.
   * @return returns a LocalDate of the correctly ordered date parameters.
   */
  private static LocalDate[] fixDateOrder(String startingDate, String endDate) {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate startingDates = LocalDate.parse(startingDate, dateFormat);
    LocalDate endDates = LocalDate.parse(endDate, dateFormat);
    //checks if the most recent date is older than the starting date
    // & fixes if dates is flipped
    if (endDates.isBefore(startingDates)) {
      LocalDate temp = startingDates;
      startingDates = endDates;
      endDates = temp;
    }

    return new LocalDate[]{startingDates, endDates};
  }

  /**
   * gives whether the company had gain or loss in the speicifc dates,
   * by subtracting opening - clothes.
   *
   * @param ticker       is the ticker of the company.
   * @param startingDate is the start of date you want to know in the format "yyyy-mm-dd".
   * @param endDate      is the end of date you want to know in the format "yyyy-mm-dd".
   * @return a double with the average cost between starting day and end date.
   */
  @Override
  public String gainOrLoss(String ticker, String startingDate, String endDate) {
    LocalDate[] dates = fixDateOrder(startingDate, endDate);
    LocalDate start = dates[0];
    LocalDate end = dates[1];
    String pathToCsv = "res/" + ticker + "csvfile.csv";
    double openingStartDayPrice = 0.0;
    double endDayClosePrice = 0.0;
    double gainOrLoss = 0.0;

    try {
      BufferedReader reader = new BufferedReader(new FileReader(pathToCsv));
      String rowline;
      while ((rowline = reader.readLine()) != null) {
        String[] fields = rowline.split(",");
        if (start.toString().equals(fields[0])) {
          openingStartDayPrice = Double.parseDouble(fields[1]);
        }
        if (end.toString().equals(fields[0])) {
          endDayClosePrice = Double.parseDouble(fields[4]);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    gainOrLoss = endDayClosePrice - openingStartDayPrice;
    if (gainOrLoss < 0.0) {
      return ticker + " stock lost value, more specifically: " + gainOrLoss;
    } else if (gainOrLoss > 0.0) {
      return ticker + " stock gained value, more specifically: " + gainOrLoss + " yay! :) :P XD";
    } else {
      return ticker + " stock remained the exact same, sham :|";
    }
  }

  /**
   * gives the dates that are crossover days in the speicifc dates.
   *
   * @param ticker       is the ticker of the company.
   * @param startingDate is the start of date you want to know in the format "yyyy-mm-dd".
   * @param endDate      is the end of date you want to know in the format "yyyy-mm-dd".
   * @param days         is the number of days to use for moving day calcualtions.
   * @return a list of string of the days that are crossover.
   */
  public String daysCrossover(String ticker, String startingDate, String endDate,
                              int days) {
    LocalDate[] dates = fixDateOrder(startingDate, endDate);
    LocalDate start = dates[0];
    LocalDate end = dates[1];
    String pathToCsv =
            "res/" + ticker + "csvfile.csv";
    List<String> crossoverResults = new ArrayList<>();
    Queue<Double> prices = new LinkedList<>();

    try {
      BufferedReader reader = new BufferedReader(new FileReader(pathToCsv));
      String rowline;
      boolean range = false;
      double closeCounter = 0.0;
      double openCounter = 0.0;
      double averageOfToday = 0.0;
      double total = 0.0;

      // iterate over file
      while ((rowline = reader.readLine()) != null) {
        String[] fields = rowline.split(",");
        // in format
        LocalDate csvDate = LocalDate.parse(fields[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (csvDate.isBefore(end)) {
          // if so now in range
          range = true;
        }
        if (range) {
          // get closing price of given day in range
          closeCounter = Double.parseDouble(fields[4]);
          // get open price of given day in range
          openCounter = Double.parseDouble(fields[1]);
          // get average price of the day in range
          averageOfToday = (closeCounter + openCounter) / 2.0;
          // add to total  & queue
          total += averageOfToday;
          prices.add(averageOfToday);
          // make sure size is days amount big
          if (prices.size() > days) {
            // if passing remove something kind of like a stack(but its a queue not a stack)
            total -= prices.remove();
          }
        }
        // now we are out of range
        if (csvDate.equals(start)) {
          range = false;
        }
      }

      //goes through the loop again and compares it with the averageofToday
      reader = new BufferedReader(new FileReader(pathToCsv));
      while ((rowline = reader.readLine()) != null) {
        String[] fields = rowline.split(",");
        LocalDate csvDate = LocalDate.parse(fields[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //checks if the date is before the oldest date (top of the bottom read in file)
        if (csvDate.isBefore(end)) {
          //if it is now in range
          range = true;
        }
        //calculates each day's average
        if (range) {
          //gets the closing price of the day
          closeCounter = Double.parseDouble(fields[4]);
          //gets the opeining price of the day
          openCounter = Double.parseDouble(fields[1]);
          //calculates the average of the day with the closing and opening price
          averageOfToday = (closeCounter + openCounter) / 2.0;
          if (prices.size() == days && averageOfToday > total / days) {
            crossoverResults.add(fields[0].substring(ticker.length()));
          }
          // add to total and to the queue
          total += averageOfToday;
          prices.add(averageOfToday);
          // take oldest from queue away
          if (prices.size() > days) {
            total -= prices.remove();
          }
        }
        // out of range
        if (csvDate.equals(start)) {
          range = false;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    String stringResult = String.join(", \n", crossoverResults);
    return stringResult;
  }

  /**
   * gives the movingDay average of the company in the speciifc date and how many days backwards.
   *
   * @param ticker  is the ticker of the company.
   * @param endDate is the most recent date.
   * @param days    is how many days to go backwards starting from the endDate.
   * @return the movingDay average.
   */
  public double movingDayAverage(String ticker, String endDate, int days) {
    String pathToCsv = "res/" + ticker + "csvfile.csv";
    double total = 0.0;

    if (days < 1) {
      throw new IllegalArgumentException("days cannot be negative: " + days);
    }

    LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    try {
      BufferedReader reader = new BufferedReader(new FileReader(pathToCsv));
      String rowline;
      double lineCounter = 0.0;
      boolean foundDate = false;

      while ((rowline = reader.readLine()) != null) {
        String[] fields = rowline.split(",");
        LocalDate csvDate = LocalDate.parse(fields[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (csvDate.equals(end)) {
          foundDate = true;
        }

        if (foundDate) {
          total += Double.parseDouble(fields[4]);
          lineCounter++;
        }

        if (lineCounter == days) {
          foundDate = false;
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return total / days;
  }
}

