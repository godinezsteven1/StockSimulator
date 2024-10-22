package model;


/**
 * This interface represents all the operations to be offered by the StockTransaction.
 * These operations are supposed to be a barebones set upon which other operations
 * may be developed.
 */
public interface StockTransaction {


  /**
   * Gets the ticker of the company.
   * @return ticker of the company.
   */
  public String getTicker();

  /**
   * gets the date of the StockTransaction.
   * @return date of the StockTransaction.
   */
  public String getDate();

  /**
   * gets the closing price of the stock.
   * @return gets the ClosingPrice of the stock.
   */
  public double getClosingPrice();

  /**
   * Gets the particular stock value.
   * @param shares the amount of shares in that stock.
   * @return double the cost/value of the stock with the amount of shares in that stock.
   */
  public double totalStockCost(int shares);

}
