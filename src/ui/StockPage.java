package ui;

import manager.SystemManager;
import manager.entity.Collateral;
import manager.entity.Result;
import manager.entity.Stock;
import manager.timer.Timer;
import manager.user.Consumer;
import utils.FileUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
/**
 * This file is for StockPage class that implements IPages interface
 * contains all the functions we need for StockPage class
 * provides the UI for StockPage
 */
public class StockPage implements IPages{

    JFrame frame;
    SystemManager systemManager = SystemManager.getInstance();
    List<Stock> stockList;

    Vector priceList;
    Vector nameList;
    JList list;

    public StockPage(){

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

        stockList = systemManager.getStockList().getData();

        JLabel priceLabel=new JLabel("Price:");
        priceLabel.setBounds(400,250,200,50);
        JTextField priceText=new JTextField(20);
        priceText.setBounds(400,300,200,50);

        JLabel label=new JLabel("Available collaterals");
        label.setBounds(400,30,200,25);

        priceList=new Vector();
        nameList=new Vector();
        list=new JList(nameList);

        for(int i = 0; i< stockList.size(); i++){
            priceList.addElement(stockList.get(i).getPrice());
            nameList.addElement(stockList.get(i).getStockName());

        }
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = list.getSelectedIndex();
                priceText.setText(priceList.get(index).toString());

            }
        });

        JScrollPane scrollPane=new JScrollPane(list);


        scrollPane.setBounds(400,100,200,50);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(0, 250, 150, 50);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=list.getSelectedIndex();// the index of collaterals
                Stock stock = stockList.get(index);
                int price = Integer.parseInt(String.valueOf(priceText.getText()));
                Result<Void> result = systemManager.saveStock(stock.getStockId(), price);

                Result<List<Stock>> newStockList = systemManager.getStockList();
                stockList.clear();
                stockList.addAll(newStockList.getData());

                priceList.clear();
                nameList.clear();

                System.out.println(stockList);
                for(int i = 0; i< stockList.size(); i++){
                    priceList.addElement(stockList.get(i).getPrice());
                    nameList.addElement(stockList.get(i).getStockName());
                }

                if(result.isSuccess()){
                    JOptionPane.showMessageDialog(null,"Saving succeed!.","Edit Stock ",JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null,result.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        scrollPane.setBounds(400,100,200,50);
        JButton addButton = new JButton("Add");
        addButton.setBounds(0, 350, 150, 50);
        StockPage stockPage = this;
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StockAddPage(stockPage);
            }
        });



        scrollPane.setBounds(400,100,200,50);
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(850, 250, 150, 50);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=list.getSelectedIndex();// the index of collaterals
                if(true) {
                    Stock stock = stockList.get(index);
                    systemManager.delStock(stock.getStockId());
                    updateList();
                    priceText.setText("");
                    JOptionPane.showMessageDialog(null,"Delete succeed!.","Delete Stock ",JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Delete Failed!.","Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(850, 450, 150, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ManagerPage();
            }
        });

        panel.add(scrollPane);
        panel.add(deleteButton);
        panel.add(label);
        panel.add(addButton);
        panel.add(saveButton);
        panel.add(priceLabel);
        panel.add(backButton);
        panel.add(priceText);
    }

    public void updateList(){
        Result<List<Stock>> newStockList = systemManager.getStockList();
        stockList.clear();
        stockList.addAll(newStockList.getData());

        priceList.clear();
        nameList.clear();

        System.out.println(stockList);
        for(int i = 0; i< stockList.size(); i++){
            priceList.addElement(stockList.get(i).getPrice());
            nameList.addElement(stockList.get(i).getStockName());
        }

        list.updateUI();
    }


}

