package com.company;

import java.io.*;
import java.util.Properties;

public class SwitchUserOnVCJoin {
//"C:\\Users\\thoma\\bumg.mp4" 407995681853407232 "https://www.youtube.com/watch?v=kHX6IBFXyzA"
    public static String getURL(String ID){

        try (InputStream input = new FileInputStream("userJoinAudio.properties")) {

            Properties prop = new Properties();

            prop.load(input);

            return prop.getProperty(ID, "bumg.mp4");
        } catch (IOException ex) {
            ex.printStackTrace();
            return "bumg.mp4";
        }

        /*

        return switch (ID) {
            case "407995681853407232" -> "https://www.youtube.com/watch?v=kHX6IBFXyzA";
            case "216641843641712641" -> "https://www.youtube.com/watch?v=_czzkqPjvS4";
//Flem
            case "572870594232582154" -> "bungus.mp4";
            case "525348643289694212" -> "https://www.youtube.com/watch?v=FODS-7wDEN0";
            case "325747500822560770" -> "https://www.youtube.com/watch?v=-uAZdIJIl8o";
            case "175679469690945536" -> "bumg.mp4";
            case "692404409790890125" -> "https://www.youtube.com/watch?v=LzxCJzM4xLo";
            default -> "wack.mp4";
        };

         */
    }

    public static void editUserSounds(String ID, String soundValue){

        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("userJoinAudio.properties")) {
            prop.load(input);
        } catch (Exception E) {
            E.printStackTrace();
        }

        try (OutputStream output = new FileOutputStream("userJoinAudio.properties")) {

            prop.setProperty(ID, soundValue);

            prop.store(output, "");

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }


}
