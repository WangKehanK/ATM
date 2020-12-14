package manager.account;

import dao.AccountDao;
import dao.LogDao;
import dao.StockDao;
import manager.BankIncomeLedger;
import manager.entity.Log;
import manager.entity.Stock;
import manager.timer.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关于证券的账户
 * 保存购买时的证券金额
 * 计算赚了多少钱
 */
public class SecurityAccount implements Account{

    private String accountId;
    private String userId;

    private int balance;
    //记录购买的股票数量
    private Map<Stock, Integer> stockMap;
    //记录股票购买的总金额
    private Map<Stock, Integer> stockPurchaseMap;

    private AccountDao accountDao;
    private StockDao stockDao = StockDao.getInstance();
    private BankIncomeLedger bankIncomeLedger = BankIncomeLedger.getInstance();
    private LogDao logDao = LogDao.getInstance();


    public SecurityAccount(String userId) {
        this.userId = userId;
        stockMap = new HashMap<>();
        stockPurchaseMap = new HashMap<>();
        this.accountId = accountDao.getNewAccountId();

    }

    public SecurityAccount(String accountId, String userId, int balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
        stockMap = new HashMap<>();
        stockPurchaseMap = new HashMap<>();
    }

    private AccountDao getAccountDao() {
        if(accountDao == null){
            accountDao = AccountDao.getInstance();
        }
        return accountDao;
    }

    public void setStockMap(Map<Stock, Integer> stockMap){
        this.stockMap = stockMap;
    }

    public void setStockPurchaseMap(Map<Stock, Integer> stockPurchaseMap){
        this.stockPurchaseMap = stockPurchaseMap;
    }

    public Map<Stock, Integer> getAccountStockMap() {
        return stockMap;
    }

    public boolean purchasingStock(String stockId, int stockAmount) {

        Stock stock = stockDao.getStockById(stockId);
        if(stock == null){
            return false;
        }

        if(balance < stockAmount * stock.getPrice()){
            return false;
        }



        int number = stockMap.getOrDefault(stock, 0);
        int purchaseMoney = stockPurchaseMap.getOrDefault(stock, 0);

        number += stockAmount;
        purchaseMoney += (stockAmount * stock.getPrice());
        balance -= (stockAmount * stock.getPrice());

        stockMap.put(stock, number);
        stockPurchaseMap.put(stock, purchaseMoney);

        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "Security Account purchase "+ stock.getStockName() + " number:" + stockAmount));

        getAccountDao().updateAccount(this);
        return true;
    }

    public boolean sellStock(String stockId, int stockAmount){
        Stock stock = stockDao.getStockById(stockId);
        if(stock == null){
            return false;
        }
        int number = stockMap.getOrDefault(stock, 0);
        int purchaseMoney = stockPurchaseMap.getOrDefault(stock, 0);

        if(number < stockAmount){
            return false;
        }
        number -= stockAmount;
        purchaseMoney -= (stockAmount * stock.getPrice());
        balance += (stockAmount * stock.getPrice());

        stockMap.put(stock, number);
        stockPurchaseMap.put(stock, purchaseMoney);

        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "Security Account sell stock "+ stock.getStockName() + " number:" + stockAmount));

        getAccountDao().updateAccount(this);
        return true;
    }

    @Override
    public boolean saving(int money) {
        balance += money;
        getAccountDao().updateAccount(this);

        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "Security Account save "+ money));

        return true;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public boolean draw(int money) {
        if(balance < money){
            return false;
        }
        balance -= money;
        getAccountDao().updateAccount(this);

        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "Security Account draw "+ money));

        return true;
    }

    @Override
    public boolean chooseCurrency(int type) {
        return false;
    }

    @Override
    public boolean convert(int currencyType, int toCurrencyType, int money) {
        return false;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public int getAccountType() {
        return AccountType.SECURITY.getAccountType();
    }

    @Override
    public String getStr() {
        String str = "";
        str += balance;
        str += "|";
        for(Map.Entry<Stock, Integer> entry: stockMap.entrySet()){
            str += entry.getKey().getStockId();
            str += ":";
            str += entry.getValue();
            str += "$";
            str += stockPurchaseMap.get(entry.getKey());
            str += ",";
        }

        str = str.substring(0, str.length() - 1);

        return str;
    }

    @Override
    public void fee(int fee) {
        balance -= fee;
        bankIncomeLedger.income(fee);
        getAccountDao().updateAccount(this);
    }

    @Override
    public String getBalanceStr() {
        return "Security[" + balance + "]";
    }

    @Override
    public boolean draw(int transferMoney, int currencyType) {
        return false;
    }

    @Override
    public boolean saving(int transferMoney, int currencyType) {
        return false;
    }

    public Map<Stock, List<Integer>> getStockProfitInfo() {
        Map<Stock, List<Integer>> profitMap =  new HashMap<>();
        for(Stock stock : stockMap.keySet()){
            int stockAmount = stockMap.get(stock);
            int purchaseMoney = stockPurchaseMap.get(stock);
            List<Integer> list = new ArrayList<>();
            list.add(stockAmount);
            list.add(purchaseMoney);
            profitMap.put(stock, list);
        }
        return profitMap;
    }
}
