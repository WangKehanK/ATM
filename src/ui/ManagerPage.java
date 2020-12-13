import javax.swing.*;

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



    }


}
