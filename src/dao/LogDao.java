package dao;

import manager.entity.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 存放关于用户操作的日志数据操作
 */
public class LogDao {

    private static LogDao logDao = new LogDao();


    /**
     * 按用户区分的操作记录
     */
    private Map<String, List<Log>> logMap;

    public LogDao() {
        logMap = new HashMap<>();
    }

    public static LogDao getInstance() {
        return logDao;
    }

    public Map<String, List<Log>> getLogs() {
        return logMap;
    }

    public void addLog(String uerId, Log log){
        List<Log> logs = logMap.getOrDefault(uerId, new ArrayList<>());

        logs.add(log);

        logMap.put(uerId, logs);

    }
}
