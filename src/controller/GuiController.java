package controller;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import model.AdvPortfolioInterface;
import model.AdvancePortfolio;
import model.StockTransactionImpl;
import view.IGuiView;

/**
 * represents a working Stock Controller that deals with GUIS
 * and trades with Stocks.
 * {@code GuiController} represents the
 * operators to deal with stocks.
 */
public class GuiController implements Features {
  private final AdvPortfolioInterface model;
  private IGuiView view;


  /**
   * Constuctor that initalies so that it can use the implemented methods that deal with stocks
   * from the AdvPortfolio interface.
   *
   * @param m the AdvPortfolio interface.
   */
  public GuiController(AdvPortfolioInterface m) {
    model = m;
  }


  /**
   * Connects the guiView to the controller through their addFeatures.
   *
   * @param v the IGuiView interface.
   */
  public void setView(IGuiView v) {
    this.view = v;
    view.addFeatures(this);
  }


  /**
   * creates the portfolio gui from the user's input.
   */
  public void makePort() {
    JDialog interactive = new JDialog();
    interactive.setTitle("Make Portfolio");
    interactive.setModal(true);

    String[] allowedDirectories = {"res", "src", "out"};
    JComboBox<String> directoryMenu = new JComboBox<>(allowedDirectories);
    JTextField portName = new JTextField(20);

    JPanel panel = new JPanel();
    panel.add(new JLabel("Select file path:")); //choose from allowed
    panel.add(directoryMenu);
    panel.add(new JLabel("Enter portfolio name:")); // name
    panel.add(portName);
    JButton makePortButton = new JButton("Create");
    makePortButton.addActionListener(e -> {
      String directoryPath = (String) directoryMenu.getSelectedItem(); // to string
      String portfolioName = portName.getText();
      if (portfolioName != null && !portfolioName.isEmpty()) { // if exist basically
        AdvancePortfolio userPort = new AdvancePortfolio(portfolioName); // new obj
        userPort.savePorts(directoryPath); // give info to that obj, boom extension
        interactive.dispose();
        JOptionPane.showMessageDialog(interactive, "Portfolio: "
                + portfolioName + " created successfully at: " + directoryPath);
        // ask ta about how to display this in existing panel and not going back to
        // main home page panel
      }
    });
    // other stuff
    panel.add(makePortButton);
    interactive.getContentPane().add(panel);
    interactive.pack();
    interactive.setVisible(true);
  }

  /**
   * buys stock for the user's portfolio from the user's inputs in the gui.
   */
  public void purchaseStock() {
    JDialog feature = new JDialog();
    feature.setTitle("Purchase Stock");
    feature.setModal(true);

    List<String> tickers = getStockTickers(); // helper
    JComboBox<String> tickerMenu = new JComboBox<>(tickers.toArray(new String[0]));
    String[] year = generateYears();
    String[] month = generateMonths();
    String[] day = generateDays();
    JComboBox<String> yearDM = new JComboBox<>(year); // displays up to 20 yo
    JComboBox<String> monthDM = new JComboBox<>(month);
    JComboBox<String> dayDM = new JComboBox<>(day);
    JTextField shares = new JTextField(20); // looks good i guess?
    JTextField portName = new JTextField(20);
    String[] allowedDirectories = {"res", "src", "out"};
    JComboBox<String> directoryMenu = new JComboBox<>(allowedDirectories);
    JLabel messageLabel = new JLabel();
    JPanel panel = new JPanel(new GridLayout(0, 2)); // seems good t resize
    panel.setPreferredSize(new Dimension(580, 300));

    panel.add(new JLabel("Select Ticker:"));
    panel.add(tickerMenu);
    panel.add(new JLabel("Select year for stock to buy:"));
    panel.add(yearDM);
    panel.add(new JLabel("Select month:"));
    panel.add(monthDM);
    panel.add(new JLabel("Select day:"));
    panel.add(dayDM);
    panel.add(new JLabel("Enter # of shares:"));
    panel.add(shares);
    panel.add(new JLabel("Choose portfolio you want to add to:"));
    panel.add(portName);
    panel.add(new JLabel("Location to where you saved the portfolio:"));
    panel.add(directoryMenu);
    panel.add(messageLabel); // for the comp

    JButton buyButton = new JButton("Purchase");
    buyButton.addActionListener(e -> {
      String tickerName = (String) tickerMenu.getSelectedItem();
      String date = yearDM.getSelectedItem() + "-" + monthDM.getSelectedItem() + "-"
              + dayDM.getSelectedItem();
      String sharesAsString = shares.getText();
      String userPort = portName.getText(); // Get the entered portfolio name
      String fileLoco = (String) directoryMenu.getSelectedItem(); // Get the selected file location

      purchaseSaveXML(tickerName, date, sharesAsString, userPort,
              fileLoco, messageLabel, feature); // helper pt 2
    });
    panel.add(buyButton);

    JButton closeButton = new JButton("Back");
    closeButton.addActionListener(e -> closeDialog(feature));

    panel.add(closeButton);
    feature.getContentPane().add(panel);
    feature.pack();
    feature.setVisible(true);
  }

