package manager.user;

import dao.AccountDao;
import dao.LogDao;
import dao.UserDao;
import manager.account.*;
import manager.entity.Log;
import manager.timer.Timer;

import java.util.ArrayList;
import java.util.List;

/**
 * 顾客
 */
public class Consumer extends AbstractUser {
    private String consumerId;

    private SavingAccount savingAccount;
    private CheckingAccount checkingAccount;
    private LoanAccount loanAccount;
    private SecurityAccount securityAccount;

    private List<Account> accountList;

    private AccountDao accountDao = AccountDao.getInstance();
    private UserDao userDao = UserDao.getInstance();
    private LogDao logDao = LogDao.getInstance();


    public Consumer(String userName, String password) {
        super(userName, password);
        accountList = new ArrayList<>();
        this.consumerId = userDao.getNewConsumerId();

        userDao.createConsumer(this);
    }

    public Consumer(String userName, String password, String consumerId) {
        super(userName, password);
        accountList = new ArrayList<>();
        this.consumerId = consumerId;

        reloadAccountList();
    }

    /**
     * 恢复用户的account信息
     */
    private void reloadAccountList() {
        List<Account> dbAccountList = accountDao.getUserAccount(consumerId);
        if(dbAccountList == null){
            return;
        }
        for(Account account : dbAccountList){
            if(account.getAccountType() == AccountType.SAVING.getAccountType()){
                savingAccount = (SavingAccount) account;
                accountList.add(savingAccount);
            }else if(account.getAccountType() == AccountType.CHECKING.getAccountType()){
                checkingAccount = (CheckingAccount) account;
                accountList.add(checkingAccount);
            }else if(account.getAccountType() == AccountType.LOAN.getAccountType()){
                loanAccount = (LoanAccount) account;
                accountList.add(loanAccount);
            }else if(account.getAccountType() == AccountType.SECURITY.getAccountType()){
                securityAccount = (SecurityAccount) account;
                accountList.add(securityAccount);
            }
        }

    }

    @Override
    public List<Account> getAccounts() {
        return accountList.isEmpty()? null : accountList;
    }

    @Override
    public Account getCheckingAccount() {
        return checkingAccount;
    }

    @Override
    public Account getSavingAccount() {
        return savingAccount;
    }

    @Override
    public Account getLoanAccount() {
        return loanAccount;
    }

    @Override
    public Account getSecurityAccount() {
        return securityAccount;
    }

    @Override
    public boolean hasSavingAccount() {
        return savingAccount != null;
    }

    @Override
    public boolean hasCheckingAccount() {
        return checkingAccount != null;
    }

    @Override
    public boolean hasLoanAccount() {
        return loanAccount != null;
    }

    @Override
    public boolean hasSecurityAccount() {
        return securityAccount != null;
    }

    @Override
    public SavingAccount createSavingAccount() {
        if(savingAccount != null){
            return null;
        }
        SavingAccount savingAccount = new SavingAccount(consumerId, AccountDao.DAY_RATE);
        this.savingAccount = savingAccount;
        accountList.add(savingAccount);
        accountDao.addAccount(consumerId, savingAccount);
        logDao.addLog(consumerId, new Log(Timer.getInstance().getTimeStr(), "create new saving account"));
        savingAccount.fee(1);
        return savingAccount;
    }

    @Override
    public CheckingAccount createCheckingAccount() {
        if(checkingAccount != null){
            return null;
        }
        CheckingAccount checkingAccount = new CheckingAccount(consumerId);
        this.checkingAccount = checkingAccount;
        accountList.add(checkingAccount);
        accountDao.addAccount(consumerId, checkingAccount);
        logDao.addLog(consumerId, new Log(Timer.getInstance().getTimeStr(), "create new checking account"));
        checkingAccount.fee(1);
        return checkingAccount;
    }

    @Override
    public LoanAccount createLoanAccount(int loanRateType) {
        if(loanAccount != null){
            return null;
        }
        LoanAccount loanAccount = new LoanAccount(consumerId, loanRateType);
        this.loanAccount = loanAccount;
        accountList.add(loanAccount);
        accountDao.addAccount(consumerId, loanAccount);
        logDao.addLog(consumerId, new Log(Timer.getInstance().getTimeStr(), "create new loan account"));
        loanAccount.fee(1);
        return loanAccount;
    }

    @Override
    public SecurityAccount createSecurityAccount() {
        if(securityAccount != null){
            return null;
        }
        SecurityAccount securityAccount = new SecurityAccount(consumerId);
        this.securityAccount = securityAccount;
        accountList.add(securityAccount);
        accountDao.addAccount(consumerId, securityAccount);
        logDao.addLog(consumerId, new Log(Timer.getInstance().getTimeStr(), "create new security account"));
        securityAccount.fee(1);
        return securityAccount;
    }

    @Override
    public Account searchAccountByNo(String accountId) {
        for(Account account : accountList){
            if(account.getAccountId().equals(accountId)){
                return account;
            }
        }

        return null;
    }

    @Override
    public String getId() {
        return consumerId;
    }



    public String getConsumerId() {
        return consumerId;
    }

    public String getBalance(){
        String str ="";
        for(Account account : accountList){
            str += account.getBalanceStr();
        }
        return str;
    }
}
