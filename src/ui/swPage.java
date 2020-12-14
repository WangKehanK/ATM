package ui;

import manager.SystemManager;
import manager.account.Account;
import manager.entity.Result;

import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class swPage implements IPages{

    JFrame frame;
    PickAccountPage.OPTION_TYPE option_type;
    PickAccountPage.ACCOUT_TYPE accout_type;
    int currencyType;
    SystemManager systemManager = SystemManager.getInstance();

    //Customer customer;
    public swPage(PickAccountPage.OPTION_TYPE option_type,PickAccountPage.ACCOUT_TYPE account_type){
        this.option_type=option_type;
        this.accout_type=accout_type;
        this.currencyType = Account.USD;

        frame = new JFrame("Welcome to the bank.");
        JPanel panel = new JPanel();
        frame.setSize(1000, 600);

        placePanelComponents(panel);

        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.repaint();
        frame.setVisible(true);
    }

    private void placePanelComponents(JPanel panel){

        panel.setLayout(null);

        JLabel label = new JLabel("Welcome.");
        label.setBounds(400,50,200,50);
        //panel.add(label);

        JLabel type = new JLabel("USD");
        type.setBounds(300,120,80,25);
        panel.add(type);

        JTextField number = new JTextField(20);
        number.setBounds(400,120,165,25);
        panel.add(number);

        JTextField userNameText = new JTextField(20);;
        JTextField accountIdText = new JTextField(20);
        if(option_type == PickAccountPage.OPTION_TYPE.TRANSFER){
            JLabel usrName = new JLabel("userName");
            usrName.setBounds(300,150,80,25);
            panel.add(usrName);


            userNameText.setBounds(400,150,165,25);
            panel.add(userNameText);

            JLabel accountId = new JLabel("accountId");
            accountId.setBounds(300,180,80,25);
            panel.add(accountId);


            accountIdText.setBounds(400,180,165,25);
            panel.add(accountIdText);
        }

        JButton usdButton = new JButton("USD");
        usdButton.setBounds(0, 150, 150, 50);
        usdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemManager.chooseCurrency(Account.USD);
                type.setText("USD");
                currencyType = Account.USD;
            }
        });



        JButton euroButton = new JButton("EURO");
        euroButton.setBounds(0, 250, 150, 50);
        euroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemManager.chooseCurrency(Account.EURO);
                type.setText("EURO");
                currencyType = Account.EURO;
            }
        });
        //panel.add(transferButton);

        String option="";

        if(option_type== PickAccountPage.OPTION_TYPE.SAVE)
            option="Save";
        else if(option_type== PickAccountPage.OPTION_TYPE.WITHDRAW)
            option="Withdraw";
        else if(option_type== PickAccountPage.OPTION_TYPE.TRANSFER)
            option="transfer";

        JButton optionButton = new JButton(option);
        optionButton.setBounds(850, 250, 150, 50);
        optionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(option_type== PickAccountPage.OPTION_TYPE.SAVE){
                    int savingNumber = Integer.parseInt(String.valueOf(number.getText()));
                    Result<Void> saving = systemManager.saving(savingNumber);
                    if(saving.isSuccess()){
                        JOptionPane.showMessageDialog(null,"success","Saving",JOptionPane.PLAIN_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null,saving.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
                    }
                }else if(option_type== PickAccountPage.OPTION_TYPE.WITHDRAW){
                    int drawNumber = Integer.parseInt(String.valueOf(number.getText()));
                    Result<Void> drawResult = systemManager.draw(drawNumber);
                    if(drawResult.isSuccess()){
                        JOptionPane.showMessageDialog(null,"success","Drawing",JOptionPane.PLAIN_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null,drawResult.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
                    }
                }else if(option_type== PickAccountPage.OPTION_TYPE.TRANSFER){
                    int transferNumber = Integer.parseInt(String.valueOf(number.getText()));
                    String userName = String.valueOf(userNameText.getText());
                    String accountId = String.valueOf(accountIdText.getText());

                    Result<Void> transfer = systemManager.transfer(userName, accountId, transferNumber, currencyType);
                    if(transfer.isSuccess()){
                        JOptionPane.showMessageDialog(null,"success","Transfer",JOptionPane.PLAIN_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null,transfer.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        JButton cnyButton = new JButton("CNY");
        cnyButton.setBounds(0, 350, 150, 50);
        cnyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemManager.chooseCurrency(Account.CNY);
                type.setText("CNY");
                currencyType = Account.CNY;
            }

        });


        JButton backButton = new JButton("Back");
        backButton.setBounds(850, 350, 150, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerPage();
            }
        });
        //panel.add(withdrawButton);


        panel.add(label);
        panel.add(usdButton);
        panel.add(euroButton);
        panel.add(optionButton);
        panel.add(cnyButton);
        panel.add(backButton);

    }


}