  /**
   * sells stock from the user's portfolio with the given inputs from the user.
   */
  public void removeStock() {
    JDialog feature = new JDialog();
    feature.setTitle("Sell Stock");
    feature.setModal(true);

    List<String> tickers = getStockTickers(); // helper
    JComboBox<String> tickerMenu = new JComboBox<>(tickers.toArray(new String[0]));
    String[] year = generateYears();
    String[] month = generateMonths();
    String[] day = generateDays();
    JComboBox<String> yearDM = new JComboBox<>(year);
    JComboBox<String> monthDM = new JComboBox<>(month);
    JComboBox<String> dayDM = new JComboBox<>(day);
    JTextField shares = new JTextField(20);
    JTextField portName = new JTextField(20);
    String[] allowedDirectories = {"res", "src", "out"};
    JComboBox<String> directoryMenu = new JComboBox<>(allowedDirectories);
    JLabel messageLabel = new JLabel();
    JPanel panel = new JPanel(new GridLayout(0, 2));
    panel.setPreferredSize(new Dimension(580, 300));  //fix size later

    panel.add(new JLabel("Select Ticker:"));
    panel.add(tickerMenu);
    panel.add(new JLabel("Select year of stock to sell:"));
    panel.add(yearDM);
    panel.add(new JLabel("Select month:"));
    panel.add(monthDM);
    panel.add(new JLabel("Select day:"));
    panel.add(dayDM);
    panel.add(new JLabel("Enter number of shares to sell:"));
    panel.add(shares);
    panel.add(new JLabel("Name of portfolio you want to sell from:"));
    panel.add(portName);
    panel.add(new JLabel("Location of portfolio you want to sell from:"));
    panel.add(directoryMenu);
    panel.add(messageLabel);

    JButton sellButton = new JButton("Sell");
    sellButton.addActionListener(e -> {
      String tickerName = (String) tickerMenu.getSelectedItem();
      String date = yearDM.getSelectedItem() + "-" + monthDM.getSelectedItem()
              + "-" + dayDM.getSelectedItem();
      String sharesAsString = shares.getText();
      String userPort = portName.getText();
      String fileLoco = (String) directoryMenu.getSelectedItem();

      sellSaveXML(tickerName, date, sharesAsString, userPort, fileLoco, messageLabel, feature);
    });
    panel.add(sellButton);

    JButton closeButton = new JButton("Back");
    closeButton.addActionListener(e -> closeDialog(feature));
    panel.add(closeButton);
    feature.getContentPane().add(panel);
    feature.pack();
    feature.setVisible(true);
  }


