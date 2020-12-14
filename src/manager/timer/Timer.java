package manager.timer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * 时间类，用来计算时间
 */
public class Timer implements Runnable{

    private List<TimerObserver> observerList;
    private Calendar calendar;

    private static Timer timer = new Timer();

    private Timer() {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        observerList =  new ArrayList<>();
    }

    public static Timer getInstance(){
        return timer;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void addTimerObserver(TimerObserver timerObserver){
        observerList.add(timerObserver);
    }

    public String getTimeStr(){
        return new SimpleDateFormat("yyyy-MM-dd HH").format(calendar.getTime().getTime());
    }

    @Override
    public void run() {
        while(true){
            //10S = 1小时
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            calendar.add(Calendar.HOUR, 1);
            System.out.println(getTimeStr());

            for(TimerObserver timerObserver : observerList){
                timerObserver.timeChange();
            }

        }
    }

}
