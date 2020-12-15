package ui;

import manager.SystemManager;
import manager.entity.Result;
import manager.entity.Stock;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class StockAddPage implements  IPages{

    JFrame frame;
    SystemManager systemManager = SystemManager.getInstance();
    StockPage stockPage;
    public StockAddPage(StockPage stockPage){

        frame = new JFrame("Add Stock");
        JPanel panel = new JPanel();
        placePanelComponents(panel);
        frame.setSize(500, 600);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();
        frame.setVisible(true);

        this.stockPage = stockPage;
    }

    private void placePanelComponents(JPanel panel){


        panel.setLayout(null);

        JLabel stockIdLabel=new JLabel("stockId:");
        stockIdLabel.setBounds(50,50,200,50);
        JTextField idText=new JTextField(20);
        idText.setBounds(120,50,200,50);

        panel.add(stockIdLabel);
        panel.add(idText);

        JLabel stockNameLabel=new JLabel("stockName:");
        stockNameLabel.setBounds(50,150,200,50);
        JTextField nameText=new JTextField(20);
        nameText.setBounds(120,150,200,50);
        panel.add(stockNameLabel);
        panel.add(nameText);


        JLabel priceLabel=new JLabel("Price:");
        priceLabel.setBounds(50,250,200,50);
        JTextField priceText=new JTextField(20);
        priceText.setBounds(120,250,200,50);
        panel.add(priceLabel);
        panel.add(priceText);

        JButton addButton = new JButton("Add");
        addButton.setBounds(100, 350, 150, 50);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idText.getText();
                String name = nameText.getText();
                int price  =  Integer.parseInt(priceText.getText());

                Result<Void> result = systemManager.addStock(id, name, price);

                if(result.isSuccess()){
                    JOptionPane.showMessageDialog(null,"success","Saving",JOptionPane.PLAIN_MESSAGE);
                    stockPage.updateList();
                    frame.dispose();
                }else{
                    JOptionPane.showMessageDialog(null,result.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(addButton);

        JButton backButton = new JButton("back");
        addButton.setBounds(100, 450, 150, 50);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stockPage.updateList();
                frame.dispose();
            }
        });

        panel.add(backButton);
    }



}
