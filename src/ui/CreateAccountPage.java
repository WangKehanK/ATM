import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountPage implements IPages{

    JFrame frame;

    public CreateAccountPage(){
        frame = new JFrame("Welcome to the bank.");
        JPanel panel = new JPanel();
        placePanelComponents(panel);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.repaint();
        frame.setVisible(true);
    }

    private void placePanelComponents(JPanel panel){
        panel.setLayout(null);


        JLabel label = new JLabel("Create account.");
        label.setBounds(120,20,160,25);
        panel.add(label);

        JButton customerButton = new JButton("Saving Account");
        customerButton.setBounds(100, 80, 160, 50);
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(true){
                    frame.dispose();
                    new NewAccountPage(PickAccountPage.ACCOUT_TYPE.SAVING);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Saving account existed!","Error ",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(customerButton);

        JButton ManagerButton = new JButton("Checking Account");
        ManagerButton.setBounds(100, 150, 160, 50);
        ManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(true){
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

}
