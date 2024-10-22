package controller;

/**
 * This interface represents all the operations to be offered by the Feature interface.
 * These operations are supposed to be a barebones set upon which other operations
 * may be developed.
 */
public interface Features {

  /**
   * creates the portfolio gui from the user's input.
   */
  void makePort();

  /**
   * buys stock for the user's portfolio from the user's inputs.
   */
  void purchaseStock();

  /**
   * sells stock from the user's portfolio with the given inputs from the user.
   */
  void removeStock();

  /**
   * gets the value and Composition of the user's portfolio with the given inputs from the user.
   */
  void getVandC();

  /**
   * calls the Api to get the stock info on the company
   * and the amount of days past the current date when this is called.
   */
  void callBack();

}
