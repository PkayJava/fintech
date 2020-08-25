package com.angkorteam.fintech.client.dto;

import java.util.List;

public class ErrorItem {

    private String developerMessage;

    private String defaultUserMessage;

    private String userMessageGlobalisationCode;

    private String parameterName;

    private String value;

    private List<ErrorValue> args;

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getDefaultUserMessage() {
        return defaultUserMessage;
    }

    public void setDefaultUserMessage(String defaultUserMessage) {
        this.defaultUserMessage = defaultUserMessage;
    }

    public String getUserMessageGlobalisationCode() {
        return userMessageGlobalisationCode;
    }

    public void setUserMessageGlobalisationCode(String userMessageGlobalisationCode) {
        this.userMessageGlobalisationCode = userMessageGlobalisationCode;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ErrorValue> getArgs() {
        return args;
    }

    public void setArgs(List<ErrorValue> args) {
        this.args = args;
    }

}
