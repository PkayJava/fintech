package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.enums.TableTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class PutDataTableRequest {

    private TableTypeEnum apptableName;

    private List<AddColumn> addColumns = new ArrayList<>();

    private List<DropColumn> dropColumns = new ArrayList<>();

    private List<ChangeColumn> changeColumns = new ArrayList<>();

    public TableTypeEnum getApptableName() {
        return apptableName;
    }

    public void setApptableName(TableTypeEnum apptableName) {
        this.apptableName = apptableName;
    }

    public List<AddColumn> getAddColumns() {
        return addColumns;
    }

    public void setAddColumns(List<AddColumn> addColumns) {
        this.addColumns = addColumns;
    }

    public List<DropColumn> getDropColumns() {
        return dropColumns;
    }

    public void setDropColumns(List<DropColumn> dropColumns) {
        this.dropColumns = dropColumns;
    }

    public List<ChangeColumn> getChangeColumns() {
        return changeColumns;
    }

    public void setChangeColumns(List<ChangeColumn> changeColumns) {
        this.changeColumns = changeColumns;
    }

    public static class DropColumn {

        private String name;

        public DropColumn() {
        }

        public DropColumn(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ChangeColumn {

        private String type;

        private String name;

        private String newName;

        private String code;

        private String newCode;

        private int length;

        private boolean mandatory;

        public ChangeColumn() {
        }

        public ChangeColumn(String type, String name, String newName, String code, String newCode, int length, boolean mandatory) {
            this.type = type;
            this.name = name;
            this.newName = newName;
            this.code = code;
            this.newCode = newCode;
            this.length = length;
            this.mandatory = mandatory;
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

        public String getNewName() {
            return newName;
        }

        public String getNewCode() {
            return newCode;
        }
    }

    public static class AddColumn {

        private String type;

        private String name;

        private int length;

        private String code;

        private boolean mandatory;

        private AddColumn() {
        }

        public static AddColumn string(String name, boolean mandatory, int length) {
            AddColumn column = new AddColumn();
            column.type = "String";
            column.name = name;
            column.mandatory = mandatory;
            column.length = length;
            return column;
        }

        public static AddColumn number(String name, boolean mandatory) {
            AddColumn column = new AddColumn();
            column.type = "Number";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static AddColumn yesno(String name, boolean mandatory) {
            AddColumn column = new AddColumn();
            column.type = "Boolean";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static AddColumn decimal(String name, boolean mandatory) {
            AddColumn column = new AddColumn();
            column.type = "Decimal";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static AddColumn date(String name, boolean mandatory) {
            AddColumn column = new AddColumn();
            column.type = "Date";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static AddColumn datetime(String name, boolean mandatory) {
            AddColumn column = new AddColumn();
            column.type = "DateTime";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static AddColumn text(String name, boolean mandatory) {
            AddColumn column = new AddColumn();
            column.type = "Text";
            column.name = name;
            column.mandatory = mandatory;
            return column;
        }

        public static AddColumn dropdown(String name, boolean mandatory, String code) {
            AddColumn column = new AddColumn();
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
