package dao;

import manager.user.Consumer;
import manager.user.Manager;
import utils.FileUtils;

import java.util.List;

/**
 * 单例，操作user相关的数据操作
 */
public class UserDao {

    private static UserDao userDao = new UserDao();
    private List<Consumer> consumerList;
    private List<Manager> managerList;

    private UserDao() {
        consumerList = FileUtils.readConsumers();
        managerList = FileUtils.readManagers();
    }

    public static UserDao getInstance() {
        return userDao;
    }

    public Consumer searchConsumer(String userName, String password) {
        for(Consumer consumer : consumerList){
            if(consumer.getUserName().equals(userName) && consumer.getPassword().equals(password)){
                return consumer;
            }
        }
        return null;
    }

    public Manager searchManager(String userName, String password) {
        for(Manager manager : managerList){
            if(manager.getUserName().equals(userName) && manager.getPassword().equals(password)){
                return manager;
            }
        }
        return null;
    }

    public boolean addConsumer(String userName, String password){
        Consumer consumer = searchConsumerByName(userName);
        if(consumer != null){
            return false;
        }else{
            consumer = new Consumer(userName, password);
        }
        consumerList.add(consumer);
        FileUtils.saveConsumers(consumerList);
        return true;
    }

    private Consumer searchConsumerByName(String userName) {
        for(Consumer consumer : consumerList){
            if(consumer.getUserName().equals(userName)){
                return consumer;
            }
        }
        return null;
    }
}
