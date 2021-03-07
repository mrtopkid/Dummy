package com.company.GoodBotBadBot;

import com.company.EventListener;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.*;
import java.util.Properties;

public class BotScore {

    public static void goodBotMethod(String[] command, TextChannel channel) {
        if ("bot".equals(command[2])) {

            Integer botScore = 0;
            try (InputStream input = new FileInputStream("goodbotbadbot.properties")) {

                Properties prop = new Properties();

                prop.load(input);

                botScore = Integer.valueOf(prop.getProperty("botscore", "0"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            channel.sendMessage("Good human").queue();
            botScore++;
            channel.sendMessage("Botscore ( " + botScore + " )").queue();
            SaveScore(botScore);
        }
    }

    public static void badBotMethod(String[] command, TextChannel channel) {
        if ("bot".equals(command[2])) {
            Integer badbotScore = 0;
            try (InputStream input = new FileInputStream("goodbotbadbot.properties")) {

                Properties prop = new Properties();

                prop.load(input);

                badbotScore = Integer.valueOf(prop.getProperty("botscore", "0"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            channel.sendMessage("Bad human >:(").queue();
            badbotScore--;
            channel.sendMessage("Botscore ( " + badbotScore + " )").queue();
            SaveScore(badbotScore);

        }
    }

    public static void SaveScore(Integer badbotScore) {
        try (OutputStream output = new FileOutputStream("goodbotbadbot.properties")) {

            Properties prop = new Properties();

            prop.setProperty("botscore", String.valueOf(badbotScore));

            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
