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

public class StockPage implements IPages{

    JFrame frame;
    SystemManager systemManager = SystemManager.getInstance();
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

        List<Stock> stockList = FileUtils.readStockList();

        JLabel priceLabel=new JLabel("Price:");
        priceLabel.setBounds(400,250,200,50);

        JLabel label=new JLabel("Available collaterals");
        label.setBounds(400,30,200,25);

        Vector priceList=new Vector();
        Vector nameList=new Vector();
        JList list=new JList(nameList);

        for(int i = 0; i< stockList.size(); i++){
            priceList.addElement(stockList.get(i).getPrice());
            nameList.addElement(stockList.get(i).getName());

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
        JButton editButton = new JButton("Edit");
        editButton.setBounds(0, 250, 150, 50);
        editButton.addActionListener(new ActionListener() {
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



        scrollPane.setBounds(400,100,200,50);
        JButton addButton = new JButton("Add");
        addButton.setBounds(0, 350, 150, 50);
        addButton.addActionListener(new ActionListener() {
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



        scrollPane.setBounds(400,100,200,50);
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(850, 250, 150, 50);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=list.getSelectedIndex();// the index of collaterals
                if(true) {
                    nameList.remove(index);
                    priceList.remove(index);
                    list.updateUI();
                    priceLabel.setText("Price");
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
        panel.add(editButton);
        panel.add(priceLabel);
        panel.add(backButton);
    }


}

