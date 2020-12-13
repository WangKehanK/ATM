import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class swPage implements IPages{

    JFrame frame;
    PickAccountPage.OPTION_TYPE option_type;
    PickAccountPage.ACCOUT_TYPE accout_type;
    //Customer customer;
    public swPage(PickAccountPage.OPTION_TYPE option_type,PickAccountPage.ACCOUT_TYPE account_type){
        this.option_type=option_type;
        this.accout_type=accout_type;

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

        JLabel label = new JLabel("Welcome.");
        label.setBounds(400,50,200,50);
        //panel.add(label);

        JButton usdButton = new JButton("USD");
        usdButton.setBounds(0, 150, 150, 50);
        usdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });



        JButton euroButton = new JButton("EURO");
        euroButton.setBounds(0, 250, 150, 50);
        euroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        //panel.add(transferButton);

        String option="";

        if(option_type== PickAccountPage.OPTION_TYPE.SAVE)
            option="Save";
        else if(option_type== PickAccountPage.OPTION_TYPE.WITHDRAW)
            option="Withdraw";

        JButton optionButton = new JButton(option);
        optionButton.setBounds(850, 250, 150, 50);
        optionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        JButton cnyButton = new JButton("CNY");
        cnyButton.setBounds(0, 350, 150, 50);
        cnyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }

        });


        JButton backButton = new JButton("Back");
        backButton.setBounds(850, 350, 150, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        //panel.add(withdrawButton);


        panel.add(label);
        panel.add(usdButton);
        panel.add(euroButton);
        panel.add(optionButton);
        panel.add(cnyButton);
        panel.add(backButton);

    }


}