  /**
   * gets the value and Composition of the user's portfolio with the given inputs from the user.
   */
  public void getVandC() {
    JDialog feature = new JDialog();
    feature.setTitle("Get Value and Composition");
    feature.setModal(true);

    String[] year = generateYears();
    String[] month = generateMonths();
    String[] day = generateDays();
    JComboBox<String> yearMenu = new JComboBox<>(year);
    JComboBox<String> monthMenu = new JComboBox<>(month);
    JComboBox<String> dayMenu = new JComboBox<>(day);
    JTextField portName = new JTextField(20);
    String[] allowedDirectories = {"res", "src", "out"};
    JComboBox<String> directoryMenu = new JComboBox<>(allowedDirectories);
    JLabel messageLabel = new JLabel();
    JPanel panel = new JPanel(new GridLayout(0, 2));
    panel.setPreferredSize(new Dimension(610, 430)); // works best

    panel.add(new JLabel("Enter Portfolio name:"));
    panel.add(portName);
    panel.add(new JLabel("Enter Portfolio location:"));
    panel.add(directoryMenu);
    panel.add(new JLabel("Select year:"));
    panel.add(yearMenu);
    panel.add(new JLabel("Select month:"));
    panel.add(monthMenu);
    panel.add(new JLabel("Select day:"));
    panel.add(dayMenu);
    panel.add(messageLabel);

    JButton getButton = new JButton("Calculate");
    getButton.addActionListener(e -> {
      String portfolioName = portName.getText();
      String portfolioLocation = (String) directoryMenu.getSelectedItem();
      String date = yearMenu.getSelectedItem() + "-" + monthMenu.getSelectedItem()
              + "-" + dayMenu.getSelectedItem();

      //method that does both compo and value.
      calculateVandC(portfolioName, portfolioLocation, date, messageLabel, feature);
    });
    panel.add(getButton);

    JButton closeButton = new JButton("Back");
    closeButton.addActionListener(e -> closeDialog(feature));
    panel.add(closeButton);
    feature.getContentPane().add(panel);
    feature.pack();
    feature.setVisible(true);
  }

