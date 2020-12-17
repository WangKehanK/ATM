package ui;

import manager.SystemManager;
import manager.account.Account;
import manager.account.AccountType;
import manager.entity.Result;
import manager.timer.Timer;
import manager.timer.TimerObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This page provides options to choose which account the customers want to use while they save, withdraw...
 */


public class PickAccountPage implements IPages, TimerObserver {


    JFrame frame;
    OPTION_TYPE type;
    JLabel time = new JLabel();

    public PickAccountPage(OPTION_TYPE type){
        this.type=type;
        frame = new JFrame("Welcome to the bank.");
        JPanel panel = new JPanel();
        placePanelComponents(panel);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.repaint();
        frame.setVisible(true);

        Timer.getInstance().addTimerObserver(this);
    }

    @Override
    public void timeChange() {
        time.setText(Timer.getInstance().getTimeStr());
    }

    public enum OPTION_TYPE{
        SAVE,WITHDRAW,TRANSFER
    }
    public enum ACCOUT_TYPE{
        SAVING,CHECKING
    }


    private void placePanelComponents(JPanel panel){
        panel.setLayout(null);

        time = new JLabel(Timer.getInstance().getTimeStr());
        time.setBounds(0,0,160,25);
        panel.add(time);

        JLabel label = new JLabel("Please pick your account to save or withdraw.");
        label.setBounds(120,20,160,25);
        panel.add(label);

        JButton savingButton = new JButton("Saving Account");
        savingButton.setBounds(100, 80, 160, 50);
        savingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Result<Account> result = SystemManager.getInstance().chooseAccount(AccountType.SAVING.getAccountType());
                if(result.isSuccess()){
                    frame.dispose();
                    switch (type){
                        case SAVE:
                            new swPage(OPTION_TYPE.SAVE,ACCOUT_TYPE.SAVING);
                            break;
                        case WITHDRAW:
                            new swPage(OPTION_TYPE.WITHDRAW,ACCOUT_TYPE.SAVING);
                            break;
                        case TRANSFER:
                            new swPage(OPTION_TYPE.TRANSFER,ACCOUT_TYPE.SAVING);
                            break;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"No existed saving account.","Error ",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        panel.add(savingButton);

        JButton checkingButton = new JButton("Checking Account");
        checkingButton.setBounds(100, 150, 160, 50);
        checkingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Result<Account> result = SystemManager.getInstance().chooseAccount(AccountType.CHECKING.getAccountType());
                if(result.isSuccess()){
                    frame.dispose();
                    switch (type){
                        case SAVE:
                            new swPage(OPTION_TYPE.SAVE,ACCOUT_TYPE.CHECKING);
                            break;
                        case WITHDRAW:
                            new swPage(OPTION_TYPE.WITHDRAW,ACCOUT_TYPE.CHECKING);
                            break;
                        case TRANSFER:
                            new swPage(OPTION_TYPE.TRANSFER,ACCOUT_TYPE.CHECKING);
                            break;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"No existed checking account.","Error ",JOptionPane.ERROR_MESSAGE);
                }


            }
        });
        panel.add(checkingButton);


        JButton backButton = new JButton("back");
        backButton.setBounds(100, 220, 160, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CustomerPage();

            }
        });
        panel.add(backButton);

    }

}
