package ru.otus.kulygin.aop;

public class TestTimer {

    private long startTestTime;

    public void before() {
        startTestTime = System.currentTimeMillis();
    }

    public void after() {
        System.out.println("Spent time: " + (double) ((System.currentTimeMillis() - startTestTime) / 1000) + " seconds");
    }
}
