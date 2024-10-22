This program version, 3.2.0, has added the controller.Features, controller.GuiController, view.IGuiView,
and view.JFrameView. This program and its entirety of its contents was created by Steven Godinez
and Alex Voung on Wednesday, June 19 2024.

# note no changes were made on existing code, just extensions were made.

# in version 3.2.0
The controller.Features is the new interface, compatible and built around with the design of a gui
in mind. Controller.Feature only has seven methods which are meant to do the logic of the transferred
and adapted methods. This class is implemented by controller.GuiController . The new controller
offers the following methods. The existing '-text' methods are still the same which are in version 2.1.0
This explanation only offers interface features that are public
:                  *note the --help is not included here*

        # Version 1.1.0 : Scroll down to see Version 2.1.0  (line 53) : Scroll down to see Version 3.2.0 (line 114)
    - execute() : Initializes program with no parameters and allows the for the following features.
                  if inputted a number and enter, it considers that as the amount of days to call
                  back, followed by a Stock Ticker and enter, creating an API call of that stock
                  and saving it to a csv file. Once stock(s) exist in a csv file a user will have
                  the options of inputting different commands in the TUI(text user interface).
                  The first command the user will have access to is the gain or loss feature.
                  Feature can be accessed by entering number of day, followed by Ticker.

                  The gain or loss command feature allows a user to examine the gain or loss of a
                  stock over a specified period. The gain or loss feature will input ticker,
                  starting date and  end date in 'yyyy-mm-dd' format. please note the end date will
                  be the most recent date.
                  Feature can be accessed by entering 'g' enter, followed by Ticker, starting date,
                  and end date.

                  The moving average command feature allows a user to examine the x-day moving
                  average of a  stock for a specified date and a specified value of x. The method
                  will take in ticker, date and amount of days to go backwards. Note: date will
                  have to be passed in format 'yyyy-mm-dd'.
                  Feature can be accessed by entering 'm', followed by Ticker, date, and days.

                  The crossovers command feature allows a user to determine which days are x-day
                  crossovers for a specified stock over a specified date range and a specified
                  value of x. This feature takes in the stock ticker, starting date, end date, and
                  amount of days to check between ranges. Note: date will have to be passed in
                  format 'yyyy-mm-dd'
                  Feature can be accessed by entering 'c', followed by ticker, starting date, end
                  date, days.

                  The Portfolio creation command features allows a user to create as many Portfolios
                  and add as many Tickers into the Portfolio as possible. They can exit out of this by
                  pressing 'f', and when they want to see the value of the Portfolio, then the user can
                  enter a date, and its value of the Portfolio.

                  The user can reuse all these commands and if the user wants to quit using the Program,
                  they can press 'q'.

                  # Features in Version 2.1.0

                  The updated Portfolio creation command feature allows a user to create as many
                  empty portfolios and saves them to the directory of the user's choice. We decided
                  to limit the user to only saving portfolios in res, out, src. We also changed the
                  portfolio type from csv to XML. The XML is composed of portfolio name, and stocks
                  where a stock represents, ticker, date, closing price, and amount of stock.
                  # Feature works, endorsed by creators.

                  The portfolio value method returns the value of a portfolio at a given date.
                  It does not matter what stock it is, as long as the date matches, the method
                  calculates the value of all stock and amount of stock, and returns that value.
                  If no date matches, method returns zero.
                  # Feature works, endorsed by creators.

                  The Buy stock command allows a user to create a stock and assign it to an existing
                  portfolio. The user has to input ticker, date, and amount of stock and controller
                  will return the cost of a single cost and the total cost a user spent to buy that
                  stock. This method will overwrite the existing portfolio XML to add that stock.
                  # Feature works, endorsed by creators.

                  The Sell Stock command allows a user to sell a specific amount of stock from an
                  existing portfolio. The user has to input, portfolio name, portfolio location,
                  date, ticker, amount of stock to sell. If user inputs an amount greater than total
                  amount of current stock, the program will sell all stock associated with that
                  ticker. This method overwrites XML portfolio file and removes that file / updates
                  the amount of stocks.
                  # Feature works, endorsed by creators.

                  The Composition command allows a user to view the composition of a stock on a given
                  date. A user will input date, location, date and console will return stocks that
                  match that date and the amount of stock that goes with it.
                  # Feature works, endorsed by creators.

                  The Distribution Command is similar to composition but instead of returning stock
                  and amount of stock. This method returns the total monetary value of all the
                  stocks at a given date, and calculates the percentage of composition compared to
                  the entirety of the portfolio. Where the entire portfolio is 100% of the value
                  and the days that match get their price calculated according to their price and
                  amount which in term returns the percentage of 100 that it builds up. Also showing
                  the percentage that all the matching dates add up to out of 100 and their added
                  price.
                  # Feature works, endorsed by creators.

                  The Re-balance command allows a user to access a specific portfolio, assign it a
                  budget and one by one assign each stock of different date, a percentage of the
                  total budget it should be. All percentages must add to 100. Note that the program
                  will display the percentage left to reach 100. This was built in hopes of if a
                  user buys or sells stocks and that assigned percentage changes, the re-balance
                  will automatically buy / sells stocks even at fractions in order to satisfy both
                  percentages and staying within budget.
                  # Feature is implemented however buggy and does not produce the right answer.

                  The Chart commands allows a user to input a stock or a portfolio and a date range
                  in which to access said object in order to display growth or decay of a given
                  stock / portfolio through asterisk that automatically compute a key value to
                  assign said asterisk. Note the method may return anywhere from 5 to 30 lines.
                  if given any range below 5 days, not enough information error will be thrown, as
                  the API call in this program is daily and cannot return by the hour.
                  # Feature is implemented however buggy and does not produce the right answer.

                  # Features in Version 3.2.0

                  The 'Make Portfolio' button allows a user to input a location and a portfolio name.
                  This in turn creates a portfolio in a dropdown menu of either res, src, or out.
                  Then user puts in the name creating an empty portfolio in their location.
                  # Feature works, endorsed by creators.

                  The 'Get Stocks' button allows a user to api call a new stock. A user just needs
                  to put in a stock ticker and amount of days as a ticker and click enter. This
                  creates stocks as a csv file once the user clicks 'Fetch'.
                  # Feature works, endorsed by creators.

                  The 'Sell Stocks' button allows a user to sell stocks from an existing portfolio
                  that a user bought. A user has to insert from a dropdown menu the ticker, the year,
                  the month, the day and the portfolio location. Additionally, the user also has to
                  input the number of shares and the name of the portfolio.
                  # Feature works, endorsed by creators.

                  The 'Get value and composition of portfolio' button allows a user to get composition
                  and the value of a portfolio choosing location, year, month, day from a dropdown menu.
                  A user then clicks calculate and the composition and total price at that given day of
                  given portfolio is displayed in the same panel.
                  # Feature works, endorsed by creators.

                  The 'Buy Stock' button allows a user to purchase a stock from an existing csv and add
                  it to an existing portfolio. A user will choose ticker, year, month, day , and location
                  from a dropdown menu and input number of shares and portfolio name manually and click
                  purchase to complete the transaction.
                  # Feature works, endorsed by creators.

                  The 'Back' button in every sub panel allows a user to return to the main panel.
                  # Feature works, endorsed by creators.

                  The 'Exit' Button in the main panel terminates the program.
                  # Feature works, endorsed by creators.

                  * All other methods for the Text based interface are still the same functionality from
                  version 2.1.0 *




