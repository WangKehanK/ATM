package dao;

import manager.account.Account;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * operating account-related data operations
 */
public class AccountDao {

    public static final int DAY_RATE = 1;
    public static final int MONTH_RATE = 2;
    public static final int YEAR_RATE = 3;

    private int maxId;

    private static AccountDao accountDao = new AccountDao();
    private Map<String, List<Account>> accountMap;
    private Map<Integer, Double> savingRateMap;

    private AccountDao() {
        maxId = 0;
        accountMap = FileUtils.readAccountMap();
        for(String userId: accountMap.keySet()){
            for(Account account : accountMap.get(userId)){
                int id = Integer.parseInt(account.getAccountId());
                if(id > maxId){
                    maxId = id;
                }
            }
        }
        savingRateMap = getSavingRateMap();
    }

    public static AccountDao getInstance(){
        return accountDao;
    }

    public List<Account> getUserAccount(String userId){
        return accountMap.get(userId);
    }

    public void addAccount(String userId, Account account) {
        List<Account> accountList = accountMap.getOrDefault(userId, new ArrayList<>());
        accountList.add(account);

        accountMap.put(userId, accountList);
        FileUtils.saveAccount(accountMap);
    }

    public String getNewAccountId() {
        maxId++;
        return String.format("%010d", maxId);
    }

    public void updateAccount(Account account) {
        FileUtils.saveAccount(accountMap);
    }


    private Map<Integer, Double> getSavingRateMap() {
        Map<Integer, Double> map = new HashMap<>();
        map.put(DAY_RATE, 0.001);
        map.put(MONTH_RATE, 0.006);
        map.put(YEAR_RATE, 0.072);

        return map;
    }

    public double getRate(int rateType) {
        return savingRateMap.get(rateType);
    }
}
