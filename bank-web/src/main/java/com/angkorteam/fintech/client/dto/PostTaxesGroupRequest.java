package com.angkorteam.fintech.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostTaxesGroupRequest {

    private String name;

    private List<TaxComponent> taxComponents = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaxComponent> getTaxComponents() {
        return taxComponents;
    }

    public void setTaxComponents(List<TaxComponent> taxComponents) {
        this.taxComponents = taxComponents;
    }

    public static class TaxComponent {

        private long id;

        private long taxComponentId;

        private Date startDate;

        private Date endDate;

        public TaxComponent() {
        }

        public TaxComponent(long id, long taxComponentId, Date startDate, Date endDate) {
            this.id = id;
            this.taxComponentId = taxComponentId;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getTaxComponentId() {
            return taxComponentId;
        }

        public void setTaxComponentId(long taxComponentId) {
            this.taxComponentId = taxComponentId;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

    }

}
