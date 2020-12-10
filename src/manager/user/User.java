package manager.user;

import manager.account.Account;
import manager.account.CheckingAccount;
import manager.account.LoanAccount;
import manager.account.SavingAccount;

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

    LoanAccount createLoanAccount();

    SavingAccount createSecurityAccount();


}
