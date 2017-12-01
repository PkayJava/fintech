package com.angkorteam.fintech.popup;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

public class IncentivePreviewPopup extends PopupPanel {

    protected ModalWindow window;

    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected List<Map<String, Object>> dataValue;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected ListDataProvider dataProvider;

    public IncentivePreviewPopup(String name, ModalWindow window, List<Map<String, Object>> incentiveValue) {
        super(name, window);
        this.window = window;
        this.dataValue = incentiveValue;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        // Table
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
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("attribute".equals(column) || "operator".equals(column) || "operandType".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("operand".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("interest".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
