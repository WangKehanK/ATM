package manager.account;

/**
 * Account的接口
 */
public interface Account {
    int USD = 1;
    int EURO = 2;
    int CNY = 3;

    double USD_2_EURO = 2;
    double USD_2_CNY = 10;
    double EURO_2_USD = 0.5;
    double EURO_2_CNY = 5;
    double CNY_2_USD = 0.1;
    double CNY_2_EURO = 0.2;


    boolean saving(int money);

    int getBalance();

    boolean draw(int money);

    boolean chooseCurrency(int type);

    boolean convert(int currencyType, int toCurrencyType, int money);

    String getAccountId();

    int getAccountType();

    String getStr();

    void fee(int fee);

    String getBalanceStr();

    boolean draw(int transferMoney, int currencyType);

    boolean saving(int transferMoney, int currencyType);
}
