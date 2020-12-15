package manager.user;

import dao.LogDao;
import manager.entity.Log;
import manager.account.*;

import java.util.List;
import java.util.Map;

/**
 * This file is for Manager class that extends AbstractUser class
 * contains all the functions we need for Manager class
 */
public class Manager extends AbstractUser {
    private LogDao logDao = LogDao.getInstance();

    public Manager(String userName, String password) {
        super(userName, password);
    }

    public Map<String, List<Log>> getLogs(){
        return logDao.getLogs();
    }

    @Override
    public List<Account> getAccounts() {
        return null;
    }

    @Override
    public Account getCheckingAccount() {
        return null;
    }

    @Override
    public Account getSavingAccount() {
        return null;
    }

    @Override
    public Account getLoanAccount() {
        return null;
    }

    @Override
    public Account getSecurityAccount() {
        return null;
    }

    @Override
    public boolean hasSavingAccount() {
        return false;
    }

    @Override
    public SavingAccount createSavingAccount() {
        return null;
    }

    @Override
    public CheckingAccount createCheckingAccount() {
        return null;
    }

    @Override
    public LoanAccount createLoanAccount(int loanRateType) {
        return null;
    }

    @Override
    public SecurityAccount createSecurityAccount() {
        return null;
    }

    @Override
    public Account searchAccountByNo(String accountNo) {
        return null;
    }

    @Override
    public String getId() {
        return 1+"";
    }

    @Override
    public boolean hasCheckingAccount() {
        return false;
    }

    @Override
    public boolean hasLoanAccount() {
        return false;
    }

    @Override
    public boolean hasSecurityAccount() {
        return false;
    }

}