  /**
   * calls the Api to get the stock info on the company
   * and the amount of days past the current date when this is called.
   */
  public void callBack() {
    JDialog feature = new JDialog();
    feature.setTitle("Stock Fetcher");
    feature.setModal(true);

    JTextField ticker = new JTextField(20);
    JTextField days = new JTextField(20);

    JPanel panel = new JPanel(new GridLayout(0, 2));
    panel.setPreferredSize(new Dimension(370, 100));

    panel.add(new JLabel("Input Ticker:"));
    panel.add(ticker);
    panel.add(new JLabel("Input amount of days:"));
    panel.add(days);

    JButton fetchButton = new JButton("Fetch");
    fetchButton.addActionListener(e -> {
      String tickerText = ticker.getText();
      String daysText = days.getText();
      int daysInt = Integer.parseInt(daysText);
      String apiKey = "NMBJV9I1JXOWWEZ6";

      try {
        String baseURL = "https://www.alphavantage.co/query?function=TIME_SERIES_"
                + "DAILY&outputsize=full&datatype=csv";

        String pathToTicStock = "res/" + tickerText + "csvfile.csv";
        File portfolioFile = new File(pathToTicStock);
        int amountOfLines = 0;
        if (portfolioFile.exists()) {
          try (BufferedReader reader = new BufferedReader(new FileReader(portfolioFile))) {
            while (reader.readLine() != null) {
              amountOfLines++;
            }
          } catch (IOException ee) {
            throw new RuntimeException("Error reading file: " + pathToTicStock, ee);
          }
        }

        if (amountOfLines >= daysInt) {
          JOptionPane.showMessageDialog(feature, "Already have the stock "
                  + "info in the database!!!!!!");
        } else {
          URL url = new URL(baseURL + "&symbol=" + tickerText + "&apikey=" + apiKey);
          try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                  url.openStream()));
               BufferedWriter writer = new BufferedWriter(new FileWriter("res/" + tickerText
                       + "csvfile.csv"))) {
            String line;
            boolean isFirstLine = true;
            int linesWritten = 0;
            while ((line = reader.readLine()) != null) {
              if (isFirstLine) {
                isFirstLine = false;
                continue;
              }
              if (linesWritten < daysInt) {
                writer.write(line);
                writer.newLine();
                linesWritten++;
              }
            }

          } catch (IOException es) {
            throw new IllegalArgumentException("No price Data found for: " + tickerText
                    + ", or ticker does not exist, please check again.");
          }
        }
      } catch (IOException eq) {
        throw new RuntimeException("API Key is invalid or has changed " + apiKey, eq);
      }
    });
    panel.add(fetchButton);

    JButton closeButton = new JButton("Back");
    closeButton.addActionListener(e -> feature.dispose());
    panel.add(closeButton);
    feature.add(panel);
    feature.pack();
    feature.setVisible(true);
  }


  /**
   * gets the closing price of a given stock at a given day.
   *
   * @param ticker takes in string ticker.
   * @param date   takes in date in format 'yyyy-mm-dd'.
   * @return closing price of a given stock at a given day
   */
  private double getCPFromCSV(String ticker, String date) {
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
      throw new IllegalArgumentException("Date does not exist for: " + ticker);
    }
    return closingPrice;
  }

  /**
   * Creates a list of sting years for the user to choose.
   *
   * @return list of string years.
   */
  private String[] generateYears() {
    String[] years = new String[25];
    for (int i = 0; i < years.length; i++) {
      years[i] = String.valueOf(2000 + i);
    }
    return years;
  }

  /**
   * Creates a list of sting months for the user to choose.
   *
   * @return list of string months.
   */
  private String[] generateMonths() {
    String[] months = new String[12];
    for (int i = 0; i < months.length; i++) {
      months[i] = String.format("%02d", i + 1);
    }
    return months;
  }

  /**
   * Creates a list of sting days for the user to choose.
   *
   * @return list of string days.
   */
  private String[] generateDays() {
    String[] days = new String[31];
    for (int i = 0; i < days.length; i++) {
      days[i] = String.format("%02d", i + 1);
    }
    return days;
  }

  /**
   * Gets the list of stock from the ticker for the purchaseStock method.
   *
   * @return list of Stock.
   */
  private List<String> getStockTickers() {
    List<String> stockTickers = new ArrayList<>();
    try (DirectoryStream<Path> files = Files.newDirectoryStream(Paths.get("res"),
            "*.csv")) {
      for (Path i : files) {
        String fileName = i.getFileName().toString(); // go to
        Matcher reader = Pattern.compile("[A-Z]+").matcher(fileName);
        // only get ticker aka all caps
        if (reader.find()) {
          String ticker = reader.group();
          stockTickers.add(ticker);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stockTickers;
  }

  /**
   * Gets the stock the user wants to purchase and overwrites it to their existing Portfolio.
   *
   * @param tickerName     the name of the company's ticker.
   * @param date           is the date that the user wants to buy the stock from.
   * @param sharesAsString is the number of shares the user wants to purchase from the company.
   * @param userPort       is the user's Portfolio they want to add the stock to.
   * @param fileLoco       the location of where the Portfolio is saved.
   * @param messageLabel   is the confirmation message to the user that they successfully bought a
   *                       stock.
   * @param feature        is the panel that adds the content.
   */
  private void purchaseSaveXML(String tickerName, String date, String sharesAsString,
                               String userPort, String fileLoco, JLabel messageLabel,
                               JDialog feature) {
    try {
      int shares = Integer.parseInt(sharesAsString);
      if (shares < 1) {
        throw new NumberFormatException();
      }
      double closingPrice = getCPFromCSV(tickerName, date);
      StockTransactionImpl buyStock = new StockTransactionImpl(tickerName, date, closingPrice);

      messageLabel.setText("Closing: $" + closingPrice
              + ", Total cost: $" + buyStock.totalStockCost(shares));

      AdvancePortfolio portfolio = new AdvancePortfolio(userPort);
      portfolio.addStock(buyStock, shares);
      portfolio.addSavePorts(fileLoco);
      // xml override, not saving
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuild = dbf.newDocumentBuilder();
      Document doc = dBuild.parse(new File(fileLoco + "/" + userPort + ".xml"));
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer t = tFactory.newTransformer();
      DOMSource src = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(fileLoco + "/" + userPort + ".xml"));
      t.transform(src, result);

    } catch (NumberFormatException ex) {
      messageLabel.setText("Shares is not a valid input.");
    } catch (Exception ex) {
      messageLabel.setText("An error occurred: " + ex.getMessage());
    }
    feature.pack();
  }

  /**
   * Removes the stock the user wants to sell and overwrites it to their existing Portfolio.
   *
   * @param tickerName        the name of the company's ticker.
   * @param date              is the date that the user wants to buy the stock from.
   * @param sharesAsString    is the number of shares the user wants to purchase from the company.
   * @param portfolioName     is the user's Portfolio they want to add the stock to.
   * @param portfolioLocation the location of where the Portfolio is saved.
   * @param messageLabel      is the confirmation message to the user that they successfully bought
   *                          a stock.
   * @param feature           is the panel that adds the content
   */
  private void sellSaveXML(String tickerName, String date, String sharesAsString,
                           String portfolioName, String portfolioLocation,
                           JLabel messageLabel, JDialog feature) {
    try {
      int shares = Integer.parseInt(sharesAsString);
      if (shares < 1) {
        throw new NumberFormatException();
      }

      AdvancePortfolio port = new AdvancePortfolio(portfolioName);
      AdvPortfolioInterface userPort = port.loadPortfolio(portfolioLocation, portfolioName);
      userPort.sellRemoveStock(date, tickerName, shares, portfolioLocation);

      messageLabel.setText("Stock sold successfully.");

    } catch (NumberFormatException e) {
      messageLabel.setText("Shares is not a valid input.");
    } catch (Exception e) {
      messageLabel.setText("An error occurred: " + e.getMessage());
    }
    feature.pack();
  }


  /**
   * gets the Port value and composition.
   *
   * @param portfolioName     is the name of the Portfolio.
   * @param portfolioLocation is where the portfolio is located
   * @param date              is the date the user wants to see the Portfolio's value
   *                          and Composition.
   * @param messageLabel      displays the value and Composition of the portoflio.
   * @param feature           is the panel that adds the content.
   */
  private void calculateVandC(String portfolioName, String portfolioLocation, String date,
                              JLabel messageLabel, JDialog feature) {
    try {
      AdvancePortfolio userPort = new AdvancePortfolio(portfolioName);
      userPort = userPort.loadPortfolio(portfolioLocation, portfolioName);
      String comp = userPort.displayComposition(date);

      double portValue = getPortValue(portfolioLocation, portfolioName, date);

      messageLabel.setText("<html><b>Composition:</b><br>" + comp
              + "<br><b>Total portfolio value on " + date + ":</b> $" + portValue + "</html>");

    } catch (Exception e) {
      messageLabel.setText("error: " + e.getMessage());
    }
    feature.pack();
  }

  /**
   * helper that calculates the portfolio value.
   *
   * @param portfolioLocation represents where user saved.
   * @param portfolioName     represents name user gave it.
   * @param date              represents the date in format yyyy-mm-dd.
   * @return a monetary value in double form.
   * @throws ParserConfigurationException if Doc builder factor cannot be created.
   * @throws SAXException                 if parser fails.
   * @throws IOException                  if doc fails because of parser.
   * @throws XPathExpressionException     if list fails initialization.
   */
  public double getPortValue(String portfolioLocation, String portfolioName, String date) {
    AdvancePortfolio port = new AdvancePortfolio(portfolioName);
    AdvPortfolioInterface portfolio = port.loadPortfolio(portfolioLocation, portfolioName);
    double portValue = 0.0;
    for (Map.Entry<StockTransactionImpl, Double> entry : portfolio.getStocks().entrySet()) {
      StockTransactionImpl stockTransaction = entry.getKey();
      double shares = entry.getValue();
      if (stockTransaction.getDate().equals(date)) {
        portValue += stockTransaction.getClosingPrice() * shares;
      }
    }
    return portValue;
  }

  /**
   * back button that disposes current dialog.
   *
   * @param d represents the current viewing dialogue (panel).
   */
  private void closeDialog(JDialog d) {
    d.dispose();
  }
}
