package ui;

import manager.SystemManager;
import manager.entity.Result;
import manager.timer.Timer;
import manager.timer.TimerObserver;
import manager.user.Consumer;
import manager.user.Manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This file is for LoginPage class that implements IPages interface and timer observer
 * contains all the functions we need for LoginPage class
 * provides the UI for Login Page
 */

public class LoginPage implements IPages, TimerObserver {

    JFrame frame;
    int catagory;
    SystemManager systemManager = SystemManager.getInstance();
    JLabel time = new JLabel();
    // Login Page received a integer to identify customer or manager.catagory 0:customer,1:manager
    public LoginPage(int catagory){
        this.catagory=catagory;
        String title;
        if(catagory==0)
            title="Customer Login";
        else
            title="Manager Login";
        frame = new JFrame(title);
        // Setting the width and height of frame
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel();
        placePanelComponents(panel);
        frame.add(panel);
        frame.setVisible(true);

        Timer.getInstance().addTimerObserver(this);
    }

    private void placePanelComponents(JPanel panel) {


        panel.setLayout(null);

        time = new JLabel(Timer.getInstance().getTimeStr());
        time.setBounds(0,0,160,25);
        panel.add(time);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        //add login button
        JButton loginButton = new JButton("login");
        loginButton.setBounds(40, 80, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String password = String.valueOf(passwordText.getPassword());
                String username = String.valueOf(userText.getText());
                System.out.println(username);
                System.out.println(password);
                Boolean login = false;

                if(password==null||username==null||password.length()==0||username.length()==0) {
                    JOptionPane.showMessageDialog(null, "Empty username or password!", "Error ", JOptionPane.ERROR_MESSAGE);
                    userText.setText("");
                }
                //Here Add Login function

                if(catagory==0) {//customer login
                    Result<Consumer> consumerResult = systemManager.consumerLogin(username, password);
                    if(consumerResult.isSuccess()){
                        login = true;
                    }
                }else {
                    Result<Manager> managerResult = systemManager.managerLogin(username, password);
                    if(managerResult.isSuccess()){
                        login = true;
                    }
                }
                //Login successed. Jump to Customer or Manager Pages.
                if(login) {
                    frame.dispose();
                    if(catagory==0)
                        new CustomerPage();
                    else
                        new ManagerPage();
                }
                // Login failed. Clear username and password field.
                else {
                    JOptionPane.showMessageDialog(null,"Wrong username or password! Please reenter.","Error ",JOptionPane.ERROR_MESSAGE);
                    userText.setText("");
                }



            }
        });
        panel.add(loginButton);


        JButton backButton = new JButton("Back To Home Page");
        backButton.setBounds(150, 80, 160, 25);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Customer Login
                frame.dispose();
                new WelcomePage();
            }
        });
        panel.add(backButton);



    }

    @Override
    public void timeChange() {
        time.setText(Timer.getInstance().getTimeStr());
    }
}
