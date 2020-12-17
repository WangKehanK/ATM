package manager.user;

import manager.account.*;

import java.util.List;

/**
 * A basic User interface, can be extended to any types of user, e.g. Customers, Managers..
 */
public interface User {

    List<Account> getAccounts();

    Account getCheckingAccount();

    Account getSavingAccount();

    Account getLoanAccount();

    Account getSecurityAccount();

    boolean hasSavingAccount();

    SavingAccount createSavingAccount(int savingRateType);

    CheckingAccount createCheckingAccount();

    LoanAccount createLoanAccount(int loanRateType);

    SecurityAccount createSecurityAccount(int accountType, int money, int type);

    Account searchAccountByNo(String accountNo);

    String getId();

    boolean hasCheckingAccount();

    boolean hasLoanAccount();

    boolean hasSecurityAccount();
}
