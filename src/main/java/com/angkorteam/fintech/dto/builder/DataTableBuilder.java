package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.angkorteam.fintech.client.enums.TableTypeEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class DataTableBuilder implements Serializable {

    private String datatableName;
    private boolean hasDatatableName;

    private String apptableName;
    private boolean hasApptableName;

    private boolean multiRow;
    private boolean hasMultiRow;

    private List<Map<String, Object>> columns = Lists.newArrayList();
    private boolean hasColumns;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        if (this.hasDatatableName) {
            object.getObject().put("datatableName", this.datatableName);
        }
        if (this.hasApptableName) {
            object.getObject().put("apptableName", this.apptableName);
        }
        if (this.hasMultiRow) {
            object.getObject().put("multiRow", this.multiRow);
        }
        if (this.hasColumns) {
            object.getObject().put("columns", this.columns);
        }
        return object;
    }

    public DataTableBuilder withDataTableName(String dataTableName) {
        this.datatableName = dataTableName;
        this.hasDatatableName = true;
        return this;
    }

    public DataTableBuilder withAppTableName(TableTypeEnum apptableName) {
        this.apptableName = apptableName.getLiteral();
        this.hasApptableName = true;
        return this;
    }

    public DataTableBuilder withMultiRow(boolean multiRow) {
        this.multiRow = multiRow;
        this.hasMultiRow = true;
        return this;
    }

    protected Map<String, Object> buildColumn(String type, String name, boolean mandatory) {
        Map<String, Object> column = Maps.newHashMap();
        column.put("type", type);
        column.put("name", name);
        column.put("mandatory", mandatory);
        return column;
    }

    public DataTableBuilder withColumnString(String name, boolean mandatory, Long length) {
        Map<String, Object> column = buildColumn("String", name, mandatory);
        column.put("length", length);
        columns.add(column);
        this.hasColumns = true;
        return this;
    }

    public DataTableBuilder withColumnNumber(String name, boolean mandatory) {
        Map<String, Object> column = buildColumn("Number", name, mandatory);
        columns.add(column);
        this.hasColumns = true;
        return this;
    }

    public DataTableBuilder withColumnBoolean(String name, boolean mandatory) {
        Map<String, Object> column = buildColumn("Boolean", name, mandatory);
        columns.add(column);
        this.hasColumns = true;
        return this;
    }

    public DataTableBuilder withColumnDecimal(String name, boolean mandatory) {
        Map<String, Object> column = buildColumn("Decimal", name, mandatory);
        columns.add(column);
        this.hasColumns = true;
        return this;
    }

    public DataTableBuilder withColumnDate(String name, boolean mandatory) {
        Map<String, Object> column = buildColumn("Date", name, mandatory);
        columns.add(column);
        this.hasColumns = true;
        return this;
    }

    public DataTableBuilder withColumnDateTime(String name, boolean mandatory) {
        Map<String, Object> column = buildColumn("DateTime", name, mandatory);
        columns.add(column);
        this.hasColumns = true;
        return this;
    }

    public DataTableBuilder withColumnText(String name, boolean mandatory) {
        Map<String, Object> column = buildColumn("Text", name, mandatory);
        columns.add(column);
        this.hasColumns = true;
        return this;
    }

    public DataTableBuilder withColumnDropDown(String name, boolean mandatory, String code) {
        Map<String, Object> column = buildColumn("DropDown", name, mandatory);
        column.put("code", code);
        columns.add(column);
        this.hasColumns = true;
        return this;
    }

}
