package manager.account;

import dao.AccountDao;
import dao.LogDao;
import manager.BankIncomeLedger;
import manager.SystemManager;
import manager.entity.Log;
import manager.timer.Timer;

import java.util.HashMap;
import java.util.Map;

public class CheckingAccount implements Account{
    private String accountId;
    private String userId;

    private int currentCurrencyType;

    private Map<Integer, Integer> balanceMap;

    private AccountDao accountDao = AccountDao.getInstance();
    private BankIncomeLedger bankIncomeLedger = BankIncomeLedger.getInstance();
    private LogDao logDao = LogDao.getInstance();

    public CheckingAccount(String userId) {
        String accountId = accountDao.getNewAccountId();
        this.accountId = accountId;
        this.userId = userId;
        balanceMap = new HashMap<>();
        currentCurrencyType = USD;
        balanceMap.put(USD, 0);
        balanceMap.put(EURO, 0);
        balanceMap.put(CNY, 0);
    }

    public CheckingAccount(String accountId, String userId) {
        this.accountId = accountId;
        this.userId = userId;
        balanceMap = new HashMap<>();
        currentCurrencyType = USD;
        balanceMap.put(USD, 0);
        balanceMap.put(EURO, 0);
        balanceMap.put(CNY, 0);
    }

    public void reloadAccount(Map<Integer, Integer> map){
        balanceMap = map;
    }


    @Override
    public boolean saving(int money) {
        int currentBalance = balanceMap.getOrDefault(currentCurrencyType, 0);
         currentBalance += money;
         balanceMap.put(currentCurrencyType, currentBalance);
         accountDao.updateAccount(this);

         logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "Checking Account save "+ money));

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
            accountDao.updateAccount(this);
        }

        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "Checking Account draw "+ money));
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
        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "Checking Account convert "+ money));
        return true;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public int getAccountType() {
        return AccountType.CHECKING.getAccountType();
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
        return str;
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
            str = "Checking[" + str;
            str += "]";
            str+= System.lineSeparator();
        }
        return str;
    }
}
