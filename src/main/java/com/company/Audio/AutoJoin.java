package com.company.Audio;

public class AutoJoin {

    private static Boolean autoJoin = false;

    public static Boolean getAutoJoin() {
        return autoJoin;
    }

    public static void setAutoJoin(Boolean autoJoin) {
        AutoJoin.autoJoin = autoJoin;
    }
}
