package ui;

import manager.SystemManager;
import manager.entity.Collateral;
import manager.entity.Result;
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

public class LoanPage implements IPages{

    JFrame frame;
    SystemManager systemManager = SystemManager.getInstance();
    public LoanPage(){

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

        final List<Collateral>[] collateralList = new List[]{FileUtils.readCollateralList()};

        JLabel priceLabel=new JLabel("Price:");
        priceLabel.setBounds(400,250,200,50);

        JLabel label=new JLabel("Available collaterals");
        label.setBounds(400,30,200,25);

        Vector priceList=new Vector();
        Vector nameList=new Vector();
        JList list=new JList(nameList);

        for(int i = 0; i< collateralList[0].size(); i++){
            priceList.addElement(collateralList[0].get(i).getPrice());
            nameList.addElement(collateralList[0].get(i).getName());

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
        JButton LoanButton = new JButton("Loan");
        LoanButton.setBounds(850, 350, 150, 50);
        LoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=list.getSelectedIndex();// the index of collaterals

                Result<Void> loaning = systemManager.loan(index);

                if(loaning.isSuccess()) {
                    nameList.remove(index);
                    priceList.remove(index);
                    list.updateUI();
                    priceLabel.setText("Price");
                    JOptionPane.showMessageDialog(null,"Loan succeed!.","Error ",JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Loan Failed!.","Error ",JOptionPane.ERROR_MESSAGE);
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
        panel.add(LoanButton);
        panel.add(label);
        panel.add(priceLabel);
        panel.add(backButton);
    }


}
