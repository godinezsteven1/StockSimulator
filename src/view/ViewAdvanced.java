package view;

/**
 * This interface represents all the operations to be offered by the ViewAdvanced.
 * These operations are supposed to be a barebones set upon which other operations
 * may be developed.
 */
public interface ViewAdvanced extends View {

  /**
   * takes in a string to be displayed in the message.
   *
   * @param message takes in a string to be displayed in the message.
   */
  @Override
  public void display(String message);

  /**
   * displays the new menu for the advancted controller with new features.
   */
  public void displayAdvancedMenu();
}
