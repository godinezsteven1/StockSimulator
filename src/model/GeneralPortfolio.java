package model;

/**
 * This interface represents all the operations to be offered by the GeneralPortfolio.
 * These operations are supposed to be a barebones set upon which other operations
 * may be developed.
 */
public interface GeneralPortfolio {


  /**
   * gives the monetary value of the Model.Portfolio on a given day.
   *
   * @param date is the specific day in which one wants to know the value of.
   * @return double which is the value of the Model.Portfolio on the day.
   */
  public double valueOfPortfolio(String date);

  /**
   * gets the name of the Model.Portfolio.
   *
   * @return string name of the Model.Portfolio.
   */
  public String getName();



}
