package com.company.Audio;

import com.company.GuildMusicManager;

import java.util.HashMap;
import java.util.Map;

public class MusicManagers {

    private final static Map<Long, GuildMusicManager> musicManagers = new HashMap<>();

    public static Map<Long, GuildMusicManager> getMusicManagers() {
        return musicManagers;
    }
}
