package com.authme.authme.utils;

public class RemoteEndpoints {
    private static final String server = "http://localhost:5000";
    private static final String entry = server + "/api/identity";
    private static final String picture = server + "/api/identityvalidity";

    public static String entry() {
        return entry;
    }

    public static String profile(Long dataId) {
        return entry + "/" + dataId;
    }

    public static String picture() {
        return picture;
    }
}
