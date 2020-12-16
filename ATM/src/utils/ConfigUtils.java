package utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A config util class, can be used to read config.properties
 */
public class ConfigUtils {

    private static Map<String, String> configMap;

    static  {
        configMap = new HashMap<>();
        readConfig();
    }

    public static void readConfig(){
        configMap = new HashMap<>();
        File file = new File("config.properties");
        if(!file.exists()){
            file = new File("../config.properties");
        }
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if(line.startsWith("#")){
                    continue;
                }
                String[] arr = line.split("=");
                if(arr.length == 2){
                    configMap.put(arr[0].trim(), arr[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Config.properties is not exit!");
        }
    }

    public static String getConfig(String key, String defaultValue){
        return configMap.get(key) == null ? defaultValue: configMap.get(key);
    }

    public static double getConfigInteger(String key, double defaultValue) {
        String value = configMap.get(key);
        if(value == null){
            return defaultValue;
        }else{
            return Double.parseDouble(value);
        }
    }
}
