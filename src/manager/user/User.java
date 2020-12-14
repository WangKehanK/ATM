package manager.user;

import manager.account.*;

import java.util.List;

/**
 * 用户抽象类
 */
public interface User {

    List<Account> getAccounts();

    Account getCheckingAccount();

    Account getSavingAccount();

    Account getLoanAccount();

    Account getSecurityAccount();

    boolean hasSavingAccount();

    SavingAccount createSavingAccount();

    CheckingAccount createCheckingAccount();

    LoanAccount createLoanAccount(int loanRateType);

    SecurityAccount createSecurityAccount();

    Account searchAccountByNo(String accountNo);

    String getId();

    boolean hasCheckingAccount();

    boolean hasLoanAccount();

    boolean hasSecurityAccount();
}
