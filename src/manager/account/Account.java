package manager.account;

/**
 * Account的抽象类
 */
public interface Account {

    boolean saving(int money);

    int getBlance();

    boolean draw(int money);

    boolean chooseCurrency(int type);
}
