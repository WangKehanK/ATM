package ui;

import manager.SystemManager;
import manager.entity.Stock;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

/**
 * This file is for StockAccountPage class that implements IPages interface
 * contains all the functions we need for StockAccountPage class
 * provides the UI for StockAccountPage
 */

public class StockAccountPage implements IPages{

    JFrame frame;
    SystemManager systemManager = SystemManager.getInstance();
    public StockAccountPage(){

        frame = new JFrame("Welcome to the bank.");
        JPanel panel = new JPanel();
        placePanelComponents(panel);
        frame.setSize(1000, 600);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();
        frame.setVisible(true);
    }

    private void placePanelComponents(JPanel panel){


        panel.setLayout(null);

        List<Stock> stockList = systemManager.getStockList().getData();

        JLabel priceLabel=new JLabel("Price:");
        priceLabel.setBounds(400,250,200,50);


        JLabel amountLabel=new JLabel("Amount:");
        amountLabel.setBounds(400,300,200,50);
        JTextField amountText=new JTextField(20);
        amountText.setBounds(400,350,200,50);

        JLabel label=new JLabel("Available collaterals");
        label.setBounds(400,30,200,25);

        Vector priceList=new Vector();
        Vector nameList=new Vector();
        JList list=new JList(nameList);

        for(int i = 0; i< stockList.size(); i++){
            priceList.addElement(stockList.get(i).getPrice());
            nameList.addElement(stockList.get(i).getStockName());

        }
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = list.getSelectedIndex();
                priceLabel.setText("Price:"+priceList.get(index).toString());

            }
        });

        JScrollPane scrollPane=new JScrollPane(list);


        scrollPane.setBounds(400,100,200,50);


        JButton purchaseButton = new JButton("purchase");
        purchaseButton.setBounds(0, 250, 150, 50);
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=list.getSelectedIndex();// the index of collaterals
                if(true) {
                    nameList.remove(index);
                    priceList.remove(index);
                    list.updateUI();
                    priceLabel.setText("Price");
                    JOptionPane.showMessageDialog(null,"Edit succeed!.","Edit Stock ",JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Edit Failed!.","Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JButton sellButton = new JButton("sell");
        sellButton.setBounds(0, 350, 150, 50);
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=list.getSelectedIndex();// the index of collaterals
                if(true) {
                    nameList.remove(index);
                    priceList.remove(index);
                    list.updateUI();
                    priceLabel.setText("Price");
                    JOptionPane.showMessageDialog(null,"Add succeed!.","Add Stock ",JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Add Failed!.","Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(850, 450, 150, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CustomerPage();
            }
        });

        panel.add(scrollPane);
        panel.add(label);
        panel.add(sellButton);
        panel.add(purchaseButton);
        panel.add(priceLabel);
        panel.add(amountLabel);
        panel.add(amountText);
        panel.add(backButton);
    }


}

