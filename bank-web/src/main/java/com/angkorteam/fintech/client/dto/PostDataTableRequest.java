package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.enums.TableTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class PostDataTableRequest {

    private String datatableName;

    private TableTypeEnum apptableName;

    private boolean multiRow;

    private List<Column> columns = new ArrayList<>();

    public String getDatatableName() {
        return datatableName;
    }

    public void setDatatableName(String datatableName) {
        this.datatableName = datatableName;
    }

    public TableTypeEnum getApptableName() {
        return apptableName;
    }

    public void setApptableName(TableTypeEnum apptableName) {
        this.apptableName = apptableName;
    }

    public boolean isMultiRow() {
        return multiRow;
    }

    public void setMultiRow(boolean multiRow) {
        this.multiRow = multiRow;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public static class Column {

        private String type;

        private String name;

        private int length;

        private String code;

        private boolean mandatory;

        private Column() {
        }

        public static Column string(String name, boolean mandatory, int length) {
            Column column = new Column();
            column.type = "String";
            column.name = name;
            column.mandatory = mandatory;
            column.length = length;
            return column;
        }

        public static Column number(String name, boolean mandatory) {
            Column column = new Column();
            column.type = "Number";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static Column yesno(String name, boolean mandatory) {
            Column column = new Column();
            column.type = "Boolean";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static Column decimal(String name, boolean mandatory) {
            Column column = new Column();
            column.type = "Decimal";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static Column date(String name, boolean mandatory) {
            Column column = new Column();
            column.type = "Date";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static Column datetime(String name, boolean mandatory) {
            Column column = new Column();
            column.type = "DateTime";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static Column text(String name, boolean mandatory) {
            Column column = new Column();
            column.type = "Text";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static Column dropdown(String name, boolean mandatory, String code) {
            Column column = new Column();
            column.type = "DropDown";
            column.name = name;
            column.mandatory = mandatory;
            column.code = code;
            return column;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public int getLength() {
            return length;
        }

        public String getCode() {
            return code;
        }

        public boolean isMandatory() {
            return mandatory;
        }
    }
}
