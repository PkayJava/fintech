package com.angkorteam.fintech.dto;

public enum Decoration {

    Fa("fa"),
    Check("fa-check"),
    TextYellow("text-yellow");

    private final String css;

    Decoration(String css) {
        this.css = css;
    }

    public String getCss() {
        return css;
    }

}
