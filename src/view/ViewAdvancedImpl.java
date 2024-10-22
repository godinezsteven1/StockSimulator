package view;

import java.io.PrintStream;

/**
 * represents an advanced display options for the new version of stock simulation 2.1.0 version.
 */
public class ViewAdvancedImpl extends ViewImpl implements ViewAdvanced {

  private PrintStream out;

  /**
   * Constucotor to initilize the new View.
   */
  public ViewAdvancedImpl() {
    super(null);
  }


  /**
   * takes in a PrintStream to display message.
   *
   * @param out PrintStream to be displayed.
   */
  public ViewAdvancedImpl(PrintStream out) {
    super(out);
  }

  /**
   * takes in a string to be displayed in the message.
   *
   * @param message takes in a string to be displayed in the message.
   */
  @Override
  public void display(String message) {
    System.out.print(message);
  }

  /**
   * Displays a new menu for the controller.
   * with directions on the new features imeplemtened.
   */
  public void displayAdvancedMenu() {
    System.out.println("#Version 2.1.0 of Stock Simulation offers new features that a user can "
            + "interact with:\nfor starters we have updated the feature 'p' which now creates"
            + " a portfolio to an XML file instead of a CSV. Note Portfolios are initialized \n"
            + "at the choice of the user, so do not forget its location! XD \n"
            + "'port-value' is designed to take a in an existing portfolio, location and date\n "
            + "In order to return the value of that portfolio at a given day. Please note if "
            + "the program cannot find your date, it will return 0\n'buy' is now implemented in"
            + " this version which allows a user to input stock information from an existing CSV"
            + " inluding date, amount, ticker, and what portfolio to put it in, and \nlocation of "
            + "portfolio. Note this feature needs an existing portfolio to be added in.\n"
            + "'sell' likewise to the buy needs the stock information, and portfolio information"
            + " in order to do the sell. Note: Both features overwrite XML files to have your new "
            + " porfolio.\n 'comp' ask for portfolio information, and date to return the content"
            + " of your portfolio on a given day, this is useful to see how many stocks you owned"
            + " at a given time.\n 'dp' is a similar feature to comp but instead this feature"
            + " returns the stock, total price of the stocks and share, the percentage of how much"
            + " of the value \n it is that day and the total amount of that day\n'rebal' feature"
            + " allows a user to input portfolio information \nand then assign each stock in a"
            + " portfolio a percentage of the total budget of a portfolio, and assign a budget \nto"
            + " an existing porfolio. This way when a rebal is called the program will \nsell/buy x"
            + " amount of stock to satisfy the assigned percentage of value corresponding to the "
            + " portfolio's budget.\n 'ch' feature allows a user to choose a porfolio or stock and"
            + " input a date range, for the program to output 5-30 lines, if\n provided enough data"
            + " of a bar chart with asterisk representing a program \ncalculated factor to show "
            + " growth / decay over the input period of time\n"
            + "Please note to avoid user error we have changed date format from yyyy-mm-dd to "
            + "a user just writing one thing at a time with yyyy mm dd format.\n"
            + "please continue to use the program normally \n\n\n");
  }

  /**
   * Displays a welcome message for the user to have basic navigation withing program.
   */
  public void displayWelcomeMessage() {
    System.out.println("Enter amount of days to call back to see stocks of company, \n"
            + "Enter 'q' to quit program \n"
            + "Enter --help to see new available features in version 2.1.0\n"
            + "All features in previous versions are still functional.");
  }


}
