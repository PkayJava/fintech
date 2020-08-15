package com.angkorteam.fintech.client.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostFloatingRatesRequest {

    private String name;

    @SerializedName("isBaseLendingRate")
    private boolean baseLendingRate;

    @SerializedName("isActive")
    private boolean active;

    private List<RatePeriod> ratePeriods = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBaseLendingRate() {
        return baseLendingRate;
    }

    public void setBaseLendingRate(boolean baseLendingRate) {
        this.baseLendingRate = baseLendingRate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<RatePeriod> getRatePeriods() {
        return ratePeriods;
    }

    public void setRatePeriods(List<RatePeriod> ratePeriods) {
        this.ratePeriods = ratePeriods;
    }

    public static class RatePeriod {

        private Date fromDate;

        private double interestRate;

        @SerializedName("isDifferentialToBaseLendingRate")
        private boolean differentialToBaseLendingRate;

        public RatePeriod() {
        }

        public RatePeriod(Date fromDate, double interestRate, boolean differentialToBaseLendingRate) {
            this.fromDate = fromDate;
            this.interestRate = interestRate;
            this.differentialToBaseLendingRate = differentialToBaseLendingRate;
        }

        public Date getFromDate() {
            return fromDate;
        }

        public void setFromDate(Date fromDate) {
            this.fromDate = fromDate;
        }

        public double getInterestRate() {
            return interestRate;
        }

        public void setInterestRate(double interestRate) {
            this.interestRate = interestRate;
        }

        public boolean isDifferentialToBaseLendingRate() {
            return differentialToBaseLendingRate;
        }

        public void setDifferentialToBaseLendingRate(boolean differentialToBaseLendingRate) {
            this.differentialToBaseLendingRate = differentialToBaseLendingRate;
        }

    }

}
