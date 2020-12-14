package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage implements IPages {

        JFrame frame;

        public WelcomePage(){
            frame = new JFrame("Welcome to BU Trust Bank.");
            JPanel panel = new JPanel();
            placePanelComponents(panel);

            frame.add(panel);
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.repaint();
            frame.setVisible(true);
        }

        private void placePanelComponents(JPanel panel) {


            panel.setLayout(null);




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





}
