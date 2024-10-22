package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code Model.Portfolio} class is a class that represents a stock portfolio,
 * a place to keep track of your stocks.
 */
public class Portfolio implements GeneralPortfolio {
  private final String name;
  private HashMap<String, Integer> stocks;

  /**
   * constructor to create a new Model.Portfolio with names and stocks.
   *
   * @param name  is the name of the Model.Portfolio.
   * @param stocks is how many stocks/shares of the company that you own.
   */
  public Portfolio(String name, HashMap<String, Integer> stocks) {
    this.name = name;
    this.stocks = stocks;
  }

  /**
   * Consturtor to intitlize a Portoflio.
   */
  public Portfolio() {
    this.name = "";
    this.stocks = new HashMap<String, Integer>();
  }


  /**
   * gets the name of the Model.Portfolio.
   *
   * @return string name of the Model.Portfolio.
   */
  public String getName() {
    return name;
  }

  /**
   * gives the monetary value of the Model.Portfolio on a given day.
   *
   * @param date is the specific day in which one wants to know the value of.
   * @return double which is the value of the Model.Portfolio on the day.
   */
  public double valueOfPortfolio(String date) {
    double price = 0.0;
    double value = 0.0;
    List<String> tickers = new ArrayList<String>(stocks.keySet());
    BufferedReader reader;
    String pathToTicStock;
    String line;
    for (String tick : tickers) {
      pathToTicStock = "res/" + tick + "csvfile.csv";
      try {
        reader = new BufferedReader(new FileReader(pathToTicStock));
        while ((line = reader.readLine()) != null) {
          String[] fields = line.split(",");
          if (fields[0].trim().equals(date)) {
            price = Double.parseDouble(fields[4]);
            break;
          }
        }
        value += price * stocks.get(tick);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return value;
  }

}


