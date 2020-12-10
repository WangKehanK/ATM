package dao;

import manager.account.Account;
import utils.FileUtils;

import java.util.List;
import java.util.Map;

/**
 * 单例，操作account相关的数据操作
 */
public class AccountDao {
    private static AccountDao accountDao = new AccountDao();
    private Map<String, List<Account>> accountMap;

    private AccountDao() {
        accountMap = FileUtils.readAccountMap();
    }

    public static AccountDao getInstance(){
        return accountDao;
    }

    public List<Account> getUserAccount(String userName){
        return accountMap.get(userName);
    }
}
