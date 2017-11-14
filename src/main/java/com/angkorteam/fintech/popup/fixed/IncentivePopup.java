package com.angkorteam.fintech.popup.fixed;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.AttributeProvider;
import com.angkorteam.fintech.provider.OperandTypeProvider;
import com.angkorteam.fintech.provider.OperatorProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class IncentivePopup extends Panel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton addButton;

    protected Option attributeValue;
    protected AttributeProvider attributeProvider;
    protected Select2SingleChoice<Option> attributeField;
    protected TextFeedbackPanel attributeFeedback;

    protected Option operatorValue;
    protected OperatorProvider operatorProvider;
    protected Select2SingleChoice<Option> operatorField;
    protected TextFeedbackPanel operatorFeedback;

    protected String operandValue;
    protected TextField<String> operandField;
    protected TextFeedbackPanel operandFeedback;

    protected Option operandTypeValue;
    protected OperandTypeProvider operandTypeProvider;
    protected Select2SingleChoice<Option> operandTypeField;
    protected TextFeedbackPanel operandTypeFeedback;

    protected Double interestValue;
    protected TextField<Double> interestField;
    protected TextFeedbackPanel interestFeedback;

    protected List<Map<String, Object>> incentiveValue;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected ListDataProvider provider;

    public IncentivePopup(String id, ModalWindow window, List<Map<String, Object>> incentiveValue) {
        super(id);
        this.window = window;
        this.incentiveValue = incentiveValue;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.addButton = new AjaxButton("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.addButton.setOnError(this::addButtonError);
        this.form.add(this.addButton);

        this.attributeProvider = new AttributeProvider();
        this.attributeField = new Select2SingleChoice<>("attributeField", 0, new PropertyModel<>(this, "attributeValue"), this.attributeProvider);
        this.attributeField.setLabel(Model.of("Attribute"));
        this.form.add(this.attributeField);
        this.attributeFeedback = new TextFeedbackPanel("attributeFeedback", this.attributeField);
        this.form.add(this.attributeFeedback);

        this.operatorProvider = new OperatorProvider();
        this.operatorField = new Select2SingleChoice<>("operatorField", 0, new PropertyModel<>(this, "operatorValue"), this.operatorProvider);
        this.operatorField.setLabel(Model.of("Operator"));
        this.form.add(this.operatorField);
        this.operatorFeedback = new TextFeedbackPanel("operatorFeedback", this.operatorField);
        this.form.add(this.operatorFeedback);

        this.operandField = new TextField<>("operandField", new PropertyModel<>(this, "operandValue"));
        this.operandField.setLabel(Model.of("Value"));
        this.form.add(this.operandField);
        this.operandFeedback = new TextFeedbackPanel("operandFeedback", this.operandField);
        this.form.add(this.operandFeedback);

        this.operandTypeProvider = new OperandTypeProvider();
        this.operandTypeField = new Select2SingleChoice<>("operandTypeField", 0, new PropertyModel<>(this, "operandTypeValue"), this.operandTypeProvider);
        this.operandTypeField.setLabel(Model.of("Type"));
        this.form.add(this.operandTypeField);
        this.operandTypeFeedback = new TextFeedbackPanel("operandTypeFeedback", this.operandTypeField);
        this.form.add(this.operandTypeFeedback);

        this.interestField = new TextField<>("interestField", new PropertyModel<>(this, "interestValue"));
        this.interestField.setLabel(Model.of("Interest"));
        this.form.add(this.interestField);
        this.interestFeedback = new TextFeedbackPanel("interestFeedback", this.interestField);
        this.form.add(this.interestFeedback);

        // Table
        List<IColumn<Map<String, Object>, String>> incentiveColumn = Lists.newArrayList();
        incentiveColumn.add(new TextColumn(Model.of("Attribute"), "attribute", "attribute", this::attributeColumn));
        incentiveColumn.add(new TextColumn(Model.of("Operator"), "operator", "operator", this::operatorColumn));
        incentiveColumn.add(new TextColumn(Model.of("Value"), "operand", "operand", this::operandColumn));
        incentiveColumn.add(new TextColumn(Model.of("Type"), "operandType", "operandType", this::operandTypeColumn));
        incentiveColumn.add(new TextColumn(Model.of("Interest"), "interest", "interest", this::interestColumn));
        incentiveColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));
        this.provider = new ListDataProvider(this.incentiveValue);
        this.dataTable = new DataTable<>("dataTable", incentiveColumn, this.provider, 20);
        add(this.dataTable);
        this.dataTable.addTopToolbar(new HeadersToolbar<>(this.dataTable, this.provider));
        this.dataTable.addBottomToolbar(new NoRecordsToolbar(this.dataTable));
    }

    protected boolean addButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("attribute", this.attributeValue);
        item.put("operator", this.operatorValue);
        item.put("operand", this.operandValue);
        item.put("operandType", this.operandTypeValue);
        item.put("interest", this.interestValue);
        this.incentiveValue.add(item);
        target.add(this.dataTable);
        return false;
    }

    protected boolean addButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
        return false;
    }

    protected ItemPanel attributeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Option value = (Option) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel operatorColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Option value = (Option) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel operandColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel operandTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Option value = (Option) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel interestColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void actionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        if ("delete".equals(s)) {
            int index = -1;
            for (int i = 0; i < this.incentiveValue.size(); i++) {
                Map<String, Object> column = this.incentiveValue.get(i);
                if (model.get("uuid").equals(column.get("uuid"))) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                this.incentiveValue.remove(index);
            }
            target.add(this.dataTable);
        }
    }

    protected List<ActionItem> actionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setElementId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

    protected boolean okayButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
        target.add(this.form);
        return true;
    }

}
