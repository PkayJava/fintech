package com.angkorteam.fintech.popup;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.dto.enums.Attribute;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class IncentivePreviewPopup extends PopupPanel {

    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected List<Map<String, Object>> dataValue;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected ListDataProvider dataProvider;

    public IncentivePreviewPopup(String name, List<Map<String, Object>> incentiveValue) {
        super(name, Maps.newHashMap());
        this.dataValue = incentiveValue;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextColumn(Model.of("Attribute"), "attribute", "attribute", this::dataColumn));
        this.dataColumn.add(new TextColumn(Model.of("Operator"), "operator", "operator", this::dataColumn));
        this.dataColumn.add(new TextColumn(Model.of("Value"), "operand", "operand", this::dataColumn));
        this.dataColumn.add(new TextColumn(Model.of("Type"), "operandType", "operandType", this::dataColumn));
        this.dataColumn.add(new TextColumn(Model.of("Interest"), "interest", "interest", this::dataColumn));
        this.dataProvider = new ListDataProvider(this.dataValue);
        this.dataTable = new DataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        add(this.dataTable);
        this.dataTable.addTopToolbar(new HeadersToolbar<>(this.dataTable, this.dataProvider));
        this.dataTable.addBottomToolbar(new NoRecordsToolbar(this.dataTable));
    }

    @Override
    protected void configureMetaData() {
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("attribute".equals(column) || "operator".equals(column) || "operandType".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("operand".equals(column)) {
            Option attribute = (Option) model.get("attribute");
            if (attribute == null) {
                return new TextCell("");
            } else {
                if (attribute.getId().equals(Attribute.ClientType.name())) {
                    Option value = (Option) model.get("clientTypeOperand");
                    return new TextCell(value);
                } else if (attribute.getId().equals(Attribute.ClientClassification.name())) {
                    Option value = (Option) model.get("clientClassificationOperand");
                    return new TextCell(value);
                } else {
                    if (model.get("numberOperand") instanceof String) {
                        String value = (String) model.get("numberOperand");
                        return new TextCell(value);
                    } else {
                        Long value = (Long) model.get("numberOperand");
                        return new TextCell(value);
                    }
                }
            }
        } else if ("interest".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
