package model;

/**
 * represents a transaction of buying and selling stocks on a specific date.
 */
public class StockTransactionImpl implements StockTransaction {
  private String ticker;
  private String date;
  private double closingPrice;

  /**
   * Consturtor to save the info of the Stock Transaction.
   * @param ticker the Company.
   * @param date the date of the stock.
   * @param closingPrice the closing price of that specific stock.
   */
  public StockTransactionImpl(String ticker, String date, double closingPrice) {
    this.ticker = ticker;
    this.date = date;
    this.closingPrice = closingPrice;
  }

  /**
   * Consturtor to intilize the StockTranaction and record/saves the info.
   * @param ticker the Company.
   * @param date the date of the stock.
   */
  public StockTransactionImpl(String ticker, String date) {
    this.ticker = ticker;
    this.date = date;
  }

  /**
   * gets the Stocktransaction into a preferred string format.
   * @return the Stocktransaction into a preferred string format.
   */
  @Override
  public String toString() {
    return ticker + " ," + date + " ," + closingPrice;
  }

  /**
   * Gets the ticker of the company.
   * @return ticker of the company.
   */
  public String getTicker() {
    return ticker;
  }

  /**
   * gets the date of the StockTransaction.
   * @return date of the StockTransaction.
   */
  public String getDate() {
    return date;
  }

  /**
   * gets the closing price of the stock.
   * @return gets the ClosingPrice of the stock.
   */
  public double getClosingPrice() {
    return closingPrice;
  }

  /**
   * Gets the particular stock value.
   * @param shares the amount of shares in that stock.
   * @return double the cost/value of the stock with the amount of shares in that stock.
   */
  public double totalStockCost(int shares) {
    return shares * this.closingPrice;
  }
}