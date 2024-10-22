package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Features;

/**
 * class JFrameView that builds guis to use/implement stock simulaition features.
 */
public class JFrameView extends JFrame implements IGuiView {
  private final JButton makePort;
  private final JButton api;
  private final JButton buyStock;
  private final JButton sellStock;
  private final JButton valueAndCompo;
  private final JButton quitButton;

  /**
   * Constructor that intializes the main window that pops up when the user runs the program.
   *
   * @param caption is the header caption that displays the gui Stock Simulation.
   */
  public JFrameView(String caption) {
    super(caption);
    makePort = new JButton("Make Portfolio");
    buyStock = new JButton("Buy Stock");
    sellStock = new JButton("Sell Stock");
    valueAndCompo = new JButton("Get Value and Composition of Portfolio");
    quitButton = new JButton("Exit");
    api = new JButton("Get Stocks");

    quitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    headerPanel.add(new JLabel("Choose which stock features to use:"));

    JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
    buttonsPanel.add(api);
    buttonsPanel.add(makePort);
    buttonsPanel.add(buyStock);
    buttonsPanel.add(sellStock);
    buttonsPanel.add(valueAndCompo);
    buttonsPanel.add(quitButton);


    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(headerPanel, BorderLayout.PAGE_START);
    mainPanel.add(buttonsPanel, BorderLayout.CENTER);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    getContentPane().add(mainPanel);

    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  /**
   * adds features to the existing gui for CMV.
   *
   * @param features is the individual features.
   */
  @Override
  public void addFeatures(Features features) {
    makePort.addActionListener(evt -> {
      features.makePort();
    });
    buyStock.addActionListener(evt -> {
      features.purchaseStock();
    });
    sellStock.addActionListener(evt -> {
      features.removeStock();
    });
    valueAndCompo.addActionListener(evt -> {
      features.getVandC();
    });
    api.addActionListener(evt -> {
      features.callBack();
    });
  }
}