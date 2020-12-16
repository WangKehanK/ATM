package ui;

import manager.SystemManager;
import manager.account.Account;
import manager.entity.Log;
import manager.entity.Result;
import manager.timer.Timer;
import manager.timer.TimerObserver;
import manager.user.Consumer;
import manager.user.Manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
/**
 * This file is for ManagerPage class that implements IPages interface and timer observer
 * contains all the functions we need for ManagerPage class
 * provides the UI for Manager Page
 */


public class ManagerPage implements IPages, TimerObserver {


    JFrame frame;
    JLabel time = new JLabel();
    JTextArea jTextArea = new JTextArea();
    //Manager manager;
    //public ManagerPage(Manager manager){

    public ManagerPage(){
        frame = new JFrame("Welcome to the bank.");
        JPanel panel = new JPanel();
        placePanelComponents(panel);

        frame.add(panel);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.repaint();
        frame.setVisible(true);
        Timer.getInstance().addTimerObserver(this);
    }

    private void placePanelComponents(JPanel panel){
        Manager manager = (Manager) SystemManager.getInstance().getCurrentUser();

        panel.setLayout(null);

        time = new JLabel(Timer.getInstance().getTimeStr());
        time.setBounds(0,0,160,25);
        panel.add(time);

        JLabel label = new JLabel("Welcome " + manager.getUserName());
        label.setBounds(400,50,200,50);

        //panel.add(label);

        jTextArea = new JTextArea();
        jTextArea.setBounds(200,150, 500,300);
        jTextArea.setText("");


        JButton dailyReportButton = new JButton("Daily Report");
        dailyReportButton.setBounds(0, 150, 150, 50);
        dailyReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Result<Map<String, List<Log>>> log = SystemManager.getInstance().getLog();
                String text = "";
                if(log.isSuccess()){
                    for(Map.Entry<String, List<Log>> entry : log.getData().entrySet()){
                        text += ("------" + entry.getKey() + "------");
                        text += System.lineSeparator();
                        for(Log logInfo: entry.getValue()){
                            if(logInfo.getTime().equals(Timer.getInstance().getTimeStr())){
                                text += logInfo.getLog();
                                text += System.lineSeparator();
                            }
                        }
                    }
                    jTextArea.setText(text);

                }
            }
        });
        //panel.add(balanceButton);


        JButton userListButton = new JButton("userList");
        userListButton.setBounds(0, 250, 150, 50);
        userListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Result<List<Consumer>> result = SystemManager.getInstance().getConsumers();
                String str = "";
                if(result.isSuccess()){
                    for(Consumer consumer : result.getData()){
                        str += ("------" + consumer.getConsumerId() +"------");
                        str += System.lineSeparator();
                        str += consumer.getBalance();
                        str += System.lineSeparator();
                    }
                }

                jTextArea.setText(str);
            }
        });

        JButton stockButton = new JButton("stock");
        stockButton.setBounds(0, 350, 150, 50);
        stockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new StockPage();
            }
        });



        JButton logoutButton = new JButton("logout");
        logoutButton.setBounds(850, 350, 150, 50);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SystemManager.getInstance().logout();
                frame.dispose();
                new WelcomePage();
            }
        });
        //panel.add(withdrawButton);


        panel.add(label);
        panel.add(dailyReportButton);
        panel.add(userListButton);
        panel.add(stockButton);
        panel.add(logoutButton);

        panel.add(jTextArea);



    }


    @Override
    public void timeChange() {
        time.setText(Timer.getInstance().getTimeStr());
    }
}
