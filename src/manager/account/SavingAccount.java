package manager.account;

import dao.AccountDao;
import dao.LogDao;
import manager.BankIncomeLedger;
import manager.entity.Log;
import manager.timer.Timer;
import manager.timer.TimerObserver;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * This file is for saving account, implement the account interface and timer observer
 * contains all functions for saving account
 */


public class SavingAccount implements Account, TimerObserver {
    private String accountId;
    private String userId;

    private int rateType;
    private int currentCurrencyType;

    private Map<Integer, Integer> balanceMap;

    private AccountDao accountDao;
    private Timer timer = Timer.getInstance();
    private BankIncomeLedger bankIncomeLedger = BankIncomeLedger.getInstance();
    private LogDao logDao = LogDao.getInstance();

    public SavingAccount(String userId, int rateType){
        this.userId = userId;
        String accountId = getAccountDao().getNewAccountId();
        this.accountId = accountId;
        balanceMap = new HashMap<>();
        currentCurrencyType = USD;
        balanceMap.put(USD, 0);
        balanceMap.put(EURO, 0);
        balanceMap.put(CNY, 0);
        timer.addTimerObserver(this);


        rateType = rateType;
    }

    private AccountDao getAccountDao() {
        if(accountDao == null){
            accountDao = AccountDao.getInstance();
        }
        return accountDao;
    }

    public SavingAccount(String accountId, String userId){
        this.accountId = accountId;
        this.userId = userId;
        balanceMap = new HashMap<>();
        currentCurrencyType = USD;
        balanceMap.put(USD, 0);
        balanceMap.put(EURO, 0);
        balanceMap.put(CNY, 0);
        timer.addTimerObserver(this);
    }


    public void reloadAccount(Map<Integer, Integer> map){
        balanceMap = map;
    }


