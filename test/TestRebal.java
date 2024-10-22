import org.junit.Before;

import java.util.HashMap;

import model.AdvancePortfolio;
import model.StockTransactionImpl;

/**
 * Testing for the balance of an object.
 */
public class TestRebal {
  StockTransactionImpl google06;
  StockTransactionImpl meta1;
  StockTransactionImpl mmm;
  StockTransactionImpl google31;
  AdvancePortfolio portfolio;
  StockTransactionImpl boughtStock;



  /**
   * intializes an example Portoflio for testing.
   */
  @Before
  public void init() {
    google06 = new StockTransactionImpl("GOOG", "2024-06-06", 178.35);
    meta1 = new StockTransactionImpl("META", "2024-06-06", 493.76);
    mmm = new StockTransactionImpl("MMM", "2024-06-06", 98.22);
    google31 = new StockTransactionImpl("GOOG", "2024-05-31", 173.96);

    HashMap<StockTransactionImpl, Double> shares = new HashMap<>();
    shares.put(google06, 6.0);
    shares.put(meta1, 5.0);
    shares.put(mmm, 2.0);
    shares.put(google31, 7.0);
    portfolio = new AdvancePortfolio("example", shares);
  }


}
