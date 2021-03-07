package com.company.FlemRandomKick;

import com.company.EventListener;
import com.company.Main;
import com.company.MainReference;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FlemKickService {

    ExecutorService flemService;

    Boolean killLoop = false;

    EventListener listener;

    public FlemKickService(EventListener listener) {
        this.listener = listener;
        //Initialize new thread executor to synchronously handle loop
        flemService = Executors.newSingleThreadExecutor();
        flemService.execute(this::checkLoop);
    }

    private void checkLoop(){
        //Loops
        while (!killLoop){

            //Generate random value between 1 & 4000
            Random s = new Random();
            int lows = 1;
            int highs = 30000;
            int results = s.nextInt(highs-lows) + lows;

            //if random number equals 1000, kick flem
            if (results == 250){
                //try block, just in case JDA api returns null for get calls
                try {
                    Main.getJda().getGuildById("710599559562264637").kickVoiceMember(Main.getJda().getGuildById("710599559562264637").getMemberById("572870594232582154")).complete();
                    listener.sayDumbShit("Retard removed");
                } catch (Exception E){
                    E.printStackTrace();
                }

            }

            //Sleep for 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Kill loop if flem leaves
    public void setKillLoop(Boolean killLoop) {
        this.killLoop = killLoop;
    }
}
