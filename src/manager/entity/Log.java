package manager.entity;

public class Log {
    private String time;
    private String log;

    public Log(String timeStr, String log) {
        this.time = timeStr;
        this.log = log;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
