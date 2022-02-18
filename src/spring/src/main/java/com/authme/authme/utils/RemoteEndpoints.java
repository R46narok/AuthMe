package com.authme.authme.utils;

public class RemoteEndpoints {
    private static final String server = "http://localhost:5000";
    private static final String entry = server + "/api/identity";
    private static final String profile = server + "/api/identity";

    public static String entry() {
        return entry;
    }

    public static String profile(Long dataId) {
        return profile + "/" + dataId;
    }
}
