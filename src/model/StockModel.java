package model;

/**
 * This interface represents all the operations to be offered by the StockModel.
 * These operations are supposed to be a barebones set upon which other operations
 * may be developed.
 */
public interface StockModel {

  /**
   * gives whether the company had gain or loss in the speicifc dates.
   *
   * @param ticker       is the ticker of the company.
   * @param startingDate is the start of date you want to know in the format "yyyy-mm-dd".
   * @param endDate      is the end of date you want to know in the format "yyyy-mm-dd".
   * @return a double with the average cost between starting day and end date.
   */
  public String gainOrLoss(String ticker, String startingDate, String endDate);

  /**
   * gives the dates that are crossover days in the speicifc dates.
   *
   * @param ticker       is the ticker of the company.
   * @param startingDate is the start of date you want to know in the format "yyyy-mm-dd".
   * @param endDate      is the end of date you want to know in the format "yyyy-mm-dd".
   * @param days         is the number of days to use for moving day calcualtions.
   * @return a list of string of the days that are crossover.
   */
  public String daysCrossover(String ticker, String startingDate, String endDate, int days);

  /**
   * gives the movingDay average of the company in the speciifc date and how many days backwards.
   *
   * @param ticker  is the ticker of the company.
   * @param endDate is the most recent date.
   * @param days    is how many days to go backwards starting from the endDate.
   * @return the movingDay average.
   */
  public double movingDayAverage(String ticker, String endDate, int days);
}
