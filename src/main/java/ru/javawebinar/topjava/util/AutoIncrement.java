package ru.javawebinar.topjava.util;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoIncrement{
    private static AutoIncrement instance;
    private AtomicInteger id = new AtomicInteger(0);

    private AutoIncrement(){
    }

    public static AutoIncrement getInstance(){
        if(Objects.isNull(instance))
            instance = new AutoIncrement();
        return instance;
    }

    public int getID(){
        return id.incrementAndGet();
    }

}
