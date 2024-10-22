package controller;

import model.StockModel;

import model.Portfolio;
import view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The {@code AlphaVantageAPICallController} is a control class part of the mvc used
 * to program a stock implementation having features that mimic a stock simulator.
 */
public class AlphaVantageAPICallController implements APIController {
  private StockModel model;
  private View view;
  private Readable in;

  /**
   * Constructor that connects to the model, view, and input stream to run the program.
   *
   * @param model the model class that implements the features in the controller.
   * @param view  displays the text-based interface the user sees.
   * @param in    InputStream that takes in the inputs the user takes in.
   */
  public AlphaVantageAPICallController(StockModel model, View view, Readable in) {
    this.model = model;
    this.view = view;
    this.in = in;
  }

  /**
   * executes the operations that the user inputs in the StockProgram and calls the API once..
   */
  public void execute() {
    Scanner scanner = new Scanner(in);
    int amountOfLines = 0;
    int days;
    String ticker;
    String tickerC;
    String portfoilioName;
    Portfolio portfolio;
    String apiKey = "CS7FSV6QPFQOZP1A";
    String baseURL = "https://www.alphavantage.co/query?function=TIME_SERIES_"
            + "DAILY&outputsize=full&datatype=csv";

    while (true) {
      view.displayWelcomeMessage();
      String input = scanner.next();

      if ("q".equalsIgnoreCase(input)) {
        break;
      } else if ("--help".equalsIgnoreCase(input)) {
        view.display("For all of the following features please make sure you have called"
                + " your desired stocks\n"
                + "if you enter 'q' the program will cease to run and kill progress :(."
                + "if you enter the character 'g', this will access the gain or loss feature: "
                + "sees if a specific company had a gain or loss in the given specific dates,\n "
                + " please first provide the desired company ticker, click enter \n "
                + " please provide starting date in the format 'yyyy-mm-dd', then click enter \n"
                + " please provide ending date in the format 'yyyy-mm-dd', then click enter \n"
                + "note that the most recent day is the end date, and the not so most recent "
                + "day is the starting day\n"
                + "if you enter 'm' you will access the moving day average feature: \n"
                + "the moving day average takes from date, x days going backwards, to return"
                + "a double\n please for this feature first provide companies ticker, click enter\n"
                + " please provide the date in the format 'yyyy-mm-dd', click enter\n please "
                + "provide the amount of days you want to go backwards, then click enter \n if you"
                + "enter 'c' you will access the crossover feature: the cross over feature examines"
                + "the crossover prices from a date range \n please first provide the companies "
                + " ticker and click enter\n please provide the start date, click enter \n please "
                + "provide the end date, click enter\n please provide the x day moving average\n"
                + "note that the most recent day is the end date, and the not so most recent "
                + "day is the starting day\n if you enter 'p1' you will access the portfolio "
                + "creation feature. this will create a portfolio with the given stocks you pass in"
                + "\n please first pass what you want to name the portfolio, click enter \n"
                + " then please provide ticker(s) of the company you want to add into "
                + "the portfolio. "
                + "\n When you are finished with providing ticker(s)"
                + ", click on 'f' to get the value of"
                +  "the Portfolio, which will then prompt you to enter in the"
                + " date, which will give you "
                + "the value of the PortFolio. When you're done using the"
                + " Portfolio's p1 features, press 'f'"
                + " These features are yet to be implemented correctly...:"
                + "\n if you enter 'p2' you will access a stock remove from portfolio feature \n"
                + " please first provide the companies ticker, click enter \n then provide"
                + " the portfolio's name\n if you enter 'p3' you will access the add stock to "
                + "portfolio feature\n please first provide the companies ticker click enter \n"
                + " please provide the portfolio's name.\n if you enter 'p4' you will access value"
                + " of portfolio on given day: this feature returns the monetary value in USD "
                + "of a portfolio on a given day\n please first enter the portfolio's name \n"
                + " then please enter the specified date in 'yyyy-mm-dd' format.\n"
                + "please continue using the program as normal");
        continue;
      }
      if ("g".equalsIgnoreCase(input)) {
        view.displayEnterTicker();
        tickerC = scanner.next();
        view.displayEnterStartingDate();
        String startingDate = scanner.next();
        view.displayEnterEndingDate();
        String endingDate = scanner.next();
        view.display(model.gainOrLoss(tickerC, startingDate, endingDate));
        continue;
      } else if ("m".equalsIgnoreCase(input)) {
        view.displayEnterTicker();
        tickerC = scanner.next();
        view.displayDate();
        String date = scanner.next();
        view.displayDays();
        String amountOfDaysAsString = scanner.next();
        try {
          int amountOfDaysAsInt = Integer.parseInt(amountOfDaysAsString);
          if (amountOfDaysAsInt < 1) {
            throw new NumberFormatException();
          }
          view.displayButHasNum(model.movingDayAverage(tickerC, date, amountOfDaysAsInt));
        } catch (NumberFormatException e) {
          view.displayNumDaysInvalidInput();
        }
      } else if ("c".equalsIgnoreCase(input)) {
        view.displayEnterTicker();
        tickerC = scanner.next();
        view.displayEnterStartingDate();
        String startingDate = scanner.next();
        view.displayEnterEndingDate();
        String endingDate = scanner.next();
        view.displayDays();
        String daysAsString = scanner.next();
        try {
          int daysAsInt = Integer.parseInt(daysAsString);
          if (daysAsInt < 1) {
            throw new NumberFormatException();
          }
          view.display(model.daysCrossover(tickerC, startingDate, endingDate, daysAsInt));
        } catch (NumberFormatException e) {
          view.displayNumDaysInvalidInput();
        }
      } else if ("p1".equalsIgnoreCase(input)) {
        view.displayNamingPortfolio();
        String portfolioName = scanner.next();
        HashMap<String,Integer> stocks = new HashMap<>();
        while (true) {
          view.display("enter in the tickers of the company name \n"
                  + "or press 'f' to stop entering tickers in the portfolio:");
          String inputTickers = scanner.next();
          if (inputTickers.equalsIgnoreCase("f")) {
            break;
          }
          view.display("enter the number of shares of that company");
          String inputSharesAsString = scanner.next();
          int inputSharesAsInt = Integer.parseInt(inputSharesAsString);
          stocks.put(inputTickers, inputSharesAsInt);
        }

        Portfolio savedP = new Portfolio(portfolioName, stocks);

        view.display("Enter in a date that you want to see the value of the portfolio, "
                + "or enter 'f' if you want to quit the Portfolio feature:");

        String date = scanner.next();

        while (!"f".equals(date)) {
          view.display("The value of the portflio on " + date + " is: ");
          view.displayButHasNum(savedP.valueOfPortfolio(date));
          view.display("\n Enter in a date that you want to see the value of the portfolio, "
                  + "or enter 'f' if you want to quit the Portfolio feature:");
          date = scanner.next();
        }


      }

      else {
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
                    writer.write(ticker);
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

  /**
   * returns if the portfolioFile exists in the inventory.
   *
   * @param pf is the Portfolio File
   * @return boolean if the portfolioFile exists in the inventory.
   */
  private boolean proforlioExist(File pf) {
    return (pf.exists());
  }
}



