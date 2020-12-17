package manager.user;

import dao.LogDao;
import manager.entity.Log;
import manager.account.*;

import java.util.List;
import java.util.Map;

/**
 * The customer user class, extends abstract user, use Dao to read the database
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
    public SavingAccount createSavingAccount(int savingRateType) {
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
    public SecurityAccount createSecurityAccount(int accountType, int money, int type) {
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
