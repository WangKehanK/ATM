package ui;

import manager.SystemManager;
import manager.account.Account;
import manager.entity.Result;
import manager.timer.Timer;
import manager.timer.TimerObserver;
import manager.user.Consumer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
/**
 * This page provides customers an option to choose saving account or checking account to interact with
 */
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
        frame.setSize(1000, 600);
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
        label.setBounds(120,20,160,50);
        panel.add(label);

        Vector rateList=new Vector();
        rateList.addElement("Day rate");
        rateList.addElement("Month rate");
        rateList.addElement("Year rate");
        JList list=new JList(rateList);

        JScrollPane scrollPane=new JScrollPane(list);
        scrollPane.setBounds(400,100,200,50);;
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        JButton savingButton = new JButton("Saving Account");
        savingButton.setBounds(100, 100, 160, 50);
        savingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int index = list.getSelectedIndex();

                if(index==-1){
                    JOptionPane.showMessageDialog(null,"Please select rate.","Error ",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Result<Account> result =systemManager.createSavingAccount(index + 1);
                if(result.isSuccess()){
                    frame.dispose();
                    new NewAccountPage(PickAccountPage.ACCOUT_TYPE.SAVING);
                }
                else{
                    JOptionPane.showMessageDialog(null,result.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(savingButton);

        JButton checkingButton = new JButton("Checking Account");
        checkingButton.setBounds(100, 200, 160, 50);
        checkingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=list.getSelectedIndex();

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
        panel.add(checkingButton);


        JButton loanButton = new JButton("Loan Account");
        loanButton.setBounds(100, 300, 160, 50);
        loanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();

                if(index==-1){
                    JOptionPane.showMessageDialog(null,"Please select rate.","Error ",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                System.out.println(index);//0:day 1:month 2:year
                Result<Account> result =systemManager.createLoanAccount(index  + 1);

                if(result.isSuccess()){
                    JOptionPane.showMessageDialog(null,"Loan Account successfully created!.","Loan Account ",JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Loan Account existed!.","Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(loanButton);

        JButton stockButton = new JButton("Stock Account");
        stockButton.setBounds(100, 400, 160, 50);
        stockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(systemManager.getCurrentUser().hasSecurityAccount()){
                    JOptionPane.showMessageDialog(null,"Stock account existed!.","Error ",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                new SecurityPickAccountPage();
            }
        });
        panel.add(stockButton);


        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 500, 160, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CustomerPage();

            }
        });







        panel.add(scrollPane);
        panel.add(backButton);

    }




    @Override
    public void timeChange() {
        time.setText(Timer.getInstance().getTimeStr());
    }
}
