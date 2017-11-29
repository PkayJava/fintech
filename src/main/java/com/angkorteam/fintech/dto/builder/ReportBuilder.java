package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

public class ReportBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String reportName;
    private boolean hasReportName;

    private String reportType;
    private boolean hasReportType;

    private String reportSubType;
    private boolean hasReportSubType;

    private String reportCategory;
    private boolean hasReportCategory;

    private boolean useReport;
    private boolean hasUseReport;

    private String description;
    private boolean hasDescription;

    private String reportSql;
    private boolean hasReportSql;

    private List<Map<String, String>> reportParameters = Lists.newArrayList();
    private boolean hasReportParameters;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasReportParameters) {
            object.getObject().put("reportParameters", this.reportParameters);
        }
        if (this.hasReportSql) {
            object.getObject().put("reportSql", this.reportSql);
        }
        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }
        if (this.hasUseReport) {
            object.getObject().put("useReport", this.useReport);
        }
        if (this.hasReportCategory) {
            object.getObject().put("reportCategory", this.reportCategory);
        }
        if (this.hasReportSubType) {
            object.getObject().put("reportSubType", this.reportSubType);
        }
        if (this.hasReportType) {
            object.getObject().put("reportType", this.reportType);
        }
        if (this.hasReportName) {
            object.getObject().put("reportName", this.reportName);
        }
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        return object;
    }

    public ReportBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public ReportBuilder withReportSql(String reportSql) {
        this.reportSql = reportSql;
        this.hasReportSql = true;
        return this;
    }

    public ReportBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    public ReportBuilder withUseReport(boolean useReport) {
        this.useReport = useReport;
        this.hasUseReport = true;
        return this;
    }

    public ReportBuilder withReportSubType(String reportSubType) {
        this.reportSubType = reportSubType;
        this.hasReportSubType = true;
        return this;
    }

    public ReportBuilder withReportType(String reportType) {
        this.reportType = reportType;
        this.hasReportType = true;
        return this;
    }

    public ReportBuilder withReportName(String reportName) {
        this.reportName = reportName;
        this.hasReportName = true;
        return this;
    }

    public ReportBuilder withReportParameter(String id, String parameterId, String reportParameterName) {
        Map<String, String> reportParameter = Maps.newHashMap();
        reportParameter.put("id", id);
        reportParameter.put("parameterId", parameterId);
        reportParameter.put("reportParameterName", reportParameterName);
        this.reportParameters.add(reportParameter);
        this.hasReportParameters = true;
        return this;
    }

}
