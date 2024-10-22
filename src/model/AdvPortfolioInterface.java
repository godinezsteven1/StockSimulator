package model;

import java.util.HashMap;


/**
 * This interface represents all the operations to be offered by the AdvPortfolioInterface.
 * These operations are supposed to be a barebones set upon which other operations
 * may be developed.
 */
public interface AdvPortfolioInterface extends GeneralPortfolio {

  /**
   *gets the map of the StockTarnsaction and the number of shares for the transaction.
   * @return HashMap of the StockTarnsaction and the number of shares for that.
   *
   */
  public HashMap<StockTransactionImpl, Double> getStocks();

  /**
   * Adds a stock to the Portoflio.
   * @param stock is the stock to be added/stored in the Portoflio.
   * @param shares is how many shares of that stock the user has.
   */
  public void addStock(StockTransactionImpl stock, double shares);

  /**
   * Gets the name of the Portfolio.
   * @return String name of the Portfolio.
   */
  public String getName();


  /**
   * creates an XML based off of portfolio info.
   *
   * @param filePlace is the folder where the user wants to store the Portfolio.
   */
  public void savePorts(String filePlace);



  /**
   * Removes/sells a stock from an existing Portfolio in the xml file.
   *
   * @param date   id the date of the stock you want to sell.
   * @param ticker is the company that they are selling the stocks from.
   * @param shares is the number of shares
   */
  public void sellRemoveStock(String date, String ticker, double shares, String filePath);


  /**
   * returns the value of the total Portfolio from its stocks
   * at the speific date.
   * @param date is the date the user wants to see the value of the Portoflio.
   * @return double which is the value of the Portfolio on the stock's date.
   */
  public double valueOfPortfolio(String date);


  /**
   * Adds Portfolios at the given filefolder location.
   * @param filePlace is the filefodler location to store the Portfolio.
   */
  public void addSavePorts(String filePlace);

  /**
   * goes to xml portfolio file to access.
   *
   * @param portfolioPath is the path the user chose to store in.
   * @param portfolioName is the name of the given portfolio.
   * @return a portfolio xml.
   */
  public AdvancePortfolio loadPortfolio(String portfolioPath, String portfolioName);

  /**
   * Saves the action of removing a stock from an exisitng Portfolio.
   * @param filePlace is the filefolder where the user stored the Portfolio.
   */
  public void removeSavePorts(String filePlace);

  /**
   * calcualtes the Compostion of the  Portfolio's stocks at the speific date.
   * @param date is the date inputted from the user.
   * @return String Composition of the Portfolio's stocks at the speific date.
   */
  public String displayComposition(String date);

  /**
   * calcualtes the Stock value and Portfolio distrubtion.
   * @param date is the date inputted from the user.
   * @return String of the Distrubtion of the Stocks and Portfolio percentage.
   */
  public String getDistributio(String date);


}
