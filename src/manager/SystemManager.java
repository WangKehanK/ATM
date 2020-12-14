package manager;

import dao.*;
import manager.account.*;
import manager.entity.Collateral;
import manager.entity.Log;
import manager.entity.Result;
import manager.entity.Stock;
import manager.timer.Timer;
import manager.user.Consumer;
import manager.user.Manager;
import manager.user.User;

import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;

public class SystemManager {

    private static SystemManager systemManager = new SystemManager();
    private User currentUser;
    private Account currentAccount;

    private AccountDao accountDao;
    private UserDao userDao;
    private LoanDao loanDao;
    private StockDao stockDao;
    private LogDao logDao;

    private SystemManager() {
        currentAccount = null;
        currentUser = null;
        accountDao = AccountDao.getInstance();
        userDao = UserDao.getInstance();
        loanDao = LoanDao.getInstance();
        stockDao = StockDao.getInstance();
        logDao = LogDao.getInstance();

        new Thread(Timer.getInstance()).start();
    }

    public static SystemManager getInstance(){
        return systemManager;
    }

    /**
     * 客户登陆
     * @param userName
     * @param password
     * @return
     */
    public Result<Consumer> consumerLogin(String userName, String password) {
        if (userName == null || userName.trim().equals("")
                || password == null || password.trim().equals("")) {
            return new Result(false, "", null);
        }
        Consumer consumer = userDao.searchConsumer(userName, password);
        if (consumer == null) {
            return new Result(false, "error consumer", null);
        }
        currentUser = consumer;
        return new Result(true, "success", consumer);
    }


    /**
     * 经理登陆
     * @param userName
     * @param password
     * @return
     */
    public Result<Manager> managerLogin(String userName, String password) {
        if (userName == null || userName.trim().equals("")
                || password == null || password.trim().equals("")) {
            return new Result(false, "", null);
        }
        Manager manager = userDao.searchManager(userName, password);
        if (manager == null) {
            return new Result(false, "error manager", null);
        }
        currentUser = manager;
        return new Result(true, "success", manager);
    }


    /**
     * 用户登出
     * @return
     */
    public Result<Void> logout(){
        currentUser = null;
        currentAccount = null;
        return new Result(true, "log out success", null);
    }


    /**
     * 注册客户账号
     * @param userName
     * @param password
     * @return
     */
    public Result<Consumer> registerConsumer(String userName, String password) {
        boolean result = userDao.addConsumer(userName, password);
        if (result) {
            return new Result(true, "register success", userDao.searchConsumer(userName, password));
        } else {
            return new Result(false, "consumer is exist", null);
        }
    }

    /**
     *
     * 获取客户的账户列表
     *
     * @return
     */
    public Result<List<Account>> getUserAccount() {
        if (currentUser != null) {
            List<Account> accounts = currentUser.getAccounts();
            return new Result<>(true, "success", accounts);
        } else {
            return new Result<>(false, "Current user is null", null);
        }
    }

    /**
     * 选择某一个账户
     * @param accountType
     * @return
     */
    public Result<Account> chooseAccount(int accountType) {
        if (currentUser == null) {
            return new Result<>(false, "Current user is null", null);
        }
        if (accountType == AccountType.CHECKING.getAccountType()) {
            if(currentUser.hasCheckingAccount()){
                currentAccount = currentUser.getCheckingAccount();
            }else{
                return new Result<>(false, "checking account is not exist", null);
            }
        } else if (accountType == AccountType.SAVING.getAccountType()) {
            if(currentUser.hasSavingAccount()){
                currentAccount = currentUser.getSavingAccount();
            }else{
                return new Result<>(false, "saving account is not exist", null);
            }
        } else if (accountType == AccountType.LOAN.getAccountType()) {
            if(currentUser.hasLoanAccount()){
                currentAccount = currentUser.getLoanAccount();
            }else{
                return new Result<>(false, "loan account is not exist", null);
            }
        } else if (accountType == AccountType.SECURITY.getAccountType()) {
            if(currentUser.hasSecurityAccount()){
                currentAccount = currentUser.getSecurityAccount();
            }else{
                return new Result<>(false, "security account is not exist", null);
            }
        } else {
            return new Result<>(false, "error type", null);
        }

        return new Result<>(true, "success", currentAccount);
    }

    /**
     * 存钱
     * @param money
     * @return
     */
    public Result<Void> saving(int money){
        if (currentAccount == null) {
            return new Result<>(false, "Current account is null", null);
        }
        boolean result = currentAccount.saving(money);
        if(!result){
            return new Result<>(false, "Account saving fail", null);
        }else{
            currentAccount.fee(1);
            return new Result<>(true, "Saving success", null);
        }
    }

