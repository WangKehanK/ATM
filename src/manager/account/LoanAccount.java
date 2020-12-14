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
 * 关于贷款的账户
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
        this.accountId = accountDao.getNewAccountId();
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

        collateralList.add(collateral);
        balance += collateral.getPrice();
        loan += collateral.getPrice();

        logDao.addLog(userId, new Log(Timer.getInstance().getTimeStr(), "loan "+ collateral.getPrice() + " by " + collateral.getName()));
        return true;
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
        //需要name，type，rate
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
        //贷款需还款金额的更新
        //TODO:收入多少钱没有统计
        Calendar calendar = timer.getCalendar();
        if(loanRateType == LoanDao.DAY_RATE && calendar.get(Calendar.HOUR) == 0){
            loan = (int) (loan * (1 + loanDao.getLoanRate(loanRateType)));
        }else if(loanRateType == LoanDao.MONTH_RATE && calendar.get(Calendar.DAY_OF_MONTH) == 1 && calendar.get(Calendar.HOUR) == 0){
            loan = (int) (loan * (1 + loanDao.getLoanRate(loanRateType)));
        }else if(loanRateType == LoanDao.YEAR_RATE && calendar.get(Calendar.MONTH) == 1 && calendar.get(Calendar.DAY_OF_MONTH) == 1 && calendar.get(Calendar.HOUR) == 0){
            loan = (int) (loan * (1 + loanDao.getLoanRate(loanRateType)));
        }
    }

    @Override
    public void fee(int fee) {
        balance -= fee;
        bankIncomeLedger.income(fee);
    }

    @Override
    public String getBalanceStr() {
        return "";
    }

    @Override
    public boolean draw(int transferMoney, int currencyType) {
        return false;
    }

    @Override
    public boolean saving(int transferMoney, int currencyType) {
        return false;
    }
}
