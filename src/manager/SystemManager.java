package manager;

import dao.UserDao;
import manager.account.*;
import manager.entity.Result;
import manager.user.Consumer;
import manager.user.Manager;
import manager.user.User;

import java.util.List;

public class SystemManager {
    private User currentUser;
    private Account currentAccount;

    private UserDao userDao = UserDao.getInstance();

    public SystemManager() {
    }

    public Result<Consumer> consumerLogin(String userName, String password) {
        if (userName == null || userName.trim().equals("")
                || password == null || password.trim().equals("")) {
            return new Result(false, "", null);
        }
        Consumer consumer = userDao.searchConsumer(userName, password);
        if (consumer == null) {
            return new Result(false, "error consumer", null);
        }

        return new Result(true, "success", consumer);
    }

    public Result<Manager> managerLogin(String userName, String password) {
        if (userName == null || userName.trim().equals("")
                || password == null || password.trim().equals("")) {
            return new Result(false, "", null);
        }
        Manager manager = userDao.searchManager(userName, password);
        if (manager == null) {
            return new Result(false, "error manager", null);
        }

        return new Result(true, "success", manager);
    }


    public Result<Consumer> registerConsumer(String userName, String password) {
        boolean result = userDao.addConsumer(userName, password);
        if (result) {
            return new Result(true, "register success", userDao.searchConsumer(userName, password));
        } else {
            return new Result(false, "consumer is exist", null);
        }
    }

    /**
     * 考虑返回PO
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

    public Result<Account> chooseAccount(int accountType) {
        if (currentUser == null) {
            return new Result<>(false, "Current user is null", null);
        }
        if (accountType == AccountType.CHECKING.getAccountType()) {
            currentAccount = currentUser.getCheckingAccount();
        } else if (accountType == AccountType.SAVING.getAccountType()) {
            currentAccount = currentUser.getSavingAccount();
        } else if (accountType == AccountType.LOAN.getAccountType()) {
            currentAccount = currentUser.getLoanAccount();
        } else if (accountType == AccountType.SECURITY.getAccountType()) {
            currentAccount = currentUser.getSecurityAccount();
        } else {
            return new Result<>(false, "error type", null);
        }

        return new Result<>(true, "success", currentAccount);
    }

    public Result<Void> saving(int money){
        if (currentAccount == null) {
            return new Result<>(false, "Current account is null", null);
        }

        boolean result = currentAccount.saving(money);
        if(!result){
            return new Result<>(false, "Account saving fail", null);
        }else{
            return new Result<>(true, "Saving success", null);
        }
    }

    public Result<Void> draw(int money){
        if (currentAccount == null) {
            return new Result<>(false, "Current account is null", null);
        }

        if(currentAccount.getBlance() < money){
            return new Result<>(false, "account money is not enough", null);
        }
        boolean result = currentAccount.draw(money);
        if(!result){
            return new Result<>(false, "Account draw fail", null);
        }else{
            return new Result<>(true, "draw success", null);
        }
    }

    public Result<Account> createSavingAccount(){
        if(currentUser.hasSavingAccount()){
            return new Result<>(false, "User has Saving account", null);
        }else{
            SavingAccount savingAccount = currentUser.createSavingAccount();
            if(savingAccount != null){
                return new Result<>(true, "success", savingAccount);
            }else{
                return new Result<>(false, "Saving account create fail", null);
            }
        }
    }

    public Result<Account> createCheckingAccount(){
        if(currentUser.hasSavingAccount()){
            return new Result<>(false, "User has Checking account", null);
        }else{
            CheckingAccount checkingAccount = currentUser.createCheckingAccount();
            if(checkingAccount != null){
                return new Result<>(true, "success", checkingAccount);
            }else{
                return new Result<>(false, "Checking account create fail", null);
            }
        }
    }

    public Result<Account> createLoanAccount(){
        if(currentUser.hasSavingAccount()){
            return new Result<>(false, "User has Loan account", null);
        }else{
            LoanAccount loanAccount = currentUser.createLoanAccount();
            if(loanAccount != null){
                return new Result<>(true, "success", loanAccount);
            }else{
                return new Result<>(false, "Loan account create fail", null);
            }
        }
    }

    public Result<Account> createSecurityAccount(){
        if(currentUser.hasSavingAccount()){
            return new Result<>(false, "User has Security account", null);
        }else{
            SavingAccount savingAccount = currentUser.createSecurityAccount();
            if(savingAccount != null){
                return new Result<>(true, "success", savingAccount);
            }else{
                return new Result<>(false, "Security account create fail", null);
            }
        }
    }

    //TODO:每次操作添加日志
    public Result<List<Log>> getLog(){
        if(currentUser instanceof Manager){
            List<Log> list = ((Manager) currentUser).getLogs();
            return new Result<>(true, "success", list);
        }else{
            return new Result<>(false,"current user is not manager", null);
        }
    }

    public Result<List<User>> geUserList(){
        if(currentUser instanceof Manager){
            List<User> list = getUserList();
            return new Result<>(true, "success", list);
        }else{
            return new Result<>(false,"current user is not manager", null);
        }
    }

    private List<User> getUserList() {
        return null;
    }


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

    


}
