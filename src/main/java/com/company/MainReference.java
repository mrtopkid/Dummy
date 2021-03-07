package com.company;

import java.lang.ref.WeakReference;

public class MainReference {

    private static WeakReference<Main> mainWeakReference;

    public static void updateInstance(Main main){
        mainWeakReference = new WeakReference<>(main);
    }

    public static WeakReference<Main> getMainWeakReference() {
        return mainWeakReference;
    }
}
