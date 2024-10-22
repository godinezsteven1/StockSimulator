import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


import model.AdvancePortfolio;
import model.StockTransactionImpl;


import static org.junit.Assert.assertEquals;

/**
 * test class to test the new feautres that involves Portfolios.
 */
public class TestXML {
  StockTransactionImpl stock1;
  StockTransactionImpl stock2;
  AdvancePortfolio portfolio;
  AdvancePortfolio portfolioEx;
  AdvancePortfolio portfolioEx2;
  StockTransactionImpl boughtStock;
  StockTransactionImpl boughtStock2;

  /**
   * intializes an example Portoflio for testing.
   */
  @Before
  public void init() {
    stock1 = new StockTransactionImpl("MMM", "2024-04-03", 93.1900);
    stock2 = new StockTransactionImpl("GOOG", "2024-06-03", 174.4200);
    HashMap<StockTransactionImpl, Double> shares = new HashMap<>();
    shares.put(stock1, 50.0);
    shares.put(stock2, 1.0);
    portfolio = new AdvancePortfolio("qwerty", shares);
  }


  /**
   * tests that a Portoflio will be saved.
   */
  @Test
  public void testSavePort() {
    portfolio.savePorts("res");

    String filePath = "res/" + portfolio.getName() + ".xml";
    File xmlFile = new File(filePath);

    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);

      doc.getDocumentElement().normalize();

      // Check portfolioName
      String portfolioName = doc.getElementsByTagName("portfolioName")
              .item(0).getTextContent();
      assertEquals("qwerty", portfolioName);

      // Check stocks
      NodeList stockList = doc.getElementsByTagName("stock");
      for (int i = 0; i < stockList.getLength(); i++) {
        Element stockElement = (Element) stockList.item(i);
        String name = stockElement.getAttribute("name");
        String value = stockElement.getAttribute("value");

        if (name.equals(stock1.toString())) {
          assertEquals("50", value);
        } else if (name.equals(stock2.toString())) {
          assertEquals("1", value);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * making an example to test adding a share.
   */
  @Before
  public void init2() {
    portfolioEx = new AdvancePortfolio("Exp");
    boughtStock = new StockTransactionImpl("GOOG",
            "2024-06-06", 178.3500);
    portfolioEx.addStock(boughtStock, 2);
    portfolioEx.addSavePorts("res");
  }


  /**
   * testing that adding a share works.
   */
  @Test
  public void testAddStock() throws ParserConfigurationException, IOException, SAXException {
    portfolioEx.savePorts("res");
    String filePath = "res/" + portfolioEx.getName() + ".xml";
    File xmlFile = new File(filePath);


    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(xmlFile);

    doc.getDocumentElement().normalize();


    assertEquals(boughtStock, portfolioEx.getStocks());

  }

  /**
   * creates an example to remove the stock.
   */
  @Before
  public void init3() {
    portfolioEx = new AdvancePortfolio("Exp");
    boughtStock = new StockTransactionImpl("GOOG",
            "2024-06-06", 178.3500);
    portfolioEx.addStock(boughtStock, 2);
    portfolioEx.addSavePorts("res");
    portfolioEx.sellRemoveStock("2024-06-06", "GOOG", 2, "res");
  }

  /**
   * test that removing stocks words.
   */
  @Test
  public void testRemoveStock() {
    portfolioEx.savePorts("res");
    String filePath = "res/" + portfolioEx.getName() + ".xml";
    File xmlFile = new File(filePath);
    HashMap<StockTransactionImpl, Double> emptyShares = new HashMap<>();
    assertEquals(emptyShares, portfolioEx.getStocks());
  }


  /**
   * creates an example to test port.
   */
  @Before
  public void init4() {
    boughtStock = new StockTransactionImpl("GOOG",
            "2024-06-06", 178.3500);
    HashMap<StockTransactionImpl, Double> shares = new HashMap<>();
    shares.put(boughtStock, 2.0);
    portfolioEx2 = new AdvancePortfolio("Expa", shares);
  }

  /**
   * testing to find the value of the Portoflio.
   */
  @Test
  public void testVal() {

    String date = "2024-06-06";
    portfolioEx2.savePorts("res");
    String filePath = "res/" + portfolioEx2.getName() + ".xml";
    File xmlFile = new File(filePath);


    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(new File(filePath));
      XPathFactory xPathfactory = XPathFactory.newInstance();
      XPath xpath = xPathfactory.newXPath();

      NodeList stockNodes = (NodeList) xpath.evaluate("//stock", document,
              XPathConstants.NODESET);

      // Check portfolioName
      String portfolioName = document.getElementsByTagName("portfolioName")
              .item(0).getTextContent();
      assertEquals("Expa", portfolioName);



      double portValue = 0.0;
      for (int i = 0; i < stockNodes.getLength(); i++) {
        Node stockNode = stockNodes.item(i);
        String stockDate = xpath.evaluate("date", stockNode);
        if (stockDate.compareTo(date) <= 0) {
          double closingPrice = Double.parseDouble(xpath.evaluate("close-price",
                  stockNode));
          int shares = Integer.parseInt(xpath.evaluate("Amount-of-stock", stockNode));
          portValue += closingPrice * shares;
        }
      }
      assertEquals(356.70, portValue);

    } catch (ParserConfigurationException | SAXException | IOException
             | XPathExpressionException e) {
      e.printStackTrace();
    }
  }

  /**
   * make example for distrubtion test.
   */
  @Before
  public void init5() {
    boughtStock = new StockTransactionImpl("GOOG",
            "2024-06-06", 178.3500);
    HashMap<StockTransactionImpl, Double> shares = new HashMap<>();
    boughtStock2 = new StockTransactionImpl("META",
            "2024-06-06", 178.3500);
    shares.put(boughtStock, 2.0);
    portfolioEx2 = new AdvancePortfolio("Expa", shares);
  }

  /**
   * test the distrubution of a portfolio.
   */
  @Test
  public void testDis() throws ParserConfigurationException,
          IOException, SAXException, XPathExpressionException {

    portfolioEx2.savePorts("res");
    String filePath = "res/" + portfolioEx2.getName() + ".xml";
    File xmlFile = new File(filePath);

    String date = "2024-06-06";
    AdvancePortfolio portfolio = new AdvancePortfolio("Expa");
    portfolio = portfolio.loadPortfolio("res", "Expa");


    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(new File(filePath));
    XPathFactory xPathfactory = XPathFactory.newInstance();
    XPath xpath = xPathfactory.newXPath();

    NodeList stockNodes = (NodeList) xpath.evaluate("//stock", document,
            XPathConstants.NODESET);


    assertEquals(123, portfolio.getDistributio(date));


  }





}



