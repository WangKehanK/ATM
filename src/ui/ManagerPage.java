package ui;

import manager.timer.Timer;
import manager.timer.TimerObserver;

import javax.swing.*;

public class ManagerPage implements IPages, TimerObserver {


    JFrame frame;
    JLabel time = new JLabel();
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
        Timer.getInstance().addTimerObserver(this);
    }

    private void placePanelComponents(JPanel panel){



    }


    @Override
    public void timeChange() {
        time.setText(Timer.getInstance().getTimeStr());
    }
}
