package com.company.DiscordLevelSystem;

import com.company.EventListenerWeakReference;
import com.company.Main;

import java.io.*;
import java.util.Properties;

public class UserScorePropertiesManager {

    public static Integer getScoreForUser(String ID) {
        try (InputStream input = new FileInputStream("userExp.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return Integer.valueOf(prop.getProperty(ID, "10"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static void setScoreForUser(String ID, Integer Score){
        try (OutputStream output = new FileOutputStream("userExp.properties")) {

            Properties prop = new Properties();

            prop.setProperty(ID, String.valueOf(Score));

            prop.store(output, null);

            System.out.println(prop);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void addCommentScore(String ID){
        Integer valueToIncrease = 10;
        try (InputStream input = new FileInputStream("userExp.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            valueToIncrease = Integer.valueOf(prop.getProperty(ID, "10"));
            if (valueToIncrease % 100 == 0){
                System.out.println("level up");
            }
            valueToIncrease = valueToIncrease + 10;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (OutputStream output = new FileOutputStream("userExp.properties")) {

            Properties prop = new Properties();

            prop.setProperty(ID, String.valueOf(valueToIncrease));

            prop.store(output, null);

            System.out.println(prop);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }


}
