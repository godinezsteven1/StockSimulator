package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import view.ViewAdvanced;
import view.ViewAdvancedImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

/**
 * represents a new Portoflio with advanced features, which is saved and stored.
 * {@code AdvancePortfolio} is a Portoflio to store the user's stocks.
 */
public class AdvancePortfolio implements AdvPortfolioInterface {
  private String name;
  private HashMap<StockTransactionImpl, Double> stocks;

  /**
   * Consturtor that only records the name from the user to make a new Portfolio.
   * @param name is the name of the Portfolio.
   */
  public AdvancePortfolio(String name) {
    this.name = name;
    this.stocks = new HashMap<>();
  }

  /**
   * public construcotor that initially sets the AdvancePortfolio at its starting point.
   */
  public AdvancePortfolio() {
    this.stocks = new HashMap<>();
  }

  /**
   * Consturtor that only records and saves info on the stocks it will take in,
   * and its Portfolio name.
   * @param name is the name of the Portfolio.
   * @param stocks is the stocks inside the Portfolio.
   */
  public AdvancePortfolio(String name, HashMap<StockTransactionImpl, Double> stocks) {
    this.name = name;
    this.stocks = stocks;
  }

  /**
   *gets the map of the StockTarnsaction and the number of shares for the transaction.
   * @return HashMap of the StockTarnsaction and the number of shares for that.
   *
   */
  public HashMap<StockTransactionImpl, Double> getStocks() {
    return stocks;
  }

  /**
   * Adds a stock to the Portoflio.
   * @param stock is the stock to be added/stored in the Portoflio.
   * @param shares is how many shares of that stock the user has.
   */
  public void addStock(StockTransactionImpl stock, double shares) {
    if (this.stocks.containsKey(stock)) {
      this.stocks.compute(stock, (k, currentShares) -> currentShares + shares);
    } else {
      this.stocks.put(stock, shares);
    }
  }

  /**
   * Gets the name of the Portfolio.
   * @return String name of the Portfolio.
   */
  public String getName() {
    return name;
  }


