package com.angkorteam.fintech.client.dto;

import java.util.Date;
import java.util.List;

public class SurveyRequest {

    private Long id;
    private List<ComponentData> componentDatas;
    private List<QuestionData> questionDatas;
    private String key;
    private String name;
    private String description;
    private String countryCode;
    private Date validFrom;
    private Date validTo;

    public static class ComponentData {

        private Long id;

        private String key;

        private String text;

        private String description;

        private Integer sequenceNo;

        public ComponentData() {
        }

        public ComponentData(final Long id, final String key, final String text, final String description, final Integer sequenceNo) {
            this.id = id;
            this.key = key;
            this.text = text;
            this.description = description;
            this.sequenceNo = sequenceNo;
        }

        public Long getId() {
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

        public Integer getSequenceNo() {
            return sequenceNo;
        }

        public void setSequenceNo(Integer sequenceNo) {
            this.sequenceNo = sequenceNo;
        }
    }

    public static class QuestionData {

        private Long id;

        private List<ResponseData> responseDatas;

        private String componentKey;

        private String key;

        private String text;

        private String description;

        private Integer sequenceNo;

        public QuestionData() {
        }

        public QuestionData(final Long id, final List<ResponseData> responseDatas, final String componentKey, final String key,
                            final String text, final String description, final Integer sequenceNo) {
            this.id = id;
            this.responseDatas = responseDatas;
            this.componentKey = componentKey;
            this.key = key;
            this.text = text;
            this.description = description;
            this.sequenceNo = sequenceNo;
        }

        public Long getId() {
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

        public Integer getSequenceNo() {
            return sequenceNo;
        }

        public void setSequenceNo(Integer sequenceNo) {
            this.sequenceNo = sequenceNo;
        }
    }

    public static class ResponseData {

        private Long id;

        private String text;

        private Integer value;

        private Integer sequenceNo;

        public ResponseData() {
        }

        public ResponseData(final Long id, final String text, final Integer value, final Integer sequenceNo) {
            this.id = id;
            this.text = text;
            this.value = value;
            this.sequenceNo = sequenceNo;
        }

        public Long getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Integer getSequenceNo() {
            return sequenceNo;
        }

        public void setSequenceNo(Integer sequenceNo) {
            this.sequenceNo = sequenceNo;
        }
    }

}
