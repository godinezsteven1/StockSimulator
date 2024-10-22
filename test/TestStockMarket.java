import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import controller.AdvanceStockController;
import view.ViewAdvanced;
import view.ViewAdvancedImpl;
import view.ViewImpl;
import view.View;
import controller.APIController;
import controller.AlphaVantageAPICallController;
import model.StockModel;

import static org.junit.Assert.assertEquals;

/**
 * class to test the controller.
 */
public class TestStockMarket {

  /**
   * test that it first works when staring.
   */
  @Test
  public void testExecute() {
    StringBuilder mockLog = new StringBuilder();
    StockModel model = new MockModel(mockLog);
    OutputStream out = new ByteArrayOutputStream();
    ViewAdvanced view = new ViewAdvancedImpl(new PrintStream(out));


    StringBuilder expected = new StringBuilder(); //has all of the expected output

    StringBuilder emptySB = new StringBuilder();
    // need an empty stringbuilder "emptySB" that your program
    // is going to use to put its outputs in


    StringBuilder userInput = new StringBuilder();
    //so that it has input in run correctly

    String welcomeExpect = "Enter amount of days to call back: or 'q' to quit program.\n"
            + "'--help' to see available methods in version 1.1.0\n";
    expected.append(welcomeExpect);


    Readable in = new StringReader("q"); // this
    APIController controller = new AdvanceStockController(model, view, in);
    controller.execute();
    assertEquals(expected.toString(), out.toString());

    // compare "expected" to "emptySB" for testing
  }


  /**
   * test that gain or loss works.
   */
  @Test
  public void testG() {
    StringBuilder mockLog = new StringBuilder();
    StockModel model = new MockModel(mockLog);
    OutputStream out = new ByteArrayOutputStream();
    ViewAdvanced view = new ViewAdvancedImpl(new PrintStream(out));


    StringBuilder expected = new StringBuilder(); //has all of the expected output

    StringBuilder emptySB = new StringBuilder();
    // need an empty stringbuilder "emptySB" that your program is going
    // to use to put its outputs in


    StringBuilder userInput = new StringBuilder();
    //so that it has input in run correctly

    String welcomeExpect = "Enter amount of days to call back: or 'q' to quit program.\n"
            + "'--help' to see available methods in version 1.1.0\n";


    expected.append(welcomeExpect);
    String displayE = "enter ticker of the company\n";
    String displayE2 = "Enter starting year for stock to buy : '\n";
    String displayE3 = "Enter starting month make sure its in mm format.\n" +
            "If the month is a single digit number, add a 0 at the beginning: \n"
            + "Enter starting day: make sure its in dd format." +
            "If the day is a single digit number, add a 0 at the beginning: \n" +
            "Enter ending year for stock to buy : \n" +
            "Enter ending month make sure its in mm format.\n" +
            "If the month is a single digit number, add a 0 at the beginning: \n" +
            "GOOG stock gained value, more specifically: 1.8149999999999977 yay! :) " +
            ":P XDEnter amount of days to call back to see stocks of company, \n";
    String display12 = "Enter 'q' to quit program \n" +
            "Enter --help to see new available features in version 2.1.0\n" +
            "All features in previous versions are still functional.";

    expected.append(displayE);
    expected.append(displayE2);
    expected.append(displayE3);
    expected.append(display12);


    StringBuilder sb = new StringBuilder();
    sb.append("g"
            + System.lineSeparator()
            + "GOOG"
            + System.lineSeparator()
            + "2024"
            + System.lineSeparator()
            + "06"
            + System.lineSeparator()
            + "05"
            + System.lineSeparator()
            + "2024"
            + System.lineSeparator()
            + "06"
            + System.lineSeparator()
            + "06"
            + System.lineSeparator()
            + "q"
            + System.lineSeparator());

    Readable in = new StringReader(sb.toString());


    APIController controller = new AdvanceStockController(model, view, in);
    controller.execute();
    assertEquals(expected.toString(), out.toString());
  }

  /**
   * test that moving day function works.
   */
  @Test
  public void testM() {
    StringBuilder mockLog = new StringBuilder();
    StockModel model = new MockModel(mockLog);
    OutputStream out = new ByteArrayOutputStream();
    View view = new ViewImpl(new PrintStream(out));


    StringBuilder expected = new StringBuilder(); //has all of the expected output

    StringBuilder emptySB = new StringBuilder();
    // need an empty stringbuilder "emptySB" that your program is going to use to put its outputs in


    StringBuilder userInput = new StringBuilder();
    //so that it has input in run correctly

    String welcomeExpect = "Enter amount of days to call back: or 'q' to quit program.\n"
            + "'--help' to see available methods in version 1.1.0\n";


    expected.append(welcomeExpect);
    String displayE = "enter ticker of the company\n";
    String displayE2 = "enter in date in the format 'yyyy-mm-dd'\n";
    String displayE3 = "enter amount of days\n";
    String display1123 = "0.0\n";
    String display12 = "Enter amount of days to call back: or "
            + "'q' to quit program.\n'--help' "
            + "to see available methods in version 1.1.0\n";

    expected.append(displayE);
    expected.append(displayE2);
    expected.append(displayE3);
    expected.append(display1123);
    expected.append(display12);


    Readable in = new StringReader("m\nGOOG\n2024-06-05\n2\nq\n");

    APIController controller = new AlphaVantageAPICallController(model, view, in);
    controller.execute();
    assertEquals(expected.toString(), out.toString());
  }
}

