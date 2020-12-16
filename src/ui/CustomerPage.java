package ui;

import manager.SystemManager;
import manager.timer.Timer;
import manager.timer.TimerObserver;
import manager.user.Consumer;

import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This file is for CustomerPage class that implements IPages interface and timer observer
 * contains all the functions we need for CustomerPage class
 * provides the UI for Customer Page
 */

public class CustomerPage implements IPages, TimerObserver {

    JFrame frame;
    SystemManager systemManager = SystemManager.getInstance();
    Consumer consumer;
    JLabel time = new JLabel();
    public CustomerPage(){
        frame = new JFrame("Welcome to the bank.");
        JPanel panel = new JPanel();
        frame.setSize(1000, 600);

        placePanelComponents(panel);

        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.repaint();
        frame.setVisible(true);

        Timer.getInstance().addTimerObserver(this);

    }

    private void placePanelComponents(JPanel panel){

        consumer = (Consumer) systemManager.getCurrentUser();
        panel.setLayout(null);

        time = new JLabel(Timer.getInstance().getTimeStr());
        time.setBounds(0,0,160,25);
        panel.add(time);

        JLabel label = new JLabel("Welcome " + consumer.getUserName());
        label.setBounds(400,50,200,50);
        //panel.add(label);

        JButton balanceButton = new JButton("Balance");
        balanceButton.setBounds(0, 150, 150, 50);
        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Show balance

                String balance = consumer.getBalance();
                if(balance.length() > 0)
                    JOptionPane.showMessageDialog(null,balance,"Balance",JOptionPane.PLAIN_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null,"No existed account.","Error ",JOptionPane.ERROR_MESSAGE);

            }
        });
        //panel.add(balanceButton);

        JButton loanButton = new JButton("Loan");
        loanButton.setBounds(850, 150, 150, 50);
        loanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Customer Login
                frame.dispose();
                new LoanPage();

            }
        });
        //panel.add(loanButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(0, 250, 150, 50);
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Customer Login
                frame.dispose();
                new PickAccountPage(PickAccountPage.OPTION_TYPE.TRANSFER);
            }
        });
        //panel.add(transferButton);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(850, 250, 150, 50);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Customer Login
                frame.dispose();
                new PickAccountPage(PickAccountPage.OPTION_TYPE.SAVE);
            }
        });
        //panel.add(saveButton);

        JButton createButton = new JButton("Create Account");
        createButton.setBounds(0, 350, 150, 50);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Customer Login
                frame.dispose();
                new CreateAccountPage();
            }
        });


        JButton stockButton = new JButton("Stock");
        stockButton.setBounds(0, 450, 150, 50);
        stockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Customer Login
                frame.dispose();
                new StockAccountPage();
            }
        });
        //panel.add(createButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(850, 350, 150, 50);
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Customer Login
                frame.dispose();
                new PickAccountPage(PickAccountPage.OPTION_TYPE.WITHDRAW);
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(850, 450, 150, 50);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SystemManager.getInstance().logout();
                frame.dispose();
                new WelcomePage();
            }
        });
        //panel.add(withdrawButton);

        JButton exchangeButton = new JButton("Exchange");
        exchangeButton.setBounds(425, 450, 150, 50);
        exchangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ExchangePage();
            }
        });

        panel.add(label);
        panel.add(balanceButton);
        panel.add(loanButton);
        panel.add(transferButton);
        panel.add(saveButton);
        panel.add(createButton);
        panel.add(withdrawButton);
        panel.add(stockButton);
        panel.add(logoutButton);
        panel.add(exchangeButton);

    }


    @Override
    public void timeChange() {
        time.setText(Timer.getInstance().getTimeStr());
    }
}
