package ui;

import dao.AccountDao;
import dao.LoanDao;
import manager.SystemManager;
import manager.account.AccountType;
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

/**
 * This file is for LoanPage class that implements IPages interface
 * contains all the functions we need for LoanPage class
 * provides the UI for Loan Page
 */


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

        if(systemManager.getCurrentUser().hasLoanAccount()){
            systemManager.chooseAccount(AccountType.LOAN.getAccountType());
        }else{
            //TODO:It is best to choose the loan interest rate
            systemManager.createLoanAccount(LoanDao.DAY_RATE);
        }

        final List<Collateral> collateralList = SystemManager.getInstance().getCollateralList().getData();

        JLabel priceLabel=new JLabel("Price:");
        priceLabel.setBounds(400,250,200,50);

        JLabel label=new JLabel("Available collaterals");
        label.setBounds(400,30,200,25);

        Vector priceList=new Vector();
        Vector nameList=new Vector();
        JList list=new JList(nameList);

        JTextField type = new JTextField();


        for(int i = 0; i< collateralList.size(); i++){
            priceList.addElement(collateralList.get(i).getPrice());
            nameList.addElement(collateralList.get(i).getName());

        }
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = list.getSelectedIndex();
                priceLabel.setText("Price:"+priceList.get(index).toString());
                type.setText(collateralList.get(index).getType()+"");

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

                if(index==-1){
                    JOptionPane.showMessageDialog(null,"Please select an item!.","Error ",JOptionPane.ERROR_MESSAGE);
                    return;
                }

//                int typeid=-1;
//                while(typeid<0||typeid>3) {// the valid range of typeid!
//                    String s = JOptionPane.showInputDialog(panel, "Please input valid typeid", "Typeid", JOptionPane.PLAIN_MESSAGE);
//                    if(!s.equals("")){
//                        typeid=Integer.parseInt(s);
//                    }
//                }
                int typeId = Integer.parseInt(String.valueOf(type.getText()));

                Result<Void> loaning = systemManager.loan(typeId);

                if(loaning.isSuccess()) {
                    nameList.remove(index);
                    priceList.remove(index);
                    list.updateUI();
                    priceLabel.setText("Price");
                    JOptionPane.showMessageDialog(null,"Loan succeed!.","Error ",JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null,loaning.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
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