    /**
     * 取钱
     * @param money
     * @return
     */
    public Result<Void> draw(int money){
        if (currentAccount == null) {
            return new Result<>(false, "Current account is null", null);
        }

        if(currentAccount.getBalance() < money){
            return new Result<>(false, "account money is not enough", null);
        }
        boolean result = currentAccount.draw(money);
        if(!result){
            return new Result<>(false, "Account draw fail", null);
        }else{
            currentAccount.fee(1);
            return new Result<>(true, "draw success", null);
        }
    }

    /**
     * 创建saving 账户
     * @return
     */
    public Result<Account> createSavingAccount(){
        if(currentUser.hasSavingAccount()){
            return new Result<>(false, "User has Saving account", null);
        }else{
            SavingAccount savingAccount = currentUser.createSavingAccount();
            if(savingAccount != null){
                currentAccount = savingAccount;
                return new Result<>(true, "success", savingAccount);
            }else{
                return new Result<>(false, "Saving account create fail", null);
            }
        }
    }

    /**
     * 创建checking 账户
     * @return
     */
    public Result<Account> createCheckingAccount(){
        if(currentUser.hasCheckingAccount()){
            return new Result<>(false, "User has Checking account", null);
        }else{
            CheckingAccount checkingAccount = currentUser.createCheckingAccount();
            if(checkingAccount != null){
                currentAccount = checkingAccount;
                return new Result<>(true, "success", checkingAccount);
            }else{
                return new Result<>(false, "Checking account create fail", null);
            }
        }
    }

    /**
     * 创建Loan账户
     * @return
     */
    public Result<Account> createLoanAccount(int loanRateType){
        if(currentUser.hasLoanAccount()){
            return new Result<>(false, "User has Loan account", null);
        }else{
            LoanAccount loanAccount = currentUser.createLoanAccount(loanRateType);
            if(loanAccount != null){
                currentAccount = loanAccount;
                return new Result<>(true, "success", loanAccount);
            }else{
                return new Result<>(false, "Loan account create fail", null);
            }
        }
    }

    /**
     * 创建证券账户
     * @return
     */
    public Result<Account> createSecurityAccount(){
        if(currentUser.hasSecurityAccount()){
            return new Result<>(false, "User has Security account", null);
        }else{
            SecurityAccount securityAccount = currentUser.createSecurityAccount();
            if(securityAccount != null){
                currentAccount = securityAccount;
                return new Result<>(true, "success", securityAccount);
            }else{
                return new Result<>(false, "Security account create fail", null);
            }
        }
    }

    //TODO:每次操作添加日志

    /**
     * 获取操作日志
     * @return
     */
    public Result<Map<String, List<Log>>> getLog(){
        if(currentUser instanceof Manager){
            Map<String, List<Log>> map = ((Manager) currentUser).getLogs();
            return new Result<>(true, "success", map);
        }else{
            return new Result<>(false,"current user is not manager", null);
        }
    }


    /**
     * 获取当前的客户列表
     * @return
     */
    public Result<List<Consumer>> getConsumers(){
        if(currentUser instanceof Manager){
            List<Consumer> list = getConsumerList();
            return new Result<>(true, "success", list);
        }else{
            return new Result<>(false,"current user is not manager", null);
        }
    }

    /**
     *
     * @return
     */
    private List<Consumer> getConsumerList() {
        return userDao.getConsumerList();
    }

    /**
     * 选择一种货币类型
     * @param type
     * @return
     */
    public Result<Void> chooseCurrency(int type){
        if (currentAccount == null) {
            return new Result<>(false, "Current account is null", null);
        }
        boolean result = currentAccount.chooseCurrency(type);
        if(result){
            return new Result<>(true, "success", null);
        }else{
            return new Result<>(false,"current account can not choose", null);
        }
    }

    /**
     * 获取当前账户的信息
     * @return
     */
    public Result<Account> getCurrentAccountInfo(){
        if (currentAccount == null) {
            return new Result<>(false, "Current account is null", null);
        }
        return new Result<>(true, "success", currentAccount);
    }


    /**
     * 获取当前客户的信息
     * @return
     */
    public Result<User> getCurrentUserInfo(){
        if (currentUser == null) {
            return new Result<>(false, "Current user is null", null);
        }
        return new Result<>(true, "success", currentUser);
    }

