package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class PickAccountPage implements IPages{


    JFrame frame;
    OPTION_TYPE type;

    public PickAccountPage(OPTION_TYPE type){
        this.type=type;
        frame = new JFrame("Welcome to the bank.");
        JPanel panel = new JPanel();
        placePanelComponents(panel);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.repaint();
        frame.setVisible(true);
    }

    public enum OPTION_TYPE{
        SAVE,WITHDRAW,TRANSFER
    }
    public enum ACCOUT_TYPE{
        SAVING,CHECKING
    }


    private void placePanelComponents(JPanel panel){
        panel.setLayout(null);


        JLabel label = new JLabel("Please pick your account to save or withdraw.");
        label.setBounds(120,20,160,25);
        panel.add(label);

        JButton savingButton = new JButton("Saving Account");
        savingButton.setBounds(100, 80, 160, 50);
        savingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(true){
                    frame.dispose();
                    switch (type){
                        case SAVE:
                            new swPage(OPTION_TYPE.SAVE,ACCOUT_TYPE.SAVING);
                            break;
                        case WITHDRAW:
                            new swPage(OPTION_TYPE.WITHDRAW,ACCOUT_TYPE.SAVING);
                            break;
                        case TRANSFER:
                            break;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"No existed saving account.","Error ",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        panel.add(savingButton);

        JButton checkingButton = new JButton("Checking Account");
        checkingButton.setBounds(100, 150, 160, 50);
        checkingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(true){
                    frame.dispose();
                    switch (type){
                        case SAVE:
                            new swPage(OPTION_TYPE.SAVE,ACCOUT_TYPE.CHECKING);
                            break;
                        case WITHDRAW:
                            new swPage(OPTION_TYPE.WITHDRAW,ACCOUT_TYPE.CHECKING);
                            break;
                        case TRANSFER:
                            break;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"No existed checking account.","Error ",JOptionPane.ERROR_MESSAGE);
                }


            }
        });
        panel.add(checkingButton);

    }

}
