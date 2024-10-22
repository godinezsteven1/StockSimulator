package controller;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import model.AdvPortfolioInterface;
import model.AdvancePortfolio;
import model.PortBalImpl;
import model.PortBalance;
import model.StockModel;
import model.StockTransactionImpl;
import view.ViewAdvancedImpl;
import view.ViewAdvanced;
import view.View;

import static java.lang.Math.toIntExact;

/**
 * Abstract base class for implementations of {@link APIController}. This class
 * contains all the method definitions that are common to the concrete
 * implementations of the {@link APIController} interface. A new implementation of
 * the interface has the option of extending this class, or directly
 * implementing all the methods.
 */
abstract class AbstractController implements APIController {
  StockModel model;
  View view;
  Readable in;

  /**
   * Constusctor that connects to the model, view, and readable to run the Program.
   *
   * @param stockModel the model used to implements features related to Stocks.
   * @param view       is to display options and features to the user.
   * @param in         takes in inputs/info from the user to use for implementation.
   */
  AbstractController(StockModel stockModel, View view, Readable in) {
    this.model = stockModel;
    this.view = view;
    this.in = in;

  }

  /**
   * calculates the gain or loss of a stock.
   */
  protected void doGainLoss() {
    String tickerC;
    Scanner scanner = new Scanner(in);

    view.displayEnterTicker();
    tickerC = scanner.next();

    view.display("Enter starting year for stock to buy : \n");
    String year = scanner.next();
    view.display("Enter starting month make sure its in mm format.\n"
            + "If the month is a single digit number, add a 0 at the beginning: \n");
    String month = scanner.next();
    view.display("Enter starting day: make sure its in dd format.\n"
            + "If the day is a single digit number, add a 0 at the beginning: \n");
    String day = scanner.next();
    String startingDate = year + "-" + month + "-" + day;

    view.display("Enter ending year for stock to buy : \n");
    String year1 = scanner.next();
    view.display("Enter ending month make sure its in mm format.\n"
            + "If the month is a single digit number, add a 0 at the beginning: \n");
    String month1 = scanner.next();
    view.display("Enter ending day: make sure its in dd format.\n"
            + "If the day is a single digit number, add a 0 at the beginning: \n");
    String day1 = scanner.next();
    String endingDate = year1 + "-" + month1 + "-" + day1;

    view.display(model.gainOrLoss(tickerC, startingDate, endingDate));
  }

  /**
   * calculates the moving day of a stock.
   */
  protected void doMoving() {
    String tickerC;
    Scanner scanner = new Scanner(in);

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
  }


  /**
   * calculates the moving day for a stock.
   */
  protected void doCrossing() {
    String tickerC;
    Scanner scanner = new Scanner(in);

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
  }

  /**
   * creates and saves the portfolio made by a user.
   */
  protected void createPortfolio() {
    String portfolioName;
    ViewAdvanced view = new ViewAdvancedImpl();
    Scanner scanner = new Scanner(in);

    view.display("Enter portfolio name: \n");
    portfolioName = scanner.next();
    view.display("enter filepath for the portfolio: "
            + "in Program options: out, res, src: \n");
    String filePath = scanner.next();
    AdvPortfolioInterface userPort = new AdvancePortfolio(portfolioName);
    userPort.savePorts(filePath);
  }

  /**
   * checks if there is a portfolio that exisits in a file location made by user.
   *
   * @param pf the file location where the portfolio exisits.
   * @return boolean if there is a portfolio that exisits in a file location made by user.
   */
  protected boolean proforlioExist(File pf) {
    return (pf.exists());
  }

  /**
   * ends the StockProgram when user hits 'q'.
   */
  protected void endProgram() {
    System.exit(0);
  }

