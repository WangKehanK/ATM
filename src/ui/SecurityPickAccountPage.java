package ui;


import manager.SystemManager;
import manager.account.Account;
import manager.account.AccountType;
import manager.entity.Result;
import manager.timer.Timer;
import manager.timer.TimerObserver;
import manager.user.Consumer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * This file is for SecurityPickAccountPage class that implements IPages interface and timer observer
 * contains all the functions we need for SecurityPickAccountPage class
 * provides the UI for SecurityPickAccountPage
 */
public class SecurityPickAccountPage implements IPages, TimerObserver {
    JFrame frame;
    JLabel time = new JLabel();
    int currencyType;
    SystemManager systemManager = SystemManager.getInstance();

    public SecurityPickAccountPage(){
        frame = new JFrame("Welcome to the bank.");
        JPanel panel = new JPanel();
        placePanelComponents(panel);

        frame.add(panel);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.repaint();
        frame.setVisible(true);

        this.currencyType = Account.USD;
        Timer.getInstance().addTimerObserver(this);
    }

    @Override
    public void timeChange() {
        time.setText(Timer.getInstance().getTimeStr());
    }


    private void placePanelComponents(JPanel panel){
        panel.setLayout(null);

        time = new JLabel(Timer.getInstance().getTimeStr());
        time.setBounds(0,0,160,25);
        panel.add(time);


        Vector currencyList=new Vector();
        currencyList.addElement("USD");
        currencyList.addElement("EURO");
        currencyList.addElement("CNY");

        JList list=new JList(currencyList);

        JScrollPane scrollPane=new JScrollPane(list);
        scrollPane.setBounds(350,200,100,50);

        JTextField textField=new JTextField();
        textField.setBounds(500,200,100,50);


        JLabel label = new JLabel("Please pick your account to transfer to security Account");
        label.setBounds(200,200,300,50);


        JLabel balanceLabel = new JLabel(((Consumer)systemManager.getCurrentUser()).getBalance());
        balanceLabel.setBounds(120,80,1600,25);
        panel.add(balanceLabel);

        JButton savingButton = new JButton("Saving Account");
        savingButton.setBounds(100, 150, 160, 50);
        savingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=list.getSelectedIndex();
                Result<Account> result = systemManager.createSecurityAccount(AccountType.SAVING.getAccountType(), Integer.parseInt(textField.getText()), index + 1);
                if(result.isSuccess()){
                    JOptionPane.showMessageDialog(null,"create succeed!.","create Account ",JOptionPane.PLAIN_MESSAGE);
                    frame.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null,result.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(savingButton);

        JButton checkingButton = new JButton("Checking Account");
        checkingButton.setBounds(100, 200, 160, 50);
        checkingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=list.getSelectedIndex();
                Result<Account> result = systemManager.createSecurityAccount(AccountType.SAVING.getAccountType(), Integer.parseInt(textField.getText()), index + 1);
                if(result.isSuccess()){
                    JOptionPane.showMessageDialog(null,"create succeed!.","create Account ",JOptionPane.PLAIN_MESSAGE);
                    frame.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null,result.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(checkingButton);


        JButton backButton = new JButton("back");
        backButton.setBounds(100, 420, 210, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

            }
        });
        panel.add(backButton);




        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panel.add(scrollPane);
        panel.add(textField);


    }

//    Result<Account> result =systemManager.createSecurityAccount();
//
//                if(result.isSuccess()){
//        frame.dispose();
//        JOptionPane.showMessageDialog(null,"Loan Account successfully created!.","Loan Account ",JOptionPane.PLAIN_MESSAGE);
//    }
//                else{
//        JOptionPane.showMessageDialog(null,"Loan Account existed!.","Error ",JOptionPane.ERROR_MESSAGE);
//    }
}