  /**
   * creates an XML based off of portfolio info.
   *
   * @param filePlace is the folder where the user wants to store the Portfolio..
   */
  public void savePorts(String filePlace) {
    String filePath = filePlace + "/" + getName() + ".xml";
    try {
      DocumentBuilderFactory docBuild = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = docBuild.newDocumentBuilder();
      Document doc = dBuilder.newDocument();

      Element portfolioTag = doc.createElement("portfolio");
      doc.appendChild(portfolioTag);

      Element portfolioName = doc.createElement("portfolioName");
      portfolioName.appendChild(doc.createTextNode(getName()));
      portfolioTag.appendChild(portfolioName);

      Element createStock = doc.createElement("stocks");
      portfolioTag.appendChild(createStock);

      if (stocks != null) {
        for (Map.Entry<StockTransactionImpl, Double> stock : stocks.entrySet()) {
          Element stockStuff = doc.createElement("stock");
          String[] stockParts = stock.getKey().toString().split(",");
          stockStuff.setAttribute("ticker", stockParts[0]);
          stockStuff.setAttribute("date", stockParts[1]);
          stockStuff.setAttribute("close-price", stockParts[2]);
          stockStuff.setAttribute("Amount-of-stock", stock.getValue().toString());

          createStock.appendChild(stockStuff);
        }
      }

      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer trans = tFactory.newTransformer();
      trans.setOutputProperty(OutputKeys.INDENT, "yes");
      trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(filePath));
      trans.transform(source, result);

      ViewAdvanced view = new ViewAdvancedImpl();
      view.display("Successfully saved portfolio: " + getName() + " to " + filePlace + "\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }




  /**
   * Removes/sells a stock from an existing Portfolio in the xml file.
   * @param date   id the date of the stock you want to sell.
   * @param ticker is the company that they are selling the stocks from.
   * @param shares is the number of shares
   */
  public void sellRemoveStock(String date, String ticker, double shares, String filePath) {
    ViewAdvanced view = new ViewAdvancedImpl();
    for (Map.Entry<StockTransactionImpl, Double> entry : this.stocks.entrySet()) {
      StockTransactionImpl stockTransaction = entry.getKey();
      double existingShares = entry.getValue();
      if (stockTransaction.getTicker().equals(ticker) && stockTransaction.getDate().equals(date)) {
        double remainingShares = existingShares - shares;
        if (remainingShares <= 0) {
          this.stocks.remove(stockTransaction);
          view.display("Sold all shares of " + ticker);
        } else {
          this.stocks.put(stockTransaction, remainingShares);
          view.display("Sold " + shares + " shares of " + ticker + "\n");
        }
        removeSavePorts(filePath);
        break;
      }
    }
  }


  /**
   * returns the value of the total Portfolio from its stocks
   * at the speific date.
   * @param date is the date the user wants to see the value of the Portoflio.
   * @return double which is the value of the Portfolio on the stock's date.
   */
  public double valueOfPortfolio(String date) {
    double price = 0.0;
    double value = 0.0;
    List<StockTransactionImpl> tickers = new ArrayList<>(stocks.keySet());
    BufferedReader reader;
    String pathToTicStock;
    String line;
    for (StockTransactionImpl tick : tickers) {
      pathToTicStock = "res/" + tick.getTicker() + "csvfile.csv";
      try {
        reader = new BufferedReader(new FileReader(pathToTicStock));
        while ((line = reader.readLine()) != null) {
          String[] fields = line.split(",");
          if (fields[0].trim().equals(date)) {
            price = Double.parseDouble(fields[4]); // closing
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


  /**
   * saves Portfolios at the given filefolder location, if same stoc is detected,
   * combines them into one for easier processing the XML.
   * @param filePlace is the filefodler location that the portfolio is stored in.
   */
  public void addSavePorts(String filePlace) {
    ViewAdvanced view = new ViewAdvancedImpl();
    String filePath = filePlace + "/" + getName() + ".xml";
    try {
      DocumentBuilderFactory docBuild = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = docBuild.newDocumentBuilder();
      Document doc = dBuilder.parse(new File(filePath));
      Element stocksElement = (Element) doc.getElementsByTagName("stocks").item(0);

      if (stocks != null) {
        for (Map.Entry<StockTransactionImpl, Double> stock : stocks.entrySet()) {
          NodeList existingStocks = stocksElement.getElementsByTagName("stock");
          boolean stockExists = false;

          for (int i = 0; i < existingStocks.getLength(); i++) {
            Element existingStock = (Element) existingStocks.item(i);
            String existingTicker = existingStock.getElementsByTagName("ticker").item(0)
                    .getTextContent();
            String existingDate = existingStock.getElementsByTagName("date").item(0)
                    .getTextContent();
            double existingClosingPrice = Double.parseDouble(existingStock
                    .getElementsByTagName("close-price").item(0).getTextContent());

            StockTransactionImpl stockTransaction = stock.getKey();
            String ticker = stockTransaction.getTicker();
            String date = stockTransaction.getDate();
            double closingPrice = stockTransaction.getClosingPrice();

            if (existingTicker.equals(ticker) && existingDate.equals(date)
                    && existingClosingPrice == closingPrice) {
              double existingAmountOfStock = Double.parseDouble(existingStock
                      .getElementsByTagName("Amount-of-stock").item(0).getTextContent());
              Double amountOfStock = stock.getValue();
              existingStock.getElementsByTagName("Amount-of-stock").item(0)
                      .setTextContent(String.valueOf(existingAmountOfStock + amountOfStock));
              stockExists = true;
              break;
            }
          }
          if (!stockExists) {
            Element stockElement = doc.createElement("stock");

            StockTransactionImpl stockTransaction = stock.getKey();
            String ticker = stockTransaction.getTicker();
            String date = stockTransaction.getDate();
            double closingPrice = stockTransaction.getClosingPrice();
            Double amountOfStock = stock.getValue();

            Element tickerElement = doc.createElement("ticker");
            tickerElement.appendChild(doc.createTextNode(ticker));
            stockElement.appendChild(tickerElement);

            Element dateElement = doc.createElement("date");
            dateElement.appendChild(doc.createTextNode(date));
            stockElement.appendChild(dateElement);

            Element closingPriceElement = doc.createElement("close-price");
            closingPriceElement.appendChild(doc.createTextNode(String.valueOf(closingPrice)));
            stockElement.appendChild(closingPriceElement);

            Element amountOfStockElement = doc.createElement("Amount-of-stock");
            amountOfStockElement.appendChild(doc.createTextNode(amountOfStock.toString()));
            stockElement.appendChild(amountOfStockElement);

            stocksElement.appendChild(stockElement);
          }
        }
      }

      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer trans = tFactory.newTransformer();
      trans.setOutputProperty(OutputKeys.INDENT, "yes");
      trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(filePath));
      trans.transform(source, result);

      view.display("Successfully saved portfolio: " + getName() + " to " + filePlace + "\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * goes to xml portfolio file to access.
   *
   * @param portfolioPath is the path the user chose to store in.
   * @param portfolioName is the name of the given portfolio.
   * @return a portfolio xml.
   */
  public AdvancePortfolio loadPortfolio(String portfolioPath, String portfolioName) {
    try {
      File inputFile = new File(portfolioPath + "/" + portfolioName + ".xml");
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();

      NodeList nodeList = doc.getElementsByTagName("stock");

      HashMap<StockTransactionImpl, Double> stocks = new HashMap<>();

      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          String ticker = element.getElementsByTagName("ticker").item(0).getTextContent();
          String date = element.getElementsByTagName("date").item(0).getTextContent();
          String closePrice = element.getElementsByTagName("close-price").item(0)
                  .getTextContent();
          if (closePrice.isEmpty()) {
            System.out.println("made it here ");
          }
          double closePriceDouble = Double.parseDouble(closePrice);
          double amountOfStock = Double.parseDouble(element.getElementsByTagName("Amount-of-stock")
                  .item(0).getTextContent());

          StockTransactionImpl stockTransaction = new StockTransactionImpl(
                  ticker, date, closePriceDouble);
          stocks.put(stockTransaction, amountOfStock);
        }
      }
      return new AdvancePortfolio(portfolioName, stocks);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Saves the action of removing a stock from an exisitng Portfolio.
   * @param filePlace is the filefolder where the user stored the Portfolio.
   */
  public void removeSavePorts(String filePlace) {

    String filePath = filePlace + "/" + getName() + ".xml";
    try {
      DocumentBuilderFactory docBuild = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = docBuild.newDocumentBuilder();
      Document doc = dBuilder.newDocument();

      Element portfolioTag = doc.createElement("portfolio");
      doc.appendChild(portfolioTag);

      Element portfolioName = doc.createElement("portfolioName");
      portfolioName.appendChild(doc.createTextNode(getName()));
      portfolioTag.appendChild(portfolioName);

      Element createStock = doc.createElement("stocks");
      portfolioTag.appendChild(createStock);

      if (stocks != null) {
        for (Map.Entry<StockTransactionImpl, Double> stock : stocks.entrySet()) {
          Element stockElement = doc.createElement("stock");

          StockTransactionImpl stockTransaction = stock.getKey();
          String ticker = stockTransaction.getTicker();
          String date = stockTransaction.getDate();
          double closingPrice = stockTransaction.getClosingPrice();
          Double amountOfStock = stock.getValue();

          Element tickerElement = doc.createElement("ticker");
          tickerElement.appendChild(doc.createTextNode(ticker));
          stockElement.appendChild(tickerElement);

          Element dateElement = doc.createElement("date");
          dateElement.appendChild(doc.createTextNode(date));
          stockElement.appendChild(dateElement);

          Element closingPriceElement = doc.createElement("close-price");
          closingPriceElement.appendChild(doc.createTextNode(String.valueOf(closingPrice)));
          stockElement.appendChild(closingPriceElement);

          Element amountOfStockElement = doc.createElement("Amount-of-stock");
          amountOfStockElement.appendChild(doc.createTextNode(amountOfStock.toString()));
          stockElement.appendChild(amountOfStockElement);

          createStock.appendChild(stockElement);
        }
      }
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
              "2");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(filePath));
      transformer.transform(source, result);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * calcualtes the Compsotion of the  Portfolio's stocks at the speific date.
   * @param date is the date inputted from the user.
   * @return String Composition of the Portfolio's stocks at the speific date.
   */
  public String displayComposition(String date) {
    StringBuilder output = new StringBuilder();
    for (Map.Entry<StockTransactionImpl, Double> entry : stocks.entrySet()) {
      StockTransactionImpl stockTransaction = entry.getKey();
      if (stockTransaction.getDate().equals(date)) {
        output.append(stockTransaction.getTicker()).append(": ")
                .append(entry.getValue()).append("\n");
      }
    }
    return output.toString();
  }

  /**
   * calcualtes the Stock value and Portfolio distrubtion.
   * @param date is the date inputted from the user.
   * @return String of the Distrubtion of the Stocks and Portfolio percentage.
   */
  public String getDistributio(String date) {

    StringBuilder output = new StringBuilder();
    double totalValueAtDate = 0.0;
    double totalValue = 0.0;
    double totalPercentageAtDate = 0.0;
    for (Map.Entry<StockTransactionImpl, Double> entry : stocks.entrySet()) {
      StockTransactionImpl stockTransaction = entry.getKey();
      double stockValue = entry.getValue() * stockTransaction.getClosingPrice();
      totalValue += stockValue;
      if (stockTransaction.getDate().equals(date)) {
        totalValueAtDate += stockValue;
      }
    }

    for (Map.Entry<StockTransactionImpl, Double> entry : stocks.entrySet()) {
      StockTransactionImpl stockTransaction = entry.getKey();
      if (stockTransaction.getDate().equals(date)) {
        double stockValue = entry.getValue() * stockTransaction.getClosingPrice();
        double percentage = (stockValue / totalValue) * 100;
        totalPercentageAtDate += percentage;

        output.append(stockTransaction.getTicker()).append(" : $").append(stockValue)
                .append(" : ").append(String.format("%.2f", percentage)).append("% \n");
      }
    }

    output.append("Portfolio percentage: ").append(String.format("%.2f", totalPercentageAtDate))
            .append("% out of 100%\n");
    output.append("Total at given date: $").append(totalValueAtDate).append("\n");
    return output.toString();
  }



  /**
   * Displays date of each portoflio value at the given date range, while displaying the change in
   * value of the portoflio until the startdate.
   * @param portfolio is the portfolio.
   * @param days is the days between the startDate and Enddate.
   * @param startDate is the intiali date that the user wants to see the Portoflio value.
   * @param endDate is the most recent date that the user wants to see the Portfolio value.
   */
  public void displayBarChart(AdvancePortfolio portfolio, int days,
                              LocalDate startDate, LocalDate endDate) {
    List<Double> values = new ArrayList<>();
    List<LocalDate> dates = new ArrayList<>();
    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
      String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
      values.add(portfolio.valueOfPortfolio(dateString));
      dates.add(date);
    }
    int lines = days;
    int interval = 1;
    if (lines < 5) {
      System.out.println("Not enough info");
      return;
    }
    if (lines > 30) {
      interval = (int) Math.ceil(days / 30.0);
    }
    double maxValue = Collections.max(values);
    double minValue = Collections.min(values);
    double range = maxValue - minValue;
    double scale = range / 50;
    if (scale == 0) {
      scale = 1;
    }
    System.out.println("Performance of portfolio " + portfolio.getName() + " from " +
            startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + " to " +
            endDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    System.out.println();
    for (int i = 0; i < dates.size(); i += interval) {
      LocalDate date = dates.get(i);
      double value = values.get(i);
      int stars = (int) ((value - minValue) / scale);

      System.out.printf("%-10s: %s%n",
              date.format(DateTimeFormatter.ofPattern("yyyy MM dd")),
              "*".repeat(stars));
    }
    System.out.println();
    if (minValue == 0) {
      System.out.printf("Scale: * = $%.4f", scale);
    } else {
      System.out.printf("Scale: * = $%.4f more than base amount of $%.4f%n", scale, minValue);
    }
  }
}