// nothing has changed from the model.
The model.AdcPortfolioInterface Interface offers the user the same various methods in which they could get
to edit a model.AdvancePortfolio
    - savePorts(String filePlace) : creates and saves empty portfolio
    - purchaseAddStock(String date, String ticker,int shares) : purchases stock on given information
    - sellRemoveStock(String date, String ticker, int shares, String filePath): sells / removes stock
                                                                                on given information.
    - displayComposition(String date) : returns the composition of a stock at a given day.
    - valueOfPortfolio(String date) :  gets the value of stock at a given day
    - getDistribution(String date) : returns the distribution of stock within that date
    - addSavePorts(String filePlace): overwrites XML file to add a stock/ update stock
    - loadPortfolio(String portfolioPath, String portfolioName): loads a portfolio into a Portfolio
                                                                 object from given information
# All of the above methods work.

Classes that implement this interface as of Wednesday, January 12th are as follows.
    - model.AdvancePortfolio
# All methods in AdvancePortfolio work

The model.StockTransaction class functions as our representation of the given stock.
Inheriting the methods from the parent interface allows one to get the individual fields.
Class is mostly used to be kept track of in the portfolio for an object-oriented design.
Features and usage include:
    - getTicker() :                     get for the date field.
    - getDate() :                       get for the date a stock was bought in.
    - getClosingPrice() :               get for the closing price on x day.
    - totalStockCost(int shares)() :    get the total cost of a stock.

# All methods in StockTransactionImpl work.

The model.PortBalImpl class functions as an extension to the current existing portfolio class as a
way to call re-balance on a portfolio. We chose this implementation of creating a new class because
we found ourselves needing to keep track of more fields than our initial portfolio intended for.
Features and Usage include:
    - getFilePath() :           get for the filepath field.
    - rebalancePortfolio() :    re-balances a portfolio with given field information
# RebalancePortfolio() has a buggy implementation
# All other methods in this class work.


The view.JFrameView class functions as our representation of the view class from the MVC control pattern.
Inheriting methods from the interface, the methods mostly pertain to some sort of button or panel
that allow us to display information and some of our existing methods from version 2.1.0 in a more
elegant manner
Features and Usage include:
    - void addFeatures(Features features) :  Creates feature for a given button.
# All methods in JFrameView work.