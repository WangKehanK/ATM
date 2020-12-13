import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerPage implements IPages{

    JFrame frame;
    //Customer customer;
    public CustomerPage(){
        frame = new JFrame("Welcome to the bank.");
        JPanel panel = new JPanel();
        frame.setSize(1000, 600);

        placePanelComponents(panel);

        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.repaint();
        frame.setVisible(true);
    }

    private void placePanelComponents(JPanel panel){

        panel.setLayout(null);

        JLabel label = new JLabel("Welcome Z.Y Zhang.");
        label.setBounds(400,50,200,50);
        //panel.add(label);

        JButton balanceButton = new JButton("Balance");
        balanceButton.setBounds(0, 150, 150, 50);
        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Show balance

                if(true)
                    JOptionPane.showMessageDialog(null,"Saving Account:100$ \n Checking Account:100$","Balance",JOptionPane.PLAIN_MESSAGE);
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
        //panel.add(withdrawButton);


        panel.add(label);
        panel.add(balanceButton);
        panel.add(loanButton);
        panel.add(transferButton);
        panel.add(saveButton);
        panel.add(createButton);
        panel.add(withdrawButton);

    }


}
