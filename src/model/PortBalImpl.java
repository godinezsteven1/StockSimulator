package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class that rebalanaces the uesr's Portfolio.
 */
public class PortBalImpl implements PortBalance {
  private AdvancePortfolio portfolioToRebalance;
  private final int budget;
  private HashMap<String, Double> percentages;
  private String filePath;


  /**
   * Consturctor to store/save info to conduct to rebalance the Portfolio.
   * @param portfolioToRebalance is the Portfolio that the user chooses to rebalance.
   * @param budget is the amount of money the user wants to input into the stocks.
   * @param percentages is the divided amount of money the user wishes to input
   *                    the budget into each stock.
   * @param filePath is the filefolder/location of where the Portfolio is stored.
   */
  public PortBalImpl(AdvancePortfolio portfolioToRebalance, int budget,
                     HashMap<String, Double> percentages, String filePath) {
    this.portfolioToRebalance = portfolioToRebalance;
    this.budget = budget;
    this.percentages = new HashMap<>(percentages);
    this.filePath = filePath;
  }

  /**
   *gets the filefolder/location of where the Portfolio is stored.
   * @return String filefolder/location of where the Portfolio is stored.
   */
  private String getFilePath() {
    return filePath;
  }


  /**
   * Calculates rebalanacing the Portoflio by the budget from the user
   * into the Portoflio's stocks, which divides the budget into each stock.
   *
   */
  @Override
  public void rebalancePortfolio() {
    HashMap<StockTransactionImpl, Double> stocks =
            new HashMap<>(portfolioToRebalance.getStocks());
    Set<Map.Entry<StockTransactionImpl, Double>> entries = new HashSet<>(stocks.entrySet());
    for (Map.Entry<StockTransactionImpl, Double> entry : entries) {
      StockTransactionImpl stockTransaction = entry.getKey();
      String ticker = stockTransaction.getTicker();
      double closingPrice = stockTransaction.getClosingPrice();
      double shares = entry.getValue();
      double totalValue = shares * closingPrice;

      if (percentages.containsKey(ticker)) {
        double desiredPercentage = percentages.get(ticker);
        double desiredValue = budget * desiredPercentage / 100;

        if (totalValue > desiredValue) {
          double excessValue = totalValue - desiredValue;
          double sharesToSell = excessValue / closingPrice;
          portfolioToRebalance.sellRemoveStock(stockTransaction.getDate(),
                  ticker, sharesToSell, this.getFilePath());
        } else if (totalValue < desiredValue) {
          double shortfallValue = desiredValue - totalValue;
          double sharesToBuy = shortfallValue / closingPrice;
          StockTransactionImpl buyStock = new StockTransactionImpl(ticker,
                  stockTransaction.getDate(), closingPrice);
          portfolioToRebalance.addStock(buyStock, sharesToBuy);
        }
      }
    }
    portfolioToRebalance.removeSavePorts(this.getFilePath());
  }


}
