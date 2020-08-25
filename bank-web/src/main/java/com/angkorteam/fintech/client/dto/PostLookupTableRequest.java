package com.angkorteam.fintech.client.dto;

import java.util.ArrayList;
import java.util.List;

public class PostLookupTableRequest {

    private String key;

    private long description;

    private List<LookupTableEntry> entries = new ArrayList<>();

    public PostLookupTableRequest() {
    }

    public PostLookupTableRequest(final String key, final long description, final List<LookupTableEntry> entries) {
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

    public long getDescription() {
        return description;
    }

    public void setDescription(long description) {
        this.description = description;
    }

    public List<LookupTableEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LookupTableEntry> entries) {
        this.entries = entries;
    }

    public static class LookupTableEntry {

        private int valueFrom;

        private int valueTo;

        private double score;

        public LookupTableEntry() {
        }

        public LookupTableEntry(final int valueFrom, final int valueTo, final double score) {
            this.valueFrom = valueFrom;
            this.valueTo = valueTo;
            this.score = score;
        }

        public int getValueFrom() {
            return valueFrom;
        }

        public void setValueFrom(int valueFrom) {
            this.valueFrom = valueFrom;
        }

        public int getValueTo() {
            return valueTo;
        }

        public void setValueTo(int valueTo) {
            this.valueTo = valueTo;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }

}
