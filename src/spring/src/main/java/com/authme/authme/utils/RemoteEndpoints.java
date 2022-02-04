package com.authme.authme.utils;

public class RemoteEndpoints {
    private static final String entry = "http://localhost:8080/dev/profile/create";
    private static final String profile = "http://localhost:8080/dev/profile";
    private static final String picture = "http://localhost:8080/dev/profile/picture/";
    private static final String pictures = "http://localhost:8080/dev/profile/picture/";

    public static String entry() {
        return entry;
    }

    public static String profile(Long dataId) {
        return profile + "/" + dataId;
    }

    public static String picture(Long userId, Long pictureId) {
        return picture + userId + "/" + pictureId;
    }

    public static String picture(Long userId) {
        return pictures + userId;
    }
}
