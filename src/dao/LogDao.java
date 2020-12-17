package dao;

import manager.entity.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The log Dao pattern we use to read the database, store log data operations about user operations
 */
public class LogDao {

    private static LogDao logDao = new LogDao();


    /**
     * Operation record by user
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
