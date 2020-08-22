package com.angkorteam.fintech.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SurveyRequest {

    private long id;

    private List<ComponentData> componentDatas = new ArrayList<>();

    private List<QuestionData> questionDatas = new ArrayList<>();

    private String key;

    private String name;

    private String description;

    private String countryCode;

    private Date validFrom;

    private Date validTo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ComponentData> getComponentDatas() {
        return componentDatas;
    }

    public void setComponentDatas(List<ComponentData> componentDatas) {
        this.componentDatas = componentDatas;
    }

    public List<QuestionData> getQuestionDatas() {
        return questionDatas;
    }

    public void setQuestionDatas(List<QuestionData> questionDatas) {
        this.questionDatas = questionDatas;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public static class ComponentData {

        private long id;

        private String key;

        private String text;

        private String description;

        private int sequenceNo;

        public ComponentData() {
        }

        public ComponentData(final long id, final String key, final String text, final String description, final int sequenceNo) {
            this.id = id;
            this.key = key;
            this.text = text;
            this.description = description;
            this.sequenceNo = sequenceNo;
        }

        public long getId() {
            return id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getText() {
            return text;
        }

        public void setText(String title) {
            this.text = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSequenceNo() {
            return sequenceNo;
        }

        public void setSequenceNo(int sequenceNo) {
            this.sequenceNo = sequenceNo;
        }
    }

    public static class QuestionData {

        private long id;

        private List<ResponseData> responseDatas = new ArrayList<>();

        private String componentKey;

        private String key;

        private String text;

        private String description;

        private int sequenceNo;

        public QuestionData() {
        }

        public QuestionData(final long id, final List<ResponseData> responseDatas, final String componentKey, final String key,
                            final String text, final String description, final int sequenceNo) {
            this.id = id;
            this.responseDatas = responseDatas;
            this.componentKey = componentKey;
            this.key = key;
            this.text = text;
            this.description = description;
            this.sequenceNo = sequenceNo;
        }

        public long getId() {
            return id;
        }

        public List<ResponseData> getResponseDatas() {
            return responseDatas;
        }

        public void setResponseDatas(List<ResponseData> responseDatas) {
            this.responseDatas = responseDatas;
        }

        public String getComponentKey() {
            return componentKey;
        }

        public void setComponentKey(String componentKey) {
            this.componentKey = componentKey;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSequenceNo() {
            return sequenceNo;
        }

        public void setSequenceNo(int sequenceNo) {
            this.sequenceNo = sequenceNo;
        }
    }

    public static class ResponseData {

        private long id;

        private String text;

        private int value;

        private int sequenceNo;

        public ResponseData() {
        }

        public ResponseData(final long id, final String text, final int value, final int sequenceNo) {
            this.id = id;
            this.text = text;
            this.value = value;
            this.sequenceNo = sequenceNo;
        }

        public long getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getSequenceNo() {
            return sequenceNo;
        }

        public void setSequenceNo(int sequenceNo) {
            this.sequenceNo = sequenceNo;
        }
    }

}
