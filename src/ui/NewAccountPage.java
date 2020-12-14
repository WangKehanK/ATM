package ui;

import manager.SystemManager;
import manager.account.Account;
import manager.entity.Result;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewAccountPage{


    JFrame frame;
    PickAccountPage.OPTION_TYPE option_type;
    PickAccountPage.ACCOUT_TYPE accout_type;
    SystemManager systemManager = SystemManager.getInstance();
    //Customer customer;
    public NewAccountPage(PickAccountPage.ACCOUT_TYPE account_type){
        this.option_type= PickAccountPage.OPTION_TYPE.SAVE;
        this.accout_type=accout_type;

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

        JLabel label = new JLabel("Please enter how much you are going to save.");
        label.setBounds(400,50,500,50);
        //panel.add(label);

        JLabel type = new JLabel("USD");
        type.setBounds(300,120,80,25);
        panel.add(type);

        JTextField number = new JTextField(20);
        number.setBounds(400,120,165,25);
        panel.add(number);

        JButton usdButton = new JButton("USD");
        usdButton.setBounds(0, 150, 150, 50);
        usdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemManager.chooseCurrency(Account.USD);
                type.setText("USD");
            }
        });



        JButton euroButton = new JButton("EURO");
        euroButton.setBounds(0, 250, 150, 50);
        euroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemManager.chooseCurrency(Account.EURO);
                type.setText("EURO");
            }
        });
        //panel.add(transferButton);



        JButton optionButton = new JButton("Submit");
        optionButton.setBounds(850, 250, 150, 50);
        optionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int savingNumber = Integer.parseInt(String.valueOf(number.getText()));
                Result<Void> saving = systemManager.saving(savingNumber);
                if(saving.isSuccess()){
                    JOptionPane.showMessageDialog(null,"success","Saving",JOptionPane.PLAIN_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null,saving.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
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
            }

        });


        panel.add(label);
        panel.add(usdButton);
        panel.add(euroButton);
        panel.add(optionButton);
        panel.add(cnyButton);


    }


}