    @Override
    public boolean saving(int money) {
        int currentBalance = balanceMap.getOrDefault(currentCurrencyType, 0);
        currentBalance += money;
        balanceMap.put(currentCurrencyType, currentBalance);
        getAccountDao().updateAccount(this);

        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "Saving Account save "+ money));

        return true;
    }

    @Override
    public int getBalance() {
        int currentBalance = balanceMap.getOrDefault(currentCurrencyType, 0);
        return currentBalance;
    }

    @Override
    public boolean draw(int money) {
        int currentBalance = balanceMap.getOrDefault(currentCurrencyType, 0);

        if(currentBalance < money){
            return false;
        }else{
            currentBalance -= money;
            balanceMap.put(currentCurrencyType, currentBalance);
            getAccountDao().updateAccount(this);
        }
        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "Saving Account draw "+ money));
        return true;
    }

    @Override
    public boolean chooseCurrency(int type) {
        currentCurrencyType = type;
        return true;
    }

    @Override
    public boolean convert(int currencyType, int toCurrencyType, int money) {
        int balance = balanceMap.getOrDefault(currencyType, 0);
        if(balance < money){
            return false;
        }

        int convertMoney = 0;
        if(currencyType == 1 && toCurrencyType == 2){
            convertMoney = (int) (money * USD_2_EURO);
        }else if(currencyType == 1 && toCurrencyType == 3){
            convertMoney = (int) (money * USD_2_CNY);
        }else if(currencyType == 2 && toCurrencyType == 1){
            convertMoney = (int) (money * EURO_2_USD);
        }else if(currencyType == 2 && toCurrencyType == 3){
            convertMoney = (int) (money * EURO_2_CNY);
        }else if(currencyType == 3 && toCurrencyType == 1){
            convertMoney = (int) (money * CNY_2_USD);
        }else if(currencyType == 3 && toCurrencyType == 2){
            convertMoney = (int) (money * CNY_2_EURO);
        }

        balance -= money;
        balanceMap.put(currencyType, balance);

        balance = balanceMap.getOrDefault(toCurrencyType, 0);
        balance += convertMoney;
        balanceMap.put(toCurrencyType, balance);
        getAccountDao().updateAccount(this);
        return true;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public int getAccountType() {
        return AccountType.SAVING.getAccountType();
    }

    public void setRateType(int rateType) {
        this.rateType = rateType;
    }

    @Override
    public String getStr() {
        String str = "";
        for(Map.Entry<Integer, Integer> entry : balanceMap.entrySet()){
            str += entry.getKey();
            str += ":";
            str += entry.getValue();
            str += "|";
        }
        if(str.length() > 0){
            str = str.substring(0, str.length() - 1);
        }

        if(str.length() > 0){
            str += " ";
        }

        str += rateType;
        return str;
    }

    @Override
    public void timeChange() {
        //Account amount update
        Calendar calendar = timer.getCalendar();
        if(rateType == AccountDao.DAY_RATE && calendar.get(Calendar.HOUR) == 0){
            for(int key : balanceMap.keySet()){
                Integer balance = balanceMap.get(key);
                balance = (int) (balance * (1 + getAccountDao().getRate(rateType)));
                balanceMap.put(key, balance);
            }
        }else if(rateType == AccountDao.MONTH_RATE && calendar.get(Calendar.DAY_OF_MONTH) == 1 && calendar.get(Calendar.HOUR) == 0){
            for(int key : balanceMap.keySet()){
                Integer balance = balanceMap.get(key);
                balance = (int) (balance * (1 + getAccountDao().getRate(rateType)));
                balanceMap.put(key, balance);
            }
        }else if(rateType == AccountDao.YEAR_RATE && calendar.get(Calendar.MONTH) == 1 && calendar.get(Calendar.DAY_OF_MONTH) == 1&& calendar.get(Calendar.HOUR) == 0){
            for(int key : balanceMap.keySet()){
                Integer balance = balanceMap.get(key);
                balance = (int) (balance * (1 + getAccountDao().getRate(rateType)));
                balanceMap.put(key, balance);
            }
        }
        getAccountDao().updateAccount(this);
    }

    @Override
    public void fee(int fee) {
        Integer balance = balanceMap.getOrDefault(USD, 0);
        if(balance > 0 ){
            balance -= fee;
            balanceMap.put(USD, balance);
        }else if(balanceMap.getOrDefault(EURO,0) > (fee * USD_2_EURO)){
            balance = balanceMap.getOrDefault(EURO,0);
            balance -= (int)(fee * USD_2_EURO);
            balanceMap.put(EURO, balance);
        }else if(balanceMap.getOrDefault(CNY,0) > (fee * USD_2_CNY)){
            balance = balanceMap.getOrDefault(CNY,0);
            balance -= (int)(fee * USD_2_CNY);
            balanceMap.put(CNY, balance);
        }else{
            balance -= fee;
            balanceMap.put(USD, balance);
        }
        bankIncomeLedger.income(fee);
    }

    @Override
    public String getBalanceStr() {
        String str = "";
        for(Map.Entry<Integer, Integer> entry: balanceMap.entrySet()){
            Integer key = entry.getKey();
            if(key == 1){
                str += (" USD:" + entry.getValue());
            }else if(key == 2){
                str += (" EURO:" + entry.getValue());
            }else if(key == 3){
                str += (" CNY:" + entry.getValue());
            }
        }
        if(str.length() > 0){
            str = "Saving[" + str;
            str += "]";
            str+= System.lineSeparator();
        }
        return str;
    }

    @Override
    public boolean draw(int transferMoney, int currencyType) {
        int currentBalance = balanceMap.getOrDefault(currencyType, 0);

        if(currentBalance < transferMoney){
            return false;
        }else{
            currentBalance -= transferMoney;
            balanceMap.put(currencyType, currentBalance);
            getAccountDao().updateAccount(this);
        }

        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "Saving Account transfer "+ transferMoney + " currencyType" + currencyType));
        getAccountDao().updateAccount(this);
        return true;
    }

    @Override
    public boolean saving(int transferMoney, int currencyType) {
        int currentBalance = balanceMap.getOrDefault(currencyType, 0);
        currentBalance += transferMoney;
        balanceMap.put(currencyType, currentBalance);
        getAccountDao().updateAccount(this);

        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "Saving Account received "+ transferMoney + " currencyType" + currencyType));
        getAccountDao().updateAccount(this);
        return true;
    }
}
