package manager;

import manager.timer.Timer;

import java.util.HashMap;
import java.util.Map;

public class BankIncomeLedger {

    private static BankIncomeLedger bankIncomeLedger = new BankIncomeLedger();

    private Map<String, Integer> incomeMap;

    private Timer timer = Timer.getInstance();

    private BankIncomeLedger() {
        incomeMap = new HashMap<>();
    }

    public static BankIncomeLedger getInstance(){
        return bankIncomeLedger;
    }

    public void income(int fee){
        Integer income = incomeMap.getOrDefault(timer.getTimeStr(), 0);
        income += fee;
        incomeMap.put(timer.getTimeStr(), income);
    }
}
