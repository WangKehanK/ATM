package manager.user;

import manager.account.*;

import java.util.List;

/**
 * User abstract class
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
