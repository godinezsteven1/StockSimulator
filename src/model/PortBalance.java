package model;

/**
 * This interface represents all the operations to be offered by the PortBalance.
 * These operations are supposed to be a barebones set upon which other operations
 * may be developed.
 */
public interface PortBalance {

  /**
   * Calculates rebalanacing the Portoflio by the budget from the user
   * into the Portoflio's stocks, which divides the budget into each stock.
   */
  public void rebalancePortfolio();
}
