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

import com.angkorteam.fintech.provider.fixed.AttributeProvider;
import com.angkorteam.fintech.provider.fixed.OperandTypeProvider;
import com.angkorteam.fintech.provider.fixed.OperatorProvider;
import com.angkorteam.fintech.table.TextCell;
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
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;

public class IncentivePopup extends Panel {

    private ModalWindow window;

    private Form<Void> form;
    private AjaxButton addButton;

    private Option attributeValue;
    private AttributeProvider attributeProvider;
    private Select2SingleChoice<Option> attributeField;
    private TextFeedbackPanel attributeFeedback;

    private Option operatorValue;
    private OperatorProvider operatorProvider;
    private Select2SingleChoice<Option> operatorField;
    private TextFeedbackPanel operatorFeedback;

    private String operandValue;
    private TextField<String> operandField;
    private TextFeedbackPanel operandFeedback;

    private Option operandTypeValue;
    private OperandTypeProvider operandTypeProvider;
    private Select2SingleChoice<Option> operandTypeField;
    private TextFeedbackPanel operandTypeFeedback;

    private Double interestValue;
    private TextField<Double> interestField;
    private TextFeedbackPanel interestFeedback;

    private List<Map<String, Object>> incentiveValue;
    private DataTable<Map<String, Object>, String> dataTable;
    private ListDataProvider provider;

    private Object model;

    public IncentivePopup(String id, ModalWindow window, Object model) {
	super(id);
	this.window = window;
	this.model = model;
    }

    @Override
    protected void onInitialize() {
	super.onInitialize();
	
	System.out.println("hello");

	this.incentiveValue = new PropertyModel<List<Map<String, Object>>>(this.model, "incentiveValue").getObject();
	if (this.incentiveValue == null) {
	    this.incentiveValue = Lists.newArrayList();
	}

	this.form = new Form<>("form");
	add(this.form);

	this.addButton = new AjaxButton("addButton");
	this.addButton.setOnSubmit(this::addButtonSubmit);
	this.addButton.setOnError(this::addButtonError);
	this.form.add(this.addButton);

	this.attributeProvider = new AttributeProvider();
	this.attributeField = new Select2SingleChoice<>("attributeField", 0,
		new PropertyModel<>(this, "attributeValue"), this.attributeProvider);
	this.form.add(this.attributeField);
	this.attributeFeedback = new TextFeedbackPanel("attributeFeedback", this.attributeField);
	this.form.add(this.attributeFeedback);

	this.operatorProvider = new OperatorProvider();
	this.operatorField = new Select2SingleChoice<>("operatorField", 0, new PropertyModel<>(this, "operatorValue"),
		this.operatorProvider);
	this.form.add(this.operatorField);
	this.operatorFeedback = new TextFeedbackPanel("operatorFeedback", this.operatorField);
	this.form.add(this.operatorFeedback);

	this.operandField = new TextField<>("operandField", new PropertyModel<>(this, "operandValue"));
	this.form.add(this.operandField);
	this.operandFeedback = new TextFeedbackPanel("operandFeedback", this.operandField);
	this.form.add(this.operandFeedback);

	this.operandTypeProvider = new OperandTypeProvider();
	this.operandTypeField = new Select2SingleChoice<>("operandTypeField", 0,
		new PropertyModel<>(this, "operandTypeValue"), this.operandTypeProvider);
	this.form.add(this.operandTypeField);
	this.operandTypeFeedback = new TextFeedbackPanel("operandTypeFeedback", this.operandTypeField);
	this.form.add(this.operandTypeFeedback);

	this.interestField = new TextField<>("interestField", new PropertyModel<>(this, "interestValue"));
	this.form.add(this.interestField);
	this.interestFeedback = new TextFeedbackPanel("interestFeedback", this.interestField);
	this.form.add(this.interestFeedback);

	// Table
	List<IColumn<Map<String, Object>, String>> incentiveColumn = Lists.newArrayList();
	incentiveColumn
		.add(new TextColumn(Model.of("Attribute"), "periodTypeText", "periodTypeText", this::attributeColumn));
	incentiveColumn.add(new TextColumn(Model.of("Operator"), "periodFrom", "periodFrom", this::operatorColumn));
	incentiveColumn.add(new TextColumn(Model.of("Value"), "periodTo", "periodTo", this::operandColumn));
	incentiveColumn
		.add(new TextColumn(Model.of("Type"), "amountRangeFrom", "amountRangeFrom", this::operandTypeColumn));
	incentiveColumn
		.add(new TextColumn(Model.of("Interest"), "amountRangeTo", "amountRangeTo", this::interestColumn));
	incentiveColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));
	this.provider = new ListDataProvider(this.incentiveValue);
	this.dataTable = new DataTable<>("dataTable", incentiveColumn, this.provider, 20);
	add(this.dataTable);
	this.dataTable.addTopToolbar(new HeadersToolbar<>(this.dataTable, this.provider));
	this.dataTable.addBottomToolbar(new NoRecordsToolbar(this.dataTable));

    }

    protected boolean addButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
	return false;
    }

    protected boolean addButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
	return false;
    }

    protected ItemPanel attributeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String description = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(description));
    }

    protected ItemPanel operatorColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String description = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(description));
    }

    protected ItemPanel operandColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String description = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(description));
    }

    protected ItemPanel operandTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String description = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(description));
    }

    protected ItemPanel interestColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String description = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(description));
    }

    protected void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget target) {
	target.add(this.dataTable);
    }

    protected List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
	List<ActionItem> actions = Lists.newArrayList();
	Boolean active = (Boolean) stringObjectMap.get("active");
	if (active != null && active) {
	    actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.INFO));
	} else {
	    actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.INFO));
	}
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
