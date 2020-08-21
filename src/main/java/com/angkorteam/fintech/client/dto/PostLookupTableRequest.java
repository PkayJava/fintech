package com.angkorteam.fintech.client.dto;

import java.util.List;

public class PostLookupTableRequest {

    private String key;

    private String description;

    private List<LookupTableEntry> entries;

    public PostLookupTableRequest() {
    }

    public PostLookupTableRequest(final String key, final String description, final List<LookupTableEntry> entries) {
        this.key = key;
        this.description = description;
        this.entries = entries;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LookupTableEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LookupTableEntry> entries) {
        this.entries = entries;
    }

    public static class LookupTableEntry {

        private Integer valueFrom;

        private Integer valueTo;

        private Double score;

        public LookupTableEntry() {
        }

        public LookupTableEntry(final Integer valueFrom, final Integer valueTo, final Double score) {
            this.valueFrom = valueFrom;
            this.valueTo = valueTo;
            this.score = score;
        }

        public Integer getValueFrom() {
            return valueFrom;
        }

        public void setValueFrom(Integer valueFrom) {
            this.valueFrom = valueFrom;
        }

        public Integer getValueTo() {
            return valueTo;
        }

        public void setValueTo(Integer valueTo) {
            this.valueTo = valueTo;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }
    }

}
