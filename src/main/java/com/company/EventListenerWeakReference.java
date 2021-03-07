package com.company;

import java.lang.ref.WeakReference;

public class EventListenerWeakReference {

    private static WeakReference<EventListener> mainWeakReference;

    public static void updateInstance(EventListener main){
        mainWeakReference = new WeakReference<>(main);
    }

    public static WeakReference<EventListener> getReference() {
        return mainWeakReference;
    }

}
