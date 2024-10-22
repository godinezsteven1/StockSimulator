package view;

import java.io.PrintStream;

/**
 * Class to display messages through the interface to the user.
 */
public class ViewImpl implements View {
  private PrintStream out;

  /**
   * takes in a PrintStream to display message.
   *
   * @param out PrintStream to be displayed.
   */
  public ViewImpl(PrintStream out) {
    this.out = out;
  }


  /**
   * takes in a string to be displayed in the message.
   *
   * @param message takes in a string to be displayed in the message.
   */
  @Override
  public void display(String message) {
    this.out.print(message);
  }

  /**
   * displays a welcome message for the user.
   */
  public void displayWelcomeMessage() {
    this.out.println("Enter amount of days to call back to see the stocks"
            + "of the company: or 'q' to quit program.\n"
            + "'--help' to see available methods in version 1.1.0");
  }

  /**
   * displays to ask to input ticker message.
   */
  public void displayEnterTicker() {
    this.out.println("enter ticker of the company");
  }

  /**
   * displays to ask to input starting date message.
   */
  public void displayEnterStartingDate() {
    this.out.println("enter starting date in the format 'yyyy-mm-dd'");
  }

  /**
   * displays to ask to input ending date message.
   */
  public void displayEnterEndingDate() {
    this.out.println("enter ending date in the format 'yyyy-mm-dd'");
  }


  /**
   * takes in a double to be displayed in the message.
   *
   * @param num is displayed in the message.
   */
  public void displayButHasNum(double num) {

    this.out.print(num + "\n");
  }

  /**
   * displays to ask to input a date message.
   */
  public void displayDate() {
    this.out.println("enter in date in the format 'yyyy-mm-dd'");
  }

  /**
   * displays to ask to input a day(s) message.
   */
  public void displayDays() {
    this.out.println("enter amount of days");
  }

  /**
   * displays to show message of invalid days input.
   */
  public void displayNumDaysInvalidInput() {
    this.out.println("enter a number of days as valid");
  }

  /**
   * displays to ask to input a portfolio name.
   */
  public void displayNamingPortfolio() {
    this.out.println("enter name of portfolio");
  }


  /**
   * displays to ask to input ticker(s) for portfolio.
   */
  public void displayEnterTickerS() {
    this.out.println("enter in tickers of the company name");
  }


}
