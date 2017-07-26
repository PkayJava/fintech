package com.angkorteam.fintech.pages.role;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.RoleHelper;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RoleBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLink;

    @Override
    protected void onInitialize() {
	super.onInitialize();
	this.provider = new JdbcProvider("m_role");
	this.provider.boardField("id", "id", Long.class);
	this.provider.boardField("description", "description", String.class);
	this.provider.boardField("is_disabled", "disabled", Boolean.class);
	this.provider.boardField("name", "name", String.class);

	List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
	columns.add(new TextFilterColumn(this.provider, ItemClass.Long, Model.of("ID"), "id", "id", this::idColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name",
		this::nameColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Description"), "description",
		"description", this::descriptionColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.Boolean, Model.of("Is Disabled ?"), "disabled",
		"disabled", this::disabledColumn));
	columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

	FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
	add(filterForm);

	this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
	this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
	filterForm.add(this.dataTable);

	this.createLink = new BookmarkablePageLink<>("createLink", RoleCreatePage.class);
	add(this.createLink);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
	try {
	    Long id = (Long) stringObjectMap.get("id");
	    if ("enable".equals(s)) {
		RoleHelper.enable((Session) getSession(), String.valueOf(id));
		ajaxRequestTarget.add(this.dataTable);
	    } else if ("disable".equals(s)) {
		RoleHelper.disable((Session) getSession(), String.valueOf(id));
		ajaxRequestTarget.add(this.dataTable);
	    } else if ("delete".equals(s)) {
		RoleHelper.delete((Session) getSession(), String.valueOf(id));
		ajaxRequestTarget.add(this.dataTable);
	    }
	} catch (UnirestException e) {
	}
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
	List<ActionItem> actions = Lists.newArrayList();
	Boolean disabled = (Boolean) stringObjectMap.get("disabled");
	if (disabled == null || disabled) {
	    actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.PRIMARY));
	} else {
	    actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.PRIMARY));
	}
	actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.WARNING));
	return actions;
    }

    private ItemPanel idColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Long id = (Long) model.get(jdbcColumn);
	return new TextCell(Model.of(String.valueOf(id)));
    }

    private ItemPanel descriptionColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String externalId = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(externalId));
    }

    private ItemPanel disabledColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Boolean disabled = (Boolean) model.get(jdbcColumn);
	if (disabled == null || disabled) {
	    return new BadgeCell(BadgeType.Danger, Model.of("Yes"));
	} else {
	    return new BadgeCell(BadgeType.Success, Model.of("No"));
	}
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String name = (String) model.get(jdbcColumn);
	if (Strings.isNullOrEmpty(name)) {
	    return new TextCell(Model.of(name));
	} else {
	    PageParameters parameters = new PageParameters();
	    parameters.add("roleId", model.get("id"));
	    return new LinkCell(RolePermissionPage.class, parameters, Model.of(name));
	}
    }

}
