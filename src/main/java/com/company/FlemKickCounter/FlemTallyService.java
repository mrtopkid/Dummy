package com.company.FlemKickCounter;

import com.company.EventListenerWeakReference;
import com.company.Main;
import com.company.MainReference;
import net.dv8tion.jda.api.entities.Guild;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.*;
import java.util.Properties;

public class FlemTallyService implements Job {

    public FlemTallyService() {}

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Integer flemMoment = 0;

        try (InputStream input = new FileInputStream("flem.properties")) {

            Properties prop = new Properties();

            prop.load(input);

            flemMoment = Integer.valueOf(prop.getProperty("tally", "0"));
            System.out.println("It has been " + flemMoment + " days since flem was last kicked");
            Main.getJda().getGuildById("710599559562264637").getTextChannelById("805854297443139614").sendMessage("It has been " + flemMoment + " days since flem was last kicked").queue();

            EventListenerWeakReference.getReference().get().sayDumbShit("It has been " + flemMoment + " days since flem was last kicked");

            try (OutputStream output = new FileOutputStream("flem.properties")) {

                flemMoment++;

                prop.setProperty("tally", String.valueOf(flemMoment));

                prop.store(output, null);

                System.out.println(prop);
            } catch (IOException io) {
                io.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
