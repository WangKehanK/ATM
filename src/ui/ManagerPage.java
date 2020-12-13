import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerPage implements IPages {


    JFrame frame;
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
    }

    private void placePanelComponents(JPanel panel){

        panel.setLayout(null);


        JLabel label = new JLabel("Welcome");
        label.setBounds(400,50,200,50);


        JButton drButton = new JButton("Daily report");
        drButton.setBounds(0, 250, 150, 50);
        drButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        //panel.add(transferButton);






        JButton ciButton = new JButton("Customer Information");
        ciButton.setBounds(0, 350, 150, 50);
        ciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }

        });

        JButton backButton = new JButton("Back To HomePage.");
        backButton.setBounds(850, 350, 150, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new WelcomePage();
            }
        });

        panel.add(label);
        panel.add(drButton);
        panel.add(ciButton);
        panel.add(backButton);
    }


}
