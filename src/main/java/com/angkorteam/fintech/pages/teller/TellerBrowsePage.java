package com.angkorteam.fintech.pages.teller;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.TellerHelper;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/13/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TellerBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLink;

    @Override
    protected void onInitialize() {
	super.onInitialize();
	this.provider = new JdbcProvider("m_tellers");
	this.provider.addJoin("LEFT JOIN m_office on m_tellers.office_id = m_office.id");
	this.provider.boardField("m_tellers.id", "id", Long.class);
	this.provider.boardField("m_office.name", "branch", String.class);
	this.provider.boardField("m_tellers.name", "name", String.class);
	this.provider.boardField("case m_tellers.state when 300 then 'Active' when 400 then 'Inactive' end", "state",
		Integer.class);
	this.provider.boardField("m_tellers.valid_from", "valid_from", Date.class);
	this.provider.boardField("m_tellers.valid_to", "valid_to", Date.class);

	this.provider.selectField("id", Long.class);

	List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
	columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Branch"), "branch", "branch",
		this::branchColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name",
		this::nameColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("State"), "state", "state",
		this::stateColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.Date, Model.of("Valid From"), "valid_from",
		"valid_from", this::validFromColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.Date, Model.of("Valid To"), "valid_to", "valid_to",
		this::validToColumn));
	columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

	FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
	add(filterForm);

	this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
	this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
	filterForm.add(this.dataTable);

	this.createLink = new BookmarkablePageLink<>("createLink", TellerCreatePage.class);
	add(this.createLink);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
	Long id = (Long) stringObjectMap.get("id");
	try {
	    TellerHelper.delete((Session) getSession(), String.valueOf(id));
	} catch (UnirestException e) {
	}
	ajaxRequestTarget.add(this.dataTable);
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private ItemPanel stateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String state = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(String.valueOf(state)));
    }

    private ItemPanel branchColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String branch = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(branch));
    }

    private ItemPanel validFromColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Date validFrom = (Date) model.get(jdbcColumn);
	if (validFrom != null) {
	    return new TextCell(Model.of(DateFormatUtils.format(validFrom, "yyyy-MM-dd")));
	} else {
	    return new TextCell(Model.of(""));
	}
    }

    private ItemPanel validToColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Date validTo = (Date) model.get(jdbcColumn);
	if (validTo != null) {
	    return new TextCell(Model.of(DateFormatUtils.format(validTo, "yyyy-MM-dd")));
	} else {
	    return new TextCell(Model.of(""));
	}
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String name = (String) model.get(jdbcColumn);
	PageParameters parameters = new PageParameters();
	parameters.add("tellerId", model.get("id"));
	return new LinkCell(TellerModifyPage.class, parameters, Model.of(name));
    }

}