    /**
     * 给指定账户转账
     * @param userName
     * @param accountNo
     * @param transferMoney
     * @return
     */
    public Result<Void> transfer(String userName, String accountNo, int transferMoney, int currencyType){
        Consumer consumer = userDao.searchConsumerByName(userName);
        if(consumer == null){
            return new Result<>(false, "The consumer is not exist", null);
        }

        Account account = consumer.searchAccountByNo(accountNo);

        if(account == null){
            return new Result<>(false, "The account is not exist", null);
        }

        if(currentAccount.getBalance() < transferMoney){
            return new Result<>(false, "account money is not enough", null);
        }else{
            currentAccount.draw(transferMoney, currencyType);
            account.saving(transferMoney, currencyType);
            currentAccount.fee(1);
            logDao.addLog(currentUser.getId(), new Log(Timer.getInstance().getTimeStr(), "transfer to " + accountNo
                    + " " + transferMoney + " success"));
        }
        return new Result<>(true, "Transfer Success", null);
    }

    /**
     * 获取抵押物的列表
     * @return
     */
    public Result<List<Collateral>> getCollateralList(){
        List<Collateral> collateralList = loanDao.getCollateralList();

        return new Result<>(true, "success", collateralList);
    }

    /**
     * 贷款，用指定的抵押物
     *
     * @param loanType
     * @return
     */
    public Result<Void> loan(int loanType){
        //当前账号是贷款账户
        if(!(currentAccount instanceof LoanAccount)){
            return new Result<>(false, "current account is not a loanAccount", null);
        }

        boolean result = ((LoanAccount) currentAccount).loan(loanType);
        if(result){
            currentAccount.fee(1);
            return new Result<>(true,"loan success",null);
        }else{
            return new Result<>(false,"loan fail",null);
        }
    }

    /**
     * 货币转化
     * @param currencyType
     * @param toCurrencyType
     * @param money
     * @return
     */
    public Result<Void> convert(int currencyType , int toCurrencyType, int money){
        boolean result = currentAccount.convert(currencyType, toCurrencyType, money);
        if(result){
            currentAccount.fee(1);
            return new Result<>(true,"convert success",null);
        }else{
            return new Result<>(false,"convert fail",null);
        }
    }

    /**
     * 获取股票列表
     * @return
     */
    public Result<List<Stock>> getStockList(){
        List<Stock> stockList = stockDao.getStockList();

        if(stockList == null || stockList.isEmpty()){
            return new Result<>(true, "stock is empty", null);
        }
        return new Result<>(true, "success", stockList);
    }

    /**
     * 修改股票列表，经理操作
     */
    public Result<Void> updateStockList(String stockId, int price){
        stockDao.updateStock(stockId, price);
        return new Result<>(true, "success", null);
    }

    /**
     * 保存股票，经理操作
     * @param stockId
     * @param price
     * @return
     */
    public Result<Void> saveStock(String stockId, int price){
        stockDao.updateStock(stockId, price);
        return new Result<>(true, "success", null);
    }

    /**
     * 获取当前账号的股票列表
     * @return
     */
    public Result<Map<Stock, Integer>> getAccountStockList(){
        if(currentAccount instanceof SecurityAccount){
            Map<Stock, Integer> accountStockMap = ((SecurityAccount) currentAccount).getAccountStockMap();
            return new Result<>(true, "success", accountStockMap);
        }else{
            return new Result<>(true, "success", null);
        }
    }

    /**
     * 购买股票
     * @param stockId
     * @param stockAmount
     * @return
     */
    public Result<Map<Stock, Integer>> purchasingStock(String stockId, int stockAmount){
        if(currentAccount instanceof SecurityAccount){
            SecurityAccount securityAccount = (SecurityAccount) currentAccount;
            boolean result = securityAccount.purchasingStock(stockId, stockAmount);
            if(result){
                currentAccount.fee(1);
                return new Result<>(true, "success", securityAccount.getAccountStockMap());
            }else{
                return new Result<>(false, "fail", null);
            }
        }else{
            return new Result<>(false, "current account is not a security account", null);
        }
    }

    /**
     * 卖股票
     * @param stockId
     * @param stockAmount
     * @return
     */
    public Result<Map<Stock, Integer>> sellStock(String stockId, int stockAmount){
        if(currentAccount instanceof SecurityAccount){
            SecurityAccount securityAccount = (SecurityAccount) currentAccount;
            boolean result = securityAccount.sellStock(stockId, stockAmount);
            if(result){
                currentAccount.fee(1);
                return new Result<>(true, "success", securityAccount.getAccountStockMap());
            }else{
                return new Result<>(false, "fail", null);
            }
        }else{
            return new Result<>(false, "current account is not a security account", null);
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
