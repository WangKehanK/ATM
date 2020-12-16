package ui;

import manager.SystemManager;
import manager.account.Account;
import manager.account.AccountType;
import manager.account.CheckingAccount;
import manager.account.SavingAccount;
import manager.entity.Result;
import manager.timer.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ExchangePage implements IPages{


    JFrame frame;
    SystemManager systemManager = SystemManager.getInstance();

    public ExchangePage(){
        frame = new JFrame("Welcome to the bank.");
        JPanel panel = new JPanel();
        placePanelComponents(panel);

        frame.add(panel);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.repaint();
        frame.setVisible(true);




    }

        private void placePanelComponents(JPanel panel){

            panel.setLayout(null);

            Vector currencyList=new Vector();
            currencyList.addElement("USD");
            currencyList.addElement("EURO");
            currencyList.addElement("CNY");

            JList leftList=new JList(currencyList);
            JList rightList=new JList(currencyList);

            JScrollPane leftScrollPane=new JScrollPane(leftList);
            JScrollPane rightScrollPane=new JScrollPane(rightList);

            JTextField textField=new JTextField();
            JLabel label=new JLabel("How much you like to exchange.");

            textField.setBounds(500,200,100,50);
            label.setBounds(200,200,300,50);

            leftScrollPane.setBounds(200,100,100,50);;
            rightScrollPane.setBounds(800,100,100,50);;

            leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            rightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


            JLabel textLabel=new JLabel("Choose the account to convert:");
            textLabel.setBounds(200,300,200,25);


            JButton savingAccountButton = new JButton("SavingAccount");
            savingAccountButton.setBounds(200, 350, 160, 50);
            savingAccountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(systemManager.getCurrentUser().hasSavingAccount()){
                        systemManager.chooseAccount(AccountType.SAVING.getAccountType());
                    }else{
                        JOptionPane.showMessageDialog(null,"Saving Account is not exist","Error ",JOptionPane.ERROR_MESSAGE);
                        return;
                    }


                    Account currentAccount = systemManager.getCurrentAccount();
                    if(!(currentAccount instanceof SavingAccount) && !(currentAccount instanceof CheckingAccount)){
                        JOptionPane.showMessageDialog(null,"current Account is error","Error ",JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int indexL=leftList.getSelectedIndex();// the currency at left . 0 USD 1 EURO 2 CNY
                    int indexR=rightList.getSelectedIndex();// the currency at right.
                    Integer amount;// the number of currency you pay to exchange;

                    String text=textField.getText();


                    try{
                        amount=Integer.parseInt(text);
                    }catch (NullPointerException e1) {
                        JOptionPane.showMessageDialog(null,"Please input number of currency you pay.","Error ",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    catch(NumberFormatException e2){
                        JOptionPane.showMessageDialog(null,"Please input a float.","Error ",JOptionPane.ERROR_MESSAGE);
                        return;
                    }



                    if(indexL==-1||indexR==-1){
                        JOptionPane.showMessageDialog(null,"Please select the currency you'd like to trade.","Error ",JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //Todo exchange function
                    //Result<Account> result =systemManager.createSavingAccount();
                    Result<Void> result=systemManager.convert(indexL+1, indexR+1, amount);
                    if(result.isSuccess()){
                        JOptionPane.showMessageDialog(null,result.getMsg(),"Exchange ",JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,result.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            JButton checkingAccountButton = new JButton("CheckingAccount");
            checkingAccountButton.setBounds(400, 350, 160, 50);
            checkingAccountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(systemManager.getCurrentUser().hasCheckingAccount()){
                        systemManager.chooseAccount(AccountType.CHECKING.getAccountType());
                    }else{
                        JOptionPane.showMessageDialog(null,"Checking Account is not exist","Error ",JOptionPane.ERROR_MESSAGE);
                        return;
                    }


                    Account currentAccount = systemManager.getCurrentAccount();
                    if(!(currentAccount instanceof SavingAccount) && !(currentAccount instanceof CheckingAccount)){
                        JOptionPane.showMessageDialog(null,"current Account is error","Error ",JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int indexL=leftList.getSelectedIndex();// the currency at left . 0 USD 1 EURO 2 CNY
                    int indexR=rightList.getSelectedIndex();// the currency at right.
                    Integer amount;// the number of currency you pay to exchange;

                    String text=textField.getText();


                    try{
                        amount=Integer.parseInt(text);
                    }catch (NullPointerException e1) {
                        JOptionPane.showMessageDialog(null,"Please input number of currency you pay.","Error ",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    catch(NumberFormatException e2){
                        JOptionPane.showMessageDialog(null,"Please input a float.","Error ",JOptionPane.ERROR_MESSAGE);
                        return;
                    }



                    if(indexL==-1||indexR==-1){
                        JOptionPane.showMessageDialog(null,"Please select the currency you'd like to trade.","Error ",JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //Todo exchange function
                    //Result<Account> result =systemManager.createSavingAccount();
                    Result<Void> result=systemManager.convert(indexL+1, indexR+1, amount);
                    if(result.isSuccess()){
                        JOptionPane.showMessageDialog(null,result.getMsg(),"Exchange ",JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,result.getMsg(),"Error ",JOptionPane.ERROR_MESSAGE);
                    }

                }
            });




            JButton backButton = new JButton("Back");
            backButton.setBounds(850, 350, 150, 50);
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    new CustomerPage();
                }
            });

            panel.add(textLabel);
            panel.add(savingAccountButton);
            panel.add(checkingAccountButton);
            panel.add(leftScrollPane);
            panel.add(rightScrollPane);
            panel.add(label);
            panel.add(textField);
            panel.add(backButton);
        }


}
