package ui;

import dao.AccountDao;
import dao.LoanDao;
import manager.SystemManager;
import manager.account.AccountType;
import manager.account.LoanAccount;
import manager.entity.Collateral;
import manager.entity.Result;
import manager.timer.Timer;
import manager.timer.TimerObserver;
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


public class LoanPage implements IPages, TimerObserver {

    JFrame frame;
    SystemManager systemManager = SystemManager.getInstance();
    JLabel balance = new JLabel();
    JLabel loan = new JLabel();
    LoanAccount loanAccount;
    JLabel time = new JLabel();
    public LoanPage(){

        frame = new JFrame("Welcome to the bank.");
        JPanel panel = new JPanel();
        placePanelComponents(panel);
        frame.setSize(1000, 600);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();
        frame.setVisible(true);
        Timer.getInstance().addTimerObserver(this);
    }

    private void placePanelComponents(JPanel panel){


        panel.setLayout(null);

        time = new JLabel(Timer.getInstance().getTimeStr());
        time.setBounds(0,0,160,25);
        panel.add(time);
        final List<Collateral> collateralList = SystemManager.getInstance().getCollateralList().getData();

        systemManager.chooseAccount(AccountType.LOAN.getAccountType());
        LoanAccount loanAccount = (LoanAccount) systemManager.getCurrentAccount();

        JLabel priceLabel=new JLabel("Price:");
        priceLabel.setBounds(400,200,200,50);

        balance = new JLabel();
        balance.setBounds(400,250,200,50);

        balance.setText("balance:"  + systemManager.getCurrentAccount().getBalance());

        loan = new JLabel();
        loan.setBounds(600,250,200,50);

        loan.setText("loan:"  + loanAccount.getLoan());


        JLabel textLabel=new JLabel("current collaterals");
        textLabel.setBounds(200,300,200,25);

        JTextArea jTextArea = new JTextArea();
        jTextArea.setBounds(200,350, 500,200);
        jTextArea.setText("");

        jTextArea.setText(loanAccount.getCurrentLoanListStr());





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
                if(index != -1){
                    priceLabel.setText("Price:"+priceList.get(index).toString());
                    type.setText(collateralList.get(index).getType()+"");
                }

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

                int typeId = Integer.parseInt(String.valueOf(type.getText()));

                Result<Void> loaning = systemManager.loan(typeId);

                if(loaning.isSuccess()) {
                    list.clearSelection();
                    priceLabel.setText("Price");
                    balance.setText("balance:" + systemManager.getCurrentAccount().getBalance());
                    loan.setText("loan:"  + loanAccount.getLoan());
                    jTextArea.setText(loanAccount.getCurrentLoanListStr());
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
        panel.add(balance);
        panel.add(loan);
        panel.add(textLabel);
        panel.add(jTextArea);
    }


    @Override
    public void timeChange() {
        balance.setText("balance:" + systemManager.getCurrentAccount().getBalance());
        if(systemManager.getCurrentAccount() instanceof LoanAccount){
            loan.setText("loan:"  + ((LoanAccount)(systemManager.getCurrentAccount())).getLoan());
        }
    }
}
