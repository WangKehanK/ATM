package ui;

import manager.SystemManager;
import manager.timer.Timer;
import manager.timer.TimerObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This file is for WelcomePage class that implements IPages interface and timer observer
 * contains all the functions we need for WelcomePage class
 * provides the UI for WelcomePage
 */

public class WelcomePage implements IPages, TimerObserver {

        JFrame frame;
        JLabel time = new JLabel();
        public WelcomePage(){
            frame = new JFrame("Welcome to BU Trust Bank.");
            JPanel panel = new JPanel();
            placePanelComponents(panel);

            frame.add(panel);
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.repaint();
            frame.setVisible(true);
            SystemManager.getInstance();
            Timer.getInstance().addTimerObserver(this);
        }

        private void placePanelComponents(JPanel panel) {


            panel.setLayout(null);


            time = new JLabel(Timer.getInstance().getTimeStr());
            time.setBounds(0,0,160,25);
            panel.add(time);



            JLabel label = new JLabel("Please select your identity.");
            label.setBounds(120,20,160,25);
            panel.add(label);

            JButton customerButton = new JButton("Customer");
            customerButton.setBounds(80, 100, 100, 25);
            customerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Customer Login
                    frame.dispose();
                    new LoginPage(0);
                }
            });
            panel.add(customerButton);

            JButton ManagerButton = new JButton("Manager");
            ManagerButton.setBounds(220, 100, 100, 25);
            ManagerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ////Manager Login
                    frame.dispose();
                    new LoginPage(1);
                }
            });
            panel.add(ManagerButton);


            panel.setBackground(Color.white);    //set background color.

        }


        @Override
        public void timeChange() {
            time.setText(Timer.getInstance().getTimeStr());
            frame.repaint();
        }
}
