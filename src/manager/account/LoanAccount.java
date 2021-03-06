package manager.account;

import dao.AccountDao;
import dao.LoanDao;
import dao.LogDao;
import manager.BankIncomeLedger;
import manager.entity.Collateral;
import manager.entity.Log;
import manager.timer.Timer;
import manager.timer.TimerObserver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This file is for loan account, implement the account interface and time observer
 * contains all functions for loan account
 */
public class LoanAccount  implements Account, TimerObserver {
    private String accountId;
    private String userId;

    private int balance;
    private int loan;
    private int loanRateType;

    private List<Collateral> collateralList;
    private LoanDao loanDao = LoanDao.getInstance();
    private AccountDao accountDao;
    private Timer timer = Timer.getInstance();
    private BankIncomeLedger bankIncomeLedger = BankIncomeLedger.getInstance();
    private LogDao logDao = LogDao.getInstance();

    public LoanAccount(String userId, int loanRateType) {
        this.userId = userId;

        collateralList = new ArrayList<>();
        this.balance = 0;
        this.loan = 0;
        this.accountId = getAccountDao().getNewAccountId();
        this.loanRateType = loanRateType;
        timer.addTimerObserver(this);
    }

    public LoanAccount(String accountId, String userId, int balance, int loan, int loanRateType) {
        collateralList = new ArrayList<>();
        this.balance = balance;
        this.loan = loan;
        this.accountId = accountId;
        this.userId = userId;
        this.loanRateType = loanRateType;
        timer.addTimerObserver(this);
    }

    private AccountDao getAccountDao() {
        if(accountDao == null){
            accountDao = AccountDao.getInstance();
        }
        return accountDao;
    }

    public void reloadLoan(List<Integer> loanList) {
        for(Integer type : loanList){
            Collateral collateral = loanDao.getCollateral(type);
            collateralList.add(collateral);
        }
    }

    public boolean loan(int loanType) {
        Collateral collateral = loanDao.getCollateral(loanType);
        if(collateral ==null){
            return false;
        }

        if (collateralList.contains(collateral)) {
            return false;
        }

        collateralList.add(collateral);
        balance += collateral.getPrice();
        loan += collateral.getPrice();

        getAccountDao().updateAccount(this);
        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "loan "+ collateral.getPrice() + " by " + collateral.getName()));
        return true;
    }

    public int getLoan(){
        return loan;
    }


    public String getCurrentLoanListStr(){
        String str = "-------current loan list ------";
        for(Collateral collateral : collateralList){
            str += System.lineSeparator();
            str += collateral;
        }
        return str;
    }

    @Override
    public boolean saving(int money) {
        return false;
    }

    @Override
    public int getBalance() {
        return balance;
    }


    @Override
    public boolean draw(int money) {
        return false;
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
        return AccountType.LOAN.getAccountType();
    }

    @Override
    public String getStr() {
        //Need name, type, rate
        String str = "";
        str += balance;
        str += "|";
        str += loan;
        str += "|";
        str += loanRateType;
        str += "|";
        for(Collateral collateral : collateralList){
            str += collateral.getType();
            str += ",";
        }

        if(collateralList.size() > 0){
            str = str.substring(0, str.length() - 1);
        }

        return str;
    }

    @Override
    public void timeChange() {
        //Update of loan repayment amount
        //TODO:There is no statistics on how much you earn
        Calendar calendar = timer.getCalendar();
        if(loanRateType == LoanDao.DAY_RATE && calendar.get(Calendar.HOUR) == 0){
            loan = (int) Math.ceil(loan * (1 + loanDao.getLoanRate(loanRateType)));
        }else if(loanRateType == LoanDao.MONTH_RATE && calendar.get(Calendar.DAY_OF_MONTH) == 1 && calendar.get(Calendar.HOUR) == 0){
            loan = (int) Math.ceil(loan * (1 + loanDao.getLoanRate(loanRateType)));
        }else if(loanRateType == LoanDao.YEAR_RATE && calendar.get(Calendar.MONTH) == 1 && calendar.get(Calendar.DAY_OF_MONTH) == 1 && calendar.get(Calendar.HOUR) == 0){
            loan = (int) Math.ceil(loan * (1 + loanDao.getLoanRate(loanRateType)));
        }
        getAccountDao().updateAccount(this);
    }

    @Override
    public void fee(int fee) {
        balance -= fee;
        bankIncomeLedger.income(fee);
        getAccountDao().updateAccount(this);
    }

    @Override
    public String getBalanceStr() {
        String str = "Loan[" + balance;
        str += "]";
        str+= System.lineSeparator();
        return str;
    }

    @Override
    public boolean draw(int transferMoney, int currencyType) {
        return false;
    }

    @Override
    public boolean saving(int transferMoney, int currencyType) {
        return false;
    }

    @Override
    public int getBalance(int type) {
        return 0;
    }
}