  /**
   * creates stock from given information by going through csv file.
   *
   * @param ticker takes in string ticker.
   * @param date   takes in date in format 'yyyy-mm-dd'.
   * @return closing price of a given stock at a given day
   */
  public double getClosingPriceFromCSV(String ticker, String date) {
    String pathToCsv = "res/" + ticker + "csvfile.csv";
    double closingPrice = 0.0;
    boolean dateFound = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(pathToCsv))) {
      String rowline;
      while ((rowline = reader.readLine()) != null) {
        String[] fields = rowline.split(",");
        if (date.equals(fields[0])) {
          closingPrice = Double.parseDouble(fields[4]);
          dateFound = true;
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    if (!dateFound) {
      view.display("Date does not exist for: " + ticker);
      throw new IllegalArgumentException("Date does not exist for: " + ticker);
    }
    return closingPrice;
  }

  /**
   * adds/buys a stock to a portfolio.
   */
  public void doBuy() {
    Scanner scanner = new Scanner(in);
    view.display("Enter Ticker: \n");
    String tickerName = scanner.next();
    view.display("Enter year for stock to buy : \n");
    String year = scanner.next();
    view.display("Enter month make sure its in mm format.\n" +
            "If the month is a single digit number, add a 0 at the beginning: \n");
    String month = scanner.next();
    view.display("Enter day: make sure its in dd format.\n" +
            "If the day is a single digit number, add a 0 at the beginning: \n");
    String day = scanner.next();
    String date = year + "-" + month + "-" + day;
    view.display("Enter # of shares: \n");
    String sharesAsString = scanner.next();

    try {
      int shares = Integer.parseInt(sharesAsString);
      if (shares < 1) {
        throw new NumberFormatException();
      }
      double closingPrice = getClosingPriceFromCSV(tickerName, date);
      StockTransactionImpl buyStock = new StockTransactionImpl(tickerName, date, closingPrice);

      view.display("Closing: $" + closingPrice + "\n");
      view.display("Total cost: $" + buyStock.totalStockCost(shares) + "\n");
      view.display("Choose portfolio you want to add to: \n");
      String portfolioName = scanner.next();
      view.display("Location to where you saved " + portfolioName + ": \n");
      String fileLoco = scanner.next();
      AdvancePortfolio portfolio = new AdvancePortfolio(portfolioName);
      portfolio.addStock(buyStock, shares);
      portfolio.addSavePorts(fileLoco);
    } catch (NumberFormatException e) {
      view.displayNumDaysInvalidInput();
    }
  }

  /**
   * calculates the portfolio value the user wants to find on a given date.
   */
  public void doPortValue() {
    ViewAdvanced view = new ViewAdvancedImpl();
    Scanner scanner = new Scanner(in);
    view.display("Enter Portfolio name: \n");
    String portfolioName = scanner.next();
    view.display("Enter Portfolio location: \n");
    String portfolioLocation = scanner.next();
    view.display("Enter year for portfolio value: \n");
    String year = scanner.next();
    view.display("Enter month make sure its in mm format.\n" +
            "If the month is a single digit number, add a 0 at the beginning: \n");
    String month = scanner.next();
    view.display("Enter day: make sure its in dd format.\n" +
            "If the day is a single digit number, add a 0 at the beginning: \n");
    String day = scanner.next();
    String date = year + "-" + month + "-" + day;
    String pathToPortfolio = portfolioLocation + "/" + portfolioName + ".xml";
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(new File(pathToPortfolio));
      XPathFactory xPathfactory = XPathFactory.newInstance();
      XPath xpath = xPathfactory.newXPath();

      NodeList stockNodes = (NodeList) xpath.evaluate("//stock", document,
              XPathConstants.NODESET);

      double portValue = 0.0;
      for (int i = 0; i < stockNodes.getLength(); i++) {
        Node stockNode = stockNodes.item(i);
        String stockDate = xpath.evaluate("date", stockNode);
        if (stockDate.compareTo(date) <= 0) {
          double closingPrice = Double.parseDouble(xpath.evaluate("close-price",
                  stockNode));
          Double shares = Double.parseDouble(xpath.evaluate("Amount-of-stock", stockNode));
          portValue += closingPrice * shares;
        }
      }
      view.display("Total portfolio value on " + date + ": $" + portValue + "\n");

    } catch (ParserConfigurationException | SAXException | IOException
             | XPathExpressionException e) {
      e.printStackTrace();
    }
  }

  /**
   * sells a stock from a portiflio that a user owns.
   */
  public void doSell() {
    Scanner scanner = new Scanner(in);
    view.display("Enter Ticker: \n");
    String ticker = scanner.next();
    view.display("Enter year of stock to sell : \n");
    String year = scanner.next();
    view.display("Enter month make sure its in mm format.\n" +
            "If the month is a single digit number, add a 0 at the beginning: \n");
    String month = scanner.next();
    view.display("Enter day: make sure its in dd format.\n" +
            "If the day is a single digit number, add a 0 at the beginning: \n");
    String day = scanner.next();
    String date = year + "-" + month + "-" + day;
    view.display("Enter number of shares to sell: \n");
    String sharesAsString = scanner.next();
    try {
      int shares = Integer.parseInt(sharesAsString);
      if (shares < 1) {
        throw new NumberFormatException();
      }
      double closingPrice = getClosingPriceFromCSV(ticker, date);
      StockTransactionImpl sellStock = new StockTransactionImpl(ticker, date, closingPrice);
      view.display("Name of portfolio you want to sell from: \n");
      String portfolioName = scanner.next();
      view.display("Location of  portfolio you want to sell from: \n");
      String portfolioLocation = scanner.next();
      AdvancePortfolio portfolio = new AdvancePortfolio(portfolioName); // empty
      AdvPortfolioInterface userLoadedPortfolio = portfolio.loadPortfolio(portfolioLocation,
              portfolioName); // portfolio obj
      userLoadedPortfolio.sellRemoveStock(date, ticker, shares, portfolioLocation);


    } catch (NumberFormatException e) {
      view.displayNumDaysInvalidInput();
    }


  }

  /**
   * finds returns the composition of a Portfolio.
   */
  public void doComposition() {
    ViewAdvanced view = new ViewAdvancedImpl();
    Scanner scanner = new Scanner(in);
    view.display("Enter Portfolio name: \n");
    String portfolioName = scanner.next();
    view.display("Enter Portfolio location: \n");
    String portfolioLocation = scanner.next();
    view.display("Enter year : \n");
    String year = scanner.next();
    view.display("Enter month make sure its in mm format.\n" +
            "If the month is a single digit number, add a 0 at the beginning: \n");
    String month = scanner.next();
    view.display("Enter day: make sure its in dd format.\n" +
            "If the day is a single digit number, add a 0 at the beginning: \n");
    String day = scanner.next();
    String date = year + "-" + month + "-" + day;

    AdvancePortfolio portfolio = new AdvancePortfolio(portfolioName);
    portfolio = portfolio.loadPortfolio(portfolioLocation, portfolioName);
    view.display(portfolio.displayComposition(date));


  }

  /**
   * finds the distribution of a portfolio.
   */
  public void doDistribution() {
    ViewAdvanced view = new ViewAdvancedImpl();
    Scanner scanner = new Scanner(in);
    view.display("Enter Portfolio name: \n");
    String portfolioName = scanner.next();
    view.display("Enter Portfolio location: \n");
    String portfolLocation = scanner.next();
    view.display("Enter year for portfolio distribution: \n");
    String year = scanner.next();
    view.display("Enter month make sure its in mm format.\n" +
            "If the month is a single digit number, add a 0 at the beginning: \n");
    String month = scanner.next();
    view.display("Enter day: make sure its in dd format.\n" +
            "If the day is a single digit number, add a 0 at the beginning: \n");
    String day = scanner.next();
    String date = year + "-" + month + "-" + day;
    AdvancePortfolio portfolio = new AdvancePortfolio(portfolioName);
    portfolio = portfolio.loadPortfolio(portfolLocation, portfolioName);
    view.display(portfolio.getDistributio(date));
  }


  /**
   * takes the user's budget and divides it/balanaces through the stocks in
   * an exisiting Portfolio.
   */
  public void doRebalance() {
    ViewAdvanced view = new ViewAdvancedImpl();
    Scanner scanner = new Scanner(in);

    view.display("Enter Portfolio name: \n");
    String portfolioName = scanner.next();
    view.display("Enter Portfolio location: \n");
    String portfolioLocation = scanner.next();
    AdvancePortfolio portfolio = new AdvancePortfolio(portfolioName);
    portfolio = portfolio.loadPortfolio(portfolioLocation, portfolioName);


    double total = 100.00;
    HashMap<String, Double> tickerValues = new HashMap<>();
    int totalStocksAmount = portfolio.getStocks().keySet().size();
    for (StockTransactionImpl stock : portfolio.getStocks().keySet()) {
      String ticker = stock.getTicker();
      String date = stock.getDate();
      view.display("Enter a percentage for " + ticker + " at date: " + date + "\n");
      double value = scanner.nextDouble();
      tickerValues.put(ticker, value);
      total -= value;
      totalStocksAmount--;
      view.display("You have  " + total + "% of 100% left for the rest of your stocks\n");
      view.display("You have " + totalStocksAmount
              + " StockTransaction objects left to process.\n");
    }
    view.display("Enter the Budget for " + portfolioName + ":\n");
    String budgetString = scanner.next();
    int budget = Integer.parseInt(budgetString);
    view.display("Successfully rebalanced!\n");
    PortBalance needsBalancePortfolio = new PortBalImpl(portfolio, budget, tickerValues,
            portfolioLocation);
    needsBalancePortfolio.rebalancePortfolio();

  }

  /**
   * takes in the info to make bar chart.
   */
  public void doChart() {
    //ViewAdvanced view = new ViewAdvancedImpl();
    Scanner scanner = new Scanner(in);
    //view.display("If you want to see the Portfolio ")
    view.display("Enter Portfolio name: \n");
    String portfolioName = scanner.next();
    view.display("Enter Portfolio location: \n");
    String portfolioLocation = scanner.next();


    view.display("Enter end year to see the graph of Portfolio: \n");
    String year = scanner.next();
    view.display("Enter end month make sure its in mm format.\n" +
            "If the month is a single digit number, add a 0 at the beginning: \n");
    String month = scanner.next();
    view.display("Enter end day: make sure its in dd format.\n" +
            "If the day is a single digit number, add a 0 at the beginning: \n");
    String day = scanner.next();
    String endDate = year + "-" + month + "-" + day;

    view.display("Enter starting year to see the graph of Portfolio: \n");
    String yearS = scanner.next();
    view.display("Enter starting month make sure its in mm format.\n" +
            "If the month is a single digit number, add a 0 at the beginning: \n");
    String monthS = scanner.next();
    view.display("Enter starting day: make sure its in dd format.\n" +
            "If the day is a single digit number, add a 0 at the beginning: \n");
    String dayS = scanner.next();
    String startDate = yearS + "-" + monthS + "-" + dayS;


    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);

    long daysBetweenDates = ChronoUnit.DAYS.between(start, end);

    int days = toIntExact(daysBetweenDates);


    AdvancePortfolio portfolio = new AdvancePortfolio(portfolioName);
    portfolio = portfolio.loadPortfolio(portfolioLocation, portfolioName);

    portfolio.displayBarChart(portfolio, days, start, end);




  }


}


