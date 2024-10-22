import model.StockModel;

/**
 * Mockmodel to test controller.
 */
class MockModel implements StockModel {
  private StringBuilder log;

  /**
   * for testing .
   *
   * @param sb is a string builder.
   */
  public MockModel(StringBuilder sb) {
    log = sb;
  }

  /**
   * testing gain or loss.
   *
   * @param ticker       the company ticker.
   * @param startingDate is the start of date you want to know in the format "yyyy-mm-dd".
   * @param endDate      is the end of date you want to know in the format "yyyy-mm-dd".
   * @return String of the gain or loss.
   */
  @Override
  public String gainOrLoss(String ticker, String startingDate, String endDate) {
    log.append("gainOrLoss:(" + ticker + "," + startingDate + "," + endDate + ")");
    return "";
  }

  /**
   * testing days cross over.
   *
   * @param ticker       the company ticker.
   * @param startingDate is the start of date you want to know in the format "yyyy-mm-dd".
   * @param endDate      is the end of date you want to know in the format "yyyy-mm-dd".
   * @param days         the amount of days to go backwrds.
   * @return String of the days that are corssovered.
   */
  @Override
  public String daysCrossover(String ticker, String startingDate, String endDate, int days) {
    log.append("daysCrossover:(" + ticker + "," + startingDate + "," + endDate + "," + days + ")");
    return "";
  }

  /**
   * tesing moving average.
   *
   * @param ticker  the company ticker.
   * @param endDate is the end of date you want to know in the format "yyyy-mm-dd".
   * @param days    the num of days to go backwards.
   * @return double of the moving day average.
   */
  @Override
  public double movingDayAverage(String ticker, String endDate, int days) {
    log.append("daysCrossover:(" + ticker + "," + endDate + "," + days + ")");
    return 0.0;
  }
}