package view;

/**
 * This interface represents all the operations to be offered by the View.View.
 * These operations are supposed to be a barebones set upon which other operations
 * may be developed.
 */
public interface View {

  /**
   * takes in a string to be displayed in the message.
   *
   * @param message takes in a string to be displayed in the message.
   */
  void display(String message);

  /**
   * displays a wlecome message for the user.
   */
  void displayWelcomeMessage();

  /**
   * displays to ask to input ticker message.
   */
  void displayEnterTicker();

  /**
   * displays to ask to input starting date message.
   */
  void displayEnterStartingDate();

  /**
   * displays to ask to input ending date message.
   */
  void displayEnterEndingDate();

  /**
   * takes in a double to be displayed in the message.
   *
   * @param num is displayed in the message.
   */
  void displayButHasNum(double num);

  /**
   * displays to ask to input a date message.
   */
  void displayDate();

  /**
   * displays an input amount of days.
   */
  void displayDays();

  /**
   * displays to show message of invalid days input.
   */
  void displayNumDaysInvalidInput();

  /**
   * displays to ask to input a portfolio name.
   */
  void displayNamingPortfolio();

}
