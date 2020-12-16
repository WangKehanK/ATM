package manager.user;

import dao.UserDao;
import manager.entity.Result;

/**
 * This file is for UserManager class
 * contains all the functions we need for UserManager class
 */
public class UserManager {
    private static UserManager userManager = new UserManager();
    private UserDao userDao = UserDao.getInstance();

    private UserManager() {
    }

    public static UserManager getInstance(){
        return userManager;
    }

    public Result<Consumer> getConsumer(String userName, String password){
        if(userName == null || userName.trim().equals("")
                || password == null || password.trim().equals("")){
            return new Result(false, "", null);
        }
        Consumer consumer = userDao.searchConsumer(userName, password);
        if(consumer == null){
            return new Result(false, "error consumer", null);
        }

        return new Result(true, "success", consumer);
    }

    public Result<Manager> getManager(String userName, String password){
        if(userName == null || userName.trim().equals("")
                || password == null || password.trim().equals("")){
            return new Result(false, "", null);
        }
        Manager manager = userDao.searchManager(userName, password);
        if(manager == null){
            return new Result(false, "error manager", null);
        }

        return new Result(true, "success", manager);
    }

    public Result<Consumer> registerConsumer(String userName, String password){
        boolean result = userDao.addConsumer(userName, password);
        if(result){
            return new Result(true, "register success", userDao.searchConsumer(userName, password));
        }else{
            return new Result(false, "consumer is exist", null);
        }
    }
}
