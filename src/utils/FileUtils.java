package utils;

import dao.StockDao;
import manager.account.*;
import manager.entity.Collateral;
import manager.entity.Stock;
import manager.user.Consumer;
import manager.user.Manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *  A file util class, read our database (csv files)
 */

public class FileUtils {
    public static void saveConsumers(List<Consumer> consumerList){
        File file = new File("consumers.csv");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("userId userName  password");
            fw.write(System.lineSeparator());
            for(Consumer consumer : consumerList){
                fw.write(consumer.getId() + " " +consumer.getUserName() + " " + consumer.getPassword());
                fw.write(System.lineSeparator());
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Consumer> readConsumers() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("consumers.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String s = sc.nextLine();
        List<Consumer> consumerList = new ArrayList<>();

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] arr = line.split(" ");
            consumerList.add(new Consumer(arr[1], arr[2], arr[0]));
        }
        sc.close();
        return consumerList;
    }

    public static List<Manager> readManagers() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("managers.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.nextLine();
        List<Manager> managerList = new ArrayList<>();

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] arr = line.split(" ");
            managerList.add(new Manager(arr[0], arr[1]));
        }
        sc.close();
        return managerList;
    }

    public static Map<String, List<Account>> readAccountMap() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("account.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.nextLine();
        Map<String, List<Account>> accountMap = new HashMap<>();

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] arr = line.split(" ");

            String userId = arr[0];
            Integer accountType = Integer.parseInt(arr[1]);
            String accountId = arr[2];
            Account account = null;
            String str = arr[3];
            if(accountType == AccountType.SAVING.getAccountType()){
                SavingAccount savingAccount = new SavingAccount(accountId, userId);
                String[] currencyArr = str.split("\\|");
                Map<Integer, Integer> map = new HashMap<>();
                for(String currency : currencyArr){
                    String[] balanceArr = currency.split(":");
                    map.put(Integer.parseInt(balanceArr[0]), Integer.parseInt(balanceArr[1]));
                }
                savingAccount.reloadAccount(map);
                savingAccount.setRateType(Integer.parseInt(arr[4]));
                account = savingAccount;
            }else if(accountType == AccountType.CHECKING.getAccountType()){
                CheckingAccount checkingAccount = new CheckingAccount(accountId, userId);
                String[] currencyArr = str.split("\\|");
                Map<Integer, Integer> map = new HashMap<>();
                for(String currency : currencyArr){
                    String[] balanceArr = currency.split(":");
                    map.put(Integer.parseInt(balanceArr[0]), Integer.parseInt(balanceArr[1]));
                }
                checkingAccount.reloadAccount(map);
                account = checkingAccount;
            }else if(accountType == AccountType.LOAN.getAccountType()){
                String[] loanArr = str.split("\\|");
                int balance = Integer.parseInt(loanArr[0]);
                int loan = Integer.parseInt(loanArr[1]);
                int loanRateType = Integer.parseInt(loanArr[2]);
                LoanAccount loanAccount = new LoanAccount(accountId,userId , balance, loan, loanRateType);
                String[] collateralArr = loanArr[3].split(",");
                List<Integer> list = new ArrayList<>();
                for(String type : collateralArr){
                    list.add(Integer.parseInt(type));
                }

                loanAccount.reloadLoan(list);
                account = loanAccount;
            }else if(accountType == AccountType.SECURITY.getAccountType()){
                String[] securityArr = str.split("\\|");
                int balance = Integer.parseInt(securityArr[0]);
                SecurityAccount securityAccount = new SecurityAccount(accountId,userId , balance);
                String[] stockArr = securityArr[1].split(",");
                Map<Stock, Integer> stockMap = new HashMap<>();
                Map<Stock, Integer> stockPurchaseMap = new HashMap<>();
                for(String stock : stockArr){
                    String[] stockInfoArr = stock.split(":");
                    Stock stockInfo = StockDao.getInstance().getStockById(stockInfoArr[0]);
                    String[] numberArr = stockInfoArr[1].split("$");
                    stockMap.put(stockInfo, Integer.parseInt(numberArr[0]));
                    stockPurchaseMap.put(stockInfo, Integer.parseInt(numberArr[1]));
                }

                securityAccount.setStockMap(stockMap);
                securityAccount.setStockPurchaseMap(stockPurchaseMap);

                account = securityAccount;

            }
            List<Account> accountList = accountMap.getOrDefault(userId, new ArrayList<>());
            accountList.add(account);
            accountMap.put(userId, accountList);
        }
        sc.close();
        return accountMap;
    }

    public static void saveAccount(Map<String, List<Account>> accountMap) {
        File file = new File("account.csv");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("userId  accountType accountId accountContent");
            fw.write(System.lineSeparator());
            for(Map.Entry<String, List<Account>> entry: accountMap.entrySet()){
                for(Account account : entry.getValue()){
                    fw.write(entry.getKey() + " " + account.getAccountType() + " " + account.getAccountId() + " " + account.getStr());
                    fw.write(System.lineSeparator());
                }
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Collateral> readCollateralList() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("collateral.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.nextLine();
        List<Collateral> collateralList = new ArrayList<>();

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] arr = line.split(" ");

            int collateralType = Integer.parseInt(arr[0]);
            String collateralName = arr[1];
            int price = Integer.parseInt(arr[2]);

            Collateral collateral = new Collateral(collateralType, collateralName, price);

            collateralList.add(collateral);
        }
        sc.close();
        return collateralList;
    }

    public static List<Stock> readStockList() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("stock.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.nextLine();
        List<Stock> stockList = new ArrayList<>();

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] arr = line.split(" ");

            String stockId = arr[0];
            String stockName = arr[1];
            int price = Integer.parseInt(arr[2]);

            Stock stock = new Stock(stockId, stockName, price);

            stockList.add(stock);
        }
        sc.close();
        return stockList;
    }

    public static void saveStockList(List<Stock> stockList) {
        File file = new File("stock.csv");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("StockId  StockName Price");
            fw.write(System.lineSeparator());
            for(Stock stock : stockList){
                fw.write(stock.getStockId() + " " + stock.getStockName() + " " + stock.getPrice());
                fw.write(System.lineSeparator());

            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
