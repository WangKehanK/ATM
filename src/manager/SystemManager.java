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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The system manager class, the most important class that contains all the main logic for front end; the front end can import these functions in very easy and convinent way.
 */

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
     * consumerLogin
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
     * managerLogin
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
     * logout
     * @return
     */
    public Result<Void> logout(){
        currentUser = null;
        currentAccount = null;
        return new Result(true, "log out success", null);
    }


    /**
     * registerConsumer
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
     * get User Account
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
     * choose Account
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
     * save money
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
     * withdraw money
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
     * create Saving Account
     * @return
     */
    public Result<Account> createSavingAccount(int savingRateType){
        if(currentUser.hasSavingAccount()){
            return new Result<>(false, "User has Saving account", null);
        }else{
            SavingAccount savingAccount = currentUser.createSavingAccount(savingRateType);
            if(savingAccount != null){
                currentAccount = savingAccount;
                return new Result<>(true, "success", savingAccount);
            }else{
                return new Result<>(false, "Saving account create fail", null);
            }
        }
    }

    /**
     * create Checking Account
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
     * create Loan Account
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
     * create Security Account
     * @return
     * @param accountType
     * @param money
     * @param type
     */
    public Result<Account> createSecurityAccount(int accountType, int money, int type){
        if(accountType == AccountType.SAVING.getAccountType() ){
            if(currentUser.hasSavingAccount()){
                int balance = currentUser.getSavingAccount().getBalance(type);
                if(balance < money){
                    return new Result<>(false, "Saving account has no enough money", null);
                }
            }else{
                return new Result<>(false, "Saving account is not exist", null);
            }
        }
        if(accountType == AccountType.CHECKING.getAccountType() ){
            if(currentUser.hasCheckingAccount()){
                int balance = currentUser.getCheckingAccount().getBalance(type);
                if(balance < money){
                    return new Result<>(false, "Checking account has no enough money", null);
                }
            }else{
                return new Result<>(false, "Checking account is not exist", null);
            }
        }

        if(accountType !=  AccountType.CHECKING.getAccountType()&& accountType !=  AccountType.SAVING.getAccountType()){
            return new Result<>(false, " Account type error", null);
        }

        if(currentUser.hasSecurityAccount()){
            return new Result<>(false, "User has Security account", null);
        }else{
            SecurityAccount securityAccount = currentUser.createSecurityAccount(accountType, money, type);
            if(securityAccount != null){
                currentAccount = securityAccount;
                return new Result<>(true, "success", securityAccount);
            }else{
                return new Result<>(false, "Security account create fail", null);
            }
        }
    }

    //TODO:Add log for each operation

    /**
     * Get operation log
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
     * Get the current customer list
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
     * Choose a currency type
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
     * Get current account information
     * @return
     */
    public Result<Account> getCurrentAccountInfo(){
        if (currentAccount == null) {
            return new Result<>(false, "Current account is null", null);
        }
        return new Result<>(true, "success", currentAccount);
    }


    /**
     * Get current customer information
     * @return
     */
    public Result<User> getCurrentUserInfo(){
        if (currentUser == null) {
            return new Result<>(false, "Current user is null", null);
        }
        return new Result<>(true, "success", currentUser);
    }

    /**
     * Transfer to designated account
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
     * Get a list of collateral
     * @return
     */
    public Result<List<Collateral>> getCollateralList(){
        List<Collateral> collateralList = loanDao.getCollateralList();

        return new Result<>(true, "success", collateralList);
    }

    /**
     * Loan with designated collateral
     *
     * @param loanType
     * @return
     */
    public Result<Void> loan(int loanType){
        //The current account is a loan account
        if(!(currentAccount instanceof LoanAccount)){
            return new Result<>(false, "current account is not a loanAccount", null);
        }

        boolean result = ((LoanAccount) currentAccount).loan(loanType);
        if(result){
            currentAccount.fee(1);
            return new Result<>(true,"loan success",null);
        }else{
            return new Result<>(false,"loan fail or type is exist",null);
        }
    }

    /**
     * Currency conversion
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
     * Get stock list
     * @return
     */
    public Result<List<Stock>> getStockList(){
        List<Stock> stockList = stockDao.getStockList();

        if(stockList == null || stockList.isEmpty()){
            return new Result<>(true, "stock is empty", null);
        }
        return new Result<>(true, "success", new ArrayList<>(stockList));
    }

    /**
     * Add stock list, manager operation
     */
    public Result<Void> addStock(String stockId, String stockName, int price){
        if(stockDao.getStockById(stockId) != null){
            return new Result<>(false, "the stockId is exist!", null);
        }
        stockDao.addStock(stockId, stockName, price);
        return new Result<>(true, "success", null);
    }

    /**
     * Save stock, manager operation
     * @param stockId
     * @param price
     * @return
     */
    public Result<Void> saveStock(String stockId, int price){
        stockDao.updateStock(stockId, price);
        return new Result<>(true, "success", null);
    }

    /**
     * Delete stock, manager operation
     * @param stockId
     * @return
     */
    public Result<Void> delStock(String stockId){
        stockDao.delStock(stockId);
        return new Result<>(true, "success", null);
    }

    /**
     * Get the stock list of the current account
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
     * Buy stock
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
     * Sell stocks
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


    public Account getCurrentAccount(){
        return currentAccount;
    }

}
