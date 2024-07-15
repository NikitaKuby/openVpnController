package com.server.test.testPost.enums;

import lombok.Getter;

@Getter
public enum Comd {
    YES("-y"),
    NO("-n"),
    LISTCLIENTS("--listclients"),
    REVOKECLIENT("--revokeclient"),
    ADDCLIENTS("--addclient");



    private final String title;
    Comd(String title) {
        this.title = title;
    }
}
