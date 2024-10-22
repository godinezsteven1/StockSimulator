package controller;


import model.StockModel;

import model.Portfolio;

import view.ViewAdvanced;
import view.ViewAdvancedImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;


/**
 * represents a working Stock Controller that deals with Stocks the user uses.
 * to deal and trade with Stocks
 * {@code AdvanceStockController} represents the
 * operators to deal with stcks
 */
public class AdvanceStockController extends AbstractController {

  /**
   * Consturtor to connect to the model, view, and readable.
   *
   * @param stockModel the model used to implements features related to Stocks.
   * @param view       is to display options and features to the user.
   * @param in         takes in inputs/info from the user to use for implementation.
   */
  public AdvanceStockController(StockModel stockModel, ViewAdvanced view, Readable in) {
    super(stockModel, view, in);
  }


  /**
   * Executes the operations that the user inputs in the StockProgram and calls the API once.
   */
  @Override
  public void execute() {
    ViewAdvanced viewAdvanced = new ViewAdvancedImpl();
    int amountOfLines = 0;
    int days;
    String ticker;
    String tickerC;
    String portfoilioName;
    Portfolio portfolio;
    String apiKey = "NMBJV9I1JXOWWEZ6";
    String baseURL = "https://www.alphavantage.co/query?function=TIME_SERIES_"
            + "DAILY&outputsize=full&datatype=csv";

    while (true) {
      viewAdvanced.displayWelcomeMessage();
      Scanner scanner = new Scanner(in);
      String input = scanner.next();

      switch (input.toLowerCase()) {
        case "--help":
          viewAdvanced.displayAdvancedMenu();
          continue;
        case "q":
          this.endProgram();
          break;
        case "g":
          this.doGainLoss();
          continue;
        case "m":
          this.doMoving();
          continue;
        case "c":
          this.doCrossing();
          continue;
        case "p":
          this.createPortfolio();
          continue;
        case "port-value":
          this.doPortValue(); // date no match return 0
          continue;
        case "buy":
          this.doBuy();
          continue;
        case "sell":
          this.doSell();
          continue;
        case "comp":
          this.doComposition();
          continue;
        case "dp":
          this.doDistribution();
          continue;
        case "rebal":
          this.doRebalance();
          continue;
        case "ch":
          this.doChart();
          continue;

        default:

      }

      try {
        days = Integer.parseInt(input);
        if (days < 1) {
          throw new IllegalArgumentException("Days must be greater than 0/cannot be negative");
        }
      } catch (NumberFormatException e) {
        view.display("Invalid input. Please enter a number or 'q' to quit.");
        continue;
      }

      view.display("Enter Company ticker: ");
      ticker = scanner.next();


      try {
        String pathToTicStock = "res/" + ticker + "csvfile.csv";
        File portfolioFile = new File(pathToTicStock);
        if (proforlioExist(portfolioFile) && amountOfLines >= days) {
          try (BufferedReader reader = new BufferedReader(new FileReader(pathToTicStock))) {
            view.display("Already have the stock info in the database!!!!!!");
            continue;
          } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + pathToTicStock, e);
          }
        } else {
          URL url = new URL(baseURL + "&symbol=" + ticker + "&apikey=" + apiKey);
          try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                  url.openStream()));
               BufferedWriter writer = new BufferedWriter(new FileWriter("res/" + ticker
                       + "csvfile.csv"))) {
            String line;
            String firstLine = null;

            while ((line = reader.readLine()) != null) {
              if (amountOfLines < (days + 1)) {
                if (amountOfLines > 0) {
                  line += "," + ticker;
                  writer.write(line);
                  writer.newLine();
                }
                amountOfLines++;
              }
              firstLine = line;
            }
            if (firstLine != null) {
              writer.write(firstLine);
            }
          } catch (IOException e) {
            throw new IllegalArgumentException("No price Data found for: " + ticker
                    + ", or ticker does not exist, please check again.");
          }
        }
      } catch (IOException e) {
        throw new RuntimeException("AIP Key is invalid or has changed " + apiKey, e);
      }
    }
  }
}
