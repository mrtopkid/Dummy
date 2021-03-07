package com.company;

import com.sun.speech.freetts.FreeTTS;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
import com.sun.speech.freetts.jsapi.FreeTTSVoice;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

public class TextToSpeechService {



    public Boolean generateAndReturnSpeech(String message){
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        AudioPlayer audioPlayer;
        Voice voice = VoiceManager.getInstance().getVoice("kevin");
        System.out.println(VoiceManager.getInstance().getVoices()[0] + " + " + VoiceManager.getInstance().getVoices()[1]);

        Random s = new Random();
        int lows = 130;
        int highs = 170;
        int results = s.nextInt(highs-lows) + lows;

        Random p = new Random();
        int lowp = 70;
        int highp = 130;
        int resultp = p.nextInt(highp-lowp) + lowp;

        if (voice != null){
            voice.allocate();
        }
        try {

            audioPlayer = new SingleFileAudioPlayer("tts", AudioFileFormat.Type.WAVE);
            voice.setAudioPlayer(audioPlayer);

            voice.setRate(results);
            voice.setPitch(resultp);
            voice.setVolume(200);

            voice.speak(message);

            voice.deallocate();
            audioPlayer.close();

            return true;
        } catch (Exception E) {
            E.printStackTrace();
            return false;
        }
    }

    public Boolean generateAndReturnSpeechSetValues(String message, int pitch, int speed){
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        AudioPlayer audioPlayer;
        Voice voice = VoiceManager.getInstance().getVoice("kevin");
        System.out.println(VoiceManager.getInstance().getVoices()[0] + " + " + VoiceManager.getInstance().getVoices()[1]);

        if (voice != null){
            voice.allocate();
        }
        try {

            audioPlayer = new SingleFileAudioPlayer("tts", AudioFileFormat.Type.WAVE);
            voice.setAudioPlayer(audioPlayer);

            voice.setRate(speed);
            voice.setPitch(pitch);
            voice.setVolume(200);

            voice.speak(message);

            voice.deallocate();
            audioPlayer.close();

            return true;
        } catch (Exception E) {
            E.printStackTrace();
            return false;
        }
    }

}
