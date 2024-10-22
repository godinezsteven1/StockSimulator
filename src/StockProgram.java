import java.io.InputStreamReader;
import controller.APIController;
import controller.AdvanceStockController;
import controller.GuiController;
import model.AdvPortfolioInterface;
import model.AdvancePortfolio;
import model.StockModel;
import model.StockModelImpl;
import view.IGuiView;

import view.JFrameView;
import view.ViewAdvanced;
import view.ViewAdvancedImpl;
import controller.AlphaVantageAPICallController;
import view.View;
import view.ViewImpl;

/**
 * Runs the program of the features that involve stock simulation.
 */
public class StockProgram {


  /**
   * This main function accepts command-line inputs, either -text, which lets
   * you interact with the text-based interface Stock Simulation or
   * the GUI-based interface of the Stock simulation.
   * Any other inputs in the command-line are invalid.
   * This connects the text-based interface(view), implemented features(model),
   * and the inputs by the user(controller).
   * @param args is how many days you want to go backwards to, to see that stocks,
   *             and the ticker of the company that you want to see their stocks.
   */
  public static void main(String[] args) {
    if (args.length > 1) {
      System.out.println("Expected one argument, but got: " + args.length);
    }
    if (args.length == 1) {
      if (args[0].equals("-text")) {
        StockModel model = new StockModelImpl();
        ViewAdvanced view = new ViewAdvancedImpl(System.out);
        Readable in = new InputStreamReader(System.in);
        APIController controller = new AdvanceStockController(model, view, in);
        controller.execute();
      }
    }
    else {
      AdvPortfolioInterface model = new AdvancePortfolio();
      GuiController controller = new GuiController(model);
      IGuiView view = new JFrameView("Stock Simulation");
      controller.setView(view);
    }
  }



  /**
   * connects the text-based interface(view), implemented features(model),
   * and the inputs by the user(controller).
   *
   * @param args is how many days you want to go backwards to, to see that stocks,
   *             and the ticker of the company that you want to see their stocks.
   */
  public static void oldMain(String[] args) {
    StockModel model = new StockModelImpl();
    View view = new ViewImpl(System.out);
    Readable in = new InputStreamReader(System.in);
    APIController controller = new AlphaVantageAPICallController(model, view, in);
    controller.execute();
  }
}