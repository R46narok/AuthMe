package com.authme.authme.utils;

public class RemoteEndpoints {
    private final static String profile = "http://localhost:8080/dev/profile";
    private final static String entry = "http://localhost:8080/dev/entry";


    public static String entry() {
        return entry;
    }

    public static String profile() {
        return profile;
    }
}