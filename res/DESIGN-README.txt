The design of the Stock Simulator. The stock Simulator program follows and MVC, model, view , and
controller, implementation that uses the main method, StockProgram to initialize. Note this is an
extension of version 1.1.0, version 2.1.0, currently in version 3.2.0

# no changes was made on existing code but instead just extended #

# Version 3.2.0

                        ----- The Controller ------
# Version 3.2.0
This package, has a new interface and controller. The Controller.Features interface holds new
features that allow a user to interact with the program in such a way that is GUI instead of TUI.
Of course this depends on whether or not the program detects '-text' when program is ran. If so the program
runs version 2.1.0 as a text based interface, if no argument is detected which is the default, it will
run version 3.2.0 with the new gui. The new gui Controller.GuiController is designed to interact with the user and
use those inputs to update, retrieve, and read XML files that are being kept track of as objects.
Methods are designed in such a way for them to just be called do--- in the view for simplification.
No existing code is edited instead the old controller was just adapted to take in parameters that are
gui compatible and not text compatible. the way we have it is a user has to do api call on stock, and then buy...
This was our way to limit use on internet so a person could api call x amount of stocks for x amount of days
and just proceed with the program buying stocks that exist in csv as to not have the buy method rely on the internet.
we wanted to separate the coupling as to have more freedom in testing and more user freedom to buy stocks
over and over again without hitting that daily limit(assuming a person uses a free key)/ without relying on
api every single time we call buy stock. so to continue with our design we wanted our new method to just
as explained be a dropdown menu from existing stock by just reading all files that end in csv. (stock)
and just display file name (ticker) as a combo box for user simplification/ friendliness. Ofcourse we
would have a separate button that uses the api as to with hold program integrity of being an internet usage design.
 although we did not want to have methods solely rely on this, so we chose to have a design that was able to
 exist without a constant reliance on api.

                        ----- The View -----
# Version 3.2.0
This package, on top of the existing implementations provided by the Version 2.1.0 controller, now with
Version 3.2.0 provides View.IGuiView interface and View.JFrameView class. The JFrameView class is in
charge of the creation of all the program's button and the main panels organization and feature creation.
With one method that uses lambda to create features in such a way that is simple, organized and short enough
as to be unreadable. The new gui view just allows its features to be called in the controller. It is worth
mentioning the change in adaption was going from a scanner reading input from command line, to reading
input from a GUI text box/ drop down menu.

                        ----- The Model -----
# Version 3.2.0
This package, remains the same. In our design no new methods were added instead our controller and
view were created in such a way to work around existing working methods as to avoid duplicate code.



# Version 2.1.0

                        ----- The Controller ------
# Version 2.1.0
This package, on top of its already existing implementation now has a new advance controller that
calls on an abstract in which objects of the older controller are referenced in order to produce
previous features results. New features are also in the abstract named do{nameHere}() so our
advanced controller, which extends the abstract can just call the methods as this.do{nameHere}
in a switch statement to be organized and to keep the switch short. The advance stock controller
is implemented by only having one execute method which is called in the StockProgram class to
initialize the program.

                         ----- The View -----
# Version 2.1.0
This package, on top of its already existing implementation now has a new AdvancedPortfolio class that
takes care of editing the portfolios. These methods are void but don't necessarily mutate. It is more
accurate to describe the buy and sell as methods that override the existing XML portfolio returning
a new portfolio object. Of course this is paired up with an interface with its methods. We also
implemented PortBalImpl class that is meant to take in, and track, more parameters, such as budget, portfolio
of a AdvancedPortfolio object, etc in order to perform re-balance feature. It was much easier to
track of all the information in a new more advanced object after call the existing save portfolio
methods to override the XML to perform the re-balance. The model also has a newly implemented class
and interface for StockTransactionImpl. StockTransactionImpl is what we choose to keep track of a
stock object. In a way being the intermediary step from CSV -> stock -> XML. This made it possible
for our Advanced Portfolio to have a Map of StockTransactionImpl and String name to easily hold
for the creation of the portfolio XML.



                            ----- The View -----
# Version 2.1.0
This package, on top of its already existing implementation now has a new ViewAdvancedImpl and interface
that display updated messages such as the updated welcome message and menu. Fortunately our earliest
version of the view was designed in such a way were this class did not need much addition. The advanced
view only holds 3 methods, but we still felt a necessity to include this implementation to support
SOLID principles.




# Version 1.1.0

                             ----- The controller -----
# Version 1.1.0
This package is on the API caller that handles the calls and the different key inputs. There exist
a void executes that has the features nested. A user is given a variety of commands to choose from
that access both the model and the view packages. Each character case, if found to be true,
immediately calls the view to display the correct next message for the next input. After each input the
model is called to pass some sort of action to be displayed later. We used a scanner in order to read the
user's input before delegating them to their corresponding classes. We did decide to hardcode the API key as we
believed it would have been easier for the user to just use the program without having to worry
about creating the new key / inputting a new key. Within this controller class we have some code
that does test for edge cases however not all extreme edge cases are accounted for. While the
program does do API calls we have implemented checkers to see if a csv file for that stock exist. If
a file exist and the amount of days supersedes the new days in which you are trying to call, no API
call is called. This was done to remove coupling between user/program and internet use. We aspired
to model this program to be as least depending on the internet as possible. All methods are
delegated to the controller to satisfy CMV. The controller is composed of one main interface holding
the main method.

                                ----- The model -----
# Version 1.1.0
This package is in charge of StockModelImpl class, and portfolio objects as well as the methods that go
behind the assignment. The model is the heart of our simulation. The Portfolio class is designed in
such a way to avoid mutation as much as possible and instead create new objects with the updated
fields through private helpers and return those new portfolios/CSV which replace the already
existing ones. This gives the illusion of a sort of mutation when in reality the process just mask
object-oriented design by creating new immutable objects. The StockModelImpl class aims to have a
similar approach of minimal mutation. This class implements a few java libraries for easier implementation.
The StockModelImpl is the core of our data analytics, it is in charge of stock related calculations.
Meanwhile, the Portfolio class manages the portfolio related operations. The model is composed of two
interfaces that the stock and portfolio implement

                                ----- The view -----
# Version 1.1.0
This view is in charge of the TUI. Although the following program is not a GUI, we have chosen to
include this as a future implementation of this program could be GUI. However, the view mostly
takes care of the output messages, which is called in the controller, that prompt a user to input
the next command and also is in charge of outputting the messages called by model methods'. Overall,
this package is the smallest as it does not do much however the controller is very much coupled with the view.
