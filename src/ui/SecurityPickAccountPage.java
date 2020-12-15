package ui;

import dao.AccountDao;
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
        manager.timer.Timer.getInstance().addTimerObserver(this);
    }

    @Override
    public void timeChange() {
        time.setText(manager.timer.Timer.getInstance().getTimeStr());
    }


    private void placePanelComponents(JPanel panel){
        panel.setLayout(null);

        time = new JLabel(Timer.getInstance().getTimeStr());
        time.setBounds(0,0,160,25);
        panel.add(time);

        JTextField accountType = new JTextField();



        JLabel label = new JLabel("Please pick your account to transfer to security Account");
        label.setBounds(120,20,1600,25);
        panel.add(label);

        JLabel balanceLabel = new JLabel(((Consumer)systemManager.getCurrentUser()).getBalance());
        balanceLabel.setBounds(120,80,1600,25);
        panel.add(balanceLabel);

        JButton savingButton = new JButton("Saving Account");
        savingButton.setBounds(100, 120, 160, 50);
        savingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountType.setText(AccountType.SAVING.getAccountType() + "");
            }
        });
        panel.add(savingButton);

        JButton checkingButton = new JButton("Checking Account");
        checkingButton.setBounds(100, 190, 160, 50);
        checkingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountType.setText(AccountType.CHECKING.getAccountType() + "");
            }
        });
        panel.add(checkingButton);


        JButton backButton = new JButton("back");
        backButton.setBounds(100, 420, 210, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CreateAccountPage();

            }
        });
        panel.add(backButton);

        JLabel type = new JLabel("USD");
        type.setBounds(300,120,80,25);
        panel.add(type);


        JButton usdButton = new JButton("USD");
        usdButton.setBounds(300, 150, 150, 50);
        usdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type.setText("USD");
                currencyType = Account.USD;
            }
        });

        panel.add(usdButton);



        JButton euroButton = new JButton("EURO");
        euroButton.setBounds(150, 250, 150, 50);
        euroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                type.setText("EURO");
                currencyType = Account.EURO;
            }
        });

        panel.add(euroButton);


        JButton cnyButton = new JButton("CNY");
        cnyButton.setBounds(150, 350, 150, 50);
        cnyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                type.setText("CNY");
                currencyType = Account.CNY;
            }

        });

        panel.add(cnyButton);

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
