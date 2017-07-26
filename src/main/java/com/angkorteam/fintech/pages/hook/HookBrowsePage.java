package com.angkorteam.fintech.pages.hook;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.HookHelper;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class HookBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private SingleChoiceProvider templateProvider;
    private Option templateValue;
    private Select2SingleChoice<Option> templateField;
    private TextFeedbackPanel templateFeedback;

    private Form<Void> form;
    private Button createButton;

    @Override
    protected void onInitialize() {
	super.onInitialize();
	this.provider = new JdbcProvider("m_hook");
	this.provider.addJoin("left JOIN m_hook_templates ON m_hook.template_id = m_hook_templates.id");
	this.provider.boardField("m_hook.id", "id", Long.class);
	this.provider.boardField("m_hook.name", "name", String.class);
	this.provider.boardField("m_hook_templates.name", "template", String.class);
	this.provider.boardField("m_hook.is_active", "active", Integer.class);

	this.provider.selectField("id", Long.class);

	List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
	columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name",
		this::nameColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Template"), "template", "template",
		this::templateColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.Integer, Model.of("Is Active ?"), "active", "active",
		this::activeColumn));
	columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

	FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
	add(filterForm);

	this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
	this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
	filterForm.add(this.dataTable);

	this.form = new Form<>("form");
	add(this.form);

	this.createButton = new Button("createButton");
	this.createButton.setOnSubmit(this::createButtonSubmit);
	this.form.add(this.createButton);

	this.templateProvider = new SingleChoiceProvider("m_hook_templates", "id", "name");
	this.templateField = new Select2SingleChoice<>("templateField", new PropertyModel<>(this, "templateValue"),
		this.templateProvider);
	this.templateField.setRequired(true);
	this.form.add(this.templateField);
	this.templateFeedback = new TextFeedbackPanel("templateFeedback", this.templateField);
	this.form.add(this.templateFeedback);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
	Long id = (Long) stringObjectMap.get("id");
	try {
	    HookHelper.delete((Session) getSession(), String.valueOf(id));
	} catch (UnirestException e) {
	}
	ajaxRequestTarget.add(this.dataTable);
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void createButtonSubmit(Button button) {
	PageParameters parameters = new PageParameters();
	parameters.add("templateId", this.templateValue.getId());
	setResponsePage(HookCreatePage.class, parameters);
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String name = (String) model.get(jdbcColumn);
	PageParameters parameters = new PageParameters();
	parameters.add("hookId", model.get("id"));
	return new LinkCell(HookModifyPage.class, parameters, Model.of(name));
    }

    private ItemPanel templateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String template = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(template));
    }

    private ItemPanel activeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Integer active = (Integer) model.get(jdbcColumn);
	if (active != null && active == 1) {
	    return new BadgeCell(BadgeType.Success, Model.of("Yes"));
	} else {
	    return new BadgeCell(BadgeType.Danger, Model.of("No"));
	}
    }

}
