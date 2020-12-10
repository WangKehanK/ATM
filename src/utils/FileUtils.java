package utils;

import manager.account.Account;
import manager.user.Consumer;
import manager.user.Manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileUtils {
    public static void saveConsumers(List<Consumer> consumerList){
        File file = new File("consumers.csv");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("userName  password");
            fw.write(System.lineSeparator());
            for(Consumer consumer : consumerList){
                fw.write(consumer.getUserName() + " " + consumer.getPassword());
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
            consumerList.add(new Consumer(arr[0], arr[1]));
        }
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
            swtich(){

            }
        }
        return accountMap;
    }
}
