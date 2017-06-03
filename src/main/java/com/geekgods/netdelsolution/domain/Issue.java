package com.geekgods.netdelsolution.domain;

public enum Issue {
    GUN_SAFETY("Gun Safety"),
    CHILD_RIGHTS("Child Rights"),
    MARIJUIANA_REFORM("Marijuiana Reform");

    private String displayName;

    Issue(String displayName) {

        this.displayName = displayName;
    }
}
