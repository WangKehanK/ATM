package ui;

import manager.SystemManager;
import manager.account.Account;
import manager.entity.Result;
import manager.timer.Timer;
import manager.timer.TimerObserver;
import manager.user.Consumer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountPage implements IPages, TimerObserver {

    JFrame frame;
    SystemManager systemManager = SystemManager.getInstance();
    Consumer consumer;
    JLabel time = new JLabel();

    public CreateAccountPage(){
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

    private void placePanelComponents(JPanel panel){
        consumer = (Consumer) systemManager.getCurrentUser();

        panel.setLayout(null);

        time = new JLabel(Timer.getInstance().getTimeStr());
        time.setBounds(0,0,160,25);
        panel.add(time);


        JLabel label = new JLabel("Create account.");
        label.setBounds(120,20,160,25);
        panel.add(label);

        JButton customerButton = new JButton("Saving Account");
        customerButton.setBounds(100, 80, 160, 50);
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Result<Account> result =systemManager.createSavingAccount();
                if(result.isSuccess()){
                    frame.dispose();
                    new NewAccountPage(PickAccountPage.ACCOUT_TYPE.SAVING);
                }
                else{
                    JOptionPane.showMessageDialog(null,result.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(customerButton);

        JButton ManagerButton = new JButton("Checking Account");
        ManagerButton.setBounds(100, 150, 160, 50);
        ManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Result<Account> result =systemManager.createCheckingAccount();
                if(result.isSuccess()){
                    frame.dispose();
                    new NewAccountPage(PickAccountPage.ACCOUT_TYPE.CHECKING);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Checking account existed!.","Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(ManagerButton);

    }

    @Override
    public void timeChange() {
        time.setText(Timer.getInstance().getTimeStr());
    }
}
