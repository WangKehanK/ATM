package manager.user;

import manager.Log;
import manager.account.Account;
import manager.account.CheckingAccount;
import manager.account.LoanAccount;
import manager.account.SavingAccount;

import java.util.List;

/**
 * 经理类
 */
public class Manager extends AbstractUser {
    public Manager(String userName, String password) {
        super(userName, password);
    }

    public List<Log> getLogs(){
        return null;
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
    public LoanAccount createLoanAccount() {
        return null;
    }

    @Override
    public SavingAccount createSecurityAccount() {
        return null;
    }
}
